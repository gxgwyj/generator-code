/**
 *    Copyright 2006-2015 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package com.xyz.generator.api;


import com.xyz.generator.config.Configuration;
import com.xyz.generator.config.Context;
import com.xyz.generator.config.MergeConstants;
import com.xyz.generator.exception.InvalidConfigurationException;
import com.xyz.generator.exception.ShellException;
import com.xyz.generator.internal.DefaultShellCallback;
import com.xyz.generator.internal.NullProgressCallback;
import com.xyz.generator.internal.ObjectFactory;
import com.xyz.generator.internal.XmlFileMergerJaxp;
import com.xyz.generator.internal.util.ClassloaderUtility;
import com.xyz.generator.internal.util.messages.Messages;

import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class MyBatisGenerator {

    /** The configuration. */
    private Configuration configuration;

    /** The shell callback. */
    private ShellCallback shellCallback;

    /** The generated java files. */
    private List<GeneratedJavaFile> generatedJavaFiles;

    /** The generated xml files. */
    private List<GeneratedXmlFile> generatedXmlFiles;

    /** The warnings. */
    private List<String> warnings;

    /** The projects. */
    private Set<String> projects;

    public MyBatisGenerator(Configuration configuration, ShellCallback shellCallback,
                            List<String> warnings) throws InvalidConfigurationException {
        super();
        if (configuration == null) {
            throw new IllegalArgumentException(Messages.getString("RuntimeError.2")); //$NON-NLS-1$
        } else {
            this.configuration = configuration;
        }

        if (shellCallback == null) {
            this.shellCallback = new DefaultShellCallback(false);
        } else {
            this.shellCallback = shellCallback;
        }

        if (warnings == null) {
            this.warnings = new ArrayList<String>();
        } else {
            this.warnings = warnings;
        }
        generatedJavaFiles = new ArrayList<GeneratedJavaFile>();
        generatedXmlFiles = new ArrayList<GeneratedXmlFile>();
        projects = new HashSet<String>();

        this.configuration.validate();
    }


    public void generate(ProgressCallback callback) throws SQLException,
            IOException, InterruptedException {
        generate(callback, null, null);
    }


    public void generate(ProgressCallback callback, Set<String> contextIds)
            throws SQLException, IOException, InterruptedException {
        generate(callback, contextIds, null);
    }


    /**
     * 读取生成配置文件后生成文件
     * @param callback
     * @param contextIds
     * @param fullyQualifiedTableNames
     * @throws SQLException
     * @throws IOException
     * @throws InterruptedException
     */
    public void generate(ProgressCallback callback, Set<String> contextIds,
            Set<String> fullyQualifiedTableNames) throws SQLException,
            IOException, InterruptedException {

        if (callback == null) {
            callback = new NullProgressCallback();
        }

        generatedJavaFiles.clear();
        generatedXmlFiles.clear();

        // calculate the contexts to run
        List<Context> contextsToRun;
        if (contextIds == null || contextIds.size() == 0) {
            contextsToRun = configuration.getContexts();
        } else {
            contextsToRun = new ArrayList<Context>();
            for (Context context : configuration.getContexts()) {
                if (contextIds.contains(context.getId())) {
                    contextsToRun.add(context);
                }
            }
        }

        // setup custom classloader if required
        if (configuration.getClassPathEntries().size() > 0) {
            ClassLoader classLoader = ClassloaderUtility.getCustomClassloader(configuration.getClassPathEntries());
            ObjectFactory.addExternalClassLoader(classLoader);
        }

        // now run the introspections...
        int totalSteps = 0;
        for (Context context : contextsToRun) {
            totalSteps += context.getIntrospectionSteps();
        }
        callback.introspectionStarted(totalSteps);

        //获取表结构
        for (Context context : contextsToRun) {
            context.introspectTables(callback, warnings,
                    fullyQualifiedTableNames);
        }

        // now run the generates
        totalSteps = 0;
        for (Context context : contextsToRun) {
            totalSteps += context.getGenerationSteps();
        }
        callback.generationStarted(totalSteps);

        //创建文件内容
        for (Context context : contextsToRun) {
            context.generateFiles(callback, generatedJavaFiles,
                    generatedXmlFiles, warnings);
        }

        // now save the files
        callback.saveStarted(generatedXmlFiles.size()
                + generatedJavaFiles.size());

        for (GeneratedXmlFile gxf : generatedXmlFiles) {
            projects.add(gxf.getTargetProject());

            File targetFile;
            String source;
            try {
                File directory = shellCallback.getDirectory(gxf
                        .getTargetProject(), gxf.getTargetPackage());
                targetFile = new File(directory, gxf.getFileName());
                if (targetFile.exists()) {
                    if (gxf.isMergeable()) {
                        source = XmlFileMergerJaxp.getMergedSource(gxf,
                                targetFile);
                    } else if (shellCallback.isOverwriteEnabled()) {
                        source = gxf.getFormattedContent();
                        warnings.add(Messages.getString("Warning.11", //$NON-NLS-1$
                                targetFile.getAbsolutePath()));
                    } else {
                        source = gxf.getFormattedContent();
                        targetFile = getUniqueFileName(directory, gxf
                                .getFileName());
                        warnings.add(Messages.getString(
                                "Warning.2", targetFile.getAbsolutePath())); //$NON-NLS-1$
                    }
                } else {
                    source = gxf.getFormattedContent();
                }
            } catch (ShellException e) {
                warnings.add(e.getMessage());
                continue;
            }

            callback.checkCancel();
            callback.startTask(Messages.getString(
                    "Progress.15", targetFile.getName())); //$NON-NLS-1$
            writeFile(targetFile, source, "UTF-8"); //$NON-NLS-1$
        }

        for (GeneratedJavaFile gjf : generatedJavaFiles) {
            projects.add(gjf.getTargetProject());

            File targetFile;
            String source;
            try {
                File directory = shellCallback.getDirectory(gjf
                        .getTargetProject(), gjf.getTargetPackage());
                targetFile = new File(directory, gjf.getFileName());
                if (targetFile.exists()) {
                    if (shellCallback.isMergeSupported()) {
                        source = shellCallback.mergeJavaFile(gjf
                                .getFormattedContent(), targetFile
                                .getAbsolutePath(),
                                MergeConstants.OLD_ELEMENT_TAGS,
                                gjf.getFileEncoding());
                    } else if (shellCallback.isOverwriteEnabled()) {
                        source = gjf.getFormattedContent();
                        warnings.add(Messages.getString("Warning.11", //$NON-NLS-1$
                                targetFile.getAbsolutePath()));
                    } else {
                        source = gjf.getFormattedContent();
                        targetFile = getUniqueFileName(directory, gjf
                                .getFileName());
                        warnings.add(Messages.getString(
                                "Warning.2", targetFile.getAbsolutePath())); //$NON-NLS-1$
                    }
                } else {
                    source = gjf.getFormattedContent();
                }

                callback.checkCancel();
                callback.startTask(Messages.getString(
                        "Progress.15", targetFile.getName())); //$NON-NLS-1$
//                writeFile(targetFile, source, gjf.getFileEncoding());
                writeFile(targetFile, source, "UTF-8");
            } catch (ShellException e) {
                warnings.add(e.getMessage());
            }
        }

        for (String project : projects) {
            shellCallback.refreshProject(project);
        }

        callback.done();
    }

    private void writeFile(File file, String content, String fileEncoding) throws IOException {
        FileOutputStream fos = new FileOutputStream(file, false);
        OutputStreamWriter osw;
        if (fileEncoding == null) {
            osw = new OutputStreamWriter(fos);
        } else {
            osw = new OutputStreamWriter(fos, fileEncoding);
        }
        
        BufferedWriter bw = new BufferedWriter(osw);
        bw.write(content);
        bw.close();
    }

    private File getUniqueFileName(File directory, String fileName) {
        File answer = null;

        // try up to 1000 times to generate a unique file name
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i < 1000; i++) {
            sb.setLength(0);
            sb.append(fileName);
            sb.append('.');
            sb.append(i);

            File testFile = new File(directory, sb.toString());
            if (!testFile.exists()) {
                answer = testFile;
                break;
            }
        }

        if (answer == null) {
            throw new RuntimeException(Messages.getString(
                    "RuntimeError.3", directory.getAbsolutePath())); //$NON-NLS-1$
        }

        return answer;
    }
}

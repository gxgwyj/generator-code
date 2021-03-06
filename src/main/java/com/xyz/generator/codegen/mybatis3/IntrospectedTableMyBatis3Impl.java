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
package com.xyz.generator.codegen.mybatis3;


import com.xyz.generator.api.GeneratedJavaFile;
import com.xyz.generator.api.GeneratedXmlFile;
import com.xyz.generator.api.IntrospectedTable;
import com.xyz.generator.api.ProgressCallback;
import com.xyz.generator.api.dom.java.CompilationUnit;
import com.xyz.generator.api.dom.xml.Document;
import com.xyz.generator.codegen.AbstractGenerator;
import com.xyz.generator.codegen.AbstractJavaClientGenerator;
import com.xyz.generator.codegen.AbstractJavaGenerator;
import com.xyz.generator.codegen.AbstractXmlGenerator;
import com.xyz.generator.codegen.mybatis3.javamapper.AnnotatedClientGenerator;
import com.xyz.generator.codegen.mybatis3.javamapper.JavaMapperGenerator;
import com.xyz.generator.codegen.mybatis3.javamapper.MixedClientGenerator;
import com.xyz.generator.codegen.mybatis3.model.BaseRecordGenerator;
import com.xyz.generator.codegen.mybatis3.model.ExampleGenerator;
import com.xyz.generator.codegen.mybatis3.model.PrimaryKeyGenerator;
import com.xyz.generator.codegen.mybatis3.model.RecordWithBLOBsGenerator;
import com.xyz.generator.codegen.mybatis3.xmlmapper.XMLMapperGenerator;
import com.xyz.generator.config.PropertyRegistry;
import com.xyz.generator.internal.ObjectFactory;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * The Class IntrospectedTableMyBatis3Impl.
 *
 * @author Jeff Butler
 */
public class IntrospectedTableMyBatis3Impl extends IntrospectedTable {

    private static final Logger logger = Logger.getLogger(IntrospectedTableMyBatis3Impl.class);
    
    /** The java model generators. */
    protected List<AbstractJavaGenerator> javaModelGenerators;
    
    /** The client generators. */
    protected List<AbstractJavaGenerator> clientGenerators;
    
    /** The xml mapper generator. */
    protected AbstractXmlGenerator xmlMapperGenerator;

    /**
     * Instantiates a new introspected table my batis3 impl.
     */
    public IntrospectedTableMyBatis3Impl() {
        super(TargetRuntime.MYBATIS3);
        javaModelGenerators = new ArrayList<AbstractJavaGenerator>();
        clientGenerators = new ArrayList<AbstractJavaGenerator>();
    }

    /* (non-Javadoc)
     * @see com.xyz.generator.api.IntrospectedTable#calculateGenerators(java.util.List, com.xyz.generator.api.ProgressCallback)
     */
    @Override
    public void calculateGenerators(List<String> warnings,
            ProgressCallback progressCallback) {
        calculateJavaModelGenerators(warnings, progressCallback);
        
        AbstractJavaClientGenerator javaClientGenerator =
            calculateClientGenerators(warnings, progressCallback);
            
        calculateXmlMapperGenerator(javaClientGenerator, warnings, progressCallback);
    }

    /**
     * Calculate xml mapper generator.
     *
     * @param javaClientGenerator
     *            the java client generator
     * @param warnings
     *            the warnings
     * @param progressCallback
     *            the progress callback
     */
    protected void calculateXmlMapperGenerator(AbstractJavaClientGenerator javaClientGenerator, 
            List<String> warnings,
            ProgressCallback progressCallback) {
        if (javaClientGenerator == null) {
            if (context.getSqlMapGeneratorConfiguration() != null) {
                xmlMapperGenerator = new XMLMapperGenerator();
            }
        } else {
            xmlMapperGenerator = javaClientGenerator.getMatchedXMLGenerator();
        }
        
        initializeAbstractGenerator(xmlMapperGenerator, warnings,
                progressCallback);
    }

    /**
     * Calculate client generators.
     *
     * @param warnings
     *            the warnings
     * @param progressCallback
     *            the progress callback
     * @return true if an XML generator is required
     */
    protected AbstractJavaClientGenerator calculateClientGenerators(List<String> warnings,
            ProgressCallback progressCallback) {
        if (!rules.generateJavaClient()) {
            return null;
        }
        
        AbstractJavaClientGenerator javaGenerator = createJavaClientGenerator();
        if (javaGenerator == null) {
            return null;
        }

        initializeAbstractGenerator(javaGenerator, warnings, progressCallback);
        clientGenerators.add(javaGenerator);
        
        return javaGenerator;
    }
    
    /**
     * Creates the java client generator.
     *
     * @return the abstract java client generator
     */
    protected AbstractJavaClientGenerator createJavaClientGenerator() {
        if (context.getJavaClientGeneratorConfiguration() == null) {
            return null;
        }
        
        String type = context.getJavaClientGeneratorConfiguration()
                .getConfigurationType();

        AbstractJavaClientGenerator javaGenerator;
        if ("XMLMAPPER".equalsIgnoreCase(type)) { //$NON-NLS-1$
            javaGenerator = new JavaMapperGenerator();
        } else if ("MIXEDMAPPER".equalsIgnoreCase(type)) { //$NON-NLS-1$
            javaGenerator = new MixedClientGenerator();
        } else if ("ANNOTATEDMAPPER".equalsIgnoreCase(type)) { //$NON-NLS-1$
            javaGenerator = new AnnotatedClientGenerator();
        } else if ("MAPPER".equalsIgnoreCase(type)) { //$NON-NLS-1$
            javaGenerator = new JavaMapperGenerator();
        } else {
            javaGenerator = (AbstractJavaClientGenerator) ObjectFactory
                    .createInternalObject(type);
        }
        
        return javaGenerator;
    }

    /**
     * Calculate java model generators.
     *
     * @param warnings
     *            the warnings
     * @param progressCallback
     *            the progress callback
     */
    protected void calculateJavaModelGenerators(List<String> warnings,
            ProgressCallback progressCallback) {
        if (getRules().generateExampleClass()) {
            AbstractJavaGenerator javaGenerator = new ExampleGenerator();
            initializeAbstractGenerator(javaGenerator, warnings,
                    progressCallback);
            javaModelGenerators.add(javaGenerator);
        }

        if (getRules().generatePrimaryKeyClass()) {
            AbstractJavaGenerator javaGenerator = new PrimaryKeyGenerator();
            initializeAbstractGenerator(javaGenerator, warnings,
                    progressCallback);
            javaModelGenerators.add(javaGenerator);
        }

        if (getRules().generateBaseRecordClass()) {
            AbstractJavaGenerator javaGenerator = new BaseRecordGenerator();
            initializeAbstractGenerator(javaGenerator, warnings,
                    progressCallback);
            javaModelGenerators.add(javaGenerator);
        }

        if (getRules().generateRecordWithBLOBsClass()) {
            AbstractJavaGenerator javaGenerator = new RecordWithBLOBsGenerator();
            initializeAbstractGenerator(javaGenerator, warnings,
                    progressCallback);
            javaModelGenerators.add(javaGenerator);
        }
    }

    /**
     * Initialize abstract generator.
     *
     * @param abstractGenerator
     *            the abstract generator
     * @param warnings
     *            the warnings
     * @param progressCallback
     *            the progress callback
     */
    protected void initializeAbstractGenerator(
            AbstractGenerator abstractGenerator, List<String> warnings,
            ProgressCallback progressCallback) {
        if (abstractGenerator == null) {
            return;
        }
        
        abstractGenerator.setContext(context);
        abstractGenerator.setIntrospectedTable(this);
        abstractGenerator.setProgressCallback(progressCallback);
        abstractGenerator.setWarnings(warnings);
    }

    /* (non-Javadoc)
     * @see com.xyz.generator.api.IntrospectedTable#getGeneratedJavaFiles()
     */
    @Override
    public List<GeneratedJavaFile> getGeneratedJavaFiles() {
        List<GeneratedJavaFile> answer = new ArrayList<GeneratedJavaFile>();

        for (AbstractJavaGenerator javaGenerator : javaModelGenerators) {
            List<CompilationUnit> compilationUnits = javaGenerator
                    .getCompilationUnits();
            for (CompilationUnit compilationUnit : compilationUnits) {
                GeneratedJavaFile gjf = new GeneratedJavaFile(compilationUnit,
                        context.getJavaModelGeneratorConfiguration()
                                .getTargetProject(),
                                context.getProperty(PropertyRegistry.CONTEXT_JAVA_FILE_ENCODING),
                                context.getJavaFormatter());
                answer.add(gjf);
            }
        }

        for (AbstractJavaGenerator javaGenerator : clientGenerators) {
            List<CompilationUnit> compilationUnits = javaGenerator
                    .getCompilationUnits();
            for (CompilationUnit compilationUnit : compilationUnits) {
                GeneratedJavaFile gjf = new GeneratedJavaFile(compilationUnit,
                        context.getJavaClientGeneratorConfiguration()
                                .getTargetProject(),
                                context.getProperty(PropertyRegistry.CONTEXT_JAVA_FILE_ENCODING),
                                context.getJavaFormatter());
                answer.add(gjf);
            }
        }

        for (GeneratedJavaFile file : answer){
            logger.info(file.getFormattedContent());
        }

        return answer;
    }

    /* (non-Javadoc)
     * @see com.xyz.generator.api.IntrospectedTable#getGeneratedXmlFiles()
     */
    @Override
    public List<GeneratedXmlFile> getGeneratedXmlFiles() {
        List<GeneratedXmlFile> answer = new ArrayList<GeneratedXmlFile>();

        if (xmlMapperGenerator != null) {
            Document document = xmlMapperGenerator.getDocument();
            GeneratedXmlFile gxf = new GeneratedXmlFile(document,
                getMyBatis3XmlMapperFileName(), getMyBatis3XmlMapperPackage(),
                context.getSqlMapGeneratorConfiguration().getTargetProject(),
                true, context.getXmlFormatter());
            if (context.getPlugins().sqlMapGenerated(gxf, this)) {
                answer.add(gxf);
            }
        }

        for (GeneratedXmlFile file : answer){
            logger.info(file.getFormattedContent());
        }


        return answer;
    }

    /* (non-Javadoc)
     * @see com.xyz.generator.api.IntrospectedTable#getGenerationSteps()
     */
    @Override
    public int getGenerationSteps() {
        return javaModelGenerators.size() + clientGenerators.size() +
            (xmlMapperGenerator == null ? 0 : 1);
    }

    /* (non-Javadoc)
     * @see com.xyz.generator.api.IntrospectedTable#isJava5Targeted()
     */
    @Override
    public boolean isJava5Targeted() {
        return true;
    }

    /* (non-Javadoc)
     * @see com.xyz.generator.api.IntrospectedTable#requiresXMLGenerator()
     */
    @Override
    public boolean requiresXMLGenerator() {
        AbstractJavaClientGenerator javaClientGenerator =
            createJavaClientGenerator();
        
        if (javaClientGenerator == null) {
            return false;
        } else {
            return javaClientGenerator.requiresXMLGenerator();
        }
    }

    public static void main(String[] args) {
        StringBuffer sb = new StringBuffer();
        for (int i =0;i<100;i++){
            sb.append(i).append("\n\t");
        }
        System.out.println(sb.toString());
    }
}

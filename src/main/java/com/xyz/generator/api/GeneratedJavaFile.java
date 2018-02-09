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


import com.xyz.generator.api.dom.java.CompilationUnit;

/**
 * 生成Java类文件
 */
public class GeneratedJavaFile extends GeneratedFile {
    
    private CompilationUnit compilationUnit;
    
    private String fileEncoding;
    
    private JavaFormatter javaFormatter;

    public GeneratedJavaFile(CompilationUnit compilationUnit,
                             String targetProject,
                             String fileEncoding,
                             JavaFormatter javaFormatter) {
        super(targetProject);
        this.compilationUnit = compilationUnit;
        this.fileEncoding = fileEncoding;
        this.javaFormatter = javaFormatter;
    }

    public GeneratedJavaFile(CompilationUnit compilationUnit,
                             String targetProject,
                             JavaFormatter javaFormatter) {
        this(compilationUnit, targetProject, null, javaFormatter);
    }
    
    @Override
    public String getFormattedContent() {
        return javaFormatter.getFormattedContent(compilationUnit);
    }

    @Override
    public String getFileName() {
        return compilationUnit.getType().getShortName() + ".java"; //$NON-NLS-1$
    }

    public String getTargetPackage() {
        return compilationUnit.getType().getPackageName();
    }

    public CompilationUnit getCompilationUnit() {
        return compilationUnit;
    }

    @Override
    public boolean isMergeable() {
        return true;
    }

    public String getFileEncoding() {
        return fileEncoding;
    }
}

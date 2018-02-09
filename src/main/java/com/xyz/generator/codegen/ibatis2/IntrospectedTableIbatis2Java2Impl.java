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
package com.xyz.generator.codegen.ibatis2;

import com.xyz.generator.api.GeneratedJavaFile;
import com.xyz.generator.api.GeneratedXmlFile;
import com.xyz.generator.api.IntrospectedTable;
import com.xyz.generator.api.ProgressCallback;
import com.xyz.generator.api.dom.java.CompilationUnit;
import com.xyz.generator.api.dom.xml.Document;
import com.xyz.generator.codegen.AbstractGenerator;
import com.xyz.generator.codegen.AbstractJavaGenerator;
import com.xyz.generator.codegen.AbstractXmlGenerator;
import com.xyz.generator.codegen.ibatis2.dao.DAOGenerator;
import com.xyz.generator.codegen.ibatis2.dao.templates.GenericCIDAOTemplate;
import com.xyz.generator.codegen.ibatis2.dao.templates.GenericSIDAOTemplate;
import com.xyz.generator.codegen.ibatis2.dao.templates.IbatisDAOTemplate;
import com.xyz.generator.codegen.ibatis2.dao.templates.SpringDAOTemplate;
import com.xyz.generator.codegen.ibatis2.model.BaseRecordGenerator;
import com.xyz.generator.codegen.ibatis2.model.ExampleGenerator;
import com.xyz.generator.codegen.ibatis2.model.RecordWithBLOBsGenerator;
import com.xyz.generator.codegen.ibatis2.sqlmap.SqlMapGenerator;
import com.xyz.generator.codegen.mybatis3.model.PrimaryKeyGenerator;
import com.xyz.generator.config.PropertyRegistry;
import com.xyz.generator.internal.ObjectFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Jeff Butler
 * 
 */
public class IntrospectedTableIbatis2Java2Impl extends IntrospectedTable {
    protected List<AbstractJavaGenerator> javaModelGenerators;
    protected List<AbstractJavaGenerator> daoGenerators;
    protected AbstractXmlGenerator sqlMapGenerator;

    public IntrospectedTableIbatis2Java2Impl() {
        super(TargetRuntime.IBATIS2);
        javaModelGenerators = new ArrayList<AbstractJavaGenerator>();
        daoGenerators = new ArrayList<AbstractJavaGenerator>();
    }

    @Override
    public void calculateGenerators(List<String> warnings,
            ProgressCallback progressCallback) {
        calculateJavaModelGenerators(warnings, progressCallback);
        calculateDAOGenerators(warnings, progressCallback);
        calculateSqlMapGenerator(warnings, progressCallback);
    }

    protected void calculateSqlMapGenerator(List<String> warnings,
            ProgressCallback progressCallback) {
        sqlMapGenerator = new SqlMapGenerator();
        initializeAbstractGenerator(sqlMapGenerator, warnings, progressCallback);
    }

    protected void calculateDAOGenerators(List<String> warnings,
            ProgressCallback progressCallback) {
        if (context.getJavaClientGeneratorConfiguration() == null) {
            return;
        }

        String type = context.getJavaClientGeneratorConfiguration()
                .getConfigurationType();

        AbstractJavaGenerator javaGenerator;
        if ("IBATIS".equalsIgnoreCase(type)) { //$NON-NLS-1$
            javaGenerator = new DAOGenerator(new IbatisDAOTemplate(),
                    isJava5Targeted());
        } else if ("SPRING".equalsIgnoreCase(type)) { //$NON-NLS-1$
            javaGenerator = new DAOGenerator(new SpringDAOTemplate(),
                    isJava5Targeted());
        } else if ("GENERIC-CI".equalsIgnoreCase(type)) { //$NON-NLS-1$
            javaGenerator = new DAOGenerator(new GenericCIDAOTemplate(),
                    isJava5Targeted());
        } else if ("GENERIC-SI".equalsIgnoreCase(type)) { //$NON-NLS-1$
            javaGenerator = new DAOGenerator(new GenericSIDAOTemplate(),
                    isJava5Targeted());
        } else {
            javaGenerator = (AbstractJavaGenerator) ObjectFactory
                    .createInternalObject(type);
        }

        initializeAbstractGenerator(javaGenerator, warnings, progressCallback);
        daoGenerators.add(javaGenerator);
    }

    protected void calculateJavaModelGenerators(List<String> warnings,
            ProgressCallback progressCallback) {
        if (getRules().generateExampleClass()) {
            AbstractJavaGenerator javaGenerator = new ExampleGenerator(
                    isJava5Targeted());
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

    protected void initializeAbstractGenerator(
            AbstractGenerator abstractGenerator, List<String> warnings,
            ProgressCallback progressCallback) {
        abstractGenerator.setContext(context);
        abstractGenerator.setIntrospectedTable(this);
        abstractGenerator.setProgressCallback(progressCallback);
        abstractGenerator.setWarnings(warnings);
    }

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

        for (AbstractJavaGenerator javaGenerator : daoGenerators) {
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

        return answer;
    }

    @Override
    public List<GeneratedXmlFile> getGeneratedXmlFiles() {
        List<GeneratedXmlFile> answer = new ArrayList<GeneratedXmlFile>();

        Document document = sqlMapGenerator.getDocument();
        GeneratedXmlFile gxf = new GeneratedXmlFile(document,
                getIbatis2SqlMapFileName(), getIbatis2SqlMapPackage(), context
                        .getSqlMapGeneratorConfiguration().getTargetProject(),
                true, context.getXmlFormatter());
        if (context.getPlugins().sqlMapGenerated(gxf, this)) {
            answer.add(gxf);
        }

        return answer;
    }

    @Override
    public boolean isJava5Targeted() {
        return false;
    }

    @Override
    public int getGenerationSteps() {
        return javaModelGenerators.size() + daoGenerators.size() + 1; // 1 for
                                                                      // the
                                                                      // sqlMapGenerator
    }

    @Override
    public boolean requiresXMLGenerator() {
        return true;
    }
}
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


import com.xyz.generator.api.dom.java.*;
import com.xyz.generator.api.dom.xml.XmlElement;

import java.util.Properties;

/**
 * 该接口的实现用于生成注释不同的工件。
 */
public interface CommentGenerator {


    void addConfigurationProperties(Properties properties);

    public void addFieldComment(Field field,
                                IntrospectedTable introspectedTable,
                                IntrospectedColumn introspectedColumn);

    public void addFieldComment(Field field, IntrospectedTable introspectedTable);

    public void addModelClassComment(TopLevelClass topLevelClass,
                                     IntrospectedTable introspectedTable);

    public void addClassComment(InnerClass innerClass,
                                IntrospectedTable introspectedTable);
    public void addClassComment(InnerClass innerClass,
                                IntrospectedTable introspectedTable, boolean markAsDoNotDelete);

    public void addEnumComment(InnerEnum innerEnum,
                               IntrospectedTable introspectedTable);

    public void addGetterComment(Method method,
                                 IntrospectedTable introspectedTable,
                                 IntrospectedColumn introspectedColumn);

    public void addSetterComment(Method method,
                                 IntrospectedTable introspectedTable,
                                 IntrospectedColumn introspectedColumn);

    public void addGeneralMethodComment(Method method,
                                        IntrospectedTable introspectedTable);

    public void addJavaFileComment(CompilationUnit compilationUnit);

    public void addComment(XmlElement xmlElement);

    public void addRootComment(XmlElement rootElement);
}

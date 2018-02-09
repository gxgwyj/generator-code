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
package com.xyz.generator.codegen.ibatis2.sqlmap.elements;


import com.xyz.generator.api.dom.xml.Attribute;
import com.xyz.generator.api.dom.xml.TextElement;
import com.xyz.generator.api.dom.xml.XmlElement;

/**
 * 
 * @author Jeff Butler
 * 
 */
public class DeleteByExampleElementGenerator extends
        AbstractXmlElementGenerator {

    public DeleteByExampleElementGenerator() {
        super();
    }

    @Override
    public void addElements(XmlElement parentElement) {
        XmlElement answer = new XmlElement("delete"); //$NON-NLS-1$

        answer.addAttribute(new Attribute(
                "id", introspectedTable.getDeleteByExampleStatementId())); //$NON-NLS-1$
        answer.addAttribute(new Attribute(
                "parameterClass", introspectedTable.getExampleType())); //$NON-NLS-1$

        context.getCommentGenerator().addComment(answer);

        StringBuilder sb = new StringBuilder();
        sb.append("delete from "); //$NON-NLS-1$
        sb.append(introspectedTable
                .getAliasedFullyQualifiedTableNameAtRuntime());
        answer.addElement(new TextElement(sb.toString()));

        XmlElement includeElement = new XmlElement("include"); //$NON-NLS-1$
        sb.setLength(0);
        sb.append(introspectedTable.getIbatis2SqlMapNamespace());
        sb.append('.');
        sb.append(introspectedTable.getExampleWhereClauseId());
        includeElement.addAttribute(new Attribute("refid", //$NON-NLS-1$
                sb.toString()));

        answer.addElement(includeElement);

        if (context.getPlugins().sqlMapDeleteByExampleElementGenerated(
                answer, introspectedTable)) {
            parentElement.addElement(answer);
        }
    }
}

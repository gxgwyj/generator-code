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
package com.xyz.generator.codegen.mybatis3.javamapper.elements.annotated;

import com.xyz.generator.api.IntrospectedColumn;
import com.xyz.generator.api.dom.java.FullyQualifiedJavaType;
import com.xyz.generator.api.dom.java.Interface;
import com.xyz.generator.api.dom.java.Method;
import com.xyz.generator.codegen.mybatis3.javamapper.elements.UpdateByPrimaryKeyWithoutBLOBsMethodGenerator;

import java.util.Iterator;

import static com.xyz.generator.api.dom.OutputUtilities.javaIndent;
import static com.xyz.generator.codegen.mybatis3.MyBatis3FormattingUtilities.getEscapedColumnName;
import static com.xyz.generator.codegen.mybatis3.MyBatis3FormattingUtilities.getParameterClause;
import static com.xyz.generator.internal.util.StringUtility.escapeStringForJava;

/**
 * 
 * @author Jeff Butler
 */
public class AnnotatedUpdateByPrimaryKeyWithoutBLOBsMethodGenerator extends
    UpdateByPrimaryKeyWithoutBLOBsMethodGenerator {

    private boolean isSimple;
    
    public AnnotatedUpdateByPrimaryKeyWithoutBLOBsMethodGenerator(boolean isSimple) {
        super();
        this.isSimple = isSimple;
    }

    @Override
    public void addMapperAnnotations(Interface interfaze, Method method) {
        interfaze.addImportedType(new FullyQualifiedJavaType("org.apache.ibatis.annotations.Update")); //$NON-NLS-1$
        
        method.addAnnotation("@Update({"); //$NON-NLS-1$

        StringBuilder sb = new StringBuilder();
        javaIndent(sb, 1);
        sb.append("\"update "); //$NON-NLS-1$
        sb.append(escapeStringForJava(introspectedTable.getFullyQualifiedTableNameAtRuntime()));
        sb.append("\","); //$NON-NLS-1$
        method.addAnnotation(sb.toString());

        // set up for first column
        sb.setLength(0);
        javaIndent(sb, 1);
        sb.append("\"set "); //$NON-NLS-1$

        Iterator<IntrospectedColumn> iter;
        if (isSimple) {
            iter = introspectedTable.getNonPrimaryKeyColumns().iterator();
        } else {
            iter = introspectedTable.getBaseColumns().iterator();
        }
        while (iter.hasNext()) {
            IntrospectedColumn introspectedColumn = iter.next();

            sb.append(escapeStringForJava(getEscapedColumnName(introspectedColumn)));
            sb.append(" = "); //$NON-NLS-1$
            sb.append(getParameterClause(introspectedColumn));

            if (iter.hasNext()) {
                sb.append(',');
            }

            sb.append("\","); //$NON-NLS-1$
            method.addAnnotation(sb.toString());

            // set up for the next column
            if (iter.hasNext()) {
                sb.setLength(0);
                javaIndent(sb, 1);
                sb.append("  \""); //$NON-NLS-1$
            }
        }

        boolean and = false;
        iter = introspectedTable.getPrimaryKeyColumns().iterator();
        while (iter.hasNext()) {
            IntrospectedColumn introspectedColumn = iter.next();
            sb.setLength(0);
            javaIndent(sb, 1);
            if (and) {
                sb.append("  \"and "); //$NON-NLS-1$
            } else {
                sb.append("\"where "); //$NON-NLS-1$
                and = true;
            }

            sb.append(escapeStringForJava(getEscapedColumnName(introspectedColumn)));
            sb.append(" = "); //$NON-NLS-1$
            sb.append(getParameterClause(introspectedColumn));
            sb.append('\"');
            if (iter.hasNext()) {
                sb.append(',');
            }
            method.addAnnotation(sb.toString());
        }
        
        method.addAnnotation("})"); //$NON-NLS-1$
    }
}

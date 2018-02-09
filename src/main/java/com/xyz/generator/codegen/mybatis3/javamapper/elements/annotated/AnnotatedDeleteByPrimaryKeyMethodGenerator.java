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
import com.xyz.generator.api.dom.OutputUtilities;
import com.xyz.generator.api.dom.java.FullyQualifiedJavaType;
import com.xyz.generator.api.dom.java.Interface;
import com.xyz.generator.api.dom.java.Method;
import com.xyz.generator.codegen.mybatis3.MyBatis3FormattingUtilities;
import com.xyz.generator.codegen.mybatis3.javamapper.elements.DeleteByPrimaryKeyMethodGenerator;
import com.xyz.generator.internal.util.StringUtility;

import java.util.Iterator;


/**
 * 
 * @author Jeff Butler
 */
public class AnnotatedDeleteByPrimaryKeyMethodGenerator extends
        DeleteByPrimaryKeyMethodGenerator {

    public AnnotatedDeleteByPrimaryKeyMethodGenerator(boolean isSimple) {
        super(isSimple);
    }

    @Override
    public void addMapperAnnotations(Interface interfaze, Method method) {
        interfaze.addImportedType(new FullyQualifiedJavaType("org.apache.ibatis.annotations.Delete")); //$NON-NLS-1$
        
        method.addAnnotation("@Delete({"); //$NON-NLS-1$
        
        StringBuilder sb = new StringBuilder();
        OutputUtilities.javaIndent(sb, 1);
        sb.append("\"delete from " ); //$NON-NLS-1$
        sb.append(StringUtility.escapeStringForJava(
                introspectedTable.getFullyQualifiedTableNameAtRuntime()));
        sb.append("\","); //$NON-NLS-1$
        method.addAnnotation(sb.toString());
        
        boolean and = false;
        Iterator<IntrospectedColumn> iter = introspectedTable.getPrimaryKeyColumns().iterator();
        while (iter.hasNext()) {
            IntrospectedColumn introspectedColumn = iter.next();
            sb.setLength(0);
            OutputUtilities.javaIndent(sb, 1);
            if (and) {
                sb.append("  \"and "); //$NON-NLS-1$
            } else {
                sb.append("\"where "); //$NON-NLS-1$
                and = true;
            }

            sb.append(StringUtility.escapeStringForJava(
                    MyBatis3FormattingUtilities.getEscapedColumnName(introspectedColumn)));
            sb.append(" = "); //$NON-NLS-1$
            sb.append(MyBatis3FormattingUtilities.getParameterClause(introspectedColumn));
            sb.append('\"');
            if (iter.hasNext()) {
                sb.append(',');
            }
            
            method.addAnnotation(sb.toString());
        }
        
        method.addAnnotation("})"); //$NON-NLS-1$
    }
}

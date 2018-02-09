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
package com.xyz.generator.codegen.mybatis3.javamapper.elements;


import com.xyz.generator.api.IntrospectedColumn;
import com.xyz.generator.api.dom.java.FullyQualifiedJavaType;
import com.xyz.generator.api.dom.java.Interface;
import com.xyz.generator.api.dom.java.Method;
import com.xyz.generator.codegen.AbstractGenerator;
import com.xyz.generator.codegen.mybatis3.MyBatis3FormattingUtilities;
import com.xyz.generator.config.GeneratedKey;
import com.xyz.generator.internal.util.StringUtility;


/**
 * 
 * @author Jeff Butler
 */
public abstract class AbstractJavaMapperMethodGenerator extends
        AbstractGenerator {
    public abstract void addInterfaceElements(Interface interfaze);

    public AbstractJavaMapperMethodGenerator() {
        super();
    }
    
    protected String getResultAnnotation(Interface interfaze, IntrospectedColumn introspectedColumn,
            boolean idColumn, boolean constructorBased) {
        StringBuilder sb = new StringBuilder();
        if (constructorBased) {
            interfaze.addImportedType(introspectedColumn.getFullyQualifiedJavaType());
            sb.append("@Arg(column=\""); //$NON-NLS-1$
            sb.append(MyBatis3FormattingUtilities.getRenamedColumnNameForResultMap(introspectedColumn));
            sb.append("\", javaType="); //$NON-NLS-1$
            sb.append(introspectedColumn.getFullyQualifiedJavaType().getShortName());
            sb.append(".class"); //$NON-NLS-1$
        } else {
            sb.append("@Result(column=\""); //$NON-NLS-1$
            sb.append(MyBatis3FormattingUtilities.getRenamedColumnNameForResultMap(introspectedColumn));
            sb.append("\", property=\""); //$NON-NLS-1$
            sb.append(introspectedColumn.getJavaProperty());
            sb.append('\"');
        }
        
        if (StringUtility.stringHasValue(introspectedColumn.getTypeHandler())) {
            FullyQualifiedJavaType fqjt =
                new FullyQualifiedJavaType(introspectedColumn.getTypeHandler());
            interfaze.addImportedType(fqjt);
            sb.append(", typeHandler="); //$NON-NLS-1$
            sb.append(fqjt.getShortName());
            sb.append(".class"); //$NON-NLS-1$
        }
        
        sb.append(", jdbcType=JdbcType."); //$NON-NLS-1$
        sb.append(introspectedColumn.getJdbcTypeName());
        if (idColumn) {
            sb.append(", id=true"); //$NON-NLS-1$
        }
        sb.append(')');

        return sb.toString();
    }

    protected void addGeneratedKeyAnnotation(Interface interfaze, Method method,
            GeneratedKey gk) {
        StringBuilder sb = new StringBuilder();
        IntrospectedColumn introspectedColumn = introspectedTable.getColumn(gk.getColumn());
        if (introspectedColumn != null) {
            if (gk.isJdbcStandard()) {
                interfaze.addImportedType(new FullyQualifiedJavaType("org.apache.ibatis.annotations.Options")); //$NON-NLS-1$
                sb.append("@Options(useGeneratedKeys=true,keyProperty=\""); //$NON-NLS-1$
                sb.append(introspectedColumn.getJavaProperty());
                sb.append("\")"); //$NON-NLS-1$
                method.addAnnotation(sb.toString());
            } else {
                interfaze.addImportedType(new FullyQualifiedJavaType("org.apache.ibatis.annotations.SelectKey")); //$NON-NLS-1$
                FullyQualifiedJavaType fqjt = introspectedColumn.getFullyQualifiedJavaType();
                interfaze.addImportedType(fqjt);
                sb.append("@SelectKey(statement=\""); //$NON-NLS-1$
                sb.append(gk.getRuntimeSqlStatement());
                sb.append("\", keyProperty=\""); //$NON-NLS-1$
                sb.append(introspectedColumn.getJavaProperty());
                sb.append("\", before="); //$NON-NLS-1$
                sb.append(gk.isIdentity() ? "false" : "true"); //$NON-NLS-1$ //$NON-NLS-2$
                sb.append(", resultType="); //$NON-NLS-1$
                sb.append(fqjt.getShortName());
                sb.append(".class)"); //$NON-NLS-1$
                method.addAnnotation(sb.toString());
            }
        }
    }
}
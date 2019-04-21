package com.xyz.generator.codegen.mybatis3.xmlmapper.elements;

import com.xyz.generator.api.IntrospectedColumn;
import com.xyz.generator.api.dom.java.FullyQualifiedJavaType;
import com.xyz.generator.api.dom.xml.Attribute;
import com.xyz.generator.api.dom.xml.TextElement;
import com.xyz.generator.api.dom.xml.XmlElement;
import com.xyz.generator.codegen.mybatis3.MyBatis3FormattingUtilities;
import com.xyz.generator.config.GeneratedKey;

/**
 * 类: InsertBatchElementGenerator <br>
 * 描述: ${DESCRIPTION}<br>
 * 作者: gaoxugang <br>
 * 时间: 2018年02月08日 17:56
 */
public class InsertBatchElementGenerator extends
        AbstractXmlElementGenerator {


    public InsertBatchElementGenerator() {
        super();
    }

    @Override
    public void addElements(XmlElement parentElement) {
        XmlElement answer = new XmlElement("insert");

        answer.addAttribute(new Attribute("id", introspectedTable.getInserBatchStatementId()));

        FullyQualifiedJavaType parameterType = FullyQualifiedJavaType.getNewListInstance();

        answer.addAttribute(new Attribute("parameterType", parameterType.getFullyQualifiedName()));

        context.getCommentGenerator().addComment(answer);

        // 批量插入首句
        answer.addElement(new TextElement("insert all"));

        XmlElement foreachElement = new XmlElement("foreach");
        foreachElement.addAttribute(new Attribute("collection", "list"));
        foreachElement.addAttribute(new Attribute("item", "item"));
        answer.addElement(foreachElement);

        GeneratedKey gk = introspectedTable.getGeneratedKey();
        if (gk != null) {
            IntrospectedColumn introspectedColumn = introspectedTable
                    .getColumn(gk.getColumn());
            // if the column is null, then it's a configuration error. The
            // warning has already been reported
            if (introspectedColumn != null) {
                if (gk.isJdbcStandard()) {
                    answer.addAttribute(new Attribute("useGeneratedKeys", "true"));  //$NON-NLS-2$
                    answer.addAttribute(new Attribute("keyProperty", introspectedColumn.getJavaProperty())); 
                } else {
                    answer.addElement(getSelectKey(introspectedColumn, gk));
                }
            }
        }


        StringBuilder sb = new StringBuilder();
        sb.append("insert into ");
        sb.append(introspectedTable.getFullyQualifiedTableNameAtRuntime());
        foreachElement.addElement(new TextElement(sb.toString()));

        XmlElement insertTrimElement = new XmlElement("trim"); 
        insertTrimElement.addAttribute(new Attribute("prefix", "("));
        insertTrimElement.addAttribute(new Attribute("suffix", ")"));
        insertTrimElement.addAttribute(new Attribute("suffixOverrides", ","));
        foreachElement.addElement(insertTrimElement);

        XmlElement valuesTrimElement = new XmlElement("trim"); 
        valuesTrimElement.addAttribute(new Attribute("prefix", "values ("));
        valuesTrimElement.addAttribute(new Attribute("suffix", ")"));
        valuesTrimElement.addAttribute(new Attribute("suffixOverrides", ","));
        foreachElement.addElement(valuesTrimElement);

        for (IntrospectedColumn introspectedColumn : introspectedTable
                .getAllColumns()) {
            if (introspectedColumn.isIdentity()) {
                continue;
            }

            if (introspectedColumn.isSequenceColumn()
                    || introspectedColumn.getFullyQualifiedJavaType().isPrimitive()) {

                // if it is primitive, we cannot do a null check
                sb.setLength(0);
                sb.append(MyBatis3FormattingUtilities
                        .getEscapedColumnName(introspectedColumn));
                sb.append(',');
                insertTrimElement.addElement(new TextElement(sb.toString()));

                sb.setLength(0);
                sb.append(MyBatis3FormattingUtilities
                        .getParameterClause(introspectedColumn));
                sb.append(',');
                valuesTrimElement.addElement(new TextElement(sb.toString()));

                continue;
            }

            XmlElement insertNotNullElement = new XmlElement("if"); 
            sb.setLength(0);
            sb.append(introspectedColumn.getJavaProperty());
            sb.append(" != null"); 
            insertNotNullElement.addAttribute(new Attribute(
                    "test", sb.toString())); 

            sb.setLength(0);
            sb.append(MyBatis3FormattingUtilities
                    .getEscapedColumnName(introspectedColumn));
            sb.append(',');
            insertNotNullElement.addElement(new TextElement(sb.toString()));
            insertTrimElement.addElement(insertNotNullElement);

            XmlElement valuesNotNullElement = new XmlElement("if"); 
            sb.setLength(0);
            sb.append(introspectedColumn.getJavaProperty());
            sb.append(" != null"); 
            valuesNotNullElement.addAttribute(new Attribute(
                    "test", sb.toString())); 

            sb.setLength(0);
            sb.append(MyBatis3FormattingUtilities
                    .getParameterClause(introspectedColumn));
            sb.append(',');
            valuesNotNullElement.addElement(new TextElement(sb.toString()));
            valuesTrimElement.addElement(valuesNotNullElement);
        }
        foreachElement.addElement(new TextElement("SELECT 1 FROM DUAL"));
        if (context.getPlugins()
                .sqlMapInsertBatchElementGenerated(answer,
                        introspectedTable)) {
            parentElement.addElement(answer);
        }


    }
}

package com.xyz.generator.codegen.mybatis3.xmlmapper.elements;

import com.xyz.generator.api.IntrospectedColumn;
import com.xyz.generator.api.dom.java.FullyQualifiedJavaType;
import com.xyz.generator.api.dom.xml.Attribute;
import com.xyz.generator.api.dom.xml.TextElement;
import com.xyz.generator.api.dom.xml.XmlElement;
import com.xyz.generator.codegen.mybatis3.MyBatis3FormattingUtilities;

/**
 * 类: SelectByWhereElementGenerator <br>
 * 描述: ${DESCRIPTION}<br>
 * 作者: gaoxugang <br>
 * 时间: 2018年02月08日 17:56
 */
public class SelectByWhereElementGenerator extends
        AbstractXmlElementGenerator{


    public SelectByWhereElementGenerator() {
        super();
    }

    @Override
    public void addElements(XmlElement parentElement) {
        XmlElement answer = new XmlElement("select");

        FullyQualifiedJavaType parameterType = new FullyQualifiedJavaType(introspectedTable.getBaseRecordType());

        answer.addAttribute(new Attribute("id", introspectedTable.getSelectByWhereStatementId()));
        answer.addAttribute(new Attribute("resultMap",introspectedTable.getBaseResultMapId()));
        answer.addAttribute(new Attribute("parameterType", parameterType.getFullyQualifiedName()));


        StringBuilder sb = new StringBuilder();

        sb.append("select ");

        answer.addElement(new TextElement(sb.toString()));
        answer.addElement(getBaseColumnListElement());


        sb.setLength(0);
        sb.append("from "); //$NON-NLS-1$
        sb.append(introspectedTable
                .getAliasedFullyQualifiedTableNameAtRuntime());
        answer.addElement(new TextElement(sb.toString()));


        //添加
        XmlElement whereTrimElement = new XmlElement("trim");
        whereTrimElement.addAttribute(new Attribute("prefix", "where"));
        whereTrimElement.addAttribute(new Attribute("prefixOverrides", "and|or"));



        for (IntrospectedColumn introspectedColumn : introspectedTable.getAllColumns()){

            XmlElement ifElement = new XmlElement("if"); //$NON-NLS-1$
            sb.setLength(0);
            sb.append(introspectedColumn.getJavaProperty()).append(" != null");
            ifElement.addAttribute(new Attribute("test", sb.toString()));

            sb.setLength(0);
            sb.append(" and ");
            sb.append(MyBatis3FormattingUtilities.getEscapedColumnName(introspectedColumn));
            sb.append(" = ");
            sb.append(MyBatis3FormattingUtilities.getParameterClause(introspectedColumn));

            ifElement.addElement(new TextElement(sb.toString()));
            whereTrimElement.addElement(ifElement);

        }


        answer.addElement(whereTrimElement);


        if (context.getPlugins()
                .sqlMapSelectByWhereElementGenerated(answer,
                        introspectedTable)) {
            parentElement.addElement(answer);
        }



    }
}

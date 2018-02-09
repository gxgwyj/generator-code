package com.xyz.generator.config;

import com.xyz.generator.api.dom.xml.Attribute;
import com.xyz.generator.api.dom.xml.XmlElement;
import com.xyz.generator.internal.util.StringUtility;
import com.xyz.generator.internal.util.messages.Messages;

import java.util.List;

/**
 * 类: ColumnRenamingRule <br>
 * 描述: ${DESCRIPTION}<br>
 * 作者: gaoxugang <br>
 * 时间: 2018年02月05日 11:02
 */
public class ColumnRenamingRule {
    private String searchString;
    private String replaceString;

    public String getReplaceString() {
        return replaceString;
    }

    public void setReplaceString(String replaceString) {
        this.replaceString = replaceString;
    }

    public String getSearchString() {
        return searchString;
    }

    public void setSearchString(String searchString) {
        this.searchString = searchString;
    }

    public void validate(List<String> errors, String tableName) {
        if (!StringUtility.stringHasValue(searchString)) {
            errors.add(Messages.getString("ValidationError.14", tableName)); //$NON-NLS-1$
        }
    }

    public XmlElement toXmlElement() {
        XmlElement xmlElement = new XmlElement("columnRenamingRule"); //$NON-NLS-1$
        xmlElement.addAttribute(new Attribute("searchString", searchString)); //$NON-NLS-1$

        if (replaceString != null) {
            xmlElement.addAttribute(new Attribute(
                    "replaceString", replaceString)); //$NON-NLS-1$
        }

        return xmlElement;
    }
}

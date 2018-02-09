package com.xyz.generator.config;

import com.xyz.generator.api.dom.xml.Attribute;
import com.xyz.generator.api.dom.xml.XmlElement;
import com.xyz.generator.internal.util.StringUtility;
import com.xyz.generator.internal.util.messages.Messages;

import java.util.List;

/**
 * 类: IgnoredColumn <br>
 * 描述: ${DESCRIPTION}<br>
 * 作者: gaoxugang <br>
 * 时间: 2018年02月05日 11:18
 */
public class IgnoredColumn {

    private String columnName;
    private boolean isColumnNameDelimited;
    private String configuredDelimitedColumnName;

    public IgnoredColumn(String columnName) {
        super();
        this.columnName = columnName;
        isColumnNameDelimited = StringUtility.stringContainsSpace(columnName);
    }

    public String getColumnName() {
        return columnName;
    }

    public boolean isColumnNameDelimited() {
        return isColumnNameDelimited;
    }

    public void setColumnNameDelimited(boolean isColumnNameDelimited) {
        this.isColumnNameDelimited = isColumnNameDelimited;
        configuredDelimitedColumnName = isColumnNameDelimited ? "true" : "false"; //$NON-NLS-1$ //$NON-NLS-2$
    }

    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof IgnoredColumn)) {
            return false;
        }

        return columnName.equals(((IgnoredColumn) obj).getColumnName());
    }

    public int hashCode() {
        return columnName.hashCode();
    }

    public XmlElement toXmlElement() {
        XmlElement xmlElement = new XmlElement("ignoreColumn"); //$NON-NLS-1$
        xmlElement.addAttribute(new Attribute("column", columnName)); //$NON-NLS-1$

        if (StringUtility.stringHasValue(configuredDelimitedColumnName)) {
            xmlElement.addAttribute(new Attribute(
                    "delimitedColumnName", configuredDelimitedColumnName)); //$NON-NLS-1$
        }

        return xmlElement;
    }

    public void validate(List<String> errors, String tableName) {
        if (!StringUtility.stringHasValue(columnName)) {
            errors.add(Messages.getString("ValidationError.21", //$NON-NLS-1$
                    tableName));
        }
    }
}

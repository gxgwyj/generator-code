package com.xyz.generator.config;

import com.xyz.generator.api.dom.xml.Attribute;
import com.xyz.generator.api.dom.xml.XmlElement;
import com.xyz.generator.internal.db.DatabaseDialects;
import com.xyz.generator.internal.util.StringUtility;
import com.xyz.generator.internal.util.messages.Messages;

import java.util.List;

/**
 * 类: GeneratedKey <br>
 * 描述: ${DESCRIPTION}<br>
 * 作者: gaoxugang <br>
 * 时间: 2018年02月05日 11:04
 */
public class GeneratedKey {
    private String column;

    private String configuredSqlStatement;

    private String runtimeSqlStatement;

    private boolean isIdentity;

    private String type;


    public GeneratedKey(String column, String configuredSqlStatement,
                        boolean isIdentity, String type) {
        super();
        this.column = column;
        this.type = type;
        this.isIdentity = isIdentity;
        this.configuredSqlStatement = configuredSqlStatement;

        DatabaseDialects dialect = DatabaseDialects
                .getDatabaseDialect(configuredSqlStatement);
        if (dialect == null) {
            this.runtimeSqlStatement = configuredSqlStatement;
        } else {
            this.runtimeSqlStatement = dialect.getIdentityRetrievalStatement();
        }
    }
    public String getColumn() {
        return column;
    }

    public boolean isIdentity() {
        return isIdentity;
    }

    public String getRuntimeSqlStatement() {
        return runtimeSqlStatement;
    }

    public String getType() {
        return type;
    }

    public boolean isPlacedBeforeInsertInIbatis2() {
        boolean rc;

        if (StringUtility.stringHasValue(type)) {
            rc = true;
        } else {
            rc = !isIdentity;
        }

        return rc;
    }

    public String getMyBatis3Order() {
        return isIdentity ? "AFTER" : "BEFORE"; //$NON-NLS-1$ //$NON-NLS-2$
    }


    public XmlElement toXmlElement() {
        XmlElement xmlElement = new XmlElement("generatedKey"); //$NON-NLS-1$
        xmlElement.addAttribute(new Attribute("column", column)); //$NON-NLS-1$
        xmlElement.addAttribute(new Attribute(
                "sqlStatement", configuredSqlStatement)); //$NON-NLS-1$
        if (StringUtility.stringHasValue(type)) {
            xmlElement.addAttribute(new Attribute("type", type)); //$NON-NLS-1$
        }
        xmlElement.addAttribute(new Attribute("identity", //$NON-NLS-1$
                isIdentity ? "true" : "false")); //$NON-NLS-1$ //$NON-NLS-2$

        return xmlElement;
    }

    public void validate(List<String> errors, String tableName) {
        if (!StringUtility.stringHasValue(runtimeSqlStatement)) {
            errors.add(Messages.getString("ValidationError.7", //$NON-NLS-1$
                    tableName));
        }

        if (StringUtility.stringHasValue(type)) {
            if (!"pre".equals(type) && !"post".equals(type)) { //$NON-NLS-1$ //$NON-NLS-2$
                errors.add(Messages.getString("ValidationError.15", //$NON-NLS-1$
                        tableName));
            }
        }

        if ("pre".equals(type) && isIdentity) { //$NON-NLS-1$
            errors.add(Messages.getString("ValidationError.23", //$NON-NLS-1$
                    tableName));
        }

        if ("post".equals(type) && !isIdentity) { //$NON-NLS-1$
            errors.add(Messages.getString("ValidationError.24", //$NON-NLS-1$
                    tableName));
        }
    }

    public boolean isJdbcStandard() {
        return "JDBC".equals(runtimeSqlStatement); //$NON-NLS-1$
    }
}

package com.xyz.generator.internal.util;

import com.xyz.generator.api.dom.java.FullyQualifiedJavaType;

import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeSet;

/**
 * 类: StringUtility <br>
 * 描述: 字符串工具类<br>
 * 作者: gaoxugang <br>
 * 时间: 2018年02月01日 16:35
 */
public class StringUtility {

    public static boolean stringHasValue(String s){
        return s != null && s.length() >0;
    }

    public static Set<String> calculateImports(
            Set<FullyQualifiedJavaType> importedTypes) {
        StringBuilder sb = new StringBuilder();
        Set<String> importStrings = new TreeSet<String>();
        for (FullyQualifiedJavaType fqjt : importedTypes) {
            for (String importString : fqjt.getImportList()) {
                sb.setLength(0);
                sb.append("import "); //$NON-NLS-1$
                sb.append(importString);
                sb.append(';');
                importStrings.add(sb.toString());
            }
        }

        return importStrings;
    }

    public static boolean stringContainsSpace(String s) {
        return s != null && s.indexOf(' ') != -1;
    }

    public static String composeFullyQualifiedTableName(String catalog,
                                                        String schema, String tableName, char separator) {
        StringBuilder sb = new StringBuilder();

        if (stringHasValue(catalog)) {
            sb.append(catalog);
            sb.append(separator);
        }

        if (stringHasValue(schema)) {
            sb.append(schema);
            sb.append(separator);
        }

        sb.append(tableName);

        return sb.toString();
    }

    public static boolean isTrue(String s) {
        return "true".equalsIgnoreCase(s);
    }


    public static String escapeStringForJava(String s) {
        StringTokenizer st = new StringTokenizer(s, "\"", true); //$NON-NLS-1$
        StringBuilder sb = new StringBuilder();
        while (st.hasMoreTokens()) {
            String token = st.nextToken();
            if ("\"".equals(token)) { //$NON-NLS-1$
                sb.append("\\\""); //$NON-NLS-1$
            } else {
                sb.append(token);
            }
        }

        return sb.toString();
    }


    public static boolean stringContainsSQLWildcard(String s) {
        if (s == null) {
            return false;
        }

        return s.indexOf('%') != -1 || s.indexOf('_') != -1;
    }

}

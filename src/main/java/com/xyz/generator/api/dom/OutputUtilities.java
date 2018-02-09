package com.xyz.generator.api.dom;

import com.xyz.generator.api.dom.java.FullyQualifiedJavaType;

import java.util.Set;
import java.util.TreeSet;

/**
 * 类: OutputUtilities <br>
 * 描述: 内容输出工具<br>
 * 作者: gaoxugang <br>
 * 时间: 2018年01月31日 14:35
 */
public class OutputUtilities {

    private static final String lineSeparator;

    static {
        //获取jdk中提供的换行符，屏蔽了windows和linux的区别
        String ls = System.getProperty("line.separator");
        if (ls == null){
            ls = "\n";
        }
        lineSeparator = ls;
    }

    private OutputUtilities() {
    }

    /**
     * java 的缩进
     * @param sb
     * @param indentLevel
     */
    public static void javaIndent(StringBuilder sb,int indentLevel){
        for (int i = 0; i < indentLevel; i++) {
            //4个空格
            sb.append("    ");
        }
    }

    /**
     * xml 的缩进
     * @param sb
     * @param indentLevel
     */
    public static void xmlIndent(StringBuilder sb,int indentLevel){
        for (int i = 0; i < indentLevel; i++) {
            //4个空格
            sb.append("    ");
        }
    }

    /**
     * 添加换行
     * @param sb
     */
    public static void newLine(StringBuilder sb){
        sb.append(lineSeparator);
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




}

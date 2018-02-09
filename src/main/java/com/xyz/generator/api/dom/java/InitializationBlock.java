package com.xyz.generator.api.dom.java;

import com.xyz.generator.api.dom.OutputUtilities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ListIterator;

/**
 * 类: InitializationBlock <br>
 * 描述: 初始化模块<br>
 * 作者: gaoxugang <br>
 * 时间: 2018年02月02日 10:41
 */
public class InitializationBlock {

    private boolean isStatic;
    private List<String> bodyLines;
    private List<String> javaDocLines;

    public InitializationBlock(){
        this(false);
    }
    public InitializationBlock(boolean isStatic) {
        this.isStatic = isStatic;
        bodyLines = new ArrayList<String>();
        javaDocLines = new ArrayList<String>();
    }

    public boolean isStatic() {
        return isStatic;
    }

    public void setStatic(boolean isStatic) {
        this.isStatic = isStatic;
    }

    public List<String> getBodyLines() {
        return bodyLines;
    }

    public void addBodyLine(String line){
        bodyLines.add(line);
    }

    public void addBodyLine(int index,String line){
        bodyLines.add(index,line);
    }

    public void addBodyLines(Collection<String> lines){
        bodyLines.addAll(lines);
    }

    public void addBodyLines(int index,Collection<String> lines){
        bodyLines.addAll(index,lines);
    }

    public List<String> getJavaDocLines() {
        return javaDocLines;
    }

    public void addJavaDocLine(String javaDocLine){
        javaDocLines.add(javaDocLine);
    }

    public String getFormattedContent(int indentLevel){
        StringBuilder sb = new StringBuilder();

        for (String javaDocLine : javaDocLines){
            OutputUtilities.javaIndent(sb,indentLevel);
            sb.append(javaDocLine);
            OutputUtilities.newLine(sb);
        }

        OutputUtilities.javaIndent(sb,indentLevel);

        if (isStatic()){
            sb.append("static ");
        }

        sb.append("{");
        indentLevel++;

        ListIterator<String> listIterator = bodyLines.listIterator();
        while (listIterator.hasNext()){
            String line = listIterator.next();
            if (line.startsWith("}")) {
                indentLevel--;
            }

            OutputUtilities.newLine(sb);
            OutputUtilities.javaIndent(sb,indentLevel);
            sb.append(line);

            if ((line.endsWith("{") && !line.startsWith("switch"))
                    || line.endsWith(":")){
                indentLevel ++;
            }

            if (line.startsWith("break")){
                if (listIterator.hasNext()){
                    String nextLine = listIterator.next();
                    if (nextLine.startsWith("}")){
                        indentLevel ++;
                    }
                    listIterator.previous();
                }
                indentLevel--;
            }
        }

        indentLevel--;
        OutputUtilities.newLine(sb);
        OutputUtilities.javaIndent(sb,indentLevel);
        sb.append('}');

        return sb.toString();
    }
}

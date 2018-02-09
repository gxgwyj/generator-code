package com.xyz.generator.api.dom.java;

import com.xyz.generator.api.dom.OutputUtilities;

import java.util.ArrayList;
import java.util.List;

/**
 * 类: JavaElement <br>
 * 描述: java 元素<br>
 * 作者: gaoxugang <br>
 * 时间: 2018年01月31日 15:59
 */
public abstract class JavaElement {

    private List<String> javaDocLines;

    private JavaVisibility visibility = JavaVisibility.DEFAULT;

    //是否是静态
    private boolean isStatic;

    //是否final修饰符
    private boolean isFinal;

    //所有的注解
    private List<String> annotations;

    public JavaElement() {
        this.javaDocLines = new ArrayList<String>();
        this.annotations = new ArrayList<String>();
    }

    public JavaElement(JavaElement javaElement){
        this();
        this.annotations.addAll(javaElement.annotations);
        this.isFinal = javaElement.isFinal;
        this.isStatic = javaElement.isStatic;
        this.javaDocLines.addAll(javaElement.javaDocLines);
        this.visibility = javaElement.visibility;
    }

    public List<String> getJavaDocLines() {
        return javaDocLines;
    }

    public void addJavaDocLine(String javaDocLine){
        javaDocLines.add(javaDocLine);
    }

    public List<String> getAnnotations() {
        return annotations;
    }

    public void addAnnotation(String annotation){
        annotations.add(annotation);
    }

    public JavaVisibility getVisibility() {
        return visibility;
    }

    public void setVisibility(JavaVisibility visibility) {
        this.visibility = visibility;
    }

    /**
     * 添加注解
     */
    public void addSuppressTypeWarningsAnnotation(){
        addAnnotation("@SuppressWarnings(\"unchecked\")");
    }

    /**
     * 添加Java文档
     * @param sb
     * @param indentLevel
     */
    public void addFormattedJavadoc(StringBuilder sb,int indentLevel){
        for (String javaDocLine:javaDocLines){
            OutputUtilities.javaIndent(sb,indentLevel);
            sb.append(javaDocLine);
            OutputUtilities.newLine(sb);
        }
    }

    /**
     * 添加注解
     * @param sb
     * @param indentLevel
     */
    public void addFormattedAnnotations(StringBuilder sb,int indentLevel){
        for (String annotation:annotations){
            OutputUtilities.javaIndent(sb,indentLevel);
            sb.append(annotation);
            OutputUtilities.newLine(sb);
        }
    }

    public boolean isFinal() {
        return isFinal;
    }

    public void setFinal(boolean isFinal) {
        this.isFinal = isFinal;
    }

    public boolean isStatic() {
        return isStatic;
    }

    public void setStatic(boolean isStatic) {
        this.isStatic = isStatic;
    }
}

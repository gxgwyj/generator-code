package com.xyz.generator.api.dom.java;

import java.util.ArrayList;
import java.util.List;

/**
 * 类: Parameter <br>
 * 描述: 参数<br>
 * 作者: gaoxugang <br>
 * 时间: 2018年02月02日 11:11
 */
public class Parameter {

    private String name;
    private FullyQualifiedJavaType type;
    private boolean isVarargs;

    private List<String> annotations;


    public Parameter(FullyQualifiedJavaType type, String name, boolean isVarargs) {
        super();
        this.name = name;
        this.type = type;
        this.isVarargs = isVarargs;
        annotations = new ArrayList<String>();
    }

    public Parameter(FullyQualifiedJavaType type, String name) {
        this(type, name, false);
    }


    public Parameter(FullyQualifiedJavaType type, String name, String annotation) {
        this(type, name, false);
        addAnnotation(annotation);
    }

    public Parameter(FullyQualifiedJavaType type, String name, String annotation, boolean isVarargs) {
        this(type, name, isVarargs);
        addAnnotation(annotation);
    }

    public String getName() {
        return name;
    }

    public FullyQualifiedJavaType getType() {
        return type;
    }

    public List<String> getAnnotations() {
        return annotations;
    }

    public void addAnnotation(String annotation) {
        annotations.add(annotation);
    }

    public String getFormattedContent() {
        StringBuilder sb = new StringBuilder();

        for (String annotation : annotations) {
            sb.append(annotation);
            sb.append(' ');
        }

        sb.append(type.getShortName());
        sb.append(' ');
        if (isVarargs) {
            sb.append("... "); //$NON-NLS-1$
        }
        sb.append(name);

        return sb.toString();
    }

    @Override
    public String toString() {
        return getFormattedContent();
    }

    public boolean isVarargs() {
        return isVarargs;
    }
}

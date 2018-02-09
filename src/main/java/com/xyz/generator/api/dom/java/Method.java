package com.xyz.generator.api.dom.java;

import com.xyz.generator.api.dom.OutputUtilities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ListIterator;

/**
 * 类: Method <br>
 * 描述: 方法<br>
 * 作者: gaoxugang <br>
 * 时间: 2018年02月02日 11:09
 */
public class Method extends JavaElement {

    private List<String> bodyLines;

    //是否是构造方法
    private boolean constructor;

    //返回类型
    private FullyQualifiedJavaType returnType;

    private String name;

    private List<Parameter> parameters;

    private List<FullyQualifiedJavaType> exceptions;

    //是否同步方法
    private boolean isSynchronized;

    //是否本地方法
    private boolean isNative;


    public Method(){
        this("bar");
    }
    public Method(String name) {
        super();
        bodyLines = new ArrayList<String>();
        parameters = new ArrayList<Parameter>();
        exceptions = new ArrayList<FullyQualifiedJavaType>();
        this.name = name;
    }

    public Method(Method other){
        super(other);
        bodyLines = new ArrayList<String>();
        parameters = new ArrayList<Parameter>();
        exceptions = new ArrayList<FullyQualifiedJavaType>();

        this.bodyLines.addAll(other.bodyLines);
        this.constructor = other.constructor;
        this.exceptions.addAll(other.exceptions);
        this.name = other.name;
        this.parameters.addAll(other.parameters);
        this.returnType = other.returnType;
        this.isNative = other.isNative;
        this.isSynchronized = other.isSynchronized;
    }

    public List<String> getBodyLines() {
        return bodyLines;
    }

    public void addBodyLine(String line) {
        bodyLines.add(line);
    }

    public void addBodyLine(int index, String line) {
        bodyLines.add(index, line);
    }

    public void addBodyLines(Collection<String> lines) {
        bodyLines.addAll(lines);
    }

    public void addBodyLines(int index, Collection<String> lines) {
        bodyLines.addAll(index, lines);
    }

    public String getFormattedContent(int indentLevel, boolean interfaceMethod) {
        StringBuilder sb = new StringBuilder();

        addFormattedJavadoc(sb, indentLevel);
        addFormattedAnnotations(sb, indentLevel);

        OutputUtilities.javaIndent(sb, indentLevel);

        if (!interfaceMethod) {
            sb.append(getVisibility().getValue());

            if (isStatic()) {
                sb.append("static "); //$NON-NLS-1$
            }

            if (isFinal()) {
                sb.append("final "); //$NON-NLS-1$
            }

            if (isSynchronized()) {
                sb.append("synchronized "); //$NON-NLS-1$
            }

            if (isNative()) {
                sb.append("native "); //$NON-NLS-1$
            } else if (bodyLines.size() == 0) {
                sb.append("abstract "); //$NON-NLS-1$
            }
        }

        if (!constructor) {
            if (getReturnType() == null) {
                sb.append("void"); //$NON-NLS-1$
            } else {
                sb.append(getReturnType().getShortName());
            }
            sb.append(' ');
        }

        sb.append(getName());
        sb.append('(');

        boolean comma = false;
        for (Parameter parameter : getParameters()) {
            if (comma) {
                sb.append(", "); //$NON-NLS-1$
            } else {
                comma = true;
            }

            sb.append(parameter.getFormattedContent());
        }

        sb.append(')');

        if (getExceptions().size() > 0) {
            sb.append(" throws "); //$NON-NLS-1$
            comma = false;
            for (FullyQualifiedJavaType fqjt : getExceptions()) {
                if (comma) {
                    sb.append(", "); //$NON-NLS-1$
                } else {
                    comma = true;
                }

                sb.append(fqjt.getShortName());
            }
        }

        // if no body lines, then this is an abstract method
        if (bodyLines.size() == 0 || isNative()) {
            sb.append(';');
        } else {
            sb.append(" {"); //$NON-NLS-1$
            indentLevel++;

            ListIterator<String> listIter = bodyLines.listIterator();
            while (listIter.hasNext()) {
                String line = listIter.next();
                if (line.startsWith("}")) { //$NON-NLS-1$
                    indentLevel--;
                }

                OutputUtilities.newLine(sb);
                OutputUtilities.javaIndent(sb, indentLevel);
                sb.append(line);

                if ((line.endsWith("{") && !line.startsWith("switch")) //$NON-NLS-1$ //$NON-NLS-2$
                        || line.endsWith(":")) { //$NON-NLS-1$
                    indentLevel++;
                }

                if (line.startsWith("break")) { //$NON-NLS-1$
                    // if the next line is '}', then don't outdent
                    if (listIter.hasNext()) {
                        String nextLine = listIter.next();
                        if (nextLine.startsWith("}")) { //$NON-NLS-1$
                            indentLevel++;
                        }

                        // set back to the previous element
                        listIter.previous();
                    }
                    indentLevel--;
                }
            }

            indentLevel--;
            OutputUtilities.newLine(sb);
            OutputUtilities.javaIndent(sb, indentLevel);
            sb.append('}');
        }

        return sb.toString();
    }


    public boolean isConstructor() {
        return constructor;
    }

    public void setConstructor(boolean constructor) {
        this.constructor = constructor;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Parameter> getParameters() {
        return parameters;
    }

    public void addParameter(Parameter parameter) {
        parameters.add(parameter);
    }

    public void addParameter(int index, Parameter parameter) {
        parameters.add(index, parameter);
    }

    public FullyQualifiedJavaType getReturnType() {
        return returnType;
    }

    public void setReturnType(FullyQualifiedJavaType returnType) {
        this.returnType = returnType;
    }

    public List<FullyQualifiedJavaType> getExceptions() {
        return exceptions;
    }

    public void addException(FullyQualifiedJavaType exception) {
        exceptions.add(exception);
    }

    public boolean isSynchronized() {
        return isSynchronized;
    }

    public void setSynchronized(boolean isSynchronized) {
        this.isSynchronized = isSynchronized;
    }

    public boolean isNative() {
        return isNative;
    }

    public void setNative(boolean isNative) {
        this.isNative = isNative;
    }




}

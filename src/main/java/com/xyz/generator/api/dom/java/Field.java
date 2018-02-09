package com.xyz.generator.api.dom.java;

import com.xyz.generator.api.dom.OutputUtilities;

/**
 * 类: Field <br>
 * 描述: 字段<br>
 * 作者: gaoxugang <br>
 * 时间: 2018年02月02日 09:59
 */
public class Field extends JavaElement {

    private FullyQualifiedJavaType type;
    private String name;
    //初始化字符串
    private String initializationString;
    //瞬时态标识
    private boolean isTransient;
    private boolean isVolatile;


    public Field(){
        this("foo",FullyQualifiedJavaType.getIntInstance());
    }

    public Field(String name, FullyQualifiedJavaType type) {
        super();
        this.name = name;
        this.type = type;
    }

    public Field(Field field){
        super(field);
        this.type = field.type;
        this.name = field.name;
        this.initializationString = field.initializationString;
    }


    public FullyQualifiedJavaType getType() {
        return type;
    }

    public void setType(FullyQualifiedJavaType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInitializationString() {
        return initializationString;
    }

    public void setInitializationString(String initializationString) {
        this.initializationString = initializationString;
    }

    public boolean isTransient() {
        return isTransient;
    }

    public void setTransient(boolean isTransient) {
        this.isTransient = isTransient;
    }

    public boolean isVolatile() {
        return isVolatile;
    }

    public void setVolatile(boolean isVolatile) {
        this.isVolatile = isVolatile;
    }

    /**
     * 获取标准的内容
     * @param indentLevel
     * @return
     */
    public String getFormattedContent(int indentLevel){
        StringBuilder sb = new StringBuilder();

        addFormattedJavadoc(sb,indentLevel);
        addFormattedAnnotations(sb,indentLevel);

        OutputUtilities.javaIndent(sb,indentLevel);
        sb.append(getVisibility().getValue());

        if (isStatic()){
            sb.append("static ");
        }

        if (isFinal()){
            sb.append("final ");
        }

        if (isTransient()){
            sb.append("transient ");
        }

        if (isVolatile()){
            sb.append("volatile");
        }

        sb.append(type.getShortName());

        sb.append(' ');
        sb.append(name);

        if (initializationString != null && initializationString.length() >0){
            sb.append(" = ");
            sb.append(initializationString);
        }

        sb.append(';');

        return sb.toString();
    }
}

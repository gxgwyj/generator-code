package com.xyz.generator.api.dom.java;

/**
 * 类: JavaVisibility <br>
 * 描述: java访问权限关键字<br>
 * 作者: gaoxugang <br>
 * 时间: 2018年01月31日 16:00
 */
public enum JavaVisibility {

    PUBLIC("public "),
    PRIVATE("private "),
    PROTECTED("protected "),
    DEFAULT("");

    private String value;

    private JavaVisibility(String value){
        this.value = value;
    }

    public String getValue(){
        return value;
    }
}

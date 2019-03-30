package com.xyz;

/**
 * 类: FieldInfo <br>
 * 描述: 字段名称<br>
 * 作者:  gaoxugang<br>
 * 时间: 2019年03月27日 13:00
 */
public class FieldInfo {

    private String name;
    private String desc;
    private String type;

    public FieldInfo(String name, String desc, String type) {
        this.name = name;
        this.desc = desc;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

package com.xyz.generator.api.dom.xml;

/**
 * 类: Attribute <br>
 * 描述: xml文件属性实体类<br>
 * 作者: gaoxugang <br>
 * 时间: 2018年01月31日 10:23
 */
public class Attribute implements Comparable<Attribute>{


    private String name;

    private String value;


    /**
     * 构造方法
     * @param name
     * @param value
     */
    public Attribute(String name, String value) {
        this.name = name;
        this.value = value;
    }


    /**
     * 排序方法
     * @param o
     * @return
     */
    @Override
    public int compareTo(Attribute o) {
        if (this.name==null){
            return o.name == null ? 0:-1;
        }else{
            if (o.name == null){
                return 0;
            }else{
                return this.name.compareTo(o.name);
            }
        }
    }

    public String getFormattedContent(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(name);
        stringBuilder.append("=\"");
        stringBuilder.append(value);
        stringBuilder.append("\"");

        return stringBuilder.toString();
    }


    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }
}

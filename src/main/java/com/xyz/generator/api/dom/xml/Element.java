package com.xyz.generator.api.dom.xml;

/**
 * 类: Element <br>
 * 描述: xml元素基类<br>
 * 作者: gaoxugang <br>
 * 时间: 2018年01月31日 10:10
 */
public abstract class Element {

    /**
     * 构造方法
     */
    public Element() {
        super();
    }

    /**
     * 获得元素格式化的内容
     * @param indentLevel
     * @return
     */
    public abstract String getFormattedContent(int indentLevel);
}

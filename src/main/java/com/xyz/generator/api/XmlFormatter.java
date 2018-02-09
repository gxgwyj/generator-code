package com.xyz.generator.api;

import com.xyz.generator.api.dom.xml.Document;
import com.xyz.generator.config.Context;

/**
 * 类: XmlFormatter <br>
 * 描述: xml格式化<br>
 * 作者: gaoxugang <br>
 * 时间: 2018年02月02日 18:48
 */
public interface XmlFormatter {
    void setContext(Context context);
    String getFormattedContent(Document document);
}

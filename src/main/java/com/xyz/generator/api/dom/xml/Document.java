package com.xyz.generator.api.dom.xml;

import com.xyz.generator.api.dom.OutputUtilities;

/**
 * 类: Documnet <br>
 * 描述: 文档实体类<br>
 * 作者: gaoxugang <br>
 * 时间: 2018年01月31日 14:12
 */
public class Document {

    private String publicId;

    private String systemId;

    private XmlElement rootElement;

    public Document(String publicId, String systemId) {
        this.publicId = publicId;
        this.systemId = systemId;
    }

    public Document() {
    }

    public String getPublicId() {
        return publicId;
    }

    public String getSystemId() {
        return systemId;
    }

    public XmlElement getRootElement() {
        return rootElement;
    }

    public void setRootElement(XmlElement rootElement) {
        this.rootElement = rootElement;
    }


    public String getFormattedContent(){
        StringBuilder sb = new StringBuilder();

        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");

        if (publicId != null && systemId != null){
            OutputUtilities.newLine(sb);
            sb.append("<!DOCTYPE ");
            sb.append(rootElement.getName());
            sb.append(" PUBLIC \"");
            sb.append(publicId);
            sb.append("\" \"");
            sb.append(systemId);
            sb.append("\">");
        }

        OutputUtilities.newLine(sb);
        sb.append(rootElement.getFormattedContent(0));

        return sb.toString();


    }
}

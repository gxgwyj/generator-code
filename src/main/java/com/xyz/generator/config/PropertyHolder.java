package com.xyz.generator.config;

import com.xyz.generator.api.dom.xml.Attribute;
import com.xyz.generator.api.dom.xml.XmlElement;

import java.util.Enumeration;
import java.util.Properties;

/**
 * 类: PropertyHolder <br>
 * 描述: xml属性处理抽象类<br>
 * 作者: gaoxugang <br>
 * 时间: 2018年01月30日 16:34
 */
public abstract class PropertyHolder {

    private Properties properties;

    /**
     * 构造方法
     */
    public PropertyHolder() {
        super();
        this.properties = new Properties();
    }

    /**
     * 添加值
     * @param key
     * @param value
     */
    public void addProperty(String key,String value){
        properties.setProperty(key,value);
    }

    /**
     * 获取属性值
     * @param key
     * @return
     */
    public String getProperty(String key){
        return properties.getProperty(key);
    }


    /**
     * 获取所有的属性值
     * @return
     */
    public Properties getProperties() {
        return properties;
    }

    protected void addPropertyXmlElements(XmlElement xmlElement){
        Enumeration<?> enumeration = properties.propertyNames();
        while (enumeration.hasMoreElements()){
            String propertyName = (String)enumeration.nextElement();
            XmlElement propertyElement = new XmlElement("property");
            propertyElement.addAttribute(new Attribute("name",propertyName));
            propertyElement.addAttribute(new Attribute("value",properties.getProperty(propertyName)));
            xmlElement.addElement(propertyElement);
        }
    }
}

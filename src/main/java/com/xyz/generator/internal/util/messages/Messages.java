package com.xyz.generator.internal.util.messages;

import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * 类: Messages <br>
 * 描述: 字符串工具类<br>
 * 作者: gaoxugang <br>
 * 时间: 2018年01月30日 10:29
 */
public class Messages {

    //属性文件的全路径
    private static final String BUNDLE_NAME = "com.xyz.generator.internal.util.messages.messages"; //$NON-NLS-1$

    //类似Propertie读取配置文件的值
    private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);//获取属性文件的值

    /**
     * 构造方法
     */
    private Messages() {
    }

    /**
     * 根据key获取值
     * @param key
     * @return
     */
    public static String getString(String key){
        try {
            return RESOURCE_BUNDLE.getString(key);
        } catch (MissingResourceException e) {
            e.printStackTrace();
            return '!'+key+'!';
        }
    }
    /**
     * 根据key和参数构造完整的消息
     * @param key
     * @param param
     * @return
     */
    public static String getString(String key,String... param){
        try {
            return MessageFormat.format(RESOURCE_BUNDLE.getString(key),param);
        } catch (MissingResourceException e) {
            e.printStackTrace();
            return '!'+key+'!';
        }
    }


}

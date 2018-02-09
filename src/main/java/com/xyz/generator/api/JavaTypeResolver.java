package com.xyz.generator.api;

import com.xyz.generator.api.dom.java.FullyQualifiedJavaType;
import com.xyz.generator.config.Context;

import java.util.List;
import java.util.Properties;

/**
 * 类: JavaTypeResolver <br>
 * 描述: <br>
 * 作者: gaoxugang <br>
 * 时间: 2018年02月02日 18:29
 */
public interface JavaTypeResolver {

    void addConfigurationProperties(Properties properties);

    void setContext(Context context);

    void setWarnings(List<String> warnings);

    FullyQualifiedJavaType calculateJavaType(
            IntrospectedColumn introspectedColumn);

    String calculateJdbcTypeName(IntrospectedColumn introspectedColumn);
}

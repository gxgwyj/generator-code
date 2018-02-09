package com.xyz.generator.api;

import com.xyz.generator.api.dom.java.CompilationUnit;
import com.xyz.generator.config.Context;

/**
 * 类: JavaFormatter <br>
 * 描述: java 格式化接口<br>
 * 作者: gaoxugang <br>
 * 时间: 2018年02月02日 16:39
 */
public interface JavaFormatter {
    void setContext(Context context);
    String getFormattedContent(CompilationUnit compilationUnit);
}

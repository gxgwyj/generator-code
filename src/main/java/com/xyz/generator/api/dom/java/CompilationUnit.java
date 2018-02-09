package com.xyz.generator.api.dom.java;

import java.util.List;
import java.util.Set;

/**
 * 类: CompilationUnit <br>
 * 描述: 编译单元接口<br>
 * 作者: gaoxugang <br>
 * 时间: 2018年01月31日 16:14
 */
public interface CompilationUnit {

    /**
     * 获取一个标准的内容
     * @return
     */
    String getFormattedContent();

    /**
     * 获取所有的import导入类型
     * @return
     */
    Set<FullyQualifiedJavaType> getImportedTypes();

    /**
     * 获取所有的static 静态导入集合
     * @return
     */
    Set<String> getStaticImports();

    /**
     * 获取直接父类
     * @return
     */
    FullyQualifiedJavaType getSuperClass();

    /**
     * 判断是否为接口
     * @return
     */
    boolean isJavaInterface();

    boolean isJavaEnumeration();

    /**
     * 获取所有的接口父类
     * @return
     */
    Set<FullyQualifiedJavaType> getSuperInterfaceTypes();

    /**
     * 获取Java类型
     * @return
     */
    FullyQualifiedJavaType getType();

    /**
     * 添加引入类
     * @param importedType
     */
    void addImportedType(FullyQualifiedJavaType importedType);

    void addImportedTypes(Set<FullyQualifiedJavaType> importedTypes);

    /**
     * 添加静态引入类
     * @param staticImport
     */
    void addStaticImport(String staticImport);

    void addStaticImports(Set<String> staticImports);

    /**
     * 添加注释
     * @param commentLine
     */
    void addFileCommentLine(String commentLine);

    List<String> getFileCommentLines();



}

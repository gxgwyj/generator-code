package com.xyz.generator.codegen.mybatis3.javamapper.elements;

import com.xyz.generator.api.dom.java.FullyQualifiedJavaType;
import com.xyz.generator.api.dom.java.Interface;
import com.xyz.generator.api.dom.java.JavaVisibility;
import com.xyz.generator.api.dom.java.Method;
import com.xyz.generator.api.dom.java.Parameter;

import java.util.Set;
import java.util.TreeSet;

/**
 * 类: InsertBatchMethodGenerator <br>
 * 描述: 批量插入方法<br>
 * 作者:  gaoxugang<br>
 * 时间: 2019年04月18日 16:44
 */
public class InsertBatchMethodGenerator extends AbstractJavaMapperMethodGenerator{

    @Override
    public void addInterfaceElements(Interface interfaze) {
        // 需要引入的类
        Set<FullyQualifiedJavaType> importedTypes = new TreeSet<FullyQualifiedJavaType>();

        //----------------------------------设置方法属性-----------------------------------
        Method method = new Method();
        // 访问权限
        method.setVisibility(JavaVisibility.PUBLIC);
        // 方法名称
        method.setName(introspectedTable.getInserBatchStatementId());
        // 方法返回类别
        method.setReturnType(FullyQualifiedJavaType.getIntInstance());
        // 方法参数
        //返回类型(列表)
        FullyQualifiedJavaType param = FullyQualifiedJavaType
                .getNewListInstance();
        FullyQualifiedJavaType listType = new FullyQualifiedJavaType(
                introspectedTable.getBaseRecordType());
        param.addTypeArgument(listType);

        method.addParameter(new Parameter(param,"recordList"));
        // 方法加注解
        addMapperAnnotations(interfaze, method);

        // 方法加入生成器
        context.getCommentGenerator().addGeneralMethodComment(method, introspectedTable);

        if (context.getPlugins().clientInsertBatchMethodGenerated(
                method, interfaze, introspectedTable)) {
            interfaze.addImportedTypes(importedTypes);
            interfaze.addMethod(method);
        }

    }


    public void addMapperAnnotations(Interface interfaze, Method method) {
        return;
    }
}

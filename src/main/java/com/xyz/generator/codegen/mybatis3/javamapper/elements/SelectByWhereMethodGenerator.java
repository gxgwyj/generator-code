package com.xyz.generator.codegen.mybatis3.javamapper.elements;

import com.xyz.generator.api.dom.java.*;

import java.util.Set;
import java.util.TreeSet;

/**
 * 类: SelectByWhereMethodGenerator <br>
 * 描述: 根据条件查询方法<br>
 * 作者: gaoxugang <br>
 * 时间: 2018年02月08日 16:28
 */
public class SelectByWhereMethodGenerator extends AbstractJavaMapperMethodGenerator{

    boolean isSimple;

    public SelectByWhereMethodGenerator(boolean isSimple) {
        super();
        this.isSimple = isSimple;
    }

    @Override
    public void addInterfaceElements(Interface interfaze) {
        //设置方法需要引入的类
        Set<FullyQualifiedJavaType> importedTypes = new TreeSet<FullyQualifiedJavaType>();

        //返回实体类型
        FullyQualifiedJavaType listType = new FullyQualifiedJavaType(
                introspectedTable.getBaseRecordType());



        //返回类型(列表)
        FullyQualifiedJavaType returnType = FullyQualifiedJavaType
                .getNewListInstance();

        returnType.addTypeArgument(listType);


        importedTypes.add(listType);
        importedTypes.add(returnType);

        //申明方法
        Method method = new Method();
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setName(introspectedTable.getSelectByWhereStatementId());
        method.setReturnType(returnType);
        method.addParameter(new Parameter(listType,"record"));


        if (context.getPlugins().clientSelectByWhereMethodGenerated(method,
                interfaze, introspectedTable)) {
            interfaze.addImportedTypes(importedTypes);
            interfaze.addMethod(method);
        }

    }
}

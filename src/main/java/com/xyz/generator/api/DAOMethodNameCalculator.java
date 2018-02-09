/**
 *    Copyright 2006-2015 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package com.xyz.generator.api;

/**
 * 该接口用于为DAO方法提供名称。用户可以提供如果提供的实现还不够，则不同的实现。
 */
public interface DAOMethodNameCalculator {
    

    String getInsertMethodName(IntrospectedTable introspectedTable);


    String getInsertSelectiveMethodName(IntrospectedTable introspectedTable);


    String getUpdateByPrimaryKeyWithoutBLOBsMethodName(
            IntrospectedTable introspectedTable);


    String getUpdateByPrimaryKeyWithBLOBsMethodName(
            IntrospectedTable introspectedTable);


    String getUpdateByPrimaryKeySelectiveMethodName(
            IntrospectedTable introspectedTable);


    String getSelectByPrimaryKeyMethodName(IntrospectedTable introspectedTable);


    String getSelectByExampleWithoutBLOBsMethodName(
            IntrospectedTable introspectedTable);


    String getSelectByExampleWithBLOBsMethodName(
            IntrospectedTable introspectedTable);


    String getDeleteByPrimaryKeyMethodName(IntrospectedTable introspectedTable);


    String getDeleteByExampleMethodName(IntrospectedTable introspectedTable);


    String getCountByExampleMethodName(IntrospectedTable introspectedTable);


    String getUpdateByExampleSelectiveMethodName(
            IntrospectedTable introspectedTable);


    String getUpdateByExampleWithBLOBsMethodName(
            IntrospectedTable introspectedTable);


    String getUpdateByExampleWithoutBLOBsMethodName(
            IntrospectedTable introspectedTable);
}

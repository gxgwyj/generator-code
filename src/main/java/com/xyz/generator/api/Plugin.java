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

import com.xyz.generator.api.dom.java.Field;
import com.xyz.generator.api.dom.java.Interface;
import com.xyz.generator.api.dom.java.Method;
import com.xyz.generator.api.dom.java.TopLevelClass;
import com.xyz.generator.api.dom.xml.Document;
import com.xyz.generator.api.dom.xml.XmlElement;
import com.xyz.generator.config.Context;

import java.util.List;
import java.util.Properties;

public interface Plugin {
    
    /**
     * The Enum ModelClassType.
     */
    public enum ModelClassType {
        
        /** The primary key. */
        PRIMARY_KEY, 
 /** The base record. */
 BASE_RECORD, 
 /** The record with blobs. */
 RECORD_WITH_BLOBS
    }


    void setContext(Context context);

    void setProperties(Properties properties);

    void initialized(IntrospectedTable introspectedTable);

    boolean validate(List<String> warnings);

    List<GeneratedJavaFile> contextGenerateAdditionalJavaFiles();

    List<GeneratedJavaFile> contextGenerateAdditionalJavaFiles(
            IntrospectedTable introspectedTable);

    List<GeneratedXmlFile> contextGenerateAdditionalXmlFiles();

    List<GeneratedXmlFile> contextGenerateAdditionalXmlFiles(
            IntrospectedTable introspectedTable);

    boolean clientGenerated(Interface interfaze, TopLevelClass topLevelClass,
                            IntrospectedTable introspectedTable);

    boolean clientCountByExampleMethodGenerated(Method method,
                                                TopLevelClass topLevelClass, IntrospectedTable introspectedTable);

    boolean clientDeleteByExampleMethodGenerated(Method method,
                                                 TopLevelClass topLevelClass, IntrospectedTable introspectedTable);

    boolean clientDeleteByPrimaryKeyMethodGenerated(Method method,
                                                    TopLevelClass topLevelClass, IntrospectedTable introspectedTable);

    boolean clientInsertMethodGenerated(Method method,
                                        TopLevelClass topLevelClass, IntrospectedTable introspectedTable);


    boolean clientInsertSelectiveMethodGenerated(Method method,
                                                 TopLevelClass topLevelClass, IntrospectedTable introspectedTable);


    boolean clientSelectByExampleWithBLOBsMethodGenerated(Method method,
                                                          TopLevelClass topLevelClass, IntrospectedTable introspectedTable);


    boolean clientSelectByExampleWithoutBLOBsMethodGenerated(Method method,
                                                             TopLevelClass topLevelClass, IntrospectedTable introspectedTable);


    boolean clientSelectByPrimaryKeyMethodGenerated(Method method,
                                                    TopLevelClass topLevelClass, IntrospectedTable introspectedTable);


    boolean clientUpdateByExampleSelectiveMethodGenerated(Method method,
                                                          TopLevelClass topLevelClass, IntrospectedTable introspectedTable);


    boolean clientUpdateByExampleWithBLOBsMethodGenerated(Method method,
                                                          TopLevelClass topLevelClass, IntrospectedTable introspectedTable);


    boolean clientUpdateByExampleWithoutBLOBsMethodGenerated(Method method,
                                                             TopLevelClass topLevelClass, IntrospectedTable introspectedTable);


    boolean clientUpdateByPrimaryKeySelectiveMethodGenerated(Method method,
                                                             TopLevelClass topLevelClass, IntrospectedTable introspectedTable);


    boolean clientUpdateByPrimaryKeyWithBLOBsMethodGenerated(Method method,
                                                             TopLevelClass topLevelClass, IntrospectedTable introspectedTable);

    /**
     * This method is called when the updateByPrimaryKeyWithoutBLOBs method has
     * been generated in the client implementation class.
     * 
     * @param method
     *            the generated updateByPrimaryKeyWithBLOBs method
     * @param topLevelClass
     *            the partially implemented client implementation class. You can
     *            add additional imported classes to the implementation class if
     *            necessary.
     * @param introspectedTable
     *            The class containing information about the table as
     *            introspected from the database
     * @return true if the method should be generated, false if the generated
     *         method should be ignored. In the case of multiple plugins, the
     *         first plugin returning false will disable the calling of further
     *         plugins.
     */
    boolean clientUpdateByPrimaryKeyWithoutBLOBsMethodGenerated(Method method,
                                                                TopLevelClass topLevelClass, IntrospectedTable introspectedTable);

    /**
     * This method is called when the countByExample method has been generated
     * in the client interface.
     * 
     * @param method
     *            the generated countByExample method
     * @param interfaze
     *            the partially implemented client interface. You can add
     *            additional imported classes to the interface if
     *            necessary.
     * @param introspectedTable
     *            The class containing information about the table as
     *            introspected from the database
     * @return true if the method should be generated, false if the generated
     *         method should be ignored. In the case of multiple plugins, the
     *         first plugin returning false will disable the calling of further
     *         plugins.
     */
    boolean clientCountByExampleMethodGenerated(Method method,
                                                Interface interfaze, IntrospectedTable introspectedTable);

    /**
     * This method is called when the deleteByExample method has been generated
     * in the client interface.
     * 
     * @param method
     *            the generated deleteByExample method
     * @param interfaze
     *            the partially implemented client interface. You can add
     *            additional imported classes to the interface if
     *            necessary.
     * @param introspectedTable
     *            The class containing information about the table as
     *            introspected from the database
     * @return true if the method should be generated, false if the generated
     *         method should be ignored. In the case of multiple plugins, the
     *         first plugin returning false will disable the calling of further
     *         plugins.
     */
    boolean clientDeleteByExampleMethodGenerated(Method method,
                                                 Interface interfaze, IntrospectedTable introspectedTable);

    /**
     * This method is called when the deleteByPrimaryKey method has been
     * generated in the client interface.
     * 
     * @param method
     *            the generated deleteByPrimaryKey method
     * @param interfaze
     *            the partially implemented client interface. You can add
     *            additional imported classes to the interface if
     *            necessary.
     * @param introspectedTable
     *            The class containing information about the table as
     *            introspected from the database
     * @return true if the method should be generated, false if the generated
     *         method should be ignored. In the case of multiple plugins, the
     *         first plugin returning false will disable the calling of further
     *         plugins.
     */
    boolean clientDeleteByPrimaryKeyMethodGenerated(Method method,
                                                    Interface interfaze, IntrospectedTable introspectedTable);

    /**
     * This method is called when the insert method has been generated in the
     * client interface.
     * 
     * @param method
     *            the generated insert method
     * @param interfaze
     *            the partially implemented client interface. You can add
     *            additional imported classes to the interface if
     *            necessary.
     * @param introspectedTable
     *            The class containing information about the table as
     *            introspected from the database
     * @return true if the method should be generated, false if the generated
     *         method should be ignored. In the case of multiple plugins, the
     *         first plugin returning false will disable the calling of further
     *         plugins.
     */
    boolean clientInsertMethodGenerated(Method method, Interface interfaze,
                                        IntrospectedTable introspectedTable);

   /**
    * 条件查询列表
    * @param method
    * @param interfaze
    * @param introspectedTable
    * @return
    */
    boolean clientSelectByWhereMethodGenerated(Method method, Interface interfaze,
                                        IntrospectedTable introspectedTable);

   /**
    * 批量插入方法
    * @param method
    * @param interfaze
    * @param introspectedTable
    * @return
    */
    boolean clientInsertBatchMethodGenerated(Method method, Interface interfaze,
                                              IntrospectedTable introspectedTable);

    /**
     * This method is called when the insert selective method has been generated
     * in the client interface.
     * 
     * @param method
     *            the generated insert method
     * @param interfaze
     *            the partially implemented client interface. You can add
     *            additional imported classes to the interface if
     *            necessary.
     * @param introspectedTable
     *            The class containing information about the table as
     *            introspected from the database
     * @return true if the method should be generated, false if the generated
     *         method should be ignored. In the case of multiple plugins, the
     *         first plugin returning false will disable the calling of further
     *         plugins.
     */
    boolean clientInsertSelectiveMethodGenerated(Method method,
                                                 Interface interfaze, IntrospectedTable introspectedTable);

    /**
     * This method is called when the selectAll method has been
     * generated in the client interface.  This method is only generated by
     * the simple runtime.
     * 
     * @param method
     *            the generated selectAll method
     * @param interfaze
     *            the partially implemented client interface. You can add
     *            additional imported classes to the interface if
     *            necessary.
     * @param introspectedTable
     *            The class containing information about the table as
     *            introspected from the database
     * @return true if the method should be generated, false if the generated
     *         method should be ignored. In the case of multiple plugins, the
     *         first plugin returning false will disable the calling of further
     *         plugins.
     */
    boolean clientSelectAllMethodGenerated(Method method,
                                           Interface interfaze, IntrospectedTable introspectedTable);

    /**
     * This method is called when the selectAll method has been
     * generated in the client implementation class.
     * 
     * @param method
     *            the generated selectAll method
     * @param topLevelClass
     *            the partially implemented client implementation class. You can
     *            add additional imported classes to the implementation class if
     *            necessary.
     * @param introspectedTable
     *            The class containing information about the table as
     *            introspected from the database
     * @return true if the method should be generated, false if the generated
     *         method should be ignored. In the case of multiple plugins, the
     *         first plugin returning false will disable the calling of further
     *         plugins.
     */
    boolean clientSelectAllMethodGenerated(Method method,
                                           TopLevelClass topLevelClass, IntrospectedTable introspectedTable);
    
    /**
     * This method is called when the selectByExampleWithBLOBs method has been
     * generated in the client interface.
     * 
     * @param method
     *            the generated selectByExampleWithBLOBs method
     * @param interfaze
     *            the partially implemented client interface. You can add
     *            additional imported classes to the interface if
     *            necessary.
     * @param introspectedTable
     *            The class containing information about the table as
     *            introspected from the database
     * @return true if the method should be generated, false if the generated
     *         method should be ignored. In the case of multiple plugins, the
     *         first plugin returning false will disable the calling of further
     *         plugins.
     */
    boolean clientSelectByExampleWithBLOBsMethodGenerated(Method method,
                                                          Interface interfaze, IntrospectedTable introspectedTable);

    /**
     * This method is called when the selectByExampleWithoutBLOBs method has
     * been generated in the client interface.
     * 
     * @param method
     *            the generated selectByExampleWithoutBLOBs method
     * @param interfaze
     *            the partially implemented client interface. You can add
     *            additional imported classes to the interface if
     *            necessary.
     * @param introspectedTable
     *            The class containing information about the table as
     *            introspected from the database
     * @return true if the method should be generated, false if the generated
     *         method should be ignored. In the case of multiple plugins, the
     *         first plugin returning false will disable the calling of further
     *         plugins.
     */
    boolean clientSelectByExampleWithoutBLOBsMethodGenerated(Method method,
                                                             Interface interfaze, IntrospectedTable introspectedTable);

    /**
     * This method is called when the selectByPrimaryKey method has been
     * generated in the client interface.
     * 
     * @param method
     *            the generated selectByPrimaryKey method
     * @param interfaze
     *            the partially implemented client interface. You can add
     *            additional imported classes to the interface if
     *            necessary.
     * @param introspectedTable
     *            The class containing information about the table as
     *            introspected from the database
     * @return true if the method should be generated, false if the generated
     *         method should be ignored. In the case of multiple plugins, the
     *         first plugin returning false will disable the calling of further
     *         plugins.
     */
    boolean clientSelectByPrimaryKeyMethodGenerated(Method method,
                                                    Interface interfaze, IntrospectedTable introspectedTable);

    /**
     * This method is called when the updateByExampleSelective method has been
     * generated in the client interface.
     * 
     * @param method
     *            the generated updateByExampleSelective method
     * @param interfaze
     *            the partially implemented client interface. You can add
     *            additional imported classes to the interface if
     *            necessary.
     * @param introspectedTable
     *            The class containing information about the table as
     *            introspected from the database
     * @return true if the method should be generated, false if the generated
     *         method should be ignored. In the case of multiple plugins, the
     *         first plugin returning false will disable the calling of further
     *         plugins.
     */
    boolean clientUpdateByExampleSelectiveMethodGenerated(Method method,
                                                          Interface interfaze, IntrospectedTable introspectedTable);

    /**
     * This method is called when the updateByExampleWithBLOBs method has been
     * generated in the client interface.
     * 
     * @param method
     *            the generated updateByExampleWithBLOBs method
     * @param interfaze
     *            the partially implemented client interface. You can add
     *            additional imported classes to the interface if
     *            necessary.
     * @param introspectedTable
     *            The class containing information about the table as
     *            introspected from the database
     * @return true if the method should be generated, false if the generated
     *         method should be ignored. In the case of multiple plugins, the
     *         first plugin returning false will disable the calling of further
     *         plugins.
     */
    boolean clientUpdateByExampleWithBLOBsMethodGenerated(Method method,
                                                          Interface interfaze, IntrospectedTable introspectedTable);

    /**
     * This method is called when the updateByExampleWithoutBLOBs method has
     * been generated in the client interface.
     * 
     * @param method
     *            the generated updateByExampleWithoutBLOBs method
     * @param interfaze
     *            the partially implemented client interface. You can add
     *            additional imported classes to the interface if
     *            necessary.
     * @param introspectedTable
     *            The class containing information about the table as
     *            introspected from the database
     * @return true if the method should be generated, false if the generated
     *         method should be ignored. In the case of multiple plugins, the
     *         first plugin returning false will disable the calling of further
     *         plugins.
     */
    boolean clientUpdateByExampleWithoutBLOBsMethodGenerated(Method method,
                                                             Interface interfaze, IntrospectedTable introspectedTable);

    /**
     * This method is called when the updateByPrimaryKeySelective method has
     * been generated in the client interface.
     * 
     * @param method
     *            the generated updateByPrimaryKeySelective method
     * @param interfaze
     *            the partially implemented client interface. You can add
     *            additional imported classes to the interface if
     *            necessary.
     * @param introspectedTable
     *            The class containing information about the table as
     *            introspected from the database
     * @return true if the method should be generated, false if the generated
     *         method should be ignored. In the case of multiple plugins, the
     *         first plugin returning false will disable the calling of further
     *         plugins.
     */
    boolean clientUpdateByPrimaryKeySelectiveMethodGenerated(Method method,
                                                             Interface interfaze, IntrospectedTable introspectedTable);

    /**
     * This method is called when the updateByPrimaryKeyWithBLOBs method has
     * been generated in the client interface.
     * 
     * @param method
     *            the generated updateByPrimaryKeyWithBLOBs method
     * @param interfaze
     *            the partially implemented client interface. You can add
     *            additional imported classes to the interface if
     *            necessary.
     * @param introspectedTable
     *            The class containing information about the table as
     *            introspected from the database
     * @return true if the method should be generated, false if the generated
     *         method should be ignored. In the case of multiple plugins, the
     *         first plugin returning false will disable the calling of further
     *         plugins.
     */
    boolean clientUpdateByPrimaryKeyWithBLOBsMethodGenerated(Method method,
                                                             Interface interfaze, IntrospectedTable introspectedTable);

    /**
     * This method is called when the updateByPrimaryKeyWithoutBLOBs method has
     * been generated in the client interface.
     * 
     * @param method
     *            the generated updateByPrimaryKeyWithoutBLOBs method
     * @param interfaze
     *            the partially implemented client interface. You can add
     *            additional imported classes to the interface if
     *            necessary.
     * @param introspectedTable
     *            The class containing information about the table as
     *            introspected from the database
     * @return true if the method should be generated, false if the generated
     *         method should be ignored. In the case of multiple plugins, the
     *         first plugin returning false will disable the calling of further
     *         plugins.
     */
    boolean clientUpdateByPrimaryKeyWithoutBLOBsMethodGenerated(Method method,
                                                                Interface interfaze, IntrospectedTable introspectedTable);

    /**
     * This method is called after the field is generated for a specific column
     * in a table.
     * 
     * @param field
     *            the field generated for the specified column
     * @param topLevelClass
     *            the partially implemented model class. You can add additional
     *            imported classes to the implementation class if necessary.
     * @param introspectedColumn
     *            The class containing information about the column related
     *            to this field as introspected from the database
     * @param introspectedTable
     *            The class containing information about the table as
     *            introspected from the database
     * @param modelClassType
     *            the type of class that the field is generated for
     * @return true if the field should be generated, false if the generated
     *         field should be ignored. In the case of multiple plugins, the
     *         first plugin returning false will disable the calling of further
     *         plugins.
     */
    boolean modelFieldGenerated(Field field, TopLevelClass topLevelClass,
                                IntrospectedColumn introspectedColumn,
                                IntrospectedTable introspectedTable, ModelClassType modelClassType);

    /**
     * This method is called after the getter, or accessor, method is generated
     * for a specific column in a table.
     * 
     * @param method
     *            the getter, or accessor, method generated for the specified
     *            column
     * @param topLevelClass
     *            the partially implemented model class. You can add additional
     *            imported classes to the implementation class if necessary.
     * @param introspectedColumn
     *            The class containing information about the column related
     *            to this field as introspected from the database
     * @param introspectedTable
     *            The class containing information about the table as
     *            introspected from the database
     * @param modelClassType
     *            the type of class that the field is generated for
     * @return true if the method should be generated, false if the generated
     *         method should be ignored. In the case of multiple plugins, the
     *         first plugin returning false will disable the calling of further
     *         plugins.
     */
    boolean modelGetterMethodGenerated(Method method,
                                       TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn,
                                       IntrospectedTable introspectedTable, ModelClassType modelClassType);

    /**
     * This method is called after the setter, or mutator, method is generated
     * for a specific column in a table.
     * 
     * @param method
     *            the setter, or mutator, method generated for the specified
     *            column
     * @param topLevelClass
     *            the partially implemented model class. You can add additional
     *            imported classes to the implementation class if necessary.
     * @param introspectedColumn
     *            The class containing information about the column related
     *            to this field as introspected from the database
     * @param introspectedTable
     *            The class containing information about the table as
     *            introspected from the database
     * @param modelClassType
     *            the type of class that the field is generated for
     * @return true if the method should be generated, false if the generated
     *         method should be ignored. In the case of multiple plugins, the
     *         first plugin returning false will disable the calling of further
     *         plugins.
     */
    boolean modelSetterMethodGenerated(Method method,
                                       TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn,
                                       IntrospectedTable introspectedTable, ModelClassType modelClassType);

    /**
     * This method is called after the primary key class is generated by the
     * JavaModelGenerator. This method will only be called if
     * the table rules call for generation of a primary key class.
     * <br><br>
     * This method is only guaranteed to be called by the Java
     * model generators. Other user supplied generators may, or may not, call
     * this method.
     * 
     * @param topLevelClass
     *            the generated primary key class
     * @param introspectedTable
     *            The class containing information about the table as
     *            introspected from the database
     * @return true if the class should be generated, false if the generated
     *         class should be ignored. In the case of multiple plugins, the
     *         first plugin returning false will disable the calling of further
     *         plugins.
     */
    boolean modelPrimaryKeyClassGenerated(TopLevelClass topLevelClass,
                                          IntrospectedTable introspectedTable);

    /**
     * This method is called after the base record class is generated by the
     * JavaModelGenerator. This method will only be called if
     * the table rules call for generation of a base record class.
     * <br><br>
     * This method is only guaranteed to be called by the default Java
     * model generators. Other user supplied generators may, or may not, call
     * this method.
     * 
     * @param topLevelClass
     *            the generated base record class
     * @param introspectedTable
     *            The class containing information about the table as
     *            introspected from the database
     * @return true if the class should be generated, false if the generated
     *         class should be ignored. In the case of multiple plugins, the
     *         first plugin returning false will disable the calling of further
     *         plugins.
     */
    boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass,
                                          IntrospectedTable introspectedTable);

    /**
     * This method is called after the record with BLOBs class is generated by
     * the JavaModelGenerator. This method will only be called
     * if the table rules call for generation of a record with BLOBs class.
     * <br><br>
     * This method is only guaranteed to be called by the default Java
     * model generators. Other user supplied generators may, or may not, call
     * this method.
     * 
     * @param topLevelClass
     *            the generated record with BLOBs class
     * @param introspectedTable
     *            The class containing information about the table as
     *            introspected from the database
     * @return true if the class should be generated, false if the generated
     *         class should be ignored. In the case of multiple plugins, the
     *         first plugin returning false will disable the calling of further
     *         plugins.
     */
    boolean modelRecordWithBLOBsClassGenerated(TopLevelClass topLevelClass,
                                               IntrospectedTable introspectedTable);

    /**
     * This method is called after the example class is generated by the 
     * JavaModelGenerator. This method will only be called if the table
     * rules call for generation of an example class.
     * <br><br>
     * This method is only guaranteed to be called by the default Java
     * model generators. Other user supplied generators may, or may not, call
     * this method.
     * 
     * @param topLevelClass
     *            the generated example class
     * @param introspectedTable
     *            The class containing information about the table as
     *            introspected from the database
     * @return true if the class should be generated, false if the generated
     *         class should be ignored. In the case of multiple plugins, the
     *         first plugin returning false will disable the calling of further
     *         plugins.
     */
    boolean modelExampleClassGenerated(TopLevelClass topLevelClass,
                                       IntrospectedTable introspectedTable);

    /**
     * This method is called when the SqlMap file has been generated.
     * 
     * @param sqlMap
     *            the generated file (containing the file name, package name,
     *            and project name)
     * @param introspectedTable
     *            The class containing information about the table as
     *            introspected from the database
     * @return true if the sqlMap should be generated, false if the generated
     *         sqlMap should be ignored. In the case of multiple plugins, the
     *         first plugin returning false will disable the calling of further
     *         plugins.
     */
    boolean sqlMapGenerated(GeneratedXmlFile sqlMap,
                            IntrospectedTable introspectedTable);


    boolean sqlMapDocumentGenerated(Document document,
                                    IntrospectedTable introspectedTable);

    /**
     * This method is called when the base resultMap is generated.
     * 
     * @param element
     *            the generated &lt;resultMap&gt; element
     * @param introspectedTable
     *            The class containing information about the table as
     *            introspected from the database
     * @return true if the element should be generated, false if the generated
     *         element should be ignored. In the case of multiple plugins, the
     *         first plugin returning false will disable the calling of further
     *         plugins.
     */
    boolean sqlMapResultMapWithoutBLOBsElementGenerated(XmlElement element,
                                                        IntrospectedTable introspectedTable);

    /**
     * This method is called when the countByExample element is generated.
     * 
     * @param element
     *            the generated &lt;select&gt; element
     * @param introspectedTable
     *            The class containing information about the table as
     *            introspected from the database
     * @return true if the element should be generated, false if the generated
     *         element should be ignored. In the case of multiple plugins, the
     *         first plugin returning false will disable the calling of further
     *         plugins.
     */
    boolean sqlMapCountByExampleElementGenerated(XmlElement element,
                                                 IntrospectedTable introspectedTable);

    /**
     * This method is called when the deleteByExample element is generated.
     * 
     * @param element
     *            the generated &lt;delete&gt; element
     * @param introspectedTable
     *            The class containing information about the table as
     *            introspected from the database
     * @return true if the element should be generated, false if the generated
     *         element should be ignored. In the case of multiple plugins, the
     *         first plugin returning false will disable the calling of further
     *         plugins.
     */
    boolean sqlMapDeleteByExampleElementGenerated(XmlElement element,
                                                  IntrospectedTable introspectedTable);

    /**
     * This method is called when the deleteByPrimaryKey element is generated.
     * 
     * @param element
     *            the generated &lt;delete&gt; element
     * @param introspectedTable
     *            The class containing information about the table as
     *            introspected from the database
     * @return true if the element should be generated, false if the generated
     *         element should be ignored. In the case of multiple plugins, the
     *         first plugin returning false will disable the calling of further
     *         plugins.
     */
    boolean sqlMapDeleteByPrimaryKeyElementGenerated(XmlElement element,
                                                     IntrospectedTable introspectedTable);

    /**
     * This method is called when the exampleWhereClause element is generated.
     * 
     * @param element
     *            the generated &lt;sql&gt; element
     * @param introspectedTable
     *            The class containing information about the table as
     *            introspected from the database
     * @return true if the element should be generated, false if the generated
     *         element should be ignored. In the case of multiple plugins, the
     *         first plugin returning false will disable the calling of further
     *         plugins.
     */
    boolean sqlMapExampleWhereClauseElementGenerated(XmlElement element,
                                                     IntrospectedTable introspectedTable);

    /**
     * This method is called when the baseColumnList element is generated.
     * 
     * @param element
     *            the generated &lt;sql&gt; element
     * @param introspectedTable
     *            The class containing information about the table as
     *            introspected from the database
     * @return true if the element should be generated, false if the generated
     *         element should be ignored. In the case of multiple plugins, the
     *         first plugin returning false will disable the calling of further
     *         plugins.
     */
    boolean sqlMapBaseColumnListElementGenerated(XmlElement element,
                                                 IntrospectedTable introspectedTable);

    /**
     * This method is called when the blobColumnList element is generated.
     * 
     * @param element
     *            the generated &lt;sql&gt; element
     * @param introspectedTable
     *            The class containing information about the table as
     *            introspected from the database
     * @return true if the element should be generated, false if the generated
     *         element should be ignored. In the case of multiple plugins, the
     *         first plugin returning false will disable the calling of further
     *         plugins.
     */
    boolean sqlMapBlobColumnListElementGenerated(XmlElement element,
                                                 IntrospectedTable introspectedTable);

    /**
     * This method is called when the insert element is generated.
     * 
     * @param element
     *            the generated &lt;insert&gt; element
     * @param introspectedTable
     *            The class containing information about the table as
     *            introspected from the database
     * @return true if the element should be generated, false if the generated
     *         element should be ignored. In the case of multiple plugins, the
     *         first plugin returning false will disable the calling of further
     *         plugins.
     */
    boolean sqlMapInsertElementGenerated(XmlElement element,
                                         IntrospectedTable introspectedTable);

    /**
     * This method is called when the insert selective element is generated.
     * 
     * @param element
     *            the generated &lt;insert&gt; element
     * @param introspectedTable
     *            The class containing information about the table as
     *            introspected from the database
     * @return true if the element should be generated, false if the generated
     *         element should be ignored. In the case of multiple plugins, the
     *         first plugin returning false will disable the calling of further
     *         plugins.
     */
    boolean sqlMapInsertSelectiveElementGenerated(XmlElement element,
                                                  IntrospectedTable introspectedTable);

    /**
     * This method is called when the resultMap with BLOBs element is generated
     * - this resultMap will extend the base resultMap.
     * 
     * @param element
     *            the generated &lt;resultMap&gt; element
     * @param introspectedTable
     *            The class containing information about the table as
     *            introspected from the database
     * @return true if the element should be generated, false if the generated
     *         element should be ignored. In the case of multiple plugins, the
     *         first plugin returning false will disable the calling of further
     *         plugins.
     */
    boolean sqlMapResultMapWithBLOBsElementGenerated(XmlElement element,
                                                     IntrospectedTable introspectedTable);

    /**
     * This method is called when the selectAll element is generated.
     * 
     * @param element
     *            the generated &lt;select&gt; element
     * @param introspectedTable
     *            The class containing information about the table as
     *            introspected from the database
     * @return true if the element should be generated, false if the generated
     *         element should be ignored. In the case of multiple plugins, the
     *         first plugin returning false will disable the calling of further
     *         plugins.
     */
    boolean sqlMapSelectAllElementGenerated(XmlElement element,
                                            IntrospectedTable introspectedTable);

    /**
     * This method is called when the selectByPrimaryKey element is generated.
     * 
     * @param element
     *            the generated &lt;select&gt; element
     * @param introspectedTable
     *            The class containing information about the table as
     *            introspected from the database
     * @return true if the element should be generated, false if the generated
     *         element should be ignored. In the case of multiple plugins, the
     *         first plugin returning false will disable the calling of further
     *         plugins.
     */
    boolean sqlMapSelectByPrimaryKeyElementGenerated(XmlElement element,
                                                     IntrospectedTable introspectedTable);


    /**
     * 条件查询
     * @param element
     * @param introspectedTable
     * @return
     */
    boolean sqlMapSelectByWhereElementGenerated(XmlElement element,
                                                     IntrospectedTable introspectedTable);

    /**
     * 批量插入
     * @param element
     * @param introspectedTable
     * @return
     */
    boolean sqlMapInsertBatchElementGenerated(XmlElement element,
                                                IntrospectedTable introspectedTable);

    /**
     * This method is called when the selectByExample element is generated.
     * 
     * @param element
     *            the generated &lt;select&gt; element
     * @param introspectedTable
     *            The class containing information about the table as
     *            introspected from the database
     * @return true if the element should be generated, false if the generated
     *         element should be ignored. In the case of multiple plugins, the
     *         first plugin returning false will disable the calling of further
     *         plugins.
     */
    boolean sqlMapSelectByExampleWithoutBLOBsElementGenerated(
            XmlElement element, IntrospectedTable introspectedTable);

    /**
     * This method is called when the selectByExampleWithBLOBs element is
     * generated.
     * 
     * @param element
     *            the generated &lt;select&gt; element
     * @param introspectedTable
     *            The class containing information about the table as
     *            introspected from the database
     * @return true if the element should be generated, false if the generated
     *         element should be ignored. In the case of multiple plugins, the
     *         first plugin returning false will disable the calling of further
     *         plugins.
     */
    boolean sqlMapSelectByExampleWithBLOBsElementGenerated(XmlElement element,
                                                           IntrospectedTable introspectedTable);

    /**
     * This method is called when the updateByExampleSelective element is
     * generated.
     * 
     * @param element
     *            the generated &lt;update&gt; element
     * @param introspectedTable
     *            The class containing information about the table as
     *            introspected from the database
     * @return true if the element should be generated, false if the generated
     *         element should be ignored. In the case of multiple plugins, the
     *         first plugin returning false will disable the calling of further
     *         plugins.
     */
    boolean sqlMapUpdateByExampleSelectiveElementGenerated(XmlElement element,
                                                           IntrospectedTable introspectedTable);

    /**
     * This method is called when the updateByExampleWithBLOBs element is
     * generated.
     * 
     * @param element
     *            the generated &lt;update&gt; element
     * @param introspectedTable
     *            The class containing information about the table as
     *            introspected from the database
     * @return true if the element should be generated, false if the generated
     *         element should be ignored. In the case of multiple plugins, the
     *         first plugin returning false will disable the calling of further
     *         plugins.
     */
    boolean sqlMapUpdateByExampleWithBLOBsElementGenerated(XmlElement element,
                                                           IntrospectedTable introspectedTable);

    /**
     * This method is called when the updateByExampleWithourBLOBs element is
     * generated.
     * 
     * @param element
     *            the generated &lt;update&gt; element
     * @param introspectedTable
     *            The class containing information about the table as
     *            introspected from the database
     * @return true if the element should be generated, false if the generated
     *         element should be ignored. In the case of multiple plugins, the
     *         first plugin returning false will disable the calling of further
     *         plugins.
     */
    boolean sqlMapUpdateByExampleWithoutBLOBsElementGenerated(
            XmlElement element, IntrospectedTable introspectedTable);

    /**
     * This method is called when the updateByPrimaryKeySelective element is
     * generated.
     * 
     * @param element
     *            the generated &lt;update&gt; element
     * @param introspectedTable
     *            The class containing information about the table as
     *            introspected from the database
     * @return true if the element should be generated, false if the generated
     *         element should be ignored. In the case of multiple plugins, the
     *         first plugin returning false will disable the calling of further
     *         plugins.
     */
    boolean sqlMapUpdateByPrimaryKeySelectiveElementGenerated(
            XmlElement element, IntrospectedTable introspectedTable);

    /**
     * This method is called when the updateByPrimaryKeyWithBLOBs element is
     * generated.
     * 
     * @param element
     *            the generated &lt;update&gt; element
     * @param introspectedTable
     *            The class containing information about the table as
     *            introspected from the database
     * @return true if the element should be generated, false if the generated
     *         element should be ignored. In the case of multiple plugins, the
     *         first plugin returning false will disable the calling of further
     *         plugins.
     */
    boolean sqlMapUpdateByPrimaryKeyWithBLOBsElementGenerated(
            XmlElement element, IntrospectedTable introspectedTable);

    /**
     * This method is called when the updateByPrimaryKeyWithoutBLOBs element is
     * generated.
     * 
     * @param element
     *            the generated &lt;update&gt; element
     * @param introspectedTable
     *            The class containing information about the table as
     *            introspected from the database
     * @return true if the element should be generated, false if the generated
     *         element should be ignored. In the case of multiple plugins, the
     *         first plugin returning false will disable the calling of further
     *         plugins.
     */
    boolean sqlMapUpdateByPrimaryKeyWithoutBLOBsElementGenerated(
            XmlElement element, IntrospectedTable introspectedTable);

    /**
     * This method is called when the SQL provider has been generated.
     * Implement this method to add additional methods or fields to a generated
     * SQL provider.
     * 
     * @param topLevelClass
     *            the generated provider
     * @param introspectedTable
     *            The class containing information about the table as
     *            introspected from the database
     * @return true if the provider should be generated, false if the generated
     *         provider should be ignored. In the case of multiple plugins, the
     *         first plugin returning false will disable the calling of further
     *         plugins.
     */
    boolean providerGenerated(TopLevelClass topLevelClass,
                              IntrospectedTable introspectedTable);

    /**
     * This method is called when the applyWhere method has
     * been generated in the SQL provider.
     * 
     * @param method
     *            the generated applyWhere method
     * @param topLevelClass
     *            the partially generated provider class
     *            You can add additional imported classes to the class
     *            if necessary.
     * @param introspectedTable
     *            The class containing information about the table as
     *            introspected from the database
     * @return true if the method should be generated, false if the generated
     *         method should be ignored. In the case of multiple plugins, the
     *         first plugin returning false will disable the calling of further
     *         plugins.
     */
    boolean providerApplyWhereMethodGenerated(Method method,
                                              TopLevelClass topLevelClass, IntrospectedTable introspectedTable);

    /**
     * This method is called when the countByExample method has
     * been generated in the SQL provider.
     * 
     * @param method
     *            the generated countByExample method
     * @param topLevelClass
     *            the partially generated provider class
     *            You can add additional imported classes to the class
     *            if necessary.
     * @param introspectedTable
     *            The class containing information about the table as
     *            introspected from the database
     * @return true if the method should be generated, false if the generated
     *         method should be ignored. In the case of multiple plugins, the
     *         first plugin returning false will disable the calling of further
     *         plugins.
     */
    boolean providerCountByExampleMethodGenerated(Method method,
                                                  TopLevelClass topLevelClass, IntrospectedTable introspectedTable);

    /**
     * This method is called when the deleteByExample method has
     * been generated in the SQL provider.
     * 
     * @param method
     *            the generated deleteByExample method
     * @param topLevelClass
     *            the partially generated provider class
     *            You can add additional imported classes to the class
     *            if necessary.
     * @param introspectedTable
     *            The class containing information about the table as
     *            introspected from the database
     * @return true if the method should be generated, false if the generated
     *         method should be ignored. In the case of multiple plugins, the
     *         first plugin returning false will disable the calling of further
     *         plugins.
     */
    boolean providerDeleteByExampleMethodGenerated(Method method,
                                                   TopLevelClass topLevelClass, IntrospectedTable introspectedTable);

    /**
     * This method is called when the insertSelective method has
     * been generated in the SQL provider.
     * 
     * @param method
     *            the generated insertSelective method
     * @param topLevelClass
     *            the partially generated provider class
     *            You can add additional imported classes to the class
     *            if necessary.
     * @param introspectedTable
     *            The class containing information about the table as
     *            introspected from the database
     * @return true if the method should be generated, false if the generated
     *         method should be ignored. In the case of multiple plugins, the
     *         first plugin returning false will disable the calling of further
     *         plugins.
     */
    boolean providerInsertSelectiveMethodGenerated(Method method,
                                                   TopLevelClass topLevelClass, IntrospectedTable introspectedTable);

    /**
     * This method is called when the selectByExampleWithBLOBs method has
     * been generated in the SQL provider.
     * 
     * @param method
     *            the generated selectByExampleWithBLOBs method
     * @param topLevelClass
     *            the partially generated provider class
     *            You can add additional imported classes to the class
     *            if necessary.
     * @param introspectedTable
     *            The class containing information about the table as
     *            introspected from the database
     * @return true if the method should be generated, false if the generated
     *         method should be ignored. In the case of multiple plugins, the
     *         first plugin returning false will disable the calling of further
     *         plugins.
     */
    boolean providerSelectByExampleWithBLOBsMethodGenerated(Method method,
                                                            TopLevelClass topLevelClass, IntrospectedTable introspectedTable);

    /**
     * This method is called when the selectByExampleWithoutBLOBs method has
     * been generated in the SQL provider.
     * 
     * @param method
     *            the generated selectByExampleWithoutBLOBs method
     * @param topLevelClass
     *            the partially generated provider class
     *            You can add additional imported classes to the class
     *            if necessary.
     * @param introspectedTable
     *            The class containing information about the table as
     *            introspected from the database
     * @return true if the method should be generated, false if the generated
     *         method should be ignored. In the case of multiple plugins, the
     *         first plugin returning false will disable the calling of further
     *         plugins.
     */
    boolean providerSelectByExampleWithoutBLOBsMethodGenerated(Method method,
                                                               TopLevelClass topLevelClass, IntrospectedTable introspectedTable);

    /**
     * This method is called when the updateByExampleSelective method has
     * been generated in the SQL provider.
     * 
     * @param method
     *            the generated updateByExampleSelective method
     * @param topLevelClass
     *            the partially generated provider class
     *            You can add additional imported classes to the class
     *            if necessary.
     * @param introspectedTable
     *            The class containing information about the table as
     *            introspected from the database
     * @return true if the method should be generated, false if the generated
     *         method should be ignored. In the case of multiple plugins, the
     *         first plugin returning false will disable the calling of further
     *         plugins.
     */
    boolean providerUpdateByExampleSelectiveMethodGenerated(Method method,
                                                            TopLevelClass topLevelClass, IntrospectedTable introspectedTable);

    /**
     * This method is called when the updateByExampleWithBLOBs method has
     * been generated in the SQL provider.
     * 
     * @param method
     *            the generated updateByExampleWithBLOBs method
     * @param topLevelClass
     *            the partially generated provider class
     *            You can add additional imported classes to the class
     *            if necessary.
     * @param introspectedTable
     *            The class containing information about the table as
     *            introspected from the database
     * @return true if the method should be generated, false if the generated
     *         method should be ignored. In the case of multiple plugins, the
     *         first plugin returning false will disable the calling of further
     *         plugins.
     */
    boolean providerUpdateByExampleWithBLOBsMethodGenerated(Method method,
                                                            TopLevelClass topLevelClass, IntrospectedTable introspectedTable);

    /**
     * This method is called when the updateByExampleWithoutBLOBs method has
     * been generated in the SQL provider.
     * 
     * @param method
     *            the generated updateByExampleWithoutBLOBs method
     * @param topLevelClass
     *            the partially generated provider class
     *            You can add additional imported classes to the class
     *            if necessary.
     * @param introspectedTable
     *            The class containing information about the table as
     *            introspected from the database
     * @return true if the method should be generated, false if the generated
     *         method should be ignored. In the case of multiple plugins, the
     *         first plugin returning false will disable the calling of further
     *         plugins.
     */
    boolean providerUpdateByExampleWithoutBLOBsMethodGenerated(Method method,
                                                               TopLevelClass topLevelClass, IntrospectedTable introspectedTable);

    /**
     * This method is called when the updateByPrimaryKeySelective method has
     * been generated in the SQL provider.
     * 
     * @param method
     *            the generated updateByPrimaryKeySelective method
     * @param topLevelClass
     *            the partially generated provider class
     *            You can add additional imported classes to the class
     *            if necessary.
     * @param introspectedTable
     *            The class containing information about the table as
     *            introspected from the database
     * @return true if the method should be generated, false if the generated
     *         method should be ignored. In the case of multiple plugins, the
     *         first plugin returning false will disable the calling of further
     *         plugins.
     */
    boolean providerUpdateByPrimaryKeySelectiveMethodGenerated(Method method,
                                                               TopLevelClass topLevelClass, IntrospectedTable introspectedTable);
}

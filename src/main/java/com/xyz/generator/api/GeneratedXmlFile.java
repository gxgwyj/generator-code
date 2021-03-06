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


import com.xyz.generator.api.dom.xml.Document;

/**
 * 生成xml文件的类
 */
public class GeneratedXmlFile extends GeneratedFile {
    
    private Document document;

    private String fileName;

    private String targetPackage;

    private boolean isMergeable;
    
    private XmlFormatter xmlFormatter;

    public GeneratedXmlFile(Document document, String fileName,
                            String targetPackage, String targetProject, boolean isMergeable,
                            XmlFormatter xmlFormatter) {
        super(targetProject);
        this.document = document;
        this.fileName = fileName;
        this.targetPackage = targetPackage;
        this.isMergeable = isMergeable;
        this.xmlFormatter = xmlFormatter;
    }

    /* (non-Javadoc)
     * @see com.xyz.generator.api.GeneratedFile#getFormattedContent()
     */
    @Override
    public String getFormattedContent() {
        return xmlFormatter.getFormattedContent(document);
    }

    @Override
    public String getFileName() {
        return fileName;
    }

    @Override
    public String getTargetPackage() {
        return targetPackage;
    }

    /* (non-Javadoc)
     * @see com.xyz.generator.api.GeneratedFile#isMergeable()
     */
    @Override
    public boolean isMergeable() {
        return isMergeable;
    }
}

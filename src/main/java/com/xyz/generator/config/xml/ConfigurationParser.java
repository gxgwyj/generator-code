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
/*
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.xyz.generator.config.xml;

import com.xyz.generator.codegen.XmlConstants;
import com.xyz.generator.config.Configuration;
import com.xyz.generator.exception.XMLParserException;
import com.xyz.generator.internal.util.messages.Messages;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


public class ConfigurationParser {


    private static final Logger logger = Logger.getLogger(ConfigurationParser.class);

    private List<String> warnings;
    private List<String> parseErrors;
    private Properties properties;

    public ConfigurationParser(List<String> warnings) {
        this(null, warnings);
    }

    public ConfigurationParser(Properties properties, List<String> warnings) {
        super();
        if (properties == null) {
            this.properties = System.getProperties();
        } else {
            this.properties = properties;
        }

        if (warnings == null) {
            this.warnings = new ArrayList<String>();
        } else {
            this.warnings = warnings;
        }

        parseErrors = new ArrayList<String>();
    }

    public Configuration parseConfiguration(File inputFile) throws IOException,
            XMLParserException {

        FileReader fr = new FileReader(inputFile);

        return parseConfiguration(fr);
    }

    public Configuration parseConfiguration(Reader reader) throws IOException,
            XMLParserException {

        InputSource is = new InputSource(reader);

        return parseConfiguration(is);
    }

    public Configuration parseConfiguration(InputStream inputStream)
            throws IOException, XMLParserException {

        InputSource is = new InputSource(inputStream);

        return parseConfiguration(is);
    }

    private Configuration parseConfiguration(InputSource inputSource)
            throws IOException, XMLParserException {
        parseErrors.clear();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setValidating(true);

        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            // 设置xml DTD 验证
            builder.setEntityResolver(new ParserEntityResolver());


            ParserErrorHandler handler = new ParserErrorHandler(warnings,
                    parseErrors);
            builder.setErrorHandler(handler);

            Document document = null;
            try {
                document = builder.parse(inputSource);
                //打印配置文件的内容
                Doc2Str(document);
            } catch (SAXParseException e) {
                throw new XMLParserException(parseErrors);
            } catch (SAXException e) {
                if (e.getException() == null) {
                    parseErrors.add(e.getMessage());
                } else {
                    parseErrors.add(e.getException().getMessage());
                }
            }

            if (parseErrors.size() > 0) {
                throw new XMLParserException(parseErrors);
            }

            Configuration config;
            //获得xml的根节点
            Element rootNode = document.getDocumentElement();
            DocumentType docType = document.getDoctype();
            if (rootNode.getNodeType() == Node.ELEMENT_NODE
                    && docType.getPublicId().equals(
                            XmlConstants.IBATOR_CONFIG_PUBLIC_ID)) {
                config = parseIbatorConfiguration(rootNode);
            } else if (rootNode.getNodeType() == Node.ELEMENT_NODE
                    && docType.getPublicId().equals(
                            XmlConstants.MYBATIS_GENERATOR_CONFIG_PUBLIC_ID)) {
                config = parseMyBatisGeneratorConfiguration(rootNode);
            } else {
                throw new XMLParserException(Messages.getString("RuntimeError.5"));
            }

            if (parseErrors.size() > 0) {
                throw new XMLParserException(parseErrors);
            }

            return config;
        } catch (ParserConfigurationException e) {
            parseErrors.add(e.getMessage());
            throw new XMLParserException(parseErrors);
        }
    }

    private Configuration parseIbatorConfiguration(Element rootNode)
            throws XMLParserException {
        IbatorConfigurationParser parser = new IbatorConfigurationParser(
                properties);
        return parser.parseIbatorConfiguration(rootNode);
    }

    private Configuration parseMyBatisGeneratorConfiguration(Element rootNode)
            throws XMLParserException {
        MyBatisGeneratorConfigurationParser parser = new MyBatisGeneratorConfigurationParser(
                properties);
        return parser.parseConfiguration(rootNode);
    }

    private void Doc2Str(Document document){
        TransformerFactory tf   =   TransformerFactory.newInstance();
        try {
            Transformer t = tf.newTransformer();
            //解决中文问题，试过用GBK不行
            t.setOutputProperty("encoding","UTF-8");
            ByteArrayOutputStream   bos   =   new   ByteArrayOutputStream();
            t.transform(new DOMSource(document), new StreamResult(bos));
            String xmlStr = bos.toString();
            logger.info("读取到的mybatis生成代码配置文件：\n" + xmlStr);
        } catch (TransformerConfigurationException e){
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }

    }
}

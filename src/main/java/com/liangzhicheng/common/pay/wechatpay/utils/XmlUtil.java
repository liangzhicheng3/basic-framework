package com.liangzhicheng.common.pay.wechatpay.utils;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.StringReader;

/**
 * @description Xml工具类
 * @author liangzhicheng
 * @since 2020-08-12
 */
public class XmlUtil {

    /**
     * @description 把String转成Document对象
     * @param xml
     * @return Document
     */
    public static Document parseXMLDocument(String xml) {
        if (xml == null) {
            throw new IllegalArgumentException();
        }
        try {
            return newDocumentBuilder().parse(new InputSource(new StringReader(xml)));
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * @description 创建一个DocumentBuilder实例
     * @return DocumentBuilder
     * @throws ParserConfigurationException DocumentBuilder
     */
    public static DocumentBuilder newDocumentBuilder() throws ParserConfigurationException {
        return newDocumentBuilderFactory().newDocumentBuilder();
    }

    /**
     * @description 创建一个DocumentBuilderFactory实例
     * @return DocumentBuilderFactory
     */
    public static DocumentBuilderFactory newDocumentBuilderFactory() {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(true);
        return dbf;
    }

}

package com.blue_core.core.io;

import com.blue_core.core.io.XmlParser;
import org.junit.Test;
import org.w3c.dom.*;

/**
 * @Author Jason
 * @CreationDate 2023/01/08 - 20:50
 * @Description ：
 */
@Deprecated
public class XmlParseTest {
    @Test
    public void readXmlTest1(){
        XmlParser xmlParser = new XmlParser();
        Document document = xmlParser.readXml("src/main/resources/test.xml");
        NodeList bean = document.getElementsByTagName("bean");
        System.out.println(bean.item(0).getAttributes().getNamedItem("id"));
        System.out.println(bean.item(1).getAttributes().getNamedItem("id"));
    }

    @Test
    public void readXmlTest2(){
        XmlParser xmlParser = new XmlParser();
        Document document = xmlParser.readXml("src/main/resources/test.xml");
        NodeList elements = document.getElementsByTagName("component-scan");
        System.out.println(elements.item(0).getAttributes().getNamedItem("basePackage").getNodeValue());
    }

    @Test
    public void readXmlTest3(){
        XmlParser xmlParser = new XmlParser();
        Document document = xmlParser.readXml("src/main/resources/test.xml");
        Element element = document.getDocumentElement();
        System.out.println(element.getTagName());
        System.out.println(element.getAttribute("id"));
        System.out.println(element.getElementsByTagName("bean").getLength());
    }

    @Test
    public void readXmlTest4(){
        XmlParser xmlParser = new XmlParser();
        Document document = xmlParser.readXml("src/main/resources/test.xml");
        NodeList elements = document.getElementsByTagName("bean");
        Node item = elements.item(1);
        System.out.println(item.getNodeName());
        System.out.println("1----------");
        NamedNodeMap attributes = item.getAttributes();
        System.out.println(attributes.getNamedItem("id"));
        System.out.println(attributes.getNamedItem("class"));
        System.out.println("2----------");
        NodeList childNodes = item.getChildNodes();
        System.out.println("item.getChildNodes() = "+item.getChildNodes().getLength());
        for (int i = 0; i < childNodes.getLength(); i++) {
            System.out.println(childNodes.item(i).getNodeName());
            System.out.println(childNodes.item(i).getNodeValue());
            System.out.println(childNodes.item(i).getAttributes());
            System.out.println("3----------");
        }
    }
}

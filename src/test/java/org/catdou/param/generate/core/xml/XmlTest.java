package org.catdou.param.generate.core.xml;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

/**
 * @author James
 */
public class XmlTest {

    @Test
    public void testGenerateXml() throws ParserConfigurationException, TransformerException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = factory.newDocumentBuilder();
        Document document = documentBuilder.newDocument();
        document.setXmlStandalone(true);
        Element users = document.createElement("users");
        users.setAttribute("type", "list");
        Element user = document.createElement("user");
        user.setAttribute("name", "scd");
        user.setAttribute("sex","male");
        users.appendChild(user);
        Element user2 = document.createElement("user");
        user2.setAttribute("name", "chengdu");
        user2.setAttribute("sex", "male");
        users.appendChild(user2);
        Element common = document.createElement("common");
        Element text = document.createElement("text");
        text.setTextContent("文本");
        common.appendChild(text);
        user.appendChild(common);
        document.appendChild(users);
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer tf = transformerFactory.newTransformer();
        tf.setOutputProperty(OutputKeys.INDENT, "yes");
        tf.setOutputProperty(OutputKeys.STANDALONE, "yes");
        tf.transform(new DOMSource(document), new StreamResult(new File("xml/users.xml")));
    }
}

package org.freerealm.xmlwrapper;

import java.util.Enumeration;
import java.util.Properties;
import org.w3c.dom.Node;

/**
 *
 * @author Deniz ARIKAN
 */
public class XMLConverterUtility {

    public static Properties convertNodeToProperties(Node node) {
        Properties properties = new Properties();
        for (Node propertyNode = node.getFirstChild(); propertyNode != null; propertyNode = propertyNode.getNextSibling()) {
            if (propertyNode.getNodeType() == Node.ELEMENT_NODE) {
                properties.put(propertyNode.getNodeName(), propertyNode.getFirstChild().getNodeValue());
            }
        }
        return properties;
    }

    public static String convertPropertiesToXML(Properties properties, String rootNodeName) {
        StringBuilder xml = new StringBuilder();
        xml.append("<" + rootNodeName + ">\n");
        if (properties != null) {
            Enumeration keysEnumeration = properties.keys();
            while (keysEnumeration.hasMoreElements()) {
                Object key = keysEnumeration.nextElement();
                xml.append("<" + key + ">");
                xml.append(properties.get(key));
                xml.append("</" + key + ">\n");
            }
        }
        xml.append("</" + rootNodeName + ">");
        return xml.toString();
    }

    public static String convertPropertiesToXML(Properties properties) {
        return convertPropertiesToXML(properties, "properties");
    }

    public static Node findNode(Node node, String nodeName) {
        int length = node.getChildNodes().getLength();
        for (int i = 0; i < length; i++) {
            Node subNode = node.getChildNodes().item(i);
            if (subNode.getNodeType() == Node.ELEMENT_NODE) {
                if (nodeName.equals(subNode.getNodeName())) {
                    return subNode;
                }
            }
        }
        return null;
    }
}

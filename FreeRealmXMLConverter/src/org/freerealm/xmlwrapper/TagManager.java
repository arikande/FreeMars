package org.freerealm.xmlwrapper;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Iterator;
import org.apache.xerces.parsers.DOMParser;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class TagManager {

    private static final Hashtable<String, TagData> tagTable = new Hashtable<String, TagData>();
    private static final Hashtable<String, Object> objectPool = new Hashtable<String, Object>();

    
    public static void readDefaultTags() {
        readFile("config/free_realm_tags.xml");
    }
    
    public static void readFile(String fileName) {
        DOMParser builder = new DOMParser();
        BufferedInputStream bufferedInputStream = new BufferedInputStream(ClassLoader.getSystemResourceAsStream(fileName));
        InputSource inputSource = new InputSource(bufferedInputStream);
        try {
            builder.parse(inputSource);
        } catch (SAXException ex) {
        } catch (IOException ex) {
        }
        NodeList nodeList = builder.getDocument().getElementsByTagName("tag");
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            String id = node.getAttributes().getNamedItem("id").getNodeValue();
            String classValue = null;
            String xmlConverterValue = null;
            for (Node subNode = node.getFirstChild(); subNode != null; subNode = subNode.getNextSibling()) {
                if (subNode.getNodeType() == Node.ELEMENT_NODE) {
                    if (subNode.getNodeName().equals("class")) {
                        classValue = subNode.getFirstChild().getNodeValue();
                    } else if (subNode.getNodeName().equals("xmlConverter")) {
                        if (subNode.getFirstChild() != null) {
                            xmlConverterValue = subNode.getFirstChild().getNodeValue();
                        }
                    }
                }
            }
            TagData tag = new TagData(id, classValue, xmlConverterValue);
            tagTable.put(id, tag);
        }
    }

    public static int getTagCount() {
        return tagTable.size();
    }

    public static String getClassName(String tagName) {
        return tagTable.get(tagName).getClassName();
    }

    public static String getXMLConverterName(String tagName) {
        TagData tagData = tagTable.get(tagName);
        if (tagData != null) {
            return tagTable.get(tagName).getXMLConverterName();
        } else {
            System.out.println("Tag data for " + tagName + " is null.");
            return null;
        }
    }

    public static String getXMLConverterName(Class c) {
        Iterator<TagData> iterator = tagTable.values().iterator();
        while (iterator.hasNext()) {
            TagData checkTagData = iterator.next();
            if (checkTagData.getClassName().equals(c.getName())) {
                return checkTagData.getXMLConverterName();
            }
        }
        return null;
    }

    public static Object getObjectFromPool(String objectName) {
        return objectPool.get(objectName);
    }

    public static void setObjectInPool(String objectName, Object object) {
        objectPool.put(objectName, object);
    }
}

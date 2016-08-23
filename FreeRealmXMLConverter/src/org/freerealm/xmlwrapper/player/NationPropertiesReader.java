package org.freerealm.xmlwrapper.player;

import java.io.BufferedInputStream;
import java.io.IOException;
import org.apache.xerces.parsers.DOMParser;
import org.freerealm.Realm;
import org.freerealm.xmlwrapper.PropertyFactory;
import org.freerealm.xmlwrapper.XMLConverterUtility;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 *
 * @author Deniz ARIKAN
 */
public class NationPropertiesReader {

    public void readNationProperties(Realm realm, String fileName) {
        DOMParser builder = new DOMParser();
        BufferedInputStream bufferedInputStream = new BufferedInputStream(ClassLoader.getSystemResourceAsStream(fileName));
        InputSource inputSource = new InputSource(bufferedInputStream);
        try {
            builder.parse(inputSource);
        } catch (SAXException exception) {
            exception.printStackTrace();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        Node nationPropertiesNode = builder.getDocument().getFirstChild();
        for (Node nationNode = nationPropertiesNode.getFirstChild(); nationNode != null; nationNode = nationNode.getNextSibling()) {
            if (nationNode.getNodeType() == Node.ELEMENT_NODE) {
                int nationId = Integer.parseInt(XMLConverterUtility.findNode(nationNode, "id").getFirstChild().getNodeValue());
                Node propertiesNode = XMLConverterUtility.findNode(nationNode, "properties");
                for (Node propertyNode = propertiesNode.getFirstChild(); propertyNode != null; propertyNode = propertyNode.getNextSibling()) {
                    if (propertyNode.getNodeType() == Node.ELEMENT_NODE) {
                        realm.getNationManager().getNation(nationId).addProperty(PropertyFactory.createProperty(realm, propertyNode));
                    }
                }
            }
        }
    }
}

package org.freerealm.xmlwrapper.map;

import java.util.Iterator;
import org.freerealm.Realm;
import org.freerealm.property.Property;
import org.freerealm.tile.FreeRealmTileModifier;
import org.freerealm.xmlwrapper.PropertyFactory;
import org.freerealm.xmlwrapper.TagManager;
import org.freerealm.xmlwrapper.XMLConverter;
import org.freerealm.xmlwrapper.XMLConverterUtility;
import org.w3c.dom.Node;

/**
 *
 * @author Deniz ARIKAN
 */
public class FreeRealmTileModifierXMLConverter implements XMLConverter<FreeRealmTileModifier> {

    public String toXML(FreeRealmTileModifier freeRealmTileModifier) {
        StringBuilder xml = new StringBuilder();
        xml.append("<FreeRealmTileModifier>\n");
        xml.append("<Properties>\n");
        Iterator<Property> propertiesIterator = freeRealmTileModifier.getPropertiesIterator();
        while (propertiesIterator.hasNext()) {
            Property property = propertiesIterator.next();
            String xMLConverterName = TagManager.getXMLConverterName(property.getName());
            Class c;
            try {
                c = Class.forName(xMLConverterName);
                XMLConverter<Property> xMLConverter = (XMLConverter<Property>) c.newInstance();
                xml.append(xMLConverter.toXML(property) + "\n");
            } catch (Exception ex) {
            }
        }
        xml.append("</Properties>\n");
        xml.append("</FreeRealmTileModifier>");
        return xml.toString();
    }

    public FreeRealmTileModifier initializeFromNode(Realm realm, Node node) {
        FreeRealmTileModifier freeRealmTileModifier = new FreeRealmTileModifier();
        Node propertiesNode = XMLConverterUtility.findNode(node, "Properties");
        for (Node propertyNode = propertiesNode.getFirstChild(); propertyNode != null; propertyNode = propertyNode.getNextSibling()) {
            if (propertyNode.getNodeType() == Node.ELEMENT_NODE) {
                Property property = PropertyFactory.createProperty(realm, propertyNode);
                freeRealmTileModifier.addProperty(property);
            }
        }
        return freeRealmTileModifier;
    }
}

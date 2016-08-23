package org.freerealm.xmlwrapper;

import org.freerealm.Realm;
import org.freerealm.property.Property;
import org.w3c.dom.Node;

/**
 *
 * @author Deniz ARIKAN
 */
public class PropertyFactory {

    public static Property createProperty(Realm realm, Node node) {
        Property property = null;
        String xMLConverterName = TagManager.getXMLConverterName(node.getNodeName());
        try {
            XMLConverter<Property> xMLConverter = (XMLConverter<Property>) Class.forName(xMLConverterName).newInstance();
            property = xMLConverter.initializeFromNode(realm, node);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return property;
    }
}

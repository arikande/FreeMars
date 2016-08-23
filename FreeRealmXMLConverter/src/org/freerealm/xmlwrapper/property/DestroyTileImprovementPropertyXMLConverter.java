package org.freerealm.xmlwrapper.property;

import org.freerealm.Realm;
import org.freerealm.property.DestroyTileImprovementProperty;
import org.freerealm.xmlwrapper.XMLConverter;
import org.w3c.dom.Node;

/**
 *
 * @author Deniz ARIKAN
 */
public class DestroyTileImprovementPropertyXMLConverter implements XMLConverter<DestroyTileImprovementProperty> {

    public String toXML(DestroyTileImprovementProperty destroyTileImprovementProperty) {
        StringBuilder xml = new StringBuilder();
        xml.append("<DestroyTileImprovementProperty/>");
        return xml.toString();
    }

    public DestroyTileImprovementProperty initializeFromNode(Realm realm, Node node) {
        DestroyTileImprovementProperty destroyTileImprovementProperty = new DestroyTileImprovementProperty();
        return destroyTileImprovementProperty;
    }
}

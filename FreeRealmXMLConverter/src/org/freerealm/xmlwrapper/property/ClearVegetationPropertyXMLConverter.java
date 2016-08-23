package org.freerealm.xmlwrapper.property;

import org.freerealm.Realm;
import org.freerealm.property.ClearVegetationProperty;
import org.freerealm.xmlwrapper.XMLConverter;
import org.w3c.dom.Node;

/**
 *
 * @author Deniz ARIKAN
 */
public class ClearVegetationPropertyXMLConverter implements XMLConverter<ClearVegetationProperty> {

    public String toXML(ClearVegetationProperty clearVegetation) {
        StringBuilder xml = new StringBuilder();
        xml.append("<" + ClearVegetationProperty.NAME + "/>");
        return xml.toString();
    }

    public ClearVegetationProperty initializeFromNode(Realm realm, Node node) {
        return new ClearVegetationProperty();
    }
}

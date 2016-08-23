package org.freerealm.xmlwrapper.property;

import org.freerealm.Realm;
import org.freerealm.property.BuildSettlementProperty;
import org.freerealm.xmlwrapper.XMLConverter;
import org.w3c.dom.Node;

/**
 *
 * @author Deniz ARIKAN
 */
public class BuildSettlementPropertyXMLConverter implements XMLConverter<BuildSettlementProperty> {

    public String toXML(BuildSettlementProperty buildSettlementProperty) {
        StringBuilder xml = new StringBuilder();
        xml.append("<" + BuildSettlementProperty.NAME + "/>");
        return xml.toString();
    }

    public BuildSettlementProperty initializeFromNode(Realm realm, Node node) {
        return new BuildSettlementProperty();
    }
}

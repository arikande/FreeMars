package org.freerealm.xmlwrapper.property;

import org.freerealm.Realm;
import org.freerealm.property.RemoveSettlementImprovementProperty;
import org.freerealm.xmlwrapper.XMLConverter;
import org.w3c.dom.Node;

/**
 *
 * @author Deniz ARIKAN
 */
public class RemoveSettlementImprovementPropertyXMLConverter implements XMLConverter<RemoveSettlementImprovementProperty> {

    public String toXML(RemoveSettlementImprovementProperty removeSettlementImprovement) {
        StringBuilder xml = new StringBuilder();
        xml.append("<" + RemoveSettlementImprovementProperty.NAME + ">");
        xml.append(removeSettlementImprovement.getSettlementImprovementId());
        xml.append("</" + RemoveSettlementImprovementProperty.NAME + ">");
        return xml.toString();
    }

    public RemoveSettlementImprovementProperty initializeFromNode(Realm realm, Node node) {
        RemoveSettlementImprovementProperty removeSettlementImprovement = new RemoveSettlementImprovementProperty();
        int settlementImprovementId = Integer.parseInt(node.getFirstChild().getNodeValue());
        removeSettlementImprovement.setSettlementImprovementId(settlementImprovementId);
        return removeSettlementImprovement;
    }
}

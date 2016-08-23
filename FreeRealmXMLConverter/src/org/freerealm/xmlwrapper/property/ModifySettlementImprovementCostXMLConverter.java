package org.freerealm.xmlwrapper.property;

import org.freerealm.Realm;
import org.freerealm.property.ModifySettlementImprovementCost;
import org.freerealm.xmlwrapper.XMLConverter;
import org.freerealm.xmlwrapper.XMLConverterUtility;
import org.w3c.dom.Node;

/**
 *
 * @author Deniz ARIKAN
 */
public class ModifySettlementImprovementCostXMLConverter implements XMLConverter<ModifySettlementImprovementCost> {

    public String toXML(ModifySettlementImprovementCost modifySettlementImprovementCost) {
        StringBuilder xml = new StringBuilder();
        xml.append("<ModifySettlementImprovementCost>\n");
        xml.append("<modifier>" + modifySettlementImprovementCost.getModifier() + "</modifier>\n");
        xml.append("</ModifySettlementImprovementCost>");
        return xml.toString();
    }

    public ModifySettlementImprovementCost initializeFromNode(Realm realm, Node node) {
        ModifySettlementImprovementCost modifySettlementImprovementCost = new ModifySettlementImprovementCost();
        modifySettlementImprovementCost.setModifier(Integer.parseInt(XMLConverterUtility.findNode(node, "modifier").getFirstChild().getNodeValue()));
        return modifySettlementImprovementCost;
    }
}

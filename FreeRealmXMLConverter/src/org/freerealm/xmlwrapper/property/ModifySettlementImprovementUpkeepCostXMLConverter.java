package org.freerealm.xmlwrapper.property;

import org.freerealm.Realm;
import org.freerealm.property.ModifySettlementImprovementUpkeepCost;
import org.freerealm.xmlwrapper.XMLConverter;
import org.freerealm.xmlwrapper.XMLConverterUtility;
import org.w3c.dom.Node;

/**
 *
 * @author Deniz ARIKAN
 */
public class ModifySettlementImprovementUpkeepCostXMLConverter implements XMLConverter<ModifySettlementImprovementUpkeepCost> {

    public String toXML(ModifySettlementImprovementUpkeepCost modifySettlementImprovementUpkeepCost) {
        StringBuilder xml = new StringBuilder();
        xml.append("<ModifySettlementImprovementUpkeepCost>\n");
        xml.append("<modifier>" + modifySettlementImprovementUpkeepCost.getModifier() + "</modifier>\n");
        xml.append("</ModifySettlementImprovementUpkeepCost>");
        return xml.toString();
    }

    public ModifySettlementImprovementUpkeepCost initializeFromNode(Realm realm, Node node) {
        ModifySettlementImprovementUpkeepCost modifySettlementImprovementUpkeepCost = new ModifySettlementImprovementUpkeepCost();
        modifySettlementImprovementUpkeepCost.setModifier(Integer.parseInt(XMLConverterUtility.findNode(node, "modifier").getFirstChild().getNodeValue()));
        return modifySettlementImprovementUpkeepCost;
    }
}

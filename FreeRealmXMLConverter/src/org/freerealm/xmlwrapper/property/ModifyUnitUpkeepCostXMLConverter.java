package org.freerealm.xmlwrapper.property;

import org.freerealm.Realm;
import org.freerealm.property.ModifyUnitUpkeepCost;
import org.freerealm.xmlwrapper.XMLConverter;
import org.freerealm.xmlwrapper.XMLConverterUtility;
import org.w3c.dom.Node;

/**
 *
 * @author Deniz ARIKAN
 */
public class ModifyUnitUpkeepCostXMLConverter implements XMLConverter<ModifyUnitUpkeepCost> {

    public String toXML(ModifyUnitUpkeepCost modifyUnitUpkeepCost) {
        StringBuilder xml = new StringBuilder();
        xml.append("<ModifyUnitUpkeepCost>\n");
        xml.append("<modifier>" + modifyUnitUpkeepCost.getModifier() + "</modifier>\n");
        xml.append("</ModifyUnitUpkeepCost>");
        return xml.toString();
    }

    public ModifyUnitUpkeepCost initializeFromNode(Realm realm, Node node) {
        ModifyUnitUpkeepCost modifyUnitUpkeepCost = new ModifyUnitUpkeepCost();
        modifyUnitUpkeepCost.setModifier(Integer.parseInt(XMLConverterUtility.findNode(node, "modifier").getFirstChild().getNodeValue()));
        return modifyUnitUpkeepCost;
    }
}

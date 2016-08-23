package org.freerealm.xmlwrapper.property;

import org.freerealm.Realm;
import org.freerealm.property.ModifyUnitCost;
import org.freerealm.xmlwrapper.XMLConverter;
import org.freerealm.xmlwrapper.XMLConverterUtility;
import org.w3c.dom.Node;

/**
 *
 * @author Deniz ARIKAN
 */
public class ModifyUnitCostXMLConverter implements XMLConverter<ModifyUnitCost> {

    public String toXML(ModifyUnitCost modifyUnitCost) {
        StringBuilder xml = new StringBuilder();
        xml.append("<ModifyUnitCost>\n");
        xml.append("<modifier>" + modifyUnitCost.getModifier() + "</modifier>\n");
        xml.append("</ModifyUnitCost>");
        return xml.toString();
    }

    public ModifyUnitCost initializeFromNode(Realm realm, Node node) {
        ModifyUnitCost modifyUnitCost = new ModifyUnitCost();
        modifyUnitCost.setModifier(Integer.parseInt(XMLConverterUtility.findNode(node, "modifier").getFirstChild().getNodeValue()));
        return modifyUnitCost;
    }
}

package org.freerealm.xmlwrapper.property;

import org.freerealm.Realm;
import org.freerealm.property.ModifyRequiredPopulationResourceAmount;
import org.freerealm.xmlwrapper.XMLConverter;
import org.freerealm.xmlwrapper.XMLConverterUtility;
import org.w3c.dom.Node;

/**
 *
 * @author Deniz ARIKAN
 */
public class ModifyRequiredPopulationResourceAmountXMLConverter implements XMLConverter<ModifyRequiredPopulationResourceAmount> {

    public String toXML(ModifyRequiredPopulationResourceAmount modifyRequiredPopulationResourceAmount) {
        StringBuilder xml = new StringBuilder();
        xml.append("<ModifyRequiredPopulationResourceAmount>\n");
        xml.append("<resourceId>" + modifyRequiredPopulationResourceAmount.getResourceId() + "</resourceId>\n");
        xml.append("<modifier>" + modifyRequiredPopulationResourceAmount.getModifier() + "</modifier>\n");
        xml.append("</ModifyRequiredPopulationResourceAmount>");
        return xml.toString();
    }

    public ModifyRequiredPopulationResourceAmount initializeFromNode(Realm realm, Node node) {
        ModifyRequiredPopulationResourceAmount modifyRequiredPopulationResourceAmount = new ModifyRequiredPopulationResourceAmount();
        modifyRequiredPopulationResourceAmount.setResourceId(Integer.parseInt(XMLConverterUtility.findNode(node, "resourceId").getFirstChild().getNodeValue()));
        modifyRequiredPopulationResourceAmount.setModifier(Integer.parseInt(XMLConverterUtility.findNode(node, "modifier").getFirstChild().getNodeValue()));
        return modifyRequiredPopulationResourceAmount;
    }
}

package org.freemars.earth;

import org.freerealm.Realm;
import org.freerealm.xmlwrapper.XMLConverter;
import org.freerealm.xmlwrapper.XMLConverterUtility;
import org.w3c.dom.Node;

/**
 *
 * @author Deniz ARIKAN
 */
public class ModifyHydrogenConsumptionXMLConverter implements XMLConverter<ModifyHydrogenConsumption> {

    public String toXML(ModifyHydrogenConsumption modifyHydrogenConsumption) {
        StringBuilder xml = new StringBuilder();
        xml.append("<ModifyHydrogenConsumption>\n");
        xml.append("<modifier>" + modifyHydrogenConsumption.getModifier() + "</modifier>\n");
        xml.append("</ModifyHydrogenConsumption>");
        return xml.toString();
    }

    public ModifyHydrogenConsumption initializeFromNode(Realm realm, Node node) {
        ModifyHydrogenConsumption modifyHydrogenConsumption = new ModifyHydrogenConsumption();
        modifyHydrogenConsumption.setModifier(Integer.parseInt(XMLConverterUtility.findNode(node, "modifier").getFirstChild().getNodeValue()));
        return modifyHydrogenConsumption;
    }
}

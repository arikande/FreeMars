package org.freemars.earth;

import org.freerealm.Realm;
import org.freerealm.xmlwrapper.XMLConverter;
import org.freerealm.xmlwrapper.XMLConverterUtility;
import org.w3c.dom.Node;

/**
 *
 * @author Deniz ARIKAN
 */
public class ModifyFinanceColonizerCostXMLConverter implements XMLConverter<ModifyFinanceColonizerCost> {

    public String toXML(ModifyFinanceColonizerCost modifyFinanceColonizerCost) {
        StringBuilder xml = new StringBuilder();
        xml.append("<ModifyFinanceColonizerCost>\n");
        xml.append("<modifier>" + modifyFinanceColonizerCost.getModifier() + "</modifier>\n");
        xml.append("</ModifyFinanceColonizerCost>");
        return xml.toString();
    }

    public ModifyFinanceColonizerCost initializeFromNode(Realm realm, Node node) {
        ModifyFinanceColonizerCost modifyFinanceColonizerCost = new ModifyFinanceColonizerCost();
        modifyFinanceColonizerCost.setModifier(Integer.parseInt(XMLConverterUtility.findNode(node, "modifier").getFirstChild().getNodeValue()));
        return modifyFinanceColonizerCost;
    }
}

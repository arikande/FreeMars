package org.freerealm.xmlwrapper.property;

import org.freerealm.Realm;
import org.freerealm.property.ModifyStartingWealth;
import org.freerealm.xmlwrapper.XMLConverter;
import org.freerealm.xmlwrapper.XMLConverterUtility;
import org.w3c.dom.Node;

/**
 *
 * @author Deniz ARIKAN
 */
public class ModifyStartingWealthXMLConverter implements XMLConverter<ModifyStartingWealth> {

    public String toXML(ModifyStartingWealth modifyStartingWealth) {
        StringBuilder xml = new StringBuilder();
        xml.append("<ModifyStartingWealth>\n");
        xml.append("<modifier>" + modifyStartingWealth.getModifier() + "</modifier>\n");
        xml.append("</ModifyStartingWealth>");
        return xml.toString();
    }

    public ModifyStartingWealth initializeFromNode(Realm realm, Node node) {
        ModifyStartingWealth modifyStartingWealth = new ModifyStartingWealth();
        modifyStartingWealth.setModifier(Integer.parseInt(XMLConverterUtility.findNode(node, "modifier").getFirstChild().getNodeValue()));
        return modifyStartingWealth;
    }
}

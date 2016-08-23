package org.freerealm.xmlwrapper.property;

import org.freerealm.Realm;
import org.freerealm.property.ModifyProduction;
import org.freerealm.xmlwrapper.XMLConverter;
import org.freerealm.xmlwrapper.XMLConverterUtility;
import org.w3c.dom.Node;

/**
 *
 * @author Deniz ARIKAN
 */
public class ModifyProductionXMLConverter implements XMLConverter<ModifyProduction> {

    public String toXML(ModifyProduction modifyProduction) {
        StringBuilder xml = new StringBuilder();
        xml.append("<ModifyProduction>\n");
        xml.append("<modifier>" + modifyProduction.getModifier() + "</modifier>\n");
        xml.append("</ModifyProduction>");
        return xml.toString();
    }

    public ModifyProduction initializeFromNode(Realm realm, Node node) {
        ModifyProduction modifyProduction = new ModifyProduction();
        modifyProduction.setModifier(Integer.parseInt(XMLConverterUtility.findNode(node, "modifier").getFirstChild().getNodeValue()));
        return modifyProduction;
    }
}

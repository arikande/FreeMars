package org.freerealm.xmlwrapper.property;

import org.freerealm.Realm;
import org.freerealm.property.ModifyResourceProduction;
import org.freerealm.xmlwrapper.XMLConverter;
import org.freerealm.xmlwrapper.XMLConverterUtility;
import org.w3c.dom.Node;

/**
 *
 * @author Deniz ARIKAN
 */
public class ModifyResourceProductionXMLConverter implements XMLConverter<ModifyResourceProduction> {

    public String toXML(ModifyResourceProduction modifyResourceProduction) {
        StringBuilder xml = new StringBuilder();
        xml.append("<modifyResourceProduction>\n");
        xml.append("<resource>" + modifyResourceProduction.getResource().getName() + "</resource>\n");
        xml.append("<modifier>" + modifyResourceProduction.getModifier() + "</modifier>\n");
        xml.append("<modifyingOnlyIfResourceExists>" + modifyResourceProduction.isModifyingOnlyIfResourceExists() + "</modifyingOnlyIfResourceExists>\n");
        xml.append("</modifyResourceProduction>");
        return xml.toString();
    }

    public ModifyResourceProduction initializeFromNode(Realm realm, Node node) {
        ModifyResourceProduction modifyResourceProduction = new ModifyResourceProduction();
        modifyResourceProduction.setResource(realm.getResourceManager().getResource(XMLConverterUtility.findNode(node, "resource").getFirstChild().getNodeValue()));
        modifyResourceProduction.setModifier(Integer.parseInt(XMLConverterUtility.findNode(node, "modifier").getFirstChild().getNodeValue()));
        Node modifyingOnlyIfResourceExistsNode = XMLConverterUtility.findNode(node, "modifyingOnlyIfResourceExists");
        if (modifyingOnlyIfResourceExistsNode != null) {
            modifyResourceProduction.setModifyingOnlyIfResourceExists(Boolean.valueOf(modifyingOnlyIfResourceExistsNode.getFirstChild().getNodeValue()));
        }
        return modifyResourceProduction;
    }
}

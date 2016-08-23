package org.freemars.earth;

import org.freerealm.Realm;
import org.freerealm.xmlwrapper.XMLConverter;
import org.freerealm.xmlwrapper.XMLConverterUtility;
import org.w3c.dom.Node;

/**
 *
 * @author Deniz ARIKAN
 */
public class ModifyEarthTaxRateXMLConverter implements XMLConverter<ModifyEarthTaxRate> {

    public String toXML(ModifyEarthTaxRate modifyEarthTaxRate) {
        StringBuilder xml = new StringBuilder();
        xml.append("<ModifyEarthTaxRate>\n");
        xml.append("<modifier>" + modifyEarthTaxRate.getModifier() + "</modifier>\n");
        xml.append("</ModifyEarthTaxRate>");
        return xml.toString();
    }

    public ModifyEarthTaxRate initializeFromNode(Realm realm, Node node) {
        ModifyEarthTaxRate modifyEarthTaxRate = new ModifyEarthTaxRate();
        modifyEarthTaxRate.setModifier(Integer.parseInt(XMLConverterUtility.findNode(node, "modifier").getFirstChild().getNodeValue()));
        return modifyEarthTaxRate;
    }
}

package org.freemars.earth;

import org.freerealm.Realm;
import org.freerealm.xmlwrapper.XMLConverter;
import org.freerealm.xmlwrapper.XMLConverterUtility;
import org.w3c.dom.Node;

/**
 *
 * @author Deniz ARIKAN
 */
public class ModifyResourceEarthSellPriceXMLConverter implements XMLConverter<ModifyResourceEarthSellPrice> {

    public String toXML(ModifyResourceEarthSellPrice modifyResourceEarthSellPrice) {
        StringBuilder xml = new StringBuilder();
        xml.append("<ModifyResourceEarthSellPrice>\n");
        xml.append("<resource>" + modifyResourceEarthSellPrice.getResource() + "</resource>\n");
        xml.append("<modifier>" + modifyResourceEarthSellPrice.getModifier() + "</modifier>\n");
        xml.append("</ModifyResourceEarthSellPrice>");
        return xml.toString();
    }

    public ModifyResourceEarthSellPrice initializeFromNode(Realm realm, Node node) {
        ModifyResourceEarthSellPrice modifyResourceEarthSellPrice = new ModifyResourceEarthSellPrice();
        modifyResourceEarthSellPrice.setResource(XMLConverterUtility.findNode(node, "resource").getFirstChild().getNodeValue());
        modifyResourceEarthSellPrice.setModifier(Integer.parseInt(XMLConverterUtility.findNode(node, "modifier").getFirstChild().getNodeValue()));
        return modifyResourceEarthSellPrice;
    }
}

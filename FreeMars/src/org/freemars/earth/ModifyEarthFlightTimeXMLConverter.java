package org.freemars.earth;

import org.freerealm.Realm;
import org.freerealm.xmlwrapper.XMLConverter;
import org.freerealm.xmlwrapper.XMLConverterUtility;
import org.w3c.dom.Node;

/**
 *
 * @author Deniz ARIKAN
 */
public class ModifyEarthFlightTimeXMLConverter implements XMLConverter<ModifyEarthFlightTime> {

    public String toXML(ModifyEarthFlightTime modifyEarthFlightTime) {
        StringBuilder xml = new StringBuilder();
        xml.append("<ModifyEarthFlightTime>\n");
        xml.append("<modifier>" + modifyEarthFlightTime.getModifier() + "</modifier>\n");
        xml.append("</ModifyEarthFlightTime>");
        return xml.toString();
    }

    public ModifyEarthFlightTime initializeFromNode(Realm realm, Node node) {
        ModifyEarthFlightTime modifyEarthFlightTime = new ModifyEarthFlightTime();
        modifyEarthFlightTime.setModifier(Integer.parseInt(XMLConverterUtility.findNode(node, "modifier").getFirstChild().getNodeValue()));
        return modifyEarthFlightTime;
    }
}

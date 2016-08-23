package org.freerealm.xmlwrapper.player;

import java.util.Iterator;
import org.freerealm.Realm;
import org.freerealm.nation.Nation;
import org.freerealm.xmlwrapper.XMLConverter;
import org.w3c.dom.Node;

/**
 *
 * @author Deniz ARIKAN
 */
public class NationXMLConverter implements XMLConverter<Nation> {

    public String toXML(Nation nation) {
        StringBuilder xml = new StringBuilder();
        xml.append("<Nation ");
        xml.append("id =\"");
        xml.append(nation.getId());
        xml.append("\" name=\"");
        xml.append(nation.getName());
        xml.append("\">\n");
        xml.append("<adjective>");
        xml.append(nation.getAdjective());
        xml.append("</adjective>\n");
        xml.append("<countryName>");
        xml.append(nation.getCountryName());
        xml.append("</countryName>\n");
        xml.append("<defaultColor1>");
        xml.append(nation.getDefaultColor1().getRed());
        xml.append(",");
        xml.append(nation.getDefaultColor1().getGreen());
        xml.append(",");
        xml.append(nation.getDefaultColor1().getBlue());
        xml.append("</defaultColor1>\n");
        xml.append("<defaultColor2>");
        xml.append(nation.getDefaultColor2().getRed());
        xml.append(",");
        xml.append(nation.getDefaultColor2().getGreen());
        xml.append(",");
        xml.append(nation.getDefaultColor2().getBlue());
        xml.append("</defaultColor2>\n");
        xml.append("<settlementNames>\n");
        Iterator<String> cityNamesIterator = nation.getSettlementNamesIterator();
        while (cityNamesIterator.hasNext()) {
            xml.append("<name>" + cityNamesIterator.next() + "</name>\n");
        }
        xml.append("</settlementNames>\n");
        xml.append("</Nation>");
        return xml.toString();

    }

    public Nation initializeFromNode(Realm realm, Node node) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}

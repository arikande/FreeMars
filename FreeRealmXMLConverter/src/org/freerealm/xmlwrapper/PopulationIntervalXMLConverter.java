package org.freerealm.xmlwrapper;

import org.freerealm.Realm;
import org.freerealm.settlement.PopulationInterval;
import org.w3c.dom.Node;

/**
 *
 * @author Deniz ARIKAN
 */
public class PopulationIntervalXMLConverter implements XMLConverter<PopulationInterval> {

    public String toXML(PopulationInterval populationInterval) {
        StringBuilder xml = new StringBuilder();
        xml.append("<PopulationInterval>\n");
        xml.append("<start>" + populationInterval.getStart() + "</start>");
        xml.append("<end>" + populationInterval.getEnd() + "</end>");
        xml.append("</PopulationInterval>\n");
        return xml.toString();
    }

    public PopulationInterval initializeFromNode(Realm realm, Node node) {
        PopulationInterval populationInterval = new PopulationInterval();
        Node startNode = XMLConverterUtility.findNode(node, "start");
        populationInterval.setStart(Integer.parseInt(startNode.getFirstChild().getNodeValue()));
        Node endNode = XMLConverterUtility.findNode(node, "end");
        if (endNode != null) {
            populationInterval.setEnd(Integer.parseInt(endNode.getFirstChild().getNodeValue()));
        } else {
            populationInterval.setEnd(Integer.MAX_VALUE);
        }
        return populationInterval;
    }
}

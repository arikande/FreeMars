package org.freerealm.executor.order;

import org.freerealm.Realm;
import org.freerealm.xmlwrapper.XMLConverter;
import org.w3c.dom.Node;

/**
 *
 * @author Deniz ARIKAN
 */
public class UnloadAllPopulationOrderXMLConverter implements XMLConverter<UnloadAllPopulationOrder> {

    public String toXML(UnloadAllPopulationOrder unloadAllPopulationOrder) {
        StringBuilder xml = new StringBuilder();
        xml.append("<UnloadAllPopulationOrder>\n");
        xml.append("<turnGiven>" + unloadAllPopulationOrder.getTurnGiven() + "</turnGiven>\n");
        xml.append("<symbol>" + unloadAllPopulationOrder.getSymbol() + "</symbol>\n");
        xml.append("</UnloadAllPopulationOrder>");
        return xml.toString();
    }

    public UnloadAllPopulationOrder initializeFromNode(Realm realm, Node node) {
        UnloadAllPopulationOrder unloadAllPopulationOrder = new UnloadAllPopulationOrder(realm);
        for (Node subNode = node.getFirstChild(); subNode != null; subNode = subNode.getNextSibling()) {
            if (subNode.getNodeType() == Node.ELEMENT_NODE) {
                if (subNode.getNodeName().equals("turnGiven")) {
                    String turnGivenString = subNode.getFirstChild().getNodeValue();
                    int turnGivenValue = Integer.parseInt(turnGivenString);
                    unloadAllPopulationOrder.setTurnGiven(turnGivenValue);
                } else if (subNode.getNodeName().equals("symbol")) {
                    String symbolValue = subNode.getFirstChild().getNodeValue();
                    if ((symbolValue != null) && symbolValue.length() > 0) {
                        unloadAllPopulationOrder.setSymbol(symbolValue);
                    }
                }
            }
        }
        return unloadAllPopulationOrder;
    }
}

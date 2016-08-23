package org.freerealm.executor.order;

import org.freerealm.Realm;
import org.freerealm.xmlwrapper.XMLConverter;
import org.w3c.dom.Node;

/**
 *
 * @author Deniz ARIKAN
 */
public class UnloadAllCargoOrderXMLConverter implements XMLConverter<UnloadAllCargoOrder> {

    public String toXML(UnloadAllCargoOrder unloadAllCargoOrder) {
        StringBuilder xml = new StringBuilder();
        xml.append("<UnloadAllCargoOrder>\n");
        xml.append("<turnGiven>" + unloadAllCargoOrder.getTurnGiven() + "</turnGiven>\n");
        xml.append("<symbol>" + unloadAllCargoOrder.getSymbol() + "</symbol>\n");
        xml.append("</UnloadAllCargoOrder>");
        return xml.toString();
    }

    public UnloadAllCargoOrder initializeFromNode(Realm realm, Node node) {
        UnloadAllCargoOrder unloadAllCargoOrder = new UnloadAllCargoOrder(realm);
        for (Node subNode = node.getFirstChild(); subNode != null; subNode = subNode.getNextSibling()) {
            if (subNode.getNodeType() == Node.ELEMENT_NODE) {
                if (subNode.getNodeName().equals("turnGiven")) {
                    String turnGivenString = subNode.getFirstChild().getNodeValue();
                    int turnGivenValue = Integer.parseInt(turnGivenString);
                    unloadAllCargoOrder.setTurnGiven(turnGivenValue);
                } else if (subNode.getNodeName().equals("symbol")) {
                    String symbolValue = subNode.getFirstChild().getNodeValue();
                    if ((symbolValue != null) && symbolValue.length() > 0) {
                        unloadAllCargoOrder.setSymbol(symbolValue);
                    }
                }
            }
        }
        return unloadAllCargoOrder;
    }
}

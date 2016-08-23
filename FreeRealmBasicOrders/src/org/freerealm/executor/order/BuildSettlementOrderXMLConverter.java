package org.freerealm.executor.order;

import org.freerealm.Realm;
import org.freerealm.xmlwrapper.XMLConverter;
import org.w3c.dom.Node;

/**
 *
 * @author Deniz ARIKAN
 */
public class BuildSettlementOrderXMLConverter implements XMLConverter<BuildSettlementOrder> {

    public String toXML(BuildSettlementOrder buildSettlementOrder) {
        StringBuilder xml = new StringBuilder();
        xml.append("<BuildSettlementOrder>\n");
        xml.append("<turnGiven>" + buildSettlementOrder.getTurnGiven() + "</turnGiven>\n");
        xml.append("<symbol>" + buildSettlementOrder.getSymbol() + "</symbol>\n");
        xml.append("</BuildSettlementOrder>");
        return xml.toString();
    }

    public BuildSettlementOrder initializeFromNode(Realm realm, Node node) {
        BuildSettlementOrder buildSettlementOrder = new BuildSettlementOrder(realm);
        for (Node subNode = node.getFirstChild(); subNode != null; subNode = subNode.getNextSibling()) {
            if (subNode.getNodeType() == Node.ELEMENT_NODE) {
                if (subNode.getNodeName().equals("turnGiven")) {
                    String turnGivenString = subNode.getFirstChild().getNodeValue();
                    int turnGivenValue = Integer.parseInt(turnGivenString);
                    buildSettlementOrder.setTurnGiven(turnGivenValue);
                } else if (subNode.getNodeName().equals("symbol")) {
                    String symbolValue = subNode.getFirstChild().getNodeValue();
                    if ((symbolValue != null) && symbolValue.length() > 0) {
                        buildSettlementOrder.setSymbol(symbolValue);
                    }
                }
            }
        }
        return buildSettlementOrder;
    }
}

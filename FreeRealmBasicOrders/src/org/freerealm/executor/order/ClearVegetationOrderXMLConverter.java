package org.freerealm.executor.order;

import org.freerealm.Realm;
import org.freerealm.xmlwrapper.XMLConverter;
import org.w3c.dom.Node;

/**
 *
 * @author Deniz ARIKAN
 */
public class ClearVegetationOrderXMLConverter implements XMLConverter<ClearVegetationOrder> {

    public String toXML(ClearVegetationOrder clearVegetation) {
        StringBuilder xml = new StringBuilder();
        xml.append("<ClearVegetationOrder>\n");
        xml.append("<turnGiven>" + clearVegetation.getTurnGiven() + "</turnGiven>\n");
        xml.append("<symbol>" + clearVegetation.getSymbol() + "</symbol>\n");
        xml.append("</ClearVegetationOrder>");
        return xml.toString();
    }

    public ClearVegetationOrder initializeFromNode(Realm realm, Node node) {
        ClearVegetationOrder clearVegetation = new ClearVegetationOrder(realm);
        for (Node subNode = node.getFirstChild(); subNode != null; subNode = subNode.getNextSibling()) {
            if (subNode.getNodeType() == Node.ELEMENT_NODE) {
                if (subNode.getNodeName().equals("turnGiven")) {
                    String turnGivenString = subNode.getFirstChild().getNodeValue();
                    int turnGivenValue = Integer.parseInt(turnGivenString);
                    clearVegetation.setTurnGiven(turnGivenValue);
                } else if (subNode.getNodeName().equals("symbol")) {
                    String symbolValue = subNode.getFirstChild().getNodeValue();
                    if ((symbolValue != null) && symbolValue.length() > 0) {
                        clearVegetation.setSymbol(symbolValue);
                    }
                }
            }
        }
        return clearVegetation;
    }
}

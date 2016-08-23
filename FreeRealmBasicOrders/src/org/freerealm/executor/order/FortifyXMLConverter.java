package org.freerealm.executor.order;

import org.freerealm.Realm;
import org.freerealm.xmlwrapper.XMLConverter;
import org.w3c.dom.Node;

/**
 *
 * @author Deniz ARIKAN
 */
public class FortifyXMLConverter implements XMLConverter<Fortify> {

    public String toXML(Fortify fortify) {
        StringBuilder xml = new StringBuilder();
        xml.append("<Fortify>\n");
        xml.append("<turnGiven>" + fortify.getTurnGiven() + "</turnGiven>\n");
        xml.append("</Fortify>");
        return xml.toString();
    }

    public Fortify initializeFromNode(Realm realm, Node node) {
        Fortify fortify = new Fortify(realm);
        for (Node subNode = node.getFirstChild(); subNode != null; subNode = subNode.getNextSibling()) {
            if (subNode.getNodeType() == Node.ELEMENT_NODE) {
                if (subNode.getNodeName().equals("turnGiven")) {
                    String turnGivenString = subNode.getFirstChild().getNodeValue();
                    int turnGivenValue = Integer.parseInt(turnGivenString);
                    fortify.setTurnGiven(turnGivenValue);
                }
            }
        }
        return fortify;
    }
}

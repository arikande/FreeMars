package org.freerealm.executor.order;

import org.freerealm.Realm;
import org.freerealm.xmlwrapper.XMLConverter;
import org.w3c.dom.Node;

/**
 *
 * @author Deniz ARIKAN
 */
public class SentryXMLConverter implements XMLConverter<Sentry> {

    public String toXML(Sentry sentry) {
        StringBuilder xml = new StringBuilder();
        xml.append("<Sentry>\n");
        xml.append("<turnGiven>" + sentry.getTurnGiven() + "</turnGiven>\n");
        xml.append("</Sentry>");
        return xml.toString();
    }

    public Sentry initializeFromNode(Realm realm, Node node) {
        Sentry sentry = new Sentry(realm);
        for (Node subNode = node.getFirstChild(); subNode != null; subNode = subNode.getNextSibling()) {
            if (subNode.getNodeType() == Node.ELEMENT_NODE) {
                if (subNode.getNodeName().equals("turnGiven")) {
                    String turnGivenString = subNode.getFirstChild().getNodeValue();
                    int turnGivenValue = Integer.parseInt(turnGivenString);
                    sentry.setTurnGiven(turnGivenValue);
                }
            }
        }
        return sentry;
    }
}

package org.freerealm.xmlwrapper.player;

import org.freerealm.Realm;
import org.freerealm.player.DefaultMessage;
import org.freerealm.xmlwrapper.XMLConverter;
import org.w3c.dom.Node;

/**
 *
 * @author Deniz ARIKAN
 */
public class MessageXMLConverter implements XMLConverter<DefaultMessage> {

    public String toXML(DefaultMessage message) {
        StringBuilder xml = new StringBuilder();
        xml.append("<Message>\n");
        xml.append("<text>" + message.getText() + "</text>\n");
        xml.append("<read>" + message.isRead() + "</read>\n");
        xml.append("<turnSent>" + message.getTurnSent() + "</turnSent>\n");
        xml.append("</Message>\n");
        return xml.toString();
    }

    public DefaultMessage initializeFromNode(Realm realm, Node node) {
        DefaultMessage message = new DefaultMessage();
        for (Node subNode = node.getFirstChild(); subNode != null; subNode = subNode.getNextSibling()) {
            if (subNode.getNodeType() == Node.ELEMENT_NODE) {
                if (subNode.getNodeName().equals("text")) {
                    String textValue = subNode.getFirstChild().getNodeValue();
                    message.setText(textValue);
                } else if (subNode.getNodeName().equals("turnSent")) {
                    int turnSentValue = Integer.parseInt(subNode.getFirstChild().getNodeValue());
                    message.setTurnSent(turnSentValue);
                }
            }
        }
        message.setRead(true);
        return message;
    }
}

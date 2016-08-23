package org.freerealm.executor.order;

import org.freerealm.Realm;
import org.freerealm.map.Coordinate;
import org.freerealm.xmlwrapper.XMLConverter;
import org.freerealm.xmlwrapper.map.CoordinateXMLConverter;
import org.w3c.dom.Node;

/**
 *
 * @author Deniz ARIKAN
 */
public class GoToCoordinateXMLConverter implements XMLConverter<GoToCoordinate> {

    public String toXML(GoToCoordinate goToCoordinate) {
        StringBuilder xml = new StringBuilder();
        xml.append("<GoToCoordinate>\n");
        xml.append("<turnGiven>" + goToCoordinate.getTurnGiven() + "</turnGiven>\n");
        xml.append(new CoordinateXMLConverter().toXML(goToCoordinate.getCoordinate()) + "\n");
        xml.append("</GoToCoordinate>");
        return xml.toString();
    }

    public GoToCoordinate initializeFromNode(Realm realm, Node node) {
        GoToCoordinate goToCoordinate = new GoToCoordinate(realm);
        for (Node subNode = node.getFirstChild(); subNode != null; subNode = subNode.getNextSibling()) {
            if (subNode.getNodeType() == Node.ELEMENT_NODE) {
                if (subNode.getNodeName().equals("turnGiven")) {
                    String turnGivenString = subNode.getFirstChild().getNodeValue();
                    int turnGivenValue = Integer.parseInt(turnGivenString);
                    goToCoordinate.setTurnGiven(turnGivenValue);
                } else if (subNode.getNodeName().equals("coordinate")) {
                    Coordinate coordinateValue = new CoordinateXMLConverter().initializeFromNode(realm, subNode);
                    goToCoordinate.setCoordinate(coordinateValue);
                }
            }
        }
        return goToCoordinate;
    }
}

package org.freemars.earth.order;

import org.freemars.controller.FreeMarsController;
import org.freemars.earth.Location;
import org.freerealm.Realm;
import org.freerealm.xmlwrapper.TagManager;
import org.freerealm.xmlwrapper.XMLConverter;
import org.w3c.dom.Node;

/**
 *
 * @author phoenix
 */
public class RelocateUnitOrderXMLConverter implements XMLConverter<RelocateUnitOrder> {

    public String toXML(RelocateUnitOrder relocateUnitOrder) {
        StringBuilder xml = new StringBuilder();
        xml.append("<RelocateUnitOrder>\n");
        xml.append("<turnGiven>" + relocateUnitOrder.getTurnGiven() + "</turnGiven>\n");
        xml.append("<source>" + relocateUnitOrder.getSource().getId() + "</source>\n");
        xml.append("<destination>" + relocateUnitOrder.getDestination().getId() + "</destination>\n");
        if (relocateUnitOrder.getLandOnColony() != null) {
            xml.append("<landOnColony>" + relocateUnitOrder.getLandOnColony().getId() + "</landOnColony>\n");
        }
        xml.append("</RelocateUnitOrder>\n");
        return xml.toString();
    }

    public RelocateUnitOrder initializeFromNode(Realm realm, Node node) {
        RelocateUnitOrder relocateUnitOrder = new RelocateUnitOrder(realm);
        for (Node subNode = node.getFirstChild(); subNode != null; subNode = subNode.getNextSibling()) {
            if (subNode.getNodeType() == Node.ELEMENT_NODE) {
                if (subNode.getNodeName().equals("turnGiven")) {
                    String turnGivenString = subNode.getFirstChild().getNodeValue();
                    int turnGivenValue = Integer.parseInt(turnGivenString);
                    relocateUnitOrder.setTurnGiven(turnGivenValue);
                } else if (subNode.getNodeName().equals("source")) {
                    String sourceString = subNode.getFirstChild().getNodeValue();
                    int sourceValue = Integer.parseInt(sourceString);
                    relocateUnitOrder.setSource(Location.getLocation(sourceValue));
                } else if (subNode.getNodeName().equals("destination")) {
                    String destinationString = subNode.getFirstChild().getNodeValue();
                    int destinationValue = Integer.parseInt(destinationString);
                    relocateUnitOrder.setDestination(Location.getLocation(destinationValue));
                } else if (subNode.getNodeName().equals("landOnColony")) {
                    String landOnColonyString = subNode.getFirstChild().getNodeValue();
                    int colonyId = Integer.parseInt(landOnColonyString);
                    relocateUnitOrder.setLandOnColony(realm.getSettlement(colonyId));
                }
            }
        }
        FreeMarsController freeMarsController = (FreeMarsController) TagManager.getObjectFromPool("freeMarsController");
        relocateUnitOrder.setFreeMarsController(freeMarsController);
        return relocateUnitOrder;
    }
}

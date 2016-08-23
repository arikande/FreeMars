package org.freerealm.executor.order;

import org.freerealm.Realm;
import org.freerealm.xmlwrapper.XMLConverter;
import org.freerealm.xmlwrapper.XMLConverterUtility;
import org.w3c.dom.Node;

/**
 *
 * @author Deniz ARIKAN
 */
public class TransformTileOrderXMLConverter implements XMLConverter<TransformTileOrder> {

    public String toXML(TransformTileOrder transformTileOrder) {
        StringBuilder xml = new StringBuilder();
        xml.append("<TransformTileOrder>\n");
        xml.append("<turnGiven>" + transformTileOrder.getTurnGiven() + "</turnGiven>\n");
        xml.append("<tileTypeId>" + transformTileOrder.getTileType().getId() + "</tileTypeId>\n");
        xml.append("</TransformTileOrder>");
        return xml.toString();
    }

    public TransformTileOrder initializeFromNode(Realm realm, Node node) {
        TransformTileOrder transformTileOrder = new TransformTileOrder(realm);
        Node turnGivenNode = XMLConverterUtility.findNode(node, "turnGiven");
        int turnGiven = Integer.parseInt(turnGivenNode.getFirstChild().getNodeValue());
        transformTileOrder.setTurnGiven(turnGiven);
        Node tileTypeIdNode = XMLConverterUtility.findNode(node, "tileTypeId");
        int tileTypeId = Integer.parseInt(tileTypeIdNode.getFirstChild().getNodeValue());
        transformTileOrder.setTileType(realm.getTileTypeManager().getTileType(tileTypeId));
        return transformTileOrder;
    }
}

package org.freerealm.xmlwrapper.property;

import java.util.Iterator;
import org.freerealm.Realm;
import org.freerealm.property.MoveProperty;
import org.freerealm.tile.TileType;
import org.freerealm.xmlwrapper.XMLConverter;
import org.w3c.dom.Node;

/**
 *
 * @author Deniz ARIKAN
 */
public class MovePropertyXMLConverter implements XMLConverter<MoveProperty> {

    public String toXML(MoveProperty move) {
        StringBuilder xml = new StringBuilder();
        xml.append("<move_property points=\"" + move.getPoints() + "\">\n");
        Iterator<TileType> tileTypesIterator = move.getTileTypesIterator();
        while (tileTypesIterator.hasNext()) {
            TileType tileType = tileTypesIterator.next();
            xml.append("<TileType>" + tileType.getName() + "</TileType>\n");
        }
        xml.append("</move_property>");
        return xml.toString();
    }

    public MoveProperty initializeFromNode(Realm realm, Node node) {
        MoveProperty move = new MoveProperty();
        int points = Integer.parseInt(node.getAttributes().getNamedItem("points").getNodeValue());
        move.setPoints(points);
        for (Node subNode = node.getFirstChild(); subNode != null; subNode = subNode.getNextSibling()) {
            if (subNode.getNodeType() == Node.ELEMENT_NODE) {
                if (subNode.getNodeName().equals("TileType")) {
                    String tileTypeNameValue = subNode.getFirstChild().getNodeValue();
                    TileType tileType = realm.getTileTypeManager().getTileType(tileTypeNameValue);
                    move.addTileType(tileType);
                }
            }
        }
        return move;
    }
}

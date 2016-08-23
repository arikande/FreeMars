package org.freerealm.xmlwrapper.map;

import java.util.Iterator;
import org.freerealm.Realm;
import org.freerealm.tile.TileType;
import org.freerealm.tile.TileTypeManager;
import org.freerealm.xmlwrapper.XMLConverter;
import org.w3c.dom.Node;

/**
 *
 * @author Deniz ARIKAN
 */
public class TileTypeManagerXMLConverter implements XMLConverter<TileTypeManager> {

    public String toXML(TileTypeManager tileTypeManager) {
        StringBuilder xml = new StringBuilder();
        xml.append("<tileTypes>\n");
        for (Iterator<TileType> it = tileTypeManager.getTileTypesIterator(); it.hasNext();) {
            xml.append((new TileTypeXMLConverter()).toXML(it.next()) + "\n");
        }
        xml.append("</tileTypes>");
        return xml.toString();
    }

    public TileTypeManager initializeFromNode(Realm realm, Node node) {
        TileTypeManager tileTypeManager = new TileTypeManager();
        for (Node tileTypeNode = node.getFirstChild(); tileTypeNode != null; tileTypeNode = tileTypeNode.getNextSibling()) {
            if (tileTypeNode.getNodeType() == Node.ELEMENT_NODE) {
                tileTypeManager.addTileType(new TileTypeXMLConverter().initializeFromNode(realm, tileTypeNode));
            }
        }
        return tileTypeManager;
    }
}

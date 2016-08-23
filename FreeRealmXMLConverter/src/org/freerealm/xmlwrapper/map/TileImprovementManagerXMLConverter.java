package org.freerealm.xmlwrapper.map;

import java.util.Iterator;
import org.freerealm.Realm;
import org.freerealm.tile.improvement.TileImprovementType;
import org.freerealm.tile.improvement.TileImprovementTypeManager;
import org.freerealm.xmlwrapper.XMLConverter;
import org.w3c.dom.Node;

/**
 *
 * @author Deniz ARIKAN
 */
public class TileImprovementManagerXMLConverter implements XMLConverter<TileImprovementTypeManager> {

    public String toXML(TileImprovementTypeManager tileImprovementTypeManager) {
        StringBuilder xml = new StringBuilder();
        xml.append("<tileImprovements>\n");
        for (Iterator<TileImprovementType> iterator = tileImprovementTypeManager.getImprovementsIterator(); iterator.hasNext();) {
            TileImprovementType tileImprovement = (TileImprovementType) iterator.next();
            xml.append((new TileImprovementImplXMLConverter()).toXML(tileImprovement) + "\n");
        }
        xml.append("</tileImprovements>\n");
        return xml.toString();
    }

    public TileImprovementTypeManager initializeFromNode(Realm realm, Node node) {
        TileImprovementTypeManager tileImprovementTypeManager = new TileImprovementTypeManager();
        for (Node tileImprovementNode = node.getFirstChild(); tileImprovementNode != null; tileImprovementNode = tileImprovementNode.getNextSibling()) {
            if (tileImprovementNode.getNodeType() == Node.ELEMENT_NODE) {
                TileImprovementType tileImprovement = (new TileImprovementImplXMLConverter()).initializeFromNode(realm, tileImprovementNode);
                tileImprovementTypeManager.addImprovement(tileImprovement);
            }
        }
        for (Node tileImprovementNode = node.getFirstChild(); tileImprovementNode != null; tileImprovementNode = tileImprovementNode.getNextSibling()) {
            if (tileImprovementNode.getNodeType() == Node.ELEMENT_NODE) {
                new TileImprovementImplXMLConverter().setPrerequisitesFromNode(tileImprovementTypeManager, tileImprovementNode);
            }
        }
        return tileImprovementTypeManager;
    }
}

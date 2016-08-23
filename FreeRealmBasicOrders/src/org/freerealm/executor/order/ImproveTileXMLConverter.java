package org.freerealm.executor.order;

import org.freerealm.Realm;
import org.freerealm.tile.improvement.TileImprovementType;
import org.freerealm.xmlwrapper.XMLConverter;
import org.w3c.dom.Node;

/**
 *
 * @author Deniz ARIKAN
 */
public class ImproveTileXMLConverter implements XMLConverter<ImproveTile> {

    public String toXML(ImproveTile improveTile) {
        StringBuilder xml = new StringBuilder();
        xml.append("<ImproveTile>\n");
        xml.append("<turnGiven>" + improveTile.getTurnGiven() + "</turnGiven>\n");
        xml.append("<symbol>" + improveTile.getSymbol() + "</symbol>\n");
        xml.append("<tileImprovementType>" + improveTile.getTileImprovementType().getName() + "</tileImprovementType>\n");
        xml.append("</ImproveTile>");
        return xml.toString();
    }

    public ImproveTile initializeFromNode(Realm realm, Node node) {

        ImproveTile improveTile = new ImproveTile(realm);
        for (Node subNode = node.getFirstChild(); subNode != null; subNode = subNode.getNextSibling()) {
            if (subNode.getNodeType() == Node.ELEMENT_NODE) {
                if (subNode.getNodeName().equals("turnGiven")) {
                    String turnGivenString = subNode.getFirstChild().getNodeValue();
                    int turnGivenValue = Integer.parseInt(turnGivenString);
                    improveTile.setTurnGiven(turnGivenValue);
                } else if (subNode.getNodeName().equals("tileImprovementType")) {
                    String tileImprovementTypeName = subNode.getFirstChild().getNodeValue();
                    TileImprovementType tileImprovementTypeValue = (TileImprovementType) realm.getTileImprovementTypeManager().getImprovement(tileImprovementTypeName);
                    improveTile.setTileImprovementType(tileImprovementTypeValue);
                } else if (subNode.getNodeName().equals("symbol")) {
                    String symbolValue = subNode.getFirstChild().getNodeValue();
                    if ((symbolValue != null) && symbolValue.length() > 0) {
                        improveTile.setSymbol(symbolValue);
                    }
                }
            }
        }
        return improveTile;
    }
}

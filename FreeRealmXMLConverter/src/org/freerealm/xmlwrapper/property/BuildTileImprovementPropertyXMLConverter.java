package org.freerealm.xmlwrapper.property;

import java.util.Iterator;
import org.freerealm.Realm;
import org.freerealm.property.BuildTileImprovementProperty;
import org.freerealm.tile.improvement.TileImprovementType;
import org.freerealm.xmlwrapper.XMLConverter;
import org.w3c.dom.Node;

/**
 *
 * @author Deniz ARIKAN
 */
public class BuildTileImprovementPropertyXMLConverter implements XMLConverter<BuildTileImprovementProperty> {

    public String toXML(BuildTileImprovementProperty buildTileImprovement) {
        StringBuilder xml = new StringBuilder();
        xml.append("<" + BuildTileImprovementProperty.NAME + " productionPoints=\"");
        xml.append(buildTileImprovement.getProductionPoints());
        xml.append("\">\n");
        Iterator<TileImprovementType> iterator = buildTileImprovement.getTileImprovementsIterator();
        while (iterator.hasNext()) {
            TileImprovementType tileImprovementType = iterator.next();
            xml.append("<TileImprovementType>");
            xml.append(tileImprovementType.getName());
            xml.append("</TileImprovementType>\n");
        }
        xml.append("</" + BuildTileImprovementProperty.NAME + ">");
        return xml.toString();
    }

    public BuildTileImprovementProperty initializeFromNode(Realm realm, Node node) {
        BuildTileImprovementProperty buildTileImprovement = new BuildTileImprovementProperty();
        String productionPointsValue = node.getAttributes().getNamedItem("productionPoints").getNodeValue();
        buildTileImprovement.setProductionPoints(Integer.parseInt(productionPointsValue));
        for (Node subNode = node.getFirstChild(); subNode != null; subNode = subNode.getNextSibling()) {
            if (subNode.getNodeType() == Node.ELEMENT_NODE) {
                if (subNode.getNodeName().equals("TileImprovementType")) {
                    String nameValue = subNode.getFirstChild().getNodeValue();
                    TileImprovementType tileImprovementTypeValue = (TileImprovementType) realm.getTileImprovementTypeManager().getImprovement(nameValue);
                    buildTileImprovement.addTileImprovement(tileImprovementTypeValue);
                }
            }
        }
        return buildTileImprovement;
    }
}

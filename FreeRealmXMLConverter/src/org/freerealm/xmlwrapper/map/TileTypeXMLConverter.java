package org.freerealm.xmlwrapper.map;

import java.util.Iterator;
import org.freerealm.Realm;
import org.freerealm.resource.Resource;
import org.freerealm.tile.TileType;
import org.freerealm.xmlwrapper.XMLConverter;
import org.freerealm.xmlwrapper.XMLConverterUtility;
import org.w3c.dom.Node;

/**
 *
 * @author Deniz ARIKAN
 */
public class TileTypeXMLConverter implements XMLConverter<TileType> {

    public String toXML(TileType tileType) {
        StringBuilder xml = new StringBuilder();
        xml.append("<tileType ");
        xml.append("id=\"");
        xml.append(tileType.getId());
        xml.append("\" name=\"");
        xml.append(tileType.getName());
        xml.append("\" movementCost=\"");
        xml.append(tileType.getMovementCost());
        xml.append("\" probability=\"");
        xml.append(tileType.getProbability());
        xml.append("\" defencePercentage=\"");
        xml.append(tileType.getDefencePercentage());
        xml.append("\">\n");
        xml.append("<resources>\n");
        Iterator<Resource> productionIterator = tileType.getProduction().keySet().iterator();
        while (productionIterator.hasNext()) {
            Resource resource = productionIterator.next();
            xml.append("<");
            xml.append(resource.getName());
            xml.append(">");
            xml.append(tileType.getProduction().get(resource));
            xml.append("</");
            xml.append(resource.getName());
            xml.append(">\n");
        }
        xml.append("</resources>\n");

        xml.append("<transformableTileTypes>\n");
        Iterator<Integer> transformableTileTypesIterator = tileType.getTransformableTileTypeIdsIterator();
        while (transformableTileTypesIterator.hasNext()) {
            int transformableTileTypeId = transformableTileTypesIterator.next();
            xml.append("<transformableTileType>\n");
            xml.append("<tileTypeId>" + transformableTileTypeId + "</tileTypeId>\n");
            xml.append("<transformationCost>" + tileType.getTransformationCostToTileType(transformableTileTypeId) + "</transformationCost>\n");
            xml.append("</transformableTileType>\n");
        }
        xml.append("</transformableTileTypes>\n");
        xml.append("</tileType>");
        return xml.toString();
    }

    public TileType initializeFromNode(Realm realm, Node node) {
        String name = node.getAttributes().getNamedItem("name").getNodeValue();
        int movementCost = Integer.parseInt(node.getAttributes().getNamedItem("movementCost").getNodeValue());
        int probability = Integer.parseInt(node.getAttributes().getNamedItem("probability").getNodeValue());
        int defencePercentage = Integer.parseInt(node.getAttributes().getNamedItem("defencePercentage").getNodeValue());
        int id = Integer.parseInt(node.getAttributes().getNamedItem("id").getNodeValue());
        TileType tileType = new TileType();
        tileType.setName(name);
        tileType.setMovementCost(movementCost);
        tileType.setProbability(probability);
        tileType.setDefencePercentage(defencePercentage);
        tileType.setId(id);
        Node resourcesNode = XMLConverterUtility.findNode(node, "resources");
        if (resourcesNode != null) {
            for (Node subNode = resourcesNode.getFirstChild(); subNode != null; subNode = subNode.getNextSibling()) {
                if (subNode.getNodeType() == Node.ELEMENT_NODE) {
                    String resourceName = subNode.getNodeName();
                    int quantity = Integer.parseInt(subNode.getFirstChild().getNodeValue().trim());
                    Resource resource = realm.getResourceManager().getResource(resourceName);
                    tileType.addProduction(resource, quantity);
                }
            }
        }
        Node transformableTileTypesNode = XMLConverterUtility.findNode(node, "transformableTileTypes");
        if (transformableTileTypesNode != null) {
            for (Node transformableTileTypeNode = transformableTileTypesNode.getFirstChild(); transformableTileTypeNode != null; transformableTileTypeNode = transformableTileTypeNode.getNextSibling()) {
                if (transformableTileTypeNode.getNodeType() == Node.ELEMENT_NODE) {
                    if (transformableTileTypeNode.getNodeName().equals("transformableTileType")) {
                        Node tileTypeIdNode = XMLConverterUtility.findNode(transformableTileTypeNode, "tileTypeId");
                        int transformableTileTypeId = Integer.parseInt(tileTypeIdNode.getFirstChild().getNodeValue());
                        Node transformationCostNode = XMLConverterUtility.findNode(transformableTileTypeNode, "transformationCost");
                        int transformationCost = Integer.parseInt(transformationCostNode.getFirstChild().getNodeValue());
                        tileType.addTransformableTileType(transformableTileTypeId, transformationCost);
                    }
                }
            }

        }
        return tileType;
    }
}

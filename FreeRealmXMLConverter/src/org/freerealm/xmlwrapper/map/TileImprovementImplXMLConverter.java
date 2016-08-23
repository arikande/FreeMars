package org.freerealm.xmlwrapper.map;

import java.util.Iterator;
import org.freerealm.Realm;
import org.freerealm.property.Property;
import org.freerealm.tile.TileBuildablePrerequisite;
import org.freerealm.tile.TileType;
import org.freerealm.tile.improvement.FreeRealmTileImprovementType;
import org.freerealm.tile.improvement.NoTileImprovementPrerequisite;
import org.freerealm.tile.improvement.NoVegetationPrerequisite;
import org.freerealm.tile.improvement.TileImprovementType;
import org.freerealm.tile.improvement.TileImprovementTypeManager;
import org.freerealm.xmlwrapper.PropertyFactory;
import org.freerealm.xmlwrapper.TagManager;
import org.freerealm.xmlwrapper.XMLConverter;
import org.freerealm.xmlwrapper.XMLConverterUtility;
import org.w3c.dom.Node;

/**
 *
 * @author Deniz ARIKAN
 */
public class TileImprovementImplXMLConverter implements XMLConverter<TileImprovementType> {

    public String toXML(TileImprovementType tileImprovementType) {
        StringBuilder xml = new StringBuilder();
        xml.append("<TileImprovement>");
        xml.append("<id>" + tileImprovementType.getId() + "</id>");
        xml.append("<name>" + tileImprovementType.getName() + "</name>");
        xml.append("<productionCost>" + tileImprovementType.getProductionCost() + "</productionCost>");
        xml.append("<upkeepCost>" + tileImprovementType.getUpkeepCost() + "</upkeepCost>");
        xml.append("<tileTypes>\n");
        Iterator<TileType> tileTypesIterator = tileImprovementType.getBuildableTileTypesIterator();
        while (tileTypesIterator.hasNext()) {
            xml.append("<tileTypeId>" + tileTypesIterator.next().getId() + "</tileTypeId>\n");
        }
        xml.append("</tileTypes>\n");
        xml.append("<prerequisites>\n");
        Iterator<TileBuildablePrerequisite> iterator = tileImprovementType.getPrerequisitesIterator();
        while (iterator.hasNext()) {
            TileBuildablePrerequisite tileBuildablePrerequisite = iterator.next();
            if (tileBuildablePrerequisite instanceof NoVegetationPrerequisite) {
                xml.append("<NoVegetation/>\n");
            } else if (tileBuildablePrerequisite instanceof NoTileImprovementPrerequisite) {
                NoTileImprovementPrerequisite noTileImprovementPrerequisite = (NoTileImprovementPrerequisite) tileBuildablePrerequisite;
                xml.append("<NoTileImprovement>\n");
                Iterator<Integer> exclusiveImprovementsIterator = noTileImprovementPrerequisite.getExclusiveImprovementsIterator();
                while (exclusiveImprovementsIterator.hasNext()) {
                    Integer integer = exclusiveImprovementsIterator.next();
                    xml.append("<TileImprovement id=\"" + integer + "\"/>");
                }
                xml.append("</NoTileImprovement>");
            }
        }
        xml.append("</prerequisites>\n");
        xml.append("<properties>\n");
        Iterator<Property> propertyIterator = tileImprovementType.getPropertiesIterator();
        while (propertyIterator.hasNext()) {
            Property property = propertyIterator.next();
            String xMLWrapperName = TagManager.getXMLConverterName(property.getName());
            Class c;
            try {
                c = Class.forName(xMLWrapperName);
                XMLConverter<Property> xMLConverter = (XMLConverter<Property>) c.newInstance();
                xml.append(xMLConverter.toXML(property) + "\n");
            } catch (Exception ex) {
            }
        }
        xml.append("</properties>\n");
        xml.append("</TileImprovement>");
        return xml.toString();
    }

    public TileImprovementType initializeFromNode(Realm realm, Node node) {
        FreeRealmTileImprovementType tileImprovementType = new FreeRealmTileImprovementType();
        int id = Integer.parseInt(XMLConverterUtility.findNode(node, "id").getFirstChild().getNodeValue());
        String name = XMLConverterUtility.findNode(node, "name").getFirstChild().getNodeValue();
        int productionCost = Integer.parseInt(XMLConverterUtility.findNode(node, "productionCost").getFirstChild().getNodeValue());
        int upkeepCost = Integer.parseInt(XMLConverterUtility.findNode(node, "upkeepCost").getFirstChild().getNodeValue());
        tileImprovementType.setId(id);
        tileImprovementType.setName(name);
        tileImprovementType.setProductionCost(productionCost);
        tileImprovementType.setUpkeepCost(upkeepCost);
        Node tileTypesNode = XMLConverterUtility.findNode(node, "tileTypes");
        for (Node tileTypeNode = tileTypesNode.getFirstChild(); tileTypeNode != null; tileTypeNode = tileTypeNode.getNextSibling()) {
            if (tileTypeNode.getNodeType() == Node.ELEMENT_NODE) {
                int tileTypeId = Integer.parseInt(tileTypeNode.getFirstChild().getNodeValue());
                TileType tileType = realm.getTileTypeManager().getTileType(tileTypeId);
                tileImprovementType.addTileType(tileType);
            }
        }
        Node propertiesNode = XMLConverterUtility.findNode(node, "properties");
        for (Node propertyNode = propertiesNode.getFirstChild(); propertyNode != null; propertyNode = propertyNode.getNextSibling()) {
            if (propertyNode.getNodeType() == Node.ELEMENT_NODE) {
                Property property = PropertyFactory.createProperty(realm, propertyNode);
                tileImprovementType.addProperty(property);
            }
        }
        return tileImprovementType;
    }

    public void setPrerequisitesFromNode(TileImprovementTypeManager tileImprovementManager, Node node) {
        int id = Integer.parseInt(XMLConverterUtility.findNode(node, "id").getFirstChild().getNodeValue());
        TileImprovementType tileImprovement = tileImprovementManager.getImprovement(id);
        for (Node subNode = node.getFirstChild(); subNode != null; subNode = subNode.getNextSibling()) {
            if (subNode.getNodeType() == Node.ELEMENT_NODE) {
                if (subNode.getNodeName().equals("prerequisites")) {
                    for (Node prerequisiteNode = subNode.getFirstChild(); prerequisiteNode != null; prerequisiteNode = prerequisiteNode.getNextSibling()) {
                        if (prerequisiteNode.getNodeType() == Node.ELEMENT_NODE) {
                            if (prerequisiteNode.getNodeName().equals("NoVegetation")) {
                                NoVegetationPrerequisite noVegetationPrerequisite = new NoVegetationPrerequisite();
                                tileImprovement.addPrerequisite(noVegetationPrerequisite);
                            } else if (prerequisiteNode.getNodeName().equals("NoTileImprovement")) {
                                NoTileImprovementPrerequisite noTileImprovementPrerequisite = new NoTileImprovementPrerequisite();
                                for (Node prerequisiteSubNode = prerequisiteNode.getFirstChild(); prerequisiteSubNode != null; prerequisiteSubNode = prerequisiteSubNode.getNextSibling()) {
                                    if (prerequisiteSubNode.getNodeType() == Node.ELEMENT_NODE) {
                                        int prerequisiteSubNodeId = Integer.parseInt(prerequisiteSubNode.getAttributes().getNamedItem("id").getNodeValue());
                                        noTileImprovementPrerequisite.addExclusiveImprovementId(prerequisiteSubNodeId);
                                    }
                                }
                                tileImprovement.addPrerequisite(noTileImprovementPrerequisite);
                            }
                        }
                    }
                }
            }
        }
    }
}

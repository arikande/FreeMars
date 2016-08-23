package org.freerealm.xmlwrapper.map;

import java.util.Iterator;
import java.util.Properties;
import org.freerealm.Realm;
import org.freerealm.tile.Collectable;
import org.freerealm.tile.FreeRealmTile;
import org.freerealm.tile.FreeRealmTileModifier;
import org.freerealm.tile.Tile;
import org.freerealm.tile.TileType;
import org.freerealm.tile.improvement.TileImprovementType;
import org.freerealm.vegetation.FreeRealmVegetation;
import org.freerealm.vegetation.Vegetation;
import org.freerealm.xmlwrapper.TagManager;
import org.freerealm.xmlwrapper.XMLConverter;
import org.freerealm.xmlwrapper.XMLConverterUtility;
import org.freerealm.xmlwrapper.vegetation.FreeRealmVegetationXMLConverter;
import org.w3c.dom.Node;

/**
 *
 * @author Deniz ARIKAN
 */
public class TileXMLConverter implements XMLConverter<Tile> {

    public String toXML(Tile tile) {
        StringBuilder xml = new StringBuilder();
        xml.append("<tile>\n");
        xml.append("<type>" + tile.getType().getId() + "</type>\n");
        if (tile.getVegetation() != null) {
            xml.append(new FreeRealmVegetationXMLConverter().toXML((FreeRealmVegetation) tile.getVegetation()) + "\n");
        }
        if (tile.getCollectable() != null) {
            xml.append("<collectable>\n");
            String xMLConverterName = TagManager.getXMLConverterName(tile.getCollectable().getName());
            Class c;
            try {
                c = Class.forName(xMLConverterName);
                XMLConverter<Collectable> xMLConverter = (XMLConverter<Collectable>) c.newInstance();
                xml.append(xMLConverter.toXML(tile.getCollectable()));
            } catch (Exception ex) {
            }
            xml.append("</collectable>\n");
        }
        if (tile.getImprovementCount() > 0) {
            xml.append("<improvements>\n");
            Iterator<TileImprovementType> improvementsIterator = tile.getImprovementsIterator();
            while (improvementsIterator.hasNext()) {
                TileImprovementType improvement = improvementsIterator.next();
                xml.append("<improvement>" + improvement.getId() + "</improvement>\n");
            }
            xml.append("</improvements>\n");
        }
        if (tile.getBonusResource() != null) {
            xml.append("<bonusResource>" + tile.getBonusResource().getId() + "</bonusResource>\n");
        }
        if (tile.getCustomModifierCount() > 0) {
            xml.append("<customModifiers>\n");
            Iterator<String> customModifierNamesIterator = tile.getCustomModifierNamesIterator();
            while (customModifierNamesIterator.hasNext()) {
                xml.append("<customModifier>\n");
                String customModifierName = customModifierNamesIterator.next();
                xml.append("<customModifierName>");
                xml.append(customModifierName);
                xml.append("</customModifierName>\n");
                xml.append("<customModifierValue>");
                FreeRealmTileModifier freeRealmTileModifier = (FreeRealmTileModifier) tile.getCustomModifier(customModifierName);
                xml.append(new FreeRealmTileModifierXMLConverter().toXML(freeRealmTileModifier) + "\n");
                xml.append("</customModifierValue>");
                xml.append("</customModifier>\n");
            }
            xml.append("</customModifiers>\n");
        }
        xml.append(XMLConverterUtility.convertPropertiesToXML(tile.getCustomProperties(), "customProperties"));
        xml.append("</tile>");
        return xml.toString();
    }

    public Tile initializeFromNode(Realm realm, Node node) {
        Tile tile = new FreeRealmTile();
        Node typeNode = XMLConverterUtility.findNode(node, "type");
        if (typeNode != null) {
            int tileTypeId = Integer.parseInt(typeNode.getFirstChild().getNodeValue());
            TileType tileType = realm.getTileTypeManager().getTileType(tileTypeId);
            tile.setType(tileType);
        }
        Node improvementsNode = XMLConverterUtility.findNode(node, "improvements");
        if (improvementsNode != null) {
            tile.clearImprovements();
            for (Node improvementNode = improvementsNode.getFirstChild(); improvementNode != null; improvementNode = improvementNode.getNextSibling()) {
                if (improvementNode.getNodeType() == Node.ELEMENT_NODE) {
                    int improvementId = Integer.parseInt(improvementNode.getFirstChild().getNodeValue());
                    TileImprovementType improvement = realm.getTileImprovementTypeManager().getImprovement(improvementId);
                    tile.addImprovement(improvement);
                }
            }
        }
        Node vegetationNode = XMLConverterUtility.findNode(node, "vegetation");
        if (vegetationNode != null) {
            Vegetation vegetation = new FreeRealmVegetationXMLConverter().initializeFromNode(realm, vegetationNode);
            tile.setVegetation(vegetation);
        }
        Node bonusResourceNode = XMLConverterUtility.findNode(node, "bonusResource");
        if (bonusResourceNode != null) {
            int bonusResourceId = Integer.parseInt(bonusResourceNode.getFirstChild().getNodeValue());
            tile.setBonusResource(realm.getBonusResourceManager().getBonusResource(bonusResourceId));
        }
        Node customModifiersNode = XMLConverterUtility.findNode(node, "customModifiers");
        if (customModifiersNode != null) {
            tile.clearCustomModifiers();
            for (Node customModifierNode = customModifiersNode.getFirstChild(); customModifierNode != null; customModifierNode = customModifierNode.getNextSibling()) {
                if (customModifierNode.getNodeType() == Node.ELEMENT_NODE) {
                    Node customModifierNameNode = XMLConverterUtility.findNode(customModifierNode, "customModifierName");
                    String customModifierName = customModifierNameNode.getFirstChild().getNodeValue();
                    Node customModifierValueNode = XMLConverterUtility.findNode(customModifierNode, "customModifierValue");
                    FreeRealmTileModifier customTileModifier = new FreeRealmTileModifierXMLConverter().initializeFromNode(realm, customModifierValueNode.getFirstChild());
                    tile.addCustomModifier(customModifierName, customTileModifier);
                }
            }
        }
        Node collectableNode = XMLConverterUtility.findNode(node, "collectable");
        if (collectableNode != null) {
            for (Node collectableSubNode = collectableNode.getFirstChild(); collectableSubNode != null; collectableSubNode = collectableSubNode.getNextSibling()) {
                if (collectableSubNode.getNodeType() == Node.ELEMENT_NODE) {
                    Collectable collectable = null;
                    String xMLConverterName = TagManager.getXMLConverterName(collectableSubNode.getNodeName());
                    try {
                        XMLConverter<Collectable> xMLConverter = (XMLConverter<Collectable>) Class.forName(xMLConverterName).newInstance();
                        collectable = xMLConverter.initializeFromNode(realm, collectableSubNode);
                    } catch (Exception exception) {
                    }
                    tile.setCollectable(collectable);
                }
            }
        }
        Node customPropertiesNode = XMLConverterUtility.findNode(node, "customProperties");
        if (customPropertiesNode != null) {
            Properties properties = XMLConverterUtility.convertNodeToProperties(customPropertiesNode);
            tile.setCustomProperties(properties);
        }
        return tile;
    }
}

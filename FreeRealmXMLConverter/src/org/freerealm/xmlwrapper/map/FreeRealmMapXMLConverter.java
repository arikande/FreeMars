package org.freerealm.xmlwrapper.map;

import org.freerealm.Realm;
import org.freerealm.map.FreeRealmMap;
import org.freerealm.tile.Tile;
import org.freerealm.xmlwrapper.XMLConverter;
import org.freerealm.xmlwrapper.XMLConverterUtility;
import org.w3c.dom.Node;

/**
 *
 * @author Deniz ARIKAN
 */
public class FreeRealmMapXMLConverter implements XMLConverter<FreeRealmMap> {

    public String toXML(FreeRealmMap worldMap) {
        StringBuilder xml = new StringBuilder();
        xml.append("<free_realm_map>\n");
        xml.append("<name>");
        String mapName = worldMap.getName() == null ? "" : worldMap.getName();
        xml.append(mapName);
        xml.append("</name>\n");
        xml.append("<description>");
        String mapDescription = worldMap.getDescription() == null ? "" : worldMap.getDescription();
        xml.append(mapDescription);
        xml.append("</description>\n");
        xml.append("<suggested_players>");
        int suggestedPlayers = worldMap.getSuggestedPlayers() <= 0 ? 4 : worldMap.getSuggestedPlayers();
        xml.append(suggestedPlayers);
        xml.append("</suggested_players>\n");
        xml.append("<width>");
        int mapWidth = worldMap.getWidth() <= 0 ? 40 : worldMap.getWidth();
        xml.append(mapWidth);
        xml.append("</width>\n");
        xml.append("<height>" + worldMap.getHeight() + "</height>\n");
        xml.append("<tiles>\n");
        for (int i = 0; i < worldMap.getWidth(); i++) {
            for (int j = 0; j < worldMap.getHeight(); j++) {
                xml.append(new TileXMLConverter().toXML(worldMap.getMapItems()[i][j]) + "\n");
            }
        }
        xml.append("</tiles>\n");
        xml.append("</free_realm_map>\n");
        return xml.toString();
    }

    public FreeRealmMap initializeFromNode(Realm realm, Node node) {
        FreeRealmMap worldMap = new FreeRealmMap();
        String name = "";
        String description = "";
        int suggestedPlayers = 0;
        int width = 0;
        int height = 0;
        Node tilesNode = null;
        if (node != null) {
            Node nameNode = XMLConverterUtility.findNode(node, "name");
            if (nameNode != null && nameNode.getFirstChild() != null) {
                name = nameNode.getFirstChild().getNodeValue();
            }
            Node descriptionNode = XMLConverterUtility.findNode(node, "description");
            if (descriptionNode != null && descriptionNode.getFirstChild() != null) {
                description = descriptionNode.getFirstChild().getNodeValue();
            }
            Node suggestedPlayersNode = XMLConverterUtility.findNode(node, "suggested_players");
            if (suggestedPlayersNode != null) {
                suggestedPlayers = Integer.parseInt(suggestedPlayersNode.getFirstChild().getNodeValue());
            }
            Node widthNode = XMLConverterUtility.findNode(node, "width");
            if (widthNode != null) {
                width = Integer.parseInt(widthNode.getFirstChild().getNodeValue());
            }
            Node heightNode = XMLConverterUtility.findNode(node, "height");
            if (heightNode != null) {
                height = Integer.parseInt(heightNode.getFirstChild().getNodeValue());
            }
            tilesNode = XMLConverterUtility.findNode(node, "tiles");
        }
        worldMap.setName(name);
        worldMap.setDescription(description);
        worldMap.setSuggestedPlayers(suggestedPlayers);
        if (tilesNode != null) {
            Tile[][] mapItems = new Tile[width][height];
            int abscissa = 0;
            int ordinate = 0;
            for (Node tileNode = tilesNode.getFirstChild(); tileNode != null; tileNode = tileNode.getNextSibling()) {
                if (tileNode.getNodeType() == Node.ELEMENT_NODE) {
                    Tile tile = new TileXMLConverter().initializeFromNode(realm, tileNode);
                    mapItems[abscissa][ordinate] = tile;
                    ordinate = ordinate + 1;
                    if (ordinate >= height) {
                        ordinate = 0;
                        abscissa = abscissa + 1;
                    }
                }
            }
            worldMap.setMapItems(mapItems);
        }
        return worldMap;
    }
}

package org.freerealm.xmlwrapper.player;

import java.awt.Color;
import java.util.Iterator;
import org.freerealm.Realm;
import org.freerealm.diplomacy.Diplomacy;
import org.freerealm.map.Coordinate;
import org.freerealm.player.DefaultMessage;
import org.freerealm.player.FreeRealmPlayer;
import org.freerealm.player.Message;
import org.freerealm.player.SettlementManager;
import org.freerealm.player.mission.Mission;
import org.freerealm.property.Property;
import org.freerealm.xmlwrapper.PropertyFactory;
import org.freerealm.xmlwrapper.TagManager;
import org.freerealm.xmlwrapper.XMLConverter;
import org.freerealm.xmlwrapper.XMLConverterUtility;
import org.freerealm.xmlwrapper.city.SettlementManagerXMLConverter;
import org.freerealm.xmlwrapper.diplomacy.DiplomacyXMLConverter;
import org.freerealm.xmlwrapper.map.CoordinateXMLConverter;
import org.freerealm.xmlwrapper.unit.UnitManagerXMLWrapper;
import org.w3c.dom.Node;

/**
 *
 * @author Deniz ARIKAN
 */
public class FreeRealmPlayerXMLConverter implements XMLConverter<FreeRealmPlayer> {

    public String toXML(FreeRealmPlayer player) {
        StringBuilder xml = new StringBuilder();
        xml.append("<FreeRealmPlayer>\n");
        xml.append(getInnerXML(player));
        xml.append("</FreeRealmPlayer>");
        return xml.toString();
    }

    public FreeRealmPlayer initializeFromNode(Realm realm, Node node) {
        FreeRealmPlayer freeRealmPlayer = new FreeRealmPlayer(realm);
        populatePlayerFromNode(freeRealmPlayer, realm, node);
        return freeRealmPlayer;
    }

    public static String getInnerXML(FreeRealmPlayer player) {
        StringBuilder xml = new StringBuilder();
        xml.append("<id>");
        xml.append(player.getId());
        xml.append("</id>\n");
        xml.append("<status>");
        xml.append(player.getStatus());
        xml.append("</status>\n");
        xml.append("<name>");
        xml.append(player.getName());
        xml.append("</name>\n");
        xml.append("<primaryColor>");
        xml.append(player.getPrimaryColor().getRed());
        xml.append(",");
        xml.append(player.getPrimaryColor().getGreen());
        xml.append(",");
        xml.append(player.getPrimaryColor().getBlue());
        xml.append("</primaryColor>\n");
        xml.append("<secondaryColor>");
        xml.append(player.getSecondaryColor().getRed());
        xml.append(",");
        xml.append(player.getSecondaryColor().getGreen());
        xml.append(",");
        xml.append(player.getSecondaryColor().getBlue());
        xml.append("</secondaryColor>\n");
        xml.append("<turnEnded>");
        xml.append(player.isTurnEnded());
        xml.append("</turnEnded>\n");
        xml.append("<wealth>");
        xml.append(player.getWealth());
        xml.append("</wealth>\n");
        xml.append("<taxRate>");
        xml.append(player.getTaxRate());
        xml.append("</taxRate>\n");
        xml.append("<nation>");
        xml.append(player.getNation().getId());
        xml.append("</nation>");
        xml.append(System.getProperty("line.separator"));

        xml.append("<cleared_vegetation_count>");
        xml.append(player.getClearedVegetationCount());
        xml.append("</cleared_vegetation_count>");
        xml.append(System.getProperty("line.separator"));

        xml.append(System.getProperty("line.separator"));
        xml.append(new DiplomacyXMLConverter().toXML(player.getDiplomacy()));
        xml.append(System.getProperty("line.separator"));
        xml.append("<Properties>\n");
        Iterator<Property> propertyIterator = player.getPropertiesIterator();
        while (propertyIterator.hasNext()) {
            Property property = propertyIterator.next();
            String xMLWrapperName = TagManager.getXMLConverterName(property.getName());
            Class c;
            try {
                c = Class.forName(xMLWrapperName);
                XMLConverter<Property> xMLConverter = (XMLConverter<Property>) c.newInstance();
                xml.append(xMLConverter.toXML(property));
                xml.append("\n");
            } catch (Exception ex) {
            }
        }
        xml.append("</Properties>\n");
        xml.append(new SettlementManagerXMLConverter(player).toXML(player.getSettlementManager()));
        xml.append("\n");
        xml.append(new UnitManagerXMLWrapper(player.getUnitManager()).toXML());
        xml.append("\n");
        xml.append("<builtTileImprovementsCount>\n");
        Iterator<Integer> iterator = player.getBuiltTileImprovementCountIterator();
        while (iterator.hasNext()) {
            xml.append("<tileImprovementCount>\n");
            Integer typeId = iterator.next();
            int count = player.getBuiltTileImprovementCount(typeId);
            xml.append("<typeId>");
            xml.append(typeId);
            xml.append("</typeId>");
            xml.append("<count>");
            xml.append(count);
            xml.append("</count>");
            xml.append("</tileImprovementCount>\n");
        }
        xml.append("</builtTileImprovementsCount>\n");

        xml.append("<exploredCoordinates>\n");
        Iterator<Coordinate> exploredCoordinatesIterator = player.getExploredCoordinatesIterator();
        while (exploredCoordinatesIterator.hasNext()) {
            Coordinate coordinate = exploredCoordinatesIterator.next();
            xml.append(new CoordinateXMLConverter().toXML(coordinate));
            xml.append("\n");
        }
        xml.append("</exploredCoordinates>\n");

        xml.append("<missions>\n");
        Iterator<Mission> missionIterator = player.getMissionsIterator();
        while (missionIterator.hasNext()) {
            Mission mission = missionIterator.next();
            String xMLConverterName = TagManager.getXMLConverterName(mission.getMissionName());
            Class c;
            try {
                c = Class.forName(xMLConverterName);
                XMLConverter<Mission> xMLConverter = (XMLConverter<Mission>) c.newInstance();
                xml.append(xMLConverter.toXML(mission));
            } catch (Exception ex) {
            }
        }
        xml.append("</missions>\n");
        xml.append("<messages>\n");
        Iterator<Message> messagesIterator = player.getMessagesIterator();
        while (messagesIterator.hasNext()) {
            Message message = messagesIterator.next();
            DefaultMessage defaultMessage = (DefaultMessage) message;
            xml.append(new MessageXMLConverter().toXML(defaultMessage));
        }
        xml.append("</messages>\n");
        return xml.toString();
    }

    public static void populatePlayerFromNode(FreeRealmPlayer player, Realm realm, Node node) {

        Node diplomacyNode = XMLConverterUtility.findNode(node, "diplomacy");
        Diplomacy diplomacy = (new DiplomacyXMLConverter()).initializeFromNode(realm, diplomacyNode);
        player.setDiplomacy(diplomacy);

        for (Node subNode = node.getFirstChild(); subNode != null; subNode = subNode.getNextSibling()) {
            if (subNode.getNodeType() == Node.ELEMENT_NODE) {
                if (subNode.getNodeName().equals("id")) {
                    int playerId = Integer.parseInt(subNode.getFirstChild().getNodeValue());
                    player.setId(playerId);
                } else if (subNode.getNodeName().equals("status")) {
                    int status = Integer.parseInt(subNode.getFirstChild().getNodeValue());
                    player.setStatus(status);
                } else if (subNode.getNodeName().equals("name")) {
                    player.setName(subNode.getFirstChild().getNodeValue());
                } else if (subNode.getNodeName().equals("primaryColor")) {
                    String colorString = subNode.getFirstChild().getNodeValue();
                    int index = colorString.indexOf(",");
                    int red = Integer.parseInt(colorString.substring(0, index));
                    colorString = colorString.substring(index + 1);
                    index = colorString.indexOf(",");
                    int green = Integer.parseInt(colorString.substring(0, index));
                    colorString = colorString.substring(index + 1);
                    int blue = Integer.parseInt(colorString);
                    player.setPrimaryColor(new Color(red, green, blue));
                } else if (subNode.getNodeName().equals("secondaryColor")) {
                    String colorString = subNode.getFirstChild().getNodeValue();
                    int index = colorString.indexOf(",");
                    int red = Integer.parseInt(colorString.substring(0, index));
                    colorString = colorString.substring(index + 1);
                    index = colorString.indexOf(",");
                    int green = Integer.parseInt(colorString.substring(0, index));
                    colorString = colorString.substring(index + 1);
                    int blue = Integer.parseInt(colorString);
                    player.setSecondaryColor(new Color(red, green, blue));
                } else if (subNode.getNodeName().equals("turnEnded")) {
                    Boolean turnEndedValue = Boolean.valueOf(subNode.getFirstChild().getNodeValue());
                    player.setTurnEnded(turnEndedValue);
                } else if (subNode.getNodeName().equals("wealth")) {
                    int loadedWealth = Integer.parseInt(subNode.getFirstChild().getNodeValue());
                    player.setWealth(loadedWealth);
                } else if (subNode.getNodeName().equals("taxRate")) {
                    int loadedTaxRate = Integer.parseInt(subNode.getFirstChild().getNodeValue());
                    player.setTaxRate(loadedTaxRate);
                } else if (subNode.getNodeName().equals("nation")) {
                    int nationId = Integer.parseInt(subNode.getFirstChild().getNodeValue());
                    player.setNation(realm.getNationManager().getNation(nationId));
                } else if (subNode.getNodeName().equals("cleared_vegetation_count")) {
                    int clearedVegetationCount = Integer.parseInt(subNode.getFirstChild().getNodeValue());
                    player.setClearedVegetationCount(clearedVegetationCount);
                } else if (subNode.getNodeName().equals("Properties")) {
                    for (Node propertyNode = subNode.getFirstChild(); propertyNode != null; propertyNode = propertyNode.getNextSibling()) {
                        if (propertyNode.getNodeType() == Node.ELEMENT_NODE) {
                            Property ability = PropertyFactory.createProperty(realm, propertyNode);
                            player.addProperty(ability);
                        }
                    }
                } else if (subNode.getNodeName().equals("settlements")) {
                    SettlementManagerXMLConverter settlementManagerXMLConverter = new SettlementManagerXMLConverter(player);
                    SettlementManager settlementManager = settlementManagerXMLConverter.initializeFromNode(realm, subNode);
                    player.setSettlementManager(settlementManager);
                } else if (subNode.getNodeName().equals("units")) {
                    new UnitManagerXMLWrapper(player.getUnitManager()).initializeFromNode(realm, subNode);
                } else if (subNode.getNodeName().equals("builtTileImprovementsCount")) {

                    player.clearBuiltTileImprovementCount();
                    for (Node tileImprovementCountNode = subNode.getFirstChild(); tileImprovementCountNode != null; tileImprovementCountNode = tileImprovementCountNode.getNextSibling()) {
                        if (tileImprovementCountNode.getNodeType() == Node.ELEMENT_NODE) {
                            Node typeIdNode = XMLConverterUtility.findNode(tileImprovementCountNode, "typeId");
                            int typeId = Integer.parseInt(typeIdNode.getFirstChild().getNodeValue());
                            Node countNode = XMLConverterUtility.findNode(tileImprovementCountNode, "count");
                            int count = Integer.parseInt(countNode.getFirstChild().getNodeValue());
                            player.setBuiltTileImprovementCount(typeId, count);
                        }
                    }

                } else if (subNode.getNodeName().equals("exploredCoordinates")) {
                    player.clearExploredCoordinates();
                    for (Node exploredCoordinateNode = subNode.getFirstChild(); exploredCoordinateNode != null; exploredCoordinateNode = exploredCoordinateNode.getNextSibling()) {
                        if (exploredCoordinateNode.getNodeType() == Node.ELEMENT_NODE) {
                            player.addExploredCoordinate(new CoordinateXMLConverter().initializeFromNode(realm, exploredCoordinateNode));
                        }
                    }
                } else if (subNode.getNodeName().equals("missions")) {
                    player.clearMissions();
                    for (Node missionNode = subNode.getFirstChild(); missionNode != null; missionNode = missionNode.getNextSibling()) {
                        if (missionNode.getNodeType() == Node.ELEMENT_NODE) {
                            Mission mission = createMission(realm, missionNode);
                            mission.setPlayer(player);
                            player.addMission(mission);
                        }
                    }
                } else if (subNode.getNodeName().equals("messages")) {
                    player.clearMessages();
                    for (Node messageNode = subNode.getFirstChild(); messageNode != null; messageNode = messageNode.getNextSibling()) {
                        if (messageNode.getNodeType() == Node.ELEMENT_NODE) {
                            DefaultMessage message = (new MessageXMLConverter()).initializeFromNode(realm, messageNode);
                            player.addMessage(message);
                        }
                    }
                }
            }
        }
    }

    private static Mission createMission(Realm realm, Node node) {
        Mission mission = null;
        String xMLConverterName = TagManager.getXMLConverterName(node.getNodeName());
        try {
            XMLConverter<Mission> xMLConverter = (XMLConverter<Mission>) Class.forName(xMLConverterName).newInstance();
            mission = xMLConverter.initializeFromNode(realm, node);
        } catch (Exception exception) {
        }
        return mission;
    }
}

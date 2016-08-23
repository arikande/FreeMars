package org.freerealm.xmlwrapper.history;

import java.util.Iterator;
import org.freerealm.Realm;
import org.freerealm.history.FreeRealmPlayerTurnHistory;
import org.freerealm.history.PlayerTurnHistory;
import org.freerealm.xmlwrapper.XMLConverter;
import org.freerealm.xmlwrapper.XMLConverterUtility;
import org.w3c.dom.Node;

/**
 *
 * @author Deniz ARIKAN
 */
public class FreeRealmPlayerTurnHistoryXMLConverter implements XMLConverter<PlayerTurnHistory> {

    public String toXML(PlayerTurnHistory playerTurnHistory) {
        StringBuilder xml = new StringBuilder();
        xml.append("<playerTurnHistory>\n");
        xml.append("<turn>" + playerTurnHistory.getTurn() + "</turn>\n");
        xml.append("<population>" + playerTurnHistory.getPopulation() + "</population>\n");
        xml.append("<wealth>" + playerTurnHistory.getWealth() + "</wealth>\n");
        xml.append("<settlementCount>" + playerTurnHistory.getSettlementCount() + "</settlementCount>\n");
        xml.append("<unitCount>" + playerTurnHistory.getUnitCount() + "</unitCount>\n");
        xml.append("<mapExplorationPercent>" + playerTurnHistory.getMapExplorationPercent() + "</mapExplorationPercent>\n");
        xml.append("<customData>\n");
        Iterator<String> customDataIterator = playerTurnHistory.getCustomDataIterator();
        while (customDataIterator.hasNext()) {
            String key = customDataIterator.next();
            String value = (String) playerTurnHistory.getCustomData(key);
            xml.append("<customEntry>\n");
            xml.append("<key>" + key + "</key>\n");
            xml.append("<value>" + value + "</value>\n");
            xml.append("</customEntry>\n");
        }
        xml.append("</customData>\n");
        xml.append("</playerTurnHistory>\n");
        return xml.toString();
    }

    public PlayerTurnHistory initializeFromNode(Realm realm, Node node) {
        PlayerTurnHistory playerTurnHistory = new FreeRealmPlayerTurnHistory();

        Node turnNode = XMLConverterUtility.findNode(node, "turn");
        int turn = Integer.parseInt(turnNode.getFirstChild().getNodeValue());
        playerTurnHistory.setTurn(turn);

        Node populationNode = XMLConverterUtility.findNode(node, "population");
        int population = Integer.parseInt(populationNode.getFirstChild().getNodeValue());
        playerTurnHistory.setPopulation(population);

        Node wealthNode = XMLConverterUtility.findNode(node, "wealth");
        int wealth = Integer.parseInt(wealthNode.getFirstChild().getNodeValue());
        playerTurnHistory.setWealth(wealth);

        Node settlementCountNode = XMLConverterUtility.findNode(node, "settlementCount");
        int settlementCount = Integer.parseInt(settlementCountNode.getFirstChild().getNodeValue());
        playerTurnHistory.setSettlementCount(settlementCount);

        Node unitCountNode = XMLConverterUtility.findNode(node, "unitCount");
        int unitCount = Integer.parseInt(unitCountNode.getFirstChild().getNodeValue());
        playerTurnHistory.setUnitCount(unitCount);

        Node mapExplorationPercentNode = XMLConverterUtility.findNode(node, "mapExplorationPercent");
        int mapExplorationPercent = Integer.parseInt(mapExplorationPercentNode.getFirstChild().getNodeValue());
        playerTurnHistory.setMapExplorationPercent(mapExplorationPercent);

        Node customDataNode = XMLConverterUtility.findNode(node, "customData");
        for (Node customEntryNode = customDataNode.getFirstChild(); customEntryNode != null; customEntryNode = customEntryNode.getNextSibling()) {
            if (customEntryNode.getNodeType() == Node.ELEMENT_NODE) {
                Node keyNode = XMLConverterUtility.findNode(customEntryNode, "key");
                String key = keyNode.getFirstChild().getNodeValue();
                Node valueNode = XMLConverterUtility.findNode(customEntryNode, "value");
                String value = valueNode.getFirstChild().getNodeValue();
                playerTurnHistory.addCustomData(key, value);
            }
        }

        return playerTurnHistory;

    }
}

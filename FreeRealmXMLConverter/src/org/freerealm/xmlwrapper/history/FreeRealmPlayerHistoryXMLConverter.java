package org.freerealm.xmlwrapper.history;

import java.util.Iterator;
import org.freerealm.Realm;
import org.freerealm.history.FreeRealmPlayerHistory;
import org.freerealm.history.PlayerHistory;
import org.freerealm.history.PlayerTurnHistory;
import org.freerealm.xmlwrapper.XMLConverter;
import org.freerealm.xmlwrapper.XMLConverterUtility;
import org.w3c.dom.Node;

/**
 *
 * @author Deniz ARIKAN
 */
public class FreeRealmPlayerHistoryXMLConverter implements XMLConverter<PlayerHistory> {

    public String toXML(PlayerHistory playerHistory) {
        StringBuilder xml = new StringBuilder();
        xml.append("<playerHistory>\n");
        xml.append("<playerId>" + playerHistory.getPlayer().getId() + "</playerId>\n");
        xml.append("<playerHistoryTurns>\n");
        Iterator<PlayerTurnHistory> playerTurnHistoryIterator = playerHistory.getPlayerTurnHistoryIterator();
        while (playerTurnHistoryIterator.hasNext()) {
            PlayerTurnHistory playerTurnHistory = playerTurnHistoryIterator.next();
            xml.append(new FreeRealmPlayerTurnHistoryXMLConverter().toXML(playerTurnHistory));
        }
        xml.append("</playerHistoryTurns>\n");
        xml.append("</playerHistory>\n");
        return xml.toString();
    }

    public PlayerHistory initializeFromNode(Realm realm, Node node) {
        PlayerHistory playerHistory = new FreeRealmPlayerHistory();
        Node playerIdNode = XMLConverterUtility.findNode(node, "playerId");
        int playerId = Integer.parseInt(playerIdNode.getFirstChild().getNodeValue());
        playerHistory.setPlayer(realm.getPlayerManager().getPlayer(playerId));
        Node playerHistoryTurnsNode = XMLConverterUtility.findNode(node, "playerHistoryTurns");
        for (Node playerTurnHistoryNode = playerHistoryTurnsNode.getFirstChild(); playerTurnHistoryNode != null; playerTurnHistoryNode = playerTurnHistoryNode.getNextSibling()) {
            if (playerTurnHistoryNode.getNodeType() == Node.ELEMENT_NODE) {
                PlayerTurnHistory playerTurnHistory = new FreeRealmPlayerTurnHistoryXMLConverter().initializeFromNode(realm, playerTurnHistoryNode);
                playerHistory.addTurnHistory(playerTurnHistory);
            }
        }
        return playerHistory;
    }
}

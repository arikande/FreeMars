package org.freerealm.xmlwrapper.history;

import java.util.Iterator;
import org.freerealm.Realm;
import org.freerealm.history.FreeRealmHistory;
import org.freerealm.history.History;
import org.freerealm.history.PlayerHistory;
import org.freerealm.player.Player;
import org.freerealm.xmlwrapper.XMLConverter;
import org.w3c.dom.Node;

/**
 *
 * @author Deniz ARIKAN
 */
public class FreeRealmHistoryXMLConverter implements XMLConverter<History> {

    public String toXML(History history) {
        StringBuilder xml = new StringBuilder();
        xml.append("<history>\n");
        Iterator<Player> iterator = history.getPlayersIterator();
        while (iterator.hasNext()) {
            Player player = iterator.next();
            PlayerHistory playerHistory = history.getPlayerHistory(player);
            xml.append(new FreeRealmPlayerHistoryXMLConverter().toXML(playerHistory));
        }
        xml.append("</history>\n");
        return xml.toString();
    }

    public History initializeFromNode(Realm realm, Node node) {
        FreeRealmHistory freeRealmHistory = new FreeRealmHistory();
        for (Node playerHistoryNode = node.getFirstChild(); playerHistoryNode != null; playerHistoryNode = playerHistoryNode.getNextSibling()) {
            if (playerHistoryNode.getNodeType() == Node.ELEMENT_NODE) {
                PlayerHistory playerHistory = new FreeRealmPlayerHistoryXMLConverter().initializeFromNode(realm, playerHistoryNode);
                freeRealmHistory.addPlayerHistory(playerHistory.getPlayer(), playerHistory);
            }
        }
        return freeRealmHistory;
    }
}

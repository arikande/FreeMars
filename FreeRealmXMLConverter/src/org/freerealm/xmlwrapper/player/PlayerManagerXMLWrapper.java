package org.freerealm.xmlwrapper.player;

import java.util.Iterator;
import org.freerealm.Realm;
import org.freerealm.player.Player;
import org.freerealm.player.PlayerManager;
import org.freerealm.xmlwrapper.TagManager;
import org.freerealm.xmlwrapper.XMLConverter;
import org.freerealm.xmlwrapper.XMLWrapper;
import org.w3c.dom.Node;

/**
 *
 * @author Deniz ARIKAN
 */
public class PlayerManagerXMLWrapper implements XMLWrapper {

    PlayerManager playerManager;

    public PlayerManagerXMLWrapper(PlayerManager playerManager) {
        this.playerManager = playerManager;
    }

    public String toXML() {
        StringBuilder xml = new StringBuilder();
        xml.append("<Players>\n");
        for (Iterator<Player> iterator = playerManager.getPlayersIterator(); iterator.hasNext();) {
            Player player = iterator.next();
            String xMLConverterName = TagManager.getXMLConverterName(player.getClass());
            try {
                XMLConverter<Player> xMLConverter = (XMLConverter<Player>) Class.forName(xMLConverterName).newInstance();
                xml.append(xMLConverter.toXML(player) + "\n");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        xml.append("<activePlayer>" + playerManager.getActivePlayer().getId() + "</activePlayer>");
        xml.append("</Players>");
        return xml.toString();
    }

    public void initializeFromNode(Realm realm, Node node) {
        int activePlayerId = 0;
        for (Node subNode = node.getFirstChild(); subNode != null; subNode = subNode.getNextSibling()) {
            if (subNode.getNodeType() == Node.ELEMENT_NODE) {
                if (subNode.getNodeName().equals("activePlayer")) {
                    activePlayerId = Integer.parseInt(subNode.getFirstChild().getNodeValue());
                } else {
                    String xMLConverterName = TagManager.getXMLConverterName(subNode.getNodeName());
                    try {
                        XMLConverter<Player> xMLConverter = (XMLConverter<Player>) Class.forName(xMLConverterName).newInstance();
                        Player player = xMLConverter.initializeFromNode(realm, subNode);
                        playerManager.addPlayer(player);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }
        playerManager.setActivePlayer(playerManager.getPlayer(activePlayerId));
    }
}

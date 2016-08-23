package org.freerealm.xmlwrapper.diplomacy;

import org.freerealm.Realm;
import org.freerealm.diplomacy.PlayerRelation;
import org.freerealm.xmlwrapper.XMLConverter;
import org.freerealm.xmlwrapper.XMLConverterUtility;
import org.w3c.dom.Node;

/**
 *
 * @author Deniz ARIKAN
 */
public class PlayerRelationXMLConverter implements XMLConverter<PlayerRelation> {

    public String toXML(PlayerRelation playerRelation) {
        StringBuilder xml = new StringBuilder();
        int targetPlayerId = playerRelation.getTargetPlayerId();
        xml.append("<relation>");
        xml.append(System.getProperty("line.separator"));
        xml.append("<target_player_id>");
        xml.append(targetPlayerId);
        xml.append("</target_player_id>");
        xml.append(System.getProperty("line.separator"));
        xml.append("<status>");
        xml.append(playerRelation.getStatus());
        xml.append("</status>");
        xml.append(System.getProperty("line.separator"));
        xml.append("<attitude>");
        xml.append(playerRelation.getAttitude());
        xml.append("</attitude>");
        xml.append(System.getProperty("line.separator"));
        xml.append("</relation>\n");
        return xml.toString();
    }

    public PlayerRelation initializeFromNode(Realm realm, Node node) {
        int targetPlayerId = Integer.parseInt(XMLConverterUtility.findNode(node, "target_player_id").getFirstChild().getNodeValue());
        int status = Integer.parseInt(XMLConverterUtility.findNode(node, "status").getFirstChild().getNodeValue());
        int attitude = Integer.parseInt(XMLConverterUtility.findNode(node, "attitude").getFirstChild().getNodeValue());
        PlayerRelation playerRelation = new PlayerRelation(targetPlayerId);
        playerRelation.setStatus(status);
        playerRelation.setAttitude(attitude);
        return playerRelation;
    }

}

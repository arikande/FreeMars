package org.freemars.ai;

import org.freerealm.Realm;
import org.freerealm.xmlwrapper.XMLConverter;
import org.freerealm.xmlwrapper.XMLConverterUtility;
import org.freerealm.xmlwrapper.player.FreeRealmPlayerXMLConverter;
import org.w3c.dom.Node;

/**
 *
 * @author Deniz ARIKAN
 */
public class ExpeditionaryForcePlayerXMLConverter implements XMLConverter<ExpeditionaryForcePlayer> {

    public String toXML(ExpeditionaryForcePlayer player) {
        StringBuilder xml = new StringBuilder();
        xml.append("<ExpeditionaryForcePlayer>\n");
        xml.append(FreeRealmPlayerXMLConverter.getInnerXML(player));
        xml.append("\n");
        xml.append("<targetPlayerId>");
        xml.append(player.getTargetPlayerId());
        xml.append("</targetPlayerId>");
        xml.append("\n");
        xml.append("<earth_to_mars_flight_turns>");
        xml.append(player.getEarthToMarsFlightTurns());
        xml.append("</earth_to_mars_flight_turns>");
        xml.append("\n");
        xml.append("<remaining_attack_waves>");
        xml.append(player.getRemainingAttackWaves());
        xml.append("</remaining_attack_waves>");
        xml.append("\n");
        xml.append("</ExpeditionaryForcePlayer>");
        return xml.toString();
    }

    public ExpeditionaryForcePlayer initializeFromNode(Realm realm, Node node) {
        ExpeditionaryForcePlayer expeditionaryForcePlayer = new ExpeditionaryForcePlayer(realm);
        FreeRealmPlayerXMLConverter.populatePlayerFromNode(expeditionaryForcePlayer, realm, node);

        Node targetPlayerIdNode = XMLConverterUtility.findNode(node, "targetPlayerId");
        if (targetPlayerIdNode != null) {
            expeditionaryForcePlayer.setTargetPlayerId(Integer.parseInt(targetPlayerIdNode.getFirstChild().getNodeValue()));
        }

        Node earthToMarsFlightTurnsNode = XMLConverterUtility.findNode(node, "earth_to_mars_flight_turns");
        if (earthToMarsFlightTurnsNode != null) {
            expeditionaryForcePlayer.setEarthToMarsFlightTurns(Integer.parseInt(earthToMarsFlightTurnsNode.getFirstChild().getNodeValue()));
        } else {
            int defaultExpeditionaryForceFlightTurns = Integer.parseInt(realm.getProperty("expeditionary_force_flight_turns"));
            expeditionaryForcePlayer.setEarthToMarsFlightTurns(defaultExpeditionaryForceFlightTurns);
        }

        Node remainingAttackWavesNode = XMLConverterUtility.findNode(node, "remaining_attack_waves");
        if (remainingAttackWavesNode != null) {
            expeditionaryForcePlayer.setRemainingAttackWaves(Integer.parseInt(remainingAttackWavesNode.getFirstChild().getNodeValue()));
        }

        return expeditionaryForcePlayer;
    }
}

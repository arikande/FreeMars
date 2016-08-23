package org.freemars.ai;

import java.util.Properties;
import org.freemars.player.FreeMarsPlayerXMLConverter;
import org.freerealm.Realm;
import org.freerealm.xmlwrapper.XMLConverter;
import org.freerealm.xmlwrapper.XMLConverterUtility;
import org.w3c.dom.Node;

/**
 *
 * @author Deniz ARIKAN
 */
public class AIPlayerXMLConverter implements XMLConverter<AIPlayer> {

    public String toXML(AIPlayer player) {
        StringBuilder xml = new StringBuilder();
        xml.append("<ai_player>");
        xml.append(System.getProperty("line.separator"));
        xml.append(FreeMarsPlayerXMLConverter.getInnerXML(player));
        xml.append(System.getProperty("line.separator"));
        xml.append(XMLConverterUtility.convertPropertiesToXML(player.getAIProperties(), "ai_properties"));
        xml.append(System.getProperty("line.separator"));
        xml.append("</ai_player>");
        return xml.toString();
    }

    public AIPlayer initializeFromNode(Realm realm, Node node) {
        AIPlayer aiPlayer = new AIPlayer(realm);
        FreeMarsPlayerXMLConverter.populatePlayerFromNode(aiPlayer, realm, node);
        Node propertiesNode = XMLConverterUtility.findNode(node, "ai_properties");
        Properties aiProperties = XMLConverterUtility.convertNodeToProperties(propertiesNode);
        aiPlayer.setAIProperties(aiProperties);
        return aiPlayer;
    }
}

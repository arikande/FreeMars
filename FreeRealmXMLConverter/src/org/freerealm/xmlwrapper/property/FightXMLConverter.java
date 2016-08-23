package org.freerealm.xmlwrapper.property;

import org.freerealm.Realm;
import org.freerealm.property.Fight;
import org.freerealm.xmlwrapper.XMLConverter;
import org.w3c.dom.Node;

/**
 *
 * @author Deniz ARIKAN
 */
public class FightXMLConverter implements XMLConverter<Fight> {

    public String toXML(Fight fight) {
        StringBuilder xml = new StringBuilder();
        xml.append("<Fight");
        xml.append(" attackPoints=\"" + fight.getAttackPoints() + "\"");
        xml.append(" defencePoints=\"" + fight.getDefencePoints() + "\"");
        xml.append("/>");
        return xml.toString();
    }

    public Fight initializeFromNode(Realm realm, Node node) {
        Fight fight = new Fight();
        String attackPointsValue = node.getAttributes().getNamedItem("attackPoints").getNodeValue();
        fight.setAttackPoints(Integer.parseInt(attackPointsValue));
        String defencePointsValue = node.getAttributes().getNamedItem("defencePoints").getNodeValue();
        fight.setDefencePoints(Integer.parseInt(defencePointsValue));
        return fight;
    }
}

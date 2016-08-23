package org.freemars.mission;

import org.freerealm.Realm;
import org.freerealm.xmlwrapper.XMLConverter;
import org.freerealm.xmlwrapper.XMLConverterUtility;
import org.w3c.dom.Node;

/**
 *
 * @author Deniz ARIKAN
 */
public class WealthRewardXMLConverter implements XMLConverter<WealthReward> {

    public String toXML(WealthReward reward) {
        StringBuilder xml = new StringBuilder();
        xml.append("<wealthReward>\n");
        xml.append("<amount>" + reward.getAmount() + "</amount>\n");
        xml.append("</wealthReward>\n");
        return xml.toString();

    }

    public WealthReward initializeFromNode(Realm realm, Node node) {
        WealthReward reward = new WealthReward();
        Node amountNode = XMLConverterUtility.findNode(node, "amount");
        int amount = Integer.parseInt(amountNode.getFirstChild().getNodeValue());
        reward.setAmount(amount);
        return reward;
    }
}

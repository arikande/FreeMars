package org.freerealm.xmlwrapper.player.mission;

import org.freerealm.Realm;
import org.freerealm.player.mission.SettlementCountMission;
import org.freerealm.xmlwrapper.XMLConverter;
import org.freerealm.xmlwrapper.XMLConverterUtility;
import org.w3c.dom.Node;

/**
 *
 * @author Deniz ARIKAN
 */
public class SettlementCountMissionXMLConverter extends AbstractMissionXMLHelper implements XMLConverter<SettlementCountMission> {

    public String toXML(SettlementCountMission mission) {
        StringBuilder xml = new StringBuilder();
        xml.append("<settlementCountMission>\n");
        xml.append("<settlementCount>" + mission.getSettlementCount() + "</settlementCount>\n");
        xml.append(getInnerXML(mission));
        xml.append(getRewardsXML(mission));
        xml.append("</settlementCountMission>\n");
        return xml.toString();
    }

    public SettlementCountMission initializeFromNode(Realm realm, Node node) {
        SettlementCountMission mission = new SettlementCountMission();
        Node settlementCountNode = XMLConverterUtility.findNode(node, "settlementCount");
        int settlementCount = Integer.parseInt(settlementCountNode.getFirstChild().getNodeValue());
        mission.setSettlementCount(settlementCount);
        initializeMissionFromNode(realm, node, mission);
        initializeMissionRewardsFromNode(realm, node, mission);
        return mission;
    }
}

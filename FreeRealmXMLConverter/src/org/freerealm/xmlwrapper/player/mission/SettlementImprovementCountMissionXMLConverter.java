package org.freerealm.xmlwrapper.player.mission;

import java.util.Iterator;
import org.freerealm.Realm;
import org.freerealm.player.mission.SettlementImprovementCountMission;
import org.freerealm.xmlwrapper.XMLConverter;
import org.freerealm.xmlwrapper.XMLConverterUtility;
import org.w3c.dom.Node;

/**
 *
 * @author Deniz ARIKAN
 */
public class SettlementImprovementCountMissionXMLConverter extends AbstractMissionXMLHelper implements XMLConverter<SettlementImprovementCountMission> {

    public String toXML(SettlementImprovementCountMission settlementImprovementCountMission) {
        StringBuilder xml = new StringBuilder();
        xml.append("<settlementImprovementCountMission>\n");
        xml.append("<settlementImprovementCountTargets>\n");
        Iterator<Integer> targetImprovementTypesIterator = settlementImprovementCountMission.getTargetImprovementTypesIterator();
        while (targetImprovementTypesIterator.hasNext()) {
            int targetImprovementType = targetImprovementTypesIterator.next();
            int count = settlementImprovementCountMission.getTargetCountForImprovementType(targetImprovementType);
            xml.append("<settlementImprovementCountTarget>");
            xml.append("<typeId>" + targetImprovementType + "</typeId>");
            xml.append("<count>" + count + "</count>");
            xml.append("</settlementImprovementCountTarget>");
        }
        xml.append("</settlementImprovementCountTargets>\n");
        xml.append(getInnerXML(settlementImprovementCountMission));
        xml.append(getRewardsXML(settlementImprovementCountMission));
        xml.append("</settlementImprovementCountMission>\n");
        return xml.toString();
    }

    public SettlementImprovementCountMission initializeFromNode(Realm realm, Node node) {
        SettlementImprovementCountMission mission = new SettlementImprovementCountMission();
        Node settlementImprovementCountTargetsNode = XMLConverterUtility.findNode(node, "settlementImprovementCountTargets");
        for (Node countTargetNode = settlementImprovementCountTargetsNode.getFirstChild(); countTargetNode != null; countTargetNode = countTargetNode.getNextSibling()) {
            if (countTargetNode.getNodeType() == Node.ELEMENT_NODE) {
                Node idNode = XMLConverterUtility.findNode(countTargetNode, "typeId");
                Node countNode = XMLConverterUtility.findNode(countTargetNode, "count");
                int id = Integer.parseInt(idNode.getFirstChild().getNodeValue().toString());
                int count = Integer.parseInt(countNode.getFirstChild().getNodeValue().toString());
                mission.addSettlementImprovementCountTargetValue(id, count);
            }
        }
        initializeMissionFromNode(realm, node, mission);
        initializeMissionRewardsFromNode(realm, node, mission);
        return mission;
    }
}

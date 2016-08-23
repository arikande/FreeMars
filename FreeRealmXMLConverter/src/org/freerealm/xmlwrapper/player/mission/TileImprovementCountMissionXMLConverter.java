package org.freerealm.xmlwrapper.player.mission;

import org.freerealm.Realm;
import org.freerealm.player.mission.TileImprovementCountMission;
import org.freerealm.xmlwrapper.XMLConverter;
import org.freerealm.xmlwrapper.XMLConverterUtility;
import org.w3c.dom.Node;

/**
 *
 * @author Deniz ARIKAN
 */
public class TileImprovementCountMissionXMLConverter extends AbstractMissionXMLHelper implements XMLConverter<TileImprovementCountMission> {

    public String toXML(TileImprovementCountMission mission) {
        StringBuilder xml = new StringBuilder();
        xml.append("<tileImprovementCountMission>\n");
        xml.append("<tileImprovementId>" + mission.getTileImprovementId() + "</tileImprovementId>\n");
        xml.append("<tileImprovementCount>" + mission.getTileImprovementCount() + "</tileImprovementCount>\n");
        xml.append(getInnerXML(mission));
        xml.append(getRewardsXML(mission));
        xml.append("</tileImprovementCountMission>\n");
        return xml.toString();
    }

    public TileImprovementCountMission initializeFromNode(Realm realm, Node node) {
        TileImprovementCountMission mission = new TileImprovementCountMission();
        Node tileImprovementIdNode = XMLConverterUtility.findNode(node, "tileImprovementId");
        int tileImprovementId = Integer.parseInt(tileImprovementIdNode.getFirstChild().getNodeValue());
        mission.setTileImprovementId(tileImprovementId);
        Node tileImprovementCountNode = XMLConverterUtility.findNode(node, "tileImprovementCount");
        int tileImprovementCount = Integer.parseInt(tileImprovementCountNode.getFirstChild().getNodeValue());
        mission.setTileImprovementCount(tileImprovementCount);
        initializeMissionFromNode(realm, node, mission);
        initializeMissionRewardsFromNode(realm, node, mission);
        return mission;
    }
}

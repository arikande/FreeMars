package org.freerealm.xmlwrapper.player.mission;

import org.freerealm.Realm;
import org.freerealm.player.mission.ExplorationMission;
import org.freerealm.xmlwrapper.XMLConverter;
import org.freerealm.xmlwrapper.XMLConverterUtility;
import org.w3c.dom.Node;

/**
 *
 * @author Deniz ARIKAN
 */
public class ExplorationMissionXMLConverter extends AbstractMissionXMLHelper implements XMLConverter<ExplorationMission> {

    public String toXML(ExplorationMission explorationMission) {
        StringBuilder xml = new StringBuilder();
        xml.append("<");
        xml.append(ExplorationMission.NAME);
        xml.append(">\n");
        xml.append("<explorationTileCount>" + explorationMission.getExplorationTileCount() + "</explorationTileCount>\n");
        xml.append(getInnerXML(explorationMission));
        xml.append(getRewardsXML(explorationMission));
        xml.append("</");
        xml.append(ExplorationMission.NAME);
        xml.append(">\n");
        return xml.toString();
    }

    public ExplorationMission initializeFromNode(Realm realm, Node node) {
        ExplorationMission explorationMission = new ExplorationMission();
        Node explorationTileCountNode = XMLConverterUtility.findNode(node, "explorationTileCount");
        int explorationTileCount = Integer.parseInt(explorationTileCountNode.getFirstChild().getNodeValue());
        explorationMission.setExplorationTileCount(explorationTileCount);
        initializeMissionFromNode(realm, node, explorationMission);
        initializeMissionRewardsFromNode(realm, node, explorationMission);
        if (realm != null) {
            int mapTileCount = realm.getMapHeight() * realm.getMapWidth();
            if (mapTileCount > 0 && explorationMission.getExplorationTileCount() > mapTileCount) {
                explorationMission.setExplorationTileCount(mapTileCount);
            }
        }
        return explorationMission;
    }
}

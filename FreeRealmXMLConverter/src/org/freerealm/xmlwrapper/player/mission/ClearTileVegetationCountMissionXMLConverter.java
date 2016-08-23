package org.freerealm.xmlwrapper.player.mission;

import org.freerealm.Realm;
import org.freerealm.player.mission.ClearTileVegetationCountMission;
import org.freerealm.xmlwrapper.XMLConverter;
import org.freerealm.xmlwrapper.XMLConverterUtility;
import org.w3c.dom.Node;

/**
 *
 * @author Deniz ARIKAN
 */
public class ClearTileVegetationCountMissionXMLConverter extends AbstractMissionXMLHelper implements XMLConverter<ClearTileVegetationCountMission> {

    public String toXML(ClearTileVegetationCountMission clearTileVegetationCountMission) {
        StringBuilder xml = new StringBuilder();
        xml.append("<");
        xml.append(ClearTileVegetationCountMission.NAME);
        xml.append(">\n");
        xml.append("<clear_tile_vegetation_count>");
        xml.append(clearTileVegetationCountMission.getClearTileVegetationCount());
        xml.append("</clear_tile_vegetation_count>\n");
        xml.append(getInnerXML(clearTileVegetationCountMission));
        xml.append(getRewardsXML(clearTileVegetationCountMission));
        xml.append("</");
        xml.append(ClearTileVegetationCountMission.NAME);
        xml.append(">\n");
        return xml.toString();
    }

    public ClearTileVegetationCountMission initializeFromNode(Realm realm, Node node) {
        ClearTileVegetationCountMission clearTileVegetationCountMission = new ClearTileVegetationCountMission();
        Node clearTileVegetationCountNode = XMLConverterUtility.findNode(node, "clear_tile_vegetation_count");
        int clearTileVegetationCount = Integer.parseInt(clearTileVegetationCountNode.getFirstChild().getNodeValue());
        clearTileVegetationCountMission.setClearTileVegetationCount(clearTileVegetationCount);
        initializeMissionFromNode(realm, node, clearTileVegetationCountMission);
        initializeMissionRewardsFromNode(realm, node, clearTileVegetationCountMission);
        return clearTileVegetationCountMission;
    }
}

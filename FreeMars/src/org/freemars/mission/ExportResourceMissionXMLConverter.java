package org.freemars.mission;

import org.freerealm.Realm;
import org.freerealm.xmlwrapper.XMLConverter;
import org.freerealm.xmlwrapper.XMLConverterUtility;
import org.freerealm.xmlwrapper.player.mission.AbstractMissionXMLHelper;
import org.w3c.dom.Node;

/**
 *
 * @author Deniz ARIKAN
 */
public class ExportResourceMissionXMLConverter extends AbstractMissionXMLHelper implements XMLConverter<ExportResourceMission> {

    public String toXML(ExportResourceMission exportResourceMission) {
        StringBuilder xml = new StringBuilder();
        xml.append("<exportResourceMission>\n");
        xml.append("<resourceId>");
        xml.append(exportResourceMission.getResourceId());
        xml.append("</resourceId>\n");
        xml.append("<startExportedQuantity>");
        xml.append(exportResourceMission.getStartExportedQuantity());
        xml.append("</startExportedQuantity>\n");
        xml.append("<targetQuantity>");
        xml.append(exportResourceMission.getTargetQuantity());
        xml.append("</targetQuantity>\n");
        xml.append(getInnerXML(exportResourceMission));
        xml.append(getRewardsXML(exportResourceMission));
        xml.append("</exportResourceMission>\n");
        return xml.toString();
    }

    public ExportResourceMission initializeFromNode(Realm realm, Node node) {
        ExportResourceMission exportResourceMission = new ExportResourceMission();
        Node resourceIdNode = XMLConverterUtility.findNode(node, "resourceId");
        int resourceId = Integer.parseInt(resourceIdNode.getFirstChild().getNodeValue());
        exportResourceMission.setResourceId(resourceId);
        Node startExportedQuantityNode = XMLConverterUtility.findNode(node, "startExportedQuantity");
        if (startExportedQuantityNode != null) {
            int startExportedQuantity = Integer.parseInt(startExportedQuantityNode.getFirstChild().getNodeValue());
            exportResourceMission.setStartExportedQuantity(startExportedQuantity);
        }
        Node targetQuantityNode = XMLConverterUtility.findNode(node, "targetQuantity");
        if (targetQuantityNode != null) {
            int targetQuantity = Integer.parseInt(targetQuantityNode.getFirstChild().getNodeValue());
            exportResourceMission.setTargetQuantity(targetQuantity);
        }
        initializeMissionFromNode(realm, node, exportResourceMission);
        initializeMissionRewardsFromNode(realm, node, exportResourceMission);
        return exportResourceMission;

    }
}

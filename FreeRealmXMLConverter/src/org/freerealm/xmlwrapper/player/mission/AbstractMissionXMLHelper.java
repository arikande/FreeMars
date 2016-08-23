package org.freerealm.xmlwrapper.player.mission;

import java.util.Iterator;
import org.freerealm.Realm;
import org.freerealm.player.mission.AbstractMission;
import org.freerealm.player.mission.Mission;
import org.freerealm.player.mission.Reward;
import org.freerealm.xmlwrapper.TagManager;
import org.freerealm.xmlwrapper.XMLConverter;
import org.freerealm.xmlwrapper.XMLConverterUtility;
import org.w3c.dom.Node;

/**
 *
 * @author Deniz ARIKAN
 */
public class AbstractMissionXMLHelper {

    public String getInnerXML(Mission mission) {
        StringBuilder xml = new StringBuilder();
        xml.append("<id>" + mission.getId() + "</id>\n");
        xml.append("<status>" + mission.getStatus() + "</status>\n");
        xml.append("<turnIssued>" + mission.getTurnIssued() + "</turnIssued>\n");
        xml.append("<duration>" + mission.getDuration() + "</duration>\n");
        return xml.toString();
    }

    public String getRewardsXML(Mission mission) {
        StringBuilder xml = new StringBuilder();
        xml.append("<rewards>\n");
        Iterator<Reward> iterator = mission.getRewardsIterator();
        while (iterator.hasNext()) {
            Reward reward = iterator.next();
            String xMLConverterName = TagManager.getXMLConverterName(reward.getName());
            Class c;
            try {
                c = Class.forName(xMLConverterName);
                XMLConverter<Reward> xMLConverter = (XMLConverter<Reward>) c.newInstance();
                xml.append(xMLConverter.toXML(reward));
            } catch (Exception ex) {
            }
        }
        xml.append("</rewards>\n");
        return xml.toString();
    }

    public void initializeMissionFromNode(Realm realm, Node node, AbstractMission mission) {
        Node idNode = XMLConverterUtility.findNode(node, "id");
        if (idNode != null) {
            int id = Integer.parseInt(idNode.getFirstChild().getNodeValue());
            mission.setId(id);
        }
        Node statusNode = XMLConverterUtility.findNode(node, "status");
        if (statusNode != null) {
            int status = Integer.parseInt(statusNode.getFirstChild().getNodeValue());
            mission.setStatus(status);
        }
        Node turnIssuedNode = XMLConverterUtility.findNode(node, "turnIssued");
        if (turnIssuedNode != null) {
            int turnIssued = Integer.parseInt(turnIssuedNode.getFirstChild().getNodeValue());
            mission.setTurnIssued(turnIssued);
        }
        Node durationNode = XMLConverterUtility.findNode(node, "duration");
        if (durationNode != null) {
            int duration = Integer.parseInt(durationNode.getFirstChild().getNodeValue());
            mission.setDuration(duration);
        }
        mission.setRealm(realm);
    }

    public void initializeMissionRewardsFromNode(Realm realm, Node node, AbstractMission mission) {
        Node rewardsNode = XMLConverterUtility.findNode(node, "rewards");
        if (rewardsNode != null) {
            for (Node rewardNode = rewardsNode.getFirstChild(); rewardNode != null; rewardNode = rewardNode.getNextSibling()) {
                if (rewardNode.getNodeType() == Node.ELEMENT_NODE) {
                    Reward reward = createReward(realm, rewardNode);
                    mission.addReward(reward);
                }
            }
        }
    }

    private Reward createReward(Realm realm, Node node) {
        Reward reward = null;
        String xMLConverterName = TagManager.getXMLConverterName(node.getNodeName());
        try {
            XMLConverter<Reward> xMLConverter = (XMLConverter<Reward>) Class.forName(xMLConverterName).newInstance();
            reward = xMLConverter.initializeFromNode(realm, node);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return reward;
    }
}

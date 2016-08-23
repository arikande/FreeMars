package org.freemars.mission;

import java.io.BufferedInputStream;
import java.io.IOException;
import org.apache.xerces.parsers.DOMParser;
import org.freemars.controller.FreeMarsController;
import org.freemars.controller.MissionAssignment;
import org.freerealm.player.mission.Mission;
import org.freerealm.xmlwrapper.TagManager;
import org.freerealm.xmlwrapper.XMLConverter;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 *
 * @author Deniz ARIKAN
 */
public class MissionReader {

    public void readMissions(FreeMarsController freeMarsController) {
        freeMarsController.clearMissions();
        DOMParser builder = new DOMParser();
        BufferedInputStream bufferedInputStream = new BufferedInputStream(ClassLoader.getSystemResourceAsStream("config/missions.xml"));
        InputSource inputSource = new InputSource(bufferedInputStream);
        try {
            builder.parse(inputSource);
        } catch (SAXException exception) {
            exception.printStackTrace();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        Node missionsNode = builder.getDocument().getFirstChild();
        for (Node missionNode = missionsNode.getFirstChild(); missionNode != null; missionNode = missionNode.getNextSibling()) {
            if (missionNode.getNodeType() == Node.ELEMENT_NODE) {
                int turnToAssign = 0;
                int wealthToAdd = 0;
                Mission mission = null;
                for (Node subNode = missionNode.getFirstChild(); subNode != null; subNode = subNode.getNextSibling()) {
                    if (subNode.getNodeType() == Node.ELEMENT_NODE) {
                        if (subNode.getNodeName().equals("turnToAssign")) {
                            turnToAssign = Integer.parseInt(subNode.getFirstChild().getNodeValue());
                        } else if (subNode.getNodeName().equals("wealthToAdd")) {
                            wealthToAdd = Integer.parseInt(subNode.getFirstChild().getNodeValue());
                        } else {
                            String xMLConverterName = TagManager.getXMLConverterName(subNode.getNodeName());
                            try {
                                XMLConverter<Mission> xMLConverter = (XMLConverter<Mission>) Class.forName(xMLConverterName).newInstance();
                                mission = xMLConverter.initializeFromNode(freeMarsController.getFreeMarsModel().getRealm(), subNode);
                            } catch (Exception exception) {
                            }
                        }
                    }
                }
                MissionAssignment missionAssignment = new MissionAssignment();
                missionAssignment.setTurnToAssign(turnToAssign);
                missionAssignment.setMission(mission);
                missionAssignment.setWealthToAdd(wealthToAdd);
                freeMarsController.addMissionAssignment(missionAssignment);
            }
        }
    }
}

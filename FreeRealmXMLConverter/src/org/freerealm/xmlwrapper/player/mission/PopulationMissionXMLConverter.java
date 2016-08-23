package org.freerealm.xmlwrapper.player.mission;

import org.freerealm.Realm;
import org.freerealm.player.mission.PopulationMission;
import org.freerealm.xmlwrapper.XMLConverter;
import org.freerealm.xmlwrapper.XMLConverterUtility;
import org.w3c.dom.Node;

/**
 *
 * @author Deniz ARIKAN
 */
public class PopulationMissionXMLConverter extends AbstractMissionXMLHelper implements XMLConverter<PopulationMission> {

    public String toXML(PopulationMission populationMission) {
        StringBuilder xml = new StringBuilder();
        xml.append("<populationMission>\n");
        xml.append("<population>" + populationMission.getPopulation() + "</population>\n");
        xml.append(getInnerXML(populationMission));
        xml.append(getRewardsXML(populationMission));
        xml.append("</populationMission>\n");
        return xml.toString();
    }

    public PopulationMission initializeFromNode(Realm realm, Node node) {
        PopulationMission populationMission = new PopulationMission();
        Node populationNode = XMLConverterUtility.findNode(node, "population");
        int population = Integer.parseInt(populationNode.getFirstChild().getNodeValue());
        populationMission.setPopulation(population);
        initializeMissionFromNode(realm, node, populationMission);
        initializeMissionRewardsFromNode(realm, node, populationMission);
        return populationMission;
    }
}

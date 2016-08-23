package org.freerealm.xmlwrapper.city;

import java.util.Iterator;
import java.util.Vector;
import org.freerealm.settlement.SettlementBuildable;
import org.freerealm.settlement.SettlementBuildablePrerequisite;
import org.freerealm.settlement.improvement.NoSettlementImprovementPrerequisite;
import org.freerealm.settlement.improvement.PopulationPrerequisite;
import org.freerealm.settlement.improvement.SettlementImprovementManager;
import org.freerealm.settlement.improvement.SettlementImprovementPrerequisite;
import org.freerealm.settlement.improvement.SettlementImprovementType;
import org.w3c.dom.Node;

/**
 *
 * @author Deniz ARIKAN
 */
public class PrerequisitesXMLHelper {

    public String toXML(Iterator<SettlementBuildablePrerequisite> prerequisitesIterator) {
        StringBuilder xml = new StringBuilder();

        xml.append("<Prerequisites>\n");
        if (prerequisitesIterator != null) {
            while (prerequisitesIterator.hasNext()) {
                SettlementBuildablePrerequisite prerequisite = prerequisitesIterator.next();
                if (prerequisite instanceof SettlementImprovementPrerequisite) {
                    SettlementImprovementPrerequisite settlementImprovementPrerequisite = (SettlementImprovementPrerequisite) prerequisite;
                    xml.append("<SettlementImprovementPrerequisite>\n");
                    Iterator<SettlementImprovementType> iterator = settlementImprovementPrerequisite.getPrerequisiteImprovementsIterator();
                    while (iterator.hasNext()) {
                        SettlementImprovementType cityImprovementType = iterator.next();
                        xml.append("<improvementType>" + cityImprovementType.getId() + "</improvementType>\n");
                    }
                    xml.append("</SettlementImprovementPrerequisite>");
                } else if (prerequisite instanceof NoSettlementImprovementPrerequisite) {
                    NoSettlementImprovementPrerequisite noSettlementImprovementPrerequisite = (NoSettlementImprovementPrerequisite) prerequisite;
                    xml.append("<NoSettlementImprovementPrerequisite>\n");
                    Iterator<SettlementImprovementType> iterator = noSettlementImprovementPrerequisite.getExcludingImprovementsIterator();
                    while (iterator.hasNext()) {
                        SettlementImprovementType settlementImprovementType = iterator.next();
                        xml.append("<improvementType>" + settlementImprovementType.getId() + "</improvementType>\n");
                    }
                    xml.append("</NoSettlementImprovementPrerequisite>");
                } else if (prerequisite instanceof PopulationPrerequisite) {
                    PopulationPrerequisite populationPrerequisite = (PopulationPrerequisite) prerequisite;
                    xml.append("<PopulationPrerequisite>\n");
                    xml.append("<population>" + populationPrerequisite.getPrerequisitePopulation() + "</population>");
                    xml.append("</PopulationPrerequisite>");
                }
            }
        }
        xml.append("</Prerequisites>\n");
        return xml.toString();
    }

    public void initializePrerequisites(SettlementImprovementManager settlementImprovementManager, SettlementBuildable settlementBuildable, Node prerequisitesNode) {
        if (prerequisitesNode != null) {
            for (Node prerequisiteNode = prerequisitesNode.getFirstChild(); prerequisiteNode != null; prerequisiteNode = prerequisiteNode.getNextSibling()) {
                if (prerequisiteNode.getNodeType() == Node.ELEMENT_NODE) {
                    if (prerequisiteNode.getNodeName().equals("SettlementImprovementPrerequisite")) {
                        Vector<SettlementImprovementType> prerequisiteImprovements = new Vector<SettlementImprovementType>();
                        for (Node improvementSubNode = prerequisiteNode.getFirstChild(); improvementSubNode != null; improvementSubNode = improvementSubNode.getNextSibling()) {
                            if (improvementSubNode.getNodeType() == Node.ELEMENT_NODE) {
                                int prerequisiteBuildingId = Integer.parseInt(improvementSubNode.getFirstChild().getNodeValue());
                                SettlementImprovementType prerequisiteBuilding = settlementImprovementManager.getImprovement(prerequisiteBuildingId);
                                prerequisiteImprovements.add(prerequisiteBuilding);
                            }
                        }
                        SettlementImprovementPrerequisite settlementImprovementPrerequisite = new SettlementImprovementPrerequisite(prerequisiteImprovements);
                        settlementBuildable.addPrerequisite(settlementImprovementPrerequisite);
                    }else  if (prerequisiteNode.getNodeName().equals("NoSettlementImprovementPrerequisite")) {
                        Vector<SettlementImprovementType> excludingImprovements = new Vector<SettlementImprovementType>();
                        for (Node improvementSubNode = prerequisiteNode.getFirstChild(); improvementSubNode != null; improvementSubNode = improvementSubNode.getNextSibling()) {
                            if (improvementSubNode.getNodeType() == Node.ELEMENT_NODE) {
                                int excludingSettlementImprovementId = Integer.parseInt(improvementSubNode.getFirstChild().getNodeValue());
                                SettlementImprovementType excludingSettlementImprovement = settlementImprovementManager.getImprovement(excludingSettlementImprovementId);
                                excludingImprovements.add(excludingSettlementImprovement);
                            }
                        }
                        NoSettlementImprovementPrerequisite noSettlementImprovementPrerequisite = new NoSettlementImprovementPrerequisite(excludingImprovements);
                        settlementBuildable.addPrerequisite(noSettlementImprovementPrerequisite);
                    } else if (prerequisiteNode.getNodeName().equals("PopulationPrerequisite")) {
                        for (Node populationSubNode = prerequisiteNode.getFirstChild(); populationSubNode != null; populationSubNode = populationSubNode.getNextSibling()) {
                            if (populationSubNode.getNodeType() == Node.ELEMENT_NODE) {
                                int prerequisitePopulation = Integer.parseInt(populationSubNode.getFirstChild().getNodeValue());
                                PopulationPrerequisite populationPrerequisite = new PopulationPrerequisite(prerequisitePopulation);
                                settlementBuildable.addPrerequisite(populationPrerequisite);
                            }
                        }
                    }
                }
            }
        }
    }
}

package org.freemars.colonydialog;

import java.util.Iterator;
import org.freerealm.Realm;
import org.freerealm.resource.Resource;
import org.freerealm.settlement.SettlementBuildablePrerequisite;
import org.freerealm.settlement.improvement.NoSettlementImprovementPrerequisite;
import org.freerealm.settlement.improvement.PopulationPrerequisite;
import org.freerealm.settlement.improvement.SettlementImprovementPrerequisite;
import org.freerealm.settlement.improvement.SettlementImprovementType;

/**
 *
 * @author arikande
 */
public class ColonyImprovementToolTipBuilder {

    private final Realm realm;
    private final SettlementImprovementType colonyImprovementType;

    public ColonyImprovementToolTipBuilder(Realm realm, SettlementImprovementType colonyImprovementType) {
        this.realm = realm;
        this.colonyImprovementType = colonyImprovementType;
    }

    public String getFullToolTip() {
        String productionToolTip = getProductionToolTipContent();
        String prerequisiteToolTip = getPrerequisiteToolTip();
        if (!productionToolTip.equals("") && !prerequisiteToolTip.equals("")) {
            StringBuilder toolTipText = new StringBuilder();
            toolTipText.append("<html><font size='4'>");
            toolTipText.append(getProductionToolTipContent());
            toolTipText.append("<br>");
            toolTipText.append(getPrerequisiteToolTipContent());
            toolTipText.append("</font></html>");
            return toolTipText.toString();
        } else {
            return "";
        }
    }

    public String getProductionToolTip() {
        String productionToolTip = getProductionToolTipContent();
        if (!productionToolTip.equals("")) {
            StringBuilder toolTipText = new StringBuilder();
            toolTipText.append("<html><font size='4'>");
            toolTipText.append(getProductionToolTipContent());
            toolTipText.append("</font></html>");
            return toolTipText.toString();
        } else {
            return "";
        }
    }

    public String getPrerequisiteToolTip() {
        StringBuilder toolTipText = new StringBuilder();
        toolTipText.append("<html><font size='4'>");
        toolTipText.append(getPrerequisiteToolTipContent());
        toolTipText.append("</font></html>");
        return toolTipText.toString();
    }

    private String getProductionToolTipContent() {
        String toolTipText = "";
        Iterator<Resource> iterator = realm.getResourceManager().getResourcesIterator();
        while (iterator.hasNext()) {
            Resource resource = iterator.next();
            if (colonyImprovementType.getResourceProductionModifier(resource, true) > 0) {
                toolTipText = toolTipText + "+" + colonyImprovementType.getResourceProductionModifier(resource, true) + "% " + resource.getName() + " production";
            }
            if (colonyImprovementType.getCapacityModifier(resource) > 0) {
                toolTipText = toolTipText + "<br>+" + colonyImprovementType.getCapacityModifier(resource) + " " + resource.getName() + " storage";
            }
        }
        if (colonyImprovementType.getProductionPointModifier() > 0) {
            toolTipText = toolTipText + "+" + colonyImprovementType.getProductionPointModifier() + "% " + " production";
        }
        if (colonyImprovementType.getSettlementTaxModifier() > 0) {
            toolTipText = toolTipText + "+" + colonyImprovementType.getSettlementTaxModifier() + "% " + " tax income";
        }
        if (colonyImprovementType.getMaximumTileWorkersModifier() > 0) {
            toolTipText = toolTipText + "<br>+" + colonyImprovementType.getMaximumTileWorkersModifier() + " maximum tile workers";
        }
        if (colonyImprovementType.getDefenceModifier() > 0) {
            toolTipText = toolTipText + "+" + colonyImprovementType.getDefenceModifier() + "% " + " defence";
        }
        if (colonyImprovementType.getEfficiencyModifier() > 0) {
            toolTipText = toolTipText + "+" + colonyImprovementType.getEfficiencyModifier() + "% " + " efficiency";
        }

        if (colonyImprovementType.isResourceProducer()) {
            toolTipText = toolTipText + "<br>Produces :<br>";
            iterator = realm.getResourceManager().getResourcesIterator();
            while (iterator.hasNext()) {
                Resource resource = iterator.next();
                if (colonyImprovementType.getOutputQuantity(resource) > 0) {
                    toolTipText = toolTipText + " - " + colonyImprovementType.getOutputQuantity(resource) + " " + resource.getName() + "<br>";
                }
            }
            boolean fromAdded = false;
            iterator = realm.getResourceManager().getResourcesIterator();
            while (iterator.hasNext()) {
                Resource resource = iterator.next();
                if (colonyImprovementType.getInputQuantity(resource) > 0) {
                    if (!fromAdded) {
                        toolTipText = toolTipText + "From :<br>";
                        fromAdded = true;
                    }
                    toolTipText = toolTipText + " - " + colonyImprovementType.getInputQuantity(resource) + " " + resource.getName() + "<br>";
                }
            }
        }
        return toolTipText;
    }

    private String getPrerequisiteToolTipContent() {
        StringBuilder toolTip = new StringBuilder();
        if (colonyImprovementType.getPrerequisiteCount() > 0) {
            Iterator<SettlementBuildablePrerequisite> iterator = colonyImprovementType.getPrerequisitesIterator();
            toolTip.append("Requires :");
            toolTip.append("<br>");
            while (iterator.hasNext()) {
                SettlementBuildablePrerequisite settlementBuildablePrerequisite = iterator.next();
                if (settlementBuildablePrerequisite instanceof SettlementImprovementPrerequisite) {
                    SettlementImprovementPrerequisite settlementImprovementPrerequisite = (SettlementImprovementPrerequisite) settlementBuildablePrerequisite;
                    Iterator<SettlementImprovementType> improvementIterator = settlementImprovementPrerequisite.getPrerequisiteImprovementsIterator();
                    while (improvementIterator.hasNext()) {
                        SettlementImprovementType cityImprovementType = improvementIterator.next();
                        toolTip.append("- ");
                        toolTip.append(cityImprovementType.getName());
                        toolTip.append("<br>");
                    }
                } else if (settlementBuildablePrerequisite instanceof NoSettlementImprovementPrerequisite) {
                    NoSettlementImprovementPrerequisite noSettlementImprovementPrerequisite = (NoSettlementImprovementPrerequisite) settlementBuildablePrerequisite;
                    Iterator<SettlementImprovementType> improvementIterator = noSettlementImprovementPrerequisite.getExcludingImprovementsIterator();
                    while (improvementIterator.hasNext()) {
                        SettlementImprovementType settlementImprovementType = improvementIterator.next();
                        toolTip.append("- Excl. ");
                        toolTip.append(settlementImprovementType.getName());
                        toolTip.append("<br>");
                    }
                } else if (settlementBuildablePrerequisite instanceof PopulationPrerequisite) {
                    PopulationPrerequisite populationPrerequisite = (PopulationPrerequisite) settlementBuildablePrerequisite;
                    toolTip.append("- ");
                    toolTip.append("Population : ");
                    toolTip.append(populationPrerequisite.getPrerequisitePopulation());
                }
            }
        }
        return toolTip.toString();
    }
}

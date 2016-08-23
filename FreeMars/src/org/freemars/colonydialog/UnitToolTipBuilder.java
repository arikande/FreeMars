package org.freemars.colonydialog;

import java.util.Iterator;
import org.freerealm.Realm;
import org.freerealm.settlement.SettlementBuildablePrerequisite;
import org.freerealm.settlement.improvement.NoSettlementImprovementPrerequisite;
import org.freerealm.settlement.improvement.PopulationPrerequisite;
import org.freerealm.settlement.improvement.SettlementImprovementPrerequisite;
import org.freerealm.settlement.improvement.SettlementImprovementType;
import org.freerealm.unit.FreeRealmUnitType;

/**
 *
 * @author arikande
 */
public class UnitToolTipBuilder {

    private final Realm realm;
    private final FreeRealmUnitType unitType;

    public UnitToolTipBuilder(Realm realm, FreeRealmUnitType unitType) {
        this.realm = realm;
        this.unitType = unitType;
    }

    public String getPrerequisiteToolTip() {
        StringBuilder toolTipText = new StringBuilder();
        toolTipText.append("<html><font size='4'>");
        toolTipText.append(getPrerequisiteToolTipContent());
        toolTipText.append("</font></html>");
        return toolTipText.toString();
    }

    private String getPrerequisiteToolTipContent() {
        StringBuilder toolTip = new StringBuilder();
        if (unitType.getPrerequisiteCount() > 0) {
            Iterator<SettlementBuildablePrerequisite> iterator = unitType.getPrerequisitesIterator();
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

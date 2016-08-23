package org.freemars.ui.wizard.newgame;

import java.util.Iterator;
import org.freemars.earth.ModifyEarthFlightTime;
import org.freemars.earth.ModifyEarthTaxRate;
import org.freemars.earth.ModifyFinanceColonizerCost;
import org.freemars.earth.ModifyHydrogenConsumption;
import org.freemars.earth.ModifyResourceEarthSellPrice;
import org.freerealm.nation.Nation;
import org.freerealm.property.BuildTileImprovementProperty;
import org.freerealm.property.ModifyMaximumWorkers;
import org.freerealm.property.ModifyProduction;
import org.freerealm.property.ModifyRequiredPopulationResourceAmount;
import org.freerealm.property.ModifySettlementImprovementCost;
import org.freerealm.property.ModifySettlementImprovementUpkeepCost;
import org.freerealm.property.ModifyStartingWealth;
import org.freerealm.property.ModifyUnitCost;
import org.freerealm.property.ModifyUnitTypeProperty;
import org.freerealm.property.ModifyUnitUpkeepCost;
import org.freerealm.property.Property;
import org.freerealm.property.SetStartupUnitCountProperty;

/**
 *
 * @author arikande
 */
public class NationToolTipTextBuilder {

    public String getNationToolTipText(Nation nation) {
        String nationToolTipText = "<html>" + "<b>&nbsp;&nbsp;" + nation.getName() + "</b>";
        if (nation.getPropertyCount() > 0) {
            nationToolTipText = nationToolTipText + "<br>";
        }
        Iterator<Property> iterator = nation.getPropertiesIterator();
        while (iterator.hasNext()) {
            Property property = iterator.next();
            nationToolTipText = nationToolTipText + "<br>" + "&nbsp;&nbsp;-";
            if (property instanceof ModifyUnitCost) {
                ModifyUnitCost modifyUnitCost = (ModifyUnitCost) property;
                int modifier = modifyUnitCost.getModifier();
                if (modifier < 0) {
                    nationToolTipText = nationToolTipText + "Units cost " + Math.abs(modifier) + "% less&nbsp;&nbsp;&nbsp;&nbsp;";
                } else {
                    nationToolTipText = nationToolTipText + "Units cost " + modifier + "% more&nbsp;&nbsp;&nbsp;&nbsp;";
                }
            } else if (property instanceof ModifySettlementImprovementCost) {
                ModifySettlementImprovementCost modifySettlementImprovementCost = (ModifySettlementImprovementCost) property;
                int modifier = modifySettlementImprovementCost.getModifier();
                if (modifier < 0) {
                    nationToolTipText = nationToolTipText + "Colony improvements cost " + Math.abs(modifier) + "% less&nbsp;&nbsp;&nbsp;&nbsp;";
                } else {
                    nationToolTipText = nationToolTipText + "Colony improvements cost " + modifier + "% more&nbsp;&nbsp;&nbsp;&nbsp;";
                }
            } else if (property instanceof ModifyProduction) {
                ModifyProduction modifyProduction = (ModifyProduction) property;
                int modifier = modifyProduction.getModifier();
                if (modifier < 0) {
                    nationToolTipText = nationToolTipText + "Production points decreased by " + Math.abs(modifier) + "%&nbsp;&nbsp;&nbsp;&nbsp;";
                } else {
                    nationToolTipText = nationToolTipText + "Production points increased by " + modifier + "%&nbsp;&nbsp;&nbsp;&nbsp;";
                }
            } else if (property instanceof ModifyFinanceColonizerCost) {
                ModifyFinanceColonizerCost modifyFinanceColonizerCost = (ModifyFinanceColonizerCost) property;
                int modifier = modifyFinanceColonizerCost.getModifier();
                if (modifier < 0) {
                    nationToolTipText = nationToolTipText + "Financing colonizers from Earth cost " + Math.abs(modifier) + "% less&nbsp;&nbsp;&nbsp;&nbsp;";
                } else {
                    nationToolTipText = nationToolTipText + "Financing colonizers from Earth cost " + modifier + "% more&nbsp;&nbsp;&nbsp;&nbsp;";
                }
            } else if (property instanceof ModifyEarthFlightTime) {
                ModifyEarthFlightTime modifyEarthFlightTime = (ModifyEarthFlightTime) property;

                int modifier = modifyEarthFlightTime.getModifier();
                if (modifier < 0) {
                    nationToolTipText = nationToolTipText + "Earth flight is decreased by " + Math.abs(modifier) + " turn&nbsp;&nbsp;&nbsp;&nbsp;";
                } else {
                    nationToolTipText = nationToolTipText + "Earth flight is increased by " + modifier + " turn&nbsp;&nbsp;&nbsp;&nbsp;";
                }
            } else if (property instanceof ModifyHydrogenConsumption) {
                ModifyHydrogenConsumption modifyHydrogenConsumption = (ModifyHydrogenConsumption) property;
                int modifier = modifyHydrogenConsumption.getModifier();
                if (modifier < 0) {
                    nationToolTipText = nationToolTipText + "Spaceships require " + Math.abs(modifier) + "% less hydrogen&nbsp;&nbsp;&nbsp;&nbsp;";
                } else {
                    nationToolTipText = nationToolTipText + "Spaceships require " + Math.abs(modifier) + "% more hydrogen&nbsp;&nbsp;&nbsp;&nbsp;";
                }
            } else if (property instanceof ModifyRequiredPopulationResourceAmount) {
                ModifyRequiredPopulationResourceAmount modifyRequiredPopulationResourceAmount = (ModifyRequiredPopulationResourceAmount) property;
                int modifier = modifyRequiredPopulationResourceAmount.getModifier();
                if (modifier < 0) {
                    nationToolTipText = nationToolTipText + "Each colonizer requires " + Math.abs(modifier) + " less Food&nbsp;&nbsp;&nbsp;&nbsp;";
                } else {
                    nationToolTipText = nationToolTipText + "Each colonizer requires " + modifier + " more Food&nbsp;&nbsp;&nbsp;&nbsp;";
                }
            } else if (property instanceof ModifySettlementImprovementUpkeepCost) {
                ModifySettlementImprovementUpkeepCost modifySettlementImprovementUpkeepCost = (ModifySettlementImprovementUpkeepCost) property;
                int modifier = modifySettlementImprovementUpkeepCost.getModifier();
                if (modifier < 0) {
                    nationToolTipText = nationToolTipText + "Colony improvement upkeep costs " + Math.abs(modifier) + "% less&nbsp;&nbsp;&nbsp;&nbsp;";
                } else {
                    nationToolTipText = nationToolTipText + "Colony improvement upkeep costs " + modifier + "% more&nbsp;&nbsp;&nbsp;&nbsp;";
                }
            } else if (property instanceof ModifyUnitUpkeepCost) {
                ModifyUnitUpkeepCost modifyUnitUpkeepCost = (ModifyUnitUpkeepCost) property;
                int modifier = modifyUnitUpkeepCost.getModifier();
                if (modifier < 0) {
                    nationToolTipText = nationToolTipText + "Unit upkeep costs " + Math.abs(modifier) + "% less&nbsp;&nbsp;&nbsp;&nbsp;";
                } else {
                    nationToolTipText = nationToolTipText + "Unit upkeep costs " + modifier + "% more&nbsp;&nbsp;&nbsp;&nbsp;";
                }
            } else if (property instanceof ModifyUnitTypeProperty) {
                ModifyUnitTypeProperty modifyUnitTypeProperty = (ModifyUnitTypeProperty) property;
                String propertyName = modifyUnitTypeProperty.getPropertyName();
                if (propertyName.equals(BuildTileImprovementProperty.NAME)) {
                    int unitType = modifyUnitTypeProperty.getUnitType();
                    int modifier = modifyUnitTypeProperty.getModifier();
                    if (modifier < 0) {
                        nationToolTipText = nationToolTipText + "Engineers build tile improvements " + Math.abs(modifier) + "% slower&nbsp;&nbsp;&nbsp;&nbsp;";
                    } else {
                        nationToolTipText = nationToolTipText + "Engineers build tile improvements " + modifier + "% faster&nbsp;&nbsp;&nbsp;&nbsp;";
                    }
                } else if (propertyName.equals("ContainerProperty")) {
                    int unitType = modifyUnitTypeProperty.getUnitType();
                    int modifier = modifyUnitTypeProperty.getModifier();
                    if (modifier < 0) {
                        nationToolTipText = nationToolTipText + "Units have " + Math.abs(modifier) + "% less cargo capacity&nbsp;&nbsp;&nbsp;&nbsp;";
                    } else {
                        nationToolTipText = nationToolTipText + "Units have " + modifier + "% more cargo capacity&nbsp;&nbsp;&nbsp;&nbsp;";
                    }
                }
            } else if (property instanceof ModifyResourceEarthSellPrice) {
                ModifyResourceEarthSellPrice modifyResourceEarthSellPrice = (ModifyResourceEarthSellPrice) property;
                String resource = modifyResourceEarthSellPrice.getResource();
                int modifier = modifyResourceEarthSellPrice.getModifier();
                if (resource.equals(ModifyResourceEarthSellPrice.ALL)) {
                    nationToolTipText = nationToolTipText + "Selling goods ";
                } else {
                    nationToolTipText = nationToolTipText + "Selling steel ";
                }
                if (modifier < 0) {
                    nationToolTipText = nationToolTipText + "to Earth brings " + modifier + "% less profit&nbsp;&nbsp;&nbsp;&nbsp;";
                } else {
                    nationToolTipText = nationToolTipText + "to Earth brings " + modifier + "% more profit&nbsp;&nbsp;&nbsp;&nbsp;";
                }

            } else if (property instanceof ModifyEarthTaxRate) {
                ModifyEarthTaxRate modifyEarthTaxRate = (ModifyEarthTaxRate) property;
                int modifier = modifyEarthTaxRate.getModifier();
                if (modifier < 0) {
                    nationToolTipText = nationToolTipText + "Earth tax rate is decreased by " + Math.abs(modifier) + "%&nbsp;&nbsp;&nbsp;&nbsp;";
                } else {
                    nationToolTipText = nationToolTipText + "Earth tax rate is increased by " + modifier + "%&nbsp;&nbsp;&nbsp;&nbsp;";
                }

            } else if (property instanceof ModifyStartingWealth) {
                ModifyStartingWealth modifyStartingWealth = (ModifyStartingWealth) property;
                int modifier = modifyStartingWealth.getModifier();
                if (modifier < 0) {
                    nationToolTipText = nationToolTipText + "Starting wealth is decreased by " + Math.abs(modifier) + " credits&nbsp;&nbsp;&nbsp;&nbsp;";
                } else {
                    nationToolTipText = nationToolTipText + "Starting wealth is increased by " + modifier + " credits&nbsp;&nbsp;&nbsp;&nbsp;";
                }
            } else if (property instanceof ModifyMaximumWorkers) {
                ModifyMaximumWorkers modifyMaximumWorkers = (ModifyMaximumWorkers) property;
                int modifier = modifyMaximumWorkers.getModifier();
                if (modifier < 0) {
                    nationToolTipText = nationToolTipText + "Maximum workers per tile is decreased by " + Math.abs(modifier) + " colonists&nbsp;&nbsp;&nbsp;&nbsp;";
                } else {
                    nationToolTipText = nationToolTipText + "Maximum workers per tile is increased by " + modifier + " colonists&nbsp;&nbsp;&nbsp;&nbsp;";
                }
            } else if (property instanceof SetStartupUnitCountProperty) {
                SetStartupUnitCountProperty setStartupUnitCount = (SetStartupUnitCountProperty) property;
                int count = setStartupUnitCount.getCount();
                String unitTypeName = setStartupUnitCount.getUnitType().getName();
                nationToolTipText = nationToolTipText + "Start with " + count + " " + unitTypeName.toLowerCase() + "s&nbsp;&nbsp;&nbsp;&nbsp;";
            } else {
                nationToolTipText = nationToolTipText + property;
            }
        }
        nationToolTipText = nationToolTipText + "<br><br></html>";
        return nationToolTipText;
    }
}

package org.freerealm.executor.order.util;

import java.util.Iterator;
import org.freerealm.Utility;
import org.freerealm.property.BuildTileImprovementProperty;
import org.freerealm.property.ModifyUnitTypeProperty;
import org.freerealm.property.Property;
import org.freerealm.unit.Unit;

/**
 *
 * @author Deniz ARIKAN
 */
public class BuildTileImprovementProductionPointsCalculator {

    private final BuildTileImprovementProperty buildTileImprovement;
    private final Unit unit;

    public BuildTileImprovementProductionPointsCalculator(BuildTileImprovementProperty buildTileImprovement, Unit unit) {
        this.buildTileImprovement = buildTileImprovement;
        this.unit = unit;
    }

    public int getProductionPoints() {
        int productionPoints = buildTileImprovement.getProductionPoints();
        Iterator<Property> iterator = unit.getPlayer().getPropertiesIterator();
        while (iterator.hasNext()) {
            Property property = iterator.next();
            if (property instanceof ModifyUnitTypeProperty) {
                ModifyUnitTypeProperty modifyUnitTypeProperty = (ModifyUnitTypeProperty) property;
                if (modifyUnitTypeProperty.isActiveOnAllUnitTypes() || modifyUnitTypeProperty.getUnitType() == unit.getType().getId()) {
                    if (modifyUnitTypeProperty.getPropertyName().equals(BuildTileImprovementProperty.NAME)) {
                        if (modifyUnitTypeProperty.getModifierName().equals("productionPoints")) {
                            int productionPointsModifier = modifyUnitTypeProperty.getModifier();
                            productionPoints = (int) Utility.modifyByPercent(productionPoints, productionPointsModifier);
                        }
                    }
                }
            }
        }
        return productionPoints;
    }
}

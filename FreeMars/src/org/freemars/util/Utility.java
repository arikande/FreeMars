package org.freemars.util;

import org.freemars.earth.EarthFlightProperty;
import org.freerealm.player.Player;
import org.freerealm.unit.Unit;

/**
 *
 * @author Deniz ARIKAN
 */
public class Utility {

    public static String convertTurnCountToTimePeriod(int turnCount) {
        StringBuilder timePeriod = new StringBuilder();
        int years = turnCount / 12;
        if (years > 0) {
            timePeriod.append(years);
            timePeriod.append(" ");
            timePeriod.append("year(s)");
        }
        int months = turnCount % 12;
        if (months > 0) {
            timePeriod.append(" ");
            timePeriod.append("and");
            timePeriod.append(" ");
            timePeriod.append(months);
            timePeriod.append(" ");
            timePeriod.append("month(s)");
        }
        return timePeriod.toString();
    }

    public static Unit getNextPlayableUnit(Player player, Unit unit) {
        Unit tryUnit = unit;
        int triedUnitCount = 0;
        while (triedUnitCount <= player.getUnitCount()) {
            tryUnit = player.getNextUnit(tryUnit);
            if (isPlayable(tryUnit)) {
                return tryUnit;
            } else {
                triedUnitCount = triedUnitCount + 1;
            }
        }
        return null;
    }

    private static boolean isPlayable(Unit unit) {
        if (unit == null) {
            return false;
        }
        if (unit.getStatus() == Unit.UNIT_SUSPENDED) {
            return false;
        }
        if (unit.getCurrentOrder() != null) {
            return false;
        }
        if (unit.getAutomater() != null) {
            return false;
        }
        EarthFlightProperty earthFlightProperty = (EarthFlightProperty) unit.getType().getProperty(EarthFlightProperty.NAME);
        if (earthFlightProperty != null && !unit.isSkippedForCurrentTurn()) {
            return true;
        }
        if (unit.getMovementPoints() == 0) {
            return false;
        }
        return true;
    }
}

package org.freemars.ai;

import java.util.List;

import org.freemars.controller.FreeMarsController;
import org.freemars.earth.command.LandExpeditionaryForceWaveCommand;
import org.freemars.player.FreeMarsPlayer;
import org.freerealm.command.UnitAdvanceAndAttackToCoordinateCommand;
import org.freerealm.map.Coordinate;
import org.freerealm.settlement.Settlement;
import org.freerealm.tile.Tile;
import org.freerealm.unit.FreeRealmUnitType;
import org.freerealm.unit.Unit;

/**
 *
 * @author Deniz ARIKAN
 */
public class ExpeditionaryForceDecisionModel {

    private final FreeMarsController freeMarsController;
    private final ExpeditionaryForcePlayer expeditionaryForcePlayer;

    public ExpeditionaryForceDecisionModel(FreeMarsController freeMarsController, ExpeditionaryForcePlayer expeditionaryForcePlayer) {
        this.freeMarsController = freeMarsController;
        this.expeditionaryForcePlayer = expeditionaryForcePlayer;
    }

    public void play() throws Exception {
        FreeMarsPlayer targetPlayer = (FreeMarsPlayer) freeMarsController.getFreeMarsModel().getRealm().getPlayerManager().getPlayer(expeditionaryForcePlayer.getTargetPlayerId());
        if (targetPlayer.hasDeclaredIndependence()) {
            int turnsSinceIndependenceDeclaration = freeMarsController.getFreeMarsModel().getNumberOfTurns() - targetPlayer.getIndependenceTurn();
            if (turnsSinceIndependenceDeclaration > 0 && turnsSinceIndependenceDeclaration % expeditionaryForcePlayer.getEarthToMarsFlightTurns() == 0) {
                if (expeditionaryForcePlayer.getRemainingAttackWaves() > 0) {
                    freeMarsController.execute(new LandExpeditionaryForceWaveCommand(freeMarsController, expeditionaryForcePlayer));
                }
            }
            FreeRealmUnitType LGTType = freeMarsController.getFreeMarsModel().getRealm().getUnitTypeManager().getUnitType("LGT");
            List<Unit> LGTs = expeditionaryForcePlayer.getUnitsOfType(LGTType);
            for (Unit LGT : LGTs) {
                manageOffensiveUnit(LGT);
            }
            FreeRealmUnitType mechUnitType = freeMarsController.getFreeMarsModel().getRealm().getUnitTypeManager().getUnitType("Mech");
            List<Unit> mecha = expeditionaryForcePlayer.getUnitsOfType(mechUnitType);
            for (Unit mech : mecha) {
                manageOffensiveUnit(mech);
            }
        }
    }

    private void manageOffensiveUnit(Unit offensiveUnit) {
        if (offensiveUnit.getStatus() == Unit.UNIT_ACTIVE) {
            Coordinate coordinate = offensiveUnit.getCoordinate();
            if (coordinate != null) {
                Coordinate targetCoordinate = null;
                Tile tile = freeMarsController.getFreeMarsModel().getTile(coordinate);
                if (tile.getSettlement() != null && tile.getNumberOfUnits() == 1) {
                } else {
                    targetCoordinate = findCoordinateToAttack(offensiveUnit);
                }
                if (targetCoordinate != null) {
                    expeditionaryForcePlayer.addExploredCoordinate(targetCoordinate);
                    freeMarsController.execute(new UnitAdvanceAndAttackToCoordinateCommand(freeMarsController.getFreeMarsModel().getRealm(), offensiveUnit, targetCoordinate));
                }
            }
        }
    }

    private Coordinate findCoordinateToAttack(Unit fightingUnit) {
        Coordinate targetCoordinate = null;
        Settlement targetColony = findColonyToAttack(fightingUnit);
        if (targetColony != null) {
            targetCoordinate = targetColony.getCoordinate();
        } else {
            Unit targetUnit = findUnitToAttack(fightingUnit);
            if (targetUnit != null) {
                targetCoordinate = targetUnit.getCoordinate();
            }
        }
        return targetCoordinate;
    }

    private Settlement findColonyToAttack(Unit mech) {
        for (int i = 0; i < 12; i++) {
            List<Coordinate> coordinates = freeMarsController.getFreeMarsModel().getRealm().getCircleCoordinates(mech.getCoordinate(), i);
            for (Coordinate coordinate : coordinates) {
                expeditionaryForcePlayer.addExploredCoordinate(coordinate);
                Tile tile = freeMarsController.getFreeMarsModel().getTile(coordinate);
                if (tile.getSettlement() != null) {
                    Settlement colony = tile.getSettlement();
                    if (colony.getPlayer().getId() == expeditionaryForcePlayer.getTargetPlayerId()) {
                        return colony;
                    }
                }
            }
        }
        return null;
    }

    private Unit findUnitToAttack(Unit mech) {
        for (int i = 0; i < 8; i++) {
            List<Coordinate> coordinates = freeMarsController.getFreeMarsModel().getRealm().getCircleCoordinates(mech.getCoordinate(), i);
            for (Coordinate coordinate : coordinates) {
                expeditionaryForcePlayer.addExploredCoordinate(coordinate);
                Tile tile = freeMarsController.getFreeMarsModel().getTile(coordinate);
                if (tile.getNumberOfUnits() > 0) {
                    Unit tileUnit = tile.getFirstUnit();
                    if (tileUnit.getPlayer().getId() == expeditionaryForcePlayer.getTargetPlayerId()) {
                        return tileUnit;
                    }
                }
            }
        }
        return null;
    }
}

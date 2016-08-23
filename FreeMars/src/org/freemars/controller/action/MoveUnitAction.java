package org.freemars.controller.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

import org.freemars.controller.FreeMarsController;
import org.freemars.controller.viewcommand.PlaySoundCommand;
import org.freemars.diplomacy.NoDiplomacyAllowedTileDialog;
import org.freemars.diplomacy.NoDiplomacyAllowedWithUnitsDialog;
import org.freemars.ui.util.FreeMarsOptionPane;
import org.freerealm.Realm;
import org.freerealm.command.SkipUnitCommand;
import org.freerealm.diplomacy.PlayerRelation;
import org.freerealm.executor.command.CaptureSettlementCommand;
import org.freerealm.executor.command.MoveUnitCommand;
import org.freerealm.executor.command.SetDiplomaticStatusCommand;
import org.freerealm.map.Coordinate;
import org.freerealm.map.Direction;
import org.freerealm.player.Player;
import org.freerealm.settlement.Settlement;
import org.freerealm.tile.Tile;
import org.freerealm.unit.Unit;

/**
 *
 * @author Deniz ARIKAN
 */
public class MoveUnitAction extends AbstractAction {

    private final FreeMarsController freeMarsController;
    private final Direction direction;

    public MoveUnitAction(FreeMarsController freeMarsController, Direction direction) {
        this.freeMarsController = freeMarsController;
        this.direction = direction;
    }

    public void actionPerformed(ActionEvent e) {
        Realm realm = freeMarsController.getFreeMarsModel().getRealm();
        Unit unit = realm.getPlayerManager().getActivePlayer().getActiveUnit();
        if (unit == null || unit.getCoordinate() == null || unit.getCurrentOrder() != null || unit.getMovementPoints() == 0) {
            return;
        }
        Coordinate toCoordinate = realm.getRelativeCoordinate(unit.getCoordinate(), direction);
        if (toCoordinate == null) {
            return;
        }
        Tile toTile = realm.getTile(toCoordinate);
        Player movingUnitOwnerPlayer = unit.getPlayer();
        Player tileOwnerOtherPlayer = getTileOwnerOtherPlayer(toCoordinate, movingUnitOwnerPlayer);
        Unit otherPlayerUnit = getTileOccupyingOtherPlayerUnit(toCoordinate, movingUnitOwnerPlayer);
        Settlement otherPlayerSettlement = getTileOccupyingOtherPlayerSettlement(toCoordinate, movingUnitOwnerPlayer);
        String diplomaticRelationsEnableTurnProperty = freeMarsController.getFreeMarsModel().getRealm().getProperty("diplomatic_relations_enable_turn");
        int diplomaticRelationsEnableTurn = Integer.parseInt(diplomaticRelationsEnableTurnProperty);
        int currentTurn = freeMarsController.getFreeMarsModel().getNumberOfTurns();

        if (currentTurn < diplomaticRelationsEnableTurn) {
            if (tileOwnerOtherPlayer != null) {
                new NoDiplomacyAllowedTileDialog(freeMarsController, tileOwnerOtherPlayer).display();
                return;
            }
            if (otherPlayerUnit != null) {
                new NoDiplomacyAllowedWithUnitsDialog(freeMarsController, otherPlayerUnit.getPlayer()).display();
                return;
            }
        }

        if (otherPlayerUnit != null) {
            int diplomaticStatus = movingUnitOwnerPlayer.getDiplomacy().getPlayerRelation(otherPlayerUnit.getPlayer()).getStatus();
            if (diplomaticStatus == PlayerRelation.NO_DIPLOMACY_ALLOWED) {
                FreeMarsOptionPane.showMessageDialog(freeMarsController.getCurrentFrame(), "No diplomacy allowed between players", "No diplomacy allowed");
            } else if (diplomaticStatus == PlayerRelation.AT_WAR) {
                new DisplayAttackUnitDialogAction(freeMarsController, unit, otherPlayerUnit).actionPerformed(e);
            } else {
                Object[] options = {"Yes!", "No"};
                StringBuilder message = new StringBuilder();
                message.append("Do you wish to declare war on ");
                message.append(otherPlayerUnit.getPlayer().getName());
                message.append(" and attack ");
                message.append(otherPlayerUnit.getName());
                message.append("?");
                int value = JOptionPane.showOptionDialog(freeMarsController.getCurrentFrame(),
                        message, "Declare war?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
                if (value == JOptionPane.YES_OPTION) {
                    SetDiplomaticStatusCommand setDiplomaticStatusCommand
                            = new SetDiplomaticStatusCommand(realm, movingUnitOwnerPlayer, otherPlayerUnit.getPlayer(), PlayerRelation.AT_WAR);
                    freeMarsController.execute(setDiplomaticStatusCommand);
                    new DisplayAttackUnitDialogAction(freeMarsController, unit, otherPlayerUnit).actionPerformed(e);
                }
            }
            return;
        }
        if (otherPlayerSettlement != null) {
            int diplomaticStatus = movingUnitOwnerPlayer.getDiplomacy().getPlayerRelation(otherPlayerSettlement.getPlayer()).getStatus();
            if (diplomaticStatus == PlayerRelation.NO_DIPLOMACY_ALLOWED) {
                FreeMarsOptionPane.showMessageDialog(freeMarsController.getCurrentFrame(), "No diplomacy allowed between players", "No diplomacy allowed");
            } else if (diplomaticStatus == PlayerRelation.AT_WAR) {
                freeMarsController.execute(new CaptureSettlementCommand(realm, unit, toTile.getSettlement()));
            } else {
                Object[] options = {"Yes!", "No"};
                StringBuilder message = new StringBuilder();
                message.append("Do you wish to declare war on ");
                message.append(otherPlayerSettlement.getPlayer().getName());
                message.append(" and invade ");
                message.append(otherPlayerSettlement.getName());
                message.append("?");
                int value = JOptionPane.showOptionDialog(freeMarsController.getCurrentFrame(),
                        message, "Declare war?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
                if (value == JOptionPane.YES_OPTION) {
                    SetDiplomaticStatusCommand setDiplomaticStatusCommand
                            = new SetDiplomaticStatusCommand(realm, movingUnitOwnerPlayer, otherPlayerSettlement.getPlayer(), PlayerRelation.AT_WAR);
                    freeMarsController.execute(setDiplomaticStatusCommand);
                    freeMarsController.execute(new CaptureSettlementCommand(realm, unit, toTile.getSettlement()));
                }
                return;
            }
        }
        if (tileOwnerOtherPlayer != null) {
            int diplomaticStatus = movingUnitOwnerPlayer.getDiplomacy().getPlayerRelation(tileOwnerOtherPlayer).getStatus();
            if (diplomaticStatus == PlayerRelation.NO_DIPLOMACY_ALLOWED) {
                FreeMarsOptionPane.showMessageDialog(freeMarsController.getCurrentFrame(), "No diplomacy allowed between players", "No diplomacy allowed");
                return;
            }
            if (diplomaticStatus != PlayerRelation.NO_CONTACT && diplomaticStatus != PlayerRelation.ALLIED && diplomaticStatus != PlayerRelation.AT_WAR) {
                Object[] options = {"Yes!", "No"};
                StringBuilder message = new StringBuilder();
                message.append("Do you wish to declare war on ");
                message.append(tileOwnerOtherPlayer.getName());
                message.append(" and enter their territory?");
                int value = JOptionPane.showOptionDialog(freeMarsController.getCurrentFrame(),
                        message, "Declare war?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
                if (value == JOptionPane.YES_OPTION) {
                    SetDiplomaticStatusCommand setDiplomaticStatusCommand
                            = new SetDiplomaticStatusCommand(realm, movingUnitOwnerPlayer, tileOwnerOtherPlayer, PlayerRelation.AT_WAR);
                    freeMarsController.execute(setDiplomaticStatusCommand);
                } else {
                    return;
                }
            }
        }
        boolean unitMovementSound = Boolean.valueOf(freeMarsController.getFreeMarsModel().getFreeMarsPreferences().getProperty("unit_movement_sound"));
        if (unitMovementSound) {
            freeMarsController.executeViewCommandImmediately(new PlaySoundCommand("sound/unit_movement.wav"));
        }
        MoveUnitCommand moveUnitCommand = new MoveUnitCommand(realm, unit, toCoordinate);
        freeMarsController.execute(moveUnitCommand);
        if (unit.getMovementPoints() == 0) {
            freeMarsController.execute(new SkipUnitCommand(unit));
        }
    }

    private Player getTileOwnerOtherPlayer(Coordinate coordinate, Player player) {
        Player tileOwnerPlayer = freeMarsController.getFreeMarsModel().getRealm().getTileOwner(coordinate);
        if (tileOwnerPlayer != null && !tileOwnerPlayer.equals(player)) {
            return tileOwnerPlayer;
        }
        return null;
    }

    private Unit getTileOccupyingOtherPlayerUnit(Coordinate coordinate, Player player) {
        Tile tile = freeMarsController.getFreeMarsModel().getTile(coordinate);
        if (tile.getNumberOfUnits() > 0) {
            Unit tileUnit = tile.getFirstUnit();
            if (!player.equals(tileUnit.getPlayer())) {
                return tileUnit;
            }
        }
        return null;
    }

    private Settlement getTileOccupyingOtherPlayerSettlement(Coordinate coordinate, Player player) {
        Tile tile = freeMarsController.getFreeMarsModel().getTile(coordinate);
        if (tile.getSettlement() != null && !player.equals((tile.getSettlement().getPlayer()))) {
            return tile.getSettlement();
        }
        return null;
    }

    private boolean invadeColonyNeeded(Tile toTile, Unit unit) {
        return (toTile.getSettlement() != null) && (!unit.getPlayer().equals((toTile.getSettlement().getPlayer())));
    }
}

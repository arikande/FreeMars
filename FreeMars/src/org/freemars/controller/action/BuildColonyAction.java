package org.freemars.controller.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

import org.freemars.colony.FreeMarsColony;
import org.freemars.controller.FreeMarsController;
import org.freerealm.Realm;
import org.freerealm.command.BuildSettlementCommand;
import org.freerealm.command.ProcessCollectableCommand;
import org.freerealm.command.SetActiveUnitCommand;
import org.freerealm.executor.CommandResult;
import org.freerealm.player.Player;
import org.freerealm.property.BuildSettlementProperty;
import org.freerealm.tile.Collectable;
import org.freerealm.tile.Tile;
import org.freerealm.unit.Unit;

/**
 *
 * @author Deniz ARIKAN
 */
public class BuildColonyAction extends AbstractAction {

    private final FreeMarsController freeMarsController;
    private final Unit unit;

    public BuildColonyAction(FreeMarsController freeMarsController, Unit unit) {
        super("Build Colony", null);
        this.freeMarsController = freeMarsController;
        this.unit = unit;
    }

    public void actionPerformed(ActionEvent e) {
        Player activePlayer = freeMarsController.getFreeMarsModel().getActivePlayer();
        Unit unitToOrder = unit != null ? unit : freeMarsController.getFreeMarsModel().getActivePlayer().getActiveUnit();
        String suggestedColonyName = freeMarsController.getFreeMarsModel().getActivePlayer().getNextSettlementName();
        String colonyName = JOptionPane.showInputDialog(freeMarsController.getCurrentFrame(), "Colony name?", suggestedColonyName);
        if (colonyName != null) {
            FreeMarsColony freeMarsColony = new FreeMarsColony(freeMarsController.getFreeMarsModel().getRealm());
            Realm realm = freeMarsController.getFreeMarsModel().getRealm();
            BuildSettlementCommand buildSettlementCommand = new BuildSettlementCommand(realm, unitToOrder, colonyName, freeMarsColony);
            CommandResult commandResult = freeMarsController.execute(buildSettlementCommand);
            if (commandResult.getCode() == CommandResult.RESULT_OK) {
                Collectable collectable = freeMarsController.getFreeMarsModel().getRealm().getTile(unitToOrder.getCoordinate()).getCollectable();
                if (collectable != null) {
                    freeMarsController.execute(new ProcessCollectableCommand(realm, unitToOrder, collectable));
                }
                Unit nextUnit = org.freemars.util.Utility.getNextPlayableUnit(activePlayer, unitToOrder);
                freeMarsController.execute(new SetActiveUnitCommand(activePlayer, nextUnit));
            } else {
                JOptionPane.showMessageDialog(freeMarsController.getCurrentFrame(), commandResult.getText());
            }
        }
    }

    public boolean checkEnabled() {
        Player activePlayer = freeMarsController.getFreeMarsModel().getActivePlayer();
        if (activePlayer == null) {
            return false;
        }
        Unit unitToOrder = unit != null ? unit : freeMarsController.getFreeMarsModel().getActivePlayer().getActiveUnit();
        if (unitToOrder == null) {
            return false;
        }
        if (unitToOrder.getMovementPoints() == 0) {
            return false;
        }
        if (unitToOrder.getCurrentOrder() != null) {
            return false;
        }
        Tile tile = freeMarsController.getFreeMarsModel().getTile(unitToOrder.getCoordinate());
        if (tile.getSettlement() != null) {
            return false;
        }
        return unitToOrder.getType().getProperty(BuildSettlementProperty.NAME) != null;
    }
}

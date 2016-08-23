package org.freemars.controller.handler;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.freemars.controller.FreeMarsController;
import org.freemars.controller.viewcommand.DisplayUnitMovementCommand;
import org.freemars.controller.viewcommand.UpdateCoordinatePaintModelCommand;
import org.freerealm.command.AttackUnitCommand;
import org.freerealm.executor.Command;
import org.freerealm.map.Coordinate;
import org.freerealm.unit.Unit;

/**
 *
 * @author Deniz ARIKAN
 */
public class AttackUnitPreCommandHandler implements PreCommandHandler {

    private static final Logger logger = Logger.getLogger(AttackUnitPreCommandHandler.class);
    private FreeMarsController freeMarsController;

    public void handle(FreeMarsController freeMarsController, Command command) {
        logger.trace("Handling AttackUnitCommand");
        AttackUnitCommand attackUnitCommand = (AttackUnitCommand) command;
        Unit attacker = attackUnitCommand.getAttacker();
        Unit defender = attackUnitCommand.getDefender();
        Coordinate fromCoordinate = attacker.getCoordinate();
        Coordinate toCoordinate = defender.getCoordinate();

        freeMarsController.getFreeMarsModel().getFreeMarsViewModel().getTilePaintModel(fromCoordinate).setUnitImage(null);

        DisplayUnitMovementCommand displayUnitMovementCommand
                = new DisplayUnitMovementCommand(freeMarsController, attacker, fromCoordinate, toCoordinate, true);
        displayUnitMovementCommand.setUpdatingPreviousCoordinate(false);
        freeMarsController.executeViewCommand(displayUnitMovementCommand);
        List<Coordinate> coordinates = new ArrayList<Coordinate>();
        coordinates.add(fromCoordinate);
        UpdateCoordinatePaintModelCommand updateCoordinatePaintModelCommand
                = new UpdateCoordinatePaintModelCommand(freeMarsController, coordinates, null);
        freeMarsController.executeViewCommand(updateCoordinatePaintModelCommand);
    }

}

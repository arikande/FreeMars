package org.freemars.updateprocessor;

import org.commandexecutor.Command;
import org.freemars.controller.FreeMarsController;
import org.freemars.controller.viewcommand.DisplayUnitCommand;
import org.freemars.controller.viewcommand.UpdateTilePaintModelUnitsCommand;
import org.freemars.ui.map.TilePaintModel;
import org.freerealm.map.Coordinate;
import org.freerealm.unit.Unit;

public class SetActiveUnitUpdateProcessor extends FreeMarsUpdateProcessor {

	public SetActiveUnitUpdateProcessor(FreeMarsController freeMarsController) {
		super(freeMarsController);
	}

	@Override
	public void processUpdate(Command command) {
		if (getFreeMarsController().getFreeMarsModel().isHumanPlayerActive()) {
			Unit previousActiveUnit = (Unit) command.getParameter("previous_active_unit");
			Unit activeUnit = (Unit) command.getParameter("active_unit");
			Coordinate previousActiveUnitCoordinate = null;
			Coordinate activeUnitCoordinate = null;
			if (previousActiveUnit != null) {
				previousActiveUnitCoordinate = previousActiveUnit.getCoordinate();
			}
			if (activeUnit != null) {
				activeUnitCoordinate = activeUnit.getCoordinate();
			}
			if (previousActiveUnitCoordinate != null && !previousActiveUnitCoordinate.equals(activeUnitCoordinate)) {
				TilePaintModel tilePaintModel = getFreeMarsController().getFreeMarsModel().getFreeMarsViewModel().getTilePaintModel(previousActiveUnitCoordinate);
				if (tilePaintModel != null) {
					tilePaintModel.setPaintingActiveUnitIndicator(false);
				}
			}
			if (activeUnitCoordinate != null) {
				getFreeMarsController().executeViewCommand(new UpdateTilePaintModelUnitsCommand(getFreeMarsController(), activeUnit));
				getFreeMarsController().executeViewCommand(new DisplayUnitCommand(getFreeMarsController(), activeUnit));
				getFreeMarsController().getGameFrame().update();
			}
		}
	}

}
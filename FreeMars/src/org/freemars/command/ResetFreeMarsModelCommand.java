package org.freemars.command;

import org.apache.log4j.Logger;
import org.freemars.earth.Earth;
import org.freemars.model.FreeMarsModel;
import org.freemars.model.FreeMarsViewModel;
import org.freerealm.command.FreeRealmAbstractCommand;
import org.freerealm.command.ResetRealmCommand;

/**
 * Command class to reset the given realm.
 * 
 * @author Deniz ARIKAN
 */
public class ResetFreeMarsModelCommand extends FreeRealmAbstractCommand {

	private final FreeMarsModel freeMarsModel;

	/**
	 * Constructs a ResetFreeMarsModelCommand.
	 */
	public ResetFreeMarsModelCommand(FreeMarsModel freeMarsModel) {
		this.freeMarsModel = freeMarsModel;
	}

	/**
	 * Executes command to reset the given Free Mars model.
	 * 
	 * @return CommandResult
	 */
	public void execute() {
		getExecutor().execute(new ResetRealmCommand(freeMarsModel.getRealm()));
		freeMarsModel.setEarth(new Earth(freeMarsModel.getRealm()));
		freeMarsModel.getFreeMarsViewModel().setMapPanelDisplayingGrid(false);
		freeMarsModel.getFreeMarsViewModel().setMapPanelDisplayingCoordinates(false);
		freeMarsModel.getFreeMarsViewModel().setMapPanelDisplayingTileTypes(false);
		freeMarsModel.getFreeMarsViewModel().setMapPanelDisplayingUnitPath(false);
		freeMarsModel.getFreeMarsViewModel().setMapPanelZoomLevel(FreeMarsViewModel.MAIN_MAP_DEFAULT_ZOOM_LEVEL);
		freeMarsModel.getFreeMarsViewModel().setMiniMapPanelZoomLevel(FreeMarsViewModel.MINI_MAP_DEFAULT_ZOOM_LEVEL);
		freeMarsModel.getFreeMarsViewModel().setCenteredCoordinate(FreeMarsViewModel.DEFAULT_CENTERED_COORDINATE);
		freeMarsModel.getFreeMarsViewModel().setMapPanelUnitPath(null);
		freeMarsModel.setHumanPlayer(null);
		freeMarsModel.setHumanPlayerDefeated(false);
		freeMarsModel.clearObjectives();
		Logger.getLogger(ResetFreeMarsModelCommand.class).info("Free Mars model reset successfully.");
		setState(SUCCEEDED);
	}
}

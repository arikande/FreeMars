package org.freemars.controller.viewcommand;

import org.freemars.controller.FreeMarsController;
import org.freerealm.command.AbstractCommand;
import org.freerealm.executor.CommandResult;

/**
 *
 * @author Deniz ARIKAN
 */
public class SetMiniMapZoomLevelCommand extends AbstractCommand implements ViewCommand {

    private final FreeMarsController freeMarsController;
    private final int zoomLevel;

    public SetMiniMapZoomLevelCommand(FreeMarsController freeMarsController, int zoomLevel) {
        this.freeMarsController = freeMarsController;
        this.zoomLevel = zoomLevel;
    }

    @Override
    public String toString() {
        return "SetMiniMapZoomLevel - Zoom level : " + zoomLevel;
    }

    public CommandResult execute() {
        freeMarsController.getFreeMarsModel().getFreeMarsViewModel().setMiniMapPanelZoomLevel(zoomLevel);
        freeMarsController.updateActions();
        freeMarsController.updateGameFrame();
        return new CommandResult(CommandResult.RESULT_OK, "");
    }

    public boolean isBlockingViewExecutionThread() {
        return false;
    }

}

package org.freemars.controller.viewcommand;

import org.freemars.controller.FreeMarsController;
import org.freerealm.command.AbstractCommand;
import org.freerealm.executor.CommandResult;
import org.freerealm.unit.Unit;

/**
 *
 * @author Deniz ARIKAN
 */
public class DisplayUnitCommand extends AbstractCommand implements ViewCommand {

    private final FreeMarsController freeMarsController;
    private final Unit unit;

    public DisplayUnitCommand(FreeMarsController freeMarsController, Unit unit) {
        this.freeMarsController = freeMarsController;
        this.unit = unit;
    }

    @Override
    public String toString() {
        return "DisplayUnit";
    }

    public CommandResult execute() {
        if (unit != null && unit.getCoordinate() != null && !unit.getCoordinate().equals(freeMarsController.getFreeMarsModel().getFreeMarsViewModel().getCenteredCoordinate())) {
            boolean alwaysCenterOnActiveUnit = Boolean.valueOf(freeMarsController.getFreeMarsModel().getFreeMarsPreferences().getProperty("always_center_on_active_unit"));
            if (alwaysCenterOnActiveUnit || !freeMarsController.getFreeMarsModel().isMapPanelDisplayingCoordinate(unit.getCoordinate())) {
                freeMarsController.getFreeMarsModel().getFreeMarsViewModel().setCenteredCoordinate(unit.getCoordinate());
                freeMarsController.getGameFrame().getMapPanel().update();
            }
        }
        return new CommandResult(CommandResult.RESULT_OK, "");
    }

    public boolean isBlockingViewExecutionThread() {
        return false;
    }

}

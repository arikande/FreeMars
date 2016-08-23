package org.freemars.controller.viewcommand;

import org.freemars.controller.FreeMarsController;
import org.freerealm.command.AbstractCommand;
import org.freerealm.executor.CommandResult;

/**
 * @author Deniz ARIKAN
 */
public class ClearPaintModelsCommand extends AbstractCommand implements ViewCommand {

    private final FreeMarsController freeMarsController;

    public ClearPaintModelsCommand(FreeMarsController freeMarsController) {
        this.freeMarsController = freeMarsController;
    }

    @Override
    public String toString() {
        return "ClearPaintModels";
    }

    public CommandResult execute() {
        freeMarsController.getFreeMarsModel().getFreeMarsViewModel().clearPaintModels();
        synchronized (ViewCommandExecutionThread.WAIT_ON) {
            ViewCommandExecutionThread.WAIT_ON.notify();
        }
        return new CommandResult(CommandResult.RESULT_OK, "");
    }

    public boolean isBlockingViewExecutionThread() {
        return true;
    }

}

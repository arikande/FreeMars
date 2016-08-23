package org.freemars.controller.viewcommand;

import org.freemars.controller.FreeMarsController;
import org.freemars.ui.util.FreeMarsDialog;
import org.freerealm.command.AbstractCommand;
import org.freerealm.executor.CommandResult;

/**
 *
 * @author Deniz ARIKAN
 */
public class DisplayDialogCommand extends AbstractCommand implements ViewCommand {

    private final FreeMarsController freeMarsController;
    private final FreeMarsDialog freeMarsDialog;
    private final int width;
    private final int height;

    public DisplayDialogCommand(FreeMarsController freeMarsController, FreeMarsDialog freeMarsDialog, int width, int height) {
        this.freeMarsController = freeMarsController;
        this.freeMarsDialog = freeMarsDialog;
        this.width = width;
        this.height = height;
    }

    @Override
    public String toString() {
        return "DisplayDialog";
    }

    public CommandResult execute() {
        freeMarsDialog.display(width, height);
        synchronized (ViewCommandExecutionThread.WAIT_ON) {
            ViewCommandExecutionThread.WAIT_ON.notify();
        }
        return new CommandResult(CommandResult.RESULT_OK, "");
    }

    public boolean isBlockingViewExecutionThread() {
        return true;
    }

}

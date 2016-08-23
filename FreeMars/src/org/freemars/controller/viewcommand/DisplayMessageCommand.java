package org.freemars.controller.viewcommand;

import org.freemars.controller.FreeMarsController;
import org.freemars.ui.util.FreeMarsOptionPane;
import org.freerealm.command.AbstractCommand;
import org.freerealm.executor.CommandResult;

/**
 * @author Deniz ARIKAN
 */
public class DisplayMessageCommand extends AbstractCommand implements ViewCommand {

    private final FreeMarsController freeMarsController;
    private final String message;

    public DisplayMessageCommand(FreeMarsController freeMarsController, String message) {
        this.freeMarsController = freeMarsController;
        this.message = message;
    }

    @Override
    public String toString() {
        return "DisplayMessage";
    }

    public CommandResult execute() {
        FreeMarsOptionPane.showMessageDialog(freeMarsController.getCurrentFrame(), message);
        synchronized (ViewCommandExecutionThread.WAIT_ON) {
            ViewCommandExecutionThread.WAIT_ON.notify();
        }
        return new CommandResult(CommandResult.RESULT_OK, "");
    }

    public boolean isBlockingViewExecutionThread() {
        return true;
    }

}

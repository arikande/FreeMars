package org.freemars.controller.viewcommand;

import org.apache.log4j.Logger;
import org.freemars.controller.FreeMarsController;
import org.freerealm.command.AbstractCommand;
import org.freerealm.executor.CommandResult;

/**
 * @author Deniz ARIKAN
 */
public class RepaintMapPanelCommand extends AbstractCommand implements ViewCommand {

    private static final Logger logger = Logger.getLogger(RepaintMapPanelCommand.class);
    private final FreeMarsController freeMarsController;

    public RepaintMapPanelCommand(FreeMarsController freeMarsController) {
        this.freeMarsController = freeMarsController;
    }

    @Override
    public String toString() {
        return "RepaintMapPanel";
    }

    public CommandResult execute() {
        long startTime = System.currentTimeMillis();
        freeMarsController.getGameFrame().getMapPanel().repaint();
        long executionTime = System.currentTimeMillis() - startTime;
        if (executionTime > 10) {
            logger.info("RepaintMapPanelCommand executed in " + executionTime + " miliseconds.");
        }
        synchronized (ViewCommandExecutionThread.WAIT_ON) {
            ViewCommandExecutionThread.WAIT_ON.notify();
        }
        return new CommandResult(CommandResult.RESULT_OK, "");
    }

    public boolean isBlockingViewExecutionThread() {
        return true;
    }

}

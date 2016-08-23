package org.freemars.controller.viewcommand;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.freemars.controller.FreeMarsController;

/**
 * @author Deniz ARIKAN
 */
public class ViewCommandExecutionThread extends Thread {

    private static final Logger logger = Logger.getLogger(ViewCommandExecutionThread.class);

    private final List<ViewCommand> viewCommands;

    public static final Object WAIT_ON = new Object();

    FreeMarsController freeMarsController;

    public ViewCommandExecutionThread(FreeMarsController freeMarsController) {
        this.freeMarsController = freeMarsController;
        viewCommands = new ArrayList<ViewCommand>();
    }

    @Override
    public void run() {
        while (true) {
            if (viewCommands.size() > 0 && freeMarsController.getFreeMarsModel().isHumanPlayerActive()) {
                synchronized (WAIT_ON) {
                    try {
                        /*
                         StringBuilder log = new StringBuilder();
                         log.append("View commands(");
                         log.append(viewCommands.size());
                         log.append(") -> ");
                         log.append(viewCommands);
                         logger.debug(log.toString());
                         */
                        ViewCommand viewCommand = viewCommands.get(0);
                        executeCommand(viewCommand);
                        if (viewCommand.isBlockingViewExecutionThread()) {
                            WAIT_ON.wait();
                        }
                        viewCommands.remove(0);
                    } catch (InterruptedException interruptedException) {
                    }
                }
            }
            try {
                sleep(10);
            } catch (InterruptedException interruptedException) {
            }
        }

    }

    public void addCommandToQueue(ViewCommand viewCommand) {
        if (viewCommand instanceof RepaintMapPanelCommand && viewCommands.size() > 0) {
            ViewCommand lastCommand = viewCommands.get(viewCommands.size() - 1);
            if (lastCommand instanceof RepaintMapPanelCommand) {
                return;
            }
        }
        viewCommands.add(viewCommand);
    }

    public void executeCommand(ViewCommand viewCommand) {
        new Thread(new ViewCommandRunnable(viewCommand)).start();
    }

}

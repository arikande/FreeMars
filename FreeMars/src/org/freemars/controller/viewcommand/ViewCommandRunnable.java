package org.freemars.controller.viewcommand;

import org.apache.log4j.Logger;
import org.freerealm.executor.Command;

/**
 *
 * @author Deniz ARIKAN
 */
public class ViewCommandRunnable implements Runnable {

    private static final Logger logger = Logger.getLogger(ViewCommandRunnable.class);
    private final Command command;

    public ViewCommandRunnable(Command command) {
        this.command = command;
    }

    public void run() {
        long start = System.currentTimeMillis();
        command.execute();
        long duration = System.currentTimeMillis() - start;
        if (logger.isTraceEnabled()) {
            StringBuilder log = new StringBuilder();
            log.append(command.toString());
            log.append(" executed in ");
            log.append(duration);
            log.append(" milliseconds.");
            logger.trace(log);
        }
    }
}

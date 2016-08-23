package org.freemars.controller.viewcommand;

import org.freerealm.executor.Command;

/**
 *
 * @author Deniz ARIKAN
 */
public interface ViewCommand extends Command {

    public boolean isBlockingViewExecutionThread();
}

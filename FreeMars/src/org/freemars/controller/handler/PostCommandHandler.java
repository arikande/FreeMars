package org.freemars.controller.handler;

import org.freemars.controller.FreeMarsController;
import org.freerealm.executor.CommandResult;

/**
 *
 * @author Deniz ARIKAN
 */
public interface PostCommandHandler {

    public void handleUpdate(FreeMarsController freeMarsController, CommandResult commandResult);

}

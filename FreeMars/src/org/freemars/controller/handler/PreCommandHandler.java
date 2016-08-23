package org.freemars.controller.handler;

import org.freemars.controller.FreeMarsController;
import org.freerealm.executor.Command;

/**
 *
 * @author Deniz ARIKAN
 */
public interface PreCommandHandler {

    public void handle(FreeMarsController freeMarsController, Command command);

}

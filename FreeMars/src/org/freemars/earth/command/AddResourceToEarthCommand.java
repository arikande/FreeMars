package org.freemars.earth.command;

import org.freemars.controller.FreeMarsController;
import org.freerealm.command.FreeRealmAbstractCommand;
import org.freerealm.resource.Resource;

/**
 * 
 * @author Deniz ARIKAN
 */
public class AddResourceToEarthCommand extends FreeRealmAbstractCommand {

	private final FreeMarsController freeMarsController;
	private final Resource resource;
	private final int amount;

	public AddResourceToEarthCommand(FreeMarsController freeMarsController, Resource resource, int amount) {
		this.freeMarsController = freeMarsController;
		this.resource = resource;
		this.amount = amount;
	}

	public void execute() {
		freeMarsController.getFreeMarsModel().getEarth().addResource(resource, amount);
		setState(SUCCEEDED);
	}

}

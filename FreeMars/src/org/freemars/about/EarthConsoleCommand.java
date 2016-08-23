package org.freemars.about;

import org.freemars.earth.command.AddResourceToEarthCommand;
import org.freerealm.resource.Resource;

/**
 * 
 * @author Deniz ARIKAN
 */
public class EarthConsoleCommand extends ConsoleCommand {

	public void execute() {
		String resourceIdArgument = getCommandArguments()[2];
		int resourceId = Integer.parseInt(resourceIdArgument);
		String resourceQuantityArgument = getCommandArguments()[3];
		int resourceQuantity = Integer.parseInt(resourceQuantityArgument);
		Resource resource = getFreeMarsController().getFreeMarsModel().getRealm().getResourceManager().getResource(resourceId);
		AddResourceToEarthCommand addResourceToEarthCommand = new AddResourceToEarthCommand(getFreeMarsController(), resource, resourceQuantity);
		getFreeMarsController().execute(addResourceToEarthCommand);
		setState(SUCCEEDED);
	}

	@Override
	public String[] getCommands() {
		return new String[] { "earth" };
	}

}

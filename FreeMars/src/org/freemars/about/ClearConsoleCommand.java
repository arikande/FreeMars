package org.freemars.about;

/**
 * 
 * @author Deniz ARIKAN
 */
public class ClearConsoleCommand extends ConsoleCommand {

	public void execute() {
		getConsoleDialog().clearCommandOutput();
		setState(SUCCEEDED);
	}

	@Override
	public String[] getCommands() {
		return new String[] { "cls", "clear" };
	}

}

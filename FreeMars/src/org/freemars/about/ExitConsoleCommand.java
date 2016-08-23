package org.freemars.about;

/**
 * 
 * @author Deniz ARIKAN
 */
public class ExitConsoleCommand extends ConsoleCommand {

	@Override
	public String[] getCommands() {
		return new String[] { "exit", "quit", "close", "bye" };
	}

	public void execute() {
		getConsoleDialog().dispose();
		setState(SUCCEEDED);
	}

}

package org.freemars.about;

import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.Logger;
import org.freemars.controller.FreeMarsController;

/**
 * 
 * @author Deniz ARIKAN
 */
public class ConsoleCommandExecutor {

	private static final Logger logger = Logger.getLogger(ConsoleCommandExecutor.class);
	private static final Map<String, ConsoleCommand> commands = new TreeMap<String, ConsoleCommand>();

	public static void addConsoleCommand(Class consoleCommandClass) {
		try {
			ConsoleCommand consoleCommand = (ConsoleCommand) consoleCommandClass.newInstance();
			for (String command : consoleCommand.getCommands()) {
				commands.put(command, consoleCommand);
			}
		} catch (InstantiationException instantiationException) {
			logger.error("InstantiationException while adding command " + instantiationException);
		} catch (IllegalAccessException illegalAccessException) {
			logger.error("IllegalAccessException while adding command " + illegalAccessException);
		}
	}

	public static String executeCommand(FreeMarsController freeMarsController, ConsoleDialog consoleDialog, String[] commandParts) {
		String commandText = commandParts[0];
		if (commandText == null || commandText.equals("")) {
			return "";
		}
		ConsoleCommand consoleCommand = commands.get(commandText);
		if (consoleCommand != null) {
			consoleCommand.setCommandArguments(commandParts);
			consoleCommand.setFreeMarsController(freeMarsController);
			consoleCommand.setConsoleDialog(consoleDialog);
			consoleCommand.execute();
			return consoleCommand.getText();
		} else {
			StringBuilder unknownCommandOutput = new StringBuilder();
			unknownCommandOutput.append("Unknown command");
			unknownCommandOutput.append(System.getProperty("line.separator"));
			return unknownCommandOutput.toString();
		}
	}

}

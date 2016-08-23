package org.freemars.about;

import org.commandexecutor.AbstractCommand;
import org.commandexecutor.Executor;
import org.freemars.controller.FreeMarsController;

/**
 * 
 * @author Deniz ARIKAN
 */
public abstract class ConsoleCommand extends AbstractCommand {

	private FreeMarsController freeMarsController;
	private ConsoleDialog consoleDialog;
	private Executor executor;
	private String[] commandArguments;

	public abstract String[] getCommands();

	public String getHelp() {
		return null;
	}

	public Executor getExecutor() {
		return executor;
	}

	public void setExecutor(Executor executor) {
		this.executor = executor;
	}

	protected String[] getCommandArguments() {
		return commandArguments;
	}

	public void setCommandArguments(String[] commandArguments) {
		this.commandArguments = commandArguments;
	}

	public FreeMarsController getFreeMarsController() {
		return freeMarsController;
	}

	public void setFreeMarsController(FreeMarsController freeMarsController) {
		this.freeMarsController = freeMarsController;
	}

	public ConsoleDialog getConsoleDialog() {
		return consoleDialog;
	}

	public void setConsoleDialog(ConsoleDialog consoleDialog) {
		this.consoleDialog = consoleDialog;
	}

}

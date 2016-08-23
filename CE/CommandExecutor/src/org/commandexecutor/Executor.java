package org.commandexecutor;

/**
 * 
 * @author Deniz ARIKAN
 */
public interface Executor extends Runnable {

	public void execute(Command command);

	public void addCommandToQueue(Command command);
}

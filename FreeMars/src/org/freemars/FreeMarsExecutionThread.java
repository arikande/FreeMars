package org.freemars;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.commandexecutor.Command;
import org.commandexecutor.Executor;
import org.commandexecutor.UpdateManager;

public class FreeMarsExecutionThread extends Thread implements Executor {

	private static final Logger logger = Logger.getLogger(FreeMarsExecutionThread.class);
	private UpdateManager updateManager;
	private final List<Command> commands;

	public FreeMarsExecutionThread(UpdateManager updateManager) {
		commands = new ArrayList<>();
		this.updateManager = updateManager;
	}

	@Override
	public void run() {
		while (true) {
			try {
				if (!commands.isEmpty()) {
					process(commands.get(0));
					commands.remove(0);
				}
				Thread.sleep(50);
			} catch (InterruptedException interruptedException) {
			}
		}
	}

	@Override
	public void execute(Command command) {
		logger.info("Executing command " + command.getClass().getCanonicalName());
		command.setExecutor(this);
		command.setState(Command.EXECUTING);
		command.execute();
		updateManager.update(command);
	}

	@Override
	public void addCommandToQueue(Command command) {
		commands.add(command);
	}

	private void process(Command command) throws InterruptedException {
		if (command.getWaitOn() != null) {
			synchronized (command.getWaitOn()) {
				command.getWaitOn().wait();
			}
		}
		execute(command);
		if (command.getSignal() != null) {
			synchronized (command.getSignal()) {
				command.getSignal().notifyAll();
			}
		}
	}
}

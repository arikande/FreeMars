package org.commandexecutor;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Deniz ARIKAN
 */
public class DefaultExecutor implements Executor {

	private final List<Command> commands;

	public DefaultExecutor() {
		commands = new ArrayList<>();
	}

	@Override
	public void run() {
		while (true) {
			try {
				if (!commands.isEmpty()) {
					process(commands.get(0));
					commands.remove(0);
				}
				Thread.sleep(100);
			} catch (InterruptedException interruptedException) {
			}
		}
	}

	public void execute(Command command) {
		command.setExecutor(this);
		command.execute();
	}

	public void addCommandToQueue(Command command) {
		commands.add(command);
	}

	private void process(Command command) throws InterruptedException {
		if (command.getWaitOn() != null) {
			synchronized (command.getWaitOn()) {
				command.getWaitOn().wait(20000);
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

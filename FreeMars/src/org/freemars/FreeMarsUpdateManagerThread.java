package org.freemars;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.commandexecutor.Command;
import org.commandexecutor.UpdateManager;
import org.commandexecutor.UpdateProcessor;
import org.freemars.controller.FreeMarsController;
import org.freemars.updateprocessor.AddExploredCoordinatesToPlayerUpdateProcessor;
import org.freemars.updateprocessor.SetActiveUnitUpdateProcessor;
import org.freemars.updateprocessor.SkipUnitUpdateProcessor;
import org.freemars.updateprocessor.UnitSetMovementPointsUpdateProcessor;
import org.freerealm.command.AddExploredCoordinatesToPlayerCommand;
import org.freerealm.command.SetActiveUnitCommand;
import org.freerealm.command.SkipUnitCommand;
import org.freerealm.command.UnitSetMovementPointsCommand;

public class FreeMarsUpdateManagerThread extends Thread implements UpdateManager {

	private static final Logger logger = Logger.getLogger(FreeMarsUpdateManagerThread.class);
	private final List<Command> commands;

	private Map<String, UpdateProcessor> updateProcessorMap;

	public FreeMarsUpdateManagerThread(FreeMarsController freeMarsController) {
		commands = new ArrayList<>();
		updateProcessorMap = new HashMap<>();
		updateProcessorMap.put(SkipUnitCommand.class.getCanonicalName(), new SkipUnitUpdateProcessor(freeMarsController));
		updateProcessorMap.put(UnitSetMovementPointsCommand.class.getCanonicalName(), new UnitSetMovementPointsUpdateProcessor(freeMarsController));
		updateProcessorMap.put(SetActiveUnitCommand.class.getCanonicalName(), new SetActiveUnitUpdateProcessor(freeMarsController));
		updateProcessorMap.put(AddExploredCoordinatesToPlayerCommand.class.getCanonicalName(), new AddExploredCoordinatesToPlayerUpdateProcessor(freeMarsController));
	}

	@Override
	public void run() {
		logger.info("Free Mars update manager thread started.");
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

	public void addUpdateToQueue(Command command) {
		commands.add(command);
	}

	private void process(Command command) {
		UpdateProcessor updateProcessor = updateProcessorMap.get(command.getClass().getCanonicalName());
		if (updateProcessor != null) {
			long start = System.currentTimeMillis();
			updateProcessor.processUpdate(command);
			long duration = System.currentTimeMillis() - start;
			logger.trace("Update processor " + updateProcessor + " processed in " + duration + " ms.");
		}
	}

	@Override
	public void update(Command command) {
		addUpdateToQueue(command);
	}
}

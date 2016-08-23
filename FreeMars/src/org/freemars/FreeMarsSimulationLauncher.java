package org.freemars;

import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.freemars.ai.AIPlayer;
import org.freemars.command.AddFreeMarsAIPlayerCommand;
import org.freemars.command.ResetFreeMarsModelCommand;
import org.freemars.controller.FreeMarsController;
import org.freemars.earth.Earth;
import org.freemars.mission.MissionReader;
import org.freemars.model.FreeMarsModel;
import org.freerealm.FreeRealmConstants;
import org.freerealm.Realm;
import org.freerealm.command.AddRequiredPopulationResourceCommand;
import org.freerealm.command.InitializeRealmCommand;
import org.freerealm.command.ReadMapFileCommand;
import org.freerealm.command.SetActivePlayerCommand;
import org.freerealm.command.SetMaximumNumberOfTurnsCommand;
import org.freerealm.map.Coordinate;
import org.freerealm.nation.Nation;

/**
 * 
 * @author Deniz ARIKAN
 */
public class FreeMarsSimulationLauncher {

	private static final Logger logger = Logger.getLogger(FreeMarsSimulationLauncher.class);

	public static void main(final String[] args) {
		initialize();
		int simulationCount = 1;
		logger.info(simulationCount + " simulation(s) will be performed.");
		logger.info("Free Mars version : " + FreeMarsModel.getVersion());
		logger.info("Free Realm version : " + FreeRealmConstants.getVersion());
		int totalTradeIncome = 0;
		int totalCompletedMissions = 0;
		int totalFailedMissions = 0;

		for (int i = 0; i < simulationCount; i++) {
			logger.info("***");
			logger.info("Starting simulation #" + (i + 1) + ".");
			long start = System.currentTimeMillis();
			final FreeMarsController freeMarsController = new FreeMarsController();
			freeMarsController.getFreeMarsModel().setMode(FreeMarsModel.SIMULATION_MODE);
			Realm realm = freeMarsController.getFreeMarsModel().getRealm();
			freeMarsController.execute(new ResetFreeMarsModelCommand(freeMarsController.getFreeMarsModel()));
			freeMarsController.execute(new InitializeRealmCommand(freeMarsController.getFreeMarsModel().getRealm()));
			freeMarsController.execute(new SetMaximumNumberOfTurnsCommand(realm, 366));
			freeMarsController.execute(new AddRequiredPopulationResourceCommand(realm, 0, 1));
			freeMarsController.execute(new AddRequiredPopulationResourceCommand(realm, 1, 6));
			freeMarsController.execute(new AddRequiredPopulationResourceCommand(realm, 2, 1));
			Earth earth = new Earth(freeMarsController.getFreeMarsModel().getRealm());
			freeMarsController.getFreeMarsModel().setEarth(earth);
			InputStream inputStream = ClassLoader.getSystemResourceAsStream("maps/mars_small.frm");
			freeMarsController.execute(new ReadMapFileCommand(realm, inputStream));
			Nation nation = realm.getNationManager().getNation(5);
			AIPlayer aiPlayer = new AIPlayer(freeMarsController.getFreeMarsModel().getRealm());
			AddFreeMarsAIPlayerCommand addFreeMarsAIPlayerCommand = new AddFreeMarsAIPlayerCommand(freeMarsController, aiPlayer, nation);
			freeMarsController.execute(addFreeMarsAIPlayerCommand);
			Coordinate coordinate = (Coordinate) addFreeMarsAIPlayerCommand.getParameter("starting_coordinate");
			logger.info("Starting coordinate for " + aiPlayer.getName() + " is " + coordinate + ".");
			new MissionReader().readMissions(freeMarsController);
			freeMarsController.execute(new SetActivePlayerCommand(realm, aiPlayer));
			long duration = System.currentTimeMillis() - start;
			logger.info("Simulation summary...");
			logger.info("Population : " + aiPlayer.getPopulation() + " Colony count : " + aiPlayer.getSettlementCount());
			logger.info("Wealth : " + aiPlayer.getWealth());
			logger.info("Unit count : " + aiPlayer.getUnitCount());
			logger.info("Trade income : " + aiPlayer.getTradeIncome());
			logger.info("Mission count : " + aiPlayer.getMissionCount());
			logger.info("Completed mission count : " + aiPlayer.getCompletedMissionCount());
			logger.info("Completed mission ratio : " + aiPlayer.getCompletedMissionPercent() + "%");
			logger.info("Failed mission count : " + aiPlayer.getFailedMissionCount());
			logger.info("Failed mission ratio : " + aiPlayer.getFailedMissionPercent() + "%");
			logger.info("Financed colonists : " + aiPlayer.getFinancedColonistTotal());
			logger.info("Simulation " + (i + 1) + " finished successfully in " + getDurationBreakdown(duration) + ".");

			totalTradeIncome = totalTradeIncome + aiPlayer.getTradeIncome();
			totalCompletedMissions = totalCompletedMissions + aiPlayer.getCompletedMissionCount();
			totalFailedMissions = totalFailedMissions + aiPlayer.getFailedMissionCount();
		}
		logger.info(simulationCount + " simulation(s) finished successfully.");
		logger.info("Mean trade income : " + (totalTradeIncome / simulationCount));
		logger.info("Completed mission(s) : " + totalCompletedMissions);
		logger.info("Failed mission(s) : " + totalFailedMissions);
		System.exit(0);
	}

	private static void initialize() {
		FreeMarsInitializer.initializeLogger();
		FreeMarsInitializer.initializeGameFolders();
		FreeMarsInitializer.initializeTags();
		logger.info("Free Mars simulator initialized successfully.");
	}

	public static String getDurationBreakdown(long millis) {
		if (millis < 0) {
			throw new IllegalArgumentException("Duration must be greater than zero!");
		}
		long minutes = TimeUnit.MILLISECONDS.toMinutes(millis);
		millis -= TimeUnit.MINUTES.toMillis(minutes);
		long seconds = TimeUnit.MILLISECONDS.toSeconds(millis);
		StringBuilder sb = new StringBuilder(64);
		if (minutes > 0) {
			sb.append(minutes);
			sb.append(" minute(s) ");
		}
		sb.append(seconds);
		sb.append(" second(s)");
		return sb.toString();
	}
}

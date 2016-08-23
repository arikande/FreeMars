package org.freemars.command;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.freemars.ai.AIPlayer;
import org.freemars.ai.DecisionModel;
import org.freemars.controller.FreeMarsController;
import org.freerealm.command.FreeRealmAbstractCommand;
import org.freerealm.nation.Nation;

/**
 * 
 * @author Deniz ARIKAN
 */
public class AddFreeMarsAIPlayerCommand extends FreeRealmAbstractCommand {

	private static final Logger logger = Logger.getLogger(AddFreeMarsAIPlayerCommand.class);

	private final FreeMarsController freeMarsController;
	private final AIPlayer aiPlayer;
	private final Nation nation;

	public AddFreeMarsAIPlayerCommand(FreeMarsController freeMarsController, AIPlayer aiPlayer, Nation nation) {
		this.freeMarsController = freeMarsController;
		this.aiPlayer = aiPlayer;
		this.nation = nation;
	}

	public void execute() {
		getExecutor().execute(new AddFreeMarsPlayerCommand(freeMarsController, aiPlayer, nation));
		aiPlayer.setDecisionModel(new DecisionModel(freeMarsController, aiPlayer));
		Properties aiProperties = new Properties();
		String nationName = aiPlayer.getNation().getAdjective().toLowerCase().replaceAll(" ", "_");
		String propertiesFileName = "config/ai/" + nationName + "_ai_player.properties";
		BufferedInputStream bufferedInputStream = new BufferedInputStream(ClassLoader.getSystemResourceAsStream(propertiesFileName));
		try {
			aiProperties.load(bufferedInputStream);
			if (aiProperties.size() > 0) {
				StringBuilder log = new StringBuilder();
				log.append(aiProperties.size()).append(" properties loaded for ");
				log.append("\"");
				log.append(aiPlayer.getName()).append("\"");
				log.append(" from file ");
				log.append(propertiesFileName).append(".");
				logger.info(log);
			}
		} catch (IOException iOException) {
			setText("IOException while adding player");
			setState(FAILED);
			return;
		}
		aiPlayer.setAIProperties(aiProperties);
		setState(SUCCEEDED);
	}

}

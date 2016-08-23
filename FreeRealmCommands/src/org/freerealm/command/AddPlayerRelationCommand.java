package org.freerealm.command;

import org.apache.log4j.Logger;
import org.freerealm.diplomacy.PlayerRelation;
import org.freerealm.player.Player;

/**
 * 
 * @author Deniz ARIKAN
 */
public class AddPlayerRelationCommand extends FreeRealmAbstractCommand {

	private static final Logger logger = Logger.getLogger(AddPlayerCommand.class);
	private final Player player1;
	private final Player player2;

	public AddPlayerRelationCommand(Player player1, Player player2) {
		this.player1 = player1;
		this.player2 = player2;
	}

	public void execute() {
		PlayerRelation playerRelation1 = new PlayerRelation(player2.getId());
		player1.getDiplomacy().addPlayerRelation(playerRelation1);
		PlayerRelation playerRelation2 = new PlayerRelation(player1.getId());
		player2.getDiplomacy().addPlayerRelation(playerRelation2);

		StringBuilder log = new StringBuilder();
		log.append("Added diplomatic relations between ");
		log.append(player1);
		log.append(" and ");
		log.append(player2);
		log.append(".");
		logger.info(log);

		setState(SUCCEEDED);
	}

}

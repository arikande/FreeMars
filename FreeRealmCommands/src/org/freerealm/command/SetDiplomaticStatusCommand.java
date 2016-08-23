package org.freerealm.command;

import org.apache.log4j.Logger;
import org.freerealm.Realm;
import org.freerealm.diplomacy.PlayerRelation;
import org.freerealm.player.Player;

/**
 * Command class to set diplomatic status between two players
 * 
 * @author Deniz ARIKAN
 */
public class SetDiplomaticStatusCommand extends FreeRealmAbstractCommand {

	private static final Logger logger = Logger.getLogger(SetDiplomaticStatusCommand.class);
	private final Player player1;
	private final Player player2;
	private final int status;

	/**
	 * Constructs a SetDiplomaticStatusCommand using two players and new
	 * diplomatic status.
	 * 
	 * @param player1
	 *            First player
	 * @param player2
	 *            Second player
	 * @param status
	 *            New diplomatic status
	 */
	public SetDiplomaticStatusCommand(Realm realm, Player player1, Player player2, int status) {
		super(realm);
		this.player1 = player1;
		this.player2 = player2;
		this.status = status;
	}

	/**
	 * Constructs a SetDiplomaticStatusCommand using two players and new
	 * diplomatic status.
	 * 
	 * @param player1
	 *            First player
	 * @param player2
	 *            Second player
	 */
	public SetDiplomaticStatusCommand(Realm realm, Player player1, Player player2) {
		this(realm, player1, player2, PlayerRelation.NO_CONTACT);
	}

	/**
	 * Executes command to set new diplomatic status between given players.
	 * 
	 * @return CommandResult
	 */
	public void execute() {
		if (player1 == null || player2 == null) {
			setText("Players must not be null");
			setState(FAILED);
		} else {
			PlayerRelation playerRelation = player1.getDiplomacy().getPlayerRelation(player2);
			int oldStatus = playerRelation.getStatus();
			playerRelation.setStatus(status);
			playerRelation = player2.getDiplomacy().getPlayerRelation(player1);
			playerRelation.setStatus(status);
			if (status == PlayerRelation.AT_WAR) {
				getExecutor().execute(new DecreaseDiplomaticAttitudeCommand(player1, player2, 300));
				getExecutor().execute(new DecreaseDiplomaticAttitudeCommand(player2, player1, 300));
			}

			StringBuilder log = new StringBuilder();
			log.append("Set diplomatic relations between ");
			log.append(player1);
			log.append(" and ");
			log.append(player2);
			log.append(" to \"");
			log.append(getDiplomaticStatusName(status));
			log.append("\".");
			logger.info(log);
			putParameter("player1", player1);
			putParameter("player2", player2);
			putParameter("old_status", oldStatus);
			putParameter("old_status_name", getDiplomaticStatusName(oldStatus));
			putParameter("new_status", status);
			putParameter("new_status_name", getDiplomaticStatusName(status));
			setState(SUCCEEDED);
		}

	}

	private String getDiplomaticStatusName(int status) {
		switch (status) {
		case PlayerRelation.NO_CONTACT:
			return "No contact";
		case PlayerRelation.AT_PEACE:
			return "At peace";
		case PlayerRelation.AT_WAR:
			return "At war";
		case PlayerRelation.ALLIED:
			return "Allied";
		default:
			return "No contact";
		}

	}

}

package org.freerealm.command;

import org.freerealm.player.Player;
import org.freerealm.player.mission.Mission;

/**
 * 
 * @author Deniz ARIKAN
 */
public class AssignMissionToPlayerCommand extends FreeRealmAbstractCommand {

	private final Player player;
	private final Mission mission;

	/**
	 * Constructs an AddMissionToPlayerCommand using player and mission.
	 * 
	 * @param player
	 *            New player to add to realm
	 * @param mission
	 *            Mission to assign to player
	 */
	public AssignMissionToPlayerCommand(Player player, Mission mission) {
		this.player = player;
		this.mission = mission;
	}

	/**
	 * Executes command and adds the mission to player.
	 * 
	 * @return CommandResult
	 */
	public void execute() {
		mission.setPlayer(player);
		player.addMission(mission);
		putParameter("player", player);
		putParameter("mission", mission);
		setState(SUCCEEDED);
	}
}

package org.freerealm.command;

import org.freerealm.player.Player;
import org.freerealm.player.mission.Mission;

/**
 * 
 * @author Deniz ARIKAN
 */
public class RemoveMissionFromPlayerCommand extends FreeRealmAbstractCommand {

	private final Player player;
	private final Mission mission;

	/**
	 * Constructs a RemoveMissionFromPlayerCommand using player and mission.
	 * 
	 * @param player
	 *            Player to remove mission
	 * @param mission
	 *            Mission to remove from player
	 */
	public RemoveMissionFromPlayerCommand(Player player, Mission mission) {
		this.player = player;
		this.mission = mission;
	}

	/**
	 * Executes command and removes the mission from player.
	 * 
	 * @param realm
	 *            Realm to execute the command
	 * @return CommandResult
	 */
	public void execute() {
		player.removeMission(mission);
		putParameter("player", player);
		putParameter("mission", mission);
		setState(SUCCEEDED);
	}
}

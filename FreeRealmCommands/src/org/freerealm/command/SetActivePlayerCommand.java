package org.freerealm.command;

import org.freerealm.Realm;
import org.freerealm.player.Player;

/**
 * Command class to activate a player. If activated player does not have an
 * active unit, then first unit in player manager will be activated.
 * 
 * @author Deniz ARIKAN
 */
public class SetActivePlayerCommand extends FreeRealmAbstractCommand {

	private final Player player;

	/**
	 * Constructs an SetActivePlayerCommand using player.
	 * 
	 * @param player
	 *            Player that will be activated
	 */
	public SetActivePlayerCommand(Realm realm, Player player) {
		super(realm);
		this.player = player;
	}

	/**
	 * Executes command to make given player active.
	 * 
	 * @param realm
	 *            Realm to execute the command
	 * @return CommandResult
	 */
	public void execute() {
		getRealm().getPlayerManager().setActivePlayer(player);
		setState(SUCCEEDED);
	}
}

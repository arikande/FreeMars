package org.freerealm.command;

import java.util.Iterator;

import org.apache.log4j.Logger;
import org.freerealm.Realm;
import org.freerealm.diplomacy.Diplomacy;
import org.freerealm.player.Player;
import org.freerealm.property.ModifyStartingWealth;

/**
 * Command class to add a new player to realm.
 * 
 * @author Deniz ARIKAN
 */
public class AddPlayerCommand extends FreeRealmAbstractCommand {

	private static final Logger logger = Logger.getLogger(AddPlayerCommand.class);
	private final Player player;

	/**
	 * Constructs an AddPlayerCommand using player.
	 * 
	 * @param realm
	 *            Realm to add player.
	 * @param player
	 *            New player to add to realm.
	 */
	public AddPlayerCommand(Realm realm, Player player) {
		super(realm);
		this.player = player;
	}

	/**
	 * Executes command and adds the given player to realm.
	 * 
	 * @return CommandResult
	 */
	public void execute() {
		int newPlayerId = player.getId();
		logger.info("Adding player " + player.getName() + " with id " + newPlayerId + ".");
		if (getRealm().getPlayerManager().getPlayer(newPlayerId) != null) {
			setText("A player with given id already exists");
			setState(FAILED);
			return;
		}
		getRealm().getPlayerManager().addPlayer(player);
		ModifyStartingWealth modifyStartingWealth = (ModifyStartingWealth) player.getProperty("ModifyStartingWealth");
		if (modifyStartingWealth != null) {
			player.setWealth(player.getWealth() + modifyStartingWealth.getModifier());
		}
		player.setDiplomacy(new Diplomacy());
		Iterator<Player> playerIterator = getRealm().getPlayerManager().getPlayersIterator();
		while (playerIterator.hasNext()) {
			Player otherPlayer = playerIterator.next();
			if (!player.equals(otherPlayer)) {
				AddPlayerRelationCommand addPlayerRelationCommand = new AddPlayerRelationCommand(player, otherPlayer);
				getExecutor().execute(addPlayerRelationCommand);
			}
		}
		logger.info("Player " + player.getName() + " with id " + newPlayerId + " successfully added to realm.");
		setState(SUCCEEDED);
	}

}

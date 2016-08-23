package org.freerealm.command;

import org.freerealm.Realm;
import org.freerealm.history.FreeRealmPlayerHistory;
import org.freerealm.history.PlayerHistory;
import org.freerealm.history.PlayerTurnHistory;
import org.freerealm.player.Player;

/**
 * 
 * @author Deniz ARIKAN
 */
public class AddPlayerTurnHistoryCommand extends FreeRealmAbstractCommand {

	private final Player player;
	private final PlayerTurnHistory playerTurnHistory;

	public AddPlayerTurnHistoryCommand(Realm realm, Player player, PlayerTurnHistory playerTurnHistory) {
		super(realm);
		this.player = player;
		this.playerTurnHistory = playerTurnHistory;
	}

	public void execute() {
		if (getRealm().getHistory().getPlayerHistory(player) == null) {
			PlayerHistory playerHistory = new FreeRealmPlayerHistory();
			playerHistory.setPlayer(player);
			getRealm().getHistory().addPlayerHistory(player, playerHistory);
		}
		getRealm().getHistory().addPlayerTurnHistory(player, playerTurnHistory);
		setState(SUCCEEDED);
	}

}

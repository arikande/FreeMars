package org.freerealm.command;

import java.util.Iterator;

import org.freerealm.Realm;
import org.freerealm.player.Player;

/**
 * 
 * @author Deniz ARIKAN
 */
public class EndCurrentTurnCommand extends FreeRealmAbstractCommand {

	public EndCurrentTurnCommand(Realm realm) {
		super(realm);
	}

	public void execute() {
		Iterator<Player> playerIterator = getRealm().getPlayerManager().getPlayersIterator();
		while (playerIterator.hasNext()) {
			getExecutor().execute(new EndPlayerTurnCommand(getRealm(), playerIterator.next()));
		}
		setState(SUCCEEDED);
	}
}

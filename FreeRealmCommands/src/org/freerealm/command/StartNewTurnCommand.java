package org.freerealm.command;

import java.util.Iterator;

import org.freerealm.Realm;
import org.freerealm.player.Player;

/**
 * Command class to start a new turn in realm, it increases number of turns by
 * one and sets first player active.
 * 
 * @author Deniz ARIKAN
 */
public class StartNewTurnCommand extends FreeRealmAbstractCommand {

	public StartNewTurnCommand(Realm realm) {
		super(realm);
	}

	/**
	 * Executes command to start new turn.
	 * 
	 * @return CommandResult
	 */
	public void execute() {
		getRealm().setNumberOfTurns(getRealm().getNumberOfTurns() + 1);
		Iterator<Player> playerIterator = getRealm().getPlayerManager().getPlayersIterator();
		while (playerIterator.hasNext()) {
			getExecutor().execute(new StartPlayerTurnCommand(getRealm(), playerIterator.next()));
		}
		Player firstPlayer = getRealm().getPlayerManager().getFirstPlayer();
		getExecutor().execute(new SetActivePlayerCommand(getRealm(), firstPlayer));
		setState(SUCCEEDED);
	}
}

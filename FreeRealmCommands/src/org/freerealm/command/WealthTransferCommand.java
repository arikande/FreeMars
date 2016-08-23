package org.freerealm.command;

import org.freerealm.player.Player;

/**
 * Command class to remove given amount of wealth from a player.
 * 
 * @author Deniz ARIKAN
 */
public class WealthTransferCommand extends FreeRealmAbstractCommand {

	private final Player fromPlayer;
	private final Player toPlayer;
	private int amount = 0;

	/**
	 * Constructs a WealthTransferCommand using fromPlayer, toPlayer, amount
	 * 
	 * @param fromPlayer
	 *            Player to remove wealth
	 * @param toPlayer
	 *            Player to add wealth
	 * @param amount
	 *            Amount to transfer
	 */
	public WealthTransferCommand(Player fromPlayer, Player toPlayer, int amount) {
		this.fromPlayer = fromPlayer;
		this.toPlayer = toPlayer;
		this.amount = amount;
	}

	/**
	 * Executes command to remove given amount of wealth from player
	 * 
	 * @return CommandResult
	 */
	public void execute() {
		WealthRemoveCommand wealthRemoveCommand = new WealthRemoveCommand(fromPlayer, amount);
		getExecutor().execute(wealthRemoveCommand);
		if (wealthRemoveCommand.getState() == SUCCEEDED) {
			getExecutor().execute(new WealthAddCommand(toPlayer, amount));
			setState(SUCCEEDED);
		} else {
			setState(FAILED);
		}
	}
}

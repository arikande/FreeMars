package org.freerealm.command;

import org.freerealm.player.Player;

/**
 * Command class to set tax rate of a given player. If <tt>taxRate</tt> is
 * greater than 100 it will be set to 100 or if <tt>taxRate</tt> is lesser than
 * 0 it will be set to 0.
 * 
 * @author Deniz ARIKAN
 */
public class SetTaxRateCommand extends FreeRealmAbstractCommand {

	private static final int MINIMUM_TAX_RATE = 0;
	private static final int MAXIMUM_TAX_RATE = 100;
	private final Player player;
	private int taxRate = 0;

	/**
	 * Constructs a SetTaxRateCommand using player, taxRate
	 * 
	 * @param player
	 *            Player whose tax rate will be set
	 * @param taxRate
	 *            New taxRate value
	 */
	public SetTaxRateCommand(Player player, int taxRate) {
		this.player = player;
		this.taxRate = taxRate;
	}

	/**
	 * Executes command to set new tax rate to given player.
	 * 
	 * @param realm
	 *            Realm to execute the command
	 * @return CommandResult
	 */
	public void execute() {
		if (taxRate > MAXIMUM_TAX_RATE) {
			taxRate = MAXIMUM_TAX_RATE;
		}
		if (taxRate < MINIMUM_TAX_RATE) {
			taxRate = MINIMUM_TAX_RATE;
		}
		player.setTaxRate(taxRate);
		setState(SUCCEEDED);
	}
}

package org.freerealm.command;

import org.freerealm.settlement.Settlement;

/**
 * Command class to rename a settlement. Command will return an error if new
 * name is null or empty.
 * 
 * @author Deniz ARIKAN
 */
public class RenameSettlementCommand extends FreeRealmAbstractCommand {

	private final Settlement settlement;
	private final String name;

	/**
	 * Constructs a RenameCityCommand using settlement, name.
	 * 
	 * @param settlement
	 *            Settlement to rename
	 * @param name
	 *            New name for the settlement
	 */
	public RenameSettlementCommand(Settlement settlement, String name) {
		this.settlement = settlement;
		this.name = name;
	}

	/**
	 * Executes command to rename given settlement.
	 * 
	 * @return CommandResult
	 */
	public void execute() {
		if ((name == null) || (name.trim().equals(""))) {
			setText("Settlement name cannot be empty");
			setState(FAILED);
			return;
		}
		settlement.setName(name);
		setState(SUCCEEDED);
	}
}

package org.freerealm.command;

import org.freerealm.map.Coordinate;
import org.freerealm.settlement.Settlement;

/**
 * Command class to remove workforce from a coordinate for a given settlement.
 * 
 * @author Deniz ARIKAN
 */
public class WorkforceRemoveCommand extends FreeRealmAbstractCommand {

	private final Settlement settlement;
	private final Coordinate coordinate;

	/**
	 * Constructs an WorkforceRemoveCommand using settlement, coordinate.
	 * 
	 * @param settlement
	 *            Settlement in which the workforce will be removed
	 * @param coordinate
	 *            Target coordinate
	 */
	public WorkforceRemoveCommand(Settlement settlement, Coordinate coordinate) {
		this.settlement = settlement;
		this.coordinate = coordinate;
	}

	/**
	 * Executes command to remove a new workforce from given coordinate for the
	 * settlement.
	 * 
	 * @param realm
	 *            Realm to execute the command
	 * @return CommandResult
	 */
	public void execute() {
		settlement.getWorkForceManager().removeWorkForce(coordinate);
		setState(SUCCEEDED);
	}
}

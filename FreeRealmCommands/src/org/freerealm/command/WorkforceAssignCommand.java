package org.freerealm.command;

import org.freerealm.Realm;
import org.freerealm.Utility;
import org.freerealm.map.Coordinate;
import org.freerealm.settlement.Settlement;
import org.freerealm.settlement.workforce.WorkForce;

/**
 * Command class to assign workforce to a coordinate for a given settlement.
 * <p>
 * Upon execution this command will return an error if:
 * <ul>
 * <li>Given coordinate already has a workforce from another settlement and is
 * not available for workforce assignment.</li>
 * <li>Number of workers in workforce exceeds settlement's number of maximum
 * workers per tile.</li>
 * <li>City does not have enough free workers for the new workforce.</li>
 * </ul>
 * <p>
 * If no error is fired after checking these conditions WorkforceAssignCommand
 * will add given workforce to settlement's workforce manager. Even if number of
 * workers in given workforce is 0 command will add a new workforce.
 * 
 * @author Deniz ARIKAN
 */
public class WorkforceAssignCommand extends FreeRealmAbstractCommand {

	private final Settlement settlement;
	private final WorkForce workforce;
	private final Coordinate coordinate;

	/**
	 * Constructs an WorkforceAssignCommand using settlement, workforce,
	 * coordinate.
	 * 
	 * @param settlement
	 *            Settlement in which the workforce will be assigned
	 * @param workforce
	 *            New workforce which will be assigned to coordinate
	 * @param coordinate
	 *            Target coordinate
	 */
	public WorkforceAssignCommand(Realm realm, Settlement settlement, WorkForce workforce, Coordinate coordinate) {
		super(realm);
		this.settlement = settlement;
		this.workforce = workforce;
		this.coordinate = coordinate;
	}

	/**
	 * Executes command to add a new workforce to given coordinate for the
	 * settlement.
	 * 
	 * @param realm
	 *            Realm to execute the command
	 * @return CommandResult
	 */
	public void execute() {
		if (!Utility.isTileAvailableForSettlement(getRealm(), settlement, coordinate)) {
			setText("Tile is not available for settlement");
			setState(FAILED);
			return;
		}
		if (workforce.getNumberOfWorkers() > settlement.getMaxWorkersPerTile()) {
			setText("Number of workers can not exceed " + settlement.getMaxWorkersPerTile());
			setState(FAILED);
			return;
		}
		int numberOfWorkersForTile = 0;
		if (settlement.getWorkForceManager().getAssignedWorkforceForTile(coordinate) != null) {
			numberOfWorkersForTile = settlement.getWorkForceManager().getAssignedWorkforceForTile(coordinate).getNumberOfWorkers();
		}
		if (workforce.getNumberOfWorkers() - numberOfWorkersForTile <= settlement.getProductionWorkforce()) {
			settlement.getWorkForceManager().addWorkForce(coordinate, workforce);
			setState(SUCCEEDED);
		} else {
			setText("Not enough free workforce");
			setState(FAILED);
			return;
		}
	}
}

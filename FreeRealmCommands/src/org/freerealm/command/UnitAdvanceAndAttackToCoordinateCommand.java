package org.freerealm.command;

import org.commandexecutor.Command;
import org.freerealm.Realm;
import org.freerealm.map.Coordinate;
import org.freerealm.map.Path;
import org.freerealm.map.PathFinder;
import org.freerealm.settlement.Settlement;
import org.freerealm.tile.Tile;
import org.freerealm.unit.Unit;

/**
 * Command class to advance a unit towards a given coordinate, unit will attack
 * other units on path tiles and invade settlements if needed.
 * UnitAdvanceAndAttackToCoordinateCommand will get pathfinder for realm, find a
 * path from unit's current coordinate to target coordinate and move unit
 * towards it as long as it has enough movement points. If given unit is already
 * at the target coordinate, this command will immediately return RESULT_OK.
 * <p>
 * Upon execution this command will return an error if:
 * <ul>
 * <li>Pathfinder for realm is null.</li>
 * <li>There is not any path from unit's current location to target tile.
 * <li>One of the MoveCommands returns error.</li>
 * </ul>
 * 
 * @author Deniz ARIKAN
 */
public class UnitAdvanceAndAttackToCoordinateCommand extends FreeRealmAbstractCommand {

	private final Unit unit;
	private final Coordinate coordinate;

	/**
	 * Constructs a UnitAdvanceAndAttackToCoordinateCommand using unit and
	 * coordinate.
	 * 
	 * @param unit
	 *            Unit to advance to coordinate, can not be null
	 * @param coordinate
	 *            Unit will be moved towards given coordinate
	 */
	public UnitAdvanceAndAttackToCoordinateCommand(Realm realm, Unit unit, Coordinate coordinate) {
		super(realm);
		this.unit = unit;
		this.coordinate = coordinate;
	}

	/**
	 * Executes command to advance given unit towards target tile.
	 * 
	 * @return CommandResult
	 */
	public void execute() {
		if (unit.getCoordinate().equals(coordinate)) {
			setState(SUCCEEDED);
			return;
		}
		PathFinder pathFinder = getRealm().getPathFinder();
		if (pathFinder == null) {
			setText("PathFinder for realm is null.");
			setState(FAILED);
		}
		Path path = pathFinder.findPath(unit, coordinate);
		if (path == null) {
			String errorMessage = "There is not any path from unit's current location to target tile\n";
			errorMessage = errorMessage + "Units location : " + unit.getCoordinate();
			errorMessage = errorMessage + " Target coordinate :" + coordinate;
			setText(errorMessage);
			setState(FAILED);
		}
		int i = 0;
		while ((unit.getMovementPoints() > 0) && (i < path.getLength())) {
			Coordinate pathCoordinate = path.getStep(i);
			Tile tile = getRealm().getTile(pathCoordinate);
			Command command;
			if (tile.getNumberOfUnits() > 0) {
				Unit tileUnit = tile.getFirstUnit();
				if (!unit.getPlayer().equals(tileUnit.getPlayer())) {
					command = new AttackTileCommand(getRealm(), unit, tile);
				} else {
					command = new MoveUnitCommand(getRealm(), unit, pathCoordinate);
				}
			} else {
				if (tile.getSettlement() != null) {
					Settlement settlement = tile.getSettlement();
					if (!unit.getPlayer().equals(settlement.getPlayer())) {
						command = new CaptureSettlementCommand(getRealm(), unit, settlement);
					} else {
						command = new MoveUnitCommand(getRealm(), unit, pathCoordinate);
					}
				} else {
					command = new MoveUnitCommand(getRealm(), unit, pathCoordinate);
				}
			}
			getExecutor().execute(command);
			i++;
			if (command.getState() == FAILED) {
				setText("A command returned an error. Error : " + command.getText());
				return;
			}
		}
		setState(SUCCEEDED);
	}

}

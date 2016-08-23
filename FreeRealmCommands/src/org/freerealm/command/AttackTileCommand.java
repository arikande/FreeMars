package org.freerealm.command;

import org.freerealm.Realm;
import org.freerealm.tile.Tile;
import org.freerealm.unit.Unit;

/**
 * Command class to execute an attack to a tile by the given unit.
 * <p>
 * Upon execution this command will return an error if:
 * <ul>
 * <li>Unit does not have "Fight" property.</li>
 * <li>Target tile does not have any hostile units.</li>
 * <li>Unit's movement points is 0. (Change ?)</li>
 * </ul>
 * If above conditions are satisfied and AttackTileCommand is executed, it will
 * find first hostile unit(?) at given tile and execute an AttackUnitCommand to
 * it.
 * 
 * @author Deniz ARIKAN
 */
public class AttackTileCommand extends FreeRealmAbstractCommand {

	private final Unit unit;
	private final Tile tile;

	/**
	 * Constructs an AttackTileCommand using unit, tile.
	 * 
	 * @param unit
	 *            Unit making the attack
	 * @param tile
	 *            Tile being attacked
	 */
	public AttackTileCommand(Realm realm, Unit unit, Tile tile) {
		super(realm);
		this.unit = unit;
		this.tile = tile;
	}

	/**
	 * Executes command to make unit attack given tile.
	 * 
	 * @return CommandResult
	 */
	public void execute() {
		if (unit.getType().getProperty("Fight") == null) {
			setText("No attack ability");
			setState(FAILED);
			return;
		}
		if (unit.getMovementPoints() <= 0) {
			setText("Unit can not move");
			setState(FAILED);
			return;
		}
		if (tile.getNumberOfUnits() > 0) {
			Unit defendingUnit = tile.getFirstUnit();
			if (!unit.getPlayer().equals(defendingUnit.getPlayer())) {
				Tile defenceTile = getRealm().getTile(defendingUnit.getCoordinate());
				int defencePercentage = defenceTile.getType().getDefencePercentage();
				if ((tile.getSettlement() != null) && tile.getSettlement().getDefenceModifier() > 0) {
					defencePercentage = (defencePercentage * tile.getSettlement().getDefenceModifier()) / 100;
				}
				getExecutor().execute(new AttackUnitCommand(getRealm(), unit, defendingUnit));
			}
		}
		setState(SUCCEEDED);
	}

}

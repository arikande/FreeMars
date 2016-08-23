package org.freerealm.executor.order;

import org.freerealm.Realm;
import org.freerealm.command.SetTileVegetationCommand;
import org.freerealm.command.UnitSetMovementPointsCommand;
import org.freerealm.vegetation.Vegetation;

/**
 * 
 * @author Deniz ARIKAN
 */
public class ClearVegetationOrder extends AbstractOrder {

	private static final String NAME = "ClearVegetationOrder";

	public ClearVegetationOrder(Realm realm) {
		super(realm);
		setSymbol("C");
	}

	@Override
	public boolean isExecutable() {
		if (getUnit().getMovementPoints() == 0) {
			return false;
		}
		return true;
	}

	@Override
	public void execute() {
		Vegetation vegetation = getRealm().getTile(getUnit().getCoordinate()).getVegetation();
		if (vegetation != null) {
			int turnsSpent = getRealm().getNumberOfTurns() - getTurnGiven();
			if (turnsSpent >= vegetation.getType().getTurnsNeededToClear()) {
				SetTileVegetationCommand setTileVegetationCommand = new SetTileVegetationCommand(getRealm(), getUnit().getCoordinate(), null);
				getExecutor().execute(setTileVegetationCommand);
				getUnit().getPlayer().setClearedVegetationCount(getUnit().getPlayer().getClearedVegetationCount() + 1);
				setComplete(true);
			} else {
				getExecutor().execute(new UnitSetMovementPointsCommand(getUnit(), 0));
			}
		} else {
			setComplete(true);
		}
	}

	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public int getRemainingTurns() {
		Vegetation vegetation = getRealm().getTile(getUnit().getCoordinate()).getVegetation();
		if (vegetation != null) {
			int turnsSpent = getRealm().getNumberOfTurns() - getTurnGiven();
			return vegetation.getType().getTurnsNeededToClear() - turnsSpent;
		}
		return -1;
	}
}

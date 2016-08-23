package org.freerealm.executor.order;

import org.freerealm.Realm;
import org.freerealm.command.RemoveTileImprovementCommand;
import org.freerealm.command.UnitSetMovementPointsCommand;
import org.freerealm.property.DestroyTileImprovementProperty;
import org.freerealm.tile.Tile;
import org.freerealm.tile.improvement.TileImprovementType;

/**
 * 
 * @author Deniz ARIKAN
 */
public class DestroyTileImprovement extends AbstractOrder {

	private TileImprovementType tileImprovementType;
	private static final String NAME = "DestroyTileImprovement";

	public DestroyTileImprovement(Realm realm) {
		super(realm);
		setSymbol("D");
	}

	@Override
	public boolean isExecutable() {
		return getUnit().getMovementPoints() != 0;
	}

	@Override
	public void execute() {
		DestroyTileImprovementProperty destroyTileImprovementProperty = (DestroyTileImprovementProperty) getUnit().getType().getProperty("DestroyTileImprovementProperty");
		if (destroyTileImprovementProperty != null) {
			Tile tile = getRealm().getTile(getUnit().getCoordinate());
			int turnsNeeded = 1;
			int turnsSpent = getRealm().getNumberOfTurns() - getTurnGiven();
			if (turnsSpent >= turnsNeeded) {
				RemoveTileImprovementCommand removeTileImprovementCommand = new RemoveTileImprovementCommand(tile, tileImprovementType);
				getExecutor().execute(removeTileImprovementCommand);
				setComplete(true);
			} else {
				getExecutor().execute(new UnitSetMovementPointsCommand(getUnit(), 0));
			}
		}
	}

	@Override
	public String getName() {
		return NAME;
	}

	public TileImprovementType getTileImprovementType() {
		return tileImprovementType;
	}

	public void setTileImprovementType(TileImprovementType tileImprovementType) {
		setSymbol("D" + String.valueOf(tileImprovementType.getSymbol()));
		this.tileImprovementType = tileImprovementType;
	}

	@Override
	public int getRemainingTurns() {
		DestroyTileImprovementProperty destroyTileImprovementProperty = (DestroyTileImprovementProperty) getUnit().getType().getProperty("DestroyTileImprovementProperty");
		if (destroyTileImprovementProperty != null) {
			int turnsNeeded = 1;
			int turnsSpent = getRealm().getNumberOfTurns() - getTurnGiven();
			return turnsNeeded - turnsSpent;
		}
		return -1;
	}
}

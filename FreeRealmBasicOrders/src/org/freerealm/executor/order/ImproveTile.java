package org.freerealm.executor.order;

import org.freerealm.Realm;
import org.freerealm.command.AddTileImprovementCommand;
import org.freerealm.command.UnitSetMovementPointsCommand;
import org.freerealm.executor.order.util.BuildTileImprovementProductionPointsCalculator;
import org.freerealm.property.BuildTileImprovementProperty;
import org.freerealm.tile.Tile;
import org.freerealm.tile.improvement.BuildTileImprovementChecker;
import org.freerealm.tile.improvement.TileImprovementType;

/**
 * 
 * @author Deniz ARIKAN
 */
public class ImproveTile extends AbstractOrder {

	private TileImprovementType tileImprovementType;
	private static final String NAME = "ImproveTile";

	public ImproveTile(Realm realm) {
		super(realm);
		setSymbol("B");
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
		BuildTileImprovementProperty buildTileImprovement = (BuildTileImprovementProperty) getUnit().getType().getProperty(BuildTileImprovementProperty.NAME);
		if (buildTileImprovement != null) {
			Tile tile = getRealm().getTile(getUnit().getCoordinate());
			if (new BuildTileImprovementChecker().canBuildTileImprovement(buildTileImprovement, tileImprovementType, tile)) {
				int productionCost = tileImprovementType.getProductionCost();
				int productionPoints = new BuildTileImprovementProductionPointsCalculator(buildTileImprovement, getUnit()).getProductionPoints();
				int turnsNeeded = (productionCost / productionPoints);
				int turnsSpent = getRealm().getNumberOfTurns() - getTurnGiven();
				if (turnsSpent >= turnsNeeded) {
					AddTileImprovementCommand addTileImprovementCommand = new AddTileImprovementCommand(tile, tileImprovementType);
					getExecutor().execute(addTileImprovementCommand);
					getUnit().getPlayer().setBuiltTileImprovementCount(tileImprovementType.getId(), getUnit().getPlayer().getBuiltTileImprovementCount(tileImprovementType.getId()) + 1);
					setComplete(true);
				} else {
					getExecutor().execute(new UnitSetMovementPointsCommand(getUnit(), 0));
				}
			} else {
				setComplete(true);
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
		this.tileImprovementType = tileImprovementType;
		setSymbol(String.valueOf(tileImprovementType.getSymbol()));
	}

	@Override
	public int getRemainingTurns() {
		BuildTileImprovementProperty buildTileImprovement = (BuildTileImprovementProperty) getUnit().getType().getProperty(BuildTileImprovementProperty.NAME);
		if (buildTileImprovement != null) {
			int productionCost = tileImprovementType.getProductionCost();
			int productionPoints = new BuildTileImprovementProductionPointsCalculator(buildTileImprovement, getUnit()).getProductionPoints();
			int turnsNeeded = (productionCost / productionPoints);
			int turnsSpent = getRealm().getNumberOfTurns() - getTurnGiven();
			return turnsNeeded - turnsSpent;
		}
		return -1;
	}
}

package org.freerealm.executor.order;

import java.util.ArrayList;
import java.util.Iterator;

import org.freerealm.Realm;
import org.freerealm.command.RemoveTileImprovementCommand;
import org.freerealm.command.SetTileBonusResourceCommand;
import org.freerealm.command.SetTileTypeCommand;
import org.freerealm.command.SetTileVegetationCommand;
import org.freerealm.command.UnitSetMovementPointsCommand;
import org.freerealm.property.ChangeTileTypeProperty;
import org.freerealm.tile.Tile;
import org.freerealm.tile.TileType;
import org.freerealm.tile.improvement.TileImprovementType;

/**
 * 
 * @author Deniz ARIKAN
 */
public class TransformTileOrder extends AbstractOrder {

	private static final String NAME = "TransformTileOrder";
	private TileType tileType;

	public TransformTileOrder(Realm realm) {
		super(realm);
		setSymbol("T");
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
		if (isValid()) {
			if (getRemainingTurns() == 0) {
				getExecutor().execute(new SetTileTypeCommand(getRealm(), getUnit().getCoordinate(), tileType));
				getExecutor().execute(new SetTileVegetationCommand(getRealm(), getUnit().getCoordinate(), null));
				getExecutor().execute(new SetTileBonusResourceCommand(getRealm(), getUnit().getCoordinate(), null));
				Iterator<TileImprovementType> iterator = getRealm().getTile(getUnit().getCoordinate()).getImprovementsIterator();
				ArrayList<TileImprovementType> improvementsToRemove = new ArrayList<TileImprovementType>();
				while (iterator.hasNext()) {
					TileImprovementType tileImprovementType = iterator.next();
					if (!tileImprovementType.hasPropertyNamed("not_removed_by_tile_transformation")) {
						improvementsToRemove.add(tileImprovementType);
					}
				}
				for (TileImprovementType tileImprovementType : improvementsToRemove) {
					getExecutor().execute(new RemoveTileImprovementCommand(getRealm().getTile(getUnit().getCoordinate()), tileImprovementType));
				}
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
		ChangeTileTypeProperty changeTileTypeProperty = (ChangeTileTypeProperty) getUnit().getType().getProperty("ChangeTileTypeProperty");
		TileType unitTileType = getRealm().getTile(getUnit().getCoordinate()).getType();
		int productionCost = unitTileType.getTransformationCostToTileType(tileType.getId());
		int productionPoints = changeTileTypeProperty.getProductionPoints();
		int turnsNeeded = (productionCost / productionPoints);
		int turnsSpent = getRealm().getNumberOfTurns() - getTurnGiven();
		return turnsNeeded - turnsSpent;
	}

	public TileType getTileType() {
		return tileType;
	}

	public void setTileType(TileType tileType) {
		this.tileType = tileType;
	}

	private boolean isValid() {
		Tile tile = getRealm().getTile(getUnit().getCoordinate());
		TileType currentTileType = tile.getType();
		TileType transformToTileType = null;
		if (currentTileType.getTransformableTileTypecount() > 0) {
			Iterator<Integer> iterator = currentTileType.getTransformableTileTypeIdsIterator();
			while (iterator.hasNext()) {
				Integer integer = iterator.next();
				transformToTileType = getRealm().getTileTypeManager().getTileType(integer);
			}
		}
		if (tileType != null && tileType.equals(transformToTileType)) {
			return true;
		} else {
			return false;
		}
	}
}

package org.freerealm.command;

import org.freerealm.tile.Tile;
import org.freerealm.tile.improvement.TileImprovementType;

/**
 * Command class to add a tile improvement to given tile. Since some
 * improvements cannot be applied to some tile types this command can return an
 * error if given improvement is not available for tile's type.
 * 
 * @author Deniz ARIKAN
 */
public class AddTileImprovementCommand extends FreeRealmAbstractCommand {

	private final TileImprovementType improvement;
	private final Tile tile;

	/**
	 * Constructs an AddTileImprovementCommand using tile, improvement.
	 * 
	 * @param tile
	 *            Tile to add improvement
	 * @param improvement
	 *            New improvement
	 */
	public AddTileImprovementCommand(Tile tile, TileImprovementType improvement) {
		this.improvement = improvement;
		this.tile = tile;
	}

	/**
	 * Executes command to add a new improvement to tile. If given tile
	 * improvement can not be build on this tile's type command returns an
	 * error.
	 * 
	 * @return CommandResult
	 */
	public void execute() {
		if (improvement.canBeBuiltOnTileType(tile.getType())) {
			tile.addImprovement(improvement);
			putParameter("tile", tile);
			putParameter("improvement", improvement);
			setState(SUCCEEDED);
		}
		setState(FAILED);
	}

}

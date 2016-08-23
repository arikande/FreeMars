package org.freerealm.command;

import org.freerealm.tile.Tile;
import org.freerealm.tile.improvement.TileImprovementType;

/**
 * Command class to remove a tile improvement from a given tile.
 * 
 * @author Deniz ARIKAN
 */
public class RemoveTileImprovementCommand extends FreeRealmAbstractCommand {

	private final TileImprovementType improvement;
	private final Tile tile;

	/**
	 * Constructs an RemoveTileImprovementCommand using tile, improvement.
	 * 
	 * @param tile
	 *            Tile to add improvement
	 * @param improvement
	 *            Improvement to remove
	 */
	public RemoveTileImprovementCommand(Tile tile, TileImprovementType improvement) {
		this.improvement = improvement;
		this.tile = tile;
	}

	/**
	 * Executes command to remove improvement from tile.
	 * 
	 * @return CommandResult
	 */
	public void execute() {
		tile.removeImprovement(improvement);
		setState(SUCCEEDED);
	}

}

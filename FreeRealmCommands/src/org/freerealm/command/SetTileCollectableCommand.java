package org.freerealm.command;

import org.freerealm.tile.Collectable;
import org.freerealm.tile.Tile;

/**
 * @author Deniz ARIKAN
 */
public class SetTileCollectableCommand extends FreeRealmAbstractCommand {

	private final Tile tile;
	private final Collectable collectable;

	public SetTileCollectableCommand(Tile tile, Collectable collectable) {
		this.tile = tile;
		this.collectable = collectable;
	}

	public void execute() {
		if (tile == null) {
			setText("Tile cannot be null");
			setState(FAILED);
			return;
		}
		tile.setCollectable(collectable);
		putParameter("tile", tile);
		putParameter("collectable", collectable);
		setState(SUCCEEDED);
	}
}

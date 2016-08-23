package org.freerealm.command;

import org.commandexecutor.Command;
import org.freerealm.Realm;
import org.freerealm.tile.Collectable;
import org.freerealm.unit.Unit;

/**
 * 
 * @author Deniz ARIKAN
 */
public class ProcessCollectableCommand extends FreeRealmAbstractCommand {

	private final Unit unit;
	private final Collectable collectable;

	public ProcessCollectableCommand(Realm realm, Unit unit, Collectable collectable) {
		super(realm);
		this.unit = unit;
		this.collectable = collectable;
	}

	public void execute() {
		collectable.collected(getRealm(), unit);
		putParameter("unit", unit);
		putParameter("collectable", collectable);
		putParameter("collected_coordinate", unit.getCoordinate());
		setState(Command.SUCCEEDED);
	}

}

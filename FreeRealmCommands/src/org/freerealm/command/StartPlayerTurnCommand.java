package org.freerealm.command;

import java.util.ArrayList;
import java.util.Iterator;

import org.freerealm.Realm;
import org.freerealm.player.Player;
import org.freerealm.unit.Unit;

/**
 * 
 * @author Deniz ARIKAN
 */
public class StartPlayerTurnCommand extends FreeRealmAbstractCommand {

	private final Player player;

	public StartPlayerTurnCommand(Realm realm, Player player) {
		super(realm);
		this.player = player;
	}

	public void execute() {
		manageUnits();
		player.setTurnEnded(false);
		setState(SUCCEEDED);
	}

	/*
	 * Put all units of player in a list to prevent
	 * java.util.ConcurrentModificationException when a unit is removed from
	 * game after execution of a command like BuildSettlement.
	 */
	private void manageUnits() {
		ArrayList<Unit> temporaryUnits = new ArrayList<Unit>();
		Iterator<Unit> unitIterator = player.getUnitsIterator();
		while (unitIterator.hasNext()) {
			temporaryUnits.add(unitIterator.next());
		}
		for (Unit unit : temporaryUnits) {
			manageUnit(unit);
		}
	}

	private void manageUnit(Unit unit) {
		unit.setSkippedForCurrentTurn(false);
		if (unit.getStatus() == Unit.UNIT_ACTIVE) {
			unit.setMovementPoints(unit.getType().getMovementPoints());
		}
		if (unit.getCurrentOrder() != null || unit.getNextOrder() != null) {
			getExecutor().execute(new UnitOrderExecuteCommand(getRealm(), unit));
		}
	}
}

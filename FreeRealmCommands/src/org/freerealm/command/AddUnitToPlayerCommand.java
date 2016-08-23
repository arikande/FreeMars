package org.freerealm.command;

import org.freerealm.Realm;
import org.freerealm.player.Player;
import org.freerealm.unit.Unit;

/**
 * @author Deniz ARIKAN
 */
public class AddUnitToPlayerCommand extends FreeRealmAbstractCommand {

	private final Player player;
	private final Unit unit;

	public AddUnitToPlayerCommand(Realm realm, Player player, Unit unit) {
		super(realm);
		this.player = player;
		this.unit = unit;
	}

	/**
	 * Executes command and removes given unit.
	 * 
	 * @return CommandResult
	 */
	public void execute() {
		if (player == null) {
			setText("Player is null.");
			setState(FAILED);
			return;
		}
		if (unit == null) {
			setText("Unit already belongs to player.");
			setState(FAILED);
			return;
		}
		if (player.hasUnit(unit)) {
			setText("Player is null.");
			setState(FAILED);
			return;
		}
		player.addUnit(unit);
		unit.setPlayer(player);
		if (unit.getStatus() == Unit.UNIT_ACTIVE && unit.getCoordinate() != null) {
			for (int i = 0; i < unit.getType().getExplorationRadius() + 1; i++) {
				getExecutor().execute(new AddExploredCoordinatesToPlayerCommand(unit.getPlayer(), getRealm().getCircleCoordinates(unit.getCoordinate(), i)));
			}
		}
		setState(SUCCEEDED);
	}
}

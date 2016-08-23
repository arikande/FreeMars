package org.freerealm.command;

import org.commandexecutor.Command;
import org.freerealm.Realm;
import org.freerealm.player.Player;
import org.freerealm.settlement.Settlement;
import org.freerealm.unit.Unit;

/**
 * Command class to make a unit capture a settlement that does not belong to
 * unit's player. When executed InvadeCityCommand will remove the settlement
 * from its player, add it to unit's player and set movement points of capturing
 * unit to 0. The command will not move the unit, it assumes that the invading
 * unit is already moved and is at the coordinate of invaded settlement.
 * <p>
 * InvadeCityCommand will return an error if :
 * <ul>
 * <li>Unit does not have "Move" property</li>
 * <li>Unit does not have "Fight" property</li>
 * <li>Unit is not at the coordinate of invaded settlement</li>
 * </ul>
 * 
 * @author Deniz ARIKAN
 */
public class CaptureSettlementCommand extends FreeRealmAbstractCommand {

	private final Unit unit;
	private final Settlement settlement;

	/**
	 * Constructs an InvadeCityCommand using unit, settlement.
	 * 
	 * @param unit
	 *            Capturing unit
	 * @param settlement
	 *            Settlement that is invaded by unit
	 */
	public CaptureSettlementCommand(Realm realm, Unit unit, Settlement settlement) {
		super(realm);
		this.unit = unit;
		this.settlement = settlement;
	}

	/**
	 * Executes command and adds captured settlement to unit's player.
	 * 
	 * @return CommandResult
	 */
	public void execute() {
		Player defendingPlayer = settlement.getPlayer();
		MoveUnitCommand moveUnitCommand = new MoveUnitCommand(getRealm(), unit, settlement.getCoordinate());
		getExecutor().execute(moveUnitCommand);
		if (moveUnitCommand.getState() == Command.SUCCEEDED) {
			defendingPlayer.removeSettlement(settlement);
			unit.getPlayer().addSettlement(settlement);
			settlement.setPlayer(unit.getPlayer());
			getExecutor().execute(new UnitSetMovementPointsCommand(unit, 0));
			putParameter("settlement", settlement);
			putParameter("newOwner", unit.getPlayer());
			putParameter("previousOwner", defendingPlayer);
		} else {
			settlement.setPlayer(defendingPlayer);
		}
		setState(SUCCEEDED);
	}

}

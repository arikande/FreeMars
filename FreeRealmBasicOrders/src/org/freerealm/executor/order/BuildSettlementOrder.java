package org.freerealm.executor.order;

import org.freerealm.Realm;
import org.freerealm.command.BuildSettlementCommand;
import org.freerealm.settlement.Settlement;

/**
 * 
 * @author Deniz ARIKAN
 */
public class BuildSettlementOrder extends AbstractOrder {

	private static final String NAME = "BuildSettlementOrder";
	private Settlement settlement;

	public BuildSettlementOrder(Realm realm) {
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
		String settlementName = getUnit().getPlayer().getNextSettlementName();
		BuildSettlementCommand buildSettlementCommand = new BuildSettlementCommand(getRealm(), getUnit(), settlementName, getSettlement());
		getExecutor().execute(buildSettlementCommand);
		setComplete(true);
	}

	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public int getRemainingTurns() {
		return -1;
	}

	public Settlement getSettlement() {
		return settlement;
	}

	public void setSettlement(Settlement settlement) {
		this.settlement = settlement;
	}
}

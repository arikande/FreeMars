package org.freerealm.executor.order;

import org.freerealm.Realm;
import org.freerealm.command.UnitSetMovementPointsCommand;

/**
 * 
 * @author Deniz ARIKAN
 */
public class Fortify extends AbstractOrder {

	private static final String NAME = "Fortify";

	public Fortify(Realm realm) {
		super(realm);
		setSymbol("F");
	}

	@Override
	public boolean isExecutable() {
		return true;
	}

	@Override
	public void execute() {
		getExecutor().execute(new UnitSetMovementPointsCommand(getUnit(), 0));
	}

	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public int getRemainingTurns() {
		return -1;
	}
}

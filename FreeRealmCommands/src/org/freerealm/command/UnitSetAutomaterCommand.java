package org.freerealm.command;

import org.freerealm.unit.Unit;
import org.freerealm.unit.UnitAutomater;

/**
 * @author Deniz ARIKAN
 */
public class UnitSetAutomaterCommand extends FreeRealmAbstractCommand {

	private final Unit unit;
	private final UnitAutomater unitAutomater;

	public UnitSetAutomaterCommand(Unit unit, UnitAutomater unitAutomater) {
		this.unit = unit;
		this.unitAutomater = unitAutomater;
	}

	public void execute() {
		getExecutor().execute(new UnitOrdersClearCommand(unit));
		if (unit != null) {
			unit.setAutomater(unitAutomater);
			if (unitAutomater != null) {
				unitAutomater.setUnit(unit);
				unitAutomater.automate();
			}
			setState(SUCCEEDED);
		} else {
			setText("Unit can not be null");
			setState(FAILED);
		}
	}

}

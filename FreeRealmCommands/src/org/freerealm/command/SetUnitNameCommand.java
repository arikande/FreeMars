package org.freerealm.command;

import org.freerealm.unit.Unit;

/**
 * 
 * @author Deniz ARIKAN
 */
public class SetUnitNameCommand extends FreeRealmAbstractCommand {

	private final Unit unit;
	private String name;

	public SetUnitNameCommand(Unit unit, String name) {
		this.unit = unit;
		this.name = name;
	}

	public void execute() {
		if (unit == null) {
			setText("Unit cannot be null");
			setState(FAILED);
			return;
		}
		if (name == null) {
			setText("Name cannot be null");
			setState(FAILED);
			return;
		}
		name = name.trim();
		if (name.equals("")) {
			setText("Name cannot be empty");
			setState(FAILED);
			return;
		}
		unit.setName(name);
		setState(SUCCEEDED);
	}
}

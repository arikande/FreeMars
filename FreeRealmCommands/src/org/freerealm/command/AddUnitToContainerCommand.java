package org.freerealm.command;

import org.freerealm.unit.Unit;
import org.freerealm.unit.UnitContainer;

/**
 * 
 * @author Deniz ARIKAN
 */
public class AddUnitToContainerCommand extends FreeRealmAbstractCommand {

	private final UnitContainer unitContainer;
	private final Unit unit;

	/**
	 * Constructs an AddUnitToContainerCommand using unitContainer, unit.
	 * 
	 * @param unitContainer
	 *            Unit container to hold unit
	 * @param unit
	 *            Unit to add to container
	 */
	public AddUnitToContainerCommand(UnitContainer unitContainer, Unit unit) {
		this.unitContainer = unitContainer;
		this.unit = unit;
	}

	/**
	 * Executes command and adds the given unit to container.
	 * 
	 * @return CommandResult
	 */
	public void execute() {
		if (unitContainer.getTotalCapacity() >= (unit.getType().getWeightForContainer() + unitContainer.getTotalContainedWeight())) {
			unit.setStatus(Unit.UNIT_SUSPENDED);
			unitContainer.addUnit(unit.getId());
			setState(SUCCEEDED);
		} else {
			StringBuilder resultText = new StringBuilder();
			resultText.append("Not enough capacity on \"");
			resultText.append(unitContainer.getName());
			resultText.append("\" for \"");
			resultText.append(unit.getName());
			resultText.append("\".");
			setText(resultText.toString());
			setState(FAILED);
		}
	}
}

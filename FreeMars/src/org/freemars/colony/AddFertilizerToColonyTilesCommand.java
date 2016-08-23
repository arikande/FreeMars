package org.freemars.colony;

import java.util.Iterator;

import org.freerealm.Realm;
import org.freerealm.command.FreeRealmAbstractCommand;
import org.freerealm.resource.Resource;
import org.freerealm.settlement.workforce.WorkForce;

/**
 * 
 * @author Deniz ARIKAN
 */
public class AddFertilizerToColonyTilesCommand extends FreeRealmAbstractCommand {

	private final FreeMarsColony freeMarsColony;

	public AddFertilizerToColonyTilesCommand(Realm realm, FreeMarsColony freeMarsColony) {
		super(realm);
		this.freeMarsColony = freeMarsColony;
	}

	public void execute() {
		boolean fertilizerQuantityEnough = true;
		Resource foodResource = getRealm().getResourceManager().getResource(Resource.FOOD);
		Iterator<WorkForce> iterator = freeMarsColony.getWorkForceManager().getWorkForceIterator();
		while (iterator.hasNext() && fertilizerQuantityEnough) {
			WorkForce workForce = iterator.next();
			if (workForce.getResource().equals(foodResource)) {
				CommandResult commandResult = getExecutor().execute(new AddFertilizerToWorkforceTileCommand(getRealm(), freeMarsColony, workForce));
				if (commandResult.getCode() == CommandResult.RESULT_ERROR) {
					fertilizerQuantityEnough = false;
				}
			}
		}
		if (fertilizerQuantityEnough) {
			setState(SUCCEEDED);
			return;
		} else {
			setState(FAILED);
			return;
		}
	}
}

package org.freerealm.executor.order;

import java.util.Iterator;

import org.freerealm.Realm;
import org.freerealm.command.TransferResourceCommand;
import org.freerealm.property.ContainerProperty;
import org.freerealm.resource.Resource;
import org.freerealm.settlement.Settlement;

/**
 * 
 * @author Deniz ARIKAN
 */
public class UnloadAllCargoOrder extends AbstractOrder {

	private static final String NAME = "UnloadAllCargoOrder";

	public UnloadAllCargoOrder(Realm realm) {
		super(realm);
		setSymbol("U");
	}

	@Override
	public boolean isExecutable() {
		return true;
	}

	@Override
	public void execute() {
		Settlement settlement = getRealm().getTile(getUnit().getCoordinate()).getSettlement();
		if (settlement != null) {
			ContainerProperty containerProperty = (ContainerProperty) getUnit().getType().getProperty("ContainerProperty");
			if (containerProperty != null) {
				Iterator<Resource> iterator = getUnit().getContainedResourcesIterator();
				while (iterator.hasNext()) {
					Resource resource = iterator.next();
					int quantity = getUnit().getResourceQuantity(resource);
					getExecutor().execute(new TransferResourceCommand(getUnit(), settlement, resource, quantity));
				}
			}
		}
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
}

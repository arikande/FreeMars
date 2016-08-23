package org.freerealm.command;

import org.freerealm.resource.Resource;
import org.freerealm.settlement.Settlement;

/**
 * 
 * @author Deniz ARIKAN
 */
public class SettlementAddAutomanagedResourceCommand extends FreeRealmAbstractCommand {

	private final Settlement settlement;
	private final Resource resource;

	public SettlementAddAutomanagedResourceCommand(Settlement settlement, Resource resource) {
		this.settlement = settlement;
		this.resource = resource;
	}

	public void execute() {
		settlement.addAutomanagedResource(resource);
		setState(SUCCEEDED);
	}
}

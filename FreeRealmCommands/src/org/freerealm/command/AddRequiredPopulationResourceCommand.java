package org.freerealm.command;

import org.freerealm.Realm;

/**
 * Command class to add a new required settlement resource to realm.
 * 
 * @author Deniz ARIKAN
 */
public class AddRequiredPopulationResourceCommand extends FreeRealmAbstractCommand {

	private final int resourceId;
	private final int amount;

	/**
	 * Constructs an AddRequiredPopulationResourceCommand using resourceId,
	 * amount.
	 * 
	 * @param resourceId
	 *            Id of resource to add
	 * @param amount
	 *            Needed amount for the resource
	 */
	public AddRequiredPopulationResourceCommand(Realm realm, int resourceId, int amount) {
		super(realm);
		this.resourceId = resourceId;
		this.amount = amount;
	}

	/**
	 * Executes command and adds given resourceId and amount to required
	 * settlement resources of the realm
	 * 
	 * @param realm
	 *            Realm to execute the command
	 * @return CommandResult
	 */
	public void execute() {
		getRealm().addRequiredPopulationResourceAmount(resourceId, amount);
		setState(SUCCEEDED);
	}

}

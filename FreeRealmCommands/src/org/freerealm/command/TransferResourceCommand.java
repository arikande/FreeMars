package org.freerealm.command;

import org.freerealm.resource.Resource;
import org.freerealm.resource.ResourceStorer;

/**
 * Command class to transfer given amount of resource from a source resource
 * storer to destination. Upon execution this command will return an error if
 * Source does not have enough resource for transfer. If capacity of destination
 * is not enough for transfer then amount of resource for available space will
 * be transferred.
 * 
 * @author Deniz ARIKAN
 */
public class TransferResourceCommand extends FreeRealmAbstractCommand {

	private final ResourceStorer source;
	private final ResourceStorer destination;
	private final Resource resource;
	private int amount = 0;

	/**
	 * Constructs a TransferResourceCommand using source, destination, resource,
	 * amount
	 * 
	 * @param source
	 *            Source for resource transfer
	 * @param destination
	 *            Destination for resource transfer
	 * @param resource
	 *            Resource type
	 * @param amount
	 *            Amount to transfer
	 */
	public TransferResourceCommand(ResourceStorer source, ResourceStorer destination, Resource resource, int amount) {
		this.source = source;
		this.destination = destination;
		this.resource = resource;
		this.amount = amount;
	}

	/**
	 * Executes command to transfer given amount of resource from source to
	 * destination.
	 * 
	 * @param realm
	 *            Realm to execute the command
	 * @return CommandResult
	 */
	public void execute() {
		if (amount > source.getResourceQuantity(resource)) {
			setText("Not enough resource");
			setState(FAILED);
			return;
		}
		if (destination.getRemainingCapacity(resource) < amount) {
			amount = destination.getRemainingCapacity(resource);
		}
		source.setResourceQuantity(resource, source.getResourceQuantity(resource) - amount);
		destination.setResourceQuantity(resource, destination.getResourceQuantity(resource) + amount);
		setState(SUCCEEDED);
	}

}

package org.freemars.random.event;

import org.freerealm.executor.CommandResult;
import org.freerealm.resource.Resource;

/**
 * @author Deniz ARIKAN
 */
public class FoodContaminationEventCommand extends ResourceLossEventCommand {

    @Override
    public CommandResult execute() {
        Resource foodResource = getRealm().getResourceManager().getResource("Food");
        setResource(foodResource);
        return super.execute();
    }
}

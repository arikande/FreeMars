package org.freemars.random.event;

import org.freerealm.executor.CommandResult;
import org.freerealm.resource.Resource;

/**
 * @author Deniz ARIKAN
 */
public class WaterLeakEventCommand extends ResourceLossEventCommand {

    @Override
    public CommandResult execute() {
        Resource waterResource = getRealm().getResourceManager().getResource("Water");
        setResource(waterResource);
        return super.execute();
    }
}

package org.freemars.random.event;

import org.freerealm.executor.CommandResult;
import org.freerealm.resource.Resource;

/**
 * @author Deniz ARIKAN
 */
public class EnergyLossEventCommand extends ResourceLossEventCommand {

    @Override
    public CommandResult execute() {
        Resource energyResource = getRealm().getResourceManager().getResource("Energy");
        setResource(energyResource);
        return super.execute();
    }
}

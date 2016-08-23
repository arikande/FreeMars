package org.freemars.message;

import org.freemars.colony.FreeMarsColony;
import org.freerealm.player.DefaultMessage;
import org.freerealm.resource.Resource;

/**
 *
 * @author Deniz ARIKAN
 */
public class NotEnoughFertilizerMessage extends DefaultMessage {

    private FreeMarsColony freeMarsColony;
    private Resource fertilizerResource;

    public FreeMarsColony getFreeMarsColony() {
        return freeMarsColony;
    }

    public void setFreeMarsColony(FreeMarsColony freeMarsColony) {
        this.freeMarsColony = freeMarsColony;
    }

    public Resource getFertilizerResource() {
        return fertilizerResource;
    }

    public void setFertilizerResource(Resource fertilizerResource) {
        this.fertilizerResource = fertilizerResource;
    }
}

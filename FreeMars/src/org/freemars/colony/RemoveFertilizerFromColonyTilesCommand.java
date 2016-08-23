package org.freemars.colony;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.freerealm.Realm;
import org.freerealm.command.AbstractCommand;
import org.freerealm.executor.CommandResult;
import org.freerealm.map.Coordinate;

/**
 *
 * @author Deniz ARIKAN
 */
public class RemoveFertilizerFromColonyTilesCommand extends AbstractCommand {

    private final FreeMarsColony freeMarsColony;
    private final boolean addFertilizerToColonyStorage;

    public RemoveFertilizerFromColonyTilesCommand(Realm realm, FreeMarsColony freeMarsColony, boolean addFertilizerToColonyStorage) {
        super(realm);
        this.freeMarsColony = freeMarsColony;
        this.addFertilizerToColonyStorage = addFertilizerToColonyStorage;
    }

    public CommandResult execute() {
        List<Coordinate> fertilizedCoordinates = new ArrayList<Coordinate>();
        Iterator<Coordinate> iterator = freeMarsColony.getFertilizedCoordinatesIterator();
        while (iterator.hasNext()) {
            fertilizedCoordinates.add(iterator.next());
        }
        iterator = fertilizedCoordinates.iterator();
        while (iterator.hasNext()) {
            Coordinate coordinate = iterator.next();
            getExecutor().execute(new RemoveFertilizerFromTileCommand(getRealm(), freeMarsColony, coordinate, addFertilizerToColonyStorage));
        }
        return new CommandResult(CommandResult.RESULT_OK, "");
    }
}

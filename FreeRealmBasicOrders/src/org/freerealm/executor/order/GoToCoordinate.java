package org.freerealm.executor.order;

import org.freerealm.Realm;
import org.freerealm.command.UnitAdvanceToCoordinateCommand;
import org.freerealm.map.Coordinate;

/**
 *
 * @author Deniz ARIKAN
 */
public class GoToCoordinate extends AbstractOrder {

    private Coordinate coordinate;
    private static final String NAME = "GoToCoordinate";

    public GoToCoordinate(Realm realm) {
        super(realm);
        setSymbol("G");
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    @Override
    public boolean isExecutable() {
        if (getUnit().getMovementPoints() == 0) {
            return false;
        }
        return true;
    }

    @Override
    public void execute() {
        getExecutor().execute(new UnitAdvanceToCoordinateCommand(getRealm(), getUnit(), coordinate));
        if (getUnit().getCoordinate().equals(coordinate)) {
            setComplete(true);
        }
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

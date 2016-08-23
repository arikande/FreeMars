package org.freemars.ui.map;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.List;
import org.freerealm.Realm;
import org.freerealm.map.Coordinate;
import org.freerealm.map.Map;

/**
 *
 * @author Deniz ARIKAN
 */
public class RealmGridPanel extends RhombusGridPanel {

    private Realm realm;
    private int abscissaOffset = 0;
    private int ordinateOffset = 0;

    public RealmGridPanel(Realm realm, int gridWidth, int gridHeight) {
        super(gridWidth, gridHeight);
        setRealm(realm);
    }

    public Rectangle findPaintRectangle(List<Coordinate> worldCoordinates) {
        int minPaintAbscissa = Integer.MAX_VALUE;
        int maxPaintAbscissa = 0;
        int minPaintOrdinate = Integer.MAX_VALUE;
        int maxPaintOrdinate = 0;
        for (Coordinate worldCoordinate : worldCoordinates) {
            Coordinate relativeCoordinate = getRelativeCoordinate(worldCoordinate);
            Point point = getPaintPoint(relativeCoordinate);
            minPaintAbscissa = Math.min(minPaintAbscissa, (int) point.getX());
            maxPaintAbscissa = Math.max(maxPaintAbscissa, (int) point.getX() + getGridWidth());
            minPaintOrdinate = Math.min(minPaintOrdinate, (int) point.getY());
            maxPaintOrdinate = Math.max(maxPaintOrdinate, (int) point.getY() + getGridHeight());
        }
        return new Rectangle(minPaintAbscissa - 10, minPaintOrdinate - 10, maxPaintAbscissa - minPaintAbscissa + 20, maxPaintOrdinate - minPaintOrdinate + 20);
    }

    private void calibrateGridsAndOffsets() {
        Map map = getRealm().getMap();
        if (map != null) {
            setAbscissaOffset(getAbscissaOffset() % getRealm().getMapWidth());
            if (getAbscissaOffset() < 0) {
                setAbscissaOffset(abscissaOffset + getRealm().getMapWidth());
            }
            if ((getOrdinateOffset() + getVerticalGrids()) > realm.getMapHeight() + 2) {
                setOrdinateOffset(realm.getMapHeight() - getVerticalGrids() + 2);
            }
            if (getOrdinateOffset() < 0) {
                setOrdinateOffset(0);
            }
        }
    }

    protected Coordinate getWorldCoordinate(Coordinate relativeCoordinate) {
        int worldAbscissa = (getAbscissaOffset() + relativeCoordinate.getAbscissa()) % getRealm().getMapWidth();
        if (worldAbscissa < 0) {
            worldAbscissa = worldAbscissa + getRealm().getMapWidth();
        }
        int worldOrdinate = getOrdinateOffset() + relativeCoordinate.getOrdinate();
        return new Coordinate(worldAbscissa, worldOrdinate);
    }

    protected Coordinate getRelativeCoordinate(Coordinate worldCoordinate) {
        int relativeAbscissa = (worldCoordinate.getAbscissa() - getAbscissaOffset() + getRealm().getMapWidth()) % getRealm().getMapWidth();
        int relativeOrdinate = worldCoordinate.getOrdinate() - getOrdinateOffset();
        return new Coordinate(relativeAbscissa, relativeOrdinate);
    }

    protected Point getPaintPoint(Coordinate relativeCoordinate) {
        return super.getRhombusPoint(relativeCoordinate);
    }

    public Coordinate getCoordinateAt(int x, int y) {
        int worldAbscissa = getAbscissaOffset() * getGridWidth() + x;
        int worldOrdinate = getOrdinateOffset() * getGridHeight() / 2 + y;
        int M = (worldAbscissa + 2 * worldOrdinate - (getGridWidth() / 2)) / getGridWidth();
        int N = (-worldOrdinate + (worldAbscissa / 2) - (getGridHeight() / 2));
        N = (N > 0 ? (N / getGridHeight()) + 1 : N / getGridHeight());
        int tileAbscissa = ((M + N) / 2) % getRealm().getMapWidth();
        Coordinate coordinate = new Coordinate(tileAbscissa, M - N);
        return coordinate;
    }

    public boolean isDisplayingCoordinate(Coordinate coordinate) {
        int startAbscissa = getAbscissaOffset();
        int endAbscissa = (getAbscissaOffset() + getHorizontalGrids()) % getRealm().getMapWidth();
        int checkAbscissa = coordinate.getAbscissa();
        if (startAbscissa > endAbscissa) {
//            startAbscissa = startAbscissa - getRealm().getMapWidth();
            endAbscissa = endAbscissa + getRealm().getMapWidth();
        }
        if (startAbscissa > checkAbscissa) {
            checkAbscissa = checkAbscissa + getRealm().getMapWidth();
        }
        boolean isDisplayingAbscissa = (startAbscissa + 2 <= checkAbscissa) && (checkAbscissa < endAbscissa - 4);
        boolean isDisplayingOrdinate = (getOrdinateOffset() + 2 <= coordinate.getOrdinate()) && (coordinate.getOrdinate() < getOrdinateOffset() + getVerticalGrids() - 3);
        return isDisplayingAbscissa && isDisplayingOrdinate;
    }

    public void centerToCoordinate(Coordinate coordinate) {
        int newAbscissaOffset = coordinate.getAbscissa() - (getHorizontalGrids() / 2) + 1;
        int newOrdinateOffset = coordinate.getOrdinate() - (getVerticalGrids() / 2);
        setAbscissaOffset(newAbscissaOffset);
        setOrdinateOffset(newOrdinateOffset);
        calibrateGridsAndOffsets();
    }

    public int getAbscissaOffset() {
        return abscissaOffset;
    }

    public void setAbscissaOffset(int abscissaOffset) {
        this.abscissaOffset = abscissaOffset;
    }

    public int getOrdinateOffset() {
        return ordinateOffset;
    }

    public void setOrdinateOffset(int ordinateOffset) {
        if (ordinateOffset < 0) {
            ordinateOffset = 0;
        }
        this.ordinateOffset = ordinateOffset;
        if (ordinateOffset % 2 == 1) {
            setColumnMode(1);
        } else {
            setColumnMode(0);
        }
    }

    public Realm getRealm() {
        return realm;
    }

    private void setRealm(Realm realm) {
        this.realm = realm;
    }
}

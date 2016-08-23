package org.freerealm.map;

/**
 *
 * @author Deniz ARIKAN
 */
public class Coordinate implements Comparable {

    int abscissa;
    int ordinate;

    public Coordinate() {
    }

    public Coordinate(int abscissa, int ordinate) {
        this.abscissa = abscissa;
        this.ordinate = ordinate;
    }

    @Override
    public String toString() {
        return "(" + getAbscissa() + "," + getOrdinate() + ")";
    }

    @Override
    public int hashCode() {
        return getAbscissa() * getOrdinate();
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof Coordinate) {
            Coordinate o = (Coordinate) other;
            return (o.getAbscissa() == getAbscissa()) && (o.getOrdinate() == getOrdinate());
        }
        return false;
    }

    public int getAbscissa() {
        return abscissa;
    }

    public void setAbscissa(int abscissa) {
        this.abscissa = abscissa;
    }

    public int getOrdinate() {
        return ordinate;
    }

    public void setOrdinate(int ordinate) {
        this.ordinate = ordinate;
    }

    public int compareTo(Object o) {
        if (getOrdinate() < ((Coordinate) o).getOrdinate()) {
            return -1;
        } else if (getOrdinate() > ((Coordinate) o).getOrdinate()) {
            return 1;
        } else {
            if (getAbscissa() < ((Coordinate) o).getAbscissa()) {
                return -1;
            } else if (getAbscissa() > ((Coordinate) o).getAbscissa()) {
                return 1;
            } else {
                return 0;
            }
        }
    }

    public Coordinate getRelativeCoordinate(Direction direction) {
        int newAbscissa = getAbscissa();
        int newOrdinate = getOrdinate();
        switch (direction.getId()) {
            case 7:
                newAbscissa = getAbscissa() - 1 + (Math.abs(getOrdinate()) % 2);
                newOrdinate = getOrdinate() - 1;
                break;
            case 8:
                newOrdinate = getOrdinate() - 2;
                break;
            case 9:
                newAbscissa = getAbscissa() + (Math.abs(getOrdinate()) % 2);
                newOrdinate = getOrdinate() - 1;
                break;
            case 4:
                newAbscissa = getAbscissa() - 1;
                break;
            case 6:
                newAbscissa = getAbscissa() + 1;
                break;
            case 1:
                newAbscissa = getAbscissa() - 1 + (Math.abs(getOrdinate()) % 2);
                newOrdinate = getOrdinate() + 1;
                break;
            case 2:
                newOrdinate = getOrdinate() + 2;
                break;
            case 3:
                newAbscissa = getAbscissa() + (Math.abs(getOrdinate()) % 2);
                newOrdinate = getOrdinate() + 1;
                break;
        }
        return new Coordinate(newAbscissa, newOrdinate);
    }
}

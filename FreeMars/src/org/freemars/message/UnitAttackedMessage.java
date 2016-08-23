package org.freemars.message;

import org.freerealm.map.Coordinate;
import org.freerealm.player.DefaultMessage;
import org.freerealm.unit.Unit;

/**
 *
 * @author Deniz ARIKAN
 */
public class UnitAttackedMessage extends DefaultMessage {

    private Unit attacker;
    private Unit defender;
    private Unit winner;
    private Coordinate coordinate;

    public Unit getAttacker() {
        return attacker;
    }

    public void setAttacker(Unit attacker) {
        this.attacker = attacker;
    }

    public Unit getDefender() {
        return defender;
    }

    public void setDefender(Unit defender) {
        this.defender = defender;
    }

    public Unit getWinner() {
        return winner;
    }

    public void setWinner(Unit winner) {
        this.winner = winner;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }
}

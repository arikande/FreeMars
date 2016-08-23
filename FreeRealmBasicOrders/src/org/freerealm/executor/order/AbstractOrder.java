package org.freerealm.executor.order;

import org.commandexecutor.Executor;
import org.freerealm.Realm;
import org.freerealm.unit.Order;
import org.freerealm.unit.Unit;

/**
 *
 * @author Deniz ARIKAN
 */
public abstract class AbstractOrder implements Order {
    
    private static final int TURN_GIVEN_DEFAULT_VALUE = 0;
    private Realm realm;
    private int turnGiven = TURN_GIVEN_DEFAULT_VALUE;
    private boolean complete = false;
    private Unit unit;
    private String symbol;
    private Executor executor;
    
    public AbstractOrder(Realm realm) {
        setRealm(realm);
        setTurnGiven(realm.getNumberOfTurns());
    }
    
    public Realm getRealm() {
        return realm;
    }
    
    public void setRealm(Realm realm) {
        this.realm = realm;
    }
    
    public int getTurnGiven() {
        return turnGiven;
    }
    
    public void setTurnGiven(int turnGiven) {
        this.turnGiven = turnGiven;
    }
    
    public boolean isComplete() {
        return complete;
    }
    
    public void setComplete(boolean complete) {
        this.complete = complete;
    }
    
    public Unit getUnit() {
        return unit;
    }
    
    public void setUnit(Unit unit) {
        this.unit = unit;
    }
    
    public String getSymbol() {
        return symbol;
    }
    
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
    
    public Executor getExecutor() {
        return executor;
    }
    
    public void setExecutor(Executor executor) {
        this.executor = executor;
    }
}

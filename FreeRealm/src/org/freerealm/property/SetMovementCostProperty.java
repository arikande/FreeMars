package org.freerealm.property;

/**
 *
 * @author Deniz ARIKAN
 */
public class SetMovementCostProperty implements Property {

    public static final String NAME = "set_movement_cost_property";
    private float movementCost;

    public String getName() {
        return NAME;
    }

    public float getMovementCost() {
        return movementCost;
    }

    public void setMovementCost(float movementCost) {
        this.movementCost = movementCost;
    }
}

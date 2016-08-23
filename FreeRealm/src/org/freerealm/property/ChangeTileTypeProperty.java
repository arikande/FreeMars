package org.freerealm.property;

/**
 * @author Deniz ARIKAN
 */
public class ChangeTileTypeProperty implements Property {

    private static final String NAME = "ChangeTileTypeProperty";
    private int productionPoints;

    public String getName() {
        return NAME;
    }

    public int getProductionPoints() {
        return productionPoints;
    }

    public void setProductionPoints(int productionPoints) {
        this.productionPoints = productionPoints;
    }
}

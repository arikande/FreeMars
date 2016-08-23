package org.freemars.earth.ui;

import java.io.Serializable;

/**
 *
 * @author Deniz ARIKAN
 */
public class EarthTransferData implements Serializable {

    private final int source;
    private final String resource;
    private int quantity = 0;

    public EarthTransferData(int source, String resource, int quantity) {
        this.source = source;
        this.resource = resource;
        this.quantity = quantity;
    }

    public String getResource() {
        return resource;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getSource() {
        return source;
    }
}

package org.freemars.colonydialog.controller;

import java.io.Serializable;

public class ResourceTransferData implements Serializable {

    private final String resource;
    private int quantity = 0;

    public ResourceTransferData(String resource, int quantity) {
        this.resource = resource;
        this.quantity = quantity;
    }

    public String getResource() {
        return resource;
    }

    public int getQuantity() {
        return quantity;
    }
}

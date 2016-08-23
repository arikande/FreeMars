package org.freemars.colonydialog.controller;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.io.Serializable;

public class ResourceTransferable implements Transferable, Serializable {

    private final ResourceTransferData resourceTransferData;
    private DataFlavor resourceTransferDataFlavor;

    public ResourceTransferable(String resource, int quantity) {
        try {
            resourceTransferDataFlavor = new DataFlavor(DataFlavor.javaJVMLocalObjectMimeType + ";class=" + ResourceTransferData.class.getName());
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        resourceTransferData = new ResourceTransferData(resource, quantity);
    }

    public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
        return resourceTransferData;
    }

    public DataFlavor[] getTransferDataFlavors() {
        DataFlavor[] supportedFlavors = {resourceTransferDataFlavor};
        return supportedFlavors;
    }

    public boolean isDataFlavorSupported(DataFlavor flavor) {
        if (flavor.equals(DataFlavor.javaJVMLocalObjectMimeType)) {
            return true;
        }
        return false;
    }
}

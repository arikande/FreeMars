package org.freemars.earth.ui;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.io.Serializable;

public class EarthTransferable implements Transferable, Serializable {

    public static final int FROM_EARTH = 0;
    public static final int FROM_SPACESHIP = 1;
    private final EarthTransferData earthTransferData;
    private DataFlavor resourceTransferDataFlavor;

    public EarthTransferable(int source, String resource, int quantity) {
        try {
            resourceTransferDataFlavor = new DataFlavor(DataFlavor.javaJVMLocalObjectMimeType + ";class=" + EarthTransferData.class.getName());
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        earthTransferData = new EarthTransferData(source, resource, quantity);
    }

    public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
        return earthTransferData;
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

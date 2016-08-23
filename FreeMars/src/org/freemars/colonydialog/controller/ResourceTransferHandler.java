package org.freemars.colonydialog.controller;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;

import org.freemars.colonydialog.ColonyDialogModel;
import org.freemars.controller.FreeMarsController;
import org.freemars.controller.viewcommand.PlaySoundCommand;
import org.freemars.ui.image.FreeMarsImageManager;
import org.freerealm.command.TransferResourceCommand;
import org.freerealm.command.UnitOrdersClearCommand;
import org.freerealm.executor.command.UnitSetMovementPointsCommand;
import org.freerealm.executor.order.Sentry;
import org.freerealm.resource.Resource;
import org.freerealm.resource.ResourceStorer;
import org.freerealm.unit.Unit;

public class ResourceTransferHandler extends PatchedTransferHandler {

    private final FreeMarsController freeMarsController;
    private final ColonyDialogModel model;
    private ResourceStorer source;
    private ResourceStorer destination;

    public ResourceTransferHandler(FreeMarsController freeMarsController, ColonyDialogModel model) {
        this.freeMarsController = freeMarsController;
        this.model = model;
    }

    @Override
    public int getSourceActions(JComponent c) {
        return COPY_OR_MOVE;
    }

    @Override
    public Icon getVisualRepresentation(Transferable transferable) {
        try {
            ResourceTransferData resourceTransferData = (ResourceTransferData) transferable.getTransferData(DataFlavor.stringFlavor);
            Resource resource = model.getRealm().getResourceManager().getResource(resourceTransferData.getResource());
            return new ImageIcon(FreeMarsImageManager.getImage(resource));
        } catch (Exception e) {
        }

        return new ImageIcon(FreeMarsImageManager.getImage("RESOURCE_1"));
    }

    @Override
    public boolean canImport(JComponent arg0, DataFlavor[] arg1) {
        for (int i = 0; i < arg1.length; i++) {
            DataFlavor flavor = arg1[i];
            if (flavor.equals(DataFlavor.javaJVMLocalObjectMimeType)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean importData(JComponent comp, Transferable t) {
        try {
            ResourceTransferData resourceTransferData = (ResourceTransferData) t.getTransferData(DataFlavor.stringFlavor);
            Resource resource = model.getRealm().getResourceManager().getResource(resourceTransferData.getResource());
            int quantity = resourceTransferData.getQuantity();
            TransferResourceCommand transferResourceCommand = new TransferResourceCommand(getSource(), getDestination(), resource, quantity);
            freeMarsController.execute(transferResourceCommand);
            boolean resourceTransferSound = Boolean.valueOf(freeMarsController.getFreeMarsModel().getFreeMarsPreferences().getProperty("resource_transfer_sound"));
            if (resourceTransferSound) {
                freeMarsController.executeViewCommandImmediately(new PlaySoundCommand("sound/resource_transfer.wav"));
            }
            if (getSource() instanceof Unit) {
                freeMarsController.execute(new UnitSetMovementPointsCommand((Unit) getSource(), 0));
            }
            if (getDestination() instanceof Unit) {
                Unit destinationUnit = (Unit) getDestination();
                if (destinationUnit.getCurrentOrder() instanceof Sentry) {
                    freeMarsController.execute(new UnitOrdersClearCommand(destinationUnit));
                }
                freeMarsController.execute(new UnitSetMovementPointsCommand(destinationUnit, 0));
            }
        } catch (UnsupportedFlavorException exception) {
            exception.printStackTrace();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        return super.importData(comp, t);
    }

    public ResourceStorer getSource() {
        return source;
    }

    public void setSource(ResourceStorer source) {
        this.source = source;
    }

    public ResourceStorer getDestination() {
        return destination;
    }

    public void setDestination(ResourceStorer destination) {
        this.destination = destination;
    }

    public ColonyDialogModel getModel() {
        return model;
    }
}

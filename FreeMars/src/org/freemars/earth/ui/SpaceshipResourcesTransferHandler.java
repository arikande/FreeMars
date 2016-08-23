package org.freemars.earth.ui;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.text.DecimalFormat;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import org.freemars.controller.FreeMarsController;
import org.freemars.earth.Earth;
import org.freemars.earth.command.BuyResourceFromEarthCommand;
import org.freemars.player.FreeMarsPlayer;
import org.freemars.ui.image.FreeMarsImageManager;
import org.freemars.ui.util.FreeMarsOptionPane;
import org.freerealm.executor.CommandResult;
import org.freerealm.resource.Resource;
import org.freerealm.resource.ResourceStorer;
import org.freerealm.unit.Unit;

/**
 *
 * @author Deniz ARIKAN
 */
public class SpaceshipResourcesTransferHandler extends PatchedTransferHandler {

    private final FreeMarsController freeMarsController;
    private final EarthDialog earthDialog;
    private final ResourceStorer resourceStorer;

    public SpaceshipResourcesTransferHandler(FreeMarsController freeMarsController, EarthDialog earthDialog, ResourceStorer resourceStorer) {
        this.freeMarsController = freeMarsController;
        this.earthDialog = earthDialog;
        this.resourceStorer = resourceStorer;
    }

    @Override
    public int getSourceActions(JComponent c) {
        return COPY_OR_MOVE;
    }

    @Override
    public Icon getVisualRepresentation(Transferable transferable) {
        try {
            EarthTransferData resourceTransferData = (EarthTransferData) transferable.getTransferData(DataFlavor.stringFlavor);
            Resource resource = freeMarsController.getFreeMarsModel().getRealm().getResourceManager().getResource(resourceTransferData.getResource());
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
    protected Transferable createTransferable(JComponent c) {
        ResourceStorerPanel resourceStorerPanel = (ResourceStorerPanel) c;
        String resourceName = resourceStorerPanel.getResource().getName();
        Resource resource = freeMarsController.getFreeMarsModel().getRealm().getResourceManager().getResource(resourceName);
        int unitResourceQuantity = freeMarsController.getFreeMarsModel().getEarth().getEarthDialogSelectedUnit().getResourceQuantity(resource);
        if (unitResourceQuantity > 0) {
            int requestedTransferAmount = earthDialog.getSelectedQuantity();
            return new EarthTransferable(EarthTransferable.FROM_SPACESHIP, resourceName, requestedTransferAmount > unitResourceQuantity ? unitResourceQuantity : requestedTransferAmount);
        }
        return null;
    }

    @Override
    public boolean importData(JComponent comp, Transferable t) {
        try {
            FreeMarsPlayer player = (FreeMarsPlayer) ((Unit) resourceStorer).getPlayer();
            if (player.hasDeclaredIndependence()) {
                FreeMarsOptionPane.showMessageDialog(earthDialog, "We do not sell to rebels!", "Rejected");
            } else if (((Unit) resourceStorer).getRemainingCapacity() == 0) {
                earthDialog.addUnitInfoText("Unit has no cargo capacity left.\n\n");
            } else {
                EarthTransferData earthTransferData = (EarthTransferData) t.getTransferData(DataFlavor.stringFlavor);
                if (earthTransferData.getSource() == EarthTransferable.FROM_EARTH) {
                    Resource resource = freeMarsController.getFreeMarsModel().getRealm().getResourceManager().getResource(earthTransferData.getResource());
                    int quantity = earthTransferData.getQuantity();
                    int unitResourceRemainingCapacity = freeMarsController.getFreeMarsModel().getEarth().getEarthDialogSelectedUnit().getRemainingCapacity(resource);
                    quantity = quantity > unitResourceRemainingCapacity ? unitResourceRemainingCapacity : quantity;
                    int totalPrice = freeMarsController.getFreeMarsModel().getEarth().getEarthSellsAtPrice(resource) * quantity;
                    String formattedTotalPrice = new DecimalFormat().format(totalPrice);
                    String formattedQuantity = new DecimalFormat().format(quantity);
                    CommandResult result = freeMarsController.execute(new BuyResourceFromEarthCommand(freeMarsController.getFreeMarsModel().getEarth(), (Unit) resourceStorer, resource, quantity));
                    if (result.getCode() == CommandResult.RESULT_OK) {
                        earthDialog.addUnitInfoText(formattedQuantity + " " + resource.getName() + " bought for " + formattedTotalPrice + "\n");
                        String formattedWealth = new DecimalFormat().format(((Unit) resourceStorer).getPlayer().getWealth());
                        earthDialog.addUnitInfoText("New treasury : " + formattedWealth + " " + freeMarsController.getFreeMarsModel().getRealm().getProperty("currency_unit") + "\n\n");
                        earthDialog.update(Earth.BUY_SELL_UPDATE);
                    }
                }
            }
        } catch (UnsupportedFlavorException exception) {
            exception.printStackTrace();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        return super.importData(comp, t);
    }
}

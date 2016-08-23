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
import org.freemars.controller.viewcommand.PlaySoundCommand;
import org.freemars.earth.Earth;
import org.freemars.earth.command.SellResourceToEarthCommand;
import org.freemars.player.FreeMarsPlayer;
import org.freemars.ui.image.FreeMarsImageManager;
import org.freemars.ui.util.FreeMarsOptionPane;
import org.freerealm.executor.CommandResult;
import org.freerealm.resource.Resource;
import org.freerealm.resource.ResourceStorer;
import org.freerealm.unit.Unit;

public class EarthTransferHandler extends PatchedTransferHandler {

    private final FreeMarsController freeMarsController;
    private final EarthDialog earthDialog;
    private final ResourceStorer resourceStorer;

    public EarthTransferHandler(FreeMarsController freeMarsController, EarthDialog earthDialog, ResourceStorer resourceStorer) {
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
        EarthPricesPanel earthPricesPanel = (EarthPricesPanel) c;
        String resource = earthPricesPanel.getResource().getName();
        int amount = earthDialog.getSelectedQuantity();
        return new EarthTransferable(EarthTransferable.FROM_EARTH, resource, amount);
    }

    @Override
    public boolean importData(JComponent comp, Transferable t) {
        try {
            FreeMarsPlayer player = (FreeMarsPlayer) ((Unit) resourceStorer).getPlayer();
            if (player.hasDeclaredIndependence()) {
                FreeMarsOptionPane.showMessageDialog(earthDialog, "We do not buy from rebels!", "Rejected");
            } else {
                EarthTransferData earthTransferData = (EarthTransferData) t.getTransferData(DataFlavor.stringFlavor);
                if (earthTransferData.getSource() == EarthTransferable.FROM_SPACESHIP) {
                    Resource resource = freeMarsController.getFreeMarsModel().getRealm().getResourceManager().getResource(earthTransferData.getResource());
                    int quantity = earthTransferData.getQuantity();
                    CommandResult result = freeMarsController.execute(new SellResourceToEarthCommand(freeMarsController, (Unit) resourceStorer, resource, quantity));
                    if (result.getCode() == CommandResult.RESULT_OK) {
                        int totalPrice = ((Integer) result.getParameter("total_price"));
                        int taxRate = (Integer) result.getParameter("tax_rate");
                        int taxAmount = (Integer) result.getParameter("tax_amount");
                        int netIncome = (Integer) result.getParameter("net_income");
                        String formattedTotalPrice = new DecimalFormat().format(totalPrice);
                        String formattedQuantity = new DecimalFormat().format(quantity);
                        earthDialog.addUnitInfoText(formattedQuantity + " " + resource.getName() + " sold for " + formattedTotalPrice + "\n");
                        if (taxAmount > 0) {
                            String formattedTaxAmount = new DecimalFormat().format(taxAmount);
                            earthDialog.addUnitInfoText("Earth tax : -" + formattedTaxAmount + " (" + taxRate + "%)\n");
                            String formattedNetIncome = new DecimalFormat().format(netIncome);
                            earthDialog.addUnitInfoText("Net income : " + formattedNetIncome + "\n");
                        }
                        String formattedWealth = new DecimalFormat().format(((Unit) resourceStorer).getPlayer().getWealth());
                        earthDialog.addUnitInfoText("New treasury : " + formattedWealth + " " + freeMarsController.getFreeMarsModel().getRealm().getProperty("currency_unit") + "\n\n");
                        earthDialog.update(Earth.BUY_SELL_UPDATE);
                        boolean wealthTransferSound = Boolean.valueOf(freeMarsController.getFreeMarsModel().getFreeMarsPreferences().getProperty("wealth_transfer_sound"));
                        if (wealthTransferSound) {
                            freeMarsController.executeViewCommandImmediately(new PlaySoundCommand("sound/wealth_transfer.wav"));
                        }
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

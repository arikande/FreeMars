package org.freemars.colonydialog.controller;

import java.awt.datatransfer.Transferable;
import javax.swing.JComponent;
import javax.swing.JToggleButton;
import org.freemars.colonydialog.ColonyDialogModel;
import org.freemars.colonydialog.unit.ResourceStorerPanel;
import org.freemars.colonydialog.unit.SelectorPanel;
import org.freemars.controller.FreeMarsController;
import org.freerealm.unit.Unit;

/**
 *
 * @author Deniz ARIKAN
 */
public class UnitResourceTransferHandler extends ResourceTransferHandler {

    public UnitResourceTransferHandler(FreeMarsController freeMarsController, ColonyDialogModel model) {
        super(freeMarsController, model);
    }

    @Override
    protected Transferable createTransferable(JComponent c) {
        ResourceStorerPanel resourceStorerPanel = (ResourceStorerPanel) c;
        String resource = resourceStorerPanel.getResource().getName();
        int requestedTransferAmount = getModel().getResourceTransferAmount();
        int colonyResourceQuantity = getModel().getSelectedUnit().getResourceQuantity(getModel().getRealm().getResourceManager().getResource(resource));
        int quantity = requestedTransferAmount > colonyResourceQuantity ? colonyResourceQuantity : requestedTransferAmount;
        ResourceTransferable resourceTransferable = new ResourceTransferable(resource, quantity);
        getModel().setResourceTransferSource(getModel().getSelectedUnit());
        return resourceTransferable;
    }

    @Override
    public boolean importData(JComponent comp, Transferable t) {
        setSource(getModel().getResourceTransferSource());
        if (comp instanceof JToggleButton && comp.getParent() != null && comp.getParent() instanceof SelectorPanel) {
            JToggleButton toggleButton = (JToggleButton) comp;
            SelectorPanel selectorPanel = (SelectorPanel) comp.getParent();
            setDestination((Unit) selectorPanel.getSelectable(toggleButton));
        } else {
            setDestination(getModel().getSelectedUnit());
        }
        boolean result = super.importData(comp, t);
        getModel().refresh(ColonyDialogModel.UNIT_CARGO_CHANGE_UPDATE);
        return result;
    }
}

package org.freemars.colonydialog.controller;

import javax.swing.AbstractButton;
import javax.swing.ButtonModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.freemars.colony.AddFertilizerToColonyTilesCommand;
import org.freemars.colonydialog.ColonyDialogModel;
import org.freemars.controller.FreeMarsController;
import org.freerealm.Realm;
import org.freerealm.command.SettlementAddAutomanagedResourceCommand;
import org.freerealm.command.SettlementAutomanageResourceCommand;
import org.freerealm.command.SettlementRemoveAutomanagedResourceCommand;
import org.freerealm.resource.Resource;

/**
 *
 * @author Deniz ARIKAN
 */
public class AutomanageFoodButtonChangeListener implements ChangeListener {

    private final FreeMarsController freeMarsController;
    private final ColonyDialogModel colonyDialogModel;

    public AutomanageFoodButtonChangeListener(FreeMarsController freeMarsController, ColonyDialogModel colonyDialogModel) {
        this.freeMarsController = freeMarsController;
        this.colonyDialogModel = colonyDialogModel;
    }

    public void stateChanged(ChangeEvent changeEvent) {
        AbstractButton abstractButton = (AbstractButton) changeEvent.getSource();
        ButtonModel buttonModel = abstractButton.getModel();
        boolean armed = buttonModel.isArmed();
        boolean pressed = buttonModel.isPressed();
        boolean selected = buttonModel.isSelected();
        Resource foodResource = colonyDialogModel.getFreeMarsModel().getRealm().getResourceManager().getResource(Resource.FOOD);
        Realm realm = freeMarsController.getFreeMarsModel().getRealm();
        if (!armed & !pressed & selected) {
            freeMarsController.execute(new SettlementAddAutomanagedResourceCommand(colonyDialogModel.getColony(), foodResource));
            freeMarsController.execute(new SettlementAutomanageResourceCommand(realm, colonyDialogModel.getColony(), foodResource));
            if (colonyDialogModel.getColony().isAutoUsingFertilizer()) {
                freeMarsController.execute(new AddFertilizerToColonyTilesCommand(realm, colonyDialogModel.getColony()));
                freeMarsController.execute(new SettlementAutomanageResourceCommand(realm, colonyDialogModel.getColony(), foodResource));
            }
            colonyDialogModel.refresh(ColonyDialogModel.WORKFORCE_UPDATE);
            colonyDialogModel.refresh(ColonyDialogModel.COLONY_RESOURCES_UPDATE);
            colonyDialogModel.refresh(ColonyDialogModel.COLONY_IMPROVEMENTS_UPDATE);
        } else {
            freeMarsController.execute(new SettlementRemoveAutomanagedResourceCommand(colonyDialogModel.getColony(), foodResource));
        }

    }
}

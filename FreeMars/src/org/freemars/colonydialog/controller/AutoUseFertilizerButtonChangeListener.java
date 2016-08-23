package org.freemars.colonydialog.controller;

import javax.swing.AbstractButton;
import javax.swing.ButtonModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.freemars.colony.AddFertilizerToColonyTilesCommand;
import org.freemars.colony.RemoveFertilizerFromColonyTilesCommand;
import org.freemars.colonydialog.ColonyDialogModel;
import org.freemars.controller.FreeMarsController;
import org.freerealm.Realm;
import org.freerealm.command.SettlementAutomanageResourceCommand;
import org.freerealm.resource.Resource;

/**
 *
 * @author Deniz ARIKAN
 */
public class AutoUseFertilizerButtonChangeListener implements ChangeListener {

    private final FreeMarsController freeMarsController;
    private final ColonyDialogModel colonyDialogModel;

    public AutoUseFertilizerButtonChangeListener(FreeMarsController freeMarsController, ColonyDialogModel colonyDialogModel) {
        this.freeMarsController = freeMarsController;
        this.colonyDialogModel = colonyDialogModel;
    }

    public void stateChanged(ChangeEvent changeEvent) {
        AbstractButton abstractButton = (AbstractButton) changeEvent.getSource();
        ButtonModel buttonModel = abstractButton.getModel();
        boolean armed = buttonModel.isArmed();
        boolean pressed = buttonModel.isPressed();
        boolean selected = buttonModel.isSelected();
        Realm realm = freeMarsController.getFreeMarsModel().getRealm();
        if (!armed & !pressed & selected) {
            colonyDialogModel.getColony().setAutoUsingFertilizer(true);
            freeMarsController.execute(new AddFertilizerToColonyTilesCommand(realm, colonyDialogModel.getColony()));
        } else if (!armed & !pressed & !selected) {
            colonyDialogModel.getColony().setAutoUsingFertilizer(false);
            freeMarsController.execute(new RemoveFertilizerFromColonyTilesCommand(realm, colonyDialogModel.getColony(), true));
        }
        Resource foodResource = colonyDialogModel.getRealm().getResourceManager().getResource(Resource.FOOD);
        if (colonyDialogModel.getColony().isAutomanagingResource(foodResource)) {
            freeMarsController.execute(new SettlementAutomanageResourceCommand(realm, colonyDialogModel.getColony(), foodResource));
        }
        colonyDialogModel.refresh(ColonyDialogModel.WORKFORCE_UPDATE);
        colonyDialogModel.refresh(ColonyDialogModel.COLONY_RESOURCES_UPDATE);
    }
}

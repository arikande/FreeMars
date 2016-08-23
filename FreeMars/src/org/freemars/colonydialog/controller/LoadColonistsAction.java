package org.freemars.colonydialog.controller;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import org.freemars.colonydialog.ColonyDialogModel;
import org.freemars.command.LoadColonistsCommand;
import org.freemars.controller.FreeMarsController;

/**
 *
 * @author Deniz ARIKAN
 */
public class LoadColonistsAction extends AbstractAction {

    private final FreeMarsController freeMarsController;
    private final ColonyDialogModel colonyDialogModel;
    private final int numberOfColonists;

    public LoadColonistsAction(FreeMarsController freeMarsController, ColonyDialogModel colonyDialogModel, int numberOfColonists) {
        super(String.valueOf(numberOfColonists));
        this.freeMarsController = freeMarsController;
        this.colonyDialogModel = colonyDialogModel;
        this.numberOfColonists = numberOfColonists;
    }

    public void actionPerformed(ActionEvent e) {
        /*
         int colonyPopulation = colonyDialogModel.getColony().getPopulation();
         int unitPopulation = colonyDialogModel.getSelectedUnit().getContainedPopulation();
         int weightPerCitizen = Integer.parseInt(freeMarsController.getFreeMarsModel().getRealm().getProperty("weight_per_citizen"));
         freeMarsController.execute(new SetContainerPopulationCommand(colonyDialogModel.getSelectedUnit(), unitPopulation + numberOfColonists, weightPerCitizen));
         freeMarsController.execute(new SetSettlementPopulationCommand(colonyDialogModel.getColony(), colonyPopulation - numberOfColonists));
         */
        freeMarsController.execute(new LoadColonistsCommand(freeMarsController, colonyDialogModel.getColony(), colonyDialogModel.getSelectedUnit(), numberOfColonists));
        colonyDialogModel.refresh(ColonyDialogModel.WORKFORCE_UPDATE);
        colonyDialogModel.refresh(ColonyDialogModel.COLONY_RESOURCES_UPDATE);
        colonyDialogModel.refresh(ColonyDialogModel.COLONY_IMPROVEMENTS_UPDATE);
        colonyDialogModel.refresh(ColonyDialogModel.UNIT_CARGO_CHANGE_UPDATE);
    }
}

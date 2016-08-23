package org.freemars.colonydialog;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;

import org.freemars.controller.FreeMarsController;
import org.freemars.controller.action.DisplayHelpContentsAction;
import org.freemars.controller.viewcommand.PlaySoundCommand;
import org.freerealm.command.SettlementImprovementSetEnabledCommand;
import org.freerealm.command.SettlementImprovementSetWorkersCommand;
import org.freerealm.settlement.improvement.SettlementImprovement;

/**
 *
 * @author Deniz ARIKAN
 */
public class ColonyImprovementPopupMenu extends JPopupMenu {

    private final ColonyDialogModel colonyDialogModel;
    private final FreeMarsController freeMarsController;
    private SettlementImprovement settlementImprovement;

    public ColonyImprovementPopupMenu(ColonyDialogModel colonyDialogModel, FreeMarsController freeMarsController) {
        this.colonyDialogModel = colonyDialogModel;
        this.freeMarsController = freeMarsController;
    }

    public void setCityImprovement(SettlementImprovement settlementImprovement) {
        this.settlementImprovement = settlementImprovement;
        removeAll();
        addAssignWorkersMenu();
        if (settlementImprovement.isEnabled()) {
            add(new JMenuItem(new DisableColonyImprovementAction(settlementImprovement)));
        } else {
            add(new JMenuItem(new EnableColonyImprovementAction(settlementImprovement)));
        }
        add(new JSeparator());

        JMenuItem displayUnitHelpMenuItem = new JMenuItem(new DisplayHelpContentsAction(freeMarsController, "ColonyImprovement." + settlementImprovement.getType().getName().replace(' ', '_')));
        displayUnitHelpMenuItem.setText(settlementImprovement.getType().getName() + " help");
        add(displayUnitHelpMenuItem);
    }

    private void addAssignWorkersMenu() {
        if (settlementImprovement.getType().getMaximumWorkers() > 0) {
            JMenu assignWorkersMenu = new JMenu("Assign workers");
            int itemCount = 5;
            int maximumAssignableWorkers = 0;
            if (colonyDialogModel.getColony().getProductionWorkforce() + settlementImprovement.getNumberOfWorkers() > settlementImprovement.getType().getMaximumWorkers()) {
                maximumAssignableWorkers = settlementImprovement.getType().getMaximumWorkers();
            } else {
                maximumAssignableWorkers = colonyDialogModel.getColony().getProductionWorkforce() + settlementImprovement.getNumberOfWorkers();
            }
            if (maximumAssignableWorkers < itemCount) {
                itemCount = maximumAssignableWorkers;
            }
            float part = maximumAssignableWorkers / (float) itemCount;
            float workerCount = maximumAssignableWorkers;
            for (int i = 0; i < itemCount + 1; i++) {
                JMenuItem assignWorkersMenuItem = new JMenuItem(new SetColonyImprovementWorkersAction(settlementImprovement, (int) workerCount));
                if (workerCount == settlementImprovement.getType().getMaximumWorkers()) {
                    assignWorkersMenuItem.setText("All (" + settlementImprovement.getType().getMaximumWorkers() + ")");
                }
                assignWorkersMenu.add(assignWorkersMenuItem);
                workerCount = workerCount - part;
            }
            add(assignWorkersMenu);
        }
    }

    private class SetColonyImprovementWorkersAction extends AbstractAction {

        private final SettlementImprovement improvement;
        private final int workers;

        private SetColonyImprovementWorkersAction(SettlementImprovement improvement, int workers) {
            super(String.valueOf(workers));
            this.improvement = improvement;
            this.workers = workers;
        }

        public void actionPerformed(ActionEvent e) {
            freeMarsController.execute(new SettlementImprovementSetEnabledCommand(colonyDialogModel.getColony(), improvement, true));
            freeMarsController.execute(new SettlementImprovementSetWorkersCommand(colonyDialogModel.getColony(), improvement, workers));
            colonyDialogModel.refresh(ColonyDialogModel.WORKFORCE_UPDATE);
            colonyDialogModel.refresh(ColonyDialogModel.COLONY_RESOURCES_UPDATE);
        }
    }

    private class EnableColonyImprovementAction extends AbstractAction {

        private final SettlementImprovement improvement;

        private EnableColonyImprovementAction(SettlementImprovement improvement) {
            super("Enable " + improvement.getType().getName());
            this.improvement = improvement;
        }

        public void actionPerformed(ActionEvent e) {
            boolean colonyImprovementEnableSound = Boolean.valueOf(freeMarsController.getFreeMarsModel().getFreeMarsPreferences().getProperty("colony_improvement_enable_sound"));
            if (colonyImprovementEnableSound) {
                freeMarsController.executeViewCommandImmediately(new PlaySoundCommand("sound/colony_improvement_enable.wav"));
            }
            freeMarsController.execute(new SettlementImprovementSetEnabledCommand(colonyDialogModel.getColony(), improvement, true));
            colonyDialogModel.refresh(ColonyDialogModel.COLONY_IMPROVEMENTS_UPDATE);
        }
    }

    private class DisableColonyImprovementAction extends AbstractAction {

        private final SettlementImprovement improvement;

        private DisableColonyImprovementAction(SettlementImprovement improvement) {
            super("Disable " + improvement.getType().getName());
            this.improvement = improvement;
        }

        public void actionPerformed(ActionEvent e) {
            boolean colonyImprovementDisableSound = Boolean.valueOf(freeMarsController.getFreeMarsModel().getFreeMarsPreferences().getProperty("colony_improvement_disable_sound"));
            if (colonyImprovementDisableSound) {
                freeMarsController.executeViewCommandImmediately(new PlaySoundCommand("sound/colony_improvement_disable.wav"));
            }
            freeMarsController.execute(new SettlementImprovementSetEnabledCommand(colonyDialogModel.getColony(), improvement, false));
            colonyDialogModel.refresh(ColonyDialogModel.WORKFORCE_UPDATE);
            colonyDialogModel.refresh(ColonyDialogModel.COLONY_IMPROVEMENTS_UPDATE);
        }
    }
}

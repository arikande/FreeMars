package org.freemars.controller.action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Iterator;
import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.KeyStroke;
import org.freemars.colony.FreeMarsColony;
import org.freemars.colonydialog.ColonyDialog;
import org.freemars.colonydialog.ColonyDialogModel;
import org.freemars.colonydialog.ColonyImprovementPopupMenu;
import org.freemars.colonydialog.ColonyImprovementsPopupListener;
import org.freemars.colonydialog.controller.AutoUseFertilizerButtonChangeListener;
import org.freemars.colonydialog.controller.AutomanageFoodButtonChangeListener;
import org.freemars.colonydialog.controller.AutomanageWaterButtonChangeListener;
import org.freemars.colonydialog.controller.ColonyResourceTransferHandler;
import org.freemars.colonydialog.controller.ColonySelectorComboBoxListener;
import org.freemars.colonydialog.controller.ContinuousProductionActionListener;
import org.freemars.colonydialog.controller.DisplayBuyProductionDialogAction;
import org.freemars.colonydialog.controller.DisplayQueueManagementDialogAction;
import org.freemars.colonydialog.controller.DisplayRenameColonyDialogAction;
import org.freemars.colonydialog.controller.UnitResourceTransferHandler;
import org.freemars.colonydialog.unit.ColonyUnitOrdersPopup;
import org.freemars.colonydialog.unit.UnitOrdersPopupListener;
import org.freemars.colonydialog.workforce.WorkForceManagementMouseAdapter;
import org.freemars.controller.FreeMarsController;
import org.freemars.controller.viewcommand.SetCenteredCoordinateCommand;
import org.freerealm.settlement.Settlement;

/**
 *
 * @author Deniz ARIKAN
 */
public class DisplayColonyDialogAction extends AbstractAction {

    private final FreeMarsController controller;
    private final FreeMarsColony freeMarsColony;

    public DisplayColonyDialogAction(FreeMarsController controller, FreeMarsColony freeMarsColony) {
        super(freeMarsColony.getName());
        this.controller = controller;
        this.freeMarsColony = freeMarsColony;
    }

    public void actionPerformed(ActionEvent e) {
        controller.executeViewCommand(new SetCenteredCoordinateCommand(controller, freeMarsColony.getCoordinate()));

        ColonyDialogModel model = new ColonyDialogModel();
        model.setFreeMarsModel(controller.getFreeMarsModel());
        model.setColony(freeMarsColony);
        Iterator<Settlement> iterator = freeMarsColony.getPlayer().getSettlementsIterator();
        while (iterator.hasNext()) {
            Settlement playerCity = iterator.next();
            model.addSelectableColony(playerCity);
        }
        ColonyDialog colonyDialog = new ColonyDialog(controller.getCurrentFrame(), controller);
        colonyDialog.setModel(model);

        ColonyUnitOrdersPopup colonyUnitOrdersPopup = new ColonyUnitOrdersPopup(model, controller);
        colonyUnitOrdersPopup.addPopupMenuListener(new UnitOrdersPopupListener(model));
        colonyDialog.setUnitOrdersPopupMenu(colonyUnitOrdersPopup);

        ColonyImprovementPopupMenu colonyImprovementPopup = new ColonyImprovementPopupMenu(model, controller);
        colonyImprovementPopup.addPopupMenuListener(new ColonyImprovementsPopupListener(model));
        colonyDialog.setColonyImprovementsPopupMenu(colonyImprovementPopup);

        colonyDialog.setRenameButtonAction(new DisplayRenameColonyDialogAction(controller, model));
        colonyDialog.setHelpButtonAction(new DisplayHelpContentsAction(controller, "Colony.Screen"));
        colonyDialog.addColonySelectorComboBoxListener(new ColonySelectorComboBoxListener(controller, model));
        colonyDialog.addColonyWorkForceManagemenMouseAdapter(new WorkForceManagementMouseAdapter(controller, model));
        colonyDialog.addAutomanageWaterButtonChangeListener(new AutomanageWaterButtonChangeListener(controller, model));
        colonyDialog.addAutomanageFoodButtonChangeListener(new AutomanageFoodButtonChangeListener(controller, model));
        colonyDialog.addAutoUseFertilizerButtonChangeListener(new AutoUseFertilizerButtonChangeListener(controller, model));
        colonyDialog.addColonyContinuousProductionActionListener(new ContinuousProductionActionListener(controller, model));
        colonyDialog.setBuyProductionAction(new DisplayBuyProductionDialogAction(controller, model));
        colonyDialog.setDisplayQueueManagementDialogButtonAction(new DisplayQueueManagementDialogAction(controller, model, colonyDialog));
        colonyDialog.setColonyResourcesTransferHandler(new ColonyResourceTransferHandler(controller, model));
        colonyDialog.setUnitResourcesTransferHandler(new UnitResourceTransferHandler(controller, model));
        assignHelpKeyStroke(colonyDialog);
        model.addObserver(colonyDialog);
        colonyDialog.display();
    }

    private void assignHelpKeyStroke(ColonyDialog colonyDialog) {
        ActionListener actionListener = new ActionListener() {

            public void actionPerformed(ActionEvent actionEvent) {
                new DisplayHelpContentsAction(controller, "Colony.Screen").actionPerformed(actionEvent);
            }
        };
        KeyStroke stroke = KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0);
        colonyDialog.getRootPane().registerKeyboardAction(actionListener, stroke, JComponent.WHEN_IN_FOCUSED_WINDOW);
    }
}

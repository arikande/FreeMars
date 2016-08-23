package org.freemars.colonydialog;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import org.freemars.colonydialog.unit.SelectorPanel;
import org.freerealm.settlement.improvement.SettlementImprovement;

/**
 *
 * @author arikande
 */
public class ColonyProductionAndImprovementsPanel extends JPanel {

    private ColonyDialogModel model;
    private JPanel currentImprovementsPanel;
    private JScrollPane buildingsListScrollPane;
    private SelectorPanel<SettlementImprovement> improvementsPanel;
    private JLabel buildingUpkeepLabel;
    private ColonyProductionManagementPanel productionManagementPanel;
    private JPopupMenu colonyImprovementsPopupMenu;

    public ColonyProductionAndImprovementsPanel() {
        super(new BorderLayout(0, 20));
        setBorder(BorderFactory.createTitledBorder("Colony improvements"));
        add(getCurrentImprovementsPanel(), BorderLayout.CENTER);
        add(getProductionManagementPanel(), BorderLayout.PAGE_END);
    }

    public void updateAll() {
        updateColonyImprovements();
        updateCurrentProduction();
        updateProductionOptions();
    }

    public void updateColonyImprovements() {
        if (model != null) {
            getImprovementsPanel().removeAll();
            int addedImprovementCount = 0;
            Iterator<SettlementImprovement> buildingIterator = model.getColony().getImprovementsIterator();
            while (buildingIterator.hasNext()) {
                SettlementImprovement improvement = buildingIterator.next();
                ColonyImprovementToggleButton colonyImprovementToggleButton = new ColonyImprovementToggleButton(model, improvement);
                colonyImprovementToggleButton.addActionListener(new ActionListener() {

                    public void actionPerformed(ActionEvent e) {
                        ColonyImprovementToggleButton toggleButton = (ColonyImprovementToggleButton) e.getSource();
                        model.setSelectedImprovement(toggleButton.getCityImprovement());
                        colonyImprovementsPopupMenu.show(toggleButton, toggleButton.getWidth(), 10);
                    }
                });
                getImprovementsPanel().addSelectable(improvement, colonyImprovementToggleButton);
                addedImprovementCount = addedImprovementCount + 1;
            }
            if (addedImprovementCount < 4) {
                getBuildingsListScrollPane().setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
                for (int i = addedImprovementCount; i < 4; i++) {
                    getImprovementsPanel().add(new JLabel());
                }
            } else {
                getBuildingsListScrollPane().setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
            }
            String buildingUpkeepString = "Total upkeep : " + model.getColony().getImprovementUpkeep();
            getBuildingUpkeepLabel().setText(buildingUpkeepString);
        }
    }

    public void updateCurrentProduction() {
        getProductionManagementPanel().update();
    }

    public void updateProductionOptions() {
    }

    public void setModel(ColonyDialogModel model) {
        this.model = model;
        getProductionManagementPanel().setModel(model);
    }

    public void addColonyContinuousProductionActionListener(ActionListener actionListener) {
        getProductionManagementPanel().addColonyContinuousProductionActionListener(actionListener);
    }

    public void setBuyProductionAction(Action action) {
        getProductionManagementPanel().setBuyProductionAction(action);
    }

    public void setDisplayQueueManagementDialogButtonAction(Action action) {
        getProductionManagementPanel().setDisplayQueueManagementDialogButtonAction(action);
    }

    public void setColonyImprovementsPopupMenu(JPopupMenu colonyImprovementsPopupMenu) {
        this.colonyImprovementsPopupMenu = colonyImprovementsPopupMenu;
    }

    private JPanel getCurrentImprovementsPanel() {
        if (currentImprovementsPanel == null) {
            currentImprovementsPanel = new JPanel(new BorderLayout());
            currentImprovementsPanel.add(getBuildingsListScrollPane(), BorderLayout.CENTER);
            currentImprovementsPanel.add(getBuildingUpkeepLabel(), BorderLayout.PAGE_END);
        }
        return currentImprovementsPanel;
    }

    private JScrollPane getBuildingsListScrollPane() {
        if (buildingsListScrollPane == null) {
            buildingsListScrollPane = new JScrollPane(getImprovementsPanel());
        }
        return buildingsListScrollPane;
    }

    private SelectorPanel<SettlementImprovement> getImprovementsPanel() {
        if (improvementsPanel == null) {
            improvementsPanel = new SelectorPanel<SettlementImprovement>(new GridLayout(0, 3));
        }
        return improvementsPanel;
    }

    private JLabel getBuildingUpkeepLabel() {
        if (buildingUpkeepLabel == null) {
            buildingUpkeepLabel = new JLabel();
        }
        return buildingUpkeepLabel;
    }

    private ColonyProductionManagementPanel getProductionManagementPanel() {
        if (productionManagementPanel == null) {
            productionManagementPanel = new ColonyProductionManagementPanel();
            productionManagementPanel.setBorder(BorderFactory.createTitledBorder("Production"));
        }
        return productionManagementPanel;
    }
}

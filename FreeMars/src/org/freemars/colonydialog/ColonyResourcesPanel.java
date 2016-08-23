package org.freemars.colonydialog;

import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import org.freemars.colonydialog.controller.ResourceTransferHandler;

/**
 *
 * @author Deniz ARIKAN
 */
public class ColonyResourcesPanel extends JPanel {

    private JScrollPane colonyResourcesScrollPane;
    private ColonyResourcesTable colonyResourcesTable;
    private ColonyResourcesTableModel colonyResourcesTableModel;

    public ColonyResourcesPanel() {
        super(new GridLayout());
        setBorder(BorderFactory.createTitledBorder("Colony resources"));
        initializeWidgets();
    }

    public void setModel(ColonyDialogModel model) {
        getColonyResourcesTableModel().setModel(model);
    }

    public void setResourceTransferHandler(ResourceTransferHandler transferHandler) {
        getColonyResourcesTable().setTransferHandler(transferHandler);
    }

    public void refresh() {
        getColonyResourcesTableModel().refresh();
    }

    private void initializeWidgets() {
        add(getCityResourcesScrollPane());
    }

    private JScrollPane getCityResourcesScrollPane() {
        if (colonyResourcesScrollPane == null) {
            colonyResourcesScrollPane = new JScrollPane(getColonyResourcesTable());
        }
        return colonyResourcesScrollPane;
    }

    private ColonyResourcesTable getColonyResourcesTable() {
        if (colonyResourcesTable == null) {
            colonyResourcesTable = new ColonyResourcesTable(getColonyResourcesTableModel());
        }
        return colonyResourcesTable;
    }

    private ColonyResourcesTableModel getColonyResourcesTableModel() {
        if (colonyResourcesTableModel == null) {
            colonyResourcesTableModel = new ColonyResourcesTableModel();
        }
        return colonyResourcesTableModel;
    }
}

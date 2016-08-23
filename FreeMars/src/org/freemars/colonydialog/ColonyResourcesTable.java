package org.freemars.colonydialog;

import java.awt.Cursor;
import java.awt.event.MouseEvent;
import javax.swing.JTable;
import javax.swing.event.MouseInputAdapter;

/**
 *
 * @author Deniz ARIKAN
 */
public class ColonyResourcesTable extends JTable {

    private final ColonyResourcesTableModel colonyResourcesTableModel;

    public ColonyResourcesTable(ColonyResourcesTableModel colonyResourcesTableModel) {
        super(colonyResourcesTableModel);
        this.colonyResourcesTableModel = colonyResourcesTableModel;
        setRowHeight(36);
        setShowGrid(false);
        setShowHorizontalLines(true);
        setDragEnabled(true);
        setShowVerticalLines(false);
        setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        getTableHeader().setReorderingAllowed(false);
        getColumnModel().getColumn(0).setPreferredWidth(140);
        getColumnModel().getColumn(2).setPreferredWidth(50);
        getTableHeader().setReorderingAllowed(false);
        for (int i = 0; i < getColumnCount(); i++) {
            getColumnModel().getColumn(i).setCellRenderer(new ColonyResourcesTableRenderer());
        }
        addMouseMotionListener(new MouseInputAdapter() {

            @Override
            public void mouseMoved(MouseEvent e) {
                requestFocus();
                int row = rowAtPoint(e.getPoint());
                getSelectionModel().setSelectionInterval(row, row);
                setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
        });
    }

    public int getTotalCapacity(int rowIndex) {
        return colonyResourcesTableModel.getTotalCapacity(rowIndex);
    }
}

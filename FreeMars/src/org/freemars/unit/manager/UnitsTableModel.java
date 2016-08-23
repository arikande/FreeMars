package org.freemars.unit.manager;

import java.util.ArrayList;
import java.util.List;
import javax.swing.Icon;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Deniz ARIKAN
 */
public class UnitsTableModel extends AbstractTableModel {

    private final List<UnitsTableRow> rows;
    private final String[] columnNames = {"", "Name", "Type", "Order", "Location", "Coordinate"};

    public UnitsTableModel() {
        rows = new ArrayList<UnitsTableRow>();
    }

    public int getRowCount() {
        return rows.size();
    }

    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Class getColumnClass(int column) {
        if (column == 0) {
            return Icon.class;
        }
        return Object.class;
    }

    @Override
    public String getColumnName(int col) {
        return columnNames[col];
    }

    public Object getValueAt(int row, int column) {
        Object returnValue = null;
        UnitsTableRow unitManagerDialogTableRow = rows.get(row);
        switch (column) {
            case 0:
                returnValue = unitManagerDialogTableRow.getUnitIcon();
                break;
            case 1:
                returnValue = unitManagerDialogTableRow.getUnitName();
                break;
            case 2:
                returnValue = unitManagerDialogTableRow.getUnitType();
                break;
            case 3:
                returnValue = unitManagerDialogTableRow.getUnitOrder();
                break;
            case 4:
                returnValue = unitManagerDialogTableRow.getUnitLocation();
                break;
            case 5:
                returnValue = unitManagerDialogTableRow.getUnitCoordinate();
                break;
        }
        return returnValue;
    }

    public void clearRows() {
        rows.clear();
    }

    public void addRow(UnitsTableRow unitManagerDialogTableRow) {
        rows.add(unitManagerDialogTableRow);
    }

    public int getUnitIdAtRow(int row) {
        return rows.get(row).getUnitId();
    }
}

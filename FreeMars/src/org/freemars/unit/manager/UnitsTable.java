package org.freemars.unit.manager;

import java.awt.Color;
import java.awt.Component;
import java.awt.Point;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author Deniz ARIKAN
 */
public class UnitsTable extends JTable {

    public UnitsTable(UnitsTableModel unitsTableModel) {
        setModel(unitsTableModel);
        setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        setShowGrid(false);
        setShowHorizontalLines(true);
        setShowVerticalLines(false);
        setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
        getTableHeader().setReorderingAllowed(false);
        getColumnModel().getColumn(0).setPreferredWidth(75);
        getColumnModel().getColumn(1).setPreferredWidth(200);
        getColumnModel().getColumn(2).setPreferredWidth(200);
        getColumnModel().getColumn(3).setPreferredWidth(200);
        getColumnModel().getColumn(4).setPreferredWidth(200);
        getColumnModel().getColumn(5).setPreferredWidth(75);
    }

    @Override
    public int getRowHeight() {
        return 45;
    }

    @Override
    public TableCellRenderer getCellRenderer(int row, int column) {
        Component component = (Component) super.getCellRenderer(row, column);
        if (row % 2 == 1) {
            component.setBackground(new Color(210, 210, 210));
        } else {
            component.setBackground(Color.WHITE);
        }
        return (TableCellRenderer) component;
    }

    public int getUnitIdAtPoint(Point point) {
        int row = rowAtPoint(point);
        return ((UnitsTableModel) getModel()).getUnitIdAtRow(row);
    }
}

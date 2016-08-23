package org.freemars.diplomacy;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author Deniz ARIKAN
 */
public class DiplomacyTable extends JTable {

    public DiplomacyTable() {
        setShowGrid(false);
        setShowHorizontalLines(true);
        setShowVerticalLines(false);
        getTableHeader().setReorderingAllowed(false);
        setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        setColumnSelectionAllowed(false);
    }

    @Override
    public int getRowHeight() {
        return 45;
    }

    @Override
    public TableCellRenderer getCellRenderer(int row, int column) {
        Component component = (Component) super.getCellRenderer(row, column);
        if (row % 2 == 1) {
            component.setBackground(new Color(240, 240, 240));
        } else {
            component.setBackground(Color.WHITE);
        }
        return (TableCellRenderer) component;
    }
}

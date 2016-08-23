package org.freemars.earth.ui;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import org.freemars.earth.Earth;
import org.freerealm.resource.Resource;

/**
 *
 * @author Deniz ARIKAN
 */
public class EarthPricesTableModel extends AbstractTableModel {

    private final Earth earthFlightModel;
    private final List<List> tableData;
    String[] columnNames = {"Resource", "Buy", "Sell"};

    public EarthPricesTableModel(Earth earthFlightModel) {
        this.earthFlightModel = earthFlightModel;
        tableData = new ArrayList<List>();
    }

    @Override
    public String getColumnName(int col) {
        return columnNames[col];
    }

    public int getRowCount() {
        return tableData.size();
    }

    public int getColumnCount() {
        return columnNames.length;
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        List rowData = (List) tableData.get(rowIndex);
        return rowData.get(columnIndex);
    }

    public void refresh() {
        tableData.clear();
        Iterator resourceIterator = earthFlightModel.getRealm().getResourceManager().getResourcesIterator();
        while (resourceIterator.hasNext()) {
            Resource resource = (Resource) resourceIterator.next();
            List<String> rowData = new ArrayList<String>();
            rowData.add(resource.getName());
            rowData.add(String.valueOf(earthFlightModel.getEarthSellsAtPrice(resource)));
            rowData.add(String.valueOf(earthFlightModel.getEarthBuysAtPrice(resource)));
            tableData.add(rowData);
        }
        fireTableDataChanged();
    }
}

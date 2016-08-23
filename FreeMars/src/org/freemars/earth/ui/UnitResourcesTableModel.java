package org.freemars.earth.ui;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import org.freerealm.resource.Resource;
import org.freerealm.resource.ResourceStorer;

/**
 *
 * @author Deniz ARIKAN
 */
public class UnitResourcesTableModel extends AbstractTableModel {

    private ResourceStorer resourceStorer;
    private List<List> tableData = null;
    String[] columnNames = {"Resource", "Quantity"};

    public UnitResourcesTableModel() {
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
        if (getResourceStorer() != null) {
            Iterator<Resource> resourceIterator = getResourceStorer().getContainedResourcesIterator();
            while (resourceIterator.hasNext()) {
                Resource resource = resourceIterator.next();
                List<String> rowData = new ArrayList<String>();
                rowData.add(resource.toString());
                rowData.add(String.valueOf(getResourceStorer().getResourceQuantity(resource)));
                tableData.add(rowData);
            }
        }
        fireTableDataChanged();
    }

    public ResourceStorer getResourceStorer() {
        return resourceStorer;
    }

    public void setResourceStorer(ResourceStorer resourceStorer) {
        this.resourceStorer = resourceStorer;
        refresh();
    }
}

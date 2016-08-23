package org.freemars.trade;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import org.freemars.model.FreeMarsModel;
import org.freemars.player.FreeMarsPlayer;
import org.freemars.player.ResourceTradeData;
import org.freerealm.resource.Resource;

/**
 *
 * @author Deniz ARIKAN
 */
public class TradeTableModel extends AbstractTableModel {

    private final FreeMarsPlayer freeMarsPlayer;
    private final List<String> fields;
    private final List<Resource> resources;

    public TradeTableModel(FreeMarsModel freeMarsModel, FreeMarsPlayer freeMarsPlayer) {
        this.freeMarsPlayer = freeMarsPlayer;
        fields = new ArrayList<String>();
        resources = new ArrayList<Resource>();
        fields.add("Quantity traded");
        fields.add("Income before taxes");
        fields.add("Tax paid");
        fields.add("Net income");
        Iterator<Resource> iterator = freeMarsModel.getRealm().getResourceManager().getResourcesIterator();
        while (iterator.hasNext()) {
            resources.add(iterator.next());
        }
    }

    @Override
    public String getColumnName(int col) {
        if (col == 0) {
            return "";
        } else {
            return resources.get(col - 1).getName();
        }
    }

    @Override
    public Class getColumnClass(int c) {
        if (c == 0) {
            return String.class;
        } else {
            return Integer.class;
        }
    }

    public int getRowCount() {
        return fields.size();
    }

    public int getColumnCount() {
        return resources.size() + 1;
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        if (columnIndex == 0) {
            return fields.get(rowIndex);
        } else {
            ResourceTradeData resourceTradeData = freeMarsPlayer.getResourceTradeData(resources.get(columnIndex - 1).getId());
            if (rowIndex == 0) {
                return new DecimalFormat().format(resourceTradeData.getQuantityExported() - resourceTradeData.getQuantityImported());
            } else if (rowIndex == 1) {
                return new DecimalFormat().format(resourceTradeData.getIncomeBeforeTaxes());
            } else if (rowIndex == 2) {
                return new DecimalFormat().format(resourceTradeData.getTax());
            } else if (rowIndex == 3) {
                return new DecimalFormat().format(resourceTradeData.getNetIncome());
            } else {
                return "";
            }
        }
    }
}

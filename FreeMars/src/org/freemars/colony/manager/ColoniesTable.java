package org.freemars.colony.manager;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableCellRenderer;
import org.freemars.colony.FreeMarsColony;
import org.freerealm.Realm;
import org.freerealm.player.Player;

/**
 *
 * @author Deniz ARIKAN
 */
public class ColoniesTable extends JTable {

    private final ColoniesTableModel coloniesTableModel;

    public ColoniesTable(Realm realm, Player player) {
        getTableHeader().setReorderingAllowed(false);
        setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        coloniesTableModel = new ColoniesTableModel(realm, player);
        this.setModel(coloniesTableModel);
        getColumnModel().getColumn(ColoniesTableModel.WATER_COLUMN_INDEX).setCellRenderer(new ColonyRequiredResourceRenderer());
        getColumnModel().getColumn(ColoniesTableModel.FOOD_COLUMN_INDEX).setCellRenderer(new ColonyRequiredResourceRenderer());
        getColumnModel().getColumn(ColoniesTableModel.FERTILIZER_COLUMN_INDEX).setCellRenderer(new ColonyFertilizerRenderer());
        getColumnModel().getColumn(ColoniesTableModel.AUTO_MANAGED_RESOURCES_COLUMN_INDEX).setCellRenderer(new ColonyAutoManagedResourcesRenderer());
        getColumnModel().getColumn(ColoniesTableModel.CURRENT_PRODUCTION_COLUMN_INDEX).setCellRenderer(new ColonyCurrentProductionRenderer());
        getColumnModel().getColumn(ColoniesTableModel.MANUFACTURING_INFO_COLUMN_INDEX).setCellRenderer(new ColonyImprovementsManufacturingDataRenderer());
        getColumnModel().getColumn(ColoniesTableModel.HYDROGEN_AMOUNT_COLUMN_INDEX).setCellRenderer(new ColonyResourceAmountRenderer());
        getColumnModel().getColumn(ColoniesTableModel.LUMBER_AMOUNT_COLUMN_INDEX).setCellRenderer(new ColonyResourceAmountRenderer());
        getColumnModel().getColumn(ColoniesTableModel.IRON_AMOUNT_COLUMN_INDEX).setCellRenderer(new ColonyResourceAmountRenderer());
        getColumnModel().getColumn(ColoniesTableModel.STEEL_AMOUNT_COLUMN_INDEX).setCellRenderer(new ColonyResourceAmountRenderer());
        getColumnModel().getColumn(ColoniesTableModel.SILICA_AMOUNT_COLUMN_INDEX).setCellRenderer(new ColonyResourceAmountRenderer());
        getColumnModel().getColumn(ColoniesTableModel.GLASS_AMOUNT_COLUMN_INDEX).setCellRenderer(new ColonyResourceAmountRenderer());
        getColumnModel().getColumn(ColoniesTableModel.MINERALS_AMOUNT_COLUMN_INDEX).setCellRenderer(new ColonyResourceAmountRenderer());
        getColumnModel().getColumn(ColoniesTableModel.CHEMICALS_AMOUNT_COLUMN_INDEX).setCellRenderer(new ColonyResourceAmountRenderer());
        getColumnModel().getColumn(ColoniesTableModel.MAGNESIUM_AMOUNT_COLUMN_INDEX).setCellRenderer(new ColonyResourceAmountRenderer());
        getColumnModel().getColumn(ColoniesTableModel.GAUSS_RIFLE_AMOUNT_COLUMN_INDEX).setCellRenderer(new ColonyResourceAmountRenderer());
        getColumnModel().getColumn(ColoniesTableModel.IMPROVEMENTS_COLUMN_INDEX).setCellRenderer(new ColonyImprovementsRenderer());
        getColumnModel().getColumn(ColoniesTableModel.DEFENSES_COLUMN_INDEX).setCellRenderer(new ColonyImprovementsRenderer());
        getColumnModel().getColumn(ColoniesTableModel.MILITARY_FACILITIES_COLUMN_INDEX).setCellRenderer(new ColonyImprovementsRenderer());
        getColumnModel().getColumn(ColoniesTableModel.UNITS_COLUMN_INDEX).setCellRenderer(new ColonyUnitsRenderer());
    }

    public FreeMarsColony getSelectedColony() {
        return coloniesTableModel.getColonyAtRow(getSelectedRow());
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

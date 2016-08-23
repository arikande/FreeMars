package org.freemars.colony.manager;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import org.freemars.ui.image.FreeMarsImageManager;
import org.freerealm.settlement.SettlementBuildable;

/**
 *
 * @author Deniz ARIKAN
 */
public class ColonyCurrentProductionRenderer extends JPanel implements TableCellRenderer {

    public ColonyCurrentProductionRenderer() {
        super(new BorderLayout());
    }

    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        removeAll();
        JLabel currentProductionLabel = new JLabel();
        if (value != null) {
            SettlementBuildable settlementBuildable = (SettlementBuildable) value;
            currentProductionLabel.setFont(table.getFont());
            currentProductionLabel.setText(settlementBuildable.getName());
            Image currentProductionImage = FreeMarsImageManager.getImage(settlementBuildable);
            currentProductionImage = FreeMarsImageManager.createResizedCopy(currentProductionImage, 34, -1, false, this);
            currentProductionLabel.setIcon(new ImageIcon(currentProductionImage));
        }
        if (isSelected) {
            setBackground(new Color(212, 123, 123));
        }
        add(currentProductionLabel, BorderLayout.CENTER);
        return this;
    }
}

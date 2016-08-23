package org.freemars.diplomacy;

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
import org.freerealm.nation.Nation;

/**
 *
 * @author Deniz ARIKAN
 */
public class NationNameAndFlagRenderer extends JPanel implements TableCellRenderer {

    public NationNameAndFlagRenderer() {
        super(new BorderLayout());
    }

    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        removeAll();
        Nation nation = (Nation) value;
        JLabel nationNameLabel = new JLabel();
        nationNameLabel.setText(nation.getAdjective());
        Image nationFlagImage = FreeMarsImageManager.getImage(nation, false, -1, 16);
        nationNameLabel.setIcon(new ImageIcon(nationFlagImage));
        nationNameLabel.setFont(table.getFont());
        add(nationNameLabel, BorderLayout.CENTER);
        if (isSelected) {
            setBackground(new Color(212, 123, 123));
        }
        return this;
    }

}

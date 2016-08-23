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

/**
 *
 * @author Deniz ARIKAN
 */
public class ColonyRequiredResourceRenderer extends JPanel implements TableCellRenderer {

    public ColonyRequiredResourceRenderer() {
        super(new BorderLayout());
    }

    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        removeAll();
        int[] resourceValues = (int[]) value;
        String resourceValuesLabelText = String.valueOf(resourceValues[0]);
        if (resourceValues[1] > 0) {
            resourceValuesLabelText = resourceValuesLabelText + "(+" + resourceValues[1] + ")";
        } else if (resourceValues[1] < 0) {
            resourceValuesLabelText = resourceValuesLabelText + "(" + resourceValues[1] + ")";
        }
        JLabel resourceValuesLabel = new JLabel();
        resourceValuesLabel.setFont(table.getFont());
        resourceValuesLabel.setText(resourceValuesLabelText);
        if ((resourceValues[0] == 0) || (resourceValues[0] + resourceValues[1]) < 0) {
            Image errorImage = FreeMarsImageManager.getImage("ERROR");
            resourceValuesLabel.setIcon(new ImageIcon(errorImage));
        }
        add(resourceValuesLabel, BorderLayout.CENTER);
        if (isSelected) {
            setBackground(new Color(212, 123, 123));
        }
        return this;
    }
}

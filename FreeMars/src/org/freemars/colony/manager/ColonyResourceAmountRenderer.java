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
public class ColonyResourceAmountRenderer extends JPanel implements TableCellRenderer {

    public ColonyResourceAmountRenderer() {
        super(new BorderLayout());
    }

    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        removeAll();
        int[] resourceAmountValues = (int[]) value;
        String resourceValuesLabelText = String.valueOf(resourceAmountValues[0]);
        if (resourceAmountValues[1] > 0) {
            resourceValuesLabelText = resourceValuesLabelText + "(+" + resourceAmountValues[1] + ")";
        }
        JLabel resourceAmountValuesLabel = new JLabel();
        resourceAmountValuesLabel.setFont(table.getFont());
        resourceAmountValuesLabel.setText(resourceValuesLabelText);

        if (resourceAmountValues[2] == 0) {
            Image errorImage = FreeMarsImageManager.getImage("ERROR");
            resourceAmountValuesLabel.setIcon(new ImageIcon(errorImage));
        } else if (resourceAmountValues[1] > 0 && resourceAmountValues[2] / resourceAmountValues[1] < 1) {
            Image warningImage = FreeMarsImageManager.getImage("WARNING");
            resourceAmountValuesLabel.setIcon(new ImageIcon(warningImage));
        }
        if (isSelected) {
            setBackground(new Color(212, 123, 123));
        }
        add(resourceAmountValuesLabel, BorderLayout.CENTER);
        return this;
    }
}

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
public class ColonyFertilizerRenderer extends JPanel implements TableCellRenderer {

    public ColonyFertilizerRenderer() {
        super(new BorderLayout());

    }

    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        removeAll();
        int[] fertilizerValues = (int[]) value;
        JLabel fertilizerLabel = new JLabel();
        fertilizerLabel.setFont(table.getFont());
        String fertilizerLabelText = String.valueOf(fertilizerValues[0]);
        if (fertilizerValues[1] > 0) {
            fertilizerLabelText = fertilizerValues[0] + "(-" + fertilizerValues[1] + ")";
        }
        fertilizerLabel.setText(fertilizerLabelText);
        if (fertilizerValues[0] == 0) {
            Image warningImage = FreeMarsImageManager.getImage("ERROR");
            fertilizerLabel.setIcon(new ImageIcon(warningImage));
        } else if (fertilizerValues[0] > 0 && fertilizerValues[1] > 0 && fertilizerValues[0] / fertilizerValues[1] < 2) {
            Image warningImage = FreeMarsImageManager.getImage("WARNING");
            fertilizerLabel.setIcon(new ImageIcon(warningImage));
        }
        add(fertilizerLabel, BorderLayout.CENTER);
        if (isSelected) {
            setBackground(new Color(212, 123, 123));
        }
        return this;
    }
}

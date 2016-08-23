package org.freemars.colony.manager;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import org.freemars.ui.image.FreeMarsImageManager;
import org.freerealm.settlement.improvement.SettlementImprovement;

/**
 *
 * @author Deniz ARIKAN
 */
public class ColonyImprovementsRenderer extends JPanel implements TableCellRenderer {

    public ColonyImprovementsRenderer() {
        super(new FlowLayout(FlowLayout.LEFT, 3, 0));
    }

    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        removeAll();
        int settlementImageWidth = 34;
        List<SettlementImprovement> improvements = (ArrayList<SettlementImprovement>) value;
        if (improvements.size() > 16) {
            settlementImageWidth = 26;
        }
        String toolTipText = "<html><br>";
        for (SettlementImprovement settlementImprovement : improvements) {
            Image settlementImage = FreeMarsImageManager.getImage(settlementImprovement);
            settlementImage = FreeMarsImageManager.createResizedCopy(settlementImage, settlementImageWidth, -1, false, this);
            JLabel imageLabel = new JLabel(new ImageIcon(settlementImage));
            imageLabel.setPreferredSize(new Dimension(settlementImageWidth, 44));
            toolTipText = toolTipText + "&nbsp;- " + (settlementImprovement.getType().getName()) + "&nbsp;&nbsp;&nbsp;&nbsp;<br>";
            if (row % 2 == 1) {
                imageLabel.setBackground(new Color(210, 210, 210));
            } else {
                imageLabel.setBackground(Color.WHITE);
            }
            add(imageLabel);
        }
        if (isSelected) {
            setBackground(new Color(212, 123, 123));
        }
        toolTipText = toolTipText + "<br></html>";
        setToolTipText(toolTipText);
        return this;
    }
}

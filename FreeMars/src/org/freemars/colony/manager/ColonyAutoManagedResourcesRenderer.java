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
import org.freerealm.resource.Resource;

/**
 *
 * @author Deniz ARIKAN
 */
public class ColonyAutoManagedResourcesRenderer extends JPanel implements TableCellRenderer {

    public ColonyAutoManagedResourcesRenderer() {
        super(new FlowLayout(FlowLayout.LEFT, 3, 0));

    }

    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        removeAll();
        List<Resource> autoManagedResources = (ArrayList<Resource>) value;
        for (Resource resource : autoManagedResources) {
            Image resourceImage = FreeMarsImageManager.getImage(resource);
            resourceImage = FreeMarsImageManager.createResizedCopy(resourceImage, 26, -1, false, this);
            JLabel imageLabel = new JLabel(new ImageIcon(resourceImage));
            imageLabel.setPreferredSize(new Dimension(26, 44));
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
        return this;
    }
}

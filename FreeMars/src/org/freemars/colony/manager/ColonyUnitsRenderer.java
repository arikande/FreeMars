package org.freemars.colony.manager;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.util.HashMap;
import java.util.Iterator;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import org.freemars.ui.image.FreeMarsImageManager;
import org.freerealm.unit.FreeRealmUnitType;

/**
 *
 * @author Deniz ARIKAN
 */
public class ColonyUnitsRenderer extends JPanel implements TableCellRenderer {

    public ColonyUnitsRenderer() {
        super(new FlowLayout(FlowLayout.LEFT, 3, 0));

    }

    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        removeAll();
        int unitImageWidth = 34;
        HashMap<FreeRealmUnitType, Integer> unitTypeQuantities = (HashMap<FreeRealmUnitType, Integer>) value;

        Iterator<FreeRealmUnitType> iterator = unitTypeQuantities.keySet().iterator();
        while (iterator.hasNext()) {
            FreeRealmUnitType unitType = iterator.next();
            int count = unitTypeQuantities.get(unitType);
            for (int i = 0; i < count; i++) {
                Image unitImage = FreeMarsImageManager.getImage(unitType);
                unitImage = FreeMarsImageManager.createResizedCopy(unitImage, unitImageWidth, -1, false, this);
                JLabel imageLabel = new JLabel(new ImageIcon(unitImage));
                imageLabel.setPreferredSize(new Dimension(unitImageWidth, 44));
                if (row % 2 == 1) {
                    imageLabel.setBackground(new Color(210, 210, 210));
                } else {
                    imageLabel.setBackground(Color.WHITE);
                }
                add(imageLabel);
            }
        }
        if (isSelected) {
            setBackground(new Color(212, 123, 123));
        }
        return this;
    }
}

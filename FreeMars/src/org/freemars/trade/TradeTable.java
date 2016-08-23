/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.freemars.trade;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import org.freemars.model.FreeMarsModel;
import org.freemars.player.FreeMarsPlayer;
import org.freemars.ui.image.FreeMarsImageManager;
import org.freerealm.resource.Resource;

/**
 *
 * @author Deniz ARIKAN
 */
public class TradeTable extends JTable {

    private final FreeMarsModel freeMarsModel;

    public TradeTable(FreeMarsModel freeMarsModel, FreeMarsPlayer freeMarsPlayer) {
        this.freeMarsModel = freeMarsModel;
        TradeTableModel tradeTableModel = new TradeTableModel(freeMarsModel, freeMarsPlayer);
        this.setModel(tradeTableModel);
        setShowGrid(true);
        setShowHorizontalLines(true);
        setShowVerticalLines(true);
        setRowSelectionAllowed(false);
        setColumnSelectionAllowed(false);
        setCellSelectionEnabled(false);
        getTableHeader().setReorderingAllowed(false);
        getTableHeader().setPreferredSize(new Dimension(40, 40));
        for (int i = 1; i < 17; i++) {
            getColumnModel().getColumn(i).setHeaderRenderer(new ResourceImageRenderer());
        }
        getColumnModel().getColumn(0).setPreferredWidth(140);
    }

    @Override
    public int getRowHeight() {
        return 30;
    }

    class ResourceImageRenderer extends DefaultTableCellRenderer {

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                boolean hasFocus, int row, int column) {
            JLabel rendererLabel = new JLabel();
            rendererLabel.setHorizontalAlignment(SwingConstants.CENTER);
            if (column > 0) {
                Resource resource = freeMarsModel.getRealm().getResourceManager().getResource(column - 1);
                Image resourceImage = FreeMarsImageManager.getImage(resource);
                resourceImage = FreeMarsImageManager.createResizedCopy(resourceImage, 30, 30, false, this);
                rendererLabel.setIcon(new ImageIcon(resourceImage));
                rendererLabel.setToolTipText(resource.getName());
            }
            return rendererLabel;
        }
    }
}

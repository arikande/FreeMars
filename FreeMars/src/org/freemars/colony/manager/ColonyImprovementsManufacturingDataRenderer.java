package org.freemars.colony.manager;

import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Image;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import org.freemars.ui.image.FreeMarsImageManager;
import org.freerealm.resource.Resource;
import org.freerealm.settlement.improvement.SettlementImprovement;
import org.freerealm.settlement.improvement.SettlementImprovementResourceProductionModel;
import org.freerealm.settlement.improvement.SettlementImprovementType;

/**
 *
 * @author Deniz ARIKAN
 */
public class ColonyImprovementsManufacturingDataRenderer extends JPanel implements TableCellRenderer {

    public ColonyImprovementsManufacturingDataRenderer() {
        super(new FlowLayout(FlowLayout.LEFT, 0, 0));

    }

    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        removeAll();
        int settlementImageWidth = 34;
        int resourceImageWidth = 15;
        List<SettlementImprovementResourceProductionModel> manufacturingData = (ArrayList<SettlementImprovementResourceProductionModel>) value;
        for (SettlementImprovementResourceProductionModel settlementImprovementResourceProductionModel : manufacturingData) {
            JPanel settlementImprovementResourceProductionModelRenderPanel = new JPanel();
            settlementImprovementResourceProductionModelRenderPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 1, Color.lightGray));
            settlementImprovementResourceProductionModelRenderPanel.setOpaque(false);
            SettlementImprovement settlementImprovement = settlementImprovementResourceProductionModel.getSettlementImprovement();
            SettlementImprovementType settlementImprovementType = settlementImprovement.getType();
            Image settlementImprovementImage = FreeMarsImageManager.getImage(settlementImprovement);
            settlementImprovementImage = FreeMarsImageManager.createResizedCopy(settlementImprovementImage, settlementImageWidth, -1, false, this);
            settlementImprovementResourceProductionModelRenderPanel.add(new JLabel(new ImageIcon(settlementImprovementImage)));
            int numberOfWorkers = settlementImprovement.getNumberOfWorkers();
            JLabel numberOfWorkersLabel = new JLabel(String.valueOf(numberOfWorkers) + "/" + settlementImprovementType.getMaximumWorkers());
            if (numberOfWorkers == 0) {
                numberOfWorkersLabel.setForeground(Color.red);
            }
            settlementImprovementResourceProductionModelRenderPanel.add(numberOfWorkersLabel);
            settlementImprovementResourceProductionModelRenderPanel.add(new JLabel(":"));
            if (settlementImprovementType.getInputResourceCount() > 0) {
                Iterator<Resource> inputResourcesIterator = settlementImprovement.getType().getInputResourcesIterator();
                while (inputResourcesIterator.hasNext()) {
                    Resource resource = inputResourcesIterator.next();
                    double ratio = settlementImprovementResourceProductionModel.getEstimatedInputRatio(resource);
                    for (int i = 0; i < settlementImprovementType.getInputQuantity(resource); i++) {
                        Image resourceImage = FreeMarsImageManager.getImage(resource);
                        resourceImage = FreeMarsImageManager.createResizedCopy(resourceImage, resourceImageWidth, -1, false, this);
                        if (i > ratio || ratio == 0) {
                            Image resourceNotEnoughImage = FreeMarsImageManager.getImage("RESOURCE_NOT_ENOUGH");
                            resourceImage = FreeMarsImageManager.combineImages(new Image[]{resourceImage, resourceNotEnoughImage}, this);
                        }
                        settlementImprovementResourceProductionModelRenderPanel.add(new JLabel(new ImageIcon(resourceImage)));
                    }
                }
                Image rightArrowImage = FreeMarsImageManager.getImage("RESOURCE_PRODUCTION_RIGHT_ARROW");
                settlementImprovementResourceProductionModelRenderPanel.add(new JLabel(new ImageIcon(rightArrowImage)));
            }
            if (settlementImprovementType.getOutputResourceCount() > 0) {
                Iterator<Resource> outputResourcesIterator = settlementImprovement.getType().getOutputResourcesIterator();
                while (outputResourcesIterator.hasNext()) {
                    Resource resource = outputResourcesIterator.next();
                    Image resourceImage = FreeMarsImageManager.getImage(resource);
                    resourceImage = FreeMarsImageManager.createResizedCopy(resourceImage, resourceImageWidth, -1, false, this);
                    if (settlementImprovementResourceProductionModel.getEstimatedProductionRatio() == 0) {
                        Image resourceNotEnoughImage = FreeMarsImageManager.getImage("RESOURCE_NOT_ENOUGH");
                        resourceImage = FreeMarsImageManager.combineImages(new Image[]{resourceImage, resourceNotEnoughImage}, this);
                    }
                    for (int i = 0; i < settlementImprovementType.getOutputQuantity(resource); i++) {
                        settlementImprovementResourceProductionModelRenderPanel.add(new JLabel(new ImageIcon(resourceImage)));
                    }
                }
            }
            add(settlementImprovementResourceProductionModelRenderPanel);
        }
        if (isSelected) {
            setBackground(new Color(212, 123, 123));
        }
        return this;
    }
}

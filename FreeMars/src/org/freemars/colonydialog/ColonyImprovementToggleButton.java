package org.freemars.colonydialog;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.image.BufferedImage;
import java.util.Iterator;
import javax.swing.AbstractButton;
import javax.swing.ImageIcon;
import javax.swing.JToggleButton;
import org.freemars.ui.image.FreeMarsImageManager;
import org.freerealm.resource.Resource;
import org.freerealm.settlement.improvement.SettlementImprovement;
import org.freerealm.settlement.improvement.SettlementImprovementResourceProductionModel;

/**
 *
 * @author arikande
 */
public class ColonyImprovementToggleButton extends JToggleButton {

    private final ColonyDialogModel colonyDialogModel;
    private final SettlementImprovement colonyImprovement;

    public ColonyImprovementToggleButton(ColonyDialogModel colonyDialogModel, SettlementImprovement colonyImprovement) {
        this.colonyDialogModel = colonyDialogModel;
        this.colonyImprovement = colonyImprovement;
        setMinimumSize(new Dimension(0, 200));
        setPreferredSize(new Dimension(0, 200));
        BufferedImage resizedImprovementImage = FreeMarsImageManager.createResizedCopy(FreeMarsImageManager.getImage(colonyImprovement.getType(), !colonyImprovement.isEnabled()), -1, 70, false, this);
        setIcon(new ImageIcon(resizedImprovementImage));
        setFocusPainted(false);
        setBorderPainted(false);
        setVerticalTextPosition(AbstractButton.BOTTOM);
        setHorizontalTextPosition(AbstractButton.CENTER);
        String text = "<html>" + colonyImprovement.getType().getName().replace(" ", "<br>");
        if (!colonyImprovement.isEnabled()) {
            text = text + "<br>(Inactive)";
        }
        text = text + "</html>";
        setText(text);
        String toolTip = new ColonyImprovementToolTipBuilder(colonyDialogModel.getRealm(), colonyImprovement.getType()).getProductionToolTip();
        if (!toolTip.equals("")) {
            setToolTipText(toolTip);
        }
    }

    public SettlementImprovement getCityImprovement() {
        return colonyImprovement;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        int lastResourceImageAbscissa = 16;
        int resourceImageWidth = 15;
        int resourceImageOrdinate = 33;
        int resourceQuantityOrdinate = 28;
        g2d.setFont(new Font("Arial", 1, 15));
        if (colonyImprovement.getType().getMaximumWorkers() > 0) {
            SettlementImprovementResourceProductionModel settlementImprovementResourceProductionModel = new SettlementImprovementResourceProductionModel(colonyDialogModel.getColony(), colonyImprovement);
            if (colonyImprovement.getType().getInputResourceCount() > 0) {
                Iterator<Resource> inputResourcesIterator = colonyImprovement.getType().getInputResourcesIterator();
                while (inputResourcesIterator.hasNext()) {
                    Resource resource = inputResourcesIterator.next();
                    double ratio = settlementImprovementResourceProductionModel.getEstimatedInputRatio(resource);
                    g2d.setColor(Color.black);
                    settlementImprovementResourceProductionModel.getEstimatedInputRatio(resource);
                    int estimatedInputAmount = settlementImprovementResourceProductionModel.getEstimatedInputAmount(resource);
                    g2d.drawString(String.valueOf(estimatedInputAmount), lastResourceImageAbscissa + 1, resourceQuantityOrdinate);
                    if (estimatedInputAmount == 0) {
                        g2d.setColor(Color.red);
                    } else if (estimatedInputAmount < settlementImprovementResourceProductionModel.getIdealInputAmount(resource)) {
                        g2d.setColor(Color.yellow);
                    } else {
                        g2d.setColor(new Color(40, 150, 40));
                    }
                    g2d.drawString(String.valueOf(settlementImprovementResourceProductionModel.getEstimatedInputAmount(resource)), lastResourceImageAbscissa, resourceQuantityOrdinate - 1);
                    Image resourceImage = FreeMarsImageManager.getImage(resource);
                    resourceImage = FreeMarsImageManager.createResizedCopy(resourceImage, resourceImageWidth, -1, false, this);
                    int inputQuantity = colonyImprovement.getType().getInputQuantity(resource);
                    if (inputQuantity < 3) {
                        for (int i = 0; i < inputQuantity; i++) {
                            g2d.drawImage(resourceImage, lastResourceImageAbscissa, resourceImageOrdinate, this);
                            lastResourceImageAbscissa = lastResourceImageAbscissa + resourceImageWidth + 2;
                            if (i > ratio || ratio == 0) {
                                g2d.setColor(Color.red);
                                Stroke stroke = g2d.getStroke();
                                g2d.setStroke(new BasicStroke(1.5f));
                                g2d.drawLine(lastResourceImageAbscissa - resourceImageWidth, resourceImageOrdinate - 2, lastResourceImageAbscissa - 5, resourceImageOrdinate + resourceImage.getHeight(this));
                                g2d.drawLine(lastResourceImageAbscissa - 5, resourceImageOrdinate - 2, lastResourceImageAbscissa - resourceImageWidth, resourceImageOrdinate + resourceImage.getHeight(this));
                                g2d.setStroke(stroke);
                            }
                        }
                    } else {
                        g2d.setFont(g2d.getFont().deriveFont(14f));
                        g2d.setColor(Color.black);
                        g2d.drawString(String.valueOf(inputQuantity), lastResourceImageAbscissa, resourceImageOrdinate + 11);
                        lastResourceImageAbscissa = lastResourceImageAbscissa + 8;
                        g2d.drawImage(resourceImage, lastResourceImageAbscissa, resourceImageOrdinate, this);
                        lastResourceImageAbscissa = lastResourceImageAbscissa + resourceImageWidth + 4;
                    }
                }
                g2d.setColor(Color.black);
                g2d.drawLine(lastResourceImageAbscissa, resourceImageOrdinate + 6, lastResourceImageAbscissa + 6, resourceImageOrdinate + 6);
                g2d.drawLine(lastResourceImageAbscissa + 4, resourceImageOrdinate + 3, lastResourceImageAbscissa + 6, resourceImageOrdinate + 6);
                g2d.drawLine(lastResourceImageAbscissa + 4, resourceImageOrdinate + 9, lastResourceImageAbscissa + 6, resourceImageOrdinate + 6);
                lastResourceImageAbscissa = lastResourceImageAbscissa + 8;
            }
            Iterator<Resource> outputResourcesIterator = colonyImprovement.getType().getOutputResourcesIterator();
            if (outputResourcesIterator != null) {
                while (outputResourcesIterator.hasNext()) {
                    Resource resource = outputResourcesIterator.next();
                    int estimatedOutputAmount = settlementImprovementResourceProductionModel.getEstimatedOutputAmount(resource);
                    g2d.drawString(String.valueOf(estimatedOutputAmount), lastResourceImageAbscissa + 1, resourceQuantityOrdinate);
                    if (estimatedOutputAmount == 0) {
                        g2d.setColor(Color.red);
                    } else if (estimatedOutputAmount < settlementImprovementResourceProductionModel.getIdealOutputAmount(resource)) {
                        g2d.setColor(Color.yellow);
                    } else {
                        g2d.setColor(new Color(40, 150, 40));
                    }
                    g2d.drawString(String.valueOf(estimatedOutputAmount), lastResourceImageAbscissa, resourceQuantityOrdinate - 1);
                    Image resourceImage = FreeMarsImageManager.getImage(resource);
                    resourceImage = FreeMarsImageManager.createResizedCopy(resourceImage, resourceImageWidth, -1, false, this);
                    for (int i = 0; i < colonyImprovement.getType().getOutputQuantity(resource); i++) {
                        g2d.drawImage(resourceImage, lastResourceImageAbscissa, resourceImageOrdinate, this);
                        lastResourceImageAbscissa = lastResourceImageAbscissa + resourceImageWidth + 2;
                        if (settlementImprovementResourceProductionModel.getEstimatedProductionRatio() == 0) {
                            g2d.setColor(Color.red);
                            Stroke stroke = g2d.getStroke();
                            g2d.setStroke(new BasicStroke(1.5f));
                            g2d.drawLine(lastResourceImageAbscissa - resourceImageWidth, resourceImageOrdinate - 2, lastResourceImageAbscissa - 5, resourceImageOrdinate + resourceImage.getHeight(this));
                            g2d.drawLine(lastResourceImageAbscissa - 5, resourceImageOrdinate - 2, lastResourceImageAbscissa - resourceImageWidth, resourceImageOrdinate + resourceImage.getHeight(this));
                            g2d.setStroke(stroke);
                        }
                    }
                }
            }
            int numberOfWorkers = colonyImprovement.getNumberOfWorkers();
            g2d.setColor(Color.black);
            g2d.drawString(String.valueOf(numberOfWorkers) + " colonists", 20, getHeight() - 30);
            if (numberOfWorkers == 0) {
                g2d.setColor(Color.red);
            } else if (numberOfWorkers < colonyImprovement.getType().getMaximumWorkers()) {
                g2d.setColor(Color.yellow);
            } else {
                g2d.setColor(new Color(40, 150, 40));
            }
            g2d.drawString(String.valueOf(numberOfWorkers) + " colonists", 19, getHeight() - 31);
        }
    }
}

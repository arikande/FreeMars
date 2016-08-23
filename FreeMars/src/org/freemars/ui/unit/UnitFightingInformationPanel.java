package org.freemars.ui.unit;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Image;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.freemars.ui.image.ImagePanel;

/**
 *
 * @author Deniz ARIKAN
 */
public class UnitFightingInformationPanel extends JPanel {

    private JPanel pageStartPanel;
    private JPanel centerPanel;
    private JPanel pageEndPanel;
    private JLabel positionLabel;
    private JLabel nameLabel;
    private ImagePanel imagePanel;
    private JLabel fightingPointsLabel;
    private JLabel terrainModifierLabel;
    private JLabel effectivePointsLabel;
    private JLabel toWinPercentLabel;

    public UnitFightingInformationPanel() {
        super(new BorderLayout());
        add(getPageStartPanel(), BorderLayout.PAGE_START);
        add(getCenterPanel(), BorderLayout.CENTER);
        add(getPageEndPanel(), BorderLayout.PAGE_END);
    }

    public void setPosition(String position) {
        getPositionLabel().setText(position);
    }

    public void setUnitName(String name) {
        getNameLabel().setText(name);
    }

    public void setImage(Image image) {
        getImagePanel().setImage(image);
    }

    public void setUnitFightingPoints(String points) {
        getFightingPointsLabel().setText(points);
    }

    public void setTerrainModifierLabelText(String text) {
        getTerrainModifierLabel().setText(text);
    }

    public void setEffectivePointsLabelText(String text) {
        getEffectivePointsLabel().setText(text);
    }

    public void setToWinPercentLabelText(String text) {
        getToWinPercentLabel().setText(text);
    }

    private JPanel getPageStartPanel() {
        if (pageStartPanel == null) {
            pageStartPanel = new JPanel(new GridLayout(0, 1, 0, 5));
            pageStartPanel.add(getPositionLabel());
            pageStartPanel.add(getNameLabel());
        }
        return pageStartPanel;
    }

    private JPanel getCenterPanel() {
        if (centerPanel == null) {
            centerPanel = new JPanel(new BorderLayout());
            centerPanel.add(getImagePanel(), BorderLayout.CENTER);
        }
        return centerPanel;
    }

    private JPanel getPageEndPanel() {
        if (pageEndPanel == null) {
            pageEndPanel = new JPanel(new GridLayout(0, 1, 0, 5));
            pageEndPanel.add(getFightingPointsLabel());
            pageEndPanel.add(getTerrainModifierLabel());
            pageEndPanel.add(getEffectivePointsLabel());
            pageEndPanel.add(getToWinPercentLabel());
        }
        return pageEndPanel;
    }

    private JLabel getPositionLabel() {
        if (positionLabel == null) {
            positionLabel = new JLabel();
            positionLabel.setFont(positionLabel.getFont().deriveFont(18f));
        }
        return positionLabel;
    }

    private JLabel getNameLabel() {
        if (nameLabel == null) {
            nameLabel = new JLabel();
        }
        return nameLabel;
    }

    private ImagePanel getImagePanel() {
        if (imagePanel == null) {
            imagePanel = new ImagePanel();
            imagePanel.setImageHorizontalAlignment(ImagePanel.LEFT_ALIGNMENT);
            imagePanel.setImageVerticalAlignment(ImagePanel.CENTER_ALIGNMENT);
        }
        return imagePanel;
    }

    private JLabel getFightingPointsLabel() {
        if (fightingPointsLabel == null) {
            fightingPointsLabel = new JLabel();
        }
        return fightingPointsLabel;
    }

    private JLabel getTerrainModifierLabel() {
        if (terrainModifierLabel == null) {
            terrainModifierLabel = new JLabel();
        }
        return terrainModifierLabel;
    }

    private JLabel getEffectivePointsLabel() {
        if (effectivePointsLabel == null) {
            effectivePointsLabel = new JLabel();
        }
        return effectivePointsLabel;
    }

    private JLabel getToWinPercentLabel() {
        if (toWinPercentLabel == null) {
            toWinPercentLabel = new JLabel();
        }
        return toWinPercentLabel;
    }
}

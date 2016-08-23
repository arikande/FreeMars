package org.freemars.colonydialog;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ItemListener;
import java.io.InputStream;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.event.ChangeListener;
import org.freemars.ui.image.FreeMarsImageManager;
import org.freerealm.settlement.Settlement;

/**
 *
 * @author Deniz ARIKAN
 */
public class ColonyDialogHeaderPanel extends JPanel {

    private JPanel namePanel;
    private JPanel summaryPanel;
    private JPanel lineEndPanel;
    private JPanel automanagedResourcesPanel;
    private JButton previousColonyButton;
    private JPanel colonySelectorPanel;
    private JButton nextColonyButton;
    private ColonySelectorComboBox colonySelectorComboBox;
    private JButton renameButton;
    private JLabel populationValueLabel;
    private JLabel efficiencyValueLabel;
    private JToggleButton automanageWaterToggleButton;
    private JToggleButton automanageFoodToggleButton;
    private JToggleButton autoUseFertilizerToggleButton;
    private JButton helpButton;

    public ColonyDialogHeaderPanel() {
        super(new GridLayout(1, 3, 10, 0));
        initializeWidgets();
    }

    public void repaintColonySelectorComboBox() {
        getColonySelectorComboBox().revalidate();
        getColonySelectorComboBox().repaint();
    }

    public void addColonySelectorComboBoxListener(ItemListener itemListener) {
        getColonySelectorComboBox().addItemListener(itemListener);
    }

    public void setPreviousColonyButtonEnabled(boolean enabled) {
        getPreviousColonyButton().setEnabled(enabled);
    }

    public void setPreviousColonyButtonAction(Action action) {
        getPreviousColonyButton().setAction(action);
        getPreviousColonyButton().setIcon(new ImageIcon(FreeMarsImageManager.getImage("PREVIOUS_COLONY")));
    }

    public void setNextColonyButtonEnabled(boolean enabled) {
        getNextColonyButton().setEnabled(enabled);
    }

    public void setNextColonyButtonAction(Action action) {
        getNextColonyButton().setAction(action);
        getNextColonyButton().setIcon(new ImageIcon(FreeMarsImageManager.getImage("NEXT_COLONY")));
    }

    public void addSelectableColony(Settlement settlement) {
        getColonySelectorComboBox().addColony(settlement);
    }

    public void setSelectedColony(Settlement settlement) {
        getColonySelectorComboBox().setSelectedItem(settlement);
    }

    public void clearColonySelectorComboBox() {
        getColonySelectorComboBox().removeAllItems();
    }

    public void setPopulation(int population) {
        getPopulationValueLabel().setText(String.valueOf(population));
    }

    public void setEfficiency(int efficiency) {
        getEfficiencyValueLabel().setText(String.valueOf(efficiency));
    }

    public void setRenameButtonAction(Action action) {
        getRenameButton().setAction(action);
        getRenameButton().setText("");
        getRenameButton().setIcon(new ImageIcon(FreeMarsImageManager.getImage("COLONY_RENAME")));
    }

    public void setHelpButtonAction(Action action) {
        getHelpButton().setAction(action);
        getHelpButton().setText("");
        Image helpImage = FreeMarsImageManager.getImage("HELP");
        ImageIcon helpImageIcon = new ImageIcon(helpImage);
        getHelpButton().setIcon(helpImageIcon);
    }

    public void addAutomanageWaterButtonChangeListener(ChangeListener changeListener) {
        getAutomanageWaterToggleButton().addChangeListener(changeListener);
    }

    public void addAutomanageFoodButtonChangeListener(ChangeListener changeListener) {
        getAutomanageFoodToggleButton().addChangeListener(changeListener);
    }

    public void addAutoUseFertilizerButtonChangeListener(ChangeListener changeListener) {
        getAutoUseFertilizerToggleButton().addChangeListener(changeListener);
    }

    public void setAutomanageWaterToggleButtonSelected(boolean selected) {
        getAutomanageWaterToggleButton().setSelected(selected);
    }

    public void setAutomanageFoodToggleButtonSelected(boolean selected) {
        getAutomanageFoodToggleButton().setSelected(selected);
    }

    public void setAutoUseFertilizerToggleButtonSelected(boolean selected) {
        getAutoUseFertilizerToggleButton().setSelected(selected);
    }

    private void initializeWidgets() {
        add(getNamePanel());
        add(getSummaryPanel());
        add(getLineEndPanel());
    }

    private JPanel getNamePanel() {
        if (namePanel == null) {
            namePanel = new JPanel(new BorderLayout(10, 0));
            namePanel.add(getColonySelectorPanel(), BorderLayout.CENTER);
            namePanel.add(getRenameButton(), BorderLayout.LINE_END);
        }
        return namePanel;
    }

    private JPanel getSummaryPanel() {
        if (summaryPanel == null) {
            summaryPanel = new JPanel(new GridLayout(2, 2));
            summaryPanel.add(new JLabel("Population"));
            summaryPanel.add(getPopulationValueLabel());
            summaryPanel.add(new JLabel("Efficiency"));
            summaryPanel.add(getEfficiencyValueLabel());
        }
        return summaryPanel;
    }

    private JPanel getLineEndPanel() {
        if (lineEndPanel == null) {
            lineEndPanel = new JPanel(new BorderLayout());
            lineEndPanel.add(getAutomanagedResourcesPanel(), BorderLayout.CENTER);
            lineEndPanel.add(getHelpButton(), BorderLayout.LINE_END);
        }
        return lineEndPanel;
    }

    private JPanel getAutomanagedResourcesPanel() {
        if (automanagedResourcesPanel == null) {
            automanagedResourcesPanel = new JPanel(new GridLayout(0, 3));
            automanagedResourcesPanel.add(getAutomanageWaterToggleButton());
            automanagedResourcesPanel.add(getAutomanageFoodToggleButton());
            automanagedResourcesPanel.add(getAutoUseFertilizerToggleButton());
        }
        return automanagedResourcesPanel;
    }

    private JToggleButton getAutomanageWaterToggleButton() {
        if (automanageWaterToggleButton == null) {
            Image grayScaleImage = FreeMarsImageManager.getImage("RESOURCE_0", true);
            grayScaleImage = FreeMarsImageManager.createResizedCopy(grayScaleImage, -1, 24, false, this);
            ImageIcon grayScaleImageIcon = new ImageIcon(grayScaleImage);
            Image image = FreeMarsImageManager.getImage("RESOURCE_0");
            image = FreeMarsImageManager.createResizedCopy(image, -1, 24, false, this);
            ImageIcon imageIcon = new ImageIcon(image);
            automanageWaterToggleButton = new JToggleButton("Auto", grayScaleImageIcon);
            automanageWaterToggleButton.setSelectedIcon(imageIcon);
        }
        return automanageWaterToggleButton;
    }

    private JToggleButton getAutomanageFoodToggleButton() {
        if (automanageFoodToggleButton == null) {
            Image grayScaleImage = FreeMarsImageManager.getImage("RESOURCE_1", true);
            grayScaleImage = FreeMarsImageManager.createResizedCopy(grayScaleImage, -1, 24, false, this);
            ImageIcon grayScaleImageIcon = new ImageIcon(grayScaleImage);
            Image image = FreeMarsImageManager.getImage("RESOURCE_1");
            image = FreeMarsImageManager.createResizedCopy(image, -1, 24, false, this);
            ImageIcon imageIcon = new ImageIcon(image);
            automanageFoodToggleButton = new JToggleButton("Auto", grayScaleImageIcon);
            automanageFoodToggleButton.setSelectedIcon(imageIcon);
        }
        return automanageFoodToggleButton;
    }

    private JToggleButton getAutoUseFertilizerToggleButton() {
        if (autoUseFertilizerToggleButton == null) {
            Image grayScaleImage = FreeMarsImageManager.getImage("RESOURCE_5", true);
            grayScaleImage = FreeMarsImageManager.createResizedCopy(grayScaleImage, -1, 24, false, this);
            ImageIcon grayScaleImageIcon = new ImageIcon(grayScaleImage);
            Image image = FreeMarsImageManager.getImage("RESOURCE_5");
            image = FreeMarsImageManager.createResizedCopy(image, -1, 24, false, this);
            ImageIcon imageIcon = new ImageIcon(image);
            autoUseFertilizerToggleButton = new JToggleButton("Auto", grayScaleImageIcon);
            autoUseFertilizerToggleButton.setSelectedIcon(imageIcon);
        }
        return autoUseFertilizerToggleButton;
    }

    private JPanel getColonySelectorPanel() {
        if (colonySelectorPanel == null) {
            colonySelectorPanel = new JPanel(new BorderLayout());
            colonySelectorPanel.add(getPreviousColonyButton(), BorderLayout.LINE_START);
            colonySelectorPanel.add(getColonySelectorComboBox(), BorderLayout.CENTER);
            colonySelectorPanel.add(getNextColonyButton(), BorderLayout.LINE_END);
        }
        return colonySelectorPanel;
    }

    private JButton getPreviousColonyButton() {
        if (previousColonyButton == null) {
            previousColonyButton = new JButton("<");
        }
        return previousColonyButton;
    }

    private JButton getNextColonyButton() {
        if (nextColonyButton == null) {
            nextColonyButton = new JButton(">");
        }
        return nextColonyButton;
    }

    private ColonySelectorComboBox getColonySelectorComboBox() {
        if (colonySelectorComboBox == null) {
            colonySelectorComboBox = new ColonySelectorComboBox();
            try {
                InputStream is = this.getClass().getResourceAsStream("/fonts/GUNSHIP2.TTF");
                Font mainMenuWindowFont = Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(Font.PLAIN, 16);
                colonySelectorComboBox.setFont(mainMenuWindowFont);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return colonySelectorComboBox;
    }

    private JButton getRenameButton() {
        if (renameButton == null) {
            renameButton = new JButton("R");
        }
        return renameButton;
    }

    private JButton getHelpButton() {
        if (helpButton == null) {
            helpButton = new JButton();
            Image helpImage = FreeMarsImageManager.getImage("HELP");
            ImageIcon helpImageIcon = new ImageIcon(helpImage);
            helpButton.setIcon(helpImageIcon);
        }
        return helpButton;
    }

    private JLabel getPopulationValueLabel() {
        if (populationValueLabel == null) {
            populationValueLabel = new JLabel();
        }
        return populationValueLabel;
    }

    private JLabel getEfficiencyValueLabel() {
        if (efficiencyValueLabel == null) {
            efficiencyValueLabel = new JLabel();
        }
        return efficiencyValueLabel;
    }
}

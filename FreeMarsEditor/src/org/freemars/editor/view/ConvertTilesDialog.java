package org.freemars.editor.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridLayout;
import javax.swing.AbstractButton;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JToggleButton;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import org.freemars.ui.image.FreeMarsImageManager;
import org.freemars.ui.util.FreeMarsDialog;

/**
 *
 * @author Deniz ARIKAN
 */
public class ConvertTilesDialog extends FreeMarsDialog {

    private static final int FRAME_WIDTH = 1250;
    private static final int FRAME_HEIGHT = 460;
    private static final float BUTTON_FONT_SIZE = 9;
    private JPanel centerPanel;
    private JPanel convertFromTilePropertiesPanel;
    private JPanel convertFromTileTypeButtonPanel;
    private ButtonGroup convertFromTileTypeButtonGroup;
    private JPanel convertFromVegetationTypeButtonPanel;
    private ButtonGroup convertFromVegetationTypeButtonGroup;
    private JPanel convertFromBonusResourceTypeButtonPanel;
    private ButtonGroup convertFromBonusResourceTypeButtonGroup;
    private JPanel convertToTilePropertiesPanel;
    private JPanel convertToTileTypeButtonPanel;
    private ButtonGroup convertToTileTypeButtonGroup;
    private JPanel convertToVegetationTypeButtonPanel;
    private ButtonGroup convertToVegetationTypeButtonGroup;
    private JPanel convertToBonusResourceTypeButtonPanel;
    private ButtonGroup convertToBonusResourceTypeButtonGroup;
    private JPanel numberOfTilesToConvertPanel;
    private JSpinner numberOfTilesToConvertSpinner;
    private JPanel pageEndPanel;
    private JButton confirmButton;
    private JButton cancelButton;

    private int fromTileTypeId = 0;
    private int toTileTypeId = 0;
    private int fromVegetationTypeId = -1;
    private int toVegetationTypeId = -1;

    public ConvertTilesDialog(Frame owner) {
        super(owner);
        setModal(true);
        setTitle("Convert tiles");
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(getCenterPanel(), BorderLayout.CENTER);
        getContentPane().add(getPageEndPanel(), BorderLayout.PAGE_END);
    }

    public void display() {
        pack();
        Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width - FRAME_WIDTH) / 2, (screenSize.height - FRAME_HEIGHT) / 2, FRAME_WIDTH, FRAME_HEIGHT);
        setVisible(true);
        toFront();
    }

    public void setConfirmButtonAction(Action action) {
        getConfirmButton().setAction(action);
        getConfirmButton().setText("Confirm");
    }

    public void setCancelButtonAction(Action action) {
        getCancelButton().setAction(action);
        getCancelButton().setText("Cancel");
    }

    public void addConvertFromTileTypeToggleButton(JToggleButton button) {
        button.setVerticalTextPosition(AbstractButton.BOTTOM);
        button.setHorizontalTextPosition(AbstractButton.CENTER);
        button.setFont(button.getFont().deriveFont(BUTTON_FONT_SIZE));
        button.setFocusPainted(false);
        getConvertFromTileTypeButtonGroup().add(button);
        getConvertFromTileTypeButtonPanel().add(button);
    }

    public void addConvertFromVegetationTypeToggleButton(JToggleButton button) {
        button.setVerticalTextPosition(AbstractButton.BOTTOM);
        button.setHorizontalTextPosition(AbstractButton.CENTER);
        button.setFont(button.getFont().deriveFont(BUTTON_FONT_SIZE));
        button.setFocusPainted(false);
        getConvertFromVegetationTypeButtonGroup().add(button);
        getConvertFromVegetationTypeButtonPanel().add(button);
    }

    public void addConvertFromBonusResourceTypeToggleButton(JToggleButton button) {
        button.setVerticalTextPosition(AbstractButton.BOTTOM);
        button.setHorizontalTextPosition(AbstractButton.CENTER);
        button.setFont(button.getFont().deriveFont(BUTTON_FONT_SIZE));
        button.setFocusPainted(false);
        getConvertFromBonusResourceTypeButtonGroup().add(button);
        getConvertFromBonusResourceTypeButtonPanel().add(button);
    }

    public void addConvertToTileTypeToggleButton(JToggleButton button) {
        button.setVerticalTextPosition(AbstractButton.BOTTOM);
        button.setHorizontalTextPosition(AbstractButton.CENTER);
        button.setFont(button.getFont().deriveFont(BUTTON_FONT_SIZE));
        button.setFocusPainted(false);
        getConvertToTileTypeButtonGroup().add(button);
        getConvertToTileTypeButtonPanel().add(button);
    }

    public void addConvertToVegetationTypeToggleButton(JToggleButton button) {
        button.setVerticalTextPosition(AbstractButton.BOTTOM);
        button.setHorizontalTextPosition(AbstractButton.CENTER);
        button.setFont(button.getFont().deriveFont(BUTTON_FONT_SIZE));
        button.setFocusPainted(false);
        getConvertToVegetationTypeButtonGroup().add(button);
        getConvertToVegetationTypeButtonPanel().add(button);
    }

    public void addConvertToBonusResourceTypeToggleButton(JToggleButton button) {
        button.setVerticalTextPosition(AbstractButton.BOTTOM);
        button.setHorizontalTextPosition(AbstractButton.CENTER);
        button.setFont(button.getFont().deriveFont(BUTTON_FONT_SIZE));
        button.setFocusPainted(false);
        getConvertToBonusResourceTypeButtonGroup().add(button);
        getConvertToBonusResourceTypeButtonPanel().add(button);
    }

    public int getFromTileTypeId() {
        return fromTileTypeId;
    }

    public void setFromTileTypeId(int fromTileTypeId) {
        this.fromTileTypeId = fromTileTypeId;
    }

    public int getToTileTypeId() {
        return toTileTypeId;
    }

    public void setToTileTypeId(int toTileTypeId) {
        this.toTileTypeId = toTileTypeId;
    }

    public int getFromVegetationTypeId() {
        return fromVegetationTypeId;
    }

    public void setFromVegetationTypeId(int fromVegetationTypeId) {
        this.fromVegetationTypeId = fromVegetationTypeId;
    }

    public int getToVegetationTypeId() {
        return toVegetationTypeId;
    }

    public void setToVegetationTypeId(int toVegetationTypeId) {
        this.toVegetationTypeId = toVegetationTypeId;
    }

    public int getNumberOfTilesToConvertSpinnerValue() {
        return Integer.parseInt(getNumberOfTilesToConvertSpinner().getValue().toString());
    }

    private JPanel getCenterPanel() {
        if (centerPanel == null) {
            centerPanel = new JPanel(new BorderLayout(10, 0));
            centerPanel.add(getConvertFromTilePropertiesPanel(), BorderLayout.LINE_START);
            centerPanel.add(getNumberOfTilesToConvertPanel(), BorderLayout.CENTER);
            centerPanel.add(getConvertToTilePropertiesPanel(), BorderLayout.LINE_END);
        }
        return centerPanel;
    }

    private JPanel getConvertFromTilePropertiesPanel() {
        if (convertFromTilePropertiesPanel == null) {
            convertFromTilePropertiesPanel = new JPanel();
            convertFromTilePropertiesPanel.setLayout(new BoxLayout(convertFromTilePropertiesPanel, BoxLayout.Y_AXIS));
            convertFromTilePropertiesPanel.add(getConvertFromTileTypeButtonPanel());
            convertFromTilePropertiesPanel.add(getConvertFromVegetationTypeButtonPanel());
            convertFromTilePropertiesPanel.add(getConvertFromBonusResourceTypeButtonPanel());
        }
        return convertFromTilePropertiesPanel;
    }

    private JPanel getConvertFromTileTypeButtonPanel() {
        if (convertFromTileTypeButtonPanel == null) {
            convertFromTileTypeButtonPanel = new JPanel(new GridLayout(0, 4));
            convertFromTileTypeButtonPanel.setBorder(BorderFactory.createTitledBorder("Type"));
        }
        return convertFromTileTypeButtonPanel;
    }

    private ButtonGroup getConvertFromTileTypeButtonGroup() {
        if (convertFromTileTypeButtonGroup == null) {
            convertFromTileTypeButtonGroup = new ButtonGroup();
        }
        return convertFromTileTypeButtonGroup;
    }

    private JPanel getConvertFromVegetationTypeButtonPanel() {
        if (convertFromVegetationTypeButtonPanel == null) {
            convertFromVegetationTypeButtonPanel = new JPanel(new GridLayout(0, 4));
            convertFromVegetationTypeButtonPanel.setBorder(BorderFactory.createTitledBorder("Vegetation"));
        }
        return convertFromVegetationTypeButtonPanel;
    }

    private ButtonGroup getConvertFromVegetationTypeButtonGroup() {
        if (convertFromVegetationTypeButtonGroup == null) {
            convertFromVegetationTypeButtonGroup = new ButtonGroup();
        }
        return convertFromVegetationTypeButtonGroup;
    }

    private JPanel getConvertFromBonusResourceTypeButtonPanel() {
        if (convertFromBonusResourceTypeButtonPanel == null) {
            convertFromBonusResourceTypeButtonPanel = new JPanel(new GridLayout(0, 4));
            convertFromBonusResourceTypeButtonPanel.setBorder(BorderFactory.createTitledBorder("Bonus resource"));
        }
        return convertFromBonusResourceTypeButtonPanel;
    }

    private ButtonGroup getConvertFromBonusResourceTypeButtonGroup() {
        if (convertFromBonusResourceTypeButtonGroup == null) {
            convertFromBonusResourceTypeButtonGroup = new ButtonGroup();
        }
        return convertFromBonusResourceTypeButtonGroup;
    }

    private JPanel getConvertToTilePropertiesPanel() {
        if (convertToTilePropertiesPanel == null) {
            convertToTilePropertiesPanel = new JPanel();
            convertToTilePropertiesPanel.setLayout(new BoxLayout(convertToTilePropertiesPanel, BoxLayout.Y_AXIS));
            convertToTilePropertiesPanel.add(getConvertToTileTypeButtonPanel());
            convertToTilePropertiesPanel.add(getConvertToVegetationTypeButtonPanel());
            convertToTilePropertiesPanel.add(getConvertToBonusResourceTypeButtonPanel());
        }
        return convertToTilePropertiesPanel;
    }

    private JPanel getConvertToTileTypeButtonPanel() {
        if (convertToTileTypeButtonPanel == null) {
            convertToTileTypeButtonPanel = new JPanel(new GridLayout(0, 4));
            convertToTileTypeButtonPanel.setBorder(BorderFactory.createTitledBorder("Type"));
        }
        return convertToTileTypeButtonPanel;
    }

    private ButtonGroup getConvertToTileTypeButtonGroup() {
        if (convertToTileTypeButtonGroup == null) {
            convertToTileTypeButtonGroup = new ButtonGroup();
        }
        return convertToTileTypeButtonGroup;
    }

    private JPanel getConvertToVegetationTypeButtonPanel() {
        if (convertToVegetationTypeButtonPanel == null) {
            convertToVegetationTypeButtonPanel = new JPanel(new GridLayout(0, 4));
            convertToVegetationTypeButtonPanel.setBorder(BorderFactory.createTitledBorder("Vegetation"));
        }
        return convertToVegetationTypeButtonPanel;
    }

    private ButtonGroup getConvertToVegetationTypeButtonGroup() {
        if (convertToVegetationTypeButtonGroup == null) {
            convertToVegetationTypeButtonGroup = new ButtonGroup();
        }
        return convertToVegetationTypeButtonGroup;
    }

    private JPanel getConvertToBonusResourceTypeButtonPanel() {
        if (convertToBonusResourceTypeButtonPanel == null) {
            convertToBonusResourceTypeButtonPanel = new JPanel(new GridLayout(0, 4));
            convertToBonusResourceTypeButtonPanel.setBorder(BorderFactory.createTitledBorder("Bonus resource"));
        }
        return convertToBonusResourceTypeButtonPanel;
    }

    private ButtonGroup getConvertToBonusResourceTypeButtonGroup() {
        if (convertToBonusResourceTypeButtonGroup == null) {
            convertToBonusResourceTypeButtonGroup = new ButtonGroup();
        }
        return convertToBonusResourceTypeButtonGroup;
    }

    private JPanel getNumberOfTilesToConvertPanel() {
        if (numberOfTilesToConvertPanel == null) {
            numberOfTilesToConvertPanel = new JPanel(new GridLayout(0, 1, 0, 10));
            numberOfTilesToConvertPanel.add(new JLabel());
            numberOfTilesToConvertPanel.add(new JLabel());
            numberOfTilesToConvertPanel.add(new JLabel(new ImageIcon(FreeMarsImageManager.getImage("CONVERT_TILES_ARROW"))));
            numberOfTilesToConvertPanel.add(getNumberOfTilesToConvertSpinner());
            numberOfTilesToConvertPanel.add(new JLabel());
            numberOfTilesToConvertPanel.add(new JLabel());
        }
        return numberOfTilesToConvertPanel;
    }

    private JSpinner getNumberOfTilesToConvertSpinner() {
        if (numberOfTilesToConvertSpinner == null) {
            SpinnerModel spinnerModel = new SpinnerNumberModel(30, 1, 100, 1);
            numberOfTilesToConvertSpinner = new JSpinner(spinnerModel);
            numberOfTilesToConvertSpinner.setEditor(new JSpinner.DefaultEditor(numberOfTilesToConvertSpinner));
            numberOfTilesToConvertSpinner.setFont(numberOfTilesToConvertSpinner.getFont().deriveFont(22f));
        }
        return numberOfTilesToConvertSpinner;
    }

    private JPanel getPageEndPanel() {
        if (pageEndPanel == null) {
            pageEndPanel = new JPanel();
            pageEndPanel.add(getConfirmButton());
            pageEndPanel.add(getCancelButton());
        }
        return pageEndPanel;
    }

    private JButton getConfirmButton() {
        if (confirmButton == null) {
            confirmButton = new JButton("Confirm");
        }
        return confirmButton;
    }

    private JButton getCancelButton() {
        if (cancelButton == null) {
            cancelButton = new JButton();
        }
        return cancelButton;
    }

}

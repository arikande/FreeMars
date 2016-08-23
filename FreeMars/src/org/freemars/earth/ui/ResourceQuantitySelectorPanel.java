package org.freemars.earth.ui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

/**
 *
 * @author Deniz ARIKAN
 */
public class ResourceQuantitySelectorPanel extends JPanel implements ActionListener {

    private ButtonGroup selectionButtonGroup;
    private JRadioButton quantity1RadioButton;
    private JRadioButton quantity2RadioButton;
    private JRadioButton quantity3RadioButton;
    private JRadioButton quantity4RadioButton;
    private JRadioButton quantity5RadioButton;
    private JRadioButton customQuantityRadioButton;
    private JLabel quantity1Label;
    private JLabel quantity2Label;
    private JLabel quantity3Label;
    private JLabel quantity4Label;
    private JLabel quantity5Label;
    private JTextField customQuantityTextField;
    private int maxCapacity = 0;

    public ResourceQuantitySelectorPanel() {
        super(new GridLayout(6, 2));
        initializeWidgets();
    }

    private void initializeWidgets() {
        add(getQuantity1RadioButton());
        add(getQuantity1Label());
        add(getQuantity2RadioButton());
        add(getQuantity2Label());
        add(getQuantity3RadioButton());
        add(getQuantity3Label());
        add(getQuantity4RadioButton());
        add(getQuantity4Label());
        add(getQuantity5RadioButton());
        add(getQuantity5Label());
        add(getCustomQuantityRadioButton());
        add(getCustomQuantityTextField());
        getSelectionButtonGroup().add(getQuantity1RadioButton());
        getSelectionButtonGroup().add(getQuantity2RadioButton());
        getSelectionButtonGroup().add(getQuantity3RadioButton());
        getSelectionButtonGroup().add(getQuantity4RadioButton());
        getSelectionButtonGroup().add(getQuantity5RadioButton());
        getSelectionButtonGroup().add(getCustomQuantityRadioButton());
    }

    private ButtonGroup getSelectionButtonGroup() {
        if (selectionButtonGroup == null) {
            selectionButtonGroup = new ButtonGroup();
        }
        return selectionButtonGroup;
    }

    private JRadioButton getQuantity1RadioButton() {
        if (quantity1RadioButton == null) {
            quantity1RadioButton = new JRadioButton();
            quantity1RadioButton.setSelected(true);
            quantity1RadioButton.addActionListener(this);
        }
        return quantity1RadioButton;
    }

    private JRadioButton getQuantity2RadioButton() {
        if (quantity2RadioButton == null) {
            quantity2RadioButton = new JRadioButton();
            quantity2RadioButton.addActionListener(this);
        }
        return quantity2RadioButton;
    }

    private JRadioButton getQuantity3RadioButton() {
        if (quantity3RadioButton == null) {
            quantity3RadioButton = new JRadioButton();
            quantity3RadioButton.addActionListener(this);
        }
        return quantity3RadioButton;
    }

    private JRadioButton getQuantity4RadioButton() {
        if (quantity4RadioButton == null) {
            quantity4RadioButton = new JRadioButton();
            quantity4RadioButton.addActionListener(this);
        }
        return quantity4RadioButton;
    }

    private JRadioButton getQuantity5RadioButton() {
        if (quantity5RadioButton == null) {
            quantity5RadioButton = new JRadioButton();
            quantity5RadioButton.addActionListener(this);
        }
        return quantity5RadioButton;
    }

    private JRadioButton getCustomQuantityRadioButton() {
        if (customQuantityRadioButton == null) {
            customQuantityRadioButton = new JRadioButton();
            customQuantityRadioButton.addActionListener(this);
        }
        return customQuantityRadioButton;
    }

    private JLabel getQuantity1Label() {
        if (quantity1Label == null) {
            quantity1Label = new JLabel();
        }
        return quantity1Label;
    }

    private JLabel getQuantity2Label() {
        if (quantity2Label == null) {
            quantity2Label = new JLabel();
        }
        return quantity2Label;
    }

    private JLabel getQuantity3Label() {
        if (quantity3Label == null) {
            quantity3Label = new JLabel();
        }
        return quantity3Label;
    }

    private JLabel getQuantity4Label() {
        if (quantity4Label == null) {
            quantity4Label = new JLabel();
        }
        return quantity4Label;
    }

    private JLabel getQuantity5Label() {
        if (quantity5Label == null) {
            quantity5Label = new JLabel();
        }
        return quantity5Label;
    }

    private JTextField getCustomQuantityTextField() {
        if (customQuantityTextField == null) {
            customQuantityTextField = new JTextField();
        }
        return customQuantityTextField;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
        getQuantity1Label().setText("  " + String.valueOf(getMaxCapacity()));
        getQuantity2Label().setText("  " + String.valueOf(getMaxCapacity() * 4 / 5));
        getQuantity3Label().setText("  " + String.valueOf(getMaxCapacity() * 3 / 5));
        getQuantity4Label().setText("  " + String.valueOf(getMaxCapacity() * 2 / 5));
        getQuantity5Label().setText("  " + String.valueOf(getMaxCapacity() * 1 / 5));

    }

    public int getSelectedQuantity() {
        if (getQuantity1RadioButton().isSelected()) {
            return getMaxCapacity();
        } else if (getQuantity2RadioButton().isSelected()) {
            return getMaxCapacity() * 4 / 5;
        } else if (getQuantity3RadioButton().isSelected()) {
            return getMaxCapacity() * 3 / 5;
        } else if (getQuantity4RadioButton().isSelected()) {
            return getMaxCapacity() * 2 / 5;
        } else if (getQuantity5RadioButton().isSelected()) {
            return getMaxCapacity() * 1 / 5;
        } else if (getCustomQuantityRadioButton().isSelected()) {
            int customQuantity = 0;
            try {
                customQuantity = Integer.parseInt(getCustomQuantityTextField().getText().trim());
                if (customQuantity < 0) {
                    customQuantity = 0;
                }
            } catch (Exception e) {
            }
            return customQuantity;
        }
        return 0;
    }

    public void actionPerformed(ActionEvent e) {
     //   getModel().setResourceTransferAmount(getSelectedQuantity());
    }
}

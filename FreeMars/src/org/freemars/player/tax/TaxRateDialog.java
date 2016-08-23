package org.freemars.player.tax;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeListener;
import org.freemars.ui.util.FreeMarsDialog;

/**
 *
 * @author arikande
 */
public class TaxRateDialog extends FreeMarsDialog {

    private static final int FONT_SIZE = 16;
    private final int FRAME_WIDTH = 500;
    private final int FRAME_HEIGHT = 320;
    private JLabel taxRateLabel;
    private JTextField taxRateTextField;
    private JLabel incomeForSelectedTaxLabel;
    private JTextField incomeForSelectedTaxTextField;
    private JLabel totalExpensesLabel;
    private JTextField totalExpensesTextField;
    private JLabel netIncomeLabel;
    private JTextField netIncomeTextField;
    private JSlider taxRateSlider;
    private JPanel centerPanel;
    private JPanel informationPanel;
    private JPanel pageEndPanel;
    private JButton acceptButton;
    private JButton cancelButton;

    public TaxRateDialog(JFrame parent) {
        super(parent);
        setModal(true);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setTitle("Manage colony tax");
    }

    public void display() {
        getContentPane().setLayout(new BorderLayout(4, 4));
        initializeWidgets();
        pack();
        final java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width - FRAME_WIDTH) / 2, (screenSize.height - FRAME_HEIGHT) / 2, FRAME_WIDTH, FRAME_HEIGHT);
        setVisible(true);
        toFront();
    }

    public void setAcceptButtonAction(Action action) {
        getAcceptButton().setAction(action);
    }

    public void addTaxRateSliderChangeListener(ChangeListener changeListener) {
        getTaxRateSlider().addChangeListener(changeListener);
    }

    public int getTaxRateSliderValue() {
        return getTaxRateSlider().getValue();
    }

    public void setTaxRateValue(String value) {
        getTaxRateTextField().setText(value);
    }

    public void setIncomeForSelectedTaxValue(String value) {
        getIncomeForSelectedTaxTextField().setText(value);
    }

    public void setTotalExpensesValue(String value) {
        getTotalExpensesTextField().setText(value);
    }

    public void setNetIncomeValue(String value) {
        getNetIncomeTextField().setText(value);
    }

    public void setNetIncomeColor(Color color) {
        getNetIncomeTextField().setForeground(color);
    }

    public void setTaxRateSliderValue(int value) {
        getTaxRateSlider().setValue(value);
    }

    private void initializeWidgets() {
        add(getCenterPanel(), BorderLayout.CENTER);
        add(getPageEndPanel(), BorderLayout.PAGE_END);
    }

    private JPanel getCenterPanel() {
        if (centerPanel == null) {
            centerPanel = new JPanel(new BorderLayout());
            centerPanel.add(new JLabel(" "), BorderLayout.PAGE_START);
            centerPanel.add(new JLabel("        "), BorderLayout.LINE_START);
            centerPanel.add(new JLabel("        "), BorderLayout.LINE_END);
            centerPanel.add(getInformationPanel(), BorderLayout.CENTER);
            centerPanel.add(getTaxRateSlider(), BorderLayout.PAGE_END);
        }
        return centerPanel;
    }

    private JPanel getInformationPanel() {
        if (informationPanel == null) {
            informationPanel = new JPanel(new GridLayout(0, 2, 10, 10));
            informationPanel.add(getTaxRateLabel());
            informationPanel.add(getTaxRateTextField());
            informationPanel.add(getIncomeForSelectedTaxLabel());
            informationPanel.add(getIncomeForSelectedTaxTextField());
            informationPanel.add(getTotalExpensesLabel());
            informationPanel.add(getTotalExpensesTextField());
            informationPanel.add(getNetIncomeLabel());
            informationPanel.add(getNetIncomeTextField());
        }
        return informationPanel;
    }

    private JLabel getTaxRateLabel() {
        if (taxRateLabel == null) {
            taxRateLabel = new JLabel("Tax rate :");
        }
        return taxRateLabel;
    }

    private JTextField getTaxRateTextField() {
        if (taxRateTextField == null) {
            taxRateTextField = new JTextField();
            taxRateTextField.setEditable(false);
            taxRateTextField.setHorizontalAlignment(JTextField.RIGHT);
            taxRateTextField.setFont(taxRateTextField.getFont().deriveFont(Font.BOLD, FONT_SIZE));
        }
        return taxRateTextField;
    }

    private JLabel getIncomeForSelectedTaxLabel() {
        if (incomeForSelectedTaxLabel == null) {
            incomeForSelectedTaxLabel = new JLabel("Income :");
        }
        return incomeForSelectedTaxLabel;
    }

    private JTextField getIncomeForSelectedTaxTextField() {
        if (incomeForSelectedTaxTextField == null) {
            incomeForSelectedTaxTextField = new JTextField();
            incomeForSelectedTaxTextField.setEditable(false);
            incomeForSelectedTaxTextField.setHorizontalAlignment(JTextField.RIGHT);
            incomeForSelectedTaxTextField.setFont(taxRateTextField.getFont().deriveFont(Font.BOLD, FONT_SIZE));
        }
        return incomeForSelectedTaxTextField;
    }

    private JLabel getTotalExpensesLabel() {
        if (totalExpensesLabel == null) {
            totalExpensesLabel = new JLabel("Expenses :");
        }
        return totalExpensesLabel;
    }

    private JTextField getTotalExpensesTextField() {
        if (totalExpensesTextField == null) {
            totalExpensesTextField = new JTextField();
            totalExpensesTextField.setEditable(false);
            totalExpensesTextField.setHorizontalAlignment(JTextField.RIGHT);
            totalExpensesTextField.setFont(totalExpensesTextField.getFont().deriveFont(Font.BOLD, FONT_SIZE));

        }
        return totalExpensesTextField;
    }

    private JLabel getNetIncomeLabel() {
        if (netIncomeLabel == null) {
            netIncomeLabel = new JLabel("Net income :");
        }
        return netIncomeLabel;
    }

    private JTextField getNetIncomeTextField() {
        if (netIncomeTextField == null) {
            netIncomeTextField = new JTextField();
            netIncomeTextField.setEditable(false);
            netIncomeTextField.setHorizontalAlignment(JTextField.RIGHT);
            netIncomeTextField.setFont(netIncomeTextField.getFont().deriveFont(Font.BOLD, FONT_SIZE));
        }
        return netIncomeTextField;
    }

    private JPanel getPageEndPanel() {
        if (pageEndPanel == null) {
            pageEndPanel = new JPanel(new GridLayout(1, 2));
            pageEndPanel.add(getAcceptButton());
            pageEndPanel.add(getCancelButton());
        }
        return pageEndPanel;
    }

    private JSlider getTaxRateSlider() {
        if (taxRateSlider == null) {
            taxRateSlider = new JSlider(0, 100);
            taxRateSlider.setMajorTickSpacing(5);
            taxRateSlider.setMinorTickSpacing(5);
            taxRateSlider.setSnapToTicks(true);
            taxRateSlider.setPaintTicks(true);
            taxRateSlider.setPaintLabels(true);
        }
        return taxRateSlider;
    }

    private JButton getAcceptButton() {
        if (acceptButton == null) {
            acceptButton = new JButton();
        }
        return acceptButton;
    }

    private JButton getCancelButton() {
        if (cancelButton == null) {
            cancelButton = new JButton("Cancel");
            cancelButton.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    dispose();
                }
            });
        }
        return cancelButton;
    }
}

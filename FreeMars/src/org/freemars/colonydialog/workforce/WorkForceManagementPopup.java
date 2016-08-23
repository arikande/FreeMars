package org.freemars.colonydialog.workforce;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import javax.swing.Box;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JSlider;
import javax.swing.event.ChangeListener;

public class WorkForceManagementPopup extends JPopupMenu {

    private JPanel pageStartPanel;
    private JPanel centerPanel;
    private NumberSelectorPanel numberSelectorPanel;
    private JPanel resourceSelectorPanel;
    private JSlider workforceColonistsSlider;
    private JPanel currentValuesPanel;
    private JLabel numberOfColonistsLabel;

    public WorkForceManagementPopup() {
        setLayout(new BorderLayout());
        add(getPageStartPanel(), BorderLayout.PAGE_START);
        add(getCenterPanel(), BorderLayout.CENTER);
    }

    public void setResourceSelectorPanelContent(JComponent component) {
        getResourceSelectorPanel().removeAll();
        getResourceSelectorPanel().add(component);
    }

    public void setCenterPanelContent(JComponent component) {
        getCenterPanel().removeAll();
        getCenterPanel().add(component);
    }

    public void initializeWorkforceColonistsSlider(int minimum, int maximum) {
        getWorkforceColonistsSlider().setMinimum(minimum);
        getWorkforceColonistsSlider().setMaximum(maximum);
    }

    public void setWorkforceColonistsSliderValue(int value) {
        getWorkforceColonistsSlider().setValue(value);
    }

    public void addWorkforceColonistsSliderChangeListener(ChangeListener changeListener) {
        getWorkforceColonistsSlider().addChangeListener(changeListener);
    }

    public void initializeNumberSelectorPanel(int interval, int maximum) {
        getNumberSelectorPanel().initialize(interval, maximum);
    }

    public int getNumberSelectorPanelValue() {
        return getNumberSelectorPanel().getValue();
    }

    public void setNumberSelectorPanelValue(int value) {
        getNumberSelectorPanel().setValue(value);
    }

    public void addNumberSelectorPanelActionListener(ActionListener actionListener) {
        getNumberSelectorPanel().addActionListener(actionListener);
    }

    public void setNumberOfColonistsLabelText(String text) {
        String numberOfColonistsLabelText = text;
        for (int i = 0; i < 3 - text.length(); i++) {
            numberOfColonistsLabelText = " " + numberOfColonistsLabelText;
        }
        getNumberOfColonistsLabel().setText(numberOfColonistsLabelText);
    }

    private JPanel getPageStartPanel() {
        if (pageStartPanel == null) {
            pageStartPanel = new JPanel(new BorderLayout());
            pageStartPanel.add(getResourceSelectorPanel(), BorderLayout.LINE_START);
            pageStartPanel.add(getWorkforceColonistsSlider(), BorderLayout.CENTER);
            pageStartPanel.add(getCurrentValuesPanel(), BorderLayout.LINE_END);
        }
        return pageStartPanel;
    }

    private JPanel getResourceSelectorPanel() {
        if (resourceSelectorPanel == null) {
            resourceSelectorPanel = new JPanel();
        }
        return resourceSelectorPanel;
    }

    private JSlider getWorkforceColonistsSlider() {
        if (workforceColonistsSlider == null) {
            workforceColonistsSlider = new JSlider();
            workforceColonistsSlider.setMajorTickSpacing(50);
            workforceColonistsSlider.setMinorTickSpacing(5);
            workforceColonistsSlider.setPaintTicks(true);
            workforceColonistsSlider.setPaintLabels(true);
        }
        return workforceColonistsSlider;
    }

    private JPanel getCenterPanel() {
        if (centerPanel == null) {
            centerPanel = new JPanel(new BorderLayout());
            centerPanel.add(getNumberSelectorPanel(), BorderLayout.CENTER);
        }
        return centerPanel;
    }

    private NumberSelectorPanel getNumberSelectorPanel() {
        if (numberSelectorPanel == null) {
            numberSelectorPanel = new NumberSelectorPanel();
        }
        return numberSelectorPanel;
    }

    private JPanel getCurrentValuesPanel() {
        if (currentValuesPanel == null) {
            currentValuesPanel = new JPanel(new BorderLayout());
            currentValuesPanel.add(Box.createHorizontalStrut(10), BorderLayout.LINE_START);
            currentValuesPanel.add(getNumberOfColonistsLabel(), BorderLayout.CENTER);
            currentValuesPanel.add(Box.createHorizontalStrut(10), BorderLayout.LINE_END);
        }
        return currentValuesPanel;
    }

    private JLabel getNumberOfColonistsLabel() {
        if (numberOfColonistsLabel == null) {
            numberOfColonistsLabel = new JLabel();
            numberOfColonistsLabel.setFont(numberOfColonistsLabel.getFont().deriveFont(16f));
            numberOfColonistsLabel.setPreferredSize(new Dimension(35, 15));
        }
        return numberOfColonistsLabel;
    }
}

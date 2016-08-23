package org.freemars.colonydialog.workforce;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

public class NumberSelectorPanel extends JPanel implements ActionListener {

    private ButtonGroup buttonGroup;
    private int value;
    private final List<ActionListener> actionListeners;
    private JToggleButton hiddenButton;

    public NumberSelectorPanel() {
        super(new GridLayout(2, 0));
        actionListeners = new ArrayList<ActionListener>();
    }

    public void initialize(int interval, int max) {
        int buttonCount = (max / interval) + 1;
        for (int i = 0; i < buttonCount; i++) {
            addNumberSelectorButton(i * interval);
        }
        if (max % interval != 0) {
            addNumberSelectorButton(max);
        }
        getButtonGroup().add(getHiddenButton());
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof NumberSelectionButton) {
            value = ((NumberSelectionButton) e.getSource()).value;
        }
        for (ActionListener actionListener : actionListeners) {
            actionListener.actionPerformed(e);
        }
    }

    public void addActionListener(ActionListener actionListener) {
        actionListeners.add(actionListener);
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
        getHiddenButton().setSelected(true);
        for (Component component : getComponents()) {
            NumberSelectionButton numberSelectionButton = (NumberSelectionButton) component;
            if (numberSelectionButton.getValue() == value) {
                numberSelectionButton.setSelected(true);
            }
        }
    }

    private JToggleButton getHiddenButton() {
        if (hiddenButton == null) {
            hiddenButton = new JToggleButton();
            hiddenButton.setVisible(false);
        }
        return hiddenButton;
    }

    private void addNumberSelectorButton(int value) {
        NumberSelectionButton numberSelectionButton = new NumberSelectionButton(value);
        numberSelectionButton.addActionListener(this);
        getButtonGroup().add(numberSelectionButton);
        add(numberSelectionButton);
    }

    private ButtonGroup getButtonGroup() {
        if (buttonGroup == null) {
            buttonGroup = new ButtonGroup();
        }
        return buttonGroup;
    }

    class NumberSelectionButton extends JToggleButton {

        private int value;

        public NumberSelectionButton(int value) {
            super(String.valueOf(value));
            this.value = value;
            setPreferredSize(new Dimension(55, 20));
        }

        public int getValue() {
            return value;
        }
    }
}

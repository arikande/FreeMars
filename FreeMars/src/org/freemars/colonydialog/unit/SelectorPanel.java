package org.freemars.colonydialog.unit;

import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Iterator;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.TransferHandler;

public class SelectorPanel<T> extends JPanel implements ActionListener {

    private final HashMap<JToggleButton, T> selectableObjects;
    private T selectedItem;

    public T getSelectable(JToggleButton toggleButton) {
        return selectableObjects.get(toggleButton);
    }

    public SelectorPanel(LayoutManager layoutManager) {
        super(layoutManager);
        selectableObjects = new HashMap<JToggleButton, T>();
    }

    public void actionPerformed(ActionEvent e) {
        setSelectedObject(selectableObjects.get((JToggleButton) e.getSource()));
    }

    @Override
    public void removeAll() {
        selectableObjects.clear();
        super.removeAll();
        super.revalidate();
        super.repaint();
    }

    public void addActionListener(ActionListener actionListener) {
        Iterator<JToggleButton> iterator = selectableObjects.keySet().iterator();
        while (iterator.hasNext()) {
            JToggleButton toggleButton = (JToggleButton) iterator.next();
            toggleButton.removeActionListener(this);
            toggleButton.addActionListener(actionListener);
            toggleButton.addActionListener(this);
        }
    }

    public void addSelectable(T selectable) {
        JToggleButton toggleButton = new JToggleButton();
        if (transferHandler != null) {
            toggleButton.setTransferHandler(transferHandler);
        }
        addSelectable(selectable, toggleButton);
    }

    public void addSelectable(T selectable, JToggleButton toggleButton) {
        if (selectable != null && toggleButton != null) {
            toggleButton.addActionListener(this);
            selectableObjects.put(toggleButton, selectable);
            if (transferHandler != null) {
                toggleButton.setTransferHandler(transferHandler);
            }
            add(toggleButton);
            super.revalidate();
            super.repaint();
        }
    }

    public T getSelectedItem() {
        return selectedItem;
    }

    public void setSelectedObject(T selectedItem) {
        this.selectedItem = selectedItem;
        refreshToggleButtons();
    }

    public JToggleButton getToggleButton(T selectable) {
        Iterator<JToggleButton> iterator = selectableObjects.keySet().iterator();
        while (iterator.hasNext()) {
            JToggleButton toggleButton = (JToggleButton) iterator.next();
            T value = selectableObjects.get(toggleButton);
            if (value.equals(selectable)) {
                return toggleButton;
            }
        }
        return null;
    }
    TransferHandler transferHandler;

    @Override
    public void setTransferHandler(TransferHandler transferHandler) {
        this.transferHandler = transferHandler;
        Iterator<JToggleButton> iterator = selectableObjects.keySet().iterator();
        while (iterator.hasNext()) {
            JToggleButton toggleButton = (JToggleButton) iterator.next();
            toggleButton.setTransferHandler(transferHandler);
        }
    }

    private void refreshToggleButtons() {
        Iterator<JToggleButton> iterator = selectableObjects.keySet().iterator();
        while (iterator.hasNext()) {
            JToggleButton toggleButton = (JToggleButton) iterator.next();
            T value = selectableObjects.get(toggleButton);
            if (value.equals(selectedItem)) {
                toggleButton.setSelected(true);
            } else {
                toggleButton.setSelected(false);
            }
        }
    }
}

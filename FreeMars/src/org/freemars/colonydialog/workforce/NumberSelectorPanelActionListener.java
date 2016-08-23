package org.freemars.colonydialog.workforce;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author Deniz ARIKAN
 */
public class NumberSelectorPanelActionListener implements ActionListener {

    private final WorkForceManagementPopup workForceManagementPopup;
    private final WorkForceManagementPopupModel workForceManagementPopupModel;

    public NumberSelectorPanelActionListener(WorkForceManagementPopup workForceManagementPopup, WorkForceManagementPopupModel workForceManagementPopupModel) {
        this.workForceManagementPopup = workForceManagementPopup;
        this.workForceManagementPopupModel = workForceManagementPopupModel;
    }

    public void actionPerformed(ActionEvent e) {
        workForceManagementPopupModel.setSelectedNumberOfWorkers(workForceManagementPopup.getNumberSelectorPanelValue());
        workForceManagementPopup.setWorkforceColonistsSliderValue(workForceManagementPopup.getNumberSelectorPanelValue());
    }
}

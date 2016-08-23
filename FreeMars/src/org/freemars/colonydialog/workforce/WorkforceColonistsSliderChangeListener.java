package org.freemars.colonydialog.workforce;

import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author Deniz ARIKAN
 */
public class WorkforceColonistsSliderChangeListener implements ChangeListener {

    private final WorkForceManagementPopup workForceManagementPopup;
    private final WorkForceManagementPopupModel workForceManagementPopupModel;

    public WorkforceColonistsSliderChangeListener(WorkForceManagementPopup workForceManagementPopup, WorkForceManagementPopupModel workForceManagementPopupModel) {
        this.workForceManagementPopup = workForceManagementPopup;
        this.workForceManagementPopupModel = workForceManagementPopupModel;
    }

    public void stateChanged(ChangeEvent e) {
        JSlider slider = (JSlider) e.getSource();
        workForceManagementPopupModel.setSelectedNumberOfWorkers(slider.getValue());
        workForceManagementPopup.setNumberSelectorPanelValue(slider.getValue());
        workForceManagementPopup.setNumberOfColonistsLabelText(String.valueOf(slider.getValue()));
    }
}

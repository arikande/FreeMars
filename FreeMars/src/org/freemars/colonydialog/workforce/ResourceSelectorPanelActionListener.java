package org.freemars.colonydialog.workforce;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.freemars.colonydialog.unit.SelectorPanel;
import org.freerealm.resource.Resource;

/**
 *
 * @author Deniz ARIKAN
 */
public class ResourceSelectorPanelActionListener implements ActionListener {

    private final SelectorPanel<Resource> resourceSelectorPanel;
    private final WorkForceManagementPopupModel workForceManagementPopupModel;

    public ResourceSelectorPanelActionListener(SelectorPanel<Resource> resourceSelectorPanel, WorkForceManagementPopupModel workForceManagementPopupModel) {
        this.resourceSelectorPanel = resourceSelectorPanel;
        this.workForceManagementPopupModel = workForceManagementPopupModel;
    }

    public void actionPerformed(ActionEvent e) {
        workForceManagementPopupModel.setSelectedResource(resourceSelectorPanel.getSelectedItem());
    }
}

package org.freemars.colonydialog;

import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

/**
 *
 * @author Deniz ARIKAN
 */
public class ColonyImprovementsPopupListener implements PopupMenuListener {

    private final ColonyDialogModel model;

    public ColonyImprovementsPopupListener(ColonyDialogModel model) {
        this.model = model;
    }

    public void popupMenuCanceled(PopupMenuEvent e) {
    }

    public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
    }

    public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
        ColonyImprovementPopupMenu popup = (ColonyImprovementPopupMenu) e.getSource();
        popup.setCityImprovement(model.getSelectedImprovement());
    }
}

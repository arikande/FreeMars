package org.freemars.colonydialog.unit;

import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import org.freemars.colonydialog.ColonyDialogModel;

/**
 *
 * @author Deniz ARIKAN
 */
public class UnitOrdersPopupListener implements PopupMenuListener {

    private final ColonyDialogModel model;

    public UnitOrdersPopupListener(ColonyDialogModel model) {
        this.model = model;
    }

    public void popupMenuCanceled(PopupMenuEvent e) {
    }

    public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
    }

    public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
        ColonyUnitOrdersPopup popup = (ColonyUnitOrdersPopup) e.getSource();
        popup.build();
    }
}

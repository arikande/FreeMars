package org.freemars.controller.action;

import java.awt.event.ActionEvent;
import java.util.HashMap;
import javax.swing.AbstractAction;
import org.freemars.controller.ActionManager;
import org.freemars.controller.FreeMarsController;
import org.freemars.earth.ui.ExpeditionaryForceUpdateDialog;
import org.freerealm.unit.FreeRealmUnitType;

/**
 *
 * @author Deniz ARIKAN
 */
public class DisplayExpeditionaryForceUpdateAction extends AbstractAction {

    private final FreeMarsController controller;
    private final HashMap<FreeRealmUnitType, Integer> expeditionaryForceUpdate;

    public DisplayExpeditionaryForceUpdateAction(FreeMarsController controller, HashMap<FreeRealmUnitType, Integer> expeditionaryForceUpdate) {
        super("Expeditionary force");
        this.controller = controller;
        this.expeditionaryForceUpdate = expeditionaryForceUpdate;
    }

    public void actionPerformed(ActionEvent e) {
        ExpeditionaryForceUpdateDialog expeditionaryForceUpdateDialog = new ExpeditionaryForceUpdateDialog(controller.getCurrentFrame(), expeditionaryForceUpdate);
        expeditionaryForceUpdateDialog.setDisplayAllExpeditionaryForceButtonAction(controller.getAction(ActionManager.DISPLAY_EXPEDITIONARY_FORCE_ACTION));
        expeditionaryForceUpdateDialog.setCloseAction(new CloseFreeMarsDialogAction(expeditionaryForceUpdateDialog));
        expeditionaryForceUpdateDialog.display();
    }
}

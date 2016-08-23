package org.freemars.earth.action;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Iterator;
import javax.swing.AbstractAction;
import org.freemars.colonydialog.controller.CloseDialogAction;
import org.freemars.controller.FreeMarsController;
import org.freemars.earth.ui.ExpeditionaryForceLandedDialog;
import org.freemars.ui.image.FreeMarsImageManager;
import org.freerealm.unit.FreeRealmUnitType;

/**
 *
 * @author Deniz ARIKAN
 */
public class DisplayExpeditionaryForceLandedDialogAction extends AbstractAction {

    private final FreeMarsController controller;
    private HashMap<FreeRealmUnitType, Integer> landedUnits;
    private int attackWave;

    public DisplayExpeditionaryForceLandedDialogAction(FreeMarsController controller) {
        this.controller = controller;
    }

    public void actionPerformed(ActionEvent e) {
        ExpeditionaryForceLandedDialog expeditionaryForceLandedDialog = new ExpeditionaryForceLandedDialog(controller.getCurrentFrame());
        expeditionaryForceLandedDialog.setOkButtonAction(new CloseDialogAction(expeditionaryForceLandedDialog));
        switch (getAttackWave()) {
            case 1:
                expeditionaryForceLandedDialog.setDescriptionPrefix("First");
                break;
            case 2:
                expeditionaryForceLandedDialog.setDescriptionPrefix("Second");
                break;
            case 3:
                expeditionaryForceLandedDialog.setDescriptionPrefix("Final");
                break;
        }
        if (landedUnits != null && !landedUnits.isEmpty()) {
            Iterator<FreeRealmUnitType> iterator = landedUnits.keySet().iterator();
            while (iterator.hasNext()) {
                FreeRealmUnitType unitType = iterator.next();
                Image image = FreeMarsImageManager.getImage(unitType);
                image = FreeMarsImageManager.createResizedCopy(image, -1, 50, false, expeditionaryForceLandedDialog);
                expeditionaryForceLandedDialog.addLandedUnitInformation(image, landedUnits.get(unitType), unitType.getName());
            }
        }
        expeditionaryForceLandedDialog.display();
    }

    public int getAttackWave() {
        return attackWave;
    }

    public void setAttackWave(int attackWave) {
        this.attackWave = attackWave;
    }

    public HashMap<FreeRealmUnitType, Integer> getLandedUnits() {
        return landedUnits;
    }

    public void setLandedUnits(HashMap<FreeRealmUnitType, Integer> landedUnits) {
        this.landedUnits = landedUnits;
    }
}

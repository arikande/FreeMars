package org.freemars.controller.action;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import org.freemars.colonydialog.controller.CloseDialogAction;
import org.freemars.controller.FreeMarsController;
import org.freemars.ui.image.FreeMarsImageManager;
import org.freemars.ui.unit.AttackUnitDialog;
import org.freerealm.Utility;
import org.freerealm.unit.Unit;

/**
 *
 * @author Deniz ARIKAN
 */
public class DisplayAttackUnitDialogAction extends AbstractAction {

    private final FreeMarsController controller;
    private final Unit attacker;
    private final Unit defender;

    public DisplayAttackUnitDialogAction(FreeMarsController controller, Unit attacker, Unit defender) {
        super("Attack unit");
        this.controller = controller;
        this.attacker = attacker;
        this.defender = defender;
    }

    public void actionPerformed(ActionEvent e) {
        AttackUnitDialog attackUnitDialog = new AttackUnitDialog(controller.getCurrentFrame());
        Image attackerImage = FreeMarsImageManager.getImage(attacker);
        attackerImage = FreeMarsImageManager.createResizedCopy(attackerImage, 100, -1, false, attackUnitDialog);
        Image defenderImage = FreeMarsImageManager.getImage(defender);
        defenderImage = FreeMarsImageManager.createResizedCopy(defenderImage, 100, -1, false, attackUnitDialog);
        attackUnitDialog.setAttackerUnitImage(attackerImage);
        attackUnitDialog.setDefenderUnitImage(defenderImage);
        attackUnitDialog.setAttackerUnitName(attacker.toString());
        attackUnitDialog.setDefenderUnitName(defender.toString());
        int attackPoints = attacker.getAttackPoints();
        int defencePoints = defender.getDefencePoints();
        attackUnitDialog.setAttackerUnitPoints("Attack : " + attackPoints);
        attackUnitDialog.setDefenderUnitPoints("Defense : " + defencePoints);
        attackUnitDialog.setAttackerTerrainModifier("");
        int terrainModifier = Utility.getCoordinateDefenceBonus(controller.getFreeMarsModel().getRealm(), defender.getCoordinate());
        attackUnitDialog.setDefenderTerrainModifier("Terrain : " + String.valueOf(terrainModifier) + " %");
        defencePoints = (int) Utility.modifyByPercent(defencePoints, terrainModifier);
        attackUnitDialog.setAttackerUnitEffectivePoints("");
        attackUnitDialog.setDefenderUnitEffectivePoints("Actual : " + defencePoints);

        int total = attackPoints + defencePoints;

        int defenderWinPercent = (int) (Math.ceil((defencePoints * 100) / total));
        int attackerWinPercent = 100 - defenderWinPercent;

        attackUnitDialog.setAttackerToWinPercent("Chance : " + String.valueOf(attackerWinPercent) + " %");
        attackUnitDialog.setDefenderToWinPercent("Chance : " + String.valueOf(defenderWinPercent) + " %");

        attackUnitDialog.setAttackerWinChanceLabelText(String.valueOf(attackerWinPercent) + " %");
        if (attackerWinPercent >= 50) {
            attackUnitDialog.setAttackerWinChanceLabelColor(new Color(40, 150, 40));
        } else {
            attackUnitDialog.setAttackerWinChanceLabelColor(Color.red);
        }

        attackUnitDialog.setConfirmAttackAction(new AttackUnitAction(controller, attacker, defender, attackUnitDialog));
        attackUnitDialog.setCloseDialogAction(new CloseDialogAction(attackUnitDialog));
        attackUnitDialog.display();
    }
}

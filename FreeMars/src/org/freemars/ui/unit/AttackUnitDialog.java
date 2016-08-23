package org.freemars.ui.unit;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Image;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.freemars.ui.util.FreeMarsDialog;

/**
 *
 * @author Deniz ARIKAN
 */
public class AttackUnitDialog extends FreeMarsDialog {

    private final int FRAME_WIDTH = 660;
    private final int FRAME_HEIGHT = 380;
    private JPanel mainPanel;
    private JPanel footerPanel;
    private JButton confirmButton;
    private JButton cancelButton;
    private UnitFightingInformationPanel attackerPanel;
    private UnitFightingInformationPanel defenderPanel;
    private JLabel attackerWinChanceLabel;

    public AttackUnitDialog(Frame owner) {
        super(owner);
        setModal(true);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setTitle("Attack unit");
        initializeWidgets();
    }

    public void setConfirmAttackAction(Action action) {
        getConfirmButton().setAction(action);
    }

    public void setCloseDialogAction(Action action) {
        getCancelButton().setAction(action);
        getCancelButton().setText("Cancel");
    }

    public void setAttackerUnitImage(Image image) {
        getAttackerPanel().setImage(image);
    }

    public void setDefenderUnitImage(Image image) {
        getDefenderPanel().setImage(image);
    }

    public void setAttackerUnitName(String name) {
        getAttackerPanel().setUnitName(name);
    }

    public void setDefenderUnitName(String name) {
        getDefenderPanel().setUnitName(name);
    }

    public void setAttackerUnitPoints(String points) {
        getAttackerPanel().setUnitFightingPoints(points);
    }

    public void setDefenderUnitPoints(String points) {
        getDefenderPanel().setUnitFightingPoints(points);
    }

    public void setAttackerTerrainModifier(String text) {
        getAttackerPanel().setTerrainModifierLabelText(text);
    }

    public void setDefenderTerrainModifier(String text) {
        getDefenderPanel().setTerrainModifierLabelText(text);
    }

    public void setAttackerUnitEffectivePoints(String points) {
        getAttackerPanel().setEffectivePointsLabelText(points);
    }

    public void setDefenderUnitEffectivePoints(String points) {
        getDefenderPanel().setEffectivePointsLabelText(points);
    }

    public void setAttackerToWinPercent(String percent) {
        getAttackerPanel().setToWinPercentLabelText(percent);
    }

    public void setDefenderToWinPercent(String percent) {
        getDefenderPanel().setToWinPercentLabelText(percent);
    }

    public void setAttackerWinChanceLabelText(String text) {
        getAttackerWinChanceLabel().setText(text);
    }

    public void setAttackerWinChanceLabelColor(Color color) {
        getAttackerWinChanceLabel().setForeground(color);
    }

    public void display() {
        super.display(FRAME_WIDTH, FRAME_HEIGHT);
    }

    private void initializeWidgets() {
        getContentPane().setLayout(new BorderLayout(0, 10));
        getContentPane().add(getMainPanel(), BorderLayout.CENTER);
        getContentPane().add(getFooterPanel(), BorderLayout.PAGE_END);
    }

    private JPanel getMainPanel() {
        if (mainPanel == null) {
            mainPanel = new JPanel(new GridLayout(0, 5));
            mainPanel.add(new JLabel());
            mainPanel.add(getAttackerPanel());
            mainPanel.add(getAttackerWinChanceLabel());
            mainPanel.add(getDefenderPanel());
            mainPanel.add(new JLabel());
        }
        return mainPanel;
    }

    private JPanel getFooterPanel() {
        if (footerPanel == null) {
            footerPanel = new JPanel();
            footerPanel.add(getConfirmButton());
            footerPanel.add(getCancelButton());
        }
        return footerPanel;
    }

    private JButton getConfirmButton() {
        if (confirmButton == null) {
            confirmButton = new JButton();
        }
        return confirmButton;
    }

    private JButton getCancelButton() {
        if (cancelButton == null) {
            cancelButton = new JButton();
        }
        return cancelButton;
    }

    private UnitFightingInformationPanel getAttackerPanel() {
        if (attackerPanel == null) {
            attackerPanel = new UnitFightingInformationPanel();
            attackerPanel.setPosition("Attacker");
        }
        return attackerPanel;
    }

    private UnitFightingInformationPanel getDefenderPanel() {
        if (defenderPanel == null) {
            defenderPanel = new UnitFightingInformationPanel();
            defenderPanel.setPosition("Defender");
        }
        return defenderPanel;
    }

    private JLabel getAttackerWinChanceLabel() {
        if (attackerWinChanceLabel == null) {
            attackerWinChanceLabel = new JLabel();
            attackerWinChanceLabel.setFont(attackerWinChanceLabel.getFont().deriveFont(45f));
        }
        return attackerWinChanceLabel;
    }
}

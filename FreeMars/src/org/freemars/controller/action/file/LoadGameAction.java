package org.freemars.controller.action.file;

import java.awt.event.ActionEvent;
import java.io.File;
import java.util.Iterator;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;

import org.freemars.ai.AIPlayer;
import org.freemars.ai.DecisionModel;
import org.freemars.ai.ExpeditionaryForceDecisionModel;
import org.freemars.ai.ExpeditionaryForcePlayer;
import org.freemars.controller.FreeMarsController;
import org.freemars.controller.action.DisplayHelpTipAction;
import org.freemars.ui.util.FreeMarsOptionPane;
import org.freemars.ui.util.FreeMarsSaveFileChooser;
import org.freemars.util.SaveLoadUtility;
import org.freerealm.Realm;
import org.freerealm.command.SetActivePlayerCommand;
import org.freerealm.player.Player;

/**
 *
 * @author Deniz ARIKAN
 */
public class LoadGameAction extends AbstractAction {

    private final FreeMarsController freeMarsController;

    public LoadGameAction(FreeMarsController controller) {
        super("Load");
        this.freeMarsController = controller;
    }

    public void actionPerformed(ActionEvent e) {
        FreeMarsSaveFileChooser freeMarsSaveFileChooser = new FreeMarsSaveFileChooser();
        if (freeMarsSaveFileChooser.showOpenDialog(freeMarsController.getCurrentFrame()) == JFileChooser.APPROVE_OPTION) {
            File file = freeMarsSaveFileChooser.getSelectedFile();
            boolean result = SaveLoadUtility.loadGameFromFile(freeMarsController, file);
            if (result) {
                Iterator<Player> playerIterator = freeMarsController.getFreeMarsModel().getPlayersIterator();
                while (playerIterator.hasNext()) {
                    Player player = playerIterator.next();
                    if (player instanceof AIPlayer) {
                        ((AIPlayer) player).setDecisionModel(new DecisionModel(freeMarsController, (AIPlayer) player));
                    }
                    if (player instanceof ExpeditionaryForcePlayer) {
                        ((ExpeditionaryForcePlayer) player).setDecisionModel(new ExpeditionaryForceDecisionModel(freeMarsController, (ExpeditionaryForcePlayer) player));
                    }
                }
                freeMarsController.displayGameFrame();
                Realm realm = freeMarsController.getFreeMarsModel().getRealm();
                freeMarsController.execute(new SetActivePlayerCommand(realm, freeMarsController.getFreeMarsModel().getActivePlayer()));
                boolean displayTipsOnStartup = Boolean.valueOf(freeMarsController.getFreeMarsModel().getFreeMarsPreferences().getProperty("display_tips_on_startup"));
                if (displayTipsOnStartup) {
                    new DisplayHelpTipAction(freeMarsController).actionPerformed(null);
                } else {
                    FreeMarsOptionPane.showMessageDialog(freeMarsController.getCurrentFrame(), "Load complete");
                }
            } else {
                FreeMarsOptionPane.showMessageDialog(freeMarsController.getCurrentFrame(), "Error while loading game");
            }
        }
    }
}

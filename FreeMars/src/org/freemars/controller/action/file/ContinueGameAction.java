package org.freemars.controller.action.file;

import java.awt.event.ActionEvent;
import java.io.File;
import java.util.Iterator;

import javax.swing.AbstractAction;

import org.apache.log4j.Logger;
import org.freemars.ai.AIPlayer;
import org.freemars.ai.DecisionModel;
import org.freemars.ai.ExpeditionaryForceDecisionModel;
import org.freemars.ai.ExpeditionaryForcePlayer;
import org.freemars.controller.FreeMarsController;
import org.freemars.controller.action.DisplayHelpTipAction;
import org.freemars.ui.util.FreeMarsOptionPane;
import org.freemars.ui.util.FreeMarsSaveFilenameFilter;
import org.freemars.util.SaveLoadUtility;
import org.freerealm.Realm;
import org.freerealm.command.SetActivePlayerCommand;
import org.freerealm.player.Player;

/**
 *
 * @author Deniz ARIKAN
 */
public class ContinueGameAction extends AbstractAction {

    private final FreeMarsController freeMarsController;

    public ContinueGameAction(FreeMarsController controller) {
        super("Continue");
        this.freeMarsController = controller;
    }

    public void actionPerformed(ActionEvent e) {
        String userHomeDirectory = System.getProperty("user.home");
        File saveFileDirectory = new File(userHomeDirectory + System.getProperty("file.separator") + "FreeMars" + System.getProperty("file.separator"));
        String[] fileNames = saveFileDirectory.list(new FreeMarsSaveFilenameFilter());
        File fileToLoad = null;
        for (int i = 0; i < fileNames.length; i++) {
            File saveFile = new File(saveFileDirectory, fileNames[i]);
            fileToLoad = fileToLoad == null || saveFile.lastModified() > fileToLoad.lastModified() ? saveFile : fileToLoad;
        }
        if (fileToLoad != null) {
            Logger.getLogger(ContinueGameAction.class).info("Continuing game from file " + fileToLoad + ".");
            boolean result = SaveLoadUtility.loadGameFromFile(freeMarsController, fileToLoad);
            if (result) {
                Iterator<Player> playerIterator = freeMarsController.getFreeMarsModel().getRealm().getPlayerManager().getPlayersIterator();
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
                Logger.getLogger(ContinueGameAction.class).info("Continue game complete.");
                boolean displayTipsOnStartup = Boolean.valueOf(freeMarsController.getFreeMarsModel().getFreeMarsPreferences().getProperty("display_tips_on_startup"));
                if (displayTipsOnStartup) {
                    new DisplayHelpTipAction(freeMarsController).actionPerformed(null);
                } else {
                    FreeMarsOptionPane.showMessageDialog(freeMarsController.getCurrentFrame(), "Load complete");
                }
            } else {
                FreeMarsOptionPane.showMessageDialog(freeMarsController.getCurrentFrame(), "Error while loading game");
            }
        } else {
            FreeMarsOptionPane.showMessageDialog(freeMarsController.getCurrentFrame(), "No Free Mars save game files found");
            Logger.getLogger(ContinueGameAction.class).info("No Free Mars save game files found.");
        }

    }
}

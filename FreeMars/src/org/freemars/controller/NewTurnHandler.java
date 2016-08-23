package org.freemars.controller;

import java.util.Iterator;

import org.freemars.controller.action.DisplayVictoryDialogAction;
import org.freemars.controller.handler.PostCommandHandler;
import org.freemars.player.FreeMarsPlayer;
import org.freemars.ui.util.FreeMarsOptionPane;
import org.freerealm.command.SetDiplomaticStatusCommand;
import org.freerealm.diplomacy.PlayerRelation;
import org.freerealm.executor.CommandResult;
import org.freerealm.player.Player;

/**
 * @author Deniz ARIKAN
 */
public class NewTurnHandler implements PostCommandHandler {

	public void handleUpdate(FreeMarsController freeMarsController, CommandResult commandResult) {
		freeMarsController.getAutosaveManager().manageAutosave();
		Iterator<Player> iterator = freeMarsController.getFreeMarsModel().getPlayersIterator();
		while (iterator.hasNext()) {
			Player player = iterator.next();
			FreeMarsPlayer freeMarsPlayer = (FreeMarsPlayer) player;
			if (!freeMarsPlayer.isContinuingGameAfterVictory() && freeMarsController.getFreeMarsModel().hasCompletedObjectives(freeMarsPlayer)) {
				new DisplayVictoryDialogAction(freeMarsController).actionPerformed(null);
				return;
			}
		}
		String diplomaticRelationsEnableTurnProperty = freeMarsController.getFreeMarsModel().getRealm().getProperty("diplomatic_relations_enable_turn");
		int diplomaticRelationsEnableTurn = Integer.parseInt(diplomaticRelationsEnableTurnProperty);
		int currentTurn = freeMarsController.getFreeMarsModel().getNumberOfTurns();
		if (currentTurn == diplomaticRelationsEnableTurn) {
			enableDiplomaticRelations(freeMarsController);
			FreeMarsOptionPane.showMessageDialog(freeMarsController.getCurrentFrame(), "Diplomatic relations enabled between colonists", "Diplomatic relations");
		}
	}

	private void enableDiplomaticRelations(FreeMarsController freeMarsController) {
		Iterator<Player> fromPlayerIterator = freeMarsController.getFreeMarsModel().getPlayersIterator();
		while (fromPlayerIterator.hasNext()) {
			Player fromPlayer = fromPlayerIterator.next();
			Iterator<Player> toPlayerIterator = freeMarsController.getFreeMarsModel().getPlayersIterator();
			while (toPlayerIterator.hasNext()) {
				Player toPlayer = toPlayerIterator.next();
				if (!fromPlayer.equals(toPlayer)) {
					PlayerRelation currentPlayerRelation = fromPlayer.getDiplomacy().getPlayerRelation(toPlayer);
					if (currentPlayerRelation.getStatus() == PlayerRelation.NO_DIPLOMACY_ALLOWED) {
						SetDiplomaticStatusCommand setDiplomaticStatusCommand = new SetDiplomaticStatusCommand(freeMarsController.getFreeMarsModel().getRealm(), fromPlayer, toPlayer,
								PlayerRelation.NO_CONTACT);
						freeMarsController.addCommandToQueue(setDiplomaticStatusCommand);
					}
				}
			}
		}
	}
}

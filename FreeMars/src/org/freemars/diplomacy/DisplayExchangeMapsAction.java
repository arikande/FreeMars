package org.freemars.diplomacy;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import org.freemars.controller.FreeMarsController;
import org.freemars.ui.util.FreeMarsOptionPane;
import org.freerealm.Utility;
import org.freerealm.command.ExchangeExploredCoordinatesCommand;
import org.freerealm.player.Player;

/**
 *
 * @author arikande
 */
public class DisplayExchangeMapsAction extends AbstractAction {

    private final FreeMarsController freeMarsController;
    private final Player toPlayer;
    private final Player fromPlayer;

    public DisplayExchangeMapsAction(FreeMarsController freeMarsController, Player fromPlayer, Player toPlayer) {
        super("Exchange maps");
        this.freeMarsController = freeMarsController;
        this.fromPlayer = fromPlayer;
        this.toPlayer = toPlayer;
    }

    public void actionPerformed(ActionEvent ae) {
        int unexploredCoordinateCountOfQueryingPlayer = Utility.getUnexploredCoordinatesOfPlayer(toPlayer, fromPlayer).size();
        if (unexploredCoordinateCountOfQueryingPlayer == 0) {
            FreeMarsOptionPane.showMessageDialog(freeMarsController.getCurrentFrame(), "You already know all of our explored map");
            return;
        }
        int attitude = toPlayer.getDiplomacy().getPlayerRelation(fromPlayer).getAttitude();
        if (attitude < 700) {
            FreeMarsOptionPane.showMessageDialog(freeMarsController.getCurrentFrame(), "We do not wish to exchange maps at this time");
        } else {
            FreeMarsOptionPane.showMessageDialog(freeMarsController.getCurrentFrame(), "We will share our explored map");
            ExchangeExploredCoordinatesCommand exchangeExploredCoordinatesCommand
                    = new ExchangeExploredCoordinatesCommand(toPlayer, fromPlayer);
            freeMarsController.execute(exchangeExploredCoordinatesCommand);
        }
    }

}

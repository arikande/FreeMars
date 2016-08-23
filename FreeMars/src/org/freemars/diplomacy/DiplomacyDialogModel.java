package org.freemars.diplomacy;

import java.util.Observable;
import org.freemars.model.FreeMarsModel;
import org.freerealm.player.Player;

/**
 *
 * @author arikande
 */
public class DiplomacyDialogModel extends Observable {

    private FreeMarsModel model;
    private Player player;
    private Player relatedPlayer;

    public FreeMarsModel getFreeMarsModel() {
        return model;
    }

    public void setFreeMarsModel(FreeMarsModel model) {
        this.model = model;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Player getRelatedPlayer() {
        return relatedPlayer;
    }

    public void setRelatedPlayer(Player relatedPlayer) {
        this.relatedPlayer = relatedPlayer;
    }

}

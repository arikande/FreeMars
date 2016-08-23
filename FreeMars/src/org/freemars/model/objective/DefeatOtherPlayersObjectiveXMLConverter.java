package org.freemars.model.objective;

import org.freemars.model.FreeMarsModel;
import org.freerealm.Realm;
import org.freerealm.xmlwrapper.XMLConverter;
import org.w3c.dom.Node;

/**
 *
 * @author arikande
 */
public class DefeatOtherPlayersObjectiveXMLConverter implements XMLConverter<Objective> {

    private FreeMarsModel freeMarsViewModel;
    private DefeatOtherPlayersObjective defeatOtherPlayersObjective;

    public DefeatOtherPlayersObjectiveXMLConverter(){
    }

    public DefeatOtherPlayersObjectiveXMLConverter(FreeMarsModel freeMarsViewModel, DefeatOtherPlayersObjective defeatOtherPlayersObjective) {
        this.freeMarsViewModel = freeMarsViewModel;
        this.defeatOtherPlayersObjective = defeatOtherPlayersObjective;
    }

    public String toXML(Objective object) {
        return "<DefeatOtherPlayers/>";
    }

    public DefeatOtherPlayersObjective initializeFromNode(Realm realm, Node node) {
        return new DefeatOtherPlayersObjective();
    }
}

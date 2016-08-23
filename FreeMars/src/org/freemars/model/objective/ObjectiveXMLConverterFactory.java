package org.freemars.model.objective;

import org.freemars.model.FreeMarsModel;
import org.freerealm.xmlwrapper.XMLConverter;

/**
 *
 * @author Deniz ARIKAN
 */
public class ObjectiveXMLConverterFactory {

    public XMLConverter<Objective> getXMLConverter(FreeMarsModel freeMarsViewModel, Objective objective) {
        if (objective instanceof DeclareAndDefendIndependenceObjective) {
            return new DeclareAndDefendIndependenceObjectiveXMLConverter();
        } else if (objective instanceof DefeatOtherPlayersObjective) {
            return new DefeatOtherPlayersObjectiveXMLConverter(freeMarsViewModel, (DefeatOtherPlayersObjective) objective);
        } else if (objective instanceof ReachPopulationObjective) {
            return new ReachPopulationObjectiveXMLConverter(freeMarsViewModel, (ReachPopulationObjective) objective);
        } else if (objective instanceof ReachTreasuryObjective) {
            return new ReachTreasuryObjectiveXMLConverter((ReachTreasuryObjective) objective);
        }
        return null;
    }
}

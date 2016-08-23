package org.freemars.model.objective;

import org.freemars.model.FreeMarsModel;
import org.freerealm.Realm;
import org.freerealm.xmlwrapper.XMLConverter;
import org.w3c.dom.Node;

/**
 *
 * @author arikande
 */
public class ReachPopulationObjectiveXMLConverter implements XMLConverter<Objective> {

    private FreeMarsModel freeMarsViewModel;
    private ReachPopulationObjective populationObjective;

    public ReachPopulationObjectiveXMLConverter() {
    }

    public ReachPopulationObjectiveXMLConverter(FreeMarsModel freeMarsViewModel, ReachPopulationObjective populationObjective) {
        this.freeMarsViewModel = freeMarsViewModel;
        this.populationObjective = populationObjective;
    }

    public String toXML(Objective object) {
        StringBuilder xml = new StringBuilder();
        xml.append("<ReachPopulation>");
        xml.append(populationObjective.getTargetPopulation());
        xml.append("</ReachPopulation>");
        return xml.toString();
    }

    public ReachPopulationObjective initializeFromNode(Realm realm, Node node) {
        String targetPopulationString = node.getFirstChild().getNodeValue();
        int targetPopulation = Integer.parseInt(targetPopulationString);
        return new ReachPopulationObjective(targetPopulation);
    }
}

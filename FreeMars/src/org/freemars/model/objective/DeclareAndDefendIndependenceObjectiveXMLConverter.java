package org.freemars.model.objective;

import org.freerealm.Realm;
import org.freerealm.xmlwrapper.XMLConverter;
import org.w3c.dom.Node;

/**
 *
 * @author arikande
 */
public class DeclareAndDefendIndependenceObjectiveXMLConverter implements XMLConverter<Objective> {

    public String toXML(Objective object) {
        return "<DeclareAndDefendIndependence/>";
    }

    public DeclareAndDefendIndependenceObjective initializeFromNode(Realm realm, Node node) {
        return new DeclareAndDefendIndependenceObjective();
    }
}

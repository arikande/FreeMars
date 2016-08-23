package org.freemars.unit.automater;

import org.freemars.controller.FreeMarsController;
import org.freerealm.Realm;
import org.freerealm.xmlwrapper.TagManager;
import org.freerealm.xmlwrapper.XMLConverter;
import org.w3c.dom.Node;

/**
 *
 * @author Deniz ARIKAN
 */
public class ScoutAutomaterXMLConverter implements XMLConverter<ScoutAutomater> {

    public String toXML(ScoutAutomater scoutAutomater) {
        StringBuilder xml = new StringBuilder();
        xml.append("<freeMarsScoutAutomater />\n");
        return xml.toString();
    }

    public ScoutAutomater initializeFromNode(Realm realm, Node node) {
        ScoutAutomater scoutAutomater = new ScoutAutomater();
        FreeMarsController freeMarsController = (FreeMarsController) TagManager.getObjectFromPool("freeMarsController");
        scoutAutomater.setFreeMarsController(freeMarsController);
        return scoutAutomater;
    }
}

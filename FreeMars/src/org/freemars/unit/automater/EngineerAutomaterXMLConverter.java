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
public class EngineerAutomaterXMLConverter implements XMLConverter<EngineerAutomater> {

    public String toXML(EngineerAutomater engineerAutomater) {
        StringBuilder xml = new StringBuilder();
        xml.append("<freeMarsEngineerAutomater />\n");
        return xml.toString();
    }

    public EngineerAutomater initializeFromNode(Realm realm, Node node) {
        EngineerAutomater engineerAutomater = new EngineerAutomater();
        FreeMarsController freeMarsController = (FreeMarsController) TagManager.getObjectFromPool("freeMarsController");
        engineerAutomater.setFreeMarsController(freeMarsController);
        return engineerAutomater;
    }
}

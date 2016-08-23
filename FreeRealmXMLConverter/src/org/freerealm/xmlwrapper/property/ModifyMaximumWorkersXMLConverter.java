package org.freerealm.xmlwrapper.property;

import org.freerealm.Realm;
import org.freerealm.property.ModifyMaximumWorkers;
import org.freerealm.xmlwrapper.XMLConverter;
import org.w3c.dom.Node;

/**
 *
 * @author Deniz ARIKAN
 */
public class ModifyMaximumWorkersXMLConverter implements XMLConverter<ModifyMaximumWorkers> {

    public String toXML(ModifyMaximumWorkers modifierMaximumWorkers) {
        return "<ModifyMaximumWorkers modifier=\"" + modifierMaximumWorkers.getModifier() + "\" />";
    }

    public ModifyMaximumWorkers initializeFromNode(Realm realm, Node node) {
        ModifyMaximumWorkers modifyMaximumWorkers = new ModifyMaximumWorkers();
        String modifierString = node.getAttributes().getNamedItem("modifier").getNodeValue();
        modifyMaximumWorkers.setModifier(Integer.parseInt(modifierString));
        return modifyMaximumWorkers;
    }
}

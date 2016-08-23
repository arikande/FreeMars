package org.freerealm.xmlwrapper.property;

import org.freerealm.Realm;
import org.freerealm.property.ModifyDefence;
import org.freerealm.xmlwrapper.XMLConverter;
import org.w3c.dom.Node;

/**
 *
 * @author Deniz ARIKAN
 */
public class ModifyDefenceXMLConverter implements XMLConverter<ModifyDefence> {

    public String toXML(ModifyDefence increaseDefence) {
        return "<ModifyDefence modifier=\"" + increaseDefence.getModifier() + "\" />";
    }

    public ModifyDefence initializeFromNode(Realm realm, Node node) {
        ModifyDefence modifyDefence = new ModifyDefence();
        String modifierString = node.getAttributes().getNamedItem("modifier").getNodeValue();
        modifyDefence.setModifier(Integer.parseInt(modifierString));
        return modifyDefence;
    }
}

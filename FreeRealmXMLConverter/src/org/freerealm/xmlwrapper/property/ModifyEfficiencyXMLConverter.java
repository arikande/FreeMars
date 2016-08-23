package org.freerealm.xmlwrapper.property;

import org.freerealm.Realm;
import org.freerealm.property.ModifyEfficiency;
import org.freerealm.xmlwrapper.XMLConverter;
import org.w3c.dom.Node;

/**
 *
 * @author Deniz ARIKAN
 */
public class ModifyEfficiencyXMLConverter implements XMLConverter<ModifyEfficiency> {

    public String toXML(ModifyEfficiency modifyEfficiency) {
        return "<ModifyEfficiency modifier=\"" + modifyEfficiency.getModifier() + "\" />";
    }

    public ModifyEfficiency initializeFromNode(Realm realm, Node node) {
        ModifyEfficiency modifyEfficiency = new ModifyEfficiency();
        String modifierString = node.getAttributes().getNamedItem("modifier").getNodeValue();
        modifyEfficiency.setModifier(Integer.parseInt(modifierString));
        return modifyEfficiency;
    }
}

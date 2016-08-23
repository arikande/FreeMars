package org.freerealm.xmlwrapper.property;

import org.freerealm.Realm;
import org.freerealm.property.SetMovementCostProperty;
import org.freerealm.xmlwrapper.XMLConverter;
import org.w3c.dom.Node;

/**
 *
 * @author Deniz ARIKAN
 */
public class SetMovementCostPropertyXMLConverter implements XMLConverter<SetMovementCostProperty> {

    public String toXML(SetMovementCostProperty setMovementCost) {
        return "<" + SetMovementCostProperty.NAME + " movementCost=\"" + setMovementCost.getMovementCost() + "\" />";
    }

    public SetMovementCostProperty initializeFromNode(Realm realm, Node node) {
        SetMovementCostProperty setMovementCost = new SetMovementCostProperty();
        String movementCostString = node.getAttributes().getNamedItem("movementCost").getNodeValue();
        setMovementCost.setMovementCost(Float.parseFloat(movementCostString));
        return setMovementCost;
    }
}

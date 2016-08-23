package org.freerealm.xmlwrapper.property;

import org.freerealm.Realm;
import org.freerealm.property.AllowMovementPropery;
import org.freerealm.xmlwrapper.XMLConverter;
import org.w3c.dom.Node;

/**
 *
 * @author Deniz ARIKAN
 */
public class AllowMovementPropertyXMLConverter implements XMLConverter<AllowMovementPropery> {

    public String toXML(AllowMovementPropery allowMovement) {
        StringBuilder xml = new StringBuilder();
        xml.append("<" + AllowMovementPropery.NAME + "/>");
        return xml.toString();
    }

    public AllowMovementPropery initializeFromNode(Realm realm, Node node) {
        return new AllowMovementPropery();
    }
}

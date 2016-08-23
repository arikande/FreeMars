package org.freerealm.xmlwrapper.property;

import org.freerealm.Realm;
import org.freerealm.property.SetStartupUnitCountProperty;
import org.freerealm.unit.FreeRealmUnitType;
import org.freerealm.xmlwrapper.XMLConverter;
import org.freerealm.xmlwrapper.XMLConverterUtility;
import org.w3c.dom.Node;

/**
 *
 * @author Deniz ARIKAN
 */
public class SetStartupUnitCountPropertyXMLConverter implements XMLConverter<SetStartupUnitCountProperty> {

    public String toXML(SetStartupUnitCountProperty setStartupUnitCount) {
        StringBuilder xml = new StringBuilder();
        xml.append("<" + SetStartupUnitCountProperty.NAME + ">\n");
        xml.append("<unitType>" + setStartupUnitCount.getUnitType().getId() + "</unitType>");
        xml.append("<count>" + setStartupUnitCount.getCount() + "</count>");
        xml.append("</" + SetStartupUnitCountProperty.NAME + ">");
        return xml.toString();
    }

    public SetStartupUnitCountProperty initializeFromNode(Realm realm, Node node) {
        SetStartupUnitCountProperty setStartupUnitCount = new SetStartupUnitCountProperty();
        Node unitTypeNode = XMLConverterUtility.findNode(node, "unitType");
        FreeRealmUnitType unitType = realm.getUnitTypeManager().getUnitType(Integer.parseInt(unitTypeNode.getFirstChild().getNodeValue()));
        setStartupUnitCount.setUnitType(unitType);
        Node countNode = XMLConverterUtility.findNode(node, "count");
        setStartupUnitCount.setCount(Integer.parseInt(countNode.getFirstChild().getNodeValue()));
        return setStartupUnitCount;
    }
}

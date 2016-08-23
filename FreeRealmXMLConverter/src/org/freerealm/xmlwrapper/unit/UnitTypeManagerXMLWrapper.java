package org.freerealm.xmlwrapper.unit;

import java.util.Iterator;
import org.freerealm.Realm;
import org.freerealm.unit.FreeRealmUnitType;
import org.freerealm.unit.UnitTypeManager;
import org.freerealm.xmlwrapper.XMLWrapper;
import org.w3c.dom.Node;

/**
 *
 * @author Deniz ARIKAN
 */
public class UnitTypeManagerXMLWrapper implements XMLWrapper {

    private final UnitTypeManager unitTypeManager;

    public UnitTypeManagerXMLWrapper(UnitTypeManager unitTypeManager) {
        this.unitTypeManager = unitTypeManager;
    }

    public String toXML() {
        StringBuilder xml = new StringBuilder();
        xml.append("<UnitTypes>\n");
        for (Iterator<FreeRealmUnitType> iterator = unitTypeManager.getUnitTypesIterator(); iterator.hasNext();) {
            FreeRealmUnitType unitType = iterator.next();
            xml.append((new UnitTypeXMLConverter()).toXML(unitType) + "\n");
        }
        xml.append("</UnitTypes>");
        return xml.toString();
    }

    public void initializeFromNode(Realm realm, Node node) {
        for (Node unitTypeNode = node.getFirstChild(); unitTypeNode != null; unitTypeNode = unitTypeNode.getNextSibling()) {
            if (unitTypeNode.getNodeType() == Node.ELEMENT_NODE) {
                FreeRealmUnitType unitType = new UnitTypeXMLConverter().initializeFromNode(realm, unitTypeNode);
                unitTypeManager.getUnitTypes().put(unitType.getId(), unitType);
            }
        }
    }
}

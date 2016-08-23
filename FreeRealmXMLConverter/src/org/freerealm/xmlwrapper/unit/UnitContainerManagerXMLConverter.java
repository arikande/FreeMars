package org.freerealm.xmlwrapper.unit;

import java.util.Iterator;
import org.freerealm.Realm;
import org.freerealm.UnitContainerManager;
import org.freerealm.unit.Unit;
import org.freerealm.xmlwrapper.XMLConverter;
import org.w3c.dom.Node;

/**
 *
 * @author Deniz ARIKAN
 */
public class UnitContainerManagerXMLConverter implements XMLConverter<UnitContainerManager> {

    private final Unit containerUnit;

    public UnitContainerManagerXMLConverter(Unit containerUnit) {
        this.containerUnit = containerUnit;
    }

    public String toXML(UnitContainerManager unitContainerManager) {
        StringBuilder xml = new StringBuilder();
        if (unitContainerManager.getNumberOfUnits() > 0) {
            xml.append("<UnitContainerManager>\n");
            Iterator<Integer> iterator = unitContainerManager.getUnitsIterator();
            while (iterator.hasNext()) {
                int unitId = iterator.next();
                xml.append("<Unit>" + unitId + "</Unit>");
            }
            xml.append("</UnitContainerManager>");
        }
        return xml.toString();
    }

    public UnitContainerManager initializeFromNode(Realm realm, Node node) {
        UnitContainerManager unitContainerManager = new UnitContainerManager(containerUnit);
        unitContainerManager.clear();
        for (Node subNode = node.getFirstChild(); subNode != null; subNode = subNode.getNextSibling()) {
            if (subNode.getNodeType() == Node.ELEMENT_NODE) {
                int unitId = Integer.parseInt(subNode.getFirstChild().getNodeValue());
                unitContainerManager.addUnit(unitId);
            }
        }
        return unitContainerManager;
    }
}

package org.freerealm.xmlwrapper.unit;

import java.util.Iterator;
import org.freerealm.Realm;
import org.freerealm.player.UnitManager;
import org.freerealm.unit.Unit;
import org.freerealm.xmlwrapper.XMLWrapper;
import org.w3c.dom.Node;

/**
 *
 * @author Deniz ARIKAN
 */
public class UnitManagerXMLWrapper implements XMLWrapper {

    UnitManager unitManager;

    public UnitManagerXMLWrapper(UnitManager unitManager) {
        this.unitManager = unitManager;
    }

    public String toXML() {
        StringBuilder xml = new StringBuilder();
        xml.append("<units>\n");
        if (unitManager.getActiveUnit() != null) {
            xml.append("<activeUnit>" + unitManager.getActiveUnit().getId() + "</activeUnit>\n");
        }
        Iterator<Unit> unitIterator = unitManager.getUnitsIterator();
        while (unitIterator.hasNext()) {
            Unit unit = unitIterator.next();
            UnitXMLWrapper unitXMLWrapper = new UnitXMLWrapper(unit);
            xml.append(unitXMLWrapper.toXML());
        }
        xml.append("</units>");
        return xml.toString();
    }

    public void initializeFromNode(Realm realm, Node node) {
        int activeUnitId = -1;
        for (Node subNode = node.getFirstChild(); subNode != null; subNode = subNode.getNextSibling()) {
            if (subNode.getNodeType() == Node.ELEMENT_NODE) {
                if (subNode.getNodeName().equals("Unit")) {
                    Unit unit = new Unit(realm);
                    UnitXMLWrapper unitXMLWrapper = new UnitXMLWrapper(unit);
                    unit.setPlayer(unitManager.getPlayer());
                    unitXMLWrapper.initializeFromNode(realm, subNode);
                    unitManager.addUnit(unit);
                } else if (subNode.getNodeName().equals("activeUnit")) {
                    activeUnitId = Integer.parseInt(subNode.getFirstChild().getNodeValue());
                }
            }
        }
        if (activeUnitId != -1) {
            unitManager.setActiveUnit(unitManager.getUnit(activeUnitId));
        }
    }
}

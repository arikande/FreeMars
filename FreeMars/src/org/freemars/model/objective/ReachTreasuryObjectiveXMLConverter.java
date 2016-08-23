package org.freemars.model.objective;

import org.freerealm.Realm;
import org.freerealm.xmlwrapper.XMLConverter;
import org.w3c.dom.Node;

/**
 *
 * @author arikande
 */
public class ReachTreasuryObjectiveXMLConverter implements XMLConverter<Objective> {

    private ReachTreasuryObjective treasuryObjective;

    public ReachTreasuryObjectiveXMLConverter() {
    }

    public ReachTreasuryObjectiveXMLConverter(ReachTreasuryObjective treasuryObjective) {
        this.treasuryObjective = treasuryObjective;
    }

    public String toXML(Objective object) {
        StringBuilder xml = new StringBuilder();
        xml.append("<ReachTreasury>");
        xml.append(treasuryObjective.getTargetTreasury());
        xml.append("</ReachTreasury>");
        return xml.toString();
    }

    public ReachTreasuryObjective initializeFromNode(Realm realm, Node node) {
        String targetTreasuryString = node.getFirstChild().getNodeValue();
        int targetTreasury = Integer.parseInt(targetTreasuryString);
        return new ReachTreasuryObjective(targetTreasury);
    }
}

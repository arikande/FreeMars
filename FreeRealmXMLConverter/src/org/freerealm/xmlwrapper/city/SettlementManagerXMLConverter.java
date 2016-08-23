package org.freerealm.xmlwrapper.city;

import java.util.Iterator;
import org.freerealm.Realm;
import org.freerealm.player.FreeRealmPlayer;
import org.freerealm.player.SettlementManager;
import org.freerealm.settlement.Settlement;
import org.freerealm.xmlwrapper.TagManager;
import org.freerealm.xmlwrapper.XMLConverter;
import org.w3c.dom.Node;

/**
 *
 * @author Deniz ARIKAN
 */
public class SettlementManagerXMLConverter implements XMLConverter<SettlementManager> {

    private final FreeRealmPlayer freeRealmPlayer;

    public SettlementManagerXMLConverter(FreeRealmPlayer freeRealmPlayer) {
        this.freeRealmPlayer = freeRealmPlayer;
    }

    public String toXML(SettlementManager settlementManager) {
        StringBuilder xml = new StringBuilder();
        xml.append("<settlements>\n");
        Iterator<Settlement> settlementIterator = settlementManager.getSettlementsIterator();
        while (settlementIterator.hasNext()) {
            Settlement settlement = settlementIterator.next();
            String xMLConverterName = TagManager.getXMLConverterName(settlement.getClass());
            try {
                XMLConverter<Settlement> xMLConverter = (XMLConverter<Settlement>) Class.forName(xMLConverterName).newInstance();
                xml.append(xMLConverter.toXML(settlement) + "\n");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        xml.append("</settlements>");
        return xml.toString();
    }

    public SettlementManager initializeFromNode(Realm realm, Node node) {
        SettlementManager settlementManager = new SettlementManager(freeRealmPlayer);
        for (Node subNode = node.getFirstChild(); subNode != null; subNode = subNode.getNextSibling()) {
            if (subNode.getNodeType() == Node.ELEMENT_NODE) {
                String xMLConverterName = TagManager.getXMLConverterName(subNode.getNodeName());
                try {
                    XMLConverter<Settlement> xMLConverter = (XMLConverter<Settlement>) Class.forName(xMLConverterName).newInstance();
                    Settlement settlement = xMLConverter.initializeFromNode(realm, subNode);
                    settlement.setPlayer(settlementManager.getPlayer());
                    settlementManager.addSettlement(settlement);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
        return settlementManager;
    }
}

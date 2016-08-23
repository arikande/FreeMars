package org.freemars.player;

import java.util.Iterator;
import org.freerealm.Realm;
import org.freerealm.resource.Resource;
import org.freerealm.xmlwrapper.XMLConverter;
import org.freerealm.xmlwrapper.XMLConverterUtility;
import org.freerealm.xmlwrapper.player.FreeRealmPlayerXMLConverter;
import org.w3c.dom.Node;

/**
 *
 * @author Deniz ARIKAN
 */
public class FreeMarsPlayerXMLConverter implements XMLConverter<FreeMarsPlayer> {

    public String toXML(FreeMarsPlayer player) {
        StringBuilder xml = new StringBuilder();
        xml.append("<FreeMarsPlayer>\n");
        xml.append(getInnerXML(player));
        xml.append("</FreeMarsPlayer>");
        return xml.toString();
    }

    public FreeMarsPlayer initializeFromNode(Realm realm, Node node) {
        FreeMarsPlayer player = new FreeMarsPlayer(realm);
        populatePlayerFromNode(player, realm, node);
        return player;
    }

    public static String getInnerXML(FreeMarsPlayer player) {
        StringBuilder xml = new StringBuilder();
        xml.append(FreeRealmPlayerXMLConverter.getInnerXML(player));
        xml.append("\n");
        xml.append("<earthTaxRate>");
        xml.append(player.getEarthTaxRate());
        xml.append("</earthTaxRate>\n");
        xml.append("<declaredIndependence>");
        xml.append(player.hasDeclaredIndependence());
        xml.append("</declaredIndependence>\n");
        xml.append("<independenceTurn>");
        xml.append(player.getIndependenceTurn());
        xml.append("</independenceTurn>\n");
        xml.append("<receivedFreeStarport>");
        xml.append(player.hasReceivedFreeStarport());
        xml.append("</receivedFreeStarport>\n");
        xml.append("<receivedFreeColonizer>");
        xml.append(player.hasReceivedFreeColonizer());
        xml.append("</receivedFreeColonizer>\n");
        xml.append("<receivedFreeTransporter>");
        xml.append(player.hasReceivedFreeTransporter());
        xml.append("</receivedFreeTransporter>\n");
        xml.append("<receivedFreeFinancialAid>");
        xml.append(player.hasReceivedFreeFinancialAid());
        xml.append("</receivedFreeFinancialAid>\n");
        xml.append("<totalTaxPaid>");
        xml.append(player.getTotalTaxPaid());
        xml.append("</totalTaxPaid>\n");
        xml.append("<autoEndTurnPossible>");
        xml.append(player.isAutoEndTurnPossible());
        xml.append("</autoEndTurnPossible>\n");
        xml.append("<continuingGameAfterVictory>");
        xml.append(player.isContinuingGameAfterVictory());
        xml.append("</continuingGameAfterVictory>\n");
        xml.append("<tradeDataMap>\n");
        Iterator<Resource> iterator = player.getResourceTradeDataIterator();
        while (iterator.hasNext()) {
            Resource resource = iterator.next();
            xml.append("<tradeData>\n");
            xml.append("<resourceId>");
            xml.append(resource.getId());
            xml.append("</resourceId>\n");
            ResourceTradeData resourceTradeData = player.getResourceTradeData(resource.getId());
            xml.append(new ResourceTradeDataXMLConverter().toXML(resourceTradeData));
            xml.append("</tradeData>\n");
        }
        xml.append("</tradeDataMap>\n");
        return xml.toString();
    }

    public static void populatePlayerFromNode(FreeMarsPlayer player, Realm realm, Node node) {
        FreeRealmPlayerXMLConverter.populatePlayerFromNode(player, realm, node);
        Node earthTaxRateNode = XMLConverterUtility.findNode(node, "earthTaxRate");
        if (earthTaxRateNode != null) {
            player.setEarthTaxRate(Byte.parseByte(earthTaxRateNode.getFirstChild().getNodeValue()));
        }
        Node declaredIndependenceNode = XMLConverterUtility.findNode(node, "declaredIndependence");
        if (declaredIndependenceNode != null) {
            player.setDeclaredIndependence(Boolean.valueOf(declaredIndependenceNode.getFirstChild().getNodeValue()));
        }
        Node independenceTurnNode = XMLConverterUtility.findNode(node, "independenceTurn");
        if (independenceTurnNode != null) {
            player.setIndependenceTurn(Integer.parseInt(independenceTurnNode.getFirstChild().getNodeValue()));
        }
        Node receivedFreeStarportNode = XMLConverterUtility.findNode(node, "receivedFreeStarport");
        if (receivedFreeStarportNode != null) {
            player.setReceivedFreeStarport(Boolean.valueOf(receivedFreeStarportNode.getFirstChild().getNodeValue()));
        }
        Node receivedFreeColonizerNode = XMLConverterUtility.findNode(node, "receivedFreeColonizer");
        if (receivedFreeColonizerNode != null) {
            player.setReceivedFreeColonizer(Boolean.valueOf(receivedFreeColonizerNode.getFirstChild().getNodeValue()));
        }
        Node receivedFreeTransporterNode = XMLConverterUtility.findNode(node, "receivedFreeTransporter");
        if (receivedFreeTransporterNode != null) {
            player.setReceivedFreeTransporter(Boolean.valueOf(receivedFreeTransporterNode.getFirstChild().getNodeValue()));
        }
        Node receivedFreeFinancialAidNode = XMLConverterUtility.findNode(node, "receivedFreeFinancialAid");
        if (receivedFreeFinancialAidNode != null) {
            player.setReceivedFreeFinancialAid(Boolean.valueOf(receivedFreeFinancialAidNode.getFirstChild().getNodeValue()));
        }
        Node totalTaxPaidNode = XMLConverterUtility.findNode(node, "totalTaxPaid");
        if (totalTaxPaidNode != null) {
            player.setTotalTaxPaid(Integer.parseInt(totalTaxPaidNode.getFirstChild().getNodeValue()));
        }
        Node continuingGameAfterVictoryNode = XMLConverterUtility.findNode(node, "continuingGameAfterVictory");
        if (continuingGameAfterVictoryNode != null) {
            player.setContinuingGameAfterVictory(Boolean.valueOf(continuingGameAfterVictoryNode.getFirstChild().getNodeValue()));
        }
        Node autoEndTurnPossibleNode = XMLConverterUtility.findNode(node, "autoEndTurnPossible");
        if (autoEndTurnPossibleNode != null) {
            player.setAutoEndTurnPossible(Boolean.valueOf(autoEndTurnPossibleNode.getFirstChild().getNodeValue()));
        }
        Node tradeDataMapNode = XMLConverterUtility.findNode(node, "tradeDataMap");
        if (tradeDataMapNode != null) {
            player.clearTradeData();
            for (Node tradeDataNode = tradeDataMapNode.getFirstChild(); tradeDataNode != null; tradeDataNode = tradeDataNode.getNextSibling()) {
                if (tradeDataNode.getNodeType() == Node.ELEMENT_NODE) {
                    Node resourceIdNode = XMLConverterUtility.findNode(tradeDataNode, "resourceId");
                    int resourceId = Integer.parseInt(resourceIdNode.getFirstChild().getNodeValue());
                    Resource resource = realm.getResourceManager().getResource(resourceId);
                    Node resourceTradeDataNode = XMLConverterUtility.findNode(tradeDataNode, "resourceTradeData");
                    ResourceTradeData resourceTradeData = new ResourceTradeDataXMLConverter().initializeFromNode(realm, resourceTradeDataNode);
                    player.addResourceTradeData(resource, resourceTradeData);
                }
            }
        }
    }

}

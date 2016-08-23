package org.freerealm.xmlwrapper;

import java.util.Iterator;
import org.freerealm.PopulationChangeManager;
import org.freerealm.Realm;
import org.freerealm.settlement.PopulationInterval;
import org.w3c.dom.Node;

/**
 *
 * @author Deniz ARIKAN
 */
public class PopulationChangeManagerXMLConverter implements XMLConverter<PopulationChangeManager> {

    public String toXML(PopulationChangeManager populationChangeManager) {
        StringBuilder xml = new StringBuilder();
        xml.append("<PopulationChangeManager>\n");
        xml.append("<populationIncreaseBasePercentData>\n");
        Iterator<PopulationInterval> iterator = populationChangeManager.getPopulationIncreaseBasePercentDataIterator();
        while (iterator.hasNext()) {
            xml.append("<populationIncreaseBasePercentDataItem>\n");
            PopulationInterval populationInterval = iterator.next();
            xml.append(new PopulationIntervalXMLConverter().toXML(populationInterval) + "\n");
            xml.append("<changePercent>" + populationChangeManager.getPopulationIncreaseBasePercent(populationInterval) + "</changePercent>");
            xml.append("</populationIncreaseBasePercentDataItem>\n");
        }
        xml.append("</populationIncreaseBasePercentData>\n");
        xml.append("<populationDecreaseBasePercentData>\n");
        iterator = populationChangeManager.getPopulationDecreaseBasePercentDataIterator();
        while (iterator.hasNext()) {
            xml.append("<populationDecreaseBasePercentDataItem>\n");
            PopulationInterval populationInterval = iterator.next();
            xml.append(new PopulationIntervalXMLConverter().toXML(populationInterval) + "\n");
            xml.append("<changePercent>" + populationChangeManager.getPopulationDecreaseBasePercent(populationInterval) + "</changePercent>");
            xml.append("</populationDecreaseBasePercentDataItem>\n");
        }
        xml.append("</populationDecreaseBasePercentData>\n");
        xml.append("</PopulationChangeManager>\n");
        return xml.toString();
    }

    public PopulationChangeManager initializeFromNode(Realm realm, Node node) {
        PopulationChangeManager populationChangeManager = new PopulationChangeManager();
        Node populationIncreaseBasePercentDataNode = XMLConverterUtility.findNode(node, "populationIncreaseBasePercentData");
        for (Node subNode = populationIncreaseBasePercentDataNode.getFirstChild(); subNode != null; subNode = subNode.getNextSibling()) {
            if (subNode.getNodeType() == Node.ELEMENT_NODE) {
                Node populationIntervalNode = XMLConverterUtility.findNode(subNode, "PopulationInterval");
                PopulationInterval populationInterval = new PopulationIntervalXMLConverter().initializeFromNode(realm, populationIntervalNode);
                Node changePercentNode = XMLConverterUtility.findNode(subNode, "changePercent");
                float baseChange = Float.parseFloat(changePercentNode.getFirstChild().getNodeValue());
                populationChangeManager.addPopulationIncreaseBasePercentData(populationInterval, baseChange);
            }
        }
        Node populationDecreaseBasePercentDataNode = XMLConverterUtility.findNode(node, "populationDecreaseBasePercentData");
        for (Node subNode = populationDecreaseBasePercentDataNode.getFirstChild(); subNode != null; subNode = subNode.getNextSibling()) {
            if (subNode.getNodeType() == Node.ELEMENT_NODE) {
                Node populationIntervalNode = XMLConverterUtility.findNode(subNode, "PopulationInterval");
                PopulationInterval populationInterval = new PopulationIntervalXMLConverter().initializeFromNode(realm, populationIntervalNode);
                Node changePercentNode = XMLConverterUtility.findNode(subNode, "changePercent");
                float baseChange = Float.parseFloat(changePercentNode.getFirstChild().getNodeValue());
                populationChangeManager.addPopulationDecreaseBasePercentData(populationInterval, baseChange);
            }
        }
        return populationChangeManager;
    }
}

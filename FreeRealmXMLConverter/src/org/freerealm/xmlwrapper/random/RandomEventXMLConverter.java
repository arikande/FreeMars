package org.freerealm.xmlwrapper.random;

import org.freerealm.Realm;
import org.freerealm.random.RandomEvent;
import org.freerealm.xmlwrapper.XMLConverter;
import org.freerealm.xmlwrapper.XMLConverterUtility;
import org.w3c.dom.Node;

/**
 *
 * @author Deniz ARIKAN
 */
public class RandomEventXMLConverter implements XMLConverter<RandomEvent> {

    public String toXML(RandomEvent randomEvent) {
        StringBuilder xml = new StringBuilder();
        xml.append("<randomEvent>\n");
        xml.append("<id>" + randomEvent.getId() + "</id>\n");
        xml.append("<name>" + randomEvent.getName() + "</name>\n");
        xml.append("<probability>" + randomEvent.getProbability() + "</probability>\n");
        xml.append("<command>" + randomEvent.getCommand() + "</command>\n");
        xml.append(XMLConverterUtility.convertPropertiesToXML(randomEvent.getProperties()) + "\n");
        xml.append("</randomEvent>\n");
        return xml.toString();
    }

    public RandomEvent initializeFromNode(Realm realm, Node node) {
        RandomEvent randomEvent = new RandomEvent();
        Node idNode = XMLConverterUtility.findNode(node, "id");
        if (idNode != null) {
            int id = Integer.parseInt(idNode.getFirstChild().getNodeValue());
            randomEvent.setId(id);
        }
        Node nameNode = XMLConverterUtility.findNode(node, "name");
        if (nameNode != null) {
            String name = nameNode.getFirstChild().getNodeValue();
            randomEvent.setName(name);
        }
        Node probabilityNode = XMLConverterUtility.findNode(node, "probability");
        if (probabilityNode != null) {
            double probability = Double.parseDouble(probabilityNode.getFirstChild().getNodeValue());
            randomEvent.setProbability(probability);
        }
        Node commandNode = XMLConverterUtility.findNode(node, "command");
        if (commandNode != null) {
            String command = commandNode.getFirstChild().getNodeValue();
            randomEvent.setCommand(command);
        }
        Node propertiesNode = XMLConverterUtility.findNode(node, "properties");
        if (propertiesNode != null) {
            randomEvent.setProperties(XMLConverterUtility.convertNodeToProperties(propertiesNode));
        }
        return randomEvent;
    }
}

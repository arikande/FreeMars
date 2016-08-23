package org.freemars.earth;

import java.util.Iterator;
import org.freerealm.Realm;
import org.freerealm.resource.Resource;
import org.freerealm.xmlwrapper.XMLConverter;
import org.freerealm.xmlwrapper.XMLConverterUtility;
import org.w3c.dom.Node;

/**
 *
 * @author Deniz ARIKAN
 */
public class EarthFlightPropertyXMLConverter implements XMLConverter<EarthFlightProperty> {

    public String toXML(EarthFlightProperty earthFlightProperty) {
        StringBuilder xml = new StringBuilder();
        xml.append("<");
        xml.append(EarthFlightProperty.NAME);
        xml.append(">");
        xml.append(System.getProperty("line.separator"));
        xml.append("<earth_mars_travel_time>");
        xml.append(earthFlightProperty.getEarthMarsTravelTime());
        xml.append("</earth_mars_travel_time>");
        xml.append(System.getProperty("line.separator"));
        Iterator<Resource> iterator = earthFlightProperty.getConsumedResourcesIterator();
        xml.append("<resource_consumption>");
        xml.append(System.getProperty("line.separator"));
        while (iterator.hasNext()) {
            Resource resource = iterator.next();
            xml.append("<consumption>");
            xml.append(System.getProperty("line.separator"));
            xml.append("<resource_id>");
            xml.append(resource.getId());
            xml.append("</resource_id>");
            xml.append(System.getProperty("line.separator"));
            xml.append("<quantity>");
            xml.append(earthFlightProperty.getResourceConsumption(resource));
            xml.append("</quantity>");
            xml.append(System.getProperty("line.separator"));
            xml.append("</consumption>");
            xml.append(System.getProperty("line.separator"));
        }
        xml.append("</resource_consumption>");
        xml.append(System.getProperty("line.separator"));
        xml.append("</");
        xml.append(EarthFlightProperty.NAME);
        xml.append(">");
        return xml.toString();
    }

    public EarthFlightProperty initializeFromNode(Realm realm, Node node) {
        EarthFlightProperty earthFlightProperty = new EarthFlightProperty();

        Node earthMarsTravelTimeNode = XMLConverterUtility.findNode(node, "earth_mars_travel_time");
        int earthMarsTravelTime = Integer.parseInt(earthMarsTravelTimeNode.getFirstChild().getNodeValue());
        earthFlightProperty.setEarthMarsTravelTime(earthMarsTravelTime);

        Node resourceConsumptionNode = XMLConverterUtility.findNode(node, "resource_consumption");

        for (Node subNode = resourceConsumptionNode.getFirstChild(); subNode != null; subNode = subNode.getNextSibling()) {
            if (subNode.getNodeType() == Node.ELEMENT_NODE) {
                Node resourceIdNode = XMLConverterUtility.findNode(subNode, "resource_id");
                int resourceId = Integer.parseInt(resourceIdNode.getFirstChild().getNodeValue());
                Resource resource = realm.getResourceManager().getResource(resourceId);
                Node quantityNode = XMLConverterUtility.findNode(subNode, "quantity");
                int quantity = Integer.parseInt(quantityNode.getFirstChild().getNodeValue());
                earthFlightProperty.setResourceConsumption(resource, quantity);
            }
        }

        return earthFlightProperty;
    }
}

package org.freerealm.xmlwrapper.property;

import java.util.Iterator;
import org.freerealm.Realm;
import org.freerealm.property.ContainerProperty;
import org.freerealm.resource.Resource;
import org.freerealm.xmlwrapper.XMLConverter;
import org.w3c.dom.Node;

/**
 *
 * @author Deniz ARIKAN
 */
public class ContainerPropertyXMLConverter implements XMLConverter<ContainerProperty> {

    public String toXML(ContainerProperty containerProperty) {
        StringBuilder xml = new StringBuilder();
        xml.append("<ContainerProperty ");
        xml.append("capacity=\"");
        xml.append(containerProperty.getCapacity());
        xml.append("\" >\n");
        Iterator<Resource> resourceIterator = containerProperty.getResourcesIterator();
        while (resourceIterator.hasNext()) {
            Resource resource = resourceIterator.next();
            xml.append("<resourceId>" + resource.getId() + "</resourceId>\n");
        }
        Iterator<Integer> unitTypeIterator = containerProperty.getUnitTypesIterator();
        while (unitTypeIterator.hasNext()) {
            Integer unitTypeId = unitTypeIterator.next();
            xml.append("<unitTypeId>" + unitTypeId.intValue() + "</unitTypeId>\n");
        }
        if (containerProperty.isAccomodatingPopulation()) {
            xml.append("<population />\n");
        }
        xml.append("</ContainerProperty>");
        return xml.toString();
    }

    public ContainerProperty initializeFromNode(Realm realm, Node node) {
        ContainerProperty containerProperty = new ContainerProperty();
        String capacityValue = node.getAttributes().getNamedItem("capacity").getNodeValue();
        containerProperty.setCapacity(Integer.parseInt(capacityValue));
        for (Node subNode = node.getFirstChild(); subNode != null; subNode = subNode.getNextSibling()) {
            if (subNode.getNodeType() == Node.ELEMENT_NODE) {
                if (subNode.getNodeName().equals("resourceId")) {
                    String resourceIdValue = subNode.getFirstChild().getNodeValue().trim();
                    int resourceId = Integer.parseInt(resourceIdValue);
                    Resource resource = realm.getResourceManager().getResource(resourceId);
                    containerProperty.addResource(resource);
                } else if (subNode.getNodeName().equals("unitTypeId")) {
                    String unitTypeIdValue = subNode.getFirstChild().getNodeValue();
                    int unitTypeId = Integer.parseInt(unitTypeIdValue);
                    containerProperty.addUnitType(unitTypeId);
                } else if (subNode.getNodeName().equals("population")) {
                    containerProperty.setAccomodatingPopulation(true);
                }
            }
        }
        return containerProperty;
    }
}

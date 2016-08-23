package org.freerealm.xmlwrapper.unit;

import org.freerealm.Realm;
import org.freerealm.ResourceStorageManager;
import org.freerealm.UnitContainerManager;
import org.freerealm.unit.ContainerManager;
import org.freerealm.unit.Unit;
import org.freerealm.xmlwrapper.XMLConverter;
import org.freerealm.xmlwrapper.XMLConverterUtility;
import org.freerealm.xmlwrapper.map.ResourceStorageManagerXMLConverter;
import org.w3c.dom.Node;

/**
 *
 * @author Deniz ARIKAN
 */
public class ContainerManagerXMLConverter implements XMLConverter<ContainerManager> {

    private final Unit containerUnit;

    public ContainerManagerXMLConverter(Unit containerUnit) {
        this.containerUnit = containerUnit;
    }

    public String toXML(ContainerManager containerManager) {
        StringBuilder xml = new StringBuilder();
        xml.append("<ContainerManager>\n");
        xml.append(new ResourceStorageManagerXMLConverter().toXML(containerManager.getResourceStorageManager()) + "\n");
        xml.append(new UnitContainerManagerXMLConverter(containerUnit).toXML(containerManager.getUnitContainerManager()) + "\n");
        if (containerManager.getContainedPopulation() > 0) {
            xml.append("<containedPopulation>" + containerManager.getContainedPopulation() + "</containedPopulation>\n");
        }
        xml.append("</ContainerManager>\n");
        return xml.toString();
    }

    public ContainerManager initializeFromNode(Realm realm, Node node) {
        ContainerManager containerManager = new ContainerManager(realm, containerUnit);
        Node resourceStorageNode = XMLConverterUtility.findNode(node, "ResourceStorage");
        if (resourceStorageNode != null) {
            ResourceStorageManager resourceStorageManager = new ResourceStorageManagerXMLConverter().initializeFromNode(realm, resourceStorageNode);
            containerManager.setResourceStorageManager(resourceStorageManager);
        }
        Node unitContainerManagerNode = XMLConverterUtility.findNode(node, "UnitContainerManager");
        if (unitContainerManagerNode != null) {
            UnitContainerManager unitContainerManager = new UnitContainerManagerXMLConverter(containerUnit).initializeFromNode(realm, unitContainerManagerNode);
            containerManager.setUnitContainerManager(unitContainerManager);
        }
        Node populationNode = XMLConverterUtility.findNode(node, "containedPopulation");
        if (populationNode != null) {
            int population = Integer.parseInt(populationNode.getFirstChild().getNodeValue());
            containerManager.setContainedPopulation(population);
        }
        return containerManager;
    }
}

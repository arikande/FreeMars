package org.freerealm.xmlwrapper.property;

import java.util.Iterator;
import org.freerealm.Realm;
import org.freerealm.property.BuildableProperty;
import org.freerealm.xmlwrapper.XMLConverter;
import org.freerealm.xmlwrapper.XMLConverterUtility;
import org.w3c.dom.Node;

/**
 *
 * @author Deniz ARIKAN
 */
public class BuildablePropertyXMLConverter implements XMLConverter<BuildableProperty> {

    public String toXML(BuildableProperty buildableProperty) {
        StringBuilder xml = new StringBuilder();
        xml.append("<buildable_property>\n");
        xml.append("<build_cost>" + buildableProperty.getBuildCost() + "</build_cost>\n");
        xml.append("<upkeep_cost>" + buildableProperty.getUpkeepCost() + "</upkeep_cost>\n");
        xml.append("<population_cost>" + buildableProperty.getPopulationCost() + "</population_cost>");
        if (buildableProperty.getBuildCostResourceCount() > 0) {
            xml.append("<build_cost_resources>\n");
            Iterator<Integer> buildCostResourceIdsIterator = buildableProperty.getBuildCostResourceIdsIterator();
            while (buildCostResourceIdsIterator.hasNext()) {
                Integer resourceId = buildCostResourceIdsIterator.next();
                int resourceQuantity = buildableProperty.getBuildCostResourceQuantity(resourceId);
                xml.append("<build_cost_resource>\n");
                xml.append("<resource_id>" + resourceId + "</resource_id>\n");
                xml.append("<resource_quantity>" + resourceQuantity + "</resource_quantity>");
                xml.append("</build_cost_resource>");
            }
            xml.append("</build_cost_resources>\n");
        }
        xml.append("</buildable_property>");
        return xml.toString();
    }

    public BuildableProperty initializeFromNode(Realm realm, Node node) {
        BuildableProperty buildableProperty = new BuildableProperty();
        Node buildCostNode = XMLConverterUtility.findNode(node, "build_cost");
        int buildCost = Integer.parseInt(buildCostNode.getFirstChild().getNodeValue());
        buildableProperty.setBuildCost(buildCost);
        Node upkeepCostNode = XMLConverterUtility.findNode(node, "upkeep_cost");
        int upkeepCost = Integer.parseInt(upkeepCostNode.getFirstChild().getNodeValue());
        buildableProperty.setUpkeepCost(upkeepCost);
        Node populationCostNode = XMLConverterUtility.findNode(node, "population_cost");
        if (populationCostNode != null) {
            buildableProperty.setPopulationCost(Integer.parseInt(populationCostNode.getFirstChild().getNodeValue()));
        }
        Node buildCostResourcesNode = XMLConverterUtility.findNode(node, "build_cost_resources");
        if (buildCostResourcesNode != null) {
            for (Node buildCostResourcesSubNode = buildCostResourcesNode.getFirstChild(); buildCostResourcesSubNode != null; buildCostResourcesSubNode = buildCostResourcesSubNode.getNextSibling()) {
                if (buildCostResourcesSubNode.getNodeType() == Node.ELEMENT_NODE) {
                    if (buildCostResourcesSubNode.getNodeName().equals("build_cost_resource")) {
                        Node resourceIdNode = XMLConverterUtility.findNode(buildCostResourcesSubNode, "resource_id");
                        int resourceId = Integer.parseInt(resourceIdNode.getFirstChild().getNodeValue());
                        Node resourceQuantityNode = XMLConverterUtility.findNode(buildCostResourcesSubNode, "resource_quantity");
                        int resourceQuantity = Integer.parseInt(resourceQuantityNode.getFirstChild().getNodeValue());
                        buildableProperty.addBuildCostResourceQuantity(resourceId, resourceQuantity);
                    }
                }
            }
        }
        return buildableProperty;
    }
}

package org.freemars.earth;

import java.util.Iterator;
import java.util.Map;
import org.freerealm.Realm;
import org.freerealm.resource.Resource;
import org.freerealm.unit.Unit;
import org.freerealm.xmlwrapper.XMLConverter;
import org.freerealm.xmlwrapper.XMLConverterUtility;
import org.w3c.dom.Node;

/**
 *
 * @author Deniz ARIKAN
 */
public class EarthXMLConverter implements XMLConverter<Earth> {

    public String toXML(Earth earth) {
        StringBuilder xml = new StringBuilder();
        xml.append("<" + Earth.TAG + ">");
        xml.append(System.getProperty("line.separator"));
        xml.append("<economy>");
        xml.append(System.getProperty("line.separator"));
        xml.append("<resources>");
        xml.append(System.getProperty("line.separator"));

        Iterator<Resource> resourcesIterator = earth.getRealm().getResourceManager().getResourcesIterator();
        while (resourcesIterator.hasNext()) {
            Resource resource = resourcesIterator.next();
            xml.append("<resource>");
            xml.append(System.getProperty("line.separator"));
            xml.append("<id>");
            xml.append(resource.getId());
            xml.append("</id>");
            xml.append(System.getProperty("line.separator"));

            xml.append("<minimum_price>");
            xml.append(earth.getMinimumPrice(resource));
            xml.append("</minimum_price>");
            xml.append(System.getProperty("line.separator"));

            xml.append("<maximum_price>");
            xml.append(earth.getMaximumPrice(resource));
            xml.append("</maximum_price>");
            xml.append(System.getProperty("line.separator"));

            xml.append("<consumption_per_player>");
            xml.append(earth.getConsumptionPerPlayer(resource));
            xml.append("</consumption_per_player>");
            xml.append(System.getProperty("line.separator"));

            xml.append("<maximum_demand_per_player>");
            xml.append(earth.getMaximumDemandPerPlayer(resource));
            xml.append("</maximum_demand_per_player>");
            xml.append(System.getProperty("line.separator"));

            xml.append("<quantity>");
            xml.append(earth.getResourceQuantity(resource));
            xml.append("</quantity>");
            xml.append(System.getProperty("line.separator"));

            xml.append("</resource>");
            xml.append(System.getProperty("line.separator"));
        }

        xml.append("</resources>");
        xml.append(System.getProperty("line.separator"));
        xml.append("</economy>");
        xml.append(System.getProperty("line.separator"));

        xml.append("<unit_locations>\n");
        Iterator<Map.Entry<Unit, Location>> iterator = earth.getUnitLocationsIterator();
        while (iterator.hasNext()) {
            Map.Entry<Unit, Location> entry = iterator.next();
            if (entry.getKey() != null) {
                xml.append("<unit_location>");
                xml.append(System.getProperty("line.separator"));
                xml.append("<player_id>");
                xml.append(entry.getKey().getPlayer().getId());
                xml.append("</player_id>");
                xml.append(System.getProperty("line.separator"));
                xml.append("<unit_id>");
                xml.append(entry.getKey().getId());
                xml.append("</unit_id>");
                xml.append(System.getProperty("line.separator"));
                xml.append("<location>");
                xml.append(entry.getValue().getId());
                xml.append("</location>");
                xml.append(System.getProperty("line.separator"));
                xml.append("</unit_location>");
                xml.append(System.getProperty("line.separator"));
            }
        }
        xml.append("</unit_locations>\n");

        xml.append("</" + Earth.TAG + ">");
        xml.append(System.getProperty("line.separator"));
        return xml.toString();
    }

    public Earth initializeFromNode(Realm realm, Node node) {
        Earth earth = new Earth(realm);

        Node economyNode = XMLConverterUtility.findNode(node, "economy");
        Node resourcesNode = XMLConverterUtility.findNode(economyNode, "resources");
        for (Node resourceNode = resourcesNode.getFirstChild(); resourceNode != null; resourceNode = resourceNode.getNextSibling()) {
            if (resourceNode.getNodeType() == Node.ELEMENT_NODE) {
                Node idNode = XMLConverterUtility.findNode(resourceNode, "id");
                int id = Integer.parseInt(idNode.getFirstChild().getNodeValue());
                Resource resource = realm.getResourceManager().getResource(id);

                Node minimumPriceNode = XMLConverterUtility.findNode(resourceNode, "minimum_price");
                int minimumPrice = Integer.parseInt(minimumPriceNode.getFirstChild().getNodeValue());
                earth.setMinimumPrice(resource, minimumPrice);

                Node maximumPriceNode = XMLConverterUtility.findNode(resourceNode, "maximum_price");
                int maximumPrice = Integer.parseInt(maximumPriceNode.getFirstChild().getNodeValue());
                earth.setMaximumPrice(resource, maximumPrice);

                Node consumptionNode = XMLConverterUtility.findNode(resourceNode, "consumption_per_player");
                int consumption = Integer.parseInt(consumptionNode.getFirstChild().getNodeValue());
                earth.setConsumptionPerPlayer(resource, consumption);

                Node maximumDemandPerPlayerNode = XMLConverterUtility.findNode(resourceNode, "maximum_demand_per_player");
                int maximumDemand = Integer.parseInt(maximumDemandPerPlayerNode.getFirstChild().getNodeValue());
                earth.setMaximumDemand(resource, maximumDemand);

                Node quantityNode = XMLConverterUtility.findNode(resourceNode, "quantity");
                if (quantityNode != null) {
                    int quantity = Integer.parseInt(quantityNode.getFirstChild().getNodeValue());
                    earth.setResourceQuantity(resource, quantity);
                }
            }
        }
        Node unitLocationsNode = XMLConverterUtility.findNode(node, "unit_locations");
        if (unitLocationsNode != null) {
            for (Node unitLocationNode = unitLocationsNode.getFirstChild(); unitLocationNode != null; unitLocationNode = unitLocationNode.getNextSibling()) {
                if (unitLocationNode.getNodeType() == Node.ELEMENT_NODE) {
                    if (unitLocationNode.getNodeName().equals("unit_location")) {
                        Node playerIdNode = XMLConverterUtility.findNode(unitLocationNode, "player_id");
                        Node unitIdNode = XMLConverterUtility.findNode(unitLocationNode, "unit_id");
                        Node locationNode = XMLConverterUtility.findNode(unitLocationNode, "location");
                        int playerIdValue = Integer.parseInt(playerIdNode.getFirstChild().getNodeValue());
                        int unitIdValue = Integer.parseInt(unitIdNode.getFirstChild().getNodeValue());
                        int locationValue = Integer.parseInt(locationNode.getFirstChild().getNodeValue());
                        Unit unit = realm.getPlayerManager().getPlayer(playerIdValue).getUnit(unitIdValue);
                        Location location = Location.getLocation(locationValue);
                        earth.addUnitLocation(unit, location);
                    }
                }
            }
        }
        return earth;
    }
}

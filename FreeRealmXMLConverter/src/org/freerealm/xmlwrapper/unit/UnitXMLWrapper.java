package org.freerealm.xmlwrapper.unit;

import java.util.Properties;
import org.freerealm.Realm;
import org.freerealm.unit.FreeRealmUnitType;
import org.freerealm.unit.Unit;
import org.freerealm.unit.UnitAutomater;
import org.freerealm.xmlwrapper.TagManager;
import org.freerealm.xmlwrapper.XMLConverter;
import org.freerealm.xmlwrapper.XMLConverterUtility;
import org.freerealm.xmlwrapper.XMLWrapper;
import org.freerealm.xmlwrapper.map.CoordinateXMLConverter;
import org.w3c.dom.Node;

/**
 *
 * @author Deniz ARIKAN
 */
public class UnitXMLWrapper implements XMLWrapper {

    private final Unit unit;

    public UnitXMLWrapper(Unit unit) {
        this.unit = unit;
    }

    public String toXML() {
        StringBuilder xml = new StringBuilder();
        xml.append("<Unit>\n");
        xml.append("<id>" + unit.getId() + "</id>\n");
        xml.append("<status>" + unit.getStatus() + "</status>\n");
        if (unit.getCoordinate() != null) {
            xml.append(new CoordinateXMLConverter().toXML(unit.getCoordinate()) + "\n");
        }
        xml.append("<type>" + unit.getType() + "</type>\n");
        xml.append("<name>" + unit.getName() + "</name>\n");
        if (unit.getMovementPoints() > 0) {
            xml.append("<movementPoints>" + unit.getMovementPoints() + "</movementPoints>\n");
        }
        if (unit.isSkippedForCurrentTurn()) {
            xml.append("<skippedForCurrentTurn>" + unit.isSkippedForCurrentTurn() + "</skippedForCurrentTurn>\n");
        }
        xml.append(new ContainerManagerXMLConverter(unit).toXML(unit.getContainerManager()) + "\n");
        if (unit.getCustomProperties().size() > 0) {
            xml.append(XMLConverterUtility.convertPropertiesToXML(unit.getCustomProperties(), "customProperties") + "\n");
        }
        if (unit.getAutomater() != null) {
            xml.append("<automater>\n");
            String xMLConverterName = TagManager.getXMLConverterName(unit.getAutomater().getName());
            try {
                XMLConverter<UnitAutomater> xMLConverter = (XMLConverter<UnitAutomater>) Class.forName(xMLConverterName).newInstance();
                xml.append(xMLConverter.toXML(unit.getAutomater()));
            } catch (Exception ex) {
            }
            xml.append("</automater>\n");
        }
        xml.append("</Unit>\n");
        return xml.toString();
    }

    public void initializeFromNode(Realm realm, Node node) {
        for (Node subNode = node.getFirstChild(); subNode != null; subNode = subNode.getNextSibling()) {
            if (subNode.getNodeType() == Node.ELEMENT_NODE) {
                if (subNode.getNodeName().equals("id")) {
                    int idValue = Integer.parseInt(subNode.getFirstChild().getNodeValue());
                    unit.setId(idValue);
                } else if (subNode.getNodeName().equals("status")) {
                    int statusValue = Integer.parseInt(subNode.getFirstChild().getNodeValue());
                    unit.setStatus(statusValue);
                } else if (subNode.getNodeName().equals("movementPoints")) {
                    float movementPointsValue = Float.parseFloat(subNode.getFirstChild().getNodeValue());
                    unit.setMovementPoints(movementPointsValue);
                } else if (subNode.getNodeName().equals("skippedForCurrentTurn")) {
                    boolean skippedForCurrentTurnValue = Boolean.parseBoolean(subNode.getFirstChild().getNodeValue());
                    unit.setSkippedForCurrentTurn(skippedForCurrentTurnValue);
                } else if (subNode.getNodeName().equals("type")) {
                    String unitTypeName = subNode.getFirstChild().getNodeValue();
                    FreeRealmUnitType unitType = realm.getUnitTypeManager().getUnitType(unitTypeName);
                    unit.setType(unitType);
                    unit.setMovementPoints(0);
                } else if (subNode.getNodeName().equals("name")) {
                    String name = subNode.getFirstChild().getNodeValue();
                    unit.setName(name);
                } else if (subNode.getNodeName().equals("coordinate")) {
                    unit.setCoordinate(new CoordinateXMLConverter().initializeFromNode(realm, subNode));
                } else if (subNode.getNodeName().equals("ContainerManager")) {
                    unit.setContainerManager(new ContainerManagerXMLConverter(unit).initializeFromNode(realm, subNode));
                } else if (subNode.getNodeName().equals("automater")) {
                    for (Node automaterNode = subNode.getFirstChild(); automaterNode != null; automaterNode = automaterNode.getNextSibling()) {
                        if (automaterNode.getNodeType() == Node.ELEMENT_NODE) {
                            String xMLConverterName = TagManager.getXMLConverterName(automaterNode.getNodeName());
                            try {
                                XMLConverter<UnitAutomater> xMLConverter = (XMLConverter<UnitAutomater>) Class.forName(xMLConverterName).newInstance();
                                UnitAutomater unitAutomater = xMLConverter.initializeFromNode(realm, automaterNode);
                                unit.setAutomater(unitAutomater);
                                unitAutomater.setUnit(unit);
                            } catch (Exception ex) {
                            }
                        }
                    }
                }
            }
        }
        Node customPropertiesNode = XMLConverterUtility.findNode(node, "customProperties");
        if (customPropertiesNode != null) {
            Properties properties = XMLConverterUtility.convertNodeToProperties(customPropertiesNode);
            unit.setCustomProperties(properties);
        }
    }
}

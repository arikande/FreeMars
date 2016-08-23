package org.freerealm.xmlwrapper.unit;

import java.util.Iterator;
import org.freerealm.Realm;
import org.freerealm.property.Property;
import org.freerealm.unit.FreeRealmUnitType;
import org.freerealm.xmlwrapper.PropertyFactory;
import org.freerealm.xmlwrapper.TagManager;
import org.freerealm.xmlwrapper.XMLConverter;
import org.freerealm.xmlwrapper.XMLConverterUtility;
import org.freerealm.xmlwrapper.city.PrerequisitesXMLHelper;
import org.w3c.dom.Node;

/**
 *
 * @author Deniz ARIKAN
 */
public class UnitTypeXMLConverter implements XMLConverter<FreeRealmUnitType> {

    public String toXML(FreeRealmUnitType unitType) {
        StringBuilder xml = new StringBuilder();
        xml.append("<UnitType ");
        xml.append("id=\"");
        xml.append(unitType.getId());
        xml.append("\" name=\"");
        xml.append(unitType.getName());
        xml.append("\" explorationRadius=\"");
        xml.append(unitType.getExplorationRadius());
        xml.append("\" weightForContainer=\"");
        xml.append(unitType.getWeightForContainer());
        xml.append("\">\n");
        xml.append("<Properties>\n");
        Iterator<Property> propertyIterator = unitType.getPropertiesIterator();
        while (propertyIterator.hasNext()) {
            Property property = propertyIterator.next();
            String xMLWrapperName = TagManager.getXMLConverterName(property.getName());
            Class c;
            try {
                c = Class.forName(xMLWrapperName);
                XMLConverter<Property> xMLConverter = (XMLConverter<Property>) c.newInstance();
                xml.append(xMLConverter.toXML(property) + "\n");
            } catch (Exception ex) {
            }
        }
        xml.append("</Properties>\n");

        xml.append(new PrerequisitesXMLHelper().toXML(unitType.getPrerequisitesIterator()));

        xml.append("</UnitType>");
        return xml.toString();
    }

    public FreeRealmUnitType initializeFromNode(Realm realm, Node node) {
        FreeRealmUnitType unitType = new FreeRealmUnitType();
        int idValue = Integer.parseInt(node.getAttributes().getNamedItem("id").getNodeValue());
        String nameValue = node.getAttributes().getNamedItem("name").getNodeValue();
        int explorationRadius = Integer.parseInt(node.getAttributes().getNamedItem("explorationRadius").getNodeValue());
        int weightForContainerValue = Integer.parseInt(node.getAttributes().getNamedItem("weightForContainer").getNodeValue());
        unitType.setId(idValue);
        unitType.setName(nameValue);
        unitType.setExplorationRadius(explorationRadius);
        unitType.setWeightForContainer(weightForContainerValue);
        for (Node subNode = node.getFirstChild(); subNode != null; subNode = subNode.getNextSibling()) {
            if (subNode.getNodeType() == Node.ELEMENT_NODE) {
                if (subNode.getNodeName().equals("Properties")) {
                    for (Node propertyNode = subNode.getFirstChild(); propertyNode != null; propertyNode = propertyNode.getNextSibling()) {
                        if (propertyNode.getNodeType() == Node.ELEMENT_NODE) {
                            Property property = PropertyFactory.createProperty(realm, propertyNode);
                            unitType.addProperty(property);
                        }
                    }
                }
            }
        }
        new PrerequisitesXMLHelper().initializePrerequisites(realm.getSettlementImprovementManager(), unitType, XMLConverterUtility.findNode(node, "Prerequisites"));
        return unitType;
    }
}

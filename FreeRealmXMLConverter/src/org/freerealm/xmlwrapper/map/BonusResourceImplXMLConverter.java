package org.freerealm.xmlwrapper.map;

import java.util.Iterator;
import org.freerealm.Realm;
import org.freerealm.property.Property;
import org.freerealm.resource.bonus.FreeRealmBonusResource;
import org.freerealm.tile.TileType;
import org.freerealm.xmlwrapper.PropertyFactory;
import org.freerealm.xmlwrapper.TagManager;
import org.freerealm.xmlwrapper.XMLConverter;
import org.freerealm.xmlwrapper.XMLConverterUtility;
import org.w3c.dom.Node;

/**
 *
 * @author Deniz ARIKAN
 */
public class BonusResourceImplXMLConverter implements XMLConverter<FreeRealmBonusResource> {

    public String toXML(FreeRealmBonusResource bonusResource) {
        StringBuilder xml = new StringBuilder();
        xml.append("<bonusResource>\n");
        xml.append("<id>" + bonusResource.getId() + "</id>\n");
        xml.append("<name>" + bonusResource.getName() + "</name>\n");
        xml.append("<tileTypes>\n");
        Iterator<TileType> iterator = bonusResource.getTileTypesIterator();
        while (iterator.hasNext()) {
            TileType tileType = iterator.next();
            xml.append("<tileTypeId>" + tileType.getId() + "</tileTypeId>\n");
        }
        xml.append("</tileTypes>\n");
        xml.append("<properties>\n");
        Iterator<Property> propertyIterator = bonusResource.getPropertiesIterator();
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
        xml.append("</properties>\n");
        xml.append("</bonusResource>\n");
        return xml.toString();
    }

    public FreeRealmBonusResource initializeFromNode(Realm realm, Node node) {
        FreeRealmBonusResource bonusResourceImpl = new FreeRealmBonusResource();
        bonusResourceImpl.setId(Integer.parseInt(XMLConverterUtility.findNode(node, "id").getFirstChild().getNodeValue()));
        bonusResourceImpl.setName(XMLConverterUtility.findNode(node, "name").getFirstChild().getNodeValue());
        Node tileTypesNode = XMLConverterUtility.findNode(node, "tileTypes");

        for (Node subNode = tileTypesNode.getFirstChild(); subNode != null; subNode = subNode.getNextSibling()) {
            if (subNode.getNodeType() == Node.ELEMENT_NODE) {
                if (subNode.getNodeName().equals("tileTypeId")) {
                    int tileTypeId = Integer.parseInt(subNode.getFirstChild().getNodeValue());
                    TileType tileType = realm.getTileTypeManager().getTileType(tileTypeId);
                    bonusResourceImpl.addTileType(tileType);
                }
            }
        }
        Node propertiesNode = XMLConverterUtility.findNode(node, "properties");
        for (Node propertyNode = propertiesNode.getFirstChild(); propertyNode != null; propertyNode = propertyNode.getNextSibling()) {
            if (propertyNode.getNodeType() == Node.ELEMENT_NODE) {
                Property property = PropertyFactory.createProperty(realm, propertyNode);
                bonusResourceImpl.addProperty(property);
            }
        }
        return bonusResourceImpl;
    }
}

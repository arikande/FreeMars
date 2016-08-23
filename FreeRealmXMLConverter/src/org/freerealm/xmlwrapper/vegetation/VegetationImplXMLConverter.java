package org.freerealm.xmlwrapper.vegetation;

import java.util.Iterator;
import org.freerealm.Realm;
import org.freerealm.property.Property;
import org.freerealm.tile.TileType;
import org.freerealm.vegetation.FreeRealmVegetationType;
import org.freerealm.vegetation.VegetationType;
import org.freerealm.xmlwrapper.PropertyFactory;
import org.freerealm.xmlwrapper.TagManager;
import org.freerealm.xmlwrapper.XMLConverter;
import org.freerealm.xmlwrapper.XMLConverterUtility;
import org.w3c.dom.Node;

/**
 *
 * @author Deniz ARIKAN
 */
public class VegetationImplXMLConverter implements XMLConverter<VegetationType> {

    public String toXML(VegetationType vegetation) {
        StringBuilder xml = new StringBuilder();
        xml.append("<Vegetation>\n");
        xml.append("<id>" + vegetation.getId() + "</id>\n");
        xml.append("<name>" + vegetation.getName() + "</name>\n");
        xml.append("<turnsNeededToClear>" + vegetation.getTurnsNeededToClear() + "</turnsNeededToClear>\n");
        xml.append("<tileTypes>\n");
        Iterator<TileType> iterator = vegetation.getGrowableTileTypesIterator();
        while (iterator.hasNext()) {
            TileType tileType = iterator.next();
            xml.append("<tileTypeId>" + tileType.getId() + "</tileTypeId>\n");
        }
        xml.append("</tileTypes>\n");
        xml.append("<properties>\n");
        Iterator<Property> propertyIterator = vegetation.getPropertiesIterator();
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
        xml.append("</Vegetation>\n");
        return xml.toString();
    }

    public VegetationType initializeFromNode(Realm realm, Node node) {
        FreeRealmVegetationType vegetationImpl = new FreeRealmVegetationType();
        Node idNode = XMLConverterUtility.findNode(node, "id");
        int id = Integer.parseInt(idNode.getFirstChild().getNodeValue());
        vegetationImpl.setId(id);
        Node nameNode = XMLConverterUtility.findNode(node, "name");
        String name = nameNode.getFirstChild().getNodeValue();
        vegetationImpl.setName(name);
        Node turnsNeededToClearNode = XMLConverterUtility.findNode(node, "turnsNeededToClear");
        int turnsNeededToClear = Integer.parseInt(turnsNeededToClearNode.getFirstChild().getNodeValue());
        vegetationImpl.setTurnsNeededToClear(turnsNeededToClear);
        Node tileTypesNode = XMLConverterUtility.findNode(node, "tileTypes");
        for (Node subNode = tileTypesNode.getFirstChild(); subNode != null; subNode = subNode.getNextSibling()) {
            if (subNode.getNodeType() == Node.ELEMENT_NODE) {
                if (subNode.getNodeName().equals("tileTypeId")) {
                    int tileTypeId = Integer.parseInt(subNode.getFirstChild().getNodeValue());
                    TileType tileType = realm.getTileTypeManager().getTileType(tileTypeId);
                    vegetationImpl.addGrowableTileType(tileType);
                }
            }
        }
        Node propertiesNode = XMLConverterUtility.findNode(node, "properties");
        for (Node propertyNode = propertiesNode.getFirstChild(); propertyNode != null; propertyNode = propertyNode.getNextSibling()) {
            if (propertyNode.getNodeType() == Node.ELEMENT_NODE) {
                Property property = PropertyFactory.createProperty(realm, propertyNode);
                vegetationImpl.addProperty(property);
            }
        }
        return vegetationImpl;
    }
}

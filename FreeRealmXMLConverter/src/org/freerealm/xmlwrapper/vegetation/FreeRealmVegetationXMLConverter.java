package org.freerealm.xmlwrapper.vegetation;

import java.util.Properties;
import org.freerealm.Realm;
import org.freerealm.vegetation.FreeRealmVegetation;
import org.freerealm.vegetation.VegetationType;
import org.freerealm.xmlwrapper.XMLConverter;
import org.freerealm.xmlwrapper.XMLConverterUtility;
import org.w3c.dom.Node;

/**
 *
 * @author Deniz ARIKAN
 */
public class FreeRealmVegetationXMLConverter implements XMLConverter<FreeRealmVegetation> {

    public String toXML(FreeRealmVegetation freeRealmVegetation) {
        StringBuilder xml = new StringBuilder();
        xml.append("<vegetation>\n");
        xml.append("<typeId>" + freeRealmVegetation.getType().getId() + "</typeId>\n");
        xml.append(XMLConverterUtility.convertPropertiesToXML(freeRealmVegetation.getCustomProperties(), "customProperties") + "\n");
        xml.append("</vegetation>");
        return xml.toString();
    }

    public FreeRealmVegetation initializeFromNode(Realm realm, Node node) {
        FreeRealmVegetation freeRealmVegetation = new FreeRealmVegetation();
        Node typeIdNode = XMLConverterUtility.findNode(node, "typeId");
        if (typeIdNode != null) {
            int typeId = Integer.parseInt(typeIdNode.getFirstChild().getNodeValue());
            VegetationType vegetationType = realm.getVegetationManager().getVegetationType(typeId);
            freeRealmVegetation.setType(vegetationType);
        }
        Node customPropertiesNode = XMLConverterUtility.findNode(node, "customProperties");
        if (customPropertiesNode != null) {
            Properties properties = XMLConverterUtility.convertNodeToProperties(customPropertiesNode);
            freeRealmVegetation.setCustomProperties(properties);
        }
        return freeRealmVegetation;
    }
}

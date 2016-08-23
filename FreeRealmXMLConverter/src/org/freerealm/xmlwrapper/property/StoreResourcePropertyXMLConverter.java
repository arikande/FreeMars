package org.freerealm.xmlwrapper.property;

import org.freerealm.Realm;
import org.freerealm.property.StoreResourceProperty;
import org.freerealm.xmlwrapper.XMLConverter;
import org.w3c.dom.Node;

/**
 *
 * @author Deniz ARIKAN
 */
public class StoreResourcePropertyXMLConverter implements XMLConverter<StoreResourceProperty> {

    public String toXML(StoreResourceProperty storeResourceProperty) {
        return "<" + StoreResourceProperty.NAME + " resource=\"" + storeResourceProperty.getResource() + "\" storage=\"" + storeResourceProperty.getStorage() + "\" />";

    }

    public StoreResourceProperty initializeFromNode(Realm realm, Node node) {
        StoreResourceProperty storeResource = new StoreResourceProperty();
        String resourceString = node.getAttributes().getNamedItem("resource").getNodeValue();
        String storageString = node.getAttributes().getNamedItem("storage").getNodeValue();
        storeResource.setResource(realm.getResourceManager().getResource(resourceString));
        storeResource.setStorage(Integer.parseInt(storageString));
        return storeResource;
    }
}

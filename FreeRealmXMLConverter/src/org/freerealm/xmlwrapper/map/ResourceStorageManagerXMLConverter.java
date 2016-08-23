package org.freerealm.xmlwrapper.map;

import java.util.Iterator;
import java.util.Map.Entry;
import org.freerealm.Realm;
import org.freerealm.ResourceStorageManager;
import org.freerealm.resource.Resource;
import org.freerealm.xmlwrapper.XMLConverter;
import org.freerealm.xmlwrapper.XMLConverterUtility;
import org.w3c.dom.Node;

/**
 *
 * @author Deniz ARIKAN
 */
public class ResourceStorageManagerXMLConverter implements XMLConverter<ResourceStorageManager> {

    public String toXML(ResourceStorageManager resourceStorageManager) {
        StringBuilder xml = new StringBuilder();
        xml.append("<ResourceStorage>\n");
        Iterator<Entry<Resource, Integer>> resourceIterator = resourceStorageManager.getResources().entrySet().iterator();
        while (resourceIterator.hasNext()) {
            Entry<Resource, Integer> entry = (Entry<Resource, Integer>) resourceIterator.next();
            Resource resource = entry.getKey();
            int quantity = entry.getValue();
            if (quantity > 0) {
                xml.append("<storedResource>\n");
                xml.append("<resourceId>" + resource.getId() + "</resourceId>\n");
                xml.append("<resourceQuantity>" + quantity + "</resourceQuantity>\n");
                xml.append("</storedResource>\n");
            }
        }
        xml.append("</ResourceStorage>");
        return xml.toString();
    }

    public ResourceStorageManager initializeFromNode(Realm realm, Node node) {
        ResourceStorageManager resourceStorageManager = new ResourceStorageManager(realm);
        Iterator<Resource> resourcesIterator = realm.getResourceManager().getResourcesIterator();
        while (resourcesIterator.hasNext()) {
            Resource resource = resourcesIterator.next();
            resourceStorageManager.setResourceQuantity(resource, 0);
        }
        for (Node subNode = node.getFirstChild(); subNode != null; subNode = subNode.getNextSibling()) {
            if (subNode.getNodeType() == Node.ELEMENT_NODE) {
                Node resourceIdNode = XMLConverterUtility.findNode(subNode, "resourceId");
                Node resourceQuantityNode = XMLConverterUtility.findNode(subNode, "resourceQuantity");
                int resourceId = Integer.parseInt(resourceIdNode.getFirstChild().getNodeValue());
                int resourceQuantity = Integer.parseInt(resourceQuantityNode.getFirstChild().getNodeValue());
                Resource resource = realm.getResourceManager().getResource(resourceId);
                resourceStorageManager.setResourceQuantity(resource, resourceQuantity);
            }
        }
        return resourceStorageManager;
    }
}

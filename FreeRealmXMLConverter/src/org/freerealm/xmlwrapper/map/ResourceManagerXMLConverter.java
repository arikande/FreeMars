package org.freerealm.xmlwrapper.map;

import java.util.Iterator;
import org.freerealm.Realm;
import org.freerealm.resource.Resource;
import org.freerealm.resource.ResourceManager;
import org.freerealm.xmlwrapper.XMLConverter;
import org.w3c.dom.Node;

/**
 *
 * @author Deniz ARIKAN
 */
public class ResourceManagerXMLConverter implements XMLConverter<ResourceManager> {

    public String toXML(ResourceManager resourceManager) {
        StringBuilder xml = new StringBuilder();
        xml.append("<resources>\n");
        for (Iterator it = resourceManager.getResourcesIterator(); it.hasNext();) {
            Resource resource = (Resource) it.next();
            xml.append(new ResourceXMLConverter().toXML(resource));
            xml.append("\n");
        }
        xml.append("</resources>");
        return xml.toString();
    }

    public ResourceManager initializeFromNode(Realm realm, Node node) {
        ResourceManager resourceManager = new ResourceManager();
        for (Node resourceNode = node.getFirstChild(); resourceNode != null; resourceNode = resourceNode.getNextSibling()) {
            if (resourceNode.getNodeType() == Node.ELEMENT_NODE) {
                String name = resourceNode.getAttributes().getNamedItem("name").getNodeValue();
                Resource resource = new Resource(name);
                String idString = resourceNode.getAttributes().getNamedItem("id").getNodeValue();
                int id = Integer.parseInt(idString);
                resource.setId(id);
                resourceManager.addResource(resource);
            }
        }
        return resourceManager;
    }
}

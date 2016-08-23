package org.freerealm.xmlwrapper.map;

import org.freerealm.Realm;
import org.freerealm.resource.Resource;
import org.freerealm.xmlwrapper.XMLConverter;
import org.w3c.dom.Node;

/**
 *
 * @author Deniz ARIKAN
 */
public class ResourceXMLConverter implements XMLConverter<Resource> {

    public String toXML(Resource resource) {
        String xml = "<resource id=\"" + resource.getId() + "\" name=\"" + resource.getName() + "\" />";
        return xml;
    }

    public Resource initializeFromNode(Realm realm, Node node) {
        return null;
    }
}

package org.freerealm.xmlwrapper.property;

import java.util.Iterator;
import org.freerealm.Realm;
import org.freerealm.property.ProduceResource;
import org.freerealm.resource.Resource;
import org.freerealm.xmlwrapper.XMLConverter;
import org.freerealm.xmlwrapper.XMLConverterUtility;
import org.w3c.dom.Node;

/**
 *
 * @author Deniz ARIKAN
 */
public class ProduceResourceXMLConverter implements XMLConverter<ProduceResource> {

    public String toXML(ProduceResource produceResource) {
        StringBuilder xml = new StringBuilder();
        xml.append("<ProduceResource maximumMultiplier=\"" + produceResource.getMaximumMultiplier() + "\">\n");
        xml.append("<Input>\n");
        Iterator<Resource> inputResourcesIterator = produceResource.getInputResourcesIterator();
        while (inputResourcesIterator.hasNext()) {
            Resource resource = inputResourcesIterator.next();
            xml.append("<Resource id=\"" + resource.getId() + "\" quantity=\"" + produceResource.getInputQuantity(resource) + "\"/>\n");
        }
        xml.append("</Input>\n");
        xml.append("<Output>\n");
        Iterator<Resource> outputResourcesIterator = produceResource.getOutputResourcesIterator();
        while (outputResourcesIterator.hasNext()) {
            Resource resource = outputResourcesIterator.next();
            xml.append("<Resource ");
            xml.append("id=\"" + resource.getId() + "\" ");
            xml.append("quantity=\"" + produceResource.getOutputQuantity(resource) + "\"/>\n");
        }
        xml.append("</Output>\n");
        xml.append("</ProduceResource>");
        return xml.toString();
    }

    public ProduceResource initializeFromNode(Realm realm, Node node) {
        ProduceResource produceResource = new ProduceResource();
        int maximumMultiplier = Integer.parseInt(node.getAttributes().getNamedItem("maximumMultiplier").getFirstChild().getNodeValue());
        produceResource.setMaximumMultiplier(maximumMultiplier);
        Node inputNode = XMLConverterUtility.findNode(node, "Input");
        if (inputNode != null) {
            for (Node subNode = inputNode.getFirstChild(); subNode != null; subNode = subNode.getNextSibling()) {
                if (subNode.getNodeType() == Node.ELEMENT_NODE) {
                    if (subNode.getNodeName().equals("Resource")) {
                        int id = Integer.parseInt(subNode.getAttributes().getNamedItem("id").getFirstChild().getNodeValue());
                        int amount = Integer.parseInt(subNode.getAttributes().getNamedItem("quantity").getFirstChild().getNodeValue());
                        Resource resource = realm.getResourceManager().getResource(id);
                        produceResource.addInput(resource, amount);
                    }
                }
            }
        }
        Node outputNode = XMLConverterUtility.findNode(node, "Output");
        if (outputNode != null) {
            for (Node subNode = outputNode.getFirstChild(); subNode != null; subNode = subNode.getNextSibling()) {
                if (subNode.getNodeType() == Node.ELEMENT_NODE) {
                    if (subNode.getNodeName().equals("Resource")) {
                        int id = Integer.parseInt(subNode.getAttributes().getNamedItem("id").getFirstChild().getNodeValue());
                        int amount = Integer.parseInt(subNode.getAttributes().getNamedItem("quantity").getFirstChild().getNodeValue());
                        Resource resource = realm.getResourceManager().getResource(id);
                        produceResource.addOutput(resource, amount);
                    }
                }
            }
        }
        return produceResource;
    }
}

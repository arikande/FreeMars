package org.freemars.tile;

import org.freemars.controller.FreeMarsController;
import org.freerealm.Realm;
import org.freerealm.xmlwrapper.TagManager;
import org.freerealm.xmlwrapper.XMLConverter;
import org.freerealm.xmlwrapper.XMLConverterUtility;
import org.w3c.dom.Node;

/**
 *
 * @author Deniz ARIKAN
 */
public class SpaceshipDebrisCollectableXMLConverter implements XMLConverter<SpaceshipDebrisCollectable> {

    public String toXML(SpaceshipDebrisCollectable spaceshipDebrisCollectable) {
        StringBuilder xml = new StringBuilder();
        xml.append("<spaceshipDebrisCollectable>\n");
        xml.append("<resourceId>" + spaceshipDebrisCollectable.getResourceId() + "</resourceId>");
        xml.append("<amount>" + spaceshipDebrisCollectable.getAmount() + "</amount>");
        xml.append("</spaceshipDebrisCollectable>\n");
        return xml.toString();
    }

    public SpaceshipDebrisCollectable initializeFromNode(Realm realm, Node node) {
        SpaceshipDebrisCollectable spaceshipDebrisCollectable = new SpaceshipDebrisCollectable();
        FreeMarsController freeMarsController = (FreeMarsController) TagManager.getObjectFromPool("freeMarsController");
        spaceshipDebrisCollectable.setExecutor(freeMarsController);
        int resourceId = Integer.parseInt(XMLConverterUtility.findNode(node, "resourceId").getFirstChild().getNodeValue());
        int amount = Integer.parseInt(XMLConverterUtility.findNode(node, "amount").getFirstChild().getNodeValue());
        spaceshipDebrisCollectable.setResourceId(resourceId);
        spaceshipDebrisCollectable.setAmount(amount);
        return spaceshipDebrisCollectable;
    }
}

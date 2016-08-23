package org.freemars.mission;

import java.util.Iterator;
import org.freerealm.Realm;
import org.freerealm.map.Coordinate;
import org.freerealm.xmlwrapper.XMLConverter;
import org.freerealm.xmlwrapper.XMLConverterUtility;
import org.freerealm.xmlwrapper.map.CoordinateXMLConverter;
import org.freerealm.xmlwrapper.player.mission.AbstractMissionXMLHelper;
import org.w3c.dom.Node;

/**
 *
 * @author Deniz ARIKAN
 */
public class CollectDebrisMissionXMLConverter extends AbstractMissionXMLHelper implements XMLConverter<CollectDebrisMission> {

    public String toXML(CollectDebrisMission collectDebrisMission) {
        StringBuilder xml = new StringBuilder();
        xml.append("<collectDebrisMission>\n");
        xml.append("<debrisCoordinates>\n");
        Iterator<Coordinate> iterator = collectDebrisMission.getDebrisCoordinatesIterator();
        while (iterator.hasNext()) {
            xml.append(new CoordinateXMLConverter().toXML(iterator.next()) + "\n");
        }
        xml.append("</debrisCoordinates>\n");
        xml.append(getInnerXML(collectDebrisMission));
        xml.append(getRewardsXML(collectDebrisMission));
        xml.append("</collectDebrisMission>\n");
        return xml.toString();
    }

    public CollectDebrisMission initializeFromNode(Realm realm, Node node) {
        CollectDebrisMission collectDebrisMission = new CollectDebrisMission();
        Node debrisCoordinatesNode = XMLConverterUtility.findNode(node, "debrisCoordinates");
        if (debrisCoordinatesNode != null) {
            for (Node debrisCoordinateNode = debrisCoordinatesNode.getFirstChild(); debrisCoordinateNode != null; debrisCoordinateNode = debrisCoordinateNode.getNextSibling()) {
                if (debrisCoordinateNode.getNodeType() == Node.ELEMENT_NODE) {
                    collectDebrisMission.addDebrisCoordinate(new CoordinateXMLConverter().initializeFromNode(realm, debrisCoordinateNode));
                }
            }
        }
        initializeMissionFromNode(realm, node, collectDebrisMission);
        initializeMissionRewardsFromNode(realm, node, collectDebrisMission);
        return collectDebrisMission;
    }
}

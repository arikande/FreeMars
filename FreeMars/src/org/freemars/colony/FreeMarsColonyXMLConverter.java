package org.freemars.colony;

import java.util.Iterator;
import org.freerealm.Realm;
import org.freerealm.map.Coordinate;
import org.freerealm.xmlwrapper.XMLConverter;
import org.freerealm.xmlwrapper.XMLConverterUtility;
import org.freerealm.xmlwrapper.city.FreeRealmSettlementXMLConverter;
import org.freerealm.xmlwrapper.map.CoordinateXMLConverter;
import org.w3c.dom.Node;

/**
 *
 * @author Deniz ARIKAN
 */
public class FreeMarsColonyXMLConverter implements XMLConverter<FreeMarsColony> {

    public String toXML(FreeMarsColony freeMarsColony) {
        StringBuilder xml = new StringBuilder();
        xml.append("<FreeMarsColony>\n");
        xml.append("<autoUsingFertilizer>" + freeMarsColony.isAutoUsingFertilizer() + "</autoUsingFertilizer>");
        xml.append("<fertilizedCoordinates>\n");
        Iterator<Coordinate> fertilizedCoordinatesIterator = freeMarsColony.getFertilizedCoordinatesIterator();
        while (fertilizedCoordinatesIterator.hasNext()) {
            Coordinate coordinate = fertilizedCoordinatesIterator.next();
            xml.append(new CoordinateXMLConverter().toXML(coordinate) + "\n");
        }
        xml.append("</fertilizedCoordinates>\n");
        xml.append(new FreeRealmSettlementXMLConverter().getInnerXML(freeMarsColony) + "\n");
        xml.append("</FreeMarsColony>");
        return xml.toString();
    }

    public FreeMarsColony initializeFromNode(Realm realm, Node node) {
        FreeMarsColony freeMarsColony = new FreeMarsColony(realm);
        Node autoUsingFertilizerNode = XMLConverterUtility.findNode(node, "autoUsingFertilizer");
        boolean autoUsingFertilizer = Boolean.parseBoolean(autoUsingFertilizerNode.getFirstChild().getNodeValue());
        freeMarsColony.setAutoUsingFertilizer(autoUsingFertilizer);
        Node fertilizedCoordinatesNode = XMLConverterUtility.findNode(node, "fertilizedCoordinates");
        if (fertilizedCoordinatesNode != null) {
            for (Node subNode = fertilizedCoordinatesNode.getFirstChild(); subNode != null; subNode = subNode.getNextSibling()) {
                if (subNode.getNodeType() == Node.ELEMENT_NODE) {
                    freeMarsColony.addFertilizedCoordinate(new CoordinateXMLConverter().initializeFromNode(realm, subNode));
                }
            }
        }
        new FreeRealmSettlementXMLConverter().populateFreeRealmSettlementFromNode(freeMarsColony, realm, node);
        return freeMarsColony;
    }
}

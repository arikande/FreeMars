package org.freerealm.xmlwrapper.diplomacy;

import java.util.Iterator;
import org.freerealm.Realm;
import org.freerealm.diplomacy.Diplomacy;
import org.freerealm.diplomacy.PlayerRelation;
import org.freerealm.xmlwrapper.XMLConverter;
import org.freerealm.xmlwrapper.XMLConverterUtility;
import org.w3c.dom.Node;

/**
 *
 * @author Deniz ARIKAN
 */
public class DiplomacyXMLConverter implements XMLConverter<Diplomacy> {

    public String toXML(Diplomacy diplomacy) {
        StringBuilder xml = new StringBuilder();
        xml.append("<diplomacy>");
        xml.append(System.getProperty("line.separator"));
        PlayerRelationXMLConverter playerRelationXMLConverter = new PlayerRelationXMLConverter();
        xml.append("<relations>");
        xml.append(System.getProperty("line.separator"));
        Iterator<PlayerRelation> iterator = diplomacy.getPlayerRelationsIterator();
        while (iterator.hasNext()) {
            PlayerRelation playerRelation = iterator.next();
            xml.append(playerRelationXMLConverter.toXML(playerRelation));
        }
        xml.append("</relations>");
        xml.append(System.getProperty("line.separator"));
        xml.append("</diplomacy>");
        xml.append(System.getProperty("line.separator"));
        return xml.toString();
    }

    public Diplomacy initializeFromNode(Realm realm, Node node) {
        Diplomacy diplomacy = new Diplomacy();
        PlayerRelationXMLConverter playerRelationXMLConverter = new PlayerRelationXMLConverter();
        Node tileTypesNode = XMLConverterUtility.findNode(node, "relations");
        for (Node subNode = tileTypesNode.getFirstChild(); subNode != null; subNode = subNode.getNextSibling()) {
            if (subNode.getNodeType() == Node.ELEMENT_NODE) {
                PlayerRelation playerRelation = playerRelationXMLConverter.initializeFromNode(realm, subNode);
                diplomacy.addPlayerRelation(playerRelation);
            }
        }
        return diplomacy;
    }
}

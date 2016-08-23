package org.freerealm.xmlwrapper.player;

import java.awt.Color;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.Vector;
import org.freerealm.Realm;
import org.freerealm.nation.Nation;
import org.freerealm.nation.NationManager;
import org.freerealm.xmlwrapper.XMLWrapper;
import org.w3c.dom.Node;

/**
 *
 * @author Deniz ARIKAN
 */
public class NationManagerXMLWrapper implements XMLWrapper {

    private final NationManager nationManager;

    public NationManagerXMLWrapper(NationManager nationManager) {
        this.nationManager = nationManager;
    }

    public String toXML() {
        StringBuilder xml = new StringBuilder();
        xml.append("<Nations>\n");
        for (Iterator<Nation> iterator = nationManager.getNationsIterator(); iterator.hasNext();) {
            xml.append((new NationXMLConverter()).toXML(iterator.next()) + "\n");
        }
        xml.append("</Nations>");
        return xml.toString();
    }

    public void initializeFromNode(Realm realm, Node node) {
        for (Node nationNode = node.getFirstChild(); nationNode != null; nationNode = nationNode.getNextSibling()) {
            if (nationNode.getNodeType() == Node.ELEMENT_NODE) {
                Nation nation = parseNationNode(nationNode);
                nationManager.addNation(nation);
            }
        }
    }

    private Nation parseNationNode(Node node) {
        Nation parsedNation = new Nation();
        String name = node.getAttributes().getNamedItem("name").getNodeValue();
        parsedNation.setName(name);
        parsedNation.setId(Integer.parseInt(node.getAttributes().getNamedItem("id").getNodeValue()));
        for (Node subNode = node.getFirstChild(); subNode != null; subNode = subNode.getNextSibling()) {
            if (subNode.getNodeType() == Node.ELEMENT_NODE) {
                if (subNode.getNodeName().equals("adjective")) {
                    String adjective = subNode.getFirstChild().getNodeValue().trim();
                    parsedNation.setAdjective(adjective);
                }
                if (subNode.getNodeName().equals("countryName")) {
                    String countryName = subNode.getFirstChild().getNodeValue().trim();
                    parsedNation.setCountryName(countryName);
                }
                if (subNode.getNodeName().equals("defaultColor1")) {
                    String defaultColor = subNode.getFirstChild().getNodeValue().trim();
                    StringTokenizer stringTokenizer = new StringTokenizer(defaultColor, ",");
                    int red = Integer.parseInt(stringTokenizer.nextToken());
                    int green = Integer.parseInt(stringTokenizer.nextToken());
                    int blue = Integer.parseInt(stringTokenizer.nextToken());
                    parsedNation.setDefaultColor1(new Color(red, green, blue));
                }
                if (subNode.getNodeName().equals("defaultColor2")) {
                    String defaultColor = subNode.getFirstChild().getNodeValue().trim();
                    StringTokenizer stringTokenizer = new StringTokenizer(defaultColor, ",");
                    int red = Integer.parseInt(stringTokenizer.nextToken());
                    int green = Integer.parseInt(stringTokenizer.nextToken());
                    int blue = Integer.parseInt(stringTokenizer.nextToken());
                    parsedNation.setDefaultColor2(new Color(red, green, blue));
                }
                if (subNode.getNodeName().equals("settlementNames")) {
                    Vector<String> cityNames = new Vector<String>();
                    for (Node cityNameNode = subNode.getFirstChild(); cityNameNode != null; cityNameNode = cityNameNode.getNextSibling()) {
                        if (cityNameNode.getNodeType() == Node.ELEMENT_NODE) {
                            String cityName = cityNameNode.getFirstChild().getNodeValue().trim();
                            cityNames.add(cityName);
                        }
                    }
                    parsedNation.setSettlementNames(cityNames);
                }
            }
        }
        return parsedNation;
    }
}

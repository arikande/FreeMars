package org.freerealm.xmlwrapper.random;

import java.util.Iterator;
import org.freerealm.Realm;
import org.freerealm.random.RandomEvent;
import org.freerealm.random.RandomEventGenerator;
import org.freerealm.xmlwrapper.XMLConverter;
import org.w3c.dom.Node;

/**
 *
 * @author Deniz ARIKAN
 */
public class RandomEventGeneratorXMLConverter implements XMLConverter<RandomEventGenerator> {

    public String toXML(RandomEventGenerator randomEventGenerator) {
        StringBuilder xml = new StringBuilder();
        xml.append("<randomEvents>\n");
        Iterator<Integer> randomEventIdsIterator = randomEventGenerator.getRandomEventIdsIterator();
        while (randomEventIdsIterator.hasNext()) {
            Integer id = randomEventIdsIterator.next();
            RandomEvent randomEvent = randomEventGenerator.getRandomEvent(id);
            xml.append(new RandomEventXMLConverter().toXML(randomEvent) + "\n");
        }
        xml.append("</randomEvents>\n");
        return xml.toString();
    }

    public RandomEventGenerator initializeFromNode(Realm realm, Node node) {
        RandomEventGenerator randomEventGenerator = new RandomEventGenerator();
        for (Node randomEventNode = node.getFirstChild(); randomEventNode != null; randomEventNode = randomEventNode.getNextSibling()) {
            if (randomEventNode.getNodeType() == Node.ELEMENT_NODE) {
                RandomEvent randomEvent = new RandomEventXMLConverter().initializeFromNode(realm, randomEventNode);
                randomEventGenerator.registerRandomEvent(randomEvent);
            }
        }
        return randomEventGenerator;
    }
}

package org.freerealm.xmlwrapper.vegetation;

import java.util.Iterator;
import org.freerealm.Realm;
import org.freerealm.vegetation.VegetationType;
import org.freerealm.vegetation.VegetationTypeManager;
import org.freerealm.vegetation.VegetationTypeManagerImpl;
import org.freerealm.xmlwrapper.XMLConverter;
import org.w3c.dom.Node;

/**
 *
 * @author Deniz ARIKAN
 */
public class VegetationManagerImplXMLConverter implements XMLConverter<VegetationTypeManager> {

    public String toXML(VegetationTypeManager vegetationManager) {
        StringBuilder xml = new StringBuilder();
        xml.append("<VegetationTypes>\n");
        Iterator<VegetationType> iterator = vegetationManager.getVegetationTypesIterator();
        while (iterator.hasNext()) {
            xml.append(new VegetationImplXMLConverter().toXML(iterator.next()));
        }
        xml.append("</VegetationTypes>\n");
        return xml.toString();
    }

    public VegetationTypeManager initializeFromNode(Realm realm, Node node) {
        VegetationTypeManagerImpl vegetationManager = new VegetationTypeManagerImpl();
        for (Node vegetationNode = node.getFirstChild(); vegetationNode != null; vegetationNode = vegetationNode.getNextSibling()) {
            if (vegetationNode.getNodeType() == Node.ELEMENT_NODE) {
                VegetationType vegetation = (new VegetationImplXMLConverter()).initializeFromNode(realm, vegetationNode);
                vegetationManager.addVegetationType(vegetation);
            }
        }
        return vegetationManager;
    }
}

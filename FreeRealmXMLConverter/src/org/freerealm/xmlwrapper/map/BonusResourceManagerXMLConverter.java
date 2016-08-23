package org.freerealm.xmlwrapper.map;

import java.util.Iterator;
import org.freerealm.Realm;
import org.freerealm.resource.bonus.BonusResource;
import org.freerealm.resource.bonus.BonusResourceManager;
import org.freerealm.resource.bonus.FreeRealmBonusResource;
import org.freerealm.xmlwrapper.XMLConverter;
import org.w3c.dom.Node;

/**
 *
 * @author Deniz ARIKAN
 */
public class BonusResourceManagerXMLConverter implements XMLConverter<BonusResourceManager> {

    public String toXML(BonusResourceManager bonusResourceManager) {
        StringBuilder xml = new StringBuilder();
        xml.append("<bonusResources>\n");
        Iterator<BonusResource> iterator = bonusResourceManager.getBonusResourcesIterator();
        while (iterator.hasNext()) {
            FreeRealmBonusResource bonusResourceImpl = (FreeRealmBonusResource) iterator.next();
            xml.append(new BonusResourceImplXMLConverter().toXML(bonusResourceImpl));
        }
        xml.append("</bonusResources>\n");
        return xml.toString();
    }

    public BonusResourceManager initializeFromNode(Realm realm, Node node) {
        BonusResourceManager bonusResourceManager = new BonusResourceManager();
        for (Node bonusResourceNode = node.getFirstChild(); bonusResourceNode != null; bonusResourceNode = bonusResourceNode.getNextSibling()) {
            if (bonusResourceNode.getNodeType() == Node.ELEMENT_NODE) {
                FreeRealmBonusResource bonusResource = new BonusResourceImplXMLConverter().initializeFromNode(realm, bonusResourceNode);
                bonusResourceManager.addBonusResource(bonusResource);
            }
        }
        return bonusResourceManager;
    }
}

package org.freerealm.xmlwrapper;

import org.freerealm.Realm;
import org.w3c.dom.Node;

/**
 *
 * @author Deniz ARIKAN
 */
public interface XMLWrapper {

    public String toXML();

    public void initializeFromNode(Realm realm, Node node);
}

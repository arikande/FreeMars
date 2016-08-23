package org.freerealm.xmlwrapper.property;

import org.freerealm.Realm;
import org.freerealm.property.NotRemovedByTileTransformationProperty;
import org.freerealm.xmlwrapper.XMLConverter;
import org.w3c.dom.Node;

/**
 *
 * @author Deniz ARIKAN
 */
public class NotRemovedByTileTransformationPropertyXMLConverter implements XMLConverter<NotRemovedByTileTransformationProperty> {

    public String toXML(NotRemovedByTileTransformationProperty notRemovedByTileTransformationProperty) {
        StringBuilder xml = new StringBuilder();
        xml.append("<not_removed_by_tile_transformation/>");
        return xml.toString();
    }

    public NotRemovedByTileTransformationProperty initializeFromNode(Realm realm, Node node) {
        return new NotRemovedByTileTransformationProperty();
    }
}

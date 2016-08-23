package org.freerealm.xmlwrapper.property;

import org.freerealm.Realm;
import org.freerealm.property.ChangeTileTypeProperty;
import org.freerealm.xmlwrapper.XMLConverter;
import org.w3c.dom.Node;

/**
 *
 * @author Deniz ARIKAN
 */
public class ChangeTileTypePropertyXMLConverter implements XMLConverter<ChangeTileTypeProperty> {

    public String toXML(ChangeTileTypeProperty changeTileTypeProperty) {
        StringBuilder xml = new StringBuilder();
        xml.append("<ChangeTileTypeProperty productionPoints=\"");
        xml.append(changeTileTypeProperty.getProductionPoints());
        xml.append("\" />\n");
        return xml.toString();
    }

    public ChangeTileTypeProperty initializeFromNode(Realm realm, Node node) {
        ChangeTileTypeProperty changeTileTypeProperty = new ChangeTileTypeProperty();
        String productionPointsValue = node.getAttributes().getNamedItem("productionPoints").getNodeValue();
        changeTileTypeProperty.setProductionPoints(Integer.parseInt(productionPointsValue));
        return changeTileTypeProperty;
    }
}

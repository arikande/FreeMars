package org.freerealm.xmlwrapper.property;

import org.freerealm.Realm;
import org.freerealm.property.ModifyUnitTypeProperty;
import org.freerealm.xmlwrapper.XMLConverter;
import org.freerealm.xmlwrapper.XMLConverterUtility;
import org.w3c.dom.Node;

/**
 *
 * @author Deniz ARIKAN
 */
public class ModifyUnitTypePropertyXMLConverter implements XMLConverter<ModifyUnitTypeProperty> {

    public String toXML(ModifyUnitTypeProperty modifyUnitTypeProperty) {
        StringBuilder xml = new StringBuilder();
        xml.append("<ModifyUnitTypeProperty>\n");
        if (modifyUnitTypeProperty.isActiveOnAllUnitTypes()) {
            xml.append("<unitType>" + ModifyUnitTypeProperty.ALL + "</unitType>\n");
        } else {
            xml.append("<unitType>" + modifyUnitTypeProperty.getUnitType() + "</unitType>\n");
        }
        xml.append("<propertyName>" + modifyUnitTypeProperty.getPropertyName() + "</propertyName>\n");
        xml.append("<modifierName>" + modifyUnitTypeProperty.getModifierName() + "</modifierName>\n");
        xml.append("<modifier>" + modifyUnitTypeProperty.getModifier() + "</modifier>\n");
        xml.append("</ModifyUnitTypeProperty>");
        return xml.toString();
    }

    public ModifyUnitTypeProperty initializeFromNode(Realm realm, Node node) {
        ModifyUnitTypeProperty modifyUnitTypeProperty = new ModifyUnitTypeProperty();
        String unitTypeValue = XMLConverterUtility.findNode(node, "unitType").getFirstChild().getNodeValue().trim();
        if (unitTypeValue.equals(ModifyUnitTypeProperty.ALL)) {
            modifyUnitTypeProperty.setActiveOnAllUnitTypes(true);
            modifyUnitTypeProperty.setUnitType(-1);
        } else {
            modifyUnitTypeProperty.setUnitType(Integer.parseInt(unitTypeValue));
        }
        modifyUnitTypeProperty.setPropertyName(XMLConverterUtility.findNode(node, "propertyName").getFirstChild().getNodeValue());
        modifyUnitTypeProperty.setModifierName(XMLConverterUtility.findNode(node, "modifierName").getFirstChild().getNodeValue());
        modifyUnitTypeProperty.setModifier(Integer.parseInt(XMLConverterUtility.findNode(node, "modifier").getFirstChild().getNodeValue()));
        return modifyUnitTypeProperty;
    }
}

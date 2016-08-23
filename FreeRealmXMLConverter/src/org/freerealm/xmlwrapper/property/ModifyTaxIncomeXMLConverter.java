package org.freerealm.xmlwrapper.property;

import org.freerealm.Realm;
import org.freerealm.property.ModifyTaxIncome;
import org.freerealm.xmlwrapper.XMLConverter;
import org.w3c.dom.Node;

/**
 *
 * @author Deniz ARIKAN
 */
public class ModifyTaxIncomeXMLConverter implements XMLConverter<ModifyTaxIncome> {

    public String toXML(ModifyTaxIncome modifyTaxIncome) {

        return "<ModifyTaxIncome modifier=\"" + modifyTaxIncome.getModifier() + "\" />";
    }

    public ModifyTaxIncome initializeFromNode(Realm realm, Node node) {
        ModifyTaxIncome modifyTaxIncome = new ModifyTaxIncome();
        String modifierString = node.getAttributes().getNamedItem("modifier").getNodeValue();
        modifyTaxIncome.setModifier(Integer.parseInt(modifierString));
        return modifyTaxIncome;
    }
}

package org.freemars.player;

import org.freerealm.Realm;
import org.freerealm.xmlwrapper.XMLConverter;
import org.freerealm.xmlwrapper.XMLConverterUtility;
import org.w3c.dom.Node;

/**
 *
 * @author Deniz ARIKAN
 */
public class ResourceTradeDataXMLConverter implements XMLConverter<ResourceTradeData> {

    public String toXML(ResourceTradeData resourceTradeData) {
        StringBuilder xml = new StringBuilder();
        xml.append("<resourceTradeData>\n");
        xml.append("<quantityExported>");
        xml.append(resourceTradeData.getQuantityExported());
        xml.append("</quantityExported>\n");
        xml.append("<quantityImported>");
        xml.append(resourceTradeData.getQuantityImported());
        xml.append("</quantityImported>\n");
        xml.append("<incomeBeforeTaxes>");
        xml.append(resourceTradeData.getIncomeBeforeTaxes());
        xml.append("</incomeBeforeTaxes>\n");
        xml.append("<tax>");
        xml.append(resourceTradeData.getTax());
        xml.append("</tax>\n");
        xml.append("<netIncome>");
        xml.append(resourceTradeData.getNetIncome());
        xml.append("</netIncome>\n");
        xml.append("<expenditure>");
        xml.append(resourceTradeData.getExpenditure());
        xml.append("</expenditure>\n");
        xml.append("</resourceTradeData>\n");
        return xml.toString();
    }

    public ResourceTradeData initializeFromNode(Realm realm, Node node) {
        ResourceTradeData resourceTradeData = new ResourceTradeData();
        Node quantityExportedNode = XMLConverterUtility.findNode(node, "quantityExported");
        if (quantityExportedNode != null) {
            resourceTradeData.setQuantityExported(Integer.parseInt(quantityExportedNode.getFirstChild().getNodeValue()));
        }
        Node quantityImportedNode = XMLConverterUtility.findNode(node, "quantityImported");
        if (quantityImportedNode != null) {
            resourceTradeData.setQuantityImported(Integer.parseInt(quantityImportedNode.getFirstChild().getNodeValue()));
        }
        Node incomeBeforeTaxesNode = XMLConverterUtility.findNode(node, "incomeBeforeTaxes");
        if (incomeBeforeTaxesNode != null) {
            resourceTradeData.setIncomeBeforeTaxes(Integer.parseInt(incomeBeforeTaxesNode.getFirstChild().getNodeValue()));
        }
        Node taxNode = XMLConverterUtility.findNode(node, "tax");
        if (taxNode != null) {
            resourceTradeData.setTax(Integer.parseInt(taxNode.getFirstChild().getNodeValue()));
        }
        Node netIncomeNode = XMLConverterUtility.findNode(node, "netIncome");
        if (netIncomeNode != null) {
            resourceTradeData.setNetIncome(Integer.parseInt(netIncomeNode.getFirstChild().getNodeValue()));
        }
        Node expenditureNode = XMLConverterUtility.findNode(node, "expenditure");
        if (expenditureNode != null) {
            resourceTradeData.setExpenditure(Integer.parseInt(expenditureNode.getFirstChild().getNodeValue()));
        }
        return resourceTradeData;
    }
}

package org.freerealm.xmlwrapper.city;

import java.util.Iterator;
import org.freerealm.Realm;
import org.freerealm.resource.Resource;
import org.freerealm.settlement.FreeRealmSettlement;
import org.freerealm.settlement.Settlement;
import org.freerealm.settlement.SettlementBuildable;
import org.freerealm.settlement.improvement.SettlementImprovement;
import org.freerealm.settlement.improvement.SettlementImprovementType;
import org.freerealm.unit.FreeRealmUnitType;
import org.freerealm.xmlwrapper.XMLConverter;
import org.freerealm.xmlwrapper.XMLConverterUtility;
import org.freerealm.xmlwrapper.map.CoordinateXMLConverter;
import org.freerealm.xmlwrapper.map.ResourceStorageManagerXMLConverter;
import org.w3c.dom.Node;

/**
 *
 * @author Deniz ARIKAN
 */
public class FreeRealmSettlementXMLConverter implements XMLConverter<FreeRealmSettlement> {

    public String toXML(FreeRealmSettlement freeRealmSettlement) {
        StringBuilder xml = new StringBuilder();
        xml.append("<settlement>\n");
        xml.append(getInnerXML(freeRealmSettlement));
        xml.append("</settlement>\n");
        return xml.toString();
    }

    public String getInnerXML(Settlement settlement) {
        StringBuilder xml = new StringBuilder();
        xml.append("<id>" + settlement.getId() + "</id>\n");
        xml.append("<name>" + settlement.getName() + "</name>\n");
        xml.append(new CoordinateXMLConverter().toXML(settlement.getCoordinate()) + "\n");
        xml.append("<population>" + settlement.getPopulation() + "</population>\n");
        xml.append("<productionPoints>" + settlement.getProductionPoints() + "</productionPoints>\n");
        xml.append("<productionQueue>\n");
        Iterator<SettlementBuildable> iterator = settlement.getProductionQueueIterator();
        while (iterator.hasNext()) {
            SettlementBuildable settlementBuildable = iterator.next();
            if (settlementBuildable instanceof SettlementImprovementType) {
                SettlementImprovementType cityImprovementType = (SettlementImprovementType) settlementBuildable;
                xml.append("<queueItem>\n");
                xml.append("<type>SETTLEMENT_IMPROVEMENT</type>\n");
                xml.append("<id>" + cityImprovementType.getId() + "</id>\n");
                xml.append("</queueItem>\n");
            } else if (settlementBuildable instanceof FreeRealmUnitType) {
                FreeRealmUnitType unitType = (FreeRealmUnitType) settlementBuildable;
                xml.append("<queueItem>\n");
                xml.append("<type>UNIT</type>\n");
                xml.append("<id>" + unitType.getId() + "</id>\n");
                xml.append("</queueItem>\n");
            }
        }
        xml.append("</productionQueue>\n");
        xml.append("<contiuousProduction>" + settlement.isContiuousProduction() + "</contiuousProduction>\n");
        xml.append("<improvements>\n");
        Iterator<SettlementImprovement> improvementsIterator = settlement.getImprovementsIterator();
        while (improvementsIterator.hasNext()) {
            SettlementImprovement improvement = improvementsIterator.next();
            xml.append("<improvement>\n");
            xml.append("<type>");
            xml.append(improvement.getType().getId());
            xml.append("</type>\n");
            xml.append("<enabled>");
            xml.append(improvement.isEnabled());
            xml.append("</enabled>\n");
            xml.append("<workers>");
            xml.append(improvement.getNumberOfWorkers());
            xml.append("</workers>\n");
            xml.append("</improvement>\n");
        }
        xml.append("</improvements>\n");
        xml.append("<autoManagedResources>\n");
        Iterator<Resource> autoManagedResourcesIterator = settlement.getAutomanagedResourcesIterator();
        while (autoManagedResourcesIterator.hasNext()) {
            Resource resource = autoManagedResourcesIterator.next();
            xml.append("<autoManagedResource>");
            xml.append(resource.getId());
            xml.append("</autoManagedResource>\n");
        }
        xml.append("</autoManagedResources>\n");
        xml.append((new WorkForceManagerXMLWrapper(settlement.getWorkForceManager())).toXML() + "\n");
        xml.append(new ResourceStorageManagerXMLConverter().toXML(settlement.getStorageManager()) + "\n");
        return xml.toString();
    }

    public FreeRealmSettlement initializeFromNode(Realm realm, Node node) {
        FreeRealmSettlement freeRealmSettlement = new FreeRealmSettlement(realm);
        populateFreeRealmSettlementFromNode(freeRealmSettlement, realm, node);
        return freeRealmSettlement;
    }

    public void populateFreeRealmSettlementFromNode(FreeRealmSettlement freeRealmSettlement, Realm realm, Node node) {
        for (Node subNode = node.getFirstChild(); subNode != null; subNode = subNode.getNextSibling()) {
            if (subNode.getNodeType() == Node.ELEMENT_NODE) {
                if (subNode.getNodeName().equals("id")) {
                    int idValue = Integer.parseInt(subNode.getFirstChild().getNodeValue());
                    freeRealmSettlement.setId(idValue);
                } else if (subNode.getNodeName().equals("name")) {
                    freeRealmSettlement.setName(subNode.getFirstChild().getNodeValue());
                } else if (subNode.getNodeName().equals("population")) {
                    int populationValue = Integer.parseInt(subNode.getFirstChild().getNodeValue());
                    freeRealmSettlement.setPopulation(populationValue);
                } else if (subNode.getNodeName().equals("productionPoints")) {
                    int productionPointsValue = Integer.parseInt(subNode.getFirstChild().getNodeValue());
                    freeRealmSettlement.setProductionPoints(productionPointsValue);
                } else if (subNode.getNodeName().equals("coordinate")) {
                    freeRealmSettlement.setCoordinate(new CoordinateXMLConverter().initializeFromNode(realm, subNode));
                } else if (subNode.getNodeName().equals("productionQueue")) {
                    for (Node queueItemNode = subNode.getFirstChild(); queueItemNode != null; queueItemNode = queueItemNode.getNextSibling()) {
                        if (queueItemNode.getNodeType() == Node.ELEMENT_NODE) {
                            Node queueItemTypeNode = XMLConverterUtility.findNode(queueItemNode, "type");
                            String queueItemType = queueItemTypeNode.getFirstChild().getNodeValue();
                            Node queueItemIdNode = XMLConverterUtility.findNode(queueItemNode, "id");
                            int id = Integer.parseInt(queueItemIdNode.getFirstChild().getNodeValue());
                            if (queueItemType.equals("SETTLEMENT_IMPROVEMENT")) {
                                SettlementImprovementType cityImprovementType = realm.getSettlementImprovementManager().getImprovement(id);
                                freeRealmSettlement.addToProductionQueue(cityImprovementType);
                            } else if (queueItemType.equals("UNIT")) {
                                FreeRealmUnitType unitType = realm.getUnitTypeManager().getUnitType(id);
                                freeRealmSettlement.addToProductionQueue(unitType);
                            }
                        }
                    }
                } else if (subNode.getNodeName().equals("contiuousProduction")) {
                    if (subNode.getFirstChild() != null) {
                        Boolean contiuousProductionValue = new Boolean(subNode.getFirstChild().getNodeValue());
                        freeRealmSettlement.setContiuousProduction(contiuousProductionValue);
                    }
                } else if (subNode.getNodeName().equals("improvements")) {
                    freeRealmSettlement.clearImprovements();
                    for (Node improvementNode = subNode.getFirstChild(); improvementNode != null; improvementNode = improvementNode.getNextSibling()) {
                        if (improvementNode.getNodeType() == Node.ELEMENT_NODE) {
                            Node improvementTypeIdNode = XMLConverterUtility.findNode(improvementNode, "type");
                            int improvementId = Integer.parseInt(improvementTypeIdNode.getFirstChild().getNodeValue());
                            Node improvementEnabledNode = XMLConverterUtility.findNode(improvementNode, "enabled");
                            boolean enabled = Boolean.valueOf(improvementEnabledNode.getFirstChild().getNodeValue());
                            Node workersNode = XMLConverterUtility.findNode(improvementNode, "workers");
                            int workers = 0;
                            if (workersNode != null) {
                                workers = Integer.parseInt(workersNode.getFirstChild().getNodeValue());
                            }
                            SettlementImprovementType improvementType = realm.getSettlementImprovementManager().getImprovement(improvementId);
                            SettlementImprovement improvement = new SettlementImprovement();
                            improvement.setType(improvementType);
                            improvement.setEnabled(enabled);
                            improvement.setNumberOfWorkers(workers);
                            freeRealmSettlement.addImprovement(improvement);
                        }
                    }
                } else if (subNode.getNodeName().equals("autoManagedResources")) {
                    freeRealmSettlement.clearAutomanagedResources();

                    for (Node autoManagedResourceNode = subNode.getFirstChild(); autoManagedResourceNode != null; autoManagedResourceNode = autoManagedResourceNode.getNextSibling()) {
                        if (autoManagedResourceNode.getNodeType() == Node.ELEMENT_NODE) {
                            int resourceId = Integer.parseInt(autoManagedResourceNode.getFirstChild().getNodeValue());
                            Resource resource = realm.getResourceManager().getResource(resourceId);
                            freeRealmSettlement.addAutomanagedResource(resource);
                        }
                    }

                } else if (subNode.getNodeName().equals("allWorkForce")) {
                    (new WorkForceManagerXMLWrapper(freeRealmSettlement.getWorkForceManager())).initializeFromNode(realm, subNode);
                } else if (subNode.getNodeName().equals("ResourceStorage")) {
                    freeRealmSettlement.setStorageManager(new ResourceStorageManagerXMLConverter().initializeFromNode(realm, subNode));
                }
            }
        }
    }
}

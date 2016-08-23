package org.freerealm.xmlwrapper;

import java.util.Iterator;
import org.freerealm.PopulationChangeManager;
import org.freerealm.Realm;
import org.freerealm.history.History;
import org.freerealm.map.FreeRealmMap;
import org.freerealm.nation.NationManager;
import org.freerealm.player.Player;
import org.freerealm.player.PlayerManager;
import org.freerealm.random.RandomEventGenerator;
import org.freerealm.resource.bonus.BonusResourceManager;
import org.freerealm.settlement.Settlement;
import org.freerealm.settlement.improvement.SettlementImprovementManager;
import org.freerealm.tile.TileTypeManager;
import org.freerealm.tile.improvement.TileImprovementTypeManager;
import org.freerealm.unit.Unit;
import org.freerealm.unit.UnitTypeManager;
import org.freerealm.vegetation.VegetationTypeManager;
import org.freerealm.xmlwrapper.city.SettlementImprovementManagerXMLWrapper;
import org.freerealm.xmlwrapper.history.FreeRealmHistoryXMLConverter;
import org.freerealm.xmlwrapper.map.BonusResourceManagerXMLConverter;
import org.freerealm.xmlwrapper.map.FreeRealmMapXMLConverter;
import org.freerealm.xmlwrapper.map.ResourceManagerXMLConverter;
import org.freerealm.xmlwrapper.map.TileImprovementManagerXMLConverter;
import org.freerealm.xmlwrapper.map.TileTypeManagerXMLConverter;
import org.freerealm.xmlwrapper.player.NationManagerXMLWrapper;
import org.freerealm.xmlwrapper.player.PlayerManagerXMLWrapper;
import org.freerealm.xmlwrapper.random.RandomEventGeneratorXMLConverter;
import org.freerealm.xmlwrapper.unit.UnitTypeManagerXMLWrapper;
import org.freerealm.xmlwrapper.vegetation.VegetationManagerImplXMLConverter;
import org.w3c.dom.Node;

/**
 *
 * @author Deniz ARIKAN
 */
public class RealmXMLWrapper implements XMLWrapper {

    private Realm realm;

    public RealmXMLWrapper(Realm realm) {
        this.realm = realm;
    }

    public String toXML() {
        StringBuilder xml = new StringBuilder();
        xml.append("<free_realm>\n");
        xml.append("<number_of_turns>");
        xml.append(realm.getNumberOfTurns());
        xml.append("</number_of_turns>\n");
        xml.append("<map_item_count>");
        xml.append(realm.getMapItemCount());
        xml.append("</map_item_count>\n");
        xml.append("<required_population_resources>\n");
        Iterator<Integer> requiredPopulationResourcesIterator = realm.getRequiredPopulationResourcesIterator();
        while (requiredPopulationResourcesIterator.hasNext()) {
            int requiredPopulationResourceId = requiredPopulationResourcesIterator.next();
            xml.append("<required_population_resource>\n");
            xml.append("<required_population_resource_id>");
            xml.append(requiredPopulationResourceId);
            xml.append("</required_population_resource_id>\n");
            xml.append("<required_population_resource_amount>");
            xml.append(realm.getRequiredPopulationResourceAmount(requiredPopulationResourceId));
            xml.append("</required_population_resource_amount>\n");
            xml.append("</required_population_resource>\n");
        }
        xml.append("</required_population_resources>\n");
        xml.append(XMLConverterUtility.convertPropertiesToXML(realm.getProperties()));
        xml.append("\n");
        xml.append(new ResourceManagerXMLConverter().toXML(realm.getResourceManager()));
        xml.append("\n");
        xml.append(new TileTypeManagerXMLConverter().toXML(realm.getTileTypeManager()) + "\n");
        xml.append((new BonusResourceManagerXMLConverter()).toXML(realm.getBonusResourceManager()) + "\n");
        xml.append((new TileImprovementManagerXMLConverter()).toXML(realm.getTileImprovementTypeManager()) + "\n");
        xml.append(new VegetationManagerImplXMLConverter().toXML(realm.getVegetationManager()));
        xml.append((new UnitTypeManagerXMLWrapper(realm.getUnitTypeManager())).toXML() + "\n");
        xml.append((new SettlementImprovementManagerXMLWrapper(realm.getSettlementImprovementManager())).toXML() + "\n");
        xml.append((new NationManagerXMLWrapper(realm.getNationManager())).toXML() + "\n");
        xml.append(new PlayerManagerXMLWrapper(realm.getPlayerManager()).toXML() + "\n");
        xml.append(new FreeRealmHistoryXMLConverter().toXML(realm.getHistory()) + "\n");
        xml.append(new FreeRealmMapXMLConverter().toXML((FreeRealmMap) realm.getMap()));
        xml.append(new PopulationChangeManagerXMLConverter().toXML(realm.getPopulationChangeManager()));
        xml.append(new RandomEventGeneratorXMLConverter().toXML(realm.getRandomEventGenerator()) + "\n");
        xml.append("</free_realm>\n");
        return xml.toString();
    }

    public void initializeFromNode(Realm realm, Node node) {
        this.realm = realm;
        Node numberOfTurnsNode = XMLConverterUtility.findNode(node, "number_of_turns");
        int numberOfTurns = Integer.parseInt(numberOfTurnsNode.getFirstChild().getNodeValue());
        realm.setNumberOfTurns(numberOfTurns);

        Node managedObjectCountNode = XMLConverterUtility.findNode(node, "map_item_count");
        int mapItemCount = Integer.parseInt(managedObjectCountNode.getFirstChild().getNodeValue());
        realm.setMapItemCount(mapItemCount);

        Node requiredPopulationResourcesNode = XMLConverterUtility.findNode(node, "required_population_resources");
        for (Node resourceNode = requiredPopulationResourcesNode.getFirstChild(); resourceNode != null; resourceNode = resourceNode.getNextSibling()) {
            if (resourceNode.getNodeType() == Node.ELEMENT_NODE) {
                Node resourceIdNode = XMLConverterUtility.findNode(resourceNode, "required_population_resource_id");
                int resourceId = Integer.parseInt(resourceIdNode.getFirstChild().getNodeValue());
                Node resourceAmountNode = XMLConverterUtility.findNode(resourceNode, "required_population_resource_amount");
                int resourceAmount = Integer.parseInt(resourceAmountNode.getFirstChild().getNodeValue());
                realm.addRequiredPopulationResourceAmount(resourceId, resourceAmount);
            }
        }
        Node propertiesNode = XMLConverterUtility.findNode(node, "properties");
        if (propertiesNode != null) {
            realm.setProperties(XMLConverterUtility.convertNodeToProperties(propertiesNode));
        }
        Node resourcesNode = XMLConverterUtility.findNode(node, "resources");
        realm.setResourceManager(new ResourceManagerXMLConverter().initializeFromNode(realm, resourcesNode));

        Node tileTypesNode = XMLConverterUtility.findNode(node, "tileTypes");
        TileTypeManager tileTypeManager = new TileTypeManagerXMLConverter().initializeFromNode(realm, tileTypesNode);
        realm.setTileTypeManager(tileTypeManager);

        Node bonusResourcesNode = XMLConverterUtility.findNode(node, "bonusResources");
        BonusResourceManager bonusResourceManager = (new BonusResourceManagerXMLConverter()).initializeFromNode(realm, bonusResourcesNode);
        realm.setBonusResourceManager(bonusResourceManager);

        Node tileImprovementsNode = XMLConverterUtility.findNode(node, "tileImprovements");
        TileImprovementTypeManager tileImprovementManager = (new TileImprovementManagerXMLConverter()).initializeFromNode(realm, tileImprovementsNode);
        realm.setTileImprovementTypeManager(tileImprovementManager);

        Node vegetationTypesNode = XMLConverterUtility.findNode(node, "VegetationTypes");
        VegetationTypeManager vegetationManager = (new VegetationManagerImplXMLConverter()).initializeFromNode(realm, vegetationTypesNode);
        realm.setVegetationManager(vegetationManager);

        Node settlementImprovementsNode = XMLConverterUtility.findNode(node, "SettlementImprovements");
        SettlementImprovementManager cityImprovementManager = new SettlementImprovementManager();
        (new SettlementImprovementManagerXMLWrapper(cityImprovementManager)).initializeFromNode(realm, settlementImprovementsNode);
        realm.setSettlementImprovementManager(cityImprovementManager);

        Node unitTypesNode = XMLConverterUtility.findNode(node, "UnitTypes");
        UnitTypeManager unitTypeManager = new UnitTypeManager();
        (new UnitTypeManagerXMLWrapper(unitTypeManager)).initializeFromNode(realm, unitTypesNode);
        realm.setUnitTypeManager(unitTypeManager);

        Node nationsNode = XMLConverterUtility.findNode(node, "Nations");
        NationManager nationManager = new NationManager();
        (new NationManagerXMLWrapper(nationManager)).initializeFromNode(realm, nationsNode);
        realm.setNationManager(nationManager);

        Node playersNode = XMLConverterUtility.findNode(node, "Players");
        PlayerManager playerManager = new PlayerManager();
        (new PlayerManagerXMLWrapper(playerManager)).initializeFromNode(realm, playersNode);
        realm.setPlayerManager(playerManager);

        Node historyNode = XMLConverterUtility.findNode(node, "history");
        History history = (new FreeRealmHistoryXMLConverter()).initializeFromNode(realm, historyNode);
        realm.setHistory(history);

        Node freeRealmMapNode = XMLConverterUtility.findNode(node, "free_realm_map");
        FreeRealmMap freeRealmMap = (new FreeRealmMapXMLConverter()).initializeFromNode(realm, freeRealmMapNode);
        Iterator<Player> playerIterator = realm.getPlayerManager().getPlayersIterator();
        while (playerIterator.hasNext()) {
            Player player = playerIterator.next();
            Iterator<Settlement> settlementsIterator = player.getSettlementsIterator();
            while (settlementsIterator.hasNext()) {
                Settlement settlement = settlementsIterator.next();
                freeRealmMap.addSettlement(settlement);
            }
            Iterator<Unit> unitsIterator = player.getUnitsIterator();
            while (unitsIterator.hasNext()) {
                Unit unit = unitsIterator.next();
                if (unit.getCoordinate() != null) {
                    freeRealmMap.addUnit(unit);
                }
            }
        }
        realm.setMap(freeRealmMap);

        Node randomEventsNode = XMLConverterUtility.findNode(node, "randomEvents");
        RandomEventGenerator randomEventGenerator = new RandomEventGeneratorXMLConverter().initializeFromNode(realm, randomEventsNode);
        realm.setRandomEventGenerator(randomEventGenerator);

        Node populationChangeManagerNode = XMLConverterUtility.findNode(node, "PopulationChangeManager");
        PopulationChangeManager populationChangeManager = new PopulationChangeManagerXMLConverter().initializeFromNode(realm, populationChangeManagerNode);
        realm.setPopulationChangeManager(populationChangeManager);
    }
}

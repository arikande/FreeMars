package org.freerealm.command;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.xerces.parsers.DOMParser;
import org.freerealm.PopulationChangeManager;
import org.freerealm.Realm;
import org.freerealm.history.FreeRealmHistory;
import org.freerealm.nation.NationManager;
import org.freerealm.player.PlayerManager;
import org.freerealm.random.RandomEventGenerator;
import org.freerealm.settlement.improvement.SettlementImprovementManager;
import org.freerealm.unit.UnitTypeManager;
import org.freerealm.xmlwrapper.PopulationChangeManagerXMLConverter;
import org.freerealm.xmlwrapper.city.SettlementImprovementManagerXMLWrapper;
import org.freerealm.xmlwrapper.map.BonusResourceManagerXMLConverter;
import org.freerealm.xmlwrapper.map.ResourceManagerXMLConverter;
import org.freerealm.xmlwrapper.map.TileImprovementManagerXMLConverter;
import org.freerealm.xmlwrapper.map.TileTypeManagerXMLConverter;
import org.freerealm.xmlwrapper.player.NationManagerXMLWrapper;
import org.freerealm.xmlwrapper.player.NationPropertiesReader;
import org.freerealm.xmlwrapper.random.RandomEventGeneratorXMLConverter;
import org.freerealm.xmlwrapper.unit.UnitTypeManagerXMLWrapper;
import org.freerealm.xmlwrapper.vegetation.VegetationManagerImplXMLConverter;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * Command class to initialize the given realm using default configuration.
 * 
 * @author Deniz ARIKAN
 */
public class InitializeRealmCommand extends FreeRealmAbstractCommand {

	public InitializeRealmCommand(Realm realm) {
		super(realm);
	}

	/**
	 * Executes command to initialize the given realm.
	 * 
	 * @return CommandResult
	 */
	public void execute() {
		init();
		Logger.getLogger(InitializeRealmCommand.class).info("InitializeRealmCommand executed successfully.");
		setState(SUCCEEDED);
	}

	private Node getRootNodeFromFile(String filename) {
		DOMParser builder = new DOMParser();
		BufferedInputStream bufferedInputStream = new BufferedInputStream(ClassLoader.getSystemResourceAsStream(filename));
		InputSource inputSource = new InputSource(bufferedInputStream);
		try {
			builder.parse(inputSource);
		} catch (SAXException exception) {
			exception.printStackTrace();
		} catch (IOException exception) {
			exception.printStackTrace();
		}
		return builder.getDocument().getFirstChild();
	}

	private void init() {
		Properties configProperties = new Properties();
		try {
			BufferedInputStream bufferedInputStream = new BufferedInputStream(ClassLoader.getSystemResourceAsStream("config/config.properties"));
			configProperties.load(bufferedInputStream);
			BufferedInputStream gamePropertiesBufferedInputStream = new BufferedInputStream(ClassLoader.getSystemResourceAsStream(configProperties.getProperty("game_properties_filename")));
			Properties gameProperties = new Properties();
			gameProperties.load(gamePropertiesBufferedInputStream);
			getRealm().setProperties(gameProperties);
		} catch (IOException exception) {
			exception.printStackTrace();
		}

		getRealm().setResourceManager(new ResourceManagerXMLConverter().initializeFromNode(getRealm(), getRootNodeFromFile(configProperties.getProperty("resources_filename"))));
		getRealm().setTileTypeManager(new TileTypeManagerXMLConverter().initializeFromNode(getRealm(), getRootNodeFromFile(configProperties.getProperty("tiletypes_filename"))));
		getRealm().setBonusResourceManager(new BonusResourceManagerXMLConverter().initializeFromNode(getRealm(), getRootNodeFromFile(configProperties.getProperty("bonus_resources_filename"))));
		getRealm().setVegetationManager(new VegetationManagerImplXMLConverter().initializeFromNode(getRealm(), getRootNodeFromFile(configProperties.getProperty("vegetation_filename"))));
		getRealm().setTileImprovementTypeManager(
				new TileImprovementManagerXMLConverter().initializeFromNode(getRealm(), getRootNodeFromFile(configProperties.getProperty("tile_improvements_filename"))));

		SettlementImprovementManager settlementImprovementManager = new SettlementImprovementManager();
		(new SettlementImprovementManagerXMLWrapper(settlementImprovementManager))
				.initializeFromNode(getRealm(), getRootNodeFromFile(configProperties.getProperty("settlement_improvements_filename")));
		getRealm().setSettlementImprovementManager(settlementImprovementManager);

		UnitTypeManager unitTypeManager = new UnitTypeManager();
		(new UnitTypeManagerXMLWrapper(unitTypeManager)).initializeFromNode(getRealm(), getRootNodeFromFile(configProperties.getProperty("unittypes_filename")));
		getRealm().setUnitTypeManager(unitTypeManager);

		NationManager nationManager = new NationManager();
		(new NationManagerXMLWrapper(nationManager)).initializeFromNode(getRealm(), getRootNodeFromFile(configProperties.getProperty("nations_filename")));
		getRealm().setNationManager(nationManager);
		new NationPropertiesReader().readNationProperties(getRealm(), configProperties.getProperty("nation_properties_filename"));

		PopulationChangeManager populationChangeManager = new PopulationChangeManagerXMLConverter().initializeFromNode(getRealm(),
				getRootNodeFromFile(configProperties.getProperty("population_change_data_filename")));
		getRealm().setPopulationChangeManager(populationChangeManager);

		RandomEventGenerator randomEventGenerator = new RandomEventGeneratorXMLConverter().initializeFromNode(getRealm(), getRootNodeFromFile(configProperties.getProperty("random_events_filename")));
		getRealm().setRandomEventGenerator(randomEventGenerator);

		getRealm().setPlayerManager(new PlayerManager());
		getRealm().setNumberOfTurns(0);
		getRealm().setHistory(new FreeRealmHistory());
	}
}

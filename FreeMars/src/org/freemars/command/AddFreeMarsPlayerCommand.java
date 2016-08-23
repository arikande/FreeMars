package org.freemars.command;

import java.awt.Color;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.freemars.ai.ExpeditionaryForceDecisionModel;
import org.freemars.ai.ExpeditionaryForcePlayer;
import org.freemars.controller.FreeMarsController;
import org.freemars.earth.Location;
import org.freemars.model.FreeMarsModel;
import org.freemars.tile.SpaceshipDebrisCollectable;
import org.freemars.util.PlayerStartCoordinateGenerator;
import org.freerealm.Realm;
import org.freerealm.command.AddPlayerCommand;
import org.freerealm.command.AddUnitCommand;
import org.freerealm.command.FreeRealmAbstractCommand;
import org.freerealm.command.SetPlayerStatusCommand;
import org.freerealm.command.SetTileCollectableCommand;
import org.freerealm.command.UnitSuspendCommand;
import org.freerealm.map.Coordinate;
import org.freerealm.nation.Nation;
import org.freerealm.player.Player;
import org.freerealm.property.Property;
import org.freerealm.property.SetStartupUnitCountProperty;
import org.freerealm.tile.Tile;
import org.freerealm.unit.FreeRealmUnitType;
import org.freerealm.unit.Unit;

/**
 * 
 * @author Deniz ARIKAN
 */
public class AddFreeMarsPlayerCommand extends FreeRealmAbstractCommand {

	private final FreeMarsController freeMarsController;
	private final Player player;
	private final Nation nation;

	public AddFreeMarsPlayerCommand(FreeMarsController freeMarsController, Player player, Nation nation) {
		this.freeMarsController = freeMarsController;
		this.player = player;
		this.nation = nation;
	}

	public void execute() {
		Realm realm = freeMarsController.getFreeMarsModel().getRealm();
		player.setId(freeMarsController.getFreeMarsModel().getRealm().getPlayerManager().getNextAvailablePlayerId());
		player.setStatus(Player.STATUS_ACTIVE);
		player.setName(nation.getName());
		player.setNation(nation);
		player.setPrimaryColor(nation.getDefaultColor1());
		player.setSecondaryColor(nation.getDefaultColor2());
		Iterator<Property> propertiesIterator = nation.getPropertiesIterator();
		while (propertiesIterator.hasNext()) {
			Property property = propertiesIterator.next();
			player.addProperty(property);
		}
		getExecutor().execute(new AddPlayerCommand(realm, player));
		Coordinate coordinate = PlayerStartCoordinateGenerator.generate(freeMarsController);
		int scoutCount = 1;
		FreeRealmUnitType scoutType = realm.getUnitTypeManager().getUnitType("Scout");
		for (int i = 0; i < scoutCount; i++) {
			getExecutor().execute(new AddUnitCommand(realm, player, new Unit(realm, scoutType, coordinate, player)));
		}
		FreeRealmUnitType colonizerType = realm.getUnitTypeManager().getUnitType("Colonizer");
		int startupColonizerCount = 2;
		SetStartupUnitCountProperty setStartupUnitCount = (SetStartupUnitCountProperty) player.getProperty(SetStartupUnitCountProperty.NAME);
		if (setStartupUnitCount != null && colonizerType.equals(setStartupUnitCount.getUnitType())) {
			startupColonizerCount = setStartupUnitCount.getCount();
		}
		for (int i = 0; i < startupColonizerCount; i++) {
			getExecutor().execute(new AddUnitCommand(realm, player, new Unit(realm, colonizerType, coordinate, player)));
		}
		FreeRealmUnitType engineerType = realm.getUnitTypeManager().getUnitType("Engineer");
		getExecutor().execute(new AddUnitCommand(realm, player, new Unit(realm, engineerType, coordinate, player)));
		getExecutor().execute(new AddUnitCommand(realm, player, new Unit(realm, engineerType, coordinate, player)));

		FreeRealmUnitType militiaType = realm.getUnitTypeManager().getUnitType("Militia");
		getExecutor().execute(new AddUnitCommand(realm, player, new Unit(realm, militiaType, coordinate, player)));

		FreeRealmUnitType shuttleType = realm.getUnitTypeManager().getUnitType("Shuttle");
		Unit shuttle = new Unit(realm, shuttleType, coordinate, player);
		getExecutor().execute(new AddUnitCommand(realm, player, shuttle));
		getExecutor().execute(new UnitSuspendCommand(realm, player, shuttle));
		freeMarsController.getFreeMarsModel().getEarth().addUnitLocation(shuttle, Location.MARS_ORBIT);
		addLandingModuleDebris(realm, coordinate);
		getExecutor().execute(new AddPlayerCommand(realm, getRelatedExpeditionaryForcePlayer(freeMarsController.getFreeMarsModel(), player)));
		putParameter("starting_coordinate", coordinate);
		setState(SUCCEEDED);
	}

	private void addLandingModuleDebris(Realm realm, Coordinate centerCoordinate) {
		for (int i = 3; i < 5; i++) {
			List<Coordinate> coordinates = freeMarsController.getFreeMarsModel().getRealm().getCircleCoordinates(centerCoordinate, i);
			for (int j = 0; j < 6; j++) {
				Coordinate coordinate = getRandomCoordinateForCollectable(realm, coordinates);
				if (coordinate != null) {
					SpaceshipDebrisCollectable spaceshipDebrisCollectable = new SpaceshipDebrisCollectable();
					spaceshipDebrisCollectable.setExecutor(freeMarsController);
					Random random = new Random();
					int resourceRandom = random.nextInt(5);
					switch (resourceRandom) {
					case 0:
						spaceshipDebrisCollectable.setResourceId(2);
						break;
					case 1:
						spaceshipDebrisCollectable.setResourceId(3);
						break;
					case 2:
						spaceshipDebrisCollectable.setResourceId(6);
						break;
					case 3:
						spaceshipDebrisCollectable.setResourceId(7);
						break;
					case 4:
						spaceshipDebrisCollectable.setResourceId(8);
						break;
					}
					int amountRandom = random.nextInt(5);
					spaceshipDebrisCollectable.setAmount(400 + 50 * amountRandom);
					freeMarsController.execute(new SetTileCollectableCommand(freeMarsController.getFreeMarsModel().getTile(coordinate), spaceshipDebrisCollectable));
				}
			}
		}
	}

	private Coordinate getRandomCoordinateForCollectable(Realm realm, List<Coordinate> coordinates) {
		for (int i = 0; i < 5; i++) {
			Random randomGenerator = new Random();
			int coordinateIndex = randomGenerator.nextInt(coordinates.size());
			Coordinate coordinate = coordinates.get(coordinateIndex);
			Tile tile = realm.getTile(coordinate);
			if (tile != null && tile.getType().getId() != 6 && tile.getType().getId() != 7 && tile.getType().getId() != 10) {
				return coordinate;
			}
		}
		return null;
	}

	private ExpeditionaryForcePlayer getRelatedExpeditionaryForcePlayer(FreeMarsModel freeMarsModel, Player player) {
		ExpeditionaryForcePlayer expeditionaryForcePlayer = new ExpeditionaryForcePlayer(freeMarsModel.getRealm());
		expeditionaryForcePlayer.setId(freeMarsModel.getRealm().getPlayerManager().getNextAvailablePlayerId());
		expeditionaryForcePlayer.setName(player.getNation().getAdjective() + " expeditionary force");
		expeditionaryForcePlayer.setNation(player.getNation());
		expeditionaryForcePlayer.setPrimaryColor(Color.black);
		expeditionaryForcePlayer.setSecondaryColor(Color.lightGray);
		expeditionaryForcePlayer.setTargetPlayerId(player.getId());
		expeditionaryForcePlayer.setDecisionModel(new ExpeditionaryForceDecisionModel(freeMarsController, expeditionaryForcePlayer));
		freeMarsController.execute(new SetPlayerStatusCommand(expeditionaryForcePlayer, Player.STATUS_PASSIVE));
		FreeRealmUnitType infantryUnitType = freeMarsModel.getRealm().getUnitTypeManager().getUnitType("Infantry");
		for (int i = 0; i < ExpeditionaryForcePlayer.BASE_INFANTRY_COUNT; i++) {
			Unit infantryUnit = new Unit(freeMarsModel.getRealm(), infantryUnitType, null, expeditionaryForcePlayer);
			freeMarsController.execute(new AddUnitCommand(freeMarsModel.getRealm(), expeditionaryForcePlayer, infantryUnit, Unit.UNIT_SUSPENDED));
		}
		FreeRealmUnitType mechUnitType = freeMarsModel.getRealm().getUnitTypeManager().getUnitType("Mech");
		for (int i = 0; i < ExpeditionaryForcePlayer.BASE_MECH_COUNT; i++) {
			Unit mechUnit = new Unit(freeMarsModel.getRealm(), mechUnitType, null, expeditionaryForcePlayer);
			freeMarsController.execute(new AddUnitCommand(freeMarsModel.getRealm(), expeditionaryForcePlayer, mechUnit, Unit.UNIT_SUSPENDED));
		}
		FreeRealmUnitType LGTUnitType = freeMarsModel.getRealm().getUnitTypeManager().getUnitType("LGT");
		for (int i = 0; i < ExpeditionaryForcePlayer.BASE_LGT_COUNT; i++) {
			Unit LGTUnit = new Unit(freeMarsModel.getRealm(), LGTUnitType, null, expeditionaryForcePlayer);
			freeMarsController.execute(new AddUnitCommand(freeMarsModel.getRealm(), expeditionaryForcePlayer, LGTUnit, Unit.UNIT_SUSPENDED));
		}
		int expeditionaryForceFlightTurns = Integer.parseInt(freeMarsModel.getRealm().getProperty("expeditionary_force_flight_turns"));
		expeditionaryForcePlayer.setEarthToMarsFlightTurns(expeditionaryForceFlightTurns);
		return expeditionaryForcePlayer;
	}
}

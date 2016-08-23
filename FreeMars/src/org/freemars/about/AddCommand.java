package org.freemars.about;

import org.freerealm.Realm;
import org.freerealm.command.AddUnitCommand;
import org.freerealm.map.Coordinate;
import org.freerealm.player.Player;
import org.freerealm.tile.Tile;
import org.freerealm.unit.Unit;
import org.freerealm.unit.UnitType;

/**
 * 
 * @author Deniz ARIKAN
 */
public class AddCommand extends ConsoleCommand {

	@Override
	public String[] getCommands() {
		return new String[] { "add" };
	}

	public void execute() {
		StringBuilder output = new StringBuilder();
		if (getCommandArguments().length == 1) {
			output.append(getHelp());
		} else {
			String type = getCommandArguments()[1];
			if (type.equals("unit")) {

				if (getCommandArguments().length == 5) {
					String coordinateString = getCommandArguments()[2];
					if (coordinateString.startsWith("(")) {
						coordinateString = coordinateString.substring(1);
					}
					if (coordinateString.endsWith(")")) {
						coordinateString = coordinateString.substring(0, coordinateString.length() - 1);
					}
					int commaPosition = coordinateString.indexOf(",");
					String abscissaValue = coordinateString.substring(0, commaPosition);
					String ordinateValue = coordinateString.substring(commaPosition + 1);
					int abscissa = Integer.parseInt(abscissaValue);
					int ordinate = Integer.parseInt(ordinateValue);
					Coordinate coordinate = new Coordinate(abscissa, ordinate);
					String playerIdString = getCommandArguments()[3];
					int playerId = Integer.parseInt(playerIdString);
					String unitTypeIdString = getCommandArguments()[4];
					int unitTypeId = Integer.parseInt(unitTypeIdString);
					Realm realm = getFreeMarsController().getFreeMarsModel().getRealm();
					Tile tile = realm.getTile(coordinate);
					Player player = realm.getPlayerManager().getPlayer(playerId);
					UnitType unitType = realm.getUnitTypeManager().getUnitType(unitTypeId);
					Unit unit = new Unit(realm, unitType, coordinate, player);
					if (tile != null) {
						AddUnitCommand addUnitCommand = new AddUnitCommand(realm, player, unit);
						getFreeMarsController().execute(addUnitCommand);
						if (addUnitCommand.getState() == SUCCEEDED) {
							output.append("\"");
							output.append(unit.getName());
							output.append("\" added to ");
							output.append(player.getName());
							output.append(" at coordinate ");
							output.append(coordinate);
							output.append(System.getProperty("line.separator"));
						}
					}
				} else {
					output.append(getHelp());
				}

			}
		}
		setText(output.toString());
		setState(SUCCEEDED);
	}

	@Override
	public String getHelp() {
		StringBuilder output = new StringBuilder();
		output.append("usage: add unit {coordinate} {player_id} {unit_id}");
		output.append(System.getProperty("line.separator"));
		return output.toString();
	}
}

package org.freemars.about;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.freerealm.Realm;
import org.freerealm.command.RemoveUnitCommand;
import org.freerealm.map.Coordinate;
import org.freerealm.tile.Tile;
import org.freerealm.unit.Unit;

/**
 * 
 * @author Deniz ARIKAN
 */
public class RemoveCommand extends ConsoleCommand {

	@Override
	public String[] getCommands() {
		return new String[] { "remove", "rm" };
	}

	public void execute() {
		StringBuilder output = new StringBuilder();
		if (getCommandArguments().length == 1) {
			output.append("Remove what?");
			output.append(System.getProperty("line.separator"));
		} else {
			String type = getCommandArguments()[1];
			if (type.equals("units")) {
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
				Realm realm = getFreeMarsController().getFreeMarsModel().getRealm();
				Tile tile = realm.getTile(coordinate);
				List<Unit> unitsToRemove = new ArrayList<Unit>();
				if (tile != null) {
					if (tile.getNumberOfUnits() > 0) {
						Iterator<Unit> units = tile.getUnitsIterator();
						while (units.hasNext()) {
							Unit unit = units.next();
							unitsToRemove.add(unit);
						}
						for (Unit unit : unitsToRemove) {
							StringBuilder removeUnitOutput = new StringBuilder();
							removeUnitOutput.append("Removed ");
							removeUnitOutput.append(unit.getPlayer().getNation().getAdjective());
							removeUnitOutput.append(" ");
							removeUnitOutput.append(unit.getName());
							removeUnitOutput.append(System.getProperty("line.separator"));
							RemoveUnitCommand removeUnitCommand = new RemoveUnitCommand(realm, unit.getPlayer(), unit);
							getFreeMarsController().execute(removeUnitCommand);
							if (removeUnitCommand.getState() == SUCCEEDED) {
								output.append(removeUnitOutput);
							}
						}
					} else {
						output.append("There are no units on tile");
						output.append(System.getProperty("line.separator"));
					}
				}
			}
		}
		setText(output.toString());
		setState(SUCCEEDED);
	}

}

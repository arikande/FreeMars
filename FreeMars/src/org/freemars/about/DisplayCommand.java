package org.freemars.about;

import java.util.Iterator;

import org.freemars.model.FreeMarsModel;
import org.freerealm.FreeRealmConstants;
import org.freerealm.map.Coordinate;
import org.freerealm.player.Player;
import org.freerealm.settlement.Settlement;
import org.freerealm.tile.Tile;
import org.freerealm.tile.improvement.TileImprovementType;
import org.freerealm.unit.Unit;

/**
 * 
 * @author Deniz ARIKAN
 */
public class DisplayCommand extends ConsoleCommand {

	@Override
	public String[] getCommands() {
		return new String[] { "display", "disp" };
	}

	public void execute() {
		StringBuilder output = new StringBuilder();
		if (getCommandArguments().length == 1) {
			output.append(getHelp());
		} else {
			String type = getCommandArguments()[1];
			if (type.equals("version")) {
				displayVersion(output);
			} else if (type.equalsIgnoreCase("coordinate") || type.equalsIgnoreCase("coor") || type.equalsIgnoreCase("c")) {
				displayCoordinate(output);
			} else if (type.equalsIgnoreCase("players")) {
				displayPlayers(output);
			} else {
				output.append("Invalid parameter");
			}
		}
		setText(output.toString());
		setState(SUCCEEDED);
	}

	@Override
	public String getHelp() {
		StringBuilder output = new StringBuilder();
		output.append("usage: display version");
		output.append(System.getProperty("line.separator"));
		output.append("   or: display coordinate (x,y)");
		output.append(System.getProperty("line.separator"));
		output.append("   or: display players");
		output.append(System.getProperty("line.separator"));
		return output.toString();
	}

	private void displayCoordinate(StringBuilder output) {
		if (getCommandArguments().length == 2) {
			output.append("usage : display coordinate (x,y)");
			output.append(System.getProperty("line.separator"));
			return;
		}
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
		Tile tile = getFreeMarsController().getFreeMarsModel().getTile(coordinate);
		if (tile != null) {
			output.append("Displaying coordinate ");
			output.append(coordinate);
			output.append("...");
			output.append(System.getProperty("line.separator"));
			String tileType = tile.getType().getName();
			output.append("Type : ");
			output.append(tileType);
			output.append(System.getProperty("line.separator"));
			Player tileOwnerPlayer = getFreeMarsController().getFreeMarsModel().getRealm().getTileOwner(coordinate);
			if (tileOwnerPlayer != null) {
				output.append("Owner : ");
				output.append(tileOwnerPlayer.getName());
				output.append(System.getProperty("line.separator"));
			}
			if (tile.getVegetation() != null) {
				output.append("Vegetation : ");
				output.append(tile.getVegetation().getType().getName());
				output.append(System.getProperty("line.separator"));
			}
			if (tile.getBonusResource() != null) {
				output.append("Bonus resource : ");
				output.append(tile.getBonusResource().getName());
				output.append(System.getProperty("line.separator"));
			}
			if (tile.getSettlement() != null) {
				Settlement settlement = tile.getSettlement();
				output.append("Settlement : ");
				output.append(settlement.getName());
				output.append(System.getProperty("line.separator"));
			}
			if (tile.getNumberOfUnits() > 0) {
				output.append("Units : ");
				output.append(System.getProperty("line.separator"));
				Iterator<Unit> iterator = tile.getUnitsIterator();
				while (iterator.hasNext()) {
					Unit unit = iterator.next();
					output.append(unit.getName());
					if (unit.getStatus() == Unit.UNIT_SUSPENDED) {
						output.append(" - Suspended");
					}
					output.append(System.getProperty("line.separator"));
				}
			}
			if (tile.getImprovementCount() > 0) {
				output.append("Improvements : ");
				output.append(System.getProperty("line.separator"));
				Iterator<TileImprovementType> iterator = tile.getImprovementsIterator();
				while (iterator.hasNext()) {
					TileImprovementType tileImprovementType = iterator.next();
					output.append(tileImprovementType.getName());
					output.append(System.getProperty("line.separator"));
				}
			}
		} else {
			output.append("Invalid coordinate");
		}

	}

	private void displayPlayers(StringBuilder output) {
		String formatPattern = "%3.3s   %-15.15s   %-8.8s   %-8.8s %8.8s   %6.6s";
		String title = String.format(formatPattern, "Id", "Name", "Nation", "Status", "Colonies", "Units");
		output.append(title);
		output.append(System.getProperty("line.separator"));
		output.append(" ---------------------------------------------------------------");
		output.append(System.getProperty("line.separator"));
		Iterator<Player> playersIterator = getFreeMarsController().getFreeMarsModel().getPlayersIterator();
		while (playersIterator.hasNext()) {
			Player player = playersIterator.next();
			String playerStatus = (player.getStatus() == 1 ? "Active" : "Passive");
			String line = String.format(formatPattern, player.getId(), player.getName(), player.getNation(), playerStatus, player.getSettlementCount(), player.getUnitCount());
			output.append(line);
			output.append(System.getProperty("line.separator"));
		}
	}

	private void displayVersion(StringBuilder output) {
		output.append("Free Mars version : ");
		output.append(FreeMarsModel.getVersion());
		output.append(System.getProperty("line.separator"));
		output.append("Free Realm version : ");
		output.append(FreeRealmConstants.getVersion());
		output.append(System.getProperty("line.separator"));
	}

}

package org.freemars.diplomacy;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import org.freemars.model.FreeMarsModel;
import org.freemars.player.FreeMarsPlayer;
import org.freerealm.player.Player;

/**
 *
 * @author Deniz ARIKAN
 */
public class DiplomacyTableModel extends AbstractTableModel {

    private final Player player;
    private final List<FreeMarsPlayer> players;
    private final String[] columnNames = {"Player", "Nation", "Status", "Attitude"};

    public DiplomacyTableModel(FreeMarsModel freeMarsModel, Player player) {
        this.player = player;
        players = new ArrayList<FreeMarsPlayer>();
        Iterator<Player> iterator = freeMarsModel.getPlayersIterator();
        while (iterator.hasNext()) {
            FreeMarsPlayer other = (FreeMarsPlayer) iterator.next();
            if (!player.equals(other) && other.getStatus() == Player.STATUS_ACTIVE) {
                players.add(other);
            }
        }
    }

    public FreeMarsPlayer getPlayer(int row) {
        return players.get(row);
    }

    @Override
    public String getColumnName(int col) {
        return columnNames[col];
    }

    public int getRowCount() {
        return players.size();
    }

    public int getColumnCount() {
        return columnNames.length;
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        if (columnIndex == 0) {
            return getPlayer(rowIndex).getName();
        } else if (columnIndex == 1) {
            return getPlayer(rowIndex).getNation();
        } else if (columnIndex == 2) {
            return player.getDiplomacy().getPlayerRelation(getPlayer(rowIndex)).getStatusDefinition();
        } else {
            int attitude = getPlayer(rowIndex).getDiplomacy().getPlayerRelation(player).getAttitude();
            return DiplomacyUtility.getAttitudeString(attitude);
        }
    }

}

package org.freemars.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.freemars.ai.ExpeditionaryForcePlayer;
import org.freemars.earth.Earth;
import org.freemars.model.objective.Objective;
import org.freemars.player.FreeMarsPlayer;
import org.freemars.ui.GameFrame;
import org.freemars.ui.player.preferences.FreeMarsPreferences;
import org.freerealm.Realm;
import org.freerealm.map.Coordinate;
import org.freerealm.player.Player;
import org.freerealm.tile.Tile;
import org.freerealm.tile.TileType;

/**
 *
 * @author Deniz ARIKAN
 */
public class FreeMarsModel {

    public static final String TAG = "free_mars_game_data";

    public static final int PLAYER_DECLARED_INDEPENDENCE_UPDATE = 1001;
    public static final int SPACESHIPS_SEIZED_UPDATE = 1002;
    public static final int UNITS_SEIZED_UPDATE = 1003;
    public static final int EXPEDITIONARY_FORCE_LANDED_UPDATE = 1004;
    public static final int EXPEDITIONARY_FORCE_CHANGED_UPDATE = 1005;
    public static final int EARTH_TAX_RATE_CHANGED_UPDATE = 1006;
    public static final int RANDOM_EVENT_UPDATE = 1007;

    public static final int GAME_MODE = 0;
    public static final int INSPECTION_MODE = 1;
    public static final int SIMULATION_MODE = 2;

    private static final String VERSION = "0.8.7.2";

    private int mode = GAME_MODE;
    private final Realm realm;
    private final FreeMarsViewModel freeMarsViewModel;
    private Earth earth;
    private GameFrame gameFrame;
    private Player humanPlayer;
    private boolean humanPlayerDefeated;
    private List<Objective> objectives;
    private FreeMarsPreferences freeMarsPreferences;

    public FreeMarsModel() {
        realm = new Realm();
        objectives = new ArrayList<Objective>();
        freeMarsViewModel = new FreeMarsViewModel();
        freeMarsPreferences = new FreeMarsPreferences();
    }

    public static final String getVersion() {
        return VERSION;
    }

    public Realm getRealm() {
        return realm;
    }

    public TileType getTileType(String name) {
        return realm.getTileTypeManager().getTileType(name);
    }

    public TileType getTileType(int id) {
        return realm.getTileTypeManager().getTileType(id);
    }

    public Iterator<Objective> getObjectivesIterator() {
        return objectives.iterator();
    }

    public void addObjective(Objective objective) {
        objectives.add(objective);
    }

    public void setObjectives(List<Objective> objectives) {
        this.objectives = objectives;
    }

    public boolean hasCompletedObjectives(Player player) {
        if (objectives.isEmpty()) {
            return false;
        }
        Iterator<Objective> objectiveIterator = objectives.iterator();
        while (objectiveIterator.hasNext()) {
            Objective objective = objectiveIterator.next();
            if (!objective.isReached(getRealm(), player)) {
                return false;
            }
        }
        return true;
    }

    public FreeMarsPreferences getFreeMarsPreferences() {
        return freeMarsPreferences;
    }

    public void setFreeMarsPreferences(FreeMarsPreferences freeMarsPreferences) {
        this.freeMarsPreferences = freeMarsPreferences;
    }

    public int getNumberOfTurns() {
        return realm.getNumberOfTurns();
    }

    public int getMaximumNumberOfTurns() {
        return realm.getMaximumNumberOfTurns();
    }

    public boolean isHumanPlayerActive() {
        if (getHumanPlayer() == null) {
            return false;
        }
        if (getActivePlayer() == null) {
            return false;
        }
        return getHumanPlayer().equals(getActivePlayer());
    }

    public FreeMarsPlayer getActivePlayer() {
        if (realm.getPlayerManager() != null) {
            return (FreeMarsPlayer) realm.getPlayerManager().getActivePlayer();
        } else {
            return null;
        }
    }

    public Iterator<Player> getPlayersIterator() {
        return realm.getPlayerManager().getPlayersIterator();
    }

    public Tile getTile(Coordinate coordinate) {
        return realm.getTile(coordinate);
    }

    public Earth getEarth() {
        return earth;
    }

    public void setEarth(Earth earth) {
        this.earth = earth;
    }

    public GameFrame getGameFrame() {
        return gameFrame;
    }

    public void setGameFrame(GameFrame gameFrame) {
        this.gameFrame = gameFrame;
    }

    public boolean isMapPanelDisplayingCoordinate(Coordinate coordinate) {
        return getGameFrame().getMapPanel().isDisplayingCoordinate(coordinate);
    }

    public void clearObjectives() {
        objectives.clear();
    }

    public ExpeditionaryForcePlayer getRelatedExpeditionaryForcePlayer(Player player) {
        Iterator<Player> iterator = getPlayersIterator();
        while (iterator.hasNext()) {
            Player checkPlayer = iterator.next();
            if (checkPlayer instanceof ExpeditionaryForcePlayer) {
                ExpeditionaryForcePlayer expeditionaryForcePlayer = (ExpeditionaryForcePlayer) checkPlayer;
                if (expeditionaryForcePlayer.getTargetPlayerId() == player.getId()) {
                    return expeditionaryForcePlayer;
                }
            }
        }
        return null;
    }

    public Player getHumanPlayer() {
        return humanPlayer;
    }

    public void setHumanPlayer(Player player) {
        this.humanPlayer = player;
    }

    public boolean isHumanPlayerDefeated() {
        return humanPlayerDefeated;
    }

    public void setHumanPlayerDefeated(boolean humanPlayerDefeated) {
        this.humanPlayerDefeated = humanPlayerDefeated;
    }

    public FreeMarsViewModel getFreeMarsViewModel() {
        return freeMarsViewModel;
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }
}

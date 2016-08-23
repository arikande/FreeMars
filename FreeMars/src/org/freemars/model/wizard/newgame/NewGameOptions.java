package org.freemars.model.wizard.newgame;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;
import org.freemars.controller.FreeMarsController;
import org.freemars.model.objective.Objective;
import org.freerealm.nation.Nation;
import org.freerealm.tile.TileType;

/**
 *
 * @author Deniz ARIKAN
 */
public class NewGameOptions {

    public static final int CUSTOMIZED_MAP = 0;
    public static final int PREMADE_MAP = 1;
    private FreeMarsController freeMarsController;
    private int returnCode;
    private int mapType = PREMADE_MAP;
    private int mapWidth;
    private int mapHeight;
    private final TreeMap<TileType, Integer> tileTypes;
    private final List<Nation> nations;
    private final TreeMap<Nation, Color> nationPrimaryColors;
    private final TreeMap<Nation, Color> nationSecondaryColors;
    private String premadeMapPath;
    private Nation humanPlayerNation;
    private final List<Objective> objectives;

    public NewGameOptions() {
        tileTypes = new TreeMap<TileType, Integer>();
        nations = new ArrayList<Nation>();
        nationPrimaryColors = new TreeMap<Nation, Color>();
        nationSecondaryColors = new TreeMap<Nation, Color>();
        objectives = new ArrayList<Objective>();
    }

    public FreeMarsController getFreeMarsController() {
        return freeMarsController;
    }

    public void setFreeMarsModel(FreeMarsController freeMarsController) {
        this.freeMarsController = freeMarsController;
    }

    public int getMapWidth() {
        return mapWidth;
    }

    public void setMapWidth(int mapWidth) {
        this.mapWidth = mapWidth;
    }

    public int getMapHeight() {
        return mapHeight;
    }

    public void setMapHeight(int mapHeight) {
        this.mapHeight = mapHeight;
    }

    public List<Nation> getNations() {
        return nations;
    }

    public Iterator<Nation> getNationsIterator() {
        return nations.iterator();
    }

    public void addNation(Nation nation) {
        nations.add(nation);
    }

    public void clearNations() {
        nations.clear();
    }

    public Color getNationPrimaryColor(Nation nation) {
        return nationPrimaryColors.get(nation);
    }

    public void setNationPrimaryColor(Nation nation, Color color) {
        nationPrimaryColors.put(nation, color);
    }

    public Color getNationSecondaryColor(Nation nation) {
        return nationSecondaryColors.get(nation);
    }

    public void setNationSecondaryColor(Nation nation, Color color) {
        nationSecondaryColors.put(nation, color);
    }

    public TreeMap<TileType, Integer> getTileTypes() {
        return tileTypes;
    }

    public Nation getHumanPlayerNation() {
        return humanPlayerNation;
    }

    public void setHumanPlayerNation(Nation humanPlayerNation) {
        this.humanPlayerNation = humanPlayerNation;
    }

    public int getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(int returnCode) {
        this.returnCode = returnCode;
    }

    public String getPremadeMapPath() {
        return premadeMapPath;
    }

    public void setPremadeMapPath(String premadeMapPath) {
        this.premadeMapPath = premadeMapPath;
    }

    public int getMapType() {
        return mapType;
    }

    public void setMapType(int mapType) {
        this.mapType = mapType;
    }

    public void addObjective(Objective objective) {
        objectives.add(objective);
    }

    public List<Objective> getObjectives() {
        return objectives;
    }

    public void clearObjectives() {
        objectives.clear();
    }
}

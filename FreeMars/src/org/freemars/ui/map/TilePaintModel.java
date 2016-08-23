package org.freemars.ui.map;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.freerealm.map.Coordinate;
import org.freerealm.map.Direction;

/**
 *
 * @author Deniz ARIKAN
 */
public class TilePaintModel {

    private Coordinate tileCoordinate;
    private final HashMap<Direction, Image> terrainImages;
    private Image vegetationImage;
    private Image bonusResourceImage;
    private final List<Image> terrainImprovementImages;
    private Image unitImage;
    private int unitCount;
    private boolean paintingActiveUnitIndicator;
    private byte unitRectangleWidth;
    private String unitRectangleContent;
    private Font unitRectangleContentFont;
    private Color unitRectangleForegroundColor;
    private Color unitRectangleBackgroundColor;
    private Image unitPlayerNationFlagImage;
    private boolean paintingUnitPath;
    private Color unitPathColor;
    private Image colonyImage;
    private String colonyName;
    private Color colonyNamePrimaryColor;
    private Color colonyNameSecondaryColor;
    private String tileTypeName;
    private Color tileTypeNameColor;
    private boolean displayingGrid;
    private boolean displayingCoordinate;
    private boolean displayingTileType;
    private Image collectableImage;
    private Color territoryPrimaryBorderColor;
    private Color territorySecondaryBorderColor;
    private final List<Direction> territoryBorderDirections;

    public TilePaintModel() {
        terrainImages = new HashMap<Direction, Image>();
        terrainImprovementImages = new ArrayList<Image>();
        territoryBorderDirections = new ArrayList<Direction>();
    }

    public Image getPaintImage(Direction direction) {
        return terrainImages.get(direction);
    }

    public void clearTerrainImages() {
        terrainImages.clear();
    }

    public void addTerrainImage(Direction direction, Image terrainImage) {
        terrainImages.put(direction, terrainImage);
    }

    public Image getVegetationImage() {
        return vegetationImage;
    }

    public void setVegetationImage(Image vegetationImage) {
        this.vegetationImage = vegetationImage;
    }

    public Image getBonusResourceImage() {
        return bonusResourceImage;
    }

    public void setResourceBonusImage(Image bonusResourceImage) {
        this.bonusResourceImage = bonusResourceImage;
    }

    public void clearTerrainImprovementImages() {
        terrainImprovementImages.clear();
    }

    public void addTerrainImprovementImage(Image image) {
        terrainImprovementImages.add(image);
    }

    public List<Image> getTerrainImprovementImages() {
        return terrainImprovementImages;
    }

    public boolean isDisplayingGrid() {
        return displayingGrid;
    }

    public void setDisplayingGrid(boolean displayingGrid) {
        this.displayingGrid = displayingGrid;
    }

    public boolean isDisplayingCoordinate() {
        return displayingCoordinate;
    }

    public void setDisplayingCoordinate(boolean displayingCoordinate) {
        this.displayingCoordinate = displayingCoordinate;
    }

    public boolean isDisplayingTileType() {
        return displayingTileType;
    }

    public void setDisplayingTileType(boolean displayingTileType) {
        this.displayingTileType = displayingTileType;
    }

    public Coordinate getTileCoordinate() {
        return tileCoordinate;
    }

    public void setTileCoordinate(Coordinate tileCoordinate) {
        this.tileCoordinate = tileCoordinate;
    }

    public Image getColonyImage() {
        return colonyImage;
    }

    public void setColonyImage(Image colonyImage) {
        this.colonyImage = colonyImage;
    }

    public String getColonyName() {
        return colonyName;
    }

    public void setColonyName(String colonyName) {
        this.colonyName = colonyName;
    }

    public Color getColonyNamePrimaryColor() {
        return colonyNamePrimaryColor;
    }

    public void setColonyNamePrimaryColor(Color colonyNamePrimaryColor) {
        this.colonyNamePrimaryColor = colonyNamePrimaryColor;
    }

    public Color getColonyyNameSecondaryColor() {
        return colonyNameSecondaryColor;
    }

    public void setColonyNameSecondaryColor(Color colonyNameSecondaryColor) {
        this.colonyNameSecondaryColor = colonyNameSecondaryColor;
    }

    public String getTileTypeName() {
        return tileTypeName;
    }

    public void setTileTypeName(String tileTypeName) {
        this.tileTypeName = tileTypeName;
    }

    public Color getTileTypeNameColor() {
        return tileTypeNameColor;
    }

    public void setTileTypeNameColor(Color tileTypeNameColor) {
        this.tileTypeNameColor = tileTypeNameColor;
    }

    public Image getUnitImage() {
        return unitImage;
    }

    public void setUnitImage(Image unitImage) {
        this.unitImage = unitImage;
    }

    public boolean isPaintingActiveUnitIndicator() {
        return paintingActiveUnitIndicator;
    }

    public void setPaintingActiveUnitIndicator(boolean paintingActiveUnitIndicator) {
        this.paintingActiveUnitIndicator = paintingActiveUnitIndicator;
    }

    public int getUnitCount() {
        return unitCount;
    }

    public void setUnitCount(int unitCount) {
        this.unitCount = unitCount;
    }

    public byte getUnitRectangleWidth() {
        return unitRectangleWidth;
    }

    public void setUnitRectangleWidth(byte unitRectangleWidth) {
        this.unitRectangleWidth = unitRectangleWidth;
    }

    public String getUnitRectangleContent() {
        return unitRectangleContent;
    }

    public void setUnitRectangleContent(String unitRectangleContent) {
        this.unitRectangleContent = unitRectangleContent;
    }

    public Color getUnitRectangleForegroundColor() {
        return unitRectangleForegroundColor;
    }

    public void setUnitRectangleForegroundColor(Color unitRectangleForegroundColor) {
        this.unitRectangleForegroundColor = unitRectangleForegroundColor;
    }

    public Color getUnitRectangleBackgroundColor() {
        return unitRectangleBackgroundColor;
    }

    public void setUnitRectangleBackgroundColor(Color unitRectangleBackgroundColor) {
        this.unitRectangleBackgroundColor = unitRectangleBackgroundColor;
    }

    public Font getUnitRectangleContentFont() {
        return unitRectangleContentFont;
    }

    public void setUnitRectangleContentFont(Font unitRectangleContentFont) {
        this.unitRectangleContentFont = unitRectangleContentFont;
    }

    public boolean isPaintingUnitPath() {
        return paintingUnitPath;
    }

    public void setPaintingUnitPath(boolean paintingUnitPath) {
        this.paintingUnitPath = paintingUnitPath;
    }

    public Color getUnitPathColor() {
        return unitPathColor;
    }

    public void setUnitPathColor(Color unitPathColor) {
        this.unitPathColor = unitPathColor;
    }

    public Image getCollectableImage() {
        return collectableImage;
    }

    public void setCollectableImage(Image collectableImage) {
        this.collectableImage = collectableImage;
    }

    public Image getUnitPlayerNationFlagImage() {
        return unitPlayerNationFlagImage;
    }

    public void setUnitPlayerNationFlagImage(Image unitPlayerNationFlagImage) {
        this.unitPlayerNationFlagImage = unitPlayerNationFlagImage;
    }

    public Color getTerritoryPrimaryBorderColor() {
        return territoryPrimaryBorderColor;
    }

    public void setTerritoryPrimaryBorderColor(Color territoryPrimaryBorderColor) {
        this.territoryPrimaryBorderColor = territoryPrimaryBorderColor;
    }

    public Color getTerritorySecondaryBorderColor() {
        return territorySecondaryBorderColor;
    }

    public void setTerritorySecondaryBorderColor(Color territorySecondaryBorderColor) {
        this.territorySecondaryBorderColor = territorySecondaryBorderColor;
    }

    public void clearTerritoryBorderDirections() {
        territoryBorderDirections.clear();
    }

    public void addTerritoryBorderDirection(Direction direction) {
        territoryBorderDirections.add(direction);
    }

    public List<Direction> getTerritoryBorderDirections() {
        return territoryBorderDirections;
    }

}

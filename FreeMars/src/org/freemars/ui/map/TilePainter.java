package org.freemars.ui.map;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import org.freemars.model.FreeMarsModel;
import org.freemars.ui.image.FreeMarsImageManager;
import org.freerealm.RealmConstants;
import org.freerealm.map.Direction;

/**
 *
 * @author Deniz ARIKAN
 */
public class TilePainter {

    public static void paintTileImages(Graphics2D g2d, RhombusGridPanel gridPanel, FreeMarsModel viewModel, TilePaintModel tilePaintModel, Point paintPoint) {
        Image centerImage = tilePaintModel.getPaintImage(RealmConstants.CENTER);
        if (centerImage != null) {
            centerImage = FreeMarsImageManager.createResizedCopy(centerImage, gridPanel.getGridWidth() + 1, gridPanel.getGridHeight() + 1, false, gridPanel);
        }
        g2d.drawImage(centerImage, (int) paintPoint.getX(), (int) paintPoint.getY(), gridPanel);
        for (Direction direction : RealmConstants.directions) {
            if (!direction.equals(RealmConstants.CENTER) && tilePaintModel.getPaintImage(direction) != null) {
                Image image = tilePaintModel.getPaintImage(direction);
                image = FreeMarsImageManager.createResizedCopy(image, gridPanel.getGridWidth() + 1, gridPanel.getGridHeight() + 1, false, gridPanel);
                g2d.drawImage(image, (int) paintPoint.getX(), (int) paintPoint.getY(), gridPanel);
            }
        }
    }

    public static void paintTileImprovements(Graphics2D g2d, RhombusGridPanel gridPanel, FreeMarsModel viewModel, TilePaintModel tilePaintModel, Point paintPoint) {
        for (Image tileImprovementImage : tilePaintModel.getTerrainImprovementImages()) {
            tileImprovementImage = FreeMarsImageManager.createResizedCopy(tileImprovementImage, gridPanel.getGridWidth() + 1, gridPanel.getGridHeight() + 1, false, gridPanel);
            g2d.drawImage(tileImprovementImage, (int) paintPoint.getX(), (int) paintPoint.getY(), gridPanel);
        }
    }

    public static void paintTileBonusResource(Graphics2D g2d, RhombusGridPanel gridPanel, FreeMarsModel viewModel, TilePaintModel tilePaintModel, Point paintPoint) {
        if (tilePaintModel.getBonusResourceImage() != null) {
            Image resourceBonusImage = tilePaintModel.getBonusResourceImage();
            int imageWidth = (resourceBonusImage.getWidth(gridPanel) * (viewModel.getFreeMarsViewModel().getMapPanelZoomLevel() + 1)) / 4;
            int imageHeight = (resourceBonusImage.getHeight(gridPanel) * (viewModel.getFreeMarsViewModel().getMapPanelZoomLevel() + 1)) / 4;
            int imageX = (int) paintPoint.getX() + (gridPanel.getGridWidth() / 2) - (imageWidth / 2);
            int imageY = (int) paintPoint.getY() + gridPanel.getGridHeight() / 2 - imageHeight / 2;
            resourceBonusImage = FreeMarsImageManager.createResizedCopy(resourceBonusImage, imageWidth, imageHeight, false, gridPanel);
            g2d.drawImage(resourceBonusImage, imageX, imageY, gridPanel);
        }
    }

    public static void paintTileVegetation(Graphics2D g2d, RhombusGridPanel gridPanel, FreeMarsModel viewModel, TilePaintModel tilePaintModel, Point paintPoint) {
        if (tilePaintModel.getVegetationImage() != null) {
            Image vegetationImage = tilePaintModel.getVegetationImage();
            vegetationImage = FreeMarsImageManager.createResizedCopy(vegetationImage, gridPanel.getGridWidth(), gridPanel.getGridHeight(), false, gridPanel);
            g2d.drawImage(vegetationImage, (int) paintPoint.getX(), (int) paintPoint.getY(), gridPanel);
        }
    }

    public static void paintTileInfo(Graphics2D g2d, RhombusGridPanel gridPanel, FreeMarsModel freeMarsModel, TilePaintModel tilePaintModel, Point paintPoint) {
        if (tilePaintModel.isDisplayingGrid()) {
            g2d.setColor(Color.WHITE);
            gridPanel.paintRhombus(g2d, paintPoint);
        }
        List<String> textToPaint = new ArrayList<String>();
        if (tilePaintModel.isDisplayingTileType()) {
            textToPaint.add(tilePaintModel.getTileTypeName());
        }
        if (tilePaintModel.isDisplayingCoordinate()) {
            textToPaint.add(tilePaintModel.getTileCoordinate().toString());
        }
        int i = 0;
        Point textPaintPoint = new Point(paintPoint);
        if (textToPaint.size() == 2) {
            textPaintPoint.y = textPaintPoint.y - 10;
        }
        int fontSize = 9 + freeMarsModel.getFreeMarsViewModel().getMapPanelZoomLevel();
        for (String string : textToPaint) {
            textPaintPoint.y = textPaintPoint.y + (i * 15);
            gridPanel.paintText(g2d, textPaintPoint, new Font("Arial", 0, fontSize), Color.WHITE, string);
            i++;
        }
    }

    public static void paintTileTerritoryBorder(Graphics2D g2d, RhombusGridPanel gridPanel, TilePaintModel tilePaintModel, Point paintPoint) {
        int lineCount = 6;
        int curveSpace = gridPanel.getGridWidth() / 6;
        int distanceFromGrid = gridPanel.getGridWidth() / 18;
        int x1 = (int) paintPoint.getX();
        int x2 = x1 + gridPanel.getGridWidth() / 2;
        int x3 = x1 + gridPanel.getGridWidth();
        int y1 = (int) paintPoint.getY();
        int y2 = y1 + gridPanel.getGridHeight() / 2;
        int y3 = y1 + gridPanel.getGridHeight();
        if (tilePaintModel.getTerritoryBorderDirections().contains(RealmConstants.NORTHWEST)) {
            for (int i = 0; i < lineCount; i++) {
                g2d.setColor(getTerritoryBorderLineColor(tilePaintModel, i));
                int lineX1 = x1 + distanceFromGrid;
                int lineY1 = y2 + i + (distanceFromGrid / 2);
                int lineX2 = x2 + distanceFromGrid;
                int lineY2 = y1 + i + (distanceFromGrid / 2);
                if (tilePaintModel.getTerritoryBorderDirections().contains(RealmConstants.NORTHEAST)) {
                    lineX2 = lineX2 - curveSpace;
                    lineY2 = lineY2 + (curveSpace / 2);
                }
                if (tilePaintModel.getTerritoryBorderDirections().contains(RealmConstants.SOUTHWEST)) {
                    lineX1 = lineX1 + curveSpace;
                    lineY1 = lineY1 - (curveSpace / 2);
                }
                g2d.drawLine(lineX1, lineY1, lineX2, lineY2);
            }
        }
        if (tilePaintModel.getTerritoryBorderDirections().contains(RealmConstants.NORTHEAST)) {
            for (int i = 0; i < lineCount; i++) {
                g2d.setColor(getTerritoryBorderLineColor(tilePaintModel, i));
                int lineX1 = x2 - distanceFromGrid;
                int lineY1 = y1 + i + (distanceFromGrid / 2);
                int lineX2 = x3 - distanceFromGrid;
                int lineY2 = y2 + i + (distanceFromGrid / 2);
                if (tilePaintModel.getTerritoryBorderDirections().contains(RealmConstants.NORTHWEST)) {
                    lineX1 = lineX1 + curveSpace;
                    lineY1 = lineY1 + (curveSpace / 2);
                }
                if (tilePaintModel.getTerritoryBorderDirections().contains(RealmConstants.SOUTHEAST)) {
                    lineX2 = lineX2 - curveSpace;
                    lineY2 = lineY2 - (curveSpace / 2);
                }
                g2d.drawLine(lineX1, lineY1, lineX2, lineY2);
            }
        }
        if (tilePaintModel.getTerritoryBorderDirections().contains(RealmConstants.SOUTHWEST)) {
            for (int i = 0; i < lineCount; i++) {
                g2d.setColor(getTerritoryBorderLineColor(tilePaintModel, i));
                int lineX1 = x1 + distanceFromGrid;
                int lineY1 = y2 - i - (distanceFromGrid / 2);
                int lineX2 = x2 + distanceFromGrid;
                int lineY2 = y3 - i - (distanceFromGrid / 2);
                if (tilePaintModel.getTerritoryBorderDirections().contains(RealmConstants.NORTHWEST)) {
                    lineX1 = lineX1 + curveSpace;
                    lineY1 = lineY1 + (curveSpace / 2);
                }
                if (tilePaintModel.getTerritoryBorderDirections().contains(RealmConstants.SOUTHEAST)) {
                    lineX2 = lineX2 - curveSpace;
                    lineY2 = lineY2 - (curveSpace / 2);
                }
                g2d.drawLine(lineX1, lineY1, lineX2, lineY2);
            }
        }
        if (tilePaintModel.getTerritoryBorderDirections().contains(RealmConstants.SOUTHEAST)) {
            for (int i = 0; i < lineCount; i++) {
                g2d.setColor(getTerritoryBorderLineColor(tilePaintModel, i));
                int lineX1 = x3 - distanceFromGrid;
                int lineY1 = y2 - i - (distanceFromGrid / 2);
                int lineX2 = x2 - distanceFromGrid;
                int lineY2 = y3 - i - (distanceFromGrid / 2);
                if (tilePaintModel.getTerritoryBorderDirections().contains(RealmConstants.SOUTHWEST)) {
                    lineX2 = lineX2 + curveSpace;
                    lineY2 = lineY2 - (curveSpace / 2);
                }
                if (tilePaintModel.getTerritoryBorderDirections().contains(RealmConstants.NORTHEAST)) {
                    lineX1 = lineX1 - curveSpace;
                    lineY1 = lineY1 + (curveSpace / 2);
                }
                g2d.drawLine(lineX1, lineY1, lineX2, lineY2);
            }
        }
        if (tilePaintModel.getTerritoryBorderDirections().contains(RealmConstants.NORTHWEST) && tilePaintModel.getTerritoryBorderDirections().contains(RealmConstants.NORTHEAST)) {
            for (int i = 0; i < lineCount; i++) {
                g2d.setColor(getTerritoryBorderLineColor(tilePaintModel, i));
                g2d.drawArc(x2 - curveSpace, y1 + (curveSpace / 2) + i + 1, curveSpace * 2, curveSpace / 2, 45, 90);
            }
        }
        if (tilePaintModel.getTerritoryBorderDirections().contains(RealmConstants.SOUTHWEST) && tilePaintModel.getTerritoryBorderDirections().contains(RealmConstants.SOUTHEAST)) {
            for (int i = 0; i < lineCount; i++) {
                g2d.setColor(getTerritoryBorderLineColor(tilePaintModel, i));
                g2d.drawArc(x2 - curveSpace, y3 - curveSpace + i - distanceFromGrid / 2, curveSpace * 2, curveSpace / 2, 225, 90);
            }
        }
        if (tilePaintModel.getTerritoryBorderDirections().contains(RealmConstants.NORTHWEST) && tilePaintModel.getTerritoryBorderDirections().contains(RealmConstants.SOUTHWEST)) {
            for (int i = 0; i < lineCount; i++) {
                g2d.setColor(getTerritoryBorderLineColor(tilePaintModel, i));
                g2d.drawArc(x1 + curveSpace, y2 + i - distanceFromGrid / 2 - 6, curveSpace * 2, 6 + curveSpace / 2, 135, 90);
            }
        }
        if (tilePaintModel.getTerritoryBorderDirections().contains(RealmConstants.NORTHEAST) && tilePaintModel.getTerritoryBorderDirections().contains(RealmConstants.SOUTHEAST)) {
            for (int i = 0; i < lineCount; i++) {
                g2d.setColor(getTerritoryBorderLineColor(tilePaintModel, i));
                g2d.drawArc(x3 - 2 * curveSpace - 3 * distanceFromGrid, y2 + i - distanceFromGrid / 2 - 6, curveSpace * 2, 6 + curveSpace / 2, 315, 90);
            }
        }
        if (tilePaintModel.getTerritoryBorderDirections().contains(RealmConstants.SOUTH) && !tilePaintModel.getTerritoryBorderDirections().contains(RealmConstants.SOUTHWEST) && !tilePaintModel.getTerritoryBorderDirections().contains(RealmConstants.SOUTHEAST)) {
            for (int i = 0; i < lineCount; i++) {
                g2d.setColor(getTerritoryBorderLineColor(tilePaintModel, i));
                g2d.drawArc(x2 - (curveSpace - 1) / 2, y3 - i + distanceFromGrid - (curveSpace / 2) - 2, curveSpace - 2, curveSpace - 2, 45, 90);
            }
        }
        if (tilePaintModel.getTerritoryBorderDirections().contains(RealmConstants.NORTH) && !tilePaintModel.getTerritoryBorderDirections().contains(RealmConstants.NORTHWEST) && !tilePaintModel.getTerritoryBorderDirections().contains(RealmConstants.NORTHEAST)) {
            for (int i = 0; i < lineCount; i++) {
                g2d.setColor(getTerritoryBorderLineColor(tilePaintModel, i));
                g2d.drawArc(x2 - (curveSpace - 1) / 2, y1 + i + distanceFromGrid - curveSpace + 1, curveSpace - 2, curveSpace - 2, 225, 90);
            }
        }
        if (tilePaintModel.getTerritoryBorderDirections().contains(RealmConstants.WEST) && !tilePaintModel.getTerritoryBorderDirections().contains(RealmConstants.NORTHWEST) && !tilePaintModel.getTerritoryBorderDirections().contains(RealmConstants.SOUTHWEST)) {
            for (int i = 0; i < lineCount; i++) {
                g2d.setColor(getTerritoryBorderLineColor(tilePaintModel, i));
                g2d.drawArc(x1 - (curveSpace / 2), y2 - i + distanceFromGrid - curveSpace / 2, curveSpace - 2, 2 + (curveSpace / 2), 315, 90);
            }
        }
        if (tilePaintModel.getTerritoryBorderDirections().contains(RealmConstants.EAST) && !tilePaintModel.getTerritoryBorderDirections().contains(RealmConstants.NORTHEAST) && !tilePaintModel.getTerritoryBorderDirections().contains(RealmConstants.SOUTHEAST)) {
            for (int i = 0; i < lineCount; i++) {
                g2d.setColor(getTerritoryBorderLineColor(tilePaintModel, i));
                g2d.drawArc(x3 - (curveSpace / 2) + 1, y2 - i + distanceFromGrid - curveSpace / 2 + 1, curveSpace - 2, curveSpace / 2, 135, 90);
            }
        }
    }

    private static Color getTerritoryBorderLineColor(TilePaintModel tilePaintModel, int i) {
        Color territoryBorderColor;
        if (i < 3) {
            territoryBorderColor = tilePaintModel.getTerritoryPrimaryBorderColor();
        } else {
            territoryBorderColor = tilePaintModel.getTerritorySecondaryBorderColor();
        }
        return new Color(territoryBorderColor.getRed(), territoryBorderColor.getGreen(), territoryBorderColor.getBlue(), 180 - 25 * i);
    }

    public static void paintTileColony(Graphics2D g2d, RhombusGridPanel gridPanel, FreeMarsModel viewModel, TilePaintModel tilePaintModel, Point paintPoint) {
        if (tilePaintModel.getColonyImage() != null) {
            Image colonyImage = tilePaintModel.getColonyImage();
            int imageWidth = (colonyImage.getWidth(gridPanel) * (viewModel.getFreeMarsViewModel().getMapPanelZoomLevel() + 1)) / 4;
            int imageHeight = (colonyImage.getHeight(gridPanel) * (viewModel.getFreeMarsViewModel().getMapPanelZoomLevel() + 1)) / 4;
            int imageX = (int) paintPoint.getX() + (gridPanel.getGridWidth() / 2) - (imageWidth / 2);
            int imageY = (int) paintPoint.getY() + gridPanel.getGridHeight() - (gridPanel.getGridHeight() / 4) - imageHeight;
            colonyImage = FreeMarsImageManager.createResizedCopy(colonyImage, imageWidth, imageHeight, false, gridPanel);
            g2d.drawImage(colonyImage, imageX, imageY, gridPanel);
            g2d.setFont(new Font("Arial", 1, 15));
            g2d.setColor(tilePaintModel.getColonyyNameSecondaryColor());
            g2d.drawString(tilePaintModel.getColonyName(), imageX + 1, imageY + gridPanel.getGridHeight() + 1);
            g2d.setColor(tilePaintModel.getColonyNamePrimaryColor());
            g2d.drawString(tilePaintModel.getColonyName(), imageX, imageY + gridPanel.getGridHeight());
        }
    }

    public static void paintTileCollectable(Graphics2D g2d, RhombusGridPanel gridPanel, FreeMarsModel viewModel, TilePaintModel tilePaintModel, Point paintPoint) {
        if (tilePaintModel.getCollectableImage() != null) {
            Image collectableImage = tilePaintModel.getCollectableImage();
            collectableImage = FreeMarsImageManager.createResizedCopy(collectableImage, gridPanel.getGridWidth() + 1, gridPanel.getGridHeight() + 1, false, gridPanel);
            g2d.drawImage(collectableImage, (int) paintPoint.getX(), (int) paintPoint.getY(), gridPanel);
        }
    }

    public static void paintTileUnit(Graphics2D g2d, RhombusGridPanel gridPanel, FreeMarsModel freeMarsModel, TilePaintModel tilePaintModel, Point paintPoint) {
        if (tilePaintModel.getUnitImage() != null) {
            if (tilePaintModel.isPaintingActiveUnitIndicator()) {
                Image activeUnitIndicator = FreeMarsImageManager.getImage("ACTIVE_UNIT_INDICATOR");
                activeUnitIndicator = FreeMarsImageManager.createResizedCopy(activeUnitIndicator, gridPanel.getGridWidth(), gridPanel.getGridHeight(), false, gridPanel);
                g2d.drawImage(activeUnitIndicator, (int) paintPoint.getX(), (int) paintPoint.getY(), gridPanel);
            }
            Image unitImage = tilePaintModel.getUnitImage();

            int imageWidth = (unitImage.getWidth(null) * (freeMarsModel.getFreeMarsViewModel().getMapPanelZoomLevel() + 1)) / 10;
            unitImage = FreeMarsImageManager.createResizedCopy(unitImage, imageWidth, -1, false, null);
            int imageX = (int) paintPoint.getX() + (gridPanel.getGridWidth() / 2) - (unitImage.getWidth(gridPanel) / 2);
            int imageY = (int) paintPoint.getY() + gridPanel.getGridHeight() - (gridPanel.getGridHeight() / 4) - unitImage.getHeight(gridPanel) + 15;

            g2d.drawImage(unitImage, imageX, imageY, gridPanel);
            g2d.setColor(tilePaintModel.getUnitRectangleBackgroundColor());
            if (tilePaintModel.getUnitCount() > 1) {
                g2d.fillRect(imageX - 14, imageY + 8, tilePaintModel.getUnitRectangleWidth(), 16);
                g2d.setColor(tilePaintModel.getUnitRectangleForegroundColor());
                g2d.drawRect(imageX - 14, imageY + 8, tilePaintModel.getUnitRectangleWidth(), 16);
                g2d.setColor(tilePaintModel.getUnitRectangleBackgroundColor());
            }
            g2d.fillRect(imageX - 12, imageY + 10, tilePaintModel.getUnitRectangleWidth(), 16);
            g2d.setColor(tilePaintModel.getUnitRectangleForegroundColor());
            g2d.drawRect(imageX - 12, imageY + 10, tilePaintModel.getUnitRectangleWidth(), 16);
            g2d.setFont(tilePaintModel.getUnitRectangleContentFont());
            g2d.setColor(tilePaintModel.getUnitRectangleForegroundColor());
            if (tilePaintModel.getUnitRectangleContent() != null) {
                g2d.drawString(tilePaintModel.getUnitRectangleContent(), imageX - 11, imageY + 23);
            }
            boolean displayPlayerNationFlagsOnUnits = Boolean.valueOf(freeMarsModel.getFreeMarsPreferences().getProperty("display_player_nation_flags_on_units"));
            if (displayPlayerNationFlagsOnUnits) {
                g2d.drawImage(tilePaintModel.getUnitPlayerNationFlagImage(), imageX - 12, imageY + 18 + 11, gridPanel);
            }
        }
    }

    public static void paintUnitPath(Graphics2D g2d, RhombusGridPanel gridPanel, FreeMarsModel viewModel, TilePaintModel tilePaintModel, Point paintPoint) {
        if (tilePaintModel.isPaintingUnitPath()) {
            int pathX = (int) paintPoint.getX() + (gridPanel.getGridWidth() / 2);
            int pathY = (int) paintPoint.getY() + (gridPanel.getGridHeight() / 2);
            g2d.setColor(tilePaintModel.getUnitPathColor());
            g2d.fillRect(pathX, pathY, 8, 8);
        }
    }

    public static void paintCrossOnTile(Graphics2D g2d, RhombusGridPanel gridPanel, FreeMarsModel viewModel, TilePaintModel tilePaintModel, Point paintPoint, Color color) {
        int lineStartX = (int) paintPoint.getX() + gridPanel.getGridWidth() / 4 + 10;
        int line1StartY = (int) paintPoint.getY() + gridPanel.getGridHeight() / 4 + 5;
        int line2StartY = (int) paintPoint.getY() + gridPanel.getGridHeight() * 3 / 4 - 5;
        int lineEndX = (int) paintPoint.getX() + gridPanel.getGridWidth() * 3 / 4 - 10;
        int line1EndY = (int) paintPoint.getY() + gridPanel.getGridHeight() * 3 / 4 - 5;
        int line2EndY = (int) paintPoint.getY() + gridPanel.getGridHeight() / 4 + 5;
        g2d.setColor(color);
        g2d.setStroke(new BasicStroke(4));
        g2d.drawLine(lineStartX, line1StartY, lineEndX, line1EndY);
        g2d.drawLine(lineStartX, line2StartY, lineEndX, line2EndY);
    }
}

package org.freemars.ui.map;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.RenderingHints;
import java.util.ArrayList;
import org.apache.log4j.Logger;
import org.freemars.model.FreeMarsModel;
import org.freemars.ui.image.FreeMarsImageManager;
import org.freerealm.map.Coordinate;
import org.freerealm.unit.Unit;

/**
 *
 * @author Deniz ARIKAN
 */
public class MapPanel extends RealmGridPanel {

    private static final Logger logger = Logger.getLogger(MapPanel.class);

    public static final int UNIT_MOVEMENT_MAX_PARTS = 4;
    private final FreeMarsModel freeMarsModel;
    private final ArrayList<MiniMapPanel> miniMaps;
    private Unit movingUnit;
    private Coordinate unitMoveFromWorldCoordinate;
    private Coordinate unitMoveToWorldCoordinate;
    private int unitMovementPartCount;

    public MapPanel(FreeMarsModel model) {
        super(model.getRealm(), 128, 64);
        setBackground(new Color(3, 14, 56));
        setFocusable(true);
        miniMaps = new ArrayList<MiniMapPanel>();
        this.freeMarsModel = model;
        setDoubleBuffered(true);
    }

    public void update() {
        setZoomLevel(freeMarsModel.getFreeMarsViewModel().getMapPanelZoomLevel());
        setGridCounts();
        centerToCoordinate(freeMarsModel.getFreeMarsViewModel().getCenteredCoordinate());
        repaint();
        revalidate();
        for (MiniMapPanel miniMapPanel : miniMaps) {
            miniMapPanel.update();
        }
    }

    @Override
    public void setBounds(int x, int y, int width, int height) {
        super.setBounds(x, y, width, height);
        update();
    }

    public void addMiniMap(MiniMapPanel miniMap) {
        miniMaps.add(miniMap);
    }

    @Override
    public void paintComponent(Graphics g) {
        long startTime = System.currentTimeMillis();
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        if ((getOrdinateOffset() + getVerticalGrids()) > getRealm().getMapHeight() + 2) {
            setOrdinateOffset(getRealm().getMapHeight() - getVerticalGrids() + 2);
        }
        for (int ordinate = (getOrdinateOffset() > 0 ? -1 : 0); ordinate < getVerticalGrids(); ordinate++) {
            for (int abscissa = -1; abscissa < getHorizontalGrids(); abscissa++) {
                Coordinate relativeCoordinate = new Coordinate(abscissa, ordinate);
                Coordinate worldCoordinate = getWorldCoordinate(relativeCoordinate);
                TilePaintModel tilePaintModel = freeMarsModel.getFreeMarsViewModel().getTilePaintModel(worldCoordinate);
                if (tilePaintModel != null) {
                    Point paintPoint = getPaintPoint(relativeCoordinate);
                    TilePainter.paintTileImages(g2d, this, freeMarsModel, tilePaintModel, paintPoint);
                }
            }
        }
        paintVegetationLayer(g2d);
        paintTileImprovementsLayer(g2d);
        paintBonusResourceLayer(g2d);
        paintCollectablesLayer(g2d);
        
        for (int ordinate = 0; ordinate < getVerticalGrids(); ordinate++) {
            for (int abscissa = -1; abscissa < getHorizontalGrids(); abscissa++) {
                Coordinate relativeCoordinate = new Coordinate(abscissa, ordinate);
                Coordinate worldCoordinate = getWorldCoordinate(relativeCoordinate);
                TilePaintModel tilePaintModel = freeMarsModel.getFreeMarsViewModel().getTilePaintModel(worldCoordinate);
                Point paintPoint = getPaintPoint(relativeCoordinate);
                if (tilePaintModel != null) {
                    TilePainter.paintTileColony(g2d, this, freeMarsModel, tilePaintModel, paintPoint);
                    TilePainter.paintTileUnit(g2d, this, freeMarsModel, tilePaintModel, paintPoint);
                    TilePainter.paintUnitPath(g2d, this, freeMarsModel, tilePaintModel, paintPoint);
                }
            }
        }
        
        paintTileInfoLayer(g2d);
        
        if (movingUnit != null && unitMoveFromWorldCoordinate != null && unitMoveToWorldCoordinate != null) {
            Coordinate unitMoveFromRelativeCoordinate = getRelativeCoordinate(unitMoveFromWorldCoordinate);
            Coordinate unitMoveToRelativeCoordinate = getRelativeCoordinate(unitMoveToWorldCoordinate);
            Point moveFromPaintPoint = getPaintPoint(unitMoveFromRelativeCoordinate);
            Point moveToPaintPoint = getPaintPoint(unitMoveToRelativeCoordinate);
            Image unitImage = FreeMarsImageManager.getImage(movingUnit);
            int imageWidth = (unitImage.getWidth(this) * (freeMarsModel.getFreeMarsViewModel().getMapPanelZoomLevel() + 1)) / 10;
            unitImage = FreeMarsImageManager.createResizedCopy(unitImage, imageWidth, -1, false, null);
            int imageX = (int) moveFromPaintPoint.getX() + (getGridWidth() / 2) - (imageWidth / 2);
            int imageY = (int) moveFromPaintPoint.getY() + getGridHeight() - (getGridHeight() / 4) - unitImage.getHeight(this) + 15;
            int partX = (int) (((moveToPaintPoint.getX() - moveFromPaintPoint.getX()) * getUnitMovementPartCount()) / UNIT_MOVEMENT_MAX_PARTS);
            int partY = (int) (((moveToPaintPoint.getY() - moveFromPaintPoint.getY()) * getUnitMovementPartCount()) / UNIT_MOVEMENT_MAX_PARTS);
            g2d.drawImage(unitImage, imageX + partX, imageY + partY, this);
        }
        long endTime = System.currentTimeMillis();
        logger.debug("Map panel paint component executed in " + (endTime - startTime) + ".");
    }

    public void setUnitMoveFromWorldCoordinate(Coordinate unitMoveFromWorldCoordinate) {
        this.unitMoveFromWorldCoordinate = unitMoveFromWorldCoordinate;
    }

    public void setUnitMoveToWorldCoordinate(Coordinate unitMoveToWorldCoordinate) {
        this.unitMoveToWorldCoordinate = unitMoveToWorldCoordinate;
    }

    public int getUnitMovementPartCount() {
        return unitMovementPartCount;
    }

    public void setUnitMovementPartCount(int unitMovementPartCount) {
        this.unitMovementPartCount = unitMovementPartCount;
    }

    public void setMovingUnit(Unit movingUnit) {
        this.movingUnit = movingUnit;
    }

    private void paintTileImprovementsLayer(Graphics2D g2d) {
        for (int ordinate = (getOrdinateOffset() > 0 ? -1 : 0); ordinate < getVerticalGrids(); ordinate++) {
            for (int abscissa = -1; abscissa < getHorizontalGrids(); abscissa++) {
                Coordinate relativeCoordinate = new Coordinate(abscissa, ordinate);
                Coordinate worldCoordinate = getWorldCoordinate(relativeCoordinate);
                TilePaintModel tilePaintModel = freeMarsModel.getFreeMarsViewModel().getTilePaintModel(worldCoordinate);
                if (tilePaintModel != null) {
                    Point paintPoint = getPaintPoint(relativeCoordinate);
                    TilePainter.paintTileImprovements(g2d, this, freeMarsModel, tilePaintModel, paintPoint);
                }
            }
        }
    }

    private void paintVegetationLayer(Graphics2D g2d) {
        for (int ordinate = (getOrdinateOffset() > 0 ? -1 : 0); ordinate < getVerticalGrids(); ordinate++) {
            for (int abscissa = -1; abscissa < getHorizontalGrids(); abscissa++) {
                Coordinate relativeCoordinate = new Coordinate(abscissa, ordinate);
                Coordinate worldCoordinate = getWorldCoordinate(relativeCoordinate);
                TilePaintModel tilePaintModel = freeMarsModel.getFreeMarsViewModel().getTilePaintModel(worldCoordinate);
                if (tilePaintModel != null) {
                    Point paintPoint = getPaintPoint(relativeCoordinate);
                    TilePainter.paintTileVegetation(g2d, this, freeMarsModel, tilePaintModel, paintPoint);
                }
            }
        }
    }

    private void paintBonusResourceLayer(Graphics2D g2d) {
        for (int ordinate = (getOrdinateOffset() > 0 ? -1 : 0); ordinate < getVerticalGrids(); ordinate++) {
            for (int abscissa = -1; abscissa < getHorizontalGrids(); abscissa++) {
                Coordinate relativeCoordinate = new Coordinate(abscissa, ordinate);
                Coordinate worldCoordinate = getWorldCoordinate(relativeCoordinate);
                TilePaintModel tilePaintModel = freeMarsModel.getFreeMarsViewModel().getTilePaintModel(worldCoordinate);
                if (tilePaintModel != null) {
                    Point paintPoint = getPaintPoint(relativeCoordinate);
                    TilePainter.paintTileBonusResource(g2d, this, freeMarsModel, tilePaintModel, paintPoint);
                }
            }
        }
    }

    private void paintCollectablesLayer(Graphics2D g2d) {
        for (int ordinate = (getOrdinateOffset() > 0 ? -1 : 0); ordinate < getVerticalGrids(); ordinate++) {
            for (int abscissa = -1; abscissa < getHorizontalGrids(); abscissa++) {
                Coordinate relativeCoordinate = new Coordinate(abscissa, ordinate);
                Coordinate worldCoordinate = getWorldCoordinate(relativeCoordinate);
                TilePaintModel tilePaintModel = freeMarsModel.getFreeMarsViewModel().getTilePaintModel(worldCoordinate);
                if (tilePaintModel != null) {
                    Point paintPoint = getPaintPoint(relativeCoordinate);
                    TilePainter.paintTileCollectable(g2d, this, freeMarsModel, tilePaintModel, paintPoint);
                }
            }
        }
    }

    private void paintTileInfoLayer(Graphics2D g2d) {
        for (int ordinate = (getOrdinateOffset() > 0 ? -1 : 0); ordinate < getVerticalGrids(); ordinate++) {
            for (int abscissa = -1; abscissa < getHorizontalGrids(); abscissa++) {
                Coordinate relativeCoordinate = new Coordinate(abscissa, ordinate);
                Coordinate worldCoordinate = getWorldCoordinate(relativeCoordinate);
                TilePaintModel tilePaintModel = freeMarsModel.getFreeMarsViewModel().getTilePaintModel(worldCoordinate);
                if (tilePaintModel != null) {
                    Point paintPoint = getPaintPoint(relativeCoordinate);
                    TilePainter.paintTileInfo(g2d, this, freeMarsModel, tilePaintModel, paintPoint);
                    boolean displayTerritoryBorders = Boolean.valueOf(freeMarsModel.getFreeMarsPreferences().getProperty("display_territory_borders"));
                    if (displayTerritoryBorders) {
                        TilePainter.paintTileTerritoryBorder(g2d, this, tilePaintModel, paintPoint);
                    }
                }
            }
        }
    }

    private void setZoomLevel(int zoomLevel) {
        setGridHeight(16 * (zoomLevel + 1));
        setGridWidth(32 * (zoomLevel + 1));
    }
}

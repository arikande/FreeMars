package org.freemars.ui.map;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.util.Iterator;
import org.freemars.model.FreeMarsModel;
import org.freemars.ui.image.FreeMarsImageManager;
import org.freerealm.map.Coordinate;
import org.freerealm.tile.Tile;
import org.freerealm.unit.Unit;

/**
 *
 * @author Deniz ARIKAN
 */
public class MiniMapPanel extends RealmGridPanel {

    private final FreeMarsModel freeMarsModel;
    private MapPanel mapPanel;

    public MiniMapPanel(FreeMarsModel freeMarsModel) {
        super(freeMarsModel.getRealm(), 128, 64);
        setBackground(new Color(3, 14, 56));
        setFocusable(true);
        this.freeMarsModel = freeMarsModel;
        setDoubleBuffered(true);
    }

    public void update() {
        setZoomLevel(freeMarsModel.getFreeMarsViewModel().getMiniMapPanelZoomLevel());
        setGridCounts();
        centerToCoordinate(freeMarsModel.getFreeMarsViewModel().getCenteredCoordinate());
        repaint();
        revalidate();
    }

    @Override
    public void setBounds(int x, int y, int width, int height) {
        super.setBounds(x, y, width, height);
        update();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        if ((getOrdinateOffset() + getVerticalGrids()) > getRealm().getMapHeight() + 2) {
            setOrdinateOffset(getRealm().getMapHeight() - getVerticalGrids() + 2);
        }
        for (int ordinate = (getOrdinateOffset() > 0 ? -1 : 0); ordinate < getVerticalGrids(); ordinate++) {
            for (int abscissa = -1; abscissa < getHorizontalGrids(); abscissa++) {
                Coordinate relativeCoordinate = new Coordinate(abscissa, ordinate);
                Coordinate worldCoordinate = getWorldCoordinate(relativeCoordinate);
                if (isPaintingCoordinate(worldCoordinate)) {
                    Point paintPoint = getPaintPoint(relativeCoordinate);
                    int miniMapTileDisplayType = Integer.parseInt(freeMarsModel.getFreeMarsPreferences().getProperty("mini_map_tile_display_type"));
                    if (miniMapTileDisplayType == 0) {
                        paintCoordinateWithTileColor(g2d, worldCoordinate, paintPoint);
                    } else {
                        paintCoordinateWithTileImage(g2d, worldCoordinate, paintPoint);
                    }
                }
            }
        }
        paintVisibleMapRectangle(g2d);
    }

    public void setZoomLevel(int zoomLevel) {
        switch (zoomLevel) {
            case 0:
                setGridHeight(6);
                setGridWidth(12);
                break;
            case 1:
                setGridHeight(8);
                setGridWidth(16);
                break;
            case 2:
                setGridHeight(10);
                setGridWidth(20);
                break;
            case 3:
                setGridHeight(12);
                setGridWidth(24);
                break;
            case 4:
                setGridHeight(14);
                setGridWidth(28);
                break;
            case 5:
                setGridHeight(16);
                setGridWidth(32);
                break;
        }
    }

    public void setMapPanel(MapPanel mapPanel) {
        this.mapPanel = mapPanel;
    }

    private boolean isPaintingCoordinate(Coordinate worldCoordinate) {
        boolean displayUnexploredTiles = Boolean.valueOf(freeMarsModel.getRealm().getProperty("display_unexplored_tiles"));
        if (!displayUnexploredTiles) {
            if (getRealm().getPlayerManager().getActivePlayer().isCoordinateExplored(worldCoordinate)) {
                return true;
            } else {
                return false;
            }
        } else {
            return true;
        }
    }

    private void paintCoordinateWithTileColor(Graphics2D g2d, Coordinate worldCoordinate, Point paintPoint) {
        Tile tile = getRealm().getTile(worldCoordinate);
        if (tile != null) {
            if (tile.getType().getName().equals("Wasteland")) {
                g2d.setColor(new Color(192, 53, 53));
            } else if (tile.getType().getName().equals("Crater")) {
                g2d.setColor(new Color(192, 53, 53));
            } else if (tile.getType().getName().equals("Hills")) {
                g2d.setColor(new Color(192, 53, 53));
            } else if (tile.getType().getName().equals("Mountains")) {
                g2d.setColor(new Color(192, 53, 53));
            } else if (tile.getType().getName().equals("Misty mountains")) {
                g2d.setColor(new Color(192, 53, 53));
            } else if (tile.getType().getName().equals("Plains")) {
                g2d.setColor(new Color(68, 80, 40));
            } else if (tile.getType().getName().equals("Desert")) {
                g2d.setColor(new Color(253, 239, 171));
            } else if (tile.getType().getName().equals("Swamp")) {
                g2d.setColor(new Color(135, 139, 114));
            } else if (tile.getType().getName().equals("Ice")) {
                g2d.setColor(new Color(254, 254, 250));
            } else if (tile.getType().getName().equals("Chasm")) {
                g2d.setColor(Color.black);
            } else if (tile.getType().getName().equals("Tundra")) {
                g2d.setColor(new Color(238, 232, 205));
            } else {
                g2d.setColor(Color.GREEN);
            }
            int tileX = (int) paintPoint.getX();
            int tileY = (int) paintPoint.getY();

            int xpoints[] = {tileX, tileX + getGridWidth() / 2, tileX + getGridWidth(), tileX + getGridWidth() / 2};
            int ypoints[] = {tileY + getGridHeight() / 2, tileY, tileY + getGridHeight() / 2, tileY + getGridHeight()};
            int npoints = 4;

            g2d.fillPolygon(xpoints, ypoints, npoints);

            if (tile.getType().getName().equals("Crater")) {
                g2d.setColor(Color.black);
                g2d.fillOval(tileX + getGridWidth() / 4, tileY + getGridHeight() / 4 + 1, getGridWidth() / 2, getGridHeight() / 2);
            }
            if (tile.getType().getName().equals("Hills")) {
                g2d.setColor(Color.black);
                g2d.setStroke(new BasicStroke(2));
                g2d.drawArc(tileX + getGridWidth() / 4, tileY + getGridHeight() / 4 + 1, getGridWidth() / 2, getGridHeight() / 2, 0, 180);
            }
            if (tile.getType().getName().equals("Mountains")) {
                g2d.setColor(Color.black);
                g2d.setStroke(new BasicStroke(2));
                g2d.drawLine(tileX + getGridWidth() / 4, tileY + getGridHeight() / 2 + 1, tileX + getGridWidth() / 2, tileY + getGridHeight() / 4);
                g2d.drawLine(tileX + getGridWidth() / 2, tileY + getGridHeight() / 4, tileX + getGridWidth() * 3 / 4, tileY + getGridHeight() / 2 + 1);
            }
            if (tile.getType().getName().equals("Misty mountains")) {
                g2d.setColor(Color.black);
                g2d.setStroke(new BasicStroke(2));
                g2d.drawLine(tileX + getGridWidth() / 4, tileY + getGridHeight() / 2 + 1, tileX + getGridWidth() / 2, tileY + getGridHeight() / 4);
                g2d.drawLine(tileX + getGridWidth() / 2, tileY + getGridHeight() / 4, tileX + getGridWidth() * 3 / 4, tileY + getGridHeight() / 2 + 1);
                g2d.drawLine(tileX + getGridWidth() / 4, tileY + getGridHeight() / 2 + 1, tileX + getGridWidth() * 3 / 4, tileY + getGridHeight() / 2 + 1);
            }
            if (tile.getSettlement() != null) {
                int pad = 4;
                int colonyXPoints[] = {tileX + pad, tileX + getGridWidth() / 2, tileX + getGridWidth() - pad, tileX + getGridWidth() / 2};
                int colonyYPoints[] = {tileY + getGridHeight() / 2, tileY + pad, tileY + getGridHeight() / 2, tileY + getGridHeight() - pad};
                g2d.setColor(Color.orange);
                g2d.fillPolygon(colonyXPoints, colonyYPoints, npoints);
                g2d.setColor(Color.black);
                g2d.drawPolygon(colonyXPoints, colonyYPoints, npoints);
            }
            Iterator<Unit> tileUnitsiterator = tile.getUnitsIterator();
            while (tileUnitsiterator.hasNext()) {
                Unit unit = tileUnitsiterator.next();
                if (unit.equals(freeMarsModel.getActivePlayer().getActiveUnit())) {
                    int pad = 4;
                    int colonyXPoints[] = {tileX + pad + 3, tileX + getGridWidth() / 2, tileX + getGridWidth() - pad - 3, tileX + getGridWidth() / 2};
                    int colonyYPoints[] = {tileY + getGridHeight() / 2, tileY + pad, tileY + getGridHeight() / 2, tileY + getGridHeight() - pad};
                    g2d.setColor(Color.green);
                    g2d.fillPolygon(colonyXPoints, colonyYPoints, npoints);
                }
            }
        }
    }

    private void paintCoordinateWithTileImage(Graphics2D g2d, Coordinate worldCoordinate, Point paintPoint) {
        Tile tile = getRealm().getTile(worldCoordinate);
        if (tile != null) {
            int tileX = (int) paintPoint.getX();
            int tileY = (int) paintPoint.getY();
            int npoints = 4;
            Image image = FreeMarsImageManager.getImage(tile);
            image = FreeMarsImageManager.createResizedCopy(image, getGridWidth(), getGridHeight(), false, this);
            g2d.drawImage(image, (int) paintPoint.getX(), (int) paintPoint.getY(), this);
            if (tile.getSettlement() != null) {
                int pad = 4;
                int colonyXPoints[] = {tileX + pad, tileX + getGridWidth() / 2, tileX + getGridWidth() - pad, tileX + getGridWidth() / 2};
                int colonyYPoints[] = {tileY + getGridHeight() / 2, tileY + pad, tileY + getGridHeight() / 2, tileY + getGridHeight() - pad};
                g2d.setColor(Color.orange);
                g2d.fillPolygon(colonyXPoints, colonyYPoints, npoints);
                g2d.setColor(Color.black);
                g2d.drawPolygon(colonyXPoints, colonyYPoints, npoints);
            }
        }
    }

    private void paintVisibleMapRectangle(Graphics2D g2d) {
        if (mapPanel != null) {
            int mapRectangleAbscissa = (mapPanel.getAbscissaOffset() - getAbscissaOffset()) * getGridWidth();
            int mapRectangleOrdinate = (mapPanel.getOrdinateOffset() - getOrdinateOffset()) * (getGridHeight() / 2);
            int mapRectangleWidth = (mapPanel.getHorizontalGrids() - 1) * getGridWidth();
            int mapRectangleHeight = ((mapPanel.getVerticalGrids() - 1) * getGridHeight()) / 2;
            g2d.setColor(Color.white);
            g2d.drawRect(mapRectangleAbscissa, mapRectangleOrdinate, mapRectangleWidth, mapRectangleHeight);
        }
    }

}

package org.freemars.colonydialog.workforce;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.RenderingHints;
import java.util.ArrayList;
import javax.swing.event.MouseInputListener;
import org.freemars.colonydialog.ColonyDialogModel;
import org.freemars.ui.image.FreeMarsImageManager;
import org.freemars.ui.map.RhombusGridPanel;
import org.freemars.ui.map.TilePaintModel;
import org.freemars.ui.map.TilePaintModelBuilder;
import org.freemars.ui.map.TilePainter;
import org.freerealm.RealmConstants;
import org.freerealm.Utility;
import org.freerealm.map.Coordinate;
import org.freerealm.map.Direction;
import org.freerealm.resource.Resource;
import org.freerealm.settlement.Settlement;
import org.freerealm.settlement.workforce.WorkForce;

/**
 *
 * @author Deniz ARIKAN
 */
public class ColonyWorkForceManagementPanel extends RhombusGridPanel {

    private static final int TILE_WIDTH = 128;
    private static final int TILE_HEIGHT = 64;
    private ColonyDialogModel model;
    private Coordinate highlightedCoordinate;
    private Coordinate highlightedWorldCoordinate;
    private final ArrayList<TileAvailableResourceImage> availableResourcesForTile;
    private String freeWorkForce;

    public ColonyWorkForceManagementPanel() {
        super(TILE_WIDTH, TILE_HEIGHT);
        availableResourcesForTile = new ArrayList<TileAvailableResourceImage>();
        freeWorkForce = "";
    }

    public void addMouseAdapter(MouseInputListener mouseInputListener) {
        addMouseListener(mouseInputListener);
        addMouseMotionListener(mouseInputListener);
    }

    public void setModel(ColonyDialogModel model) {
        this.model = model;
    }

    public void setHighlightedCoordinate(Coordinate highlightedCoordinate) {
        this.highlightedCoordinate = highlightedCoordinate;
    }

    public void setHighlightedWorldCoordinate(Coordinate highlightedWorldCoordinate) {
        this.highlightedWorldCoordinate = highlightedWorldCoordinate;
    }

    public void clearAvailableResourcesForTile() {
        availableResourcesForTile.clear();
    }

    public void addAvailableResourcesForTile(TileAvailableResourceImage tileAvailableResources) {
        availableResourcesForTile.add(tileAvailableResources);
    }

    public void setFreeWorkForce(String freeWorkForce) {
        this.freeWorkForce = freeWorkForce;
    }

    public Coordinate getCoordinateAt(int x, int y) {
        int M = (x + 2 * y - (getGridWidth() / 2)) / getGridWidth();
        int N = (-y + (x / 2) - (getGridHeight() / 2));
        N = (N > 0 ? (N / getGridHeight()) + 1 : N / getGridHeight());
        int tileAbscissa = ((M + N) / 2);
        Coordinate coordinate = new Coordinate(tileAbscissa, M - N);
        return coordinate;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (model != null) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            g2d.drawString("Available workforce  " + freeWorkForce, 0, 10);
            int resourceImageX = 0;
            for (TileAvailableResourceImage tileAvailableResourceImage : availableResourcesForTile) {
                if (tileAvailableResourceImage.getCount() < 5) {
                    for (int i = 0; i < tileAvailableResourceImage.getCount(); i++) {
                        g2d.drawImage(tileAvailableResourceImage.getImage(), resourceImageX, this.getHeight() - 25, this);
                        resourceImageX = resourceImageX + 17;
                    }
                } else {
                    Font originalFont = g2d.getFont();
                    g2d.drawImage(tileAvailableResourceImage.getImage(), resourceImageX, this.getHeight() - 25, this);
                    resourceImageX = resourceImageX + 21;
                    g2d.setFont(originalFont.deriveFont(15f).deriveFont(Font.BOLD));
                    g2d.drawString("x" + tileAvailableResourceImage.getCount(), resourceImageX, this.getHeight() - 8);
                    resourceImageX = resourceImageX + 10;
                    g2d.setFont(originalFont);
                }
                resourceImageX = resourceImageX + 15;
            }
            resourceImageX = resourceImageX + 10;
            if (highlightedWorldCoordinate != null) {
                Settlement colonyUsingCoordinate = Utility.findSettlementUsingCoordinate(model.getRealm(), highlightedWorldCoordinate);
                if (colonyUsingCoordinate != null && !colonyUsingCoordinate.equals(model.getColony())) {
                    g2d.drawString("Used by " + colonyUsingCoordinate.getName(), resourceImageX, this.getHeight() - 8);
                }
            }
            for (Direction direction : RealmConstants.directions) {
                Coordinate coordinate = model.getRealm().getRelativeCoordinate(model.getColony().getCoordinate(), direction);
                if (coordinate != null) {
                    Coordinate paintCoordinate = model.getRealm().getRelativeCoordinate(new Coordinate(1, 2), direction);
                    Point paintPoint = super.getRhombusPoint(paintCoordinate);
                    TilePaintModel tilePaintModel = TilePaintModelBuilder.buildTilePaintModel(g, model.getFreeMarsModel(), coordinate);
                    TilePainter.paintTileImages(g2d, this, model.getFreeMarsModel(), tilePaintModel, paintPoint);
                }
            }
            paintVegetationLayer(g);
            paintTileImprovementsLayer(g);
            paintBonusResourceLayer(g);
            for (Direction direction : RealmConstants.directions) {
                Coordinate coordinate = model.getRealm().getRelativeCoordinate(model.getColony().getCoordinate(), direction);
                if (coordinate != null) {
                    Coordinate paintCoordinate = model.getRealm().getRelativeCoordinate(new Coordinate(1, 2), direction);
                    Point paintPoint = super.getRhombusPoint(paintCoordinate);
                    TilePaintModel tilePaintModel = TilePaintModelBuilder.buildTilePaintModel(g, model.getFreeMarsModel(), coordinate);
                    TilePainter.paintTileColony(g2d, this, model.getFreeMarsModel(), tilePaintModel, paintPoint);
                    if (model.getColony().isCoordinateFertilized(coordinate)) {
                        Resource resource = model.getRealm().getResourceManager().getResource("Fertilizer");
                        Image fertilizerImage = FreeMarsImageManager.getImage(resource);
                        fertilizerImage = FreeMarsImageManager.createResizedCopy(fertilizerImage, 30, -1, false, this);
                        g2d.drawImage(fertilizerImage, paintPoint.x + 10, paintPoint.y + 10, this);
                    }
                    if (!Utility.isTileAvailableForSettlement(model.getRealm(), model.getColony(), coordinate)) {
                        TilePainter.paintCrossOnTile(g2d, this, null, tilePaintModel, paintPoint, Color.red);
                    }
                }
            }
            for (Direction direction : RealmConstants.directions) {
                Coordinate coordinate = model.getRealm().getRelativeCoordinate(model.getColony().getCoordinate(), direction);
                if (coordinate != null) {
                    Coordinate paintCoordinate = model.getRealm().getRelativeCoordinate(new Coordinate(1, 2), direction);
                    Point paintPoint = super.getRhombusPoint(paintCoordinate);
                    paintTileWorkForce(g2d, paintPoint, coordinate);
                    if (paintCoordinate.equals(highlightedCoordinate)) {
                        g2d.setColor(new Color(192, 53, 53));
                        g2d.setColor(Color.BLACK);
                        g2d.setStroke(new BasicStroke(2));
                        paintRhombus(g2d, paintPoint);
                    }
                }
            }
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(0, 3 * TILE_HEIGHT + 25);
    }

    private void paintVegetationLayer(Graphics g) {
        for (Direction direction : RealmConstants.directions) {
            Coordinate coordinate = model.getRealm().getRelativeCoordinate(model.getColony().getCoordinate(), direction);
            if (coordinate != null) {
                Coordinate paintCoordinate = model.getRealm().getRelativeCoordinate(new Coordinate(1, 2), direction);
                Point paintPoint = super.getRhombusPoint(paintCoordinate);
                TilePaintModel tilePaintModel = TilePaintModelBuilder.buildTilePaintModel(g, model.getFreeMarsModel(), coordinate);
                TilePainter.paintTileVegetation((Graphics2D) g, this, model.getFreeMarsModel(), tilePaintModel, paintPoint);
            }
        }
    }

    private void paintTileImprovementsLayer(Graphics g) {
        for (Direction direction : RealmConstants.directions) {
            Coordinate coordinate = model.getRealm().getRelativeCoordinate(model.getColony().getCoordinate(), direction);
            if (coordinate != null) {
                Coordinate paintCoordinate = model.getRealm().getRelativeCoordinate(new Coordinate(1, 2), direction);
                Point paintPoint = super.getRhombusPoint(paintCoordinate);
                TilePaintModel tilePaintModel = TilePaintModelBuilder.buildTilePaintModel(g, model.getFreeMarsModel(), coordinate);
                TilePainter.paintTileImprovements((Graphics2D) g, this, model.getFreeMarsModel(), tilePaintModel, paintPoint);
            }
        }
    }

    private void paintBonusResourceLayer(Graphics g) {
        for (Direction direction : RealmConstants.directions) {
            Coordinate coordinate = model.getRealm().getRelativeCoordinate(model.getColony().getCoordinate(), direction);
            if (coordinate != null) {
                Coordinate paintCoordinate = model.getRealm().getRelativeCoordinate(new Coordinate(1, 2), direction);
                Point paintPoint = super.getRhombusPoint(paintCoordinate);
                TilePaintModel tilePaintModel = TilePaintModelBuilder.buildTilePaintModel(g, model.getFreeMarsModel(), coordinate);
                TilePainter.paintTileBonusResource((Graphics2D) g, this, model.getFreeMarsModel(), tilePaintModel, paintPoint);
            }
        }
    }

    private void paintTileWorkForce(Graphics2D g2d, Point paintPoint, Coordinate coordinate) {
        WorkForce workForce = model.getColony().getWorkForceManager().getAssignedWorkforceForTile(coordinate);
        if (workForce != null) {
            int stringX = (int) (paintPoint.getX() + TILE_WIDTH / 3) - 5;
            int stringY = (int) paintPoint.getY() + TILE_HEIGHT / 2;
            g2d.setFont(new Font("Arial", 1, 11));
            int tileProduction = model.getRealm().getTile(coordinate).getProduction(workForce.getResource()) * workForce.getNumberOfWorkers();
            String resourceInfo = tileProduction + " " + workForce.getResource().getName();
            String workForceInfo = workForce.getNumberOfWorkers() + " workers";
            g2d.setColor(Color.BLACK);
            g2d.drawString(resourceInfo, stringX + 1, stringY + 1);
            g2d.drawString(workForceInfo, stringX + 1, stringY + 13);
            g2d.setColor(Color.WHITE);
            g2d.drawString(resourceInfo, stringX, stringY);
            g2d.drawString(workForceInfo, stringX, stringY + 12);
        }
    }

}

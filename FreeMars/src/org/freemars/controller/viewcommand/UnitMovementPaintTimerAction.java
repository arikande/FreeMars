package org.freemars.controller.viewcommand;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Timer;
import org.freemars.model.FreeMarsModel;
import org.freemars.ui.map.MapPanel;
import org.freemars.ui.map.TilePaintModel;
import org.freemars.ui.map.TilePaintModelBuilder;
import org.freerealm.map.Coordinate;
import org.freerealm.tile.Tile;
import org.freerealm.unit.Unit;

/**
 *
 * @author Deniz ARIKAN
 */
public class UnitMovementPaintTimerAction extends AbstractAction {

    private final FreeMarsModel freeMarsModel;
    private final MapPanel mapPanel;
    private final Unit unit;
    private final boolean pauseAfterMovement;
    private final Coordinate newCoordinate;
    private Rectangle repaintRectangle;

    protected UnitMovementPaintTimerAction(FreeMarsModel freeMarsModel, MapPanel mapPanel, Unit unit, Coordinate newCoordinate, boolean pauseAfterMovement) {
        this.freeMarsModel = freeMarsModel;
        this.mapPanel = mapPanel;
        this.unit = unit;
        this.newCoordinate = newCoordinate;
        this.pauseAfterMovement = pauseAfterMovement;
    }

    public void actionPerformed(ActionEvent e) {
        if (mapPanel.getUnitMovementPartCount() < MapPanel.UNIT_MOVEMENT_MAX_PARTS) {
            mapPanel.setUnitMovementPartCount(mapPanel.getUnitMovementPartCount() + 1);
            mapPanel.setMovingUnit(unit);
        } else {
            ((Timer) e.getSource()).stop();
            TilePaintModel tilePaintModel = freeMarsModel.getFreeMarsViewModel().getTilePaintModel(newCoordinate);
            if (tilePaintModel != null && freeMarsModel.getHumanPlayer().isCoordinateExplored(newCoordinate)) {
                Tile tile = freeMarsModel.getTile(newCoordinate);
                TilePaintModelBuilder.buildModelForTileUnits(mapPanel.getGraphics(), tilePaintModel, freeMarsModel, tile, null);
            }
            mapPanel.setMovingUnit(null);
            mapPanel.setUnitMoveFromWorldCoordinate(null);
            mapPanel.setUnitMoveToWorldCoordinate(null);
            if (pauseAfterMovement) {
                try {
                    Thread.sleep(300);
                } catch (InterruptedException interruptedException) {
                }
            }
            synchronized (ViewCommandExecutionThread.WAIT_ON) {
                ViewCommandExecutionThread.WAIT_ON.notify();
            }
        }
        mapPanel.repaint(repaintRectangle);
    }

    public void setRepaintRectangle(Rectangle repaintRectangle) {
        this.repaintRectangle = repaintRectangle;
    }
}

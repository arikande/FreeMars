package org.freemars.controller.viewcommand;

import java.awt.MouseInfo;

import org.freemars.controller.FreeMarsController;
import org.freemars.ui.map.MapPanel;
import org.freerealm.RealmConstants;
import org.freerealm.command.AbstractCommand;
import org.freerealm.executor.CommandResult;
import org.freerealm.map.Coordinate;
import org.freerealm.map.Direction;

/**
 *
 * @author Deniz ARIKAN
 */
public class ScrollMapCommand extends AbstractCommand implements ViewCommand {

    private final FreeMarsController freeMarsController;
    public static boolean running = false;
    private static final int SLIDE_AREA_WIDTH = 40;
    private static final int SLIDE_AREA_SOUTH_HEIGHT = 50;

    public ScrollMapCommand(FreeMarsController freeMarsController) {
        this.freeMarsController = freeMarsController;
    }

    @Override
    public String toString() {
        return "ScrollMap";
    }

    public CommandResult execute() {
        if (!running) {
            running = true;
            while (running) {
                Direction slideDirection = getSlideDirection(freeMarsController);
                if (slideDirection != null) {
                    Coordinate centeredCoordinate = freeMarsController.getFreeMarsModel().getFreeMarsViewModel().getCenteredCoordinate();
                    Coordinate newCenteredCoordinate = null;
                    if (slideDirection.equals(RealmConstants.NORTHWEST)) {
                        newCenteredCoordinate = new Coordinate(centeredCoordinate.getAbscissa() - 1, centeredCoordinate.getOrdinate() - 2);
                    } else if (slideDirection.equals(RealmConstants.NORTHEAST)) {
                        newCenteredCoordinate = new Coordinate(centeredCoordinate.getAbscissa() + 1, centeredCoordinate.getOrdinate() - 2);
                    } else if (slideDirection.equals(RealmConstants.NORTH)) {
                        newCenteredCoordinate = new Coordinate(centeredCoordinate.getAbscissa(), centeredCoordinate.getOrdinate() - 2);
                    } else if (slideDirection.equals(RealmConstants.SOUTH)) {
                        newCenteredCoordinate = new Coordinate(centeredCoordinate.getAbscissa(), centeredCoordinate.getOrdinate() + 2);
                    } else if (slideDirection.equals(RealmConstants.WEST)) {
                        newCenteredCoordinate = new Coordinate(centeredCoordinate.getAbscissa() - 1, centeredCoordinate.getOrdinate());
                    } else if (slideDirection.equals(RealmConstants.EAST)) {
                        newCenteredCoordinate = new Coordinate(centeredCoordinate.getAbscissa() + 1, centeredCoordinate.getOrdinate());
                    }
                    freeMarsController.executeViewCommand(new SetCenteredCoordinateCommand(freeMarsController, newCenteredCoordinate));
                    try {
                        Thread.sleep(250);
                    } catch (InterruptedException interruptedException) {
                    }
                } else {
                    running = false;
                }
            }
        }
        return new CommandResult(CommandResult.RESULT_OK, "");
    }

    public boolean isBlockingViewExecutionThread() {
        return false;
    }

    public static Direction getSlideDirection(FreeMarsController freeMarsController) {
        MapPanel mapPanel = freeMarsController.getGameFrame().getMapPanel();
        int totalTopInset = mapPanel.getY() + freeMarsController.getGameFrame().getInsets().top;
        int mapPanelWidth = mapPanel.getWidth();
        int mapPanelHeight = mapPanel.getHeight();
        int mouseX = (int) MouseInfo.getPointerInfo().getLocation().getX() - freeMarsController.getGameFrame().getX();
        int mouseY = (int) MouseInfo.getPointerInfo().getLocation().getY() - freeMarsController.getGameFrame().getY() - totalTopInset;
        if (mouseX >= 0 && mouseX <= SLIDE_AREA_WIDTH && mouseY >= -totalTopInset && mouseY <= SLIDE_AREA_WIDTH) {
            return RealmConstants.NORTHWEST;
        }
        if (mouseX >= mapPanelWidth - SLIDE_AREA_WIDTH && mouseX <= mapPanelWidth + 10 && mouseY >= -totalTopInset && mouseY <= SLIDE_AREA_WIDTH) {
            return RealmConstants.NORTHEAST;
        }
        if (mouseX >= 0 && mouseX <= SLIDE_AREA_WIDTH) {
            return RealmConstants.WEST;
        }
        if (mouseX >= mapPanelWidth - SLIDE_AREA_WIDTH && mouseX <= mapPanelWidth + 10) {
            return RealmConstants.EAST;
        }
        if (mouseY >= -totalTopInset && mouseY <= SLIDE_AREA_WIDTH) {
            return RealmConstants.NORTH;
        }
        if (mouseY >= mapPanelHeight - SLIDE_AREA_SOUTH_HEIGHT && mouseY <= mapPanelHeight + totalTopInset) {
            return RealmConstants.SOUTH;
        }
        return null;
    }

}

package org.freemars.editor.controller.command;

import java.awt.Dimension;

import org.freemars.editor.controller.EditorController;
import org.freerealm.command.AbstractCommand;
import org.freerealm.executor.CommandResult;
import org.freerealm.map.Coordinate;

/**
 *
 * @author Deniz ARIKAN
 */
public class SetCenteredCoordinateCommand extends AbstractCommand {

    private final EditorController editorController;
    private final Coordinate coordinate;

    public SetCenteredCoordinateCommand(EditorController editorController, Coordinate coordinate) {
        this.editorController = editorController;
        this.coordinate = coordinate;
    }

    public CommandResult execute() {
        Dimension imageDimension = editorController.getEditorFrame().getEditorMapPanelSize();
        int gridWidth = 16 * (editorController.getEditorModel().getEditorMapZoomLevel() + 1);
        int gridHeight = gridWidth / 2;
        int horizontalGrids = ((int) imageDimension.getWidth() / gridWidth) + 1;
        int verticalGrids = (2 * (int) imageDimension.getHeight() / (gridHeight)) + 1;
        int newAbscissaOffset = coordinate.getAbscissa() - (horizontalGrids / 2) + 1;
        if (newAbscissaOffset < 0) {
            newAbscissaOffset = newAbscissaOffset + editorController.getEditorModel().getRealm().getMapWidth();
        }
        int newOrdinateOffset = coordinate.getOrdinate() - (verticalGrids / 2) + 1;
        if (newOrdinateOffset < 0) {
            newOrdinateOffset = 0;
        }
        editorController.getEditorModel().setOffsetCoordinate(new Coordinate(newAbscissaOffset, newOrdinateOffset));
        editorController.repaintEditorPanel();
        return null;
    }

}

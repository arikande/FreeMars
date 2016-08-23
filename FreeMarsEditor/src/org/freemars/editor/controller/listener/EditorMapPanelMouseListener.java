package org.freemars.editor.controller.listener;

import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.Properties;
import java.util.Random;
import javax.swing.event.MouseInputAdapter;
import org.freemars.editor.controller.EditorController;
import org.freemars.editor.controller.action.EditorMapZoomInAction;
import org.freemars.editor.controller.action.EditorMapZoomOutAction;
import org.freemars.editor.controller.command.SetCenteredCoordinateCommand;
import org.freerealm.map.Coordinate;
import org.freerealm.tile.Tile;
import org.freerealm.tile.TileType;
import org.freerealm.vegetation.FreeRealmVegetation;

/**
 *
 * @author Deniz ARIKAN
 */
public class EditorMapPanelMouseListener extends MouseInputAdapter {

    private final EditorController editorController;

    public EditorMapPanelMouseListener(EditorController editorController) {
        this.editorController = editorController;
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        if ((mouseEvent.getModifiers() & InputEvent.BUTTON1_MASK) == InputEvent.BUTTON1_MASK) {
            handleLeftClick(mouseEvent);
        } else if ((mouseEvent.getModifiers() & InputEvent.BUTTON3_MASK) == InputEvent.BUTTON3_MASK) {
            handleRightClick(mouseEvent);
        }
    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent mouseWheelEvent) {
        int notches = mouseWheelEvent.getWheelRotation();
        if (notches < 0) {
            new EditorMapZoomInAction(editorController).actionPerformed(null);
        } else {
            new EditorMapZoomOutAction(editorController).actionPerformed(null);
        }
    }

    private void handleLeftClick(MouseEvent mouseEvent) {
        if (editorController.getEditorModel().getRealm().getMap() != null) {
            Coordinate coordinate = editorController.getCoordinateAt(mouseEvent.getX(), mouseEvent.getY());
            editorController.execute(new SetCenteredCoordinateCommand(editorController, coordinate));
        }
    }

    private void handleRightClick(MouseEvent mouseEvent) {
        if (editorController.getEditorModel().getRealm().getMap() != null) {
            Coordinate clickedCoordinate = editorController.getCoordinateAt(mouseEvent.getX(), mouseEvent.getY());
            Tile tile = editorController.getEditorModel().getRealm().getMap().getTile(clickedCoordinate);
            TileType tileType = editorController.getEditorModel().getBrush().getTileType();
            tile.setType(tileType);
            Properties tileProperties = new Properties();
            tileProperties.setProperty("imageType", "0" + new Random().nextInt(4));
            tile.setCustomProperties(tileProperties);
            if (editorController.getEditorModel().getBrush().getVegetationType() != null) {
                FreeRealmVegetation freeRealmVegetation = new FreeRealmVegetation();
                freeRealmVegetation.setType(editorController.getEditorModel().getBrush().getVegetationType());
                Properties vegetationProperties = new Properties();
                vegetationProperties.setProperty("imageType", "0" + new Random().nextInt(4));
                freeRealmVegetation.setCustomProperties(vegetationProperties);
                tile.setVegetation(freeRealmVegetation);
            } else {
                tile.setVegetation(null);
            }
            tile.setBonusResource(editorController.getEditorModel().getBrush().getBonusResource());
            editorController.getEditorModel().setFileDirty(true);
            editorController.updateEditorFrame();
        }
    }

}

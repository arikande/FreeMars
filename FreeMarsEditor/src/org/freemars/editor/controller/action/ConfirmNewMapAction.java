package org.freemars.editor.controller.action;

import java.awt.event.ActionEvent;
import java.util.Random;
import javax.swing.AbstractAction;
import org.freemars.editor.controller.EditorController;
import org.freemars.editor.view.NewMapDialog;
import org.freerealm.map.FreeRealmMap;
import org.freerealm.map.Map;
import org.freerealm.tile.FreeRealmTile;
import org.freerealm.tile.Tile;
import org.freerealm.tile.TileType;

/**
 *
 * @author Deniz ARIKAN
 */
public class ConfirmNewMapAction extends AbstractAction {

    private final EditorController editorController;
    private final NewMapDialog newMapDialog;

    public ConfirmNewMapAction(EditorController editorController, NewMapDialog newMapDialog) {
        super("Confirm");
        this.editorController = editorController;
        this.newMapDialog = newMapDialog;
    }

    public void actionPerformed(ActionEvent e) {
        editorController.getEditorModel().setFileDirty(false);
        editorController.getEditorModel().setFileName(null);

        String mapName = newMapDialog.getMapNameTextFieldValue();
        String mapDescription = newMapDialog.getMapDescriptionTextAreaValue();
        int width = newMapDialog.getMapWidthSpinnerValue();
        int height = newMapDialog.getMapHeightSpinnerValue();
        int suggestedPlayers = newMapDialog.getSuggestedPlayersSpinnerValue();

        Map map = new FreeRealmMap();
        map.setName(mapName);
        map.setDescription(mapDescription);
        map.setSuggestedPlayers(suggestedPlayers);
        Tile[][] mapItems = new Tile[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                Tile tile = new FreeRealmTile();
                TileType tileType;
                if (newMapDialog.getTileTypeComboBoxSelectedIndex() == 0) {
                    tileType = editorController.getEditorModel().getRealm().getTileTypeManager().getTileType(new Random().nextInt(11));
                } else {
                    tileType = editorController.getEditorModel().getRealm().getTileTypeManager().getTileType(newMapDialog.getTileTypeComboBoxSelectedIndex() - 1);
                }
                tile.setType(tileType);
                mapItems[i][j] = tile;
            }
        }
        map.setMapItems(mapItems);
        editorController.getEditorModel().getRealm().setMap(map);
        editorController.updateEditorFrame();

        newMapDialog.dispose();
    }
}

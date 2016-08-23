package org.freemars.editor.controller.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.freemars.editor.controller.EditorController;
import org.freemars.editor.controller.command.SetBrushTileTypeCommand;
import org.freerealm.tile.TileType;

/**
 *
 * @author Deniz ARIKAN
 */
public class TileTypeMenuItemActionListener implements ActionListener {

    private final EditorController editorController;
    private final TileType tileType;

    public TileTypeMenuItemActionListener(EditorController editorController, TileType tileType) {
        this.editorController = editorController;
        this.tileType = tileType;
    }

    public void actionPerformed(ActionEvent actionEvent) {
        editorController.execute(new SetBrushTileTypeCommand(editorController, tileType));
    }

}

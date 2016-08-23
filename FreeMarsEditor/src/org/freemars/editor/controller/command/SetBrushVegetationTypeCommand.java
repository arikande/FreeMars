package org.freemars.editor.controller.command;

import org.freemars.editor.controller.EditorController;
import org.freerealm.command.AbstractCommand;
import org.freerealm.executor.CommandResult;
import org.freerealm.tile.TileType;
import org.freerealm.vegetation.VegetationType;

/**
 *
 * @author Deniz ARIKAN
 */
public class SetBrushVegetationTypeCommand extends AbstractCommand {

    private final EditorController editorController;
    private final VegetationType vegetationType;

    public SetBrushVegetationTypeCommand(EditorController editorController, VegetationType vegetationType) {
        this.editorController = editorController;
        this.vegetationType = vegetationType;
    }

    public CommandResult execute() {
        TileType tileType = editorController.getEditorModel().getBrush().getTileType();
        if (vegetationType == null || vegetationType.canGrowOnTileType(tileType)) {
            editorController.getEditorModel().getBrush().setVegetationType(vegetationType);
            editorController.updateEditorFrame();
        }
        return null;
    }

}

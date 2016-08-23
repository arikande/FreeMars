package org.freemars.editor.controller.command;

import org.freemars.editor.controller.EditorController;
import org.freerealm.command.AbstractCommand;
import org.freerealm.executor.CommandResult;
import org.freerealm.resource.bonus.BonusResource;
import org.freerealm.tile.TileType;
import org.freerealm.vegetation.VegetationType;

/**
 *
 * @author Deniz ARIKAN
 */
public class SetBrushTileTypeCommand extends AbstractCommand {

    private final EditorController editorController;
    private final TileType tileType;

    public SetBrushTileTypeCommand(EditorController editorController, TileType tileType) {
        this.editorController = editorController;
        this.tileType = tileType;
    }

    public CommandResult execute() {
        editorController.getEditorModel().getBrush().setTileType(tileType);
        VegetationType currentVegetationType = editorController.getEditorModel().getBrush().getVegetationType();
        if (currentVegetationType != null && !currentVegetationType.canGrowOnTileType(tileType)) {
            editorController.execute(new SetBrushVegetationTypeCommand(editorController, null));
        }
        BonusResource bonusResource = editorController.getEditorModel().getBrush().getBonusResource();
        if (bonusResource != null && !bonusResource.hasTileType(tileType)) {
            editorController.execute(new SetBrushBonusResourceCommand(editorController, null));
        }
        editorController.updateEditorFrame();
        return null;
    }

}

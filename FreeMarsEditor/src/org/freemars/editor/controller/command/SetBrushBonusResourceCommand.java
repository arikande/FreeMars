package org.freemars.editor.controller.command;

import org.freemars.editor.controller.EditorController;
import org.freerealm.command.AbstractCommand;
import org.freerealm.executor.CommandResult;
import org.freerealm.resource.bonus.BonusResource;
import org.freerealm.tile.TileType;

/**
 *
 * @author Deniz ARIKAN
 */
public class SetBrushBonusResourceCommand extends AbstractCommand {

    private final EditorController editorController;
    private final BonusResource bonusResource;

    public SetBrushBonusResourceCommand(EditorController editorController, BonusResource bonusResource) {
        this.editorController = editorController;
        this.bonusResource = bonusResource;
    }

    public CommandResult execute() {
        TileType tileType = editorController.getEditorModel().getBrush().getTileType();
        if (bonusResource == null || bonusResource.hasTileType(tileType)) {
            editorController.getEditorModel().getBrush().setBonusResource(bonusResource);
            editorController.updateEditorFrame();
        }
        return null;
    }

}

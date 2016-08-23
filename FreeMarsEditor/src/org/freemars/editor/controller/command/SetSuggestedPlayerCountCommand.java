package org.freemars.editor.controller.command;

import org.freemars.editor.controller.EditorController;
import org.freerealm.command.AbstractCommand;
import org.freerealm.executor.CommandResult;
import org.freerealm.map.Map;

/**
 *
 * @author Deniz ARIKAN
 */
public class SetSuggestedPlayerCountCommand extends AbstractCommand {

    private final EditorController editorController;
    private final int suggestedPlayerCount;

    public SetSuggestedPlayerCountCommand(EditorController editorController, int suggestedPlayerCount) {
        this.editorController = editorController;
        this.suggestedPlayerCount = suggestedPlayerCount;
    }

    public CommandResult execute() {
        Map map = editorController.getEditorModel().getRealm().getMap();
        int currentSuggestedPlayerCount = map.getSuggestedPlayers();
        if (suggestedPlayerCount != currentSuggestedPlayerCount) {
            map.setSuggestedPlayers(suggestedPlayerCount);
            editorController.getEditorModel().setFileDirty(true);
            editorController.updateEditorFrame();
        }
        return null;
    }

}

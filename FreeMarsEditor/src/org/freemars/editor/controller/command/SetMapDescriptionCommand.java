package org.freemars.editor.controller.command;

import org.freemars.editor.controller.EditorController;
import org.freerealm.command.AbstractCommand;
import org.freerealm.executor.CommandResult;
import org.freerealm.map.Map;

/**
 *
 * @author Deniz ARIKAN
 */
public class SetMapDescriptionCommand extends AbstractCommand {

    private final EditorController editorController;
    private final String description;

    public SetMapDescriptionCommand(EditorController editorController, String description) {
        this.editorController = editorController;
        this.description = description;
    }

    public CommandResult execute() {
        Map map = editorController.getEditorModel().getRealm().getMap();
        String currentDescription = map.getDescription();
        if (description != null && !description.equals(currentDescription)) {
            map.setDescription(description);
            editorController.getEditorModel().setFileDirty(true);
            editorController.updateEditorFrame();
        }
        return null;
    }

}

package org.freemars.editor.controller.command;

import org.freemars.editor.controller.EditorController;
import org.freerealm.command.AbstractCommand;
import org.freerealm.executor.CommandResult;
import org.freerealm.map.Map;

/**
 *
 * @author Deniz ARIKAN
 */
public class SetMapNameCommand extends AbstractCommand {

    private final EditorController editorController;
    private final String name;

    public SetMapNameCommand(EditorController editorController, String name) {
        this.editorController = editorController;
        this.name = name;
    }

    public CommandResult execute() {
        Map map = editorController.getEditorModel().getRealm().getMap();
        String currentName = map.getName();
        if (name != null && !name.equals(currentName)) {
            map.setName(name);
            editorController.getEditorModel().setFileDirty(true);
            editorController.updateEditorFrame();
        }
        return null;
    }

}

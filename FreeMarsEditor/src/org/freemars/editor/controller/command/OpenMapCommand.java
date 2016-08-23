package org.freemars.editor.controller.command;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.freemars.editor.controller.EditorController;
import org.freerealm.command.AbstractCommand;
import org.freerealm.command.ReadMapFileCommand;
import org.freerealm.executor.CommandResult;
import org.freerealm.map.Coordinate;

/**
 *
 * @author Deniz ARIKAN
 */
public class OpenMapCommand extends AbstractCommand {

    private final EditorController editorController;
    private final File file;

    public OpenMapCommand(EditorController editorController, File file) {
        this.editorController = editorController;
        this.file = file;
    }

    public CommandResult execute() {
        try {
            editorController.execute(new ReadMapFileCommand(editorController.getEditorModel().getRealm(), new FileInputStream(file)));
            editorController.getEditorModel().setOffsetCoordinate(new Coordinate());
            editorController.getEditorModel().setFileName(file.getAbsolutePath());
            editorController.getEditorModel().setFileDirty(false);
            editorController.updateEditorFrame();
        } catch (FileNotFoundException ex) {
        }
        return null;
    }

}

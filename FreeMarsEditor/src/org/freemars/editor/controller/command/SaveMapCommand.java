package org.freemars.editor.controller.command;

import java.io.File;
import java.io.FileWriter;

import org.freemars.editor.controller.EditorController;
import org.freerealm.command.AbstractCommand;
import org.freerealm.executor.CommandResult;
import org.freerealm.map.FreeRealmMap;
import org.freerealm.map.Map;
import org.freerealm.xmlwrapper.map.FreeRealmMapXMLConverter;

/**
 *
 * @author Deniz ARIKAN
 */
public class SaveMapCommand extends AbstractCommand {

    private final EditorController editorController;
    private final String fileName;

    public SaveMapCommand(EditorController editorController, String fileName) {
        this.editorController = editorController;
        this.fileName = fileName;
    }

    public CommandResult execute() {
        try {
            StringBuilder fileData = new StringBuilder();
            Map map = editorController.getEditorModel().getRealm().getMap();
            fileData.append((new FreeRealmMapXMLConverter()).toXML((FreeRealmMap) map));
            FileWriter fileWriter = new FileWriter(new File(fileName));
            fileWriter.write(fileData.toString());
            fileWriter.close();
            editorController.getEditorModel().setFileName(fileName);
            editorController.getEditorModel().setFileDirty(false);
            editorController.updateEditorFrame();
        } catch (Exception e) {
        }
        return null;
    }

}

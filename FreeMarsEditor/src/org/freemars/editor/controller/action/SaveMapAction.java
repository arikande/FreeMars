package org.freemars.editor.controller.action;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import org.freemars.editor.controller.EditorController;
import org.freemars.editor.controller.command.SaveMapCommand;
import org.freemars.ui.util.FreeMarsOptionPane;

/**
 *
 * @author Deniz ARIKAN
 */
public class SaveMapAction extends AbstractAction {

    private final EditorController editorController;

    public SaveMapAction(EditorController editorController) {
        super("Save map");
        this.editorController = editorController;
    }

    public void actionPerformed(ActionEvent e) {
        String fileName = editorController.getEditorModel().getFileName();
        if (fileName == null || fileName.trim().equals("")) {
            new SaveMapAsAction(editorController).actionPerformed(e);
        } else {
            editorController.execute(new SaveMapCommand(editorController, fileName));
            FreeMarsOptionPane.showMessageDialog(editorController.getEditorFrame(), "Save complete");
        }
    }
}

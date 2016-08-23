package org.freemars.editor.controller.action;

import java.awt.event.ActionEvent;
import java.io.File;
import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import org.freemars.editor.controller.EditorController;
import org.freemars.editor.controller.command.OpenMapCommand;
import org.freemars.ui.util.FRMFileChooser;

/**
 *
 * @author Deniz ARIKAN
 */
public class OpenMapAction extends AbstractAction {

    private final EditorController editorController;

    public OpenMapAction(EditorController editorController) {
        super("Open map");
        this.editorController = editorController;
    }

    public void actionPerformed(ActionEvent actionEvent) {
        if (editorController.getEditorModel().isFileDirty()) {
            int result = JOptionPane.showConfirmDialog(editorController.getEditorFrame(), "Current map is not saved. Do you wish to save it?", "Save current map", JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                new SaveMapAction(editorController).actionPerformed(actionEvent);
            }
        }
        FRMFileChooser frmFileChooser = new FRMFileChooser();
        if (frmFileChooser.showOpenDialog(editorController.getEditorFrame()) == JFileChooser.APPROVE_OPTION) {
            File file = frmFileChooser.getSelectedFile();
            editorController.execute(new OpenMapCommand(editorController, file));
        }

    }
}

package org.freemars.editor.controller.action;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JOptionPane;
import org.freemars.editor.controller.EditorController;

/**
 *
 * @author Deniz ARIKAN
 */
public class ExitToSystemAction extends AbstractAction {

    private final EditorController editorController;

    public ExitToSystemAction(EditorController editorController) {
        super("Exit editor");
        this.editorController = editorController;
    }

    public void actionPerformed(ActionEvent actionEvent) {
        if (editorController.getEditorModel().isFileDirty()) {
            int result = JOptionPane.showConfirmDialog(editorController.getEditorFrame(), "Current map is not saved. Do you wish to save it?", "Save current map", JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                new SaveMapAction(editorController).actionPerformed(actionEvent);
            }
        }
        Object[] options = {"Yes, exit", "No, thanks"};
        int value = JOptionPane.showOptionDialog(editorController.getEditorFrame(),
                "Really quit editor and exit to system?",
                "Quit game",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[1]);
        if (value == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }
}

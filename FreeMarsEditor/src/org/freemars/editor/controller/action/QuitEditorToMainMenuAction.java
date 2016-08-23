package org.freemars.editor.controller.action;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JOptionPane;
import org.freemars.editor.controller.EditorController;

/**
 *
 * @author Deniz ARIKAN
 */
public class QuitEditorToMainMenuAction extends AbstractAction {

    private final EditorController editorController;

    public QuitEditorToMainMenuAction(EditorController editorController) {
        super("Quit to main menu");
        this.editorController = editorController;
    }

    public void actionPerformed(ActionEvent actionEvent) {
        if (editorController.getEditorModel().isFileDirty()) {
            int result = JOptionPane.showConfirmDialog(editorController.getEditorFrame(), "Current map is not saved. Do you wish to save it?", "Save current map", JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                new SaveMapAction(editorController).actionPerformed(actionEvent);
            }
        }
        Object[] options = {"Yes", "No, thanks"};
        int value = JOptionPane.showOptionDialog(editorController.getEditorFrame(),
                "Quit editor and return to main menu?",
                "Quit editor",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[1]);
        if (value == JOptionPane.YES_OPTION) {
            editorController.getEditorFrame().dispose();
            editorController.getQuitEditorAction().actionPerformed(actionEvent);
        }
    }

}

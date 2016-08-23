package org.freemars.editor.controller.action;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import org.freemars.editor.controller.EditorController;

/**
 *
 * @author Deniz ARIKAN
 */
public class ToggleEditorMapDisplayGridAction extends AbstractAction {

    private final EditorController editorController;

    public ToggleEditorMapDisplayGridAction(EditorController editorController) {
        super("Display grid");
        this.editorController = editorController;
    }

    public void actionPerformed(ActionEvent actionEvent) {
        editorController.getEditorModel().setEditorMapDisplayingGridLines(!editorController.getEditorModel().isEditorMapDisplayingGridLines());
        editorController.updateEditorFrame();
    }
}

package org.freemars.editor.controller.action;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import org.freemars.editor.controller.EditorController;

/**
 *
 * @author Deniz ARIKAN
 */
public class ToggleEditorMapDisplayCoordinateAction extends AbstractAction {

    private final EditorController editorController;

    public ToggleEditorMapDisplayCoordinateAction(EditorController editorController) {
        super("Display coordinates");
        this.editorController = editorController;
    }

    public void actionPerformed(ActionEvent actionEvent) {
        editorController.getEditorModel().setEditorMapDisplayingCoordinates(!editorController.getEditorModel().isEditorMapDisplayingCoordinates());
        editorController.updateEditorFrame();
    }
}

package org.freemars.editor.controller.action;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import org.freemars.editor.controller.EditorController;

/**
 *
 * @author Deniz ARIKAN
 */
public class ToggleEditorMapDisplayTileTypeAction extends AbstractAction {

    private final EditorController editorController;

    public ToggleEditorMapDisplayTileTypeAction(EditorController editorController) {
        super("Display tile types");
        this.editorController = editorController;
    }

    public void actionPerformed(ActionEvent actionEvent) {
        editorController.getEditorModel().setEditorMapDisplayingTileTypes(!editorController.getEditorModel().isEditorMapDisplayingTileTypes());
        editorController.updateEditorFrame();
    }
}

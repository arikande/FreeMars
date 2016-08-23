package org.freemars.editor.controller.action;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import org.freemars.editor.model.EditorModel;
import org.freemars.editor.controller.EditorController;

/**
 *
 * @author Deniz ARIKAN
 */
public class EditorMapZoomInAction extends AbstractAction {

    private final EditorController editorController;

    public EditorMapZoomInAction(EditorController editorController) {
        super("Zoom in");
        this.editorController = editorController;
    }

    public void actionPerformed(ActionEvent e) {
        int currentZoom = editorController.getEditorModel().getEditorMapZoomLevel();
        if (currentZoom < EditorModel.EDITOR_PANEL_MAXIMUM_ZOOM_MULTIPLIER) {
            editorController.getEditorModel().setEditorMapZoomLevel(currentZoom + 1);
            editorController.updateEditorFrame();
        }
    }
}

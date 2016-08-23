package org.freemars.editor.controller.action;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import org.freemars.editor.model.EditorModel;
import org.freemars.editor.controller.EditorController;

/**
 *
 * @author Deniz ARIKAN
 */
public class EditorMapZoomOutAction extends AbstractAction {

    private final EditorController editorController;

    public EditorMapZoomOutAction(EditorController editorController) {
        super("Zoom out");
        this.editorController = editorController;
    }

    public void actionPerformed(ActionEvent e) {
        int currentZoom = editorController.getEditorModel().getEditorMapZoomLevel();
        if (currentZoom > EditorModel.EDITOR_PANEL_MINIMUM_ZOOM_MULTIPLIER) {
            editorController.getEditorModel().setEditorMapZoomLevel(currentZoom - 1);
            editorController.repaintEditorPanel();
            editorController.updateEditorFrame();
        }
    }
}

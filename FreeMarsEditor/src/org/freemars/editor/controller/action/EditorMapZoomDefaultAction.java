package org.freemars.editor.controller.action;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import org.freemars.editor.model.EditorModel;
import org.freemars.editor.controller.EditorController;

/**
 *
 * @author Deniz ARIKAN
 */
public class EditorMapZoomDefaultAction extends AbstractAction {

    private final EditorController editorController;

    public EditorMapZoomDefaultAction(EditorController editorController) {
        super("Default zoom");
        this.editorController = editorController;
    }

    public void actionPerformed(ActionEvent e) {
        editorController.getEditorModel().setEditorMapZoomLevel(EditorModel.EDITOR_PANEL_DEFAULT_ZOOM_MULTIPLIER);
        editorController.repaintEditorPanel();
    }
}

package org.freemars.editor.controller.listener;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import org.freemars.editor.controller.EditorController;
import org.freemars.editor.view.EditorFrame;

/**
 *
 * @author Deniz ARIKAN
 */
public class EditorFrameComponentAdapter extends ComponentAdapter {

    private final EditorController editorController;

    public EditorFrameComponentAdapter(EditorController editorController) {
        this.editorController = editorController;
    }

    @Override
    public void componentResized(ComponentEvent componentEvent) {
        EditorFrame editorFrame = editorController.getEditorFrame();
        if (editorFrame.getWidth() > 0 && editorFrame.getHeight() > 0) {
            editorController.repaintEditorPanel();
        }
    }
}

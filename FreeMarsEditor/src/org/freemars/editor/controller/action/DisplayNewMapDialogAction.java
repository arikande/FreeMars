package org.freemars.editor.controller.action;

import java.awt.event.ActionEvent;
import java.util.Iterator;
import javax.swing.AbstractAction;
import javax.swing.JOptionPane;
import org.freemars.editor.view.NewMapDialog;
import org.freemars.editor.controller.EditorController;
import org.freerealm.tile.TileType;

/**
 *
 * @author Deniz ARIKAN
 */
public class DisplayNewMapDialogAction extends AbstractAction {

    private final EditorController editorController;

    public DisplayNewMapDialogAction(EditorController editorController) {
        super("New map");
        this.editorController = editorController;
    }

    public void actionPerformed(ActionEvent actionEvent) {
        if (editorController.getEditorModel().isFileDirty()) {
            int result = JOptionPane.showConfirmDialog(editorController.getEditorFrame(), "Current map is not saved. Do you wish to save it?", "Save current map", JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                new SaveMapAction(editorController).actionPerformed(actionEvent);
            }
        }
        NewMapDialog newMapDialog = new NewMapDialog(editorController.getEditorFrame());

        newMapDialog.addTileTypeOption("Randomize");
        Iterator<TileType> iterator = editorController.getEditorModel().getRealm().getTileTypeManager().getTileTypesIterator();
        while (iterator.hasNext()) {
            TileType tileType = iterator.next();
            newMapDialog.addTileTypeOption(tileType.getName());
        }

        newMapDialog.setConfirmButtonAction(new ConfirmNewMapAction(editorController, newMapDialog));
        newMapDialog.setCancelButtonAction(new CloseDialogAction(newMapDialog));
        newMapDialog.display();

    }

}

package org.freemars.editor.controller.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import org.apache.log4j.Logger;
import org.freemars.editor.view.MapInformationDialog;
import org.freemars.editor.controller.EditorController;
import org.freemars.editor.controller.command.SetMapDescriptionCommand;
import org.freemars.editor.controller.command.SetMapNameCommand;
import org.freemars.editor.controller.command.SetSuggestedPlayerCountCommand;
import org.freerealm.command.ResizeMapCommand;

/**
 *
 * @author Deniz ARIKAN
 */
public class ConfirmMapInformationAction extends AbstractAction {
    
    private static final Logger logger = Logger.getLogger(ConfirmMapInformationAction.class);
    private final EditorController editorController;
    private final MapInformationDialog mapInformationDialog;
    
    public ConfirmMapInformationAction(EditorController editorController, MapInformationDialog mapInformationDialog) {
        super("Confirm");
        this.editorController = editorController;
        this.mapInformationDialog = mapInformationDialog;
    }
    
    public void actionPerformed(ActionEvent e) {
        editorController.execute(new SetMapNameCommand(editorController, mapInformationDialog.getMapNameTextFieldValue()));
        editorController.execute(new SetMapDescriptionCommand(editorController, mapInformationDialog.getMapDescriptionTextAreaValue()));
        editorController.execute(new SetSuggestedPlayerCountCommand(editorController, mapInformationDialog.getSuggestedPlayersSpinnerValue()));
        int mapWidth = mapInformationDialog.getMapWidthSpinnerValue();
        int mapHeight = mapInformationDialog.getMapHeightSpinnerValue();
        int currentMapWidth = editorController.getEditorModel().getRealm().getMapWidth();
        int currentMapHeight = editorController.getEditorModel().getRealm().getMapHeight();
        if (mapWidth != currentMapWidth || mapHeight != currentMapHeight) {
            logger.info("Resizing map to " + mapWidth + "x" + mapHeight);
            ResizeMapCommand resizeMapCommand = new ResizeMapCommand(editorController.getEditorModel().getRealm().getMap(), mapWidth, mapHeight);
            resizeMapCommand.setRealm(editorController.getEditorModel().getRealm());
            editorController.execute(resizeMapCommand);
            editorController.getEditorModel().setFileDirty(true);
            editorController.updateEditorFrame();
        }
        mapInformationDialog.dispose();
    }
}

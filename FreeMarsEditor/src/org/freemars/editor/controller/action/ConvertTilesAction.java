package org.freemars.editor.controller.action;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import org.freemars.editor.controller.EditorController;
import org.freemars.editor.controller.command.ConvertTilesCommand;
import org.freemars.editor.view.ConvertTilesDialog;
import org.freemars.ui.util.FreeMarsOptionPane;
import org.freerealm.executor.CommandResult;
import org.freerealm.tile.TileType;
import org.freerealm.vegetation.VegetationType;

/**
 *
 * @author Deniz ARIKAN
 */
public class ConvertTilesAction extends AbstractAction {

    private final EditorController editorController;
    private final ConvertTilesDialog convertTilesDialog;

    public ConvertTilesAction(EditorController editorController, ConvertTilesDialog convertTilesDialog) {
        super("Convert tiles");
        this.editorController = editorController;
        this.convertTilesDialog = convertTilesDialog;
    }

    public void actionPerformed(ActionEvent actionEvent) {
        int tileTypeId = convertTilesDialog.getFromTileTypeId();
        TileType fromTileType = editorController.getEditorModel().getRealm().getTileTypeManager().getTileType(tileTypeId);
        TileType toTileType = editorController.getEditorModel().getRealm().getTileTypeManager().getTileType(convertTilesDialog.getToTileTypeId());
        int fromVegetationTypeId = convertTilesDialog.getFromVegetationTypeId();
        VegetationType fromVegetationType = null;
        if (fromVegetationTypeId != -1) {
            fromVegetationType = editorController.getEditorModel().getRealm().getVegetationManager().getVegetationType(fromVegetationTypeId);
        }
        int toVegetationTypeId = convertTilesDialog.getToVegetationTypeId();
        VegetationType toVegetationType = null;
        if (toVegetationTypeId != -1) {
            toVegetationType = editorController.getEditorModel().getRealm().getVegetationManager().getVegetationType(toVegetationTypeId);
        }
        int numberOfTilesToConvert = convertTilesDialog.getNumberOfTilesToConvertSpinnerValue();
        ConvertTilesCommand convertTilesCommand
                = new ConvertTilesCommand(editorController.getEditorModel().getRealm(), fromTileType, fromVegetationType, toTileType, toVegetationType, numberOfTilesToConvert);
        CommandResult commandResult = editorController.execute(convertTilesCommand);
        if (commandResult.getCode() == CommandResult.RESULT_OK) {
            editorController.getEditorModel().setFileDirty(true);
            editorController.updateEditorFrame();
            int numberOfTilesConverted = Integer.parseInt(commandResult.getParameter("number_of_tiles_converted").toString());
            FreeMarsOptionPane.showMessageDialog(convertTilesDialog, numberOfTilesConverted + " tiles converted", "Tiles converted");
        }
    }

}

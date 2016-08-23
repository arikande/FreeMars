package org.freemars.editor.controller.action;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.util.Iterator;
import javax.swing.AbstractAction;
import javax.swing.AbstractButton;
import javax.swing.ImageIcon;
import javax.swing.JToggleButton;
import org.freemars.editor.controller.EditorController;
import org.freemars.editor.controller.listener.ConvertTilesDialogButtonListener;
import org.freemars.editor.view.ConvertTilesDialog;
import org.freemars.ui.image.FreeMarsImageManager;
import org.freerealm.resource.bonus.BonusResource;
import org.freerealm.tile.TileType;
import org.freerealm.vegetation.VegetationType;

/**
 *
 * @author Deniz ARIKAN
 */
public class DisplayConvertTilesDialogAction extends AbstractAction {

    private final EditorController editorController;
    private ConvertTilesDialogButtonListener convertTilesDialogButtonListener;

    public DisplayConvertTilesDialogAction(EditorController editorController) {
        super("Convert tiles");
        this.editorController = editorController;

    }

    public void actionPerformed(ActionEvent e) {
        ConvertTilesDialog convertTilesDialog = new ConvertTilesDialog(editorController.getEditorFrame());
        convertTilesDialogButtonListener = new ConvertTilesDialogButtonListener(convertTilesDialog);
        boolean firstConvertFromTileTypeButtonAdded = true;
        boolean firstConvertToTileTypeButtonAdded = true;
        Iterator<TileType> tileTypesIterator = editorController.getEditorModel().getRealm().getTileTypeManager().getTileTypesIterator();
        while (tileTypesIterator.hasNext()) {
            TileType tileType = tileTypesIterator.next();
            Image tileTypeImage = FreeMarsImageManager.getImage(tileType);
            tileTypeImage = FreeMarsImageManager.createResizedCopy(tileTypeImage, -1, 32, false, convertTilesDialog);
            JToggleButton convertFromTileTypeButton = new JToggleButton(tileType.getName(), new ImageIcon(tileTypeImage));
            convertFromTileTypeButton.setActionCommand("FROM.TILETYPE." + tileType.getId());
            convertFromTileTypeButton.addActionListener(convertTilesDialogButtonListener);
            if (firstConvertFromTileTypeButtonAdded) {
                convertFromTileTypeButton.setSelected(true);
                firstConvertFromTileTypeButtonAdded = false;
            }
            convertTilesDialog.addConvertFromTileTypeToggleButton(convertFromTileTypeButton);
            JToggleButton convertToTileTypeButton = new JToggleButton(tileType.getName(), new ImageIcon(tileTypeImage));
            convertToTileTypeButton.setActionCommand("TO.TILETYPE." + tileType.getId());
            convertToTileTypeButton.addActionListener(convertTilesDialogButtonListener);
            if (firstConvertToTileTypeButtonAdded) {
                convertToTileTypeButton.setSelected(true);
                firstConvertToTileTypeButtonAdded = false;
            }
            convertTilesDialog.addConvertToTileTypeToggleButton(convertToTileTypeButton);
        }

        Iterator<VegetationType> vegetationTypesIterator = editorController.getEditorModel().getRealm().getVegetationManager().getVegetationTypesIterator();
        while (vegetationTypesIterator.hasNext()) {
            VegetationType vegetationType = vegetationTypesIterator.next();
            Image image = FreeMarsImageManager.getImage(vegetationType);
            image = FreeMarsImageManager.createResizedCopy(image, -1, 32, false, convertTilesDialog);
            JToggleButton convertFromVegetationTypeButton = new JToggleButton(vegetationType.getName(), new ImageIcon(image));
            convertFromVegetationTypeButton.setActionCommand("FROM.VEGETATIONTYPE." + vegetationType.getId());
            convertFromVegetationTypeButton.addActionListener(convertTilesDialogButtonListener);
            convertTilesDialog.addConvertFromVegetationTypeToggleButton(convertFromVegetationTypeButton);
            JToggleButton convertToVegetationTypeButton = new JToggleButton(vegetationType.getName(), new ImageIcon(image));
            convertToVegetationTypeButton.setActionCommand("TO.VEGETATIONTYPE." + vegetationType.getId());
            convertToVegetationTypeButton.addActionListener(convertTilesDialogButtonListener);
            convertTilesDialog.addConvertToVegetationTypeToggleButton(convertToVegetationTypeButton);
        }
        Image selectNoneImage = FreeMarsImageManager.getImage("SELECT_NONE", 32, 32);
        JToggleButton convertFromNoVegetationTypeButton = new JToggleButton("None", new ImageIcon(selectNoneImage));
        convertFromNoVegetationTypeButton.setActionCommand("FROM.VEGETATIONTYPE.-1");
        convertFromNoVegetationTypeButton.addActionListener(convertTilesDialogButtonListener);
        convertFromNoVegetationTypeButton.setSelected(true);
        convertTilesDialog.addConvertFromVegetationTypeToggleButton(convertFromNoVegetationTypeButton);

        JToggleButton convertToNoVegetationTypeButton = new JToggleButton("None", new ImageIcon(selectNoneImage));
        convertToNoVegetationTypeButton.setActionCommand("TO.VEGETATIONTYPE.-1");
        convertToNoVegetationTypeButton.addActionListener(convertTilesDialogButtonListener);
        convertToNoVegetationTypeButton.setSelected(true);
        convertTilesDialog.addConvertToVegetationTypeToggleButton(convertToNoVegetationTypeButton);

        Iterator<BonusResource> bonusResourcesIterator = editorController.getEditorModel().getRealm().getBonusResourceManager().getBonusResourcesIterator();
        while (bonusResourcesIterator.hasNext()) {
            BonusResource bonusResource = bonusResourcesIterator.next();
            Image image = FreeMarsImageManager.getImage(bonusResource);
            image = FreeMarsImageManager.createResizedCopy(image, -1, 32, false, convertTilesDialog);
            JToggleButton convertFromBonusResourceTypeButton = new JToggleButton(bonusResource.getName(), new ImageIcon(image));
            convertTilesDialog.addConvertFromBonusResourceTypeToggleButton(convertFromBonusResourceTypeButton);
            JToggleButton convertToBonusResourceTypeButton = new JToggleButton(bonusResource.getName(), new ImageIcon(image));
            convertTilesDialog.addConvertToBonusResourceTypeToggleButton(convertToBonusResourceTypeButton);
        }

        convertTilesDialog.setConfirmButtonAction(new ConvertTilesAction(editorController, convertTilesDialog));
        convertTilesDialog.setCancelButtonAction(new CloseDialogAction(convertTilesDialog));
        convertTilesDialog.display();
    }
}

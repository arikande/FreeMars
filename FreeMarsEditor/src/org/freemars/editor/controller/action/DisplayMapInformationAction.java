package org.freemars.editor.controller.action;

import java.awt.event.ActionEvent;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.swing.AbstractAction;
import org.freemars.editor.view.MapInformationDialog;
import org.freemars.editor.controller.EditorController;
import org.freerealm.map.Coordinate;
import org.freerealm.tile.Tile;
import org.freerealm.tile.TileType;
import org.freerealm.vegetation.VegetationType;

/**
 *
 * @author Deniz ARIKAN
 */
public class DisplayMapInformationAction extends AbstractAction {

    private final EditorController editorController;

    public DisplayMapInformationAction(EditorController editorController) {
        super("Map information");
        this.editorController = editorController;
    }

    public void actionPerformed(ActionEvent e) {
        MapInformationDialog dialog = new MapInformationDialog(editorController.getEditorFrame());
        String mapName = editorController.getEditorModel().getRealm().getMap().getName();
        if (mapName == null) {
            mapName = "";
        }
        dialog.setMapNameTextFieldValue(mapName);
        String mapDescription = editorController.getEditorModel().getRealm().getMap().getDescription();
        if (mapDescription == null) {
            mapDescription = "";
        }
        dialog.setMapDescriptionTextAreaValue(mapDescription);
        int mapWidth = editorController.getEditorModel().getRealm().getMap().getWidth();
        dialog.setMapWidthSpinnerValue(mapWidth);
        int mapHeight = editorController.getEditorModel().getRealm().getMap().getHeight();
        dialog.setMapHeightSpinnerValue(mapHeight);
        int suggestedPlayers = editorController.getEditorModel().getRealm().getMap().getSuggestedPlayers();
        dialog.setSuggestedPlayersSpinnerValue(suggestedPlayers);
        dialog.setConfirmButtonAction(new ConfirmMapInformationAction(editorController, dialog));
        dialog.setCancelButtonAction(new CloseDialogAction(dialog));
        int tileCount = mapWidth * mapHeight;
        dialog.addDetailInformation("Tile count", tileCount);
        dialog.addDetailInformation("Tiles per player", tileCount / suggestedPlayers);

        int vegetationTileCount = 0;
        Map<Integer, Integer> tileTypeCountMap = new HashMap<Integer, Integer>();
        Map<Integer, Integer> vegetationTypeCountMap = new HashMap<Integer, Integer>();
        for (int i = 0; i < mapWidth; i++) {
            for (int j = 0; j < mapHeight; j++) {
                Tile tile = editorController.getEditorModel().getRealm().getTile(new Coordinate(i, j));
                int tileTypeId = tile.getType().getId();
                if (tileTypeCountMap.containsKey(tileTypeId)) {
                    int currentCount = tileTypeCountMap.get(tileTypeId);
                    tileTypeCountMap.put(tileTypeId, currentCount + 1);
                } else {
                    tileTypeCountMap.put(tileTypeId, 1);
                }
                if (tile.getVegetation() != null) {
                    vegetationTileCount = vegetationTileCount + 1;
                    int vegetationTypeId = tile.getVegetation().getType().getId();
                    if (vegetationTypeCountMap.containsKey(vegetationTypeId)) {
                        int currentCount = vegetationTypeCountMap.get(vegetationTypeId);
                        vegetationTypeCountMap.put(vegetationTypeId, currentCount + 1);
                    } else {
                        vegetationTypeCountMap.put(vegetationTypeId, 1);
                    }
                }
            }
        }

        Iterator<TileType> tileTypesIterator = editorController.getEditorModel().getRealm().getTileTypeManager().getTileTypesIterator();
        while (tileTypesIterator.hasNext()) {
            TileType tileType = tileTypesIterator.next();
            int count = 0;
            if (tileTypeCountMap.containsKey(tileType.getId())) {
                count = tileTypeCountMap.get(tileType.getId());
            }
            dialog.addDetailInformation(tileType.getName() + " tiles", count);
            double percent = (double) (count * 100) / tileCount;
            NumberFormat formatter = new DecimalFormat();
            dialog.addDetailInformation(tileType.getName() + " %", formatter.format(percent));
        }

        dialog.addDetailInformation("Vegetation count", vegetationTileCount);
        double vegetationPercent = (double) (vegetationTileCount * 100) / tileCount;
        NumberFormat formatter = new DecimalFormat();
        dialog.addDetailInformation("Vegetation %", formatter.format(vegetationPercent));

        Iterator<VegetationType> vegetationTypesIterator = editorController.getEditorModel().getRealm().getVegetationManager().getVegetationTypesIterator();
        while (vegetationTypesIterator.hasNext()) {
            VegetationType vegetationType = vegetationTypesIterator.next();
            int count = 0;
            if (vegetationTypeCountMap.containsKey(vegetationType.getId())) {
                count = vegetationTypeCountMap.get(vegetationType.getId());
            }
            dialog.addDetailInformation(vegetationType.getName(), count);
            double percent = (double) (count * 100) / tileCount;
            dialog.addDetailInformation(vegetationType.getName() + " %", formatter.format(percent));
        }

        dialog.display();
    }
}

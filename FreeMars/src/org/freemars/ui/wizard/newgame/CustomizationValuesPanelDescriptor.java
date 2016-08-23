package org.freemars.ui.wizard.newgame;

import com.nexes.wizard.WizardPanelDescriptor;
import java.util.HashMap;
import java.util.Iterator;
import javax.swing.JSlider;
import org.freerealm.tile.TileType;
import org.freerealm.tile.TileTypeManager;

public class CustomizationValuesPanelDescriptor extends WizardPanelDescriptor {

    public static final String IDENTIFIER = "CUSTOMIZATION_VALUES_PANEL";

    public CustomizationValuesPanelDescriptor(TileTypeManager tileTypeManager) {
        super(IDENTIFIER, new CustomizationValuesPanel(tileTypeManager));
    }

    @Override
    public Object getNextPanelDescriptor() {
        return ChooseNationsPanelDescriptor.IDENTIFIER;
    }

    @Override
    public Object getBackPanelDescriptor() {
        return ChooseMapCustomizationPanelDescriptor.IDENTIFIER;
    }

    @Override
    public void aboutToHidePanel() {
        String mapWidthString = ((CustomizationValuesPanel) getPanelComponent()).getMapWidthSpinner().getValue().toString();
        int mapWidth = Integer.parseInt(mapWidthString);
        String mapHeightString = ((CustomizationValuesPanel) getPanelComponent()).getMapHeightSpinner().getValue().toString();
        int mapHeight = Integer.parseInt(mapHeightString);
        ((NewGameWizard) getWizard()).getNewGameOptions().setMapWidth(mapWidth);
        ((NewGameWizard) getWizard()).getNewGameOptions().setMapHeight(mapHeight);
        ((NewGameWizard) getWizard()).getNewGameOptions().getTileTypes().clear();
        HashMap<JSlider, TileType> tileTypeSliders = ((CustomizationValuesPanel) getPanelComponent()).getTileTypeSliders();
        Iterator<JSlider> iterator = tileTypeSliders.keySet().iterator();
        while (iterator.hasNext()) {
            JSlider jSlider = iterator.next();
            TileType tileType = tileTypeSliders.get(jSlider);
            int sliderValue = jSlider.getValue();
            ((NewGameWizard) getWizard()).getNewGameOptions().getTileTypes().put(tileType, sliderValue);
        }
    }
}

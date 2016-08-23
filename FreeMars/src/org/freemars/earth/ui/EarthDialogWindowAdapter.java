package org.freemars.earth.ui;

import java.awt.Image;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Iterator;
import java.util.Map.Entry;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;
import org.freemars.controller.FreeMarsController;
import org.freemars.earth.EarthFlightProperty;
import org.freemars.earth.Location;
import org.freemars.ui.image.FreeMarsImageManager;
import org.freerealm.unit.Unit;

/**
 *
 * @author Deniz ARIKAN
 */
public class EarthDialogWindowAdapter extends WindowAdapter {

    private final FreeMarsController freeMarsController;
    private final EarthDialog earthDialog;

    public EarthDialogWindowAdapter(FreeMarsController freeMarsController, EarthDialog earthDialog) {
        this.freeMarsController = freeMarsController;
        this.earthDialog = earthDialog;
    }

    @Override
    public void windowClosing(WindowEvent e) {
        earthDialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        Unit spaceshipInEarthOrbit = null;
        Iterator<Entry<Unit, Location>> iterator = freeMarsController.getFreeMarsModel().getEarth().getUnitLocationsIterator();
        while (iterator.hasNext()) {
            Entry<Unit, Location> entry = iterator.next();
            Unit unit = entry.getKey();
            Location location = entry.getValue();
            if (unit.getPlayer().equals(freeMarsController.getFreeMarsModel().getRealm().getPlayerManager().getActivePlayer())) {
                if (location.equals(Location.EARTH)) {
                    EarthFlightProperty earthFlightProperty = (EarthFlightProperty) unit.getType().getProperty(EarthFlightProperty.NAME);
                    if (earthFlightProperty != null) {
                        spaceshipInEarthOrbit = unit;
                    }
                }
            }
        }
        if (spaceshipInEarthOrbit != null) {
            Image spaceshipInEarthOrbitImage = FreeMarsImageManager.getImage(spaceshipInEarthOrbit);
            spaceshipInEarthOrbitImage = FreeMarsImageManager.createResizedCopy(spaceshipInEarthOrbitImage, -1, 55, false, earthDialog);
            Object[] options = {"Yes", "No"};
            int value = JOptionPane.showOptionDialog(earthDialog,
                    "You have idle ships in Earth orbit. Do you wish to continue?",
                    "Idle ships in Earth orbit",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    new ImageIcon(spaceshipInEarthOrbitImage),
                    options,
                    options[1]);
            if (value != JOptionPane.YES_OPTION) {
                earthDialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
            }
        }
    }
}

package org.freemars.ui.wizard.newgame;

import com.nexes.wizard.WizardPanelDescriptor;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import javax.swing.JFileChooser;

import org.freemars.model.wizard.newgame.NewGameOptions;
import org.freemars.ui.util.FRMFileChooser;
import org.freemars.ui.util.FreeMarsOptionPane;
import org.freerealm.command.ReadMapFileCommand;
import org.freerealm.executor.CommandResult;

public class SelectPremadeMapPanelDescriptor extends WizardPanelDescriptor implements ActionListener {

    public static final String IDENTIFIER = "SELECT_PREMADE_MAP_PANEL";

    public SelectPremadeMapPanelDescriptor() {
        SelectPremadeMapPanel selectPremadeMapPanel = new SelectPremadeMapPanel();
        selectPremadeMapPanel.setActionListener(this);
        setPanelDescriptorIdentifier(IDENTIFIER);
        setPanelComponent(selectPremadeMapPanel);
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
    public void aboutToDisplayPanel() {
        setNextButtonAccordingToPremadePanel();
    }

    public void actionPerformed(ActionEvent e) {
        SelectPremadeMapPanel selectPremadeMapPanel = (SelectPremadeMapPanel) getPanelComponent();
        NewGameOptions newGameOptions = ((NewGameWizard) getWizard()).getNewGameOptions();
        String mapPath = newGameOptions.getPremadeMapPath();
        if (e.getActionCommand().equals("MARS_TINY")) {
            mapPath = "maps/mars_tiny.frm";
        } else if (e.getActionCommand().equals("MARS_SMALL")) {
            mapPath = "maps/mars_small.frm";
        } else if (e.getActionCommand().equals("MARS_MEDIUM")) {
            mapPath = "maps/mars_medium.frm";
        } else if (e.getActionCommand().equals("MARS_LARGE")) {
            mapPath = "maps/mars_large.frm";
        } else if (e.getActionCommand().equals("MARS_HUGE")) {
            mapPath = "maps/mars_huge.frm";
        } else if (e.getActionCommand().equals("MARS_GIANT")) {
            mapPath = "maps/mars_giant.frm";
        } else if (e.getActionCommand().equals("BROWSE")) {
            FRMFileChooser frmFileChooser = new FRMFileChooser();
            if (frmFileChooser.showOpenDialog((Component) e.getSource()) == JFileChooser.APPROVE_OPTION) {
                File file = frmFileChooser.getSelectedFile();
                mapPath = file.getAbsolutePath();
            }
        }
        if (mapPath != null) {
            InputStream inputStream;
            if (mapPath.startsWith("maps/")) {
                inputStream = ClassLoader.getSystemResourceAsStream(mapPath);
            } else {
                try {
                    inputStream = new FileInputStream(new File(mapPath));
                } catch (Exception exception) {
                    inputStream = null;
                }
            }

            selectPremadeMapPanel.setMapName("");
            selectPremadeMapPanel.setMapDescription("");
            selectPremadeMapPanel.setMapFileName("");
            selectPremadeMapPanel.setMapSize("");
            selectPremadeMapPanel.setSuggestedPlayers("");

            CommandResult result = newGameOptions.getFreeMarsController().execute(new ReadMapFileCommand(newGameOptions.getFreeMarsController().getFreeMarsModel().getRealm(), inputStream));
            if (result.getCode() == CommandResult.RESULT_OK) {
                newGameOptions.setMapWidth(newGameOptions.getFreeMarsController().getFreeMarsModel().getRealm().getMapWidth());
                newGameOptions.setMapHeight(newGameOptions.getFreeMarsController().getFreeMarsModel().getRealm().getMapHeight());
                String mapName = (String) result.getParameter("map_name");
                String mapDescription = (String) result.getParameter("map_description");
                selectPremadeMapPanel.setMapName(mapName);
                selectPremadeMapPanel.setMapDescription(mapDescription);
                selectPremadeMapPanel.setMapFileName(mapPath);
                selectPremadeMapPanel.setMapSize(result.getParameter("width") + "x" + result.getParameter("height"));
                int suggestedPlayers = Integer.parseInt(String.valueOf(result.getParameter("suggested_players")));
                if (suggestedPlayers > 0) {
                    selectPremadeMapPanel.setSuggestedPlayers(String.valueOf(suggestedPlayers));
                } else {
                    selectPremadeMapPanel.setSuggestedPlayers("Not specified");
                }
            } else {
                mapPath = null;
                FreeMarsOptionPane.showMessageDialog((Component) e.getSource(), "Selected map file is corrupt and can not be read", "File corrupt");
            }
        }
        newGameOptions.setPremadeMapPath(mapPath);

        setNextButtonAccordingToPremadePanel();
    }

    private void setNextButtonAccordingToPremadePanel() {
        NewGameOptions newGameOptions = ((NewGameWizard) getWizard()).getNewGameOptions();
        if (newGameOptions.getPremadeMapPath() == null || newGameOptions.getPremadeMapPath().trim().equals("")) {
            getWizard().setNextFinishButtonEnabled(false);
        } else {
            getWizard().setNextFinishButtonEnabled(true);
        }
    }
}

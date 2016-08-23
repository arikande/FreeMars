package org.freemars.ui.menu;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.MouseListener;
import java.util.List;
import javax.swing.Action;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;
import javax.swing.KeyStroke;
import org.freemars.model.FreeMarsModel;
import org.freemars.player.FreeMarsPlayer;
import org.freemars.ui.image.FreeMarsImageManager;
import org.freemars.ui.player.PlayerStatusInfoPanel;
import org.freerealm.history.PlayerHistory;
import org.freerealm.history.PlayerTurnHistory;
import org.freerealm.unit.Unit;

/**
 *
 * @author Deniz ARIKAN
 */
public class MapMenu extends JMenuBar {

    private final FreeMarsModel freeMarsModel;
    private JMenu fileMenu;
    private JMenuItem newGameMenuItem;
    private JMenuItem loadGameMenuItem;
    private JMenuItem quickLoadGameMenuItem;
    private JMenuItem saveGameMenuItem;
    private JMenuItem quickSaveGameMenuItem;
    private JMenuItem preferencesMenuItem;
    private JMenuItem quitToMainMenuItem;
    private JMenuItem exitGameMenuItem;
    private JMenu governmentMenu;
    private JMenuItem displayTaxRateMenuItem;
    private JMenuItem displayDipomacyMenuItem;
    private JMenuItem displayTradeMenuItem;
    private JMenuItem displayColonyManagerMenuItem;
    private JMenuItem displayUnitManagerMenuItem;
    private JMenuItem missionsMenuItem;
    private JMenuItem displayDemographicsMenuItem;
    private JMenuItem displayMessageHistoryMenuItem;
    private JMenuItem displayObjectivesMenuItem;
    private JMenuItem declareIndependenceMenuItem;
    private JMenu statisticsMenu;
    private JMenuItem populationStatisticsMenuItem;
    private JMenuItem treasuryStatisticsMenuItem;
    private JMenuItem earthTaxRateStatisticsMenuItem;
    private JMenuItem colonyStatisticsMenuItem;
    private JMenuItem unitStatisticsMenuItem;
    private JMenuItem mapExplorationStatisticsMenuItem;
    private JMenu viewMenu;
    private JCheckBoxMenuItem toggleGridCheckBoxMenuItem;
    private JCheckBoxMenuItem toggleTileCoordinatesCheckBoxMenuItem;
    private JCheckBoxMenuItem toggleTileTypesCheckBoxMenuItem;
    private JCheckBoxMenuItem toggleUnitPathCheckBoxMenuItem;
    private JMenuItem displayOrbitMenuItem;
    private JMenuItem displayEarthMenuItem;
    private JMenuItem displayExpeditionaryForceMenuItem;
    private JMenuItem mainMapZoomInMenuItem;
    private JMenuItem mainMapZoomOutMenuItem;
    private JMenuItem mainMapDefaultZoomMenuItem;
    private JMenu zoomLevelMenu;
    private JMenu ordersMenu;
    private JMenuItem endTurnMenuItem;
    private JMenuItem nextUnitMenuItem;
    private JMenuItem skipUnitMenuItem;
    private JMenuItem sendUnitToOrbitMenuItem;
    private JMenuItem sendUnitToEarthMenuItem;
    private JMenuItem clearVegetationMenuItem;
    private JMenuItem transformTerrainMenuItem;
    private JMenuItem unloadAllCargoMenuItem;
    private JMenuItem buildIrrigationMenuItem;
    private JMenuItem buildMineMenuItem;
    private JMenuItem buildRoadMenuItem;
    private JMenuItem clearOrdersMenuItem;
    private JMenuItem fortifyUnitMenuItem;
    private JMenuItem sentryUnitMenuItem;
    private JMenuItem disbandUnitMenuItem;
    private JMenuItem buildColonyMenuItem;
    private JMenu destroyTileImprovementMenu;
    private JMenuItem destroyAllTileImprovementsMenuItem;
    private JMenuItem destroyRoadsMenuItem;
    private JMenuItem destroyIrrigationMenuItem;
    private JMenuItem destroyMineMenuItem;
    private JCheckBoxMenuItem automateUnitMenuItem;
    private JMenuItem displayRenameUnitDialogMenuItem;
    private JMenu helpMenu;
    private JMenuItem displayHelpContentsMenuItem;
    private JMenuItem showVersionMenuItem;
    private JCheckBoxMenuItem[] zoomLevelMenuItems;
    private JButton displayOrbitButton;
    private JButton displayEarthButton;
    private PlayerStatusInfoPanel playerStatusInfoPanel;

    public MapMenu(FreeMarsModel viewModel) {
        this.freeMarsModel = viewModel;
        add(getFileMenu());
        add(getGovernmentMenu());
        add(getStatisticsMenu());
        add(getViewMenu());
        add(getOrdersMenu());
        add(getGameHelpMenu());
        add(Box.createHorizontalGlue());
        add(getDisplayOrbitButton());
        add(getDisplayEarthButton());
        add(getPlayerStatusInfoPanel());
    }

    public void update() {
        getToggleGridCheckBoxMenuItem().setSelected(freeMarsModel.getFreeMarsViewModel().isMapPanelDisplayingGrid());
        getToggleTileCoordinatesCheckBoxMenuItem().setSelected(freeMarsModel.getFreeMarsViewModel().isMapPanelDisplayingCoordinates());
        getToggleTileTypesCheckBoxMenuItem().setSelected(freeMarsModel.getFreeMarsViewModel().isMapPanelDisplayingTileTypes());
        getToggleUnitPathCheckBoxMenuItem().setSelected(freeMarsModel.getFreeMarsViewModel().isMapPanelDisplayingUnitPath());
        selectZoomLevelMenuItem(freeMarsModel.getFreeMarsViewModel().getMapPanelZoomLevel());
        FreeMarsPlayer player = (FreeMarsPlayer) freeMarsModel.getActivePlayer();
        if (player != null) {
            getPlayerStatusInfoPanel().setNationFlag(FreeMarsImageManager.getImage(player.getNation(), false, -1, 16));
            getPlayerStatusInfoPanel().setNationName(player.getNation().getName());
            getPlayerStatusInfoPanel().setPopulation(player.getPopulation());
            PlayerHistory playerHistory = freeMarsModel.getRealm().getHistory().getPlayerHistory(player);
            if (playerHistory != null) {
                int turn = freeMarsModel.getRealm().getNumberOfTurns();
                if (turn > 1) {
                    PlayerTurnHistory playerTurnHistory = playerHistory.getTurnHistory(turn - 2);
                    if (playerTurnHistory != null) {
                        int previousPopulation = playerTurnHistory.getPopulation();
                        if (player.getPopulation() > previousPopulation) {
                            getPlayerStatusInfoPanel().setPopulationChangeStatus(PlayerStatusInfoPanel.POPULATION_INCREASED);
                        } else if (player.getPopulation() == previousPopulation) {
                            getPlayerStatusInfoPanel().setPopulationChangeStatus(PlayerStatusInfoPanel.POPULATION_EQUAL);
                        } else if (player.getPopulation() < previousPopulation) {
                            getPlayerStatusInfoPanel().setPopulationChangeStatus(PlayerStatusInfoPanel.POPULATION_DECREASED);
                        }
                        int previousWealth = playerTurnHistory.getWealth();
                        if (player.getWealth() > previousWealth) {
                            getPlayerStatusInfoPanel().setTreasuryChangeStatus(PlayerStatusInfoPanel.TREASURY_INCREASED);
                        } else if (player.getWealth() == previousWealth) {
                            getPlayerStatusInfoPanel().setTreasuryChangeStatus(PlayerStatusInfoPanel.TREASURY_EQUAL);
                        } else if (player.getWealth() < previousWealth) {
                            getPlayerStatusInfoPanel().setTreasuryChangeStatus(PlayerStatusInfoPanel.TREASURY_DECREASED);
                        }
                    }
                } else {
                    if (player.getPopulation() > 0) {
                        getPlayerStatusInfoPanel().setPopulationChangeStatus(PlayerStatusInfoPanel.POPULATION_INCREASED);
                        getPlayerStatusInfoPanel().setTreasuryChangeStatus(PlayerStatusInfoPanel.TREASURY_INCREASED);
                    } else {
                        getPlayerStatusInfoPanel().setPopulationChangeStatus(PlayerStatusInfoPanel.POPULATION_EQUAL);
                        getPlayerStatusInfoPanel().setTreasuryChangeStatus(PlayerStatusInfoPanel.TREASURY_EQUAL);
                    }
                }
            }
            getPlayerStatusInfoPanel().setTreasury(player.getWealth());
            getPlayerStatusInfoPanel().setEarthTaxRate(player.getEarthTaxRate());
            getPlayerStatusInfoPanel().setTurns(freeMarsModel.getNumberOfTurns());
            Unit activeUnit = player.getActiveUnit();
            if (activeUnit != null) {
                if (activeUnit.getAutomater() == null) {
                    getAutomateUnitMenuItem().setSelected(false);
                } else {
                    getAutomateUnitMenuItem().setSelected(true);
                }
            }
        }
    }

    public void setNewGameMenuItemAction(Action action) {
        getNewGameMenuItem().setAction(action);
        getNewGameMenuItem().setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N,
                KeyEvent.CTRL_DOWN_MASK));
    }

    public void setSaveGameMenuItemAction(Action action) {
        getSaveGameMenuItem().setAction(action);
        getSaveGameMenuItem().setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S,
                KeyEvent.CTRL_DOWN_MASK));
    }

    public void setQuickSaveGameMenuItemAction(Action action) {
        getQuickSaveGameMenuItem().setAction(action);
        getQuickSaveGameMenuItem().setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F8,
                0));
    }

    public void setLoadGameMenuItemAction(Action action) {
        getLoadGameMenuItem().setAction(action);
        getLoadGameMenuItem().setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_L,
                KeyEvent.CTRL_DOWN_MASK));
    }

    public void setQuickLoadGameMenuItemAction(Action action) {
        getQuickLoadGameMenuItem().setAction(action);
        getQuickLoadGameMenuItem().setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F9,
                0));
    }

    public void setPreferencesMenuItemAction(Action action) {
        getPreferencesMenuItem().setAction(action);
    }

    public void setQuitToMainMenuItemAction(Action action) {
        getQuitToMainMenuItem().setAction(action);
    }

    public void setExitGameMenuItemAction(Action action) {
        getExitGameMenuItem().setAction(action);
        getExitGameMenuItem().setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Q,
                KeyEvent.CTRL_DOWN_MASK));
    }

    public void setDisplayColonyManagerMenuItemAction(Action action) {
        getDisplayColonyManagerMenuItem().setAction(action);
        getDisplayColonyManagerMenuItem().setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F2, 0));
    }

    public void setDisplayUnitManagerMenuItemAction(Action action) {
        getDisplayUnitManagerMenuItem().setAction(action);
        getDisplayUnitManagerMenuItem().setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F3, 0));
    }

    public void setMissionsMenuItemAction(Action action) {
        getMissionsMenuItem().setAction(action);
        getMissionsMenuItem().setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F4,
                0));
    }

    public void setDisplayTradeMenuItemAction(Action action) {
        getDisplayTradeMenuItem().setAction(action);
        getDisplayTradeMenuItem().setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0));
    }

    public void setDisplayDipomacyMenuItemAction(Action action) {
        getDisplayDipomacyMenuItem().setAction(action);
        getDisplayDipomacyMenuItem().setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F6, 0));
    }

    public void setDisplayTaxRateMenuItemAction(Action action) {
        getDisplayTaxRateMenuItem().setAction(action);
        getDisplayTaxRateMenuItem().setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T, KeyEvent.CTRL_DOWN_MASK));
    }

    public void setDisplayDemographicsMenuItemAction(Action action) {
        getDisplayDemographicsMenuItem().setAction(action);
    }

    public void setDisplayMessageHistoryMenuItemAction(Action action) {
        getDisplayMessageHistoryMenuItem().setAction(action);
    }

    public void setDisplayObjectivesMenuItemAction(Action action) {
        getDisplayObjectivesMenuItem().setAction(action);
    }

    public void setDeclareIndependenceMenuItemAction(Action action) {
        getDeclareIndependenceMenuItem().setAction(action);
    }

    public void setPopulationStatisticsMenuItemAction(Action action) {
        getPopulationStatisticsMenuItem().setAction(action);
    }

    public void setTreasuryStatisticsMenuItemAction(Action action) {
        getTreasuryStatisticsMenuItem().setAction(action);
    }

    public void setEarthTaxRateStatisticsMenuItemAction(Action action) {
        getEarthTaxRateStatisticsMenuItem().setAction(action);
    }

    public void setColonyStatisticsMenuItemAction(Action action) {
        getColonyStatisticsMenuItem().setAction(action);
    }

    public void setUnitStatisticsMenuItemAction(Action action) {
        getUnitStatisticsMenuItem().setAction(action);
    }

    public void setMapExplorationStatisticsMenuItemAction(Action action) {
        getMapExplorationStatisticsMenuItem().setAction(action);
    }

    public void setToggleGridCheckBoxMenuItemAction(Action action) {
        getToggleGridCheckBoxMenuItem().setAction(action);
        getToggleGridCheckBoxMenuItem().setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_G,
                KeyEvent.CTRL_DOWN_MASK));
    }

    public void setToggleTileCoordinatesCheckBoxMenuItemAction(Action action) {
        getToggleTileCoordinatesCheckBoxMenuItem().setAction(action);
        getToggleTileCoordinatesCheckBoxMenuItem().setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_D,
                KeyEvent.CTRL_DOWN_MASK));
    }

    public void setToggleTileTypesCheckBoxMenuItemAction(Action action) {
        getToggleTileTypesCheckBoxMenuItem().setAction(action);
    }

    public void setToggleUnitPathCheckBoxMenuItemAction(Action action) {
        getToggleUnitPathCheckBoxMenuItem().setAction(action);
    }

    public void setDisplayOrbitAction(Action action) {
        getDisplayOrbitMenuItem().setAction(action);
        getDisplayOrbitButton().setAction(action);
        getDisplayOrbitMenuItem().setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, 0));
        getDisplayOrbitButton().setIcon(new ImageIcon(FreeMarsImageManager.getImage("MARS", false, 16, 16)));
    }

    public void setDisplayEarthAction(Action action) {
        getDisplayEarthMenuItem().setAction(action);
        getDisplayEarthButton().setAction(action);
        getDisplayEarthMenuItem().setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, 0));
        getDisplayEarthButton().setIcon(new ImageIcon(FreeMarsImageManager.getImage("EARTH")));
    }

    public void setDisplayExpeditionaryForceAction(Action action) {
        getDisplayExpeditionaryForceMenuItem().setAction(action);
    }

    public void setMainMapZoomInMenuItemAction(Action action) {
        getMainMapZoomInMenuItem().setAction(action);
        getMainMapZoomInMenuItem().setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_ADD,
                0));
    }

    public void setMainMapZoomOutMenuItemAction(Action action) {
        getMainMapZoomOutMenuItem().setAction(action);
        getMainMapZoomOutMenuItem().setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_SUBTRACT,
                0));
    }

    public void setMainMapDefaultZoomMenuItemAction(Action action) {
        getMainMapDefaultZoomMenuItem().setAction(action);
        getMainMapDefaultZoomMenuItem().setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_MULTIPLY,
                0));
    }

    public void setZoomLevelMenuActions(List<Action> actionsList) {
        for (int i = 0; i < getZoomLevelMenuItems().length; i++) {
            getZoomLevelMenuItems()[i].setAction(actionsList.get(i));
        }
    }

    public void setDisplayHelpContentsMenuItemAction(Action action) {
        getDisplayHelpContentsMenuItem().setAction(action);
        getDisplayHelpContentsMenuItem().setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F1,
                0));
    }

    public void setShowVersionMenuItemAction(Action action) {
        getShowVersionMenuItem().setAction(action);
    }

    public void setBuildColonyMenuItemAction(Action action) {
        getBuildColonyMenuItem().setAction(action);
        getBuildColonyMenuItem().setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_B,
                0));
    }

    public void setDisbandUnitMenuItemAction(Action action) {
        getDisbandUnitMenuItem().setAction(action);
        getDisbandUnitMenuItem().setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_DELETE,
                0));
    }

    public void setSentryUnitMenuItemAction(Action action) {
        getSentryUnitMenuItem().setAction(action);
        getSentryUnitMenuItem().setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S,
                0));
    }

    public void setFortifyUnitMenuItemAction(Action action) {
        getFortifyUnitMenuItem().setAction(action);
        getFortifyUnitMenuItem().setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F,
                0));
    }

    public void setClearOrdersMenuItemAction(Action action) {
        getClearOrdersMenuItem().setAction(action);
    }

    public void setBuildRoadMenuItemAction(Action action) {
        getBuildRoadMenuItem().setAction(action);
        getBuildRoadMenuItem().setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_R, 0));
    }

    public void setBuildIrrigationMenuItemAction(Action action) {
        getBuildIrrigationMenuItem().setAction(action);
        getBuildIrrigationMenuItem().setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_I, 0));
    }

    public void setBuildMineMenuItemAction(Action action) {
        getBuildMineMenuItem().setAction(action);
        getBuildMineMenuItem().setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_M, 0));
    }

    public void setDestroyAllTileImprovementsMenuItemAction(Action action) {
        getDestroyAllTileImprovementsMenuItem().setAction(action);
    }

    public void setDestroyRoadsMenuItemAction(Action action) {
        getDestroyRoadsMenuItem().setAction(action);
    }

    public void setDestroyIrrigationMenuItemAction(Action action) {
        getDestroyIrrigationMenuItem().setAction(action);
    }

    public void setDestroyMineMenuItemAction(Action action) {
        getDestroyMineMenuItem().setAction(action);
    }

    public void setClearVegetationMenuItemAction(Action action) {
        getClearVegetationMenuItem().setAction(action);
        getClearVegetationMenuItem().setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_V, 0));
    }

    public void setTransformTerrainMenuItemAction(Action action) {
        getTransformTerrainMenuItem().setAction(action);
        getTransformTerrainMenuItem().setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_T, 0));
    }

    public void setUnloadAllCargoMenuItemAction(Action action) {
        getUnloadAllCargoMenuItem().setAction(action);
        getUnloadAllCargoMenuItem().setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_U,
                KeyEvent.CTRL_DOWN_MASK));
    }

    public void setSendUnitToOrbitMenuItemAction(Action action) {
        getSendUnitToOrbitMenuItem().setAction(action);
        getSendUnitToOrbitMenuItem().setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O,
                KeyEvent.CTRL_DOWN_MASK));
    }

    public void setSendUnitToEarthMenuItemAction(Action action) {
        getSendUnitToEarthMenuItem().setAction(action);
        getSendUnitToEarthMenuItem().setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E,
                KeyEvent.CTRL_DOWN_MASK));
    }

    public void setAutomateUnitMenuItemAction(Action action) {
        getAutomateUnitMenuItem().setAction(action);
        getAutomateUnitMenuItem().setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A,
                0));
    }

    public void setSkipUnitMenuItemAction(Action action) {
        getSkipUnitMenuItem().setAction(action);
        getSkipUnitMenuItem().setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_SPACE,
                0));
    }

    public void setNextUnitMenuItemAction(Action action) {
        getNextUnitMenuItem().setAction(action);
        getNextUnitMenuItem().setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N,
                0));
    }

    public void setDisplayRenameUnitDialogMenuItemAction(Action action) {
        getDisplayRenameUnitDialogMenuItem().setAction(action);
        getDisplayRenameUnitDialogMenuItem().setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_R,
                KeyEvent.CTRL_DOWN_MASK));
    }

    public void setEndTurnMenuItemAction(Action action) {
        getEndTurnMenuItem().setAction(action);
    }

    public void addNationLabelMouseListener(MouseListener mouseListener) {
        getPlayerStatusInfoPanel().addNationLabelMouseListener(mouseListener);
    }

    public void addPopulationLabelMouseListener(MouseListener mouseListener) {
        getPlayerStatusInfoPanel().addPopulationLabelMouseListener(mouseListener);
    }

    public void addEarthTaxRateLabelMouseListener(MouseListener mouseListener) {
        getPlayerStatusInfoPanel().addEarthTaxRateLabelMouseListener(mouseListener);
    }

    public void addTreasuryLabelMouseListener(MouseListener mouseListener) {
        getPlayerStatusInfoPanel().addTreasuryLabelLabelMouseListener(mouseListener);
    }

    // <editor-fold defaultstate="collapsed" desc="File Menu">
    private JMenu getFileMenu() {
        if (fileMenu == null) {
            fileMenu = new JMenu("File");
            fileMenu.setMnemonic(new Integer(KeyEvent.VK_F));
            fileMenu.add(getNewGameMenuItem());
            fileMenu.add(getSaveGameMenuItem());
            fileMenu.add(getQuickSaveGameMenuItem());
            fileMenu.add(getLoadGameMenuItem());
            fileMenu.add(getQuickLoadGameMenuItem());
            fileMenu.add(getPreferencesMenuItem());
            fileMenu.add(getQuitToMainMenuItem());
            fileMenu.add(getExitGameMenuItem());
        }
        return fileMenu;
    }

    private JMenuItem getNewGameMenuItem() {
        if (newGameMenuItem == null) {
            newGameMenuItem = new JMenuItem();
        }
        return newGameMenuItem;
    }

    private JMenuItem getLoadGameMenuItem() {
        if (loadGameMenuItem == null) {
            loadGameMenuItem = new JMenuItem();
        }
        return loadGameMenuItem;
    }

    private JMenuItem getQuickLoadGameMenuItem() {
        if (quickLoadGameMenuItem == null) {
            quickLoadGameMenuItem = new JMenuItem();
        }
        return quickLoadGameMenuItem;
    }

    private JMenuItem getSaveGameMenuItem() {
        if (saveGameMenuItem == null) {
            saveGameMenuItem = new JMenuItem();
        }
        return saveGameMenuItem;
    }

    private JMenuItem getQuickSaveGameMenuItem() {
        if (quickSaveGameMenuItem == null) {
            quickSaveGameMenuItem = new JMenuItem();
        }
        return quickSaveGameMenuItem;
    }

    private JMenuItem getPreferencesMenuItem() {
        if (preferencesMenuItem == null) {
            preferencesMenuItem = new JMenuItem();
        }
        return preferencesMenuItem;
    }

    private JMenuItem getQuitToMainMenuItem() {
        if (quitToMainMenuItem == null) {
            quitToMainMenuItem = new JMenuItem();
        }
        return quitToMainMenuItem;
    }

    private JMenuItem getExitGameMenuItem() {
        if (exitGameMenuItem == null) {
            exitGameMenuItem = new JMenuItem();
        }
        return exitGameMenuItem;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Government Menu">
    private JMenu getGovernmentMenu() {
        if (governmentMenu == null) {
            governmentMenu = new JMenu("Government");
            governmentMenu.setMnemonic(new Integer(KeyEvent.VK_G));
            governmentMenu.add(getDisplayColonyManagerMenuItem());
            governmentMenu.add(getDisplayUnitManagerMenuItem());
            governmentMenu.add(getMissionsMenuItem());
            governmentMenu.add(getDisplayTradeMenuItem());
            governmentMenu.add(getDisplayDipomacyMenuItem());
            governmentMenu.add(getDisplayTaxRateMenuItem());
            governmentMenu.add(getDisplayDemographicsMenuItem());
            governmentMenu.add(getDisplayMessageHistoryMenuItem());
            governmentMenu.add(getDisplayObjectivesMenuItem());
            governmentMenu.add(new JSeparator());
            governmentMenu.add(getDeclareIndependenceMenuItem());
        }
        return governmentMenu;
    }

    private JMenuItem getDisplayColonyManagerMenuItem() {
        if (displayColonyManagerMenuItem == null) {
            displayColonyManagerMenuItem = new JMenuItem();
        }
        return displayColonyManagerMenuItem;
    }

    private JMenuItem getDisplayUnitManagerMenuItem() {
        if (displayUnitManagerMenuItem == null) {
            displayUnitManagerMenuItem = new JMenuItem();
        }
        return displayUnitManagerMenuItem;
    }

    private JMenuItem getMissionsMenuItem() {
        if (missionsMenuItem == null) {
            missionsMenuItem = new JMenuItem();
        }
        return missionsMenuItem;
    }

    private JMenuItem getDisplayDipomacyMenuItem() {
        if (displayDipomacyMenuItem == null) {
            displayDipomacyMenuItem = new JMenuItem();
        }
        return displayDipomacyMenuItem;
    }

    private JMenuItem getDisplayTradeMenuItem() {
        if (displayTradeMenuItem == null) {
            displayTradeMenuItem = new JMenuItem();
        }
        return displayTradeMenuItem;
    }

    private JMenuItem getDisplayTaxRateMenuItem() {
        if (displayTaxRateMenuItem == null) {
            displayTaxRateMenuItem = new JMenuItem();
        }
        return displayTaxRateMenuItem;
    }

    private JMenuItem getDisplayDemographicsMenuItem() {
        if (displayDemographicsMenuItem == null) {
            displayDemographicsMenuItem = new JMenuItem();
        }
        return displayDemographicsMenuItem;
    }

    private JMenuItem getDisplayObjectivesMenuItem() {
        if (displayObjectivesMenuItem == null) {
            displayObjectivesMenuItem = new JMenuItem();
        }
        return displayObjectivesMenuItem;
    }

    private JMenuItem getDisplayMessageHistoryMenuItem() {
        if (displayMessageHistoryMenuItem == null) {
            displayMessageHistoryMenuItem = new JMenuItem();
        }
        return displayMessageHistoryMenuItem;
    }

    private JMenuItem getDeclareIndependenceMenuItem() {
        if (declareIndependenceMenuItem == null) {
            declareIndependenceMenuItem = new JMenuItem();
            declareIndependenceMenuItem.setForeground(Color.red);
        }
        return declareIndependenceMenuItem;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Statistics Menu">
    private JMenu getStatisticsMenu() {
        if (statisticsMenu == null) {
            statisticsMenu = new JMenu("Statistics");
            statisticsMenu.setMnemonic(new Integer(KeyEvent.VK_S));
            statisticsMenu.add(getPopulationStatisticsMenuItem());
            statisticsMenu.add(getTreasuryStatisticsMenuItem());
            statisticsMenu.add(getEarthTaxRateStatisticsMenuItem());
            statisticsMenu.add(getColonyStatisticsMenuItem());
            statisticsMenu.add(getUnitStatisticsMenuItem());
            statisticsMenu.add(getMapExplorationStatisticsMenuItem());
        }
        return statisticsMenu;
    }

    private JMenuItem getPopulationStatisticsMenuItem() {
        if (populationStatisticsMenuItem == null) {
            populationStatisticsMenuItem = new JMenuItem();
        }
        return populationStatisticsMenuItem;
    }

    private JMenuItem getTreasuryStatisticsMenuItem() {
        if (treasuryStatisticsMenuItem == null) {
            treasuryStatisticsMenuItem = new JMenuItem();
        }
        return treasuryStatisticsMenuItem;
    }

    private JMenuItem getEarthTaxRateStatisticsMenuItem() {
        if (earthTaxRateStatisticsMenuItem == null) {
            earthTaxRateStatisticsMenuItem = new JMenuItem();
        }
        return earthTaxRateStatisticsMenuItem;
    }

    private JMenuItem getColonyStatisticsMenuItem() {
        if (colonyStatisticsMenuItem == null) {
            colonyStatisticsMenuItem = new JMenuItem();
        }
        return colonyStatisticsMenuItem;
    }

    private JMenuItem getUnitStatisticsMenuItem() {
        if (unitStatisticsMenuItem == null) {
            unitStatisticsMenuItem = new JMenuItem();
        }
        return unitStatisticsMenuItem;
    }

    private JMenuItem getMapExplorationStatisticsMenuItem() {
        if (mapExplorationStatisticsMenuItem == null) {
            mapExplorationStatisticsMenuItem = new JMenuItem();
        }
        return mapExplorationStatisticsMenuItem;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="View Menu">
    private JMenu getViewMenu() {
        if (viewMenu == null) {
            viewMenu = new JMenu("View");
            viewMenu.setMnemonic(new Integer(KeyEvent.VK_V));
            viewMenu.add(getToggleGridCheckBoxMenuItem());
            viewMenu.add(getToggleTileCoordinatesCheckBoxMenuItem());
            viewMenu.add(getToggleTileTypesCheckBoxMenuItem());
            viewMenu.add(getToggleUnitPathCheckBoxMenuItem());
            viewMenu.add(new JSeparator());
            viewMenu.add(getDisplayOrbitMenuItem());
            viewMenu.add(getDisplayEarthMenuItem());
            viewMenu.add(getDisplayExpeditionaryForceMenuItem());
            viewMenu.add(new JSeparator());
            viewMenu.add(getMainMapZoomInMenuItem());
            viewMenu.add(getMainMapZoomOutMenuItem());
            viewMenu.add(getMainMapDefaultZoomMenuItem());
            viewMenu.add(getZoomLevelMenu());
        }
        return viewMenu;
    }

    private JCheckBoxMenuItem getToggleGridCheckBoxMenuItem() {
        if (toggleGridCheckBoxMenuItem == null) {
            toggleGridCheckBoxMenuItem = new JCheckBoxMenuItem();
        }
        return toggleGridCheckBoxMenuItem;
    }

    private JCheckBoxMenuItem getToggleTileCoordinatesCheckBoxMenuItem() {
        if (toggleTileCoordinatesCheckBoxMenuItem == null) {
            toggleTileCoordinatesCheckBoxMenuItem = new JCheckBoxMenuItem();
        }
        return toggleTileCoordinatesCheckBoxMenuItem;
    }

    private JCheckBoxMenuItem getToggleTileTypesCheckBoxMenuItem() {
        if (toggleTileTypesCheckBoxMenuItem == null) {
            toggleTileTypesCheckBoxMenuItem = new JCheckBoxMenuItem();
        }
        return toggleTileTypesCheckBoxMenuItem;
    }

    private JCheckBoxMenuItem getToggleUnitPathCheckBoxMenuItem() {
        if (toggleUnitPathCheckBoxMenuItem == null) {
            toggleUnitPathCheckBoxMenuItem = new JCheckBoxMenuItem();
        }
        return toggleUnitPathCheckBoxMenuItem;
    }

    private JMenuItem getDisplayOrbitMenuItem() {
        if (displayOrbitMenuItem == null) {
            displayOrbitMenuItem = new JMenuItem();
        }
        return displayOrbitMenuItem;
    }

    private JMenuItem getDisplayEarthMenuItem() {
        if (displayEarthMenuItem == null) {
            displayEarthMenuItem = new JMenuItem();
        }
        return displayEarthMenuItem;
    }

    private JMenuItem getDisplayExpeditionaryForceMenuItem() {
        if (displayExpeditionaryForceMenuItem == null) {
            displayExpeditionaryForceMenuItem = new JMenuItem();
        }
        return displayExpeditionaryForceMenuItem;
    }

    private JMenuItem getMainMapZoomInMenuItem() {
        if (mainMapZoomInMenuItem == null) {
            mainMapZoomInMenuItem = new JMenuItem();
        }
        return mainMapZoomInMenuItem;
    }

    private JMenuItem getMainMapZoomOutMenuItem() {
        if (mainMapZoomOutMenuItem == null) {
            mainMapZoomOutMenuItem = new JMenuItem();
        }
        return mainMapZoomOutMenuItem;
    }

    private JMenuItem getMainMapDefaultZoomMenuItem() {
        if (mainMapDefaultZoomMenuItem == null) {
            mainMapDefaultZoomMenuItem = new JMenuItem();
        }
        return mainMapDefaultZoomMenuItem;
    }

    private JMenu getZoomLevelMenu() {
        zoomLevelMenu = new JMenu("Zoom level");
        for (int i = 0; i < getZoomLevelMenuItems().length; i++) {
            zoomLevelMenu.add(getZoomLevelMenuItems()[i]);
        }
        return zoomLevelMenu;
    }

    private JCheckBoxMenuItem[] getZoomLevelMenuItems() {
        if (zoomLevelMenuItems == null) {
            zoomLevelMenuItems = new JCheckBoxMenuItem[6];
            for (int i = 0; i < getZoomLevelMenuItems().length; i++) {
                zoomLevelMenuItems[i] = new JCheckBoxMenuItem();
            }
        }
        return zoomLevelMenuItems;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Orders Menu">
    private JMenu getOrdersMenu() {
        if (ordersMenu == null) {
            ordersMenu = new JMenu("Orders");
            ordersMenu.setMnemonic(new Integer(KeyEvent.VK_O));
            ordersMenu.add(getBuildColonyMenuItem());
            ordersMenu.add(getDisbandUnitMenuItem());
            ordersMenu.add(getSentryUnitMenuItem());
            ordersMenu.add(getFortifyUnitMenuItem());
            ordersMenu.add(getClearOrdersMenuItem());
            ordersMenu.add(new JSeparator());
            ordersMenu.add(getBuildRoadMenuItem());
            ordersMenu.add(getBuildIrrigationMenuItem());
            ordersMenu.add(getBuildMineMenuItem());
            ordersMenu.add(getDestroyTileImprovementMenu());
            ordersMenu.add(getClearVegetationMenuItem());
            ordersMenu.add(getTransformTerrainMenuItem());
            ordersMenu.add(getUnloadAllCargoMenuItem());
            ordersMenu.add(getSendUnitToOrbitMenuItem());
            ordersMenu.add(getSendUnitToEarthMenuItem());
            ordersMenu.add(getAutomateUnitMenuItem());
            ordersMenu.add(new JSeparator());
            ordersMenu.add(getSkipUnitMenuItem());
            ordersMenu.add(getNextUnitMenuItem());
            ordersMenu.add(getDisplayRenameUnitDialogMenuItem());
            ordersMenu.add(getEndTurnMenuItem());
        }
        return ordersMenu;
    }

    private JMenuItem getBuildColonyMenuItem() {
        if (buildColonyMenuItem == null) {
            buildColonyMenuItem = new JMenuItem();
        }
        return buildColonyMenuItem;
    }

    private JMenuItem getDisbandUnitMenuItem() {
        if (disbandUnitMenuItem == null) {
            disbandUnitMenuItem = new JMenuItem();
        }
        return disbandUnitMenuItem;
    }

    private JMenuItem getSentryUnitMenuItem() {
        if (sentryUnitMenuItem == null) {
            sentryUnitMenuItem = new JMenuItem();
        }
        return sentryUnitMenuItem;
    }

    private JMenuItem getFortifyUnitMenuItem() {
        if (fortifyUnitMenuItem == null) {
            fortifyUnitMenuItem = new JMenuItem();
        }
        return fortifyUnitMenuItem;
    }

    private JMenuItem getClearOrdersMenuItem() {
        if (clearOrdersMenuItem == null) {
            clearOrdersMenuItem = new JMenuItem();
        }
        return clearOrdersMenuItem;
    }

    private JMenuItem getBuildIrrigationMenuItem() {
        if (buildIrrigationMenuItem == null) {
            buildIrrigationMenuItem = new JMenuItem();
        }
        return buildIrrigationMenuItem;
    }

    private JMenuItem getBuildMineMenuItem() {
        if (buildMineMenuItem == null) {
            buildMineMenuItem = new JMenuItem();
        }
        return buildMineMenuItem;
    }

    private JMenuItem getBuildRoadMenuItem() {
        if (buildRoadMenuItem == null) {
            buildRoadMenuItem = new JMenuItem();
        }
        return buildRoadMenuItem;
    }

    private JMenu getDestroyTileImprovementMenu() {
        if (destroyTileImprovementMenu == null) {
            destroyTileImprovementMenu = new JMenu("Destroy");
            destroyTileImprovementMenu.add(getDestroyAllTileImprovementsMenuItem());
            destroyTileImprovementMenu.add(getDestroyRoadsMenuItem());
            destroyTileImprovementMenu.add(getDestroyIrrigationMenuItem());
            destroyTileImprovementMenu.add(getDestroyMineMenuItem());
        }
        return destroyTileImprovementMenu;
    }

    private JMenuItem getDestroyAllTileImprovementsMenuItem() {
        if (destroyAllTileImprovementsMenuItem == null) {
            destroyAllTileImprovementsMenuItem = new JMenuItem();
        }
        return destroyAllTileImprovementsMenuItem;
    }

    private JMenuItem getDestroyRoadsMenuItem() {
        if (destroyRoadsMenuItem == null) {
            destroyRoadsMenuItem = new JMenuItem();
        }
        return destroyRoadsMenuItem;
    }

    private JMenuItem getDestroyIrrigationMenuItem() {
        if (destroyIrrigationMenuItem == null) {
            destroyIrrigationMenuItem = new JMenuItem();
        }
        return destroyIrrigationMenuItem;
    }

    private JMenuItem getDestroyMineMenuItem() {
        if (destroyMineMenuItem == null) {
            destroyMineMenuItem = new JMenuItem();
        }
        return destroyMineMenuItem;
    }

    private JMenuItem getClearVegetationMenuItem() {
        if (clearVegetationMenuItem == null) {
            clearVegetationMenuItem = new JMenuItem();
        }
        return clearVegetationMenuItem;
    }

    private JMenuItem getTransformTerrainMenuItem() {
        if (transformTerrainMenuItem == null) {
            transformTerrainMenuItem = new JMenuItem();
        }
        return transformTerrainMenuItem;
    }

    private JMenuItem getUnloadAllCargoMenuItem() {
        if (unloadAllCargoMenuItem == null) {
            unloadAllCargoMenuItem = new JMenuItem();
        }
        return unloadAllCargoMenuItem;
    }

    private JMenuItem getSkipUnitMenuItem() {
        if (skipUnitMenuItem == null) {
            skipUnitMenuItem = new JMenuItem();
        }
        return skipUnitMenuItem;
    }

    private JMenuItem getSendUnitToEarthMenuItem() {
        if (sendUnitToEarthMenuItem == null) {
            sendUnitToEarthMenuItem = new JMenuItem();
        }
        return sendUnitToEarthMenuItem;
    }

    private JCheckBoxMenuItem getAutomateUnitMenuItem() {
        if (automateUnitMenuItem == null) {
            automateUnitMenuItem = new JCheckBoxMenuItem();
        }
        return automateUnitMenuItem;
    }

    private JMenuItem getSendUnitToOrbitMenuItem() {
        if (sendUnitToOrbitMenuItem == null) {
            sendUnitToOrbitMenuItem = new JMenuItem();
        }
        return sendUnitToOrbitMenuItem;
    }

    private JMenuItem getNextUnitMenuItem() {
        if (nextUnitMenuItem == null) {
            nextUnitMenuItem = new JMenuItem();
        }
        return nextUnitMenuItem;
    }

    private JMenuItem getDisplayRenameUnitDialogMenuItem() {
        if (displayRenameUnitDialogMenuItem == null) {
            displayRenameUnitDialogMenuItem = new JMenuItem();
        }
        return displayRenameUnitDialogMenuItem;
    }

    private JMenuItem getEndTurnMenuItem() {
        if (endTurnMenuItem == null) {
            endTurnMenuItem = new JMenuItem();
        }
        return endTurnMenuItem;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Help Menu">
    private JMenu getGameHelpMenu() {
        if (helpMenu == null) {
            helpMenu = new JMenu("Help");
            helpMenu.setMnemonic(new Integer(KeyEvent.VK_H));
            helpMenu.add(getDisplayHelpContentsMenuItem());
            helpMenu.add(getShowVersionMenuItem());
        }
        return helpMenu;
    }

    private JMenuItem getDisplayHelpContentsMenuItem() {
        if (displayHelpContentsMenuItem == null) {
            displayHelpContentsMenuItem = new JMenuItem();
        }
        return displayHelpContentsMenuItem;
    }

    private JMenuItem getShowVersionMenuItem() {
        if (showVersionMenuItem == null) {
            showVersionMenuItem = new JMenuItem();
        }
        return showVersionMenuItem;
    }
    // </editor-fold>

    private void selectZoomLevelMenuItem(int index) {
        for (int i = 0; i < getZoomLevelMenuItems().length; i++) {
            getZoomLevelMenuItems()[i].setSelected(false);
        }
        getZoomLevelMenuItems()[index].setSelected(true);
        revalidate();
    }

    private JButton getDisplayOrbitButton() {
        if (displayOrbitButton == null) {
            displayOrbitButton = new JButton();
            displayOrbitButton.setFocusPainted(false);
        }
        return displayOrbitButton;
    }

    private JButton getDisplayEarthButton() {
        if (displayEarthButton == null) {
            displayEarthButton = new JButton();
            displayEarthButton.setFocusPainted(false);
        }
        return displayEarthButton;
    }

    private PlayerStatusInfoPanel getPlayerStatusInfoPanel() {
        if (playerStatusInfoPanel == null) {
            playerStatusInfoPanel = new PlayerStatusInfoPanel();
        }
        return playerStatusInfoPanel;
    }
}

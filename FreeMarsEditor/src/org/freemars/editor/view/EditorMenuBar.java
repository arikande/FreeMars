package org.freemars.editor.view;

import java.awt.event.KeyEvent;
import javax.swing.Action;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;
import javax.swing.KeyStroke;

/**
 *
 * @author Deniz ARIKAN
 */
public class EditorMenuBar extends JMenuBar {

    private JMenu fileMenu;
    private JMenuItem newMapMenuItem;
    private JMenuItem openMapMenuItem;
    private JMenuItem saveMapMenuItem;
    private JMenuItem saveMapAsMenuItem;
    private JMenuItem displayMapInformationMenuItem;
    private JMenuItem quitToMainMenuMenuItem;
    private JMenuItem exitToSystemMenuItem;
    private JMenu brushMenu;
    private JMenu tileTypeMenu;
    private JMenu vegetationMenu;
    private JMenu bonusResourceMenu;
    private JMenu toolsMenu;
    private JMenu viewMenu;
    private JCheckBoxMenuItem editorMapDisplayGridCheckBoxMenuItem;
    private JCheckBoxMenuItem editorMapDisplayCoordinateCheckBoxMenuItem;
    private JCheckBoxMenuItem editorMapDisplayTileTypeCheckBoxMenuItem;
    private JMenuItem editorMapZoomInMenuItem;
    private JMenuItem editorMapZoomOutMenuItem;
    private JMenuItem editorMapZoomDefaultMenuItem;
    private JMenuItem convertTilesMenuItem;

    public EditorMenuBar() {
        add(getFileMenu());
        add(getBrushMenu());
        add(getToolsMenu());
        add(getViewMenu());
    }

    public void clearTileTypeMenu() {
        getTileTypeMenu().removeAll();
    }

    public void addTileTypeMenuItem(JCheckBoxMenuItem menuItem) {
        getTileTypeMenu().add(menuItem);
    }

    public void clearVegetationMenu() {
        getVegetationMenu().removeAll();
    }

    public void addVegetationMenuItem(JCheckBoxMenuItem menuItem) {
        getVegetationMenu().add(menuItem);
    }

    public void clearBonusResourceMenu() {
        getBonusResourceMenu().removeAll();
    }

    public void addBonusResourceMenuItem(JCheckBoxMenuItem menuItem) {
        getBonusResourceMenu().add(menuItem);
    }

    public void setNewMapMenuItemAction(Action action) {
        getNewMapMenuItem().setAction(action);
        getNewMapMenuItem().setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, KeyEvent.CTRL_DOWN_MASK));
    }

    public void setOpenMapMenuItemAction(Action action) {
        getOpenMapMenuItem().setAction(action);
        getOpenMapMenuItem().setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, KeyEvent.CTRL_DOWN_MASK));
    }

    public void setSaveMapMenuItemAction(Action action) {
        getSaveMapMenuItem().setAction(action);
        getSaveMapMenuItem().setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK));
    }

    public void setSaveMapAsMenuItemAction(Action action) {
        getSaveMapAsMenuItem().setAction(action);
        getSaveMapAsMenuItem().setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, KeyEvent.CTRL_DOWN_MASK));
    }

    public void setDisplayMapInformationMenuItemAction(Action action) {
        getDisplayMapInformationMenuItem().setAction(action);
        getDisplayMapInformationMenuItem().setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_I, KeyEvent.CTRL_DOWN_MASK));
    }

    public void setQuitToMainMenuMenuItemAction(Action action) {
        getQuitToMainMenuMenuItem().setAction(action);
        getQuitToMainMenuMenuItem().setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Q, KeyEvent.CTRL_DOWN_MASK));
    }

    public void setExitToSystemMenuItemAction(Action action) {
        getExitToSystemMenuItem().setAction(action);
        getExitToSystemMenuItem().setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, KeyEvent.CTRL_DOWN_MASK));
    }

    public void setConvertTilesMenuItemAction(Action action) {
        getConvertTilesMenuItem().setAction(action);
    }

    public void setEditorMapZoomInMenuItemAction(Action action) {
        getEditorMapZoomInMenuItem().setAction(action);
        getEditorMapZoomInMenuItem().setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_ADD, 0));
    }

    public void setEditorMapZoomOutMenuItemAction(Action action) {
        getEditorMapZoomOutMenuItem().setAction(action);
        getEditorMapZoomOutMenuItem().setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_SUBTRACT, 0));
    }

    public void setEditorMapZoomDefaultMenuItemAction(Action action) {
        getEditorMapZoomDefaultMenuItem().setAction(action);
        getEditorMapZoomDefaultMenuItem().setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_MULTIPLY, 0));
    }

    public void setEditorMapDisplayGridCheckBoxMenuItemAction(Action action) {
        getEditorMapDisplayGridCheckBoxMenuItem().setAction(action);
        getEditorMapDisplayGridCheckBoxMenuItem().setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_G, KeyEvent.CTRL_DOWN_MASK));
    }

    public void setEditorMapDisplayGridCheckBoxMenuItemSelected(boolean selected) {
        getEditorMapDisplayGridCheckBoxMenuItem().setSelected(selected);
    }

    public void setEditorMapDisplayCoordinateCheckBoxMenuItemAction(Action action) {
        getEditorMapDisplayCoordinateCheckBoxMenuItem().setAction(action);
        getEditorMapDisplayCoordinateCheckBoxMenuItem().setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_D, KeyEvent.CTRL_DOWN_MASK));
    }

    public void setEditorMapDisplayCoordinateCheckBoxMenuItemSelected(boolean selected) {
        getEditorMapDisplayCoordinateCheckBoxMenuItem().setSelected(selected);
    }

    public void setEditorMapDisplayTileTypeCheckBoxMenuItemAction(Action action) {
        getEditorMapDisplayTileTypeCheckBoxMenuItem().setAction(action);
        getEditorMapDisplayTileTypeCheckBoxMenuItem().setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_T, KeyEvent.CTRL_DOWN_MASK));
    }

    public void setEditorMapDisplayTileTypeCheckBoxMenuItemSelected(boolean selected) {
        getEditorMapDisplayTileTypeCheckBoxMenuItem().setSelected(selected);
    }

    private JMenu getFileMenu() {
        if (fileMenu == null) {
            fileMenu = new JMenu("File");
            fileMenu.add(getNewMapMenuItem());
            fileMenu.add(getOpenMapMenuItem());
            fileMenu.add(getSaveMapMenuItem());
            fileMenu.add(getSaveMapAsMenuItem());
            fileMenu.add(getDisplayMapInformationMenuItem());
            fileMenu.add(getQuitToMainMenuMenuItem());
            fileMenu.add(getExitToSystemMenuItem());
        }
        return fileMenu;
    }

    private JMenuItem getNewMapMenuItem() {
        if (newMapMenuItem == null) {
            newMapMenuItem = new JMenuItem();
        }
        return newMapMenuItem;
    }

    private JMenuItem getOpenMapMenuItem() {
        if (openMapMenuItem == null) {
            openMapMenuItem = new JMenuItem();
        }
        return openMapMenuItem;
    }

    private JMenuItem getSaveMapMenuItem() {
        if (saveMapMenuItem == null) {
            saveMapMenuItem = new JMenuItem();
        }
        return saveMapMenuItem;
    }

    private JMenuItem getSaveMapAsMenuItem() {
        if (saveMapAsMenuItem == null) {
            saveMapAsMenuItem = new JMenuItem();
        }
        return saveMapAsMenuItem;
    }

    private JMenuItem getDisplayMapInformationMenuItem() {
        if (displayMapInformationMenuItem == null) {
            displayMapInformationMenuItem = new JMenuItem();
        }
        return displayMapInformationMenuItem;
    }

    private JMenuItem getQuitToMainMenuMenuItem() {
        if (quitToMainMenuMenuItem == null) {
            quitToMainMenuMenuItem = new JMenuItem();
        }
        return quitToMainMenuMenuItem;
    }

    private JMenuItem getExitToSystemMenuItem() {
        if (exitToSystemMenuItem == null) {
            exitToSystemMenuItem = new JMenuItem();
        }
        return exitToSystemMenuItem;
    }

    private JMenuItem getConvertTilesMenuItem() {
        if (convertTilesMenuItem == null) {
            convertTilesMenuItem = new JMenuItem();
        }
        return convertTilesMenuItem;
    }

    private JMenu getBrushMenu() {
        if (brushMenu == null) {
            brushMenu = new JMenu("Brush");
            brushMenu.add(getTileTypeMenu());
            brushMenu.add(getVegetationMenu());
            brushMenu.add(getBonusResourceMenu());
        }
        return brushMenu;
    }

    private JMenu getTileTypeMenu() {
        if (tileTypeMenu == null) {
            tileTypeMenu = new JMenu("Tile type");
        }
        return tileTypeMenu;
    }

    private JMenu getVegetationMenu() {
        if (vegetationMenu == null) {
            vegetationMenu = new JMenu("Vegetation");
        }
        return vegetationMenu;
    }

    private JMenu getBonusResourceMenu() {
        if (bonusResourceMenu == null) {
            bonusResourceMenu = new JMenu("Bonus resource");
        }
        return bonusResourceMenu;
    }

    private JMenu getToolsMenu() {
        if (toolsMenu == null) {
            toolsMenu = new JMenu("Tools");
            toolsMenu.add(getConvertTilesMenuItem());
        }
        return toolsMenu;
    }

    private JMenu getViewMenu() {
        if (viewMenu == null) {
            viewMenu = new JMenu("View");
            viewMenu.add(getEditorMapDisplayGridCheckBoxMenuItem());
            viewMenu.add(getEditorMapDisplayCoordinateCheckBoxMenuItem());
            viewMenu.add(getEditorMapDisplayTileTypeCheckBoxMenuItem());
            viewMenu.add(new JSeparator());
            viewMenu.add(getEditorMapZoomInMenuItem());
            viewMenu.add(getEditorMapZoomOutMenuItem());
            viewMenu.add(getEditorMapZoomDefaultMenuItem());
        }
        return viewMenu;
    }

    private JCheckBoxMenuItem getEditorMapDisplayGridCheckBoxMenuItem() {
        if (editorMapDisplayGridCheckBoxMenuItem == null) {
            editorMapDisplayGridCheckBoxMenuItem = new JCheckBoxMenuItem();
        }
        return editorMapDisplayGridCheckBoxMenuItem;
    }

    private JCheckBoxMenuItem getEditorMapDisplayCoordinateCheckBoxMenuItem() {
        if (editorMapDisplayCoordinateCheckBoxMenuItem == null) {
            editorMapDisplayCoordinateCheckBoxMenuItem = new JCheckBoxMenuItem();
        }
        return editorMapDisplayCoordinateCheckBoxMenuItem;
    }

    private JCheckBoxMenuItem getEditorMapDisplayTileTypeCheckBoxMenuItem() {
        if (editorMapDisplayTileTypeCheckBoxMenuItem == null) {
            editorMapDisplayTileTypeCheckBoxMenuItem = new JCheckBoxMenuItem();
        }
        return editorMapDisplayTileTypeCheckBoxMenuItem;
    }

    private JMenuItem getEditorMapZoomInMenuItem() {
        if (editorMapZoomInMenuItem == null) {
            editorMapZoomInMenuItem = new JMenuItem();
        }
        return editorMapZoomInMenuItem;
    }

    private JMenuItem getEditorMapZoomOutMenuItem() {
        if (editorMapZoomOutMenuItem == null) {
            editorMapZoomOutMenuItem = new JMenuItem();
        }
        return editorMapZoomOutMenuItem;
    }

    private JMenuItem getEditorMapZoomDefaultMenuItem() {
        if (editorMapZoomDefaultMenuItem == null) {
            editorMapZoomDefaultMenuItem = new JMenuItem();
        }
        return editorMapZoomDefaultMenuItem;
    }
}

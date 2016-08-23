package org.freemars.editor.controller.action;

import java.awt.event.KeyEvent;
import javax.swing.Action;
import javax.swing.KeyStroke;
import org.freemars.editor.model.EditorModel;
import org.freemars.editor.controller.EditorController;

/**
 *
 * @author Deniz ARIKAN
 */
public class ActionManager {

    private final EditorController editorController;
    private final Action newMapAction;
    private final Action openMapAction;
    private final Action saveMapAction;
    private final Action saveMapAsAction;
    private final Action displayMapInformationAction;
    private final Action quitEditorToMainMenuAction;
    private final Action exitToSystemAction;
    private final Action displayConvertTilesAction;
    private final Action editorMapZoomInAction;
    private final Action editorMapZoomOutAction;
    private final Action editorMapZoomDefaultAction;
    private final Action toggleEditorMapDisplayGridAction;
    private final Action toggleEditorMapDisplayCoordinateAction;
    private final Action toggleEditorMapDisplayTileTypeAction;

    public ActionManager(EditorController editorController) {
        this.editorController = editorController;
        newMapAction = new DisplayNewMapDialogAction(editorController);
        openMapAction = new OpenMapAction(editorController);
        saveMapAction = new SaveMapAction(editorController);
        saveMapAsAction = new SaveMapAsAction(editorController);
        displayMapInformationAction = new DisplayMapInformationAction(editorController);
        quitEditorToMainMenuAction = new QuitEditorToMainMenuAction(editorController);
        exitToSystemAction = new ExitToSystemAction(editorController);
        displayConvertTilesAction = new DisplayConvertTilesDialogAction(editorController);
        editorMapZoomInAction = new EditorMapZoomInAction(editorController);
        editorMapZoomOutAction = new EditorMapZoomOutAction(editorController);
        editorMapZoomDefaultAction = new EditorMapZoomDefaultAction(editorController);
        toggleEditorMapDisplayGridAction = new ToggleEditorMapDisplayGridAction(editorController);
        toggleEditorMapDisplayCoordinateAction = new ToggleEditorMapDisplayCoordinateAction(editorController);
        toggleEditorMapDisplayTileTypeAction = new ToggleEditorMapDisplayTileTypeAction(editorController);
    }

    public void assignActions() {
        editorController.getEditorFrame().setNewMapMenuItemAction(newMapAction);
        editorController.getEditorFrame().setOpenMapMenuItemAction(openMapAction);
        editorController.getEditorFrame().setSaveMapMenuItemAction(saveMapAction);
        editorController.getEditorFrame().setSaveMapAsMenuItemAction(saveMapAsAction);
        editorController.getEditorFrame().setDisplayMapInformationMenuItemAction(displayMapInformationAction);
        editorController.getEditorFrame().setQuitToMainMenuMenuItemAction(quitEditorToMainMenuAction);
        editorController.getEditorFrame().setExitToSystemMenuItemAction(exitToSystemAction);
        editorController.getEditorFrame().setConvertTilesMenuItemAction(displayConvertTilesAction);
        editorController.getEditorFrame().setEditorMapZoomInMenuItemAction(editorMapZoomInAction);
        editorController.getEditorFrame().setEditorMapZoomOutMenuItemAction(editorMapZoomOutAction);
        editorController.getEditorFrame().setEditorMapZoomDefaultMenuItemAction(editorMapZoomDefaultAction);
        editorController.getEditorFrame().setEditorMapDisplayGridCheckBoxMenuItemAction(toggleEditorMapDisplayGridAction);
        editorController.getEditorFrame().setEditorMapDisplayCoordinateCheckBoxMenuItem(toggleEditorMapDisplayCoordinateAction);
        editorController.getEditorFrame().setEditorMapDisplayTileTypeCheckBoxMenuItemAction(toggleEditorMapDisplayTileTypeAction);

        editorController.getEditorFrame().putKeyboardShortcut(KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.CTRL_DOWN_MASK), newMapAction);
        editorController.getEditorFrame().putKeyboardShortcut(KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_DOWN_MASK), openMapAction);
        editorController.getEditorFrame().putKeyboardShortcut(KeyStroke.getKeyStroke(KeyEvent.VK_Q, KeyEvent.CTRL_DOWN_MASK), quitEditorToMainMenuAction);
        editorController.getEditorFrame().putKeyboardShortcut(KeyStroke.getKeyStroke(KeyEvent.VK_E, KeyEvent.CTRL_DOWN_MASK), exitToSystemAction);
        editorController.getEditorFrame().putKeyboardShortcut(KeyStroke.getKeyStroke(KeyEvent.VK_PLUS, 0), editorMapZoomInAction);
        editorController.getEditorFrame().putKeyboardShortcut(KeyStroke.getKeyStroke(107, 0), editorMapZoomInAction);
        editorController.getEditorFrame().putKeyboardShortcut(KeyStroke.getKeyStroke(KeyEvent.VK_MINUS, 0), editorMapZoomOutAction);
        editorController.getEditorFrame().putKeyboardShortcut(KeyStroke.getKeyStroke(109, 0), editorMapZoomOutAction);
        editorController.getEditorFrame().putKeyboardShortcut(KeyStroke.getKeyStroke(KeyEvent.VK_MULTIPLY, 0), editorMapZoomDefaultAction);
        editorController.getEditorFrame().putKeyboardShortcut(KeyStroke.getKeyStroke(KeyEvent.VK_G, KeyEvent.CTRL_DOWN_MASK), toggleEditorMapDisplayGridAction);
        editorController.getEditorFrame().putKeyboardShortcut(KeyStroke.getKeyStroke(KeyEvent.VK_D, KeyEvent.CTRL_DOWN_MASK), toggleEditorMapDisplayCoordinateAction);
        editorController.getEditorFrame().putKeyboardShortcut(KeyStroke.getKeyStroke(KeyEvent.VK_T, KeyEvent.CTRL_DOWN_MASK), toggleEditorMapDisplayTileTypeAction);
    }

    public void updateActions() {
        EditorModel editorModel = editorController.getEditorModel();
        saveMapAction.setEnabled(false);
        saveMapAsAction.setEnabled(false);
        displayMapInformationAction.setEnabled(false);
        toggleEditorMapDisplayGridAction.setEnabled(false);
        toggleEditorMapDisplayCoordinateAction.setEnabled(false);
        toggleEditorMapDisplayTileTypeAction.setEnabled(false);
        editorMapZoomOutAction.setEnabled(false);
        editorMapZoomInAction.setEnabled(false);
        editorMapZoomDefaultAction.setEnabled(false);
        displayConvertTilesAction.setEnabled(false);
        if (editorModel.getRealm().getMap() != null) {
            saveMapAction.setEnabled(true);
            saveMapAsAction.setEnabled(true);
            displayMapInformationAction.setEnabled(true);
            toggleEditorMapDisplayGridAction.setEnabled(true);
            toggleEditorMapDisplayCoordinateAction.setEnabled(true);
            toggleEditorMapDisplayTileTypeAction.setEnabled(true);
            editorMapZoomDefaultAction.setEnabled(true);
            displayConvertTilesAction.setEnabled(true);
            int currentEditorMapZoomLevel = editorModel.getEditorMapZoomLevel();
            if (currentEditorMapZoomLevel != EditorModel.EDITOR_PANEL_MINIMUM_ZOOM_MULTIPLIER) {
                editorMapZoomOutAction.setEnabled(true);
            }
            if (currentEditorMapZoomLevel != EditorModel.EDITOR_PANEL_MAXIMUM_ZOOM_MULTIPLIER) {

                editorMapZoomInAction.setEnabled(true);
            }
        }

    }

}

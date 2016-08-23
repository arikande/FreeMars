package org.freemars.editor.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import javax.swing.Action;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import org.freemars.ui.util.FreeMarsFrame;

/**
 *
 * @author Deniz ARIKAN
 */
public class EditorFrame extends FreeMarsFrame {
    
    private JPanel editorMainPanel;
    private EditorMapPanel editorMapPanel;
    private EditorMenuBar editorMenuBar;
    
    public EditorFrame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setJMenuBar(getEditorMenuBar());
        setContentPane(getEditorMainPanel());
        pack();
    }
    
    public void clearTileTypeMenu() {
        getEditorMenuBar().clearTileTypeMenu();
    }
    
    public void addTileTypeMenuItem(JCheckBoxMenuItem menuItem) {
        getEditorMenuBar().addTileTypeMenuItem(menuItem);
    }
    
    public void clearVegetationMenu() {
        getEditorMenuBar().clearVegetationMenu();
    }
    
    public void addVegetationMenuItem(JCheckBoxMenuItem menuItem) {
        getEditorMenuBar().addVegetationMenuItem(menuItem);
    }
    
    public void clearBonusResourceMenu() {
        getEditorMenuBar().clearBonusResourceMenu();
    }
    
    public void addBonusResourceMenuItem(JCheckBoxMenuItem menuItem) {
        getEditorMenuBar().addBonusResourceMenuItem(menuItem);
    }
    
    public void putKeyboardShortcut(KeyStroke keyStroke, Action action) {
        getEditorMainPanel().getInputMap().put(keyStroke, keyStroke.toString());
        getEditorMainPanel().getActionMap().put(keyStroke.toString(), action);
    }
    
    public void setNewMapMenuItemAction(Action action) {
        getEditorMenuBar().setNewMapMenuItemAction(action);
    }
    
    public void setOpenMapMenuItemAction(Action action) {
        getEditorMenuBar().setOpenMapMenuItemAction(action);
    }
    
    public void setSaveMapMenuItemAction(Action action) {
        getEditorMenuBar().setSaveMapMenuItemAction(action);
    }
    
    public void setSaveMapAsMenuItemAction(Action action) {
        getEditorMenuBar().setSaveMapAsMenuItemAction(action);
    }
    
    public void setDisplayMapInformationMenuItemAction(Action action) {
        getEditorMenuBar().setDisplayMapInformationMenuItemAction(action);
    }
    
    public void setQuitToMainMenuMenuItemAction(Action action) {
        getEditorMenuBar().setQuitToMainMenuMenuItemAction(action);
    }
    
    public void setExitToSystemMenuItemAction(Action action) {
        getEditorMenuBar().setExitToSystemMenuItemAction(action);
    }
    
    public void setConvertTilesMenuItemAction(Action action) {
        getEditorMenuBar().setConvertTilesMenuItemAction(action);
    }
    
    public void setEditorMapZoomInMenuItemAction(Action action) {
        getEditorMenuBar().setEditorMapZoomInMenuItemAction(action);
    }
    
    public void setEditorMapZoomOutMenuItemAction(Action action) {
        getEditorMenuBar().setEditorMapZoomOutMenuItemAction(action);
    }
    
    public void setEditorMapZoomDefaultMenuItemAction(Action action) {
        getEditorMenuBar().setEditorMapZoomDefaultMenuItemAction(action);
    }
    
    public void setEditorMapDisplayGridCheckBoxMenuItemAction(Action action) {
        getEditorMenuBar().setEditorMapDisplayGridCheckBoxMenuItemAction(action);
    }
    
    public void setEditorMapDisplayGridCheckBoxMenuItemSelected(boolean selected) {
        getEditorMenuBar().setEditorMapDisplayGridCheckBoxMenuItemSelected(selected);
    }
    
    public void setEditorMapDisplayCoordinateCheckBoxMenuItem(Action action) {
        getEditorMenuBar().setEditorMapDisplayCoordinateCheckBoxMenuItemAction(action);
    }
    
    public void setEditorMapDisplayCoordinateCheckBoxMenuItemSelected(boolean selected) {
        getEditorMenuBar().setEditorMapDisplayCoordinateCheckBoxMenuItemSelected(selected);
    }
    
    public void setEditorMapDisplayTileTypeCheckBoxMenuItemAction(Action action) {
        getEditorMenuBar().setEditorMapDisplayTileTypeCheckBoxMenuItemAction(action);
    }
    
    public void setEditorMapDisplayTileTypeCheckBoxMenuItemSelected(boolean selected) {
        getEditorMenuBar().setEditorMapDisplayTileTypeCheckBoxMenuItemSelected(selected);
    }
    
    public void setEditorMapPanelImage(Image image) {
        editorMapPanel.setImage(image);
        editorMapPanel.repaint();
    }
    
    public Dimension getEditorMapPanelSize() {
        return editorMapPanel.getSize();
    }
    
    private EditorMenuBar getEditorMenuBar() {
        if (editorMenuBar == null) {
            editorMenuBar = new EditorMenuBar();
        }
        return editorMenuBar;
    }
    
    private JPanel getEditorMainPanel() {
        if (editorMainPanel == null) {
            editorMainPanel = new JPanel(new BorderLayout());
            editorMainPanel.add(getEditorMapPanel(), BorderLayout.CENTER);
        }
        return editorMainPanel;
    }
    
    private EditorMapPanel getEditorMapPanel() {
        if (editorMapPanel == null) {
            editorMapPanel = new EditorMapPanel();
        }
        return editorMapPanel;
    }
    
    public void addEditorMapPanelMouseListener(MouseAdapter mouseAdapter) {
        getEditorMapPanel().addMouseListener(mouseAdapter);
    }
    
    public void addEditorMapPanelMouseMotionListener(MouseAdapter mouseAdapter) {
        getEditorMapPanel().addMouseMotionListener(mouseAdapter);
    }
    
    public void addEditorMapPanelMouseWheelListener(MouseAdapter mouseAdapter) {
        getEditorMapPanel().addMouseWheelListener(mouseAdapter);
    }
    
}

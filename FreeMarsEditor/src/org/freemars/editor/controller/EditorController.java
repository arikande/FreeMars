package org.freemars.editor.controller;

import org.freemars.editor.controller.listener.TileTypeMenuItemActionListener;
import org.freemars.editor.controller.listener.VegetationTypeMenuItemActionListener;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Iterator;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import org.freemars.editor.view.EditorFrame;
import org.freemars.editor.model.EditorModel;
import org.freemars.editor.controller.action.ActionManager;
import org.freemars.editor.controller.listener.BonusResourceMenuItemActionListener;
import org.freemars.editor.controller.listener.EditorFrameComponentAdapter;
import org.freemars.editor.controller.listener.EditorMapPanelMouseListener;
import org.freemars.ui.image.FreeMarsImageManager;
import org.freemars.ui.map.MapImageGenerator;
import org.freemars.ui.map.MapImageModel;
import org.freerealm.Realm;
import org.freerealm.executor.Command;
import org.freerealm.executor.CommandResult;
import org.freerealm.executor.DefaultExecutor;
import org.freerealm.executor.Executor;
import org.freerealm.map.Coordinate;
import org.freerealm.resource.bonus.BonusResource;
import org.freerealm.tile.TileType;
import org.freerealm.vegetation.VegetationType;

/**
 *
 * @author Deniz ARIKAN
 */
public class EditorController implements Executor {

    private final EditorModel editorModel;
    private final EditorFrame editorFrame;
    private ActionManager actionManager;
    private Action quitEditorAction;

    public EditorController(Realm realm) {
        editorModel = new EditorModel(realm);
        editorFrame = new EditorFrame();
        getActionManager().assignActions();
        EditorMapPanelMouseListener editorMapPanelMouseListener = new EditorMapPanelMouseListener(this);
        editorFrame.addEditorMapPanelMouseListener(editorMapPanelMouseListener);
        editorFrame.addEditorMapPanelMouseMotionListener(editorMapPanelMouseListener);
        editorFrame.addEditorMapPanelMouseWheelListener(editorMapPanelMouseListener);
        editorFrame.addComponentListener(new EditorFrameComponentAdapter(this));

        TileType defaultTileType = realm.getTileTypeManager().getTileType("Wasteland");
        editorModel.getBrush().setTileType(defaultTileType);
    }

    public CommandResult execute(Command command) {
        Executor executor = new DefaultExecutor();
        command.setExecutor(executor);
        return executor.execute(command);
    }

    public void updateEditorFrame() {
        String title = getEditorModel().getFileName();
        if (title == null) {
            title = "- New map -";
        }
        if (getEditorModel().isFileDirty()) {
            title = title + " *";
        }
        getEditorFrame().setTitle(title);
        updateActions();
        updateMenuBar();
        repaintEditorPanel();
    }

    public void repaintEditorPanel() {
        if (editorModel.getRealm().getMap() != null) {
            int gridWidth = 16 * (editorModel.getEditorMapZoomLevel() + 1);
            int gridHeight = gridWidth / 2;
            Dimension imageDimension = getEditorFrame().getEditorMapPanelSize();
            Coordinate offsetCoordinate = editorModel.getOffsetCoordinate();
            int horizontalGrids = ((int) imageDimension.getWidth() / gridWidth) + 1;
            int verticalGrids = (2 * (int) imageDimension.getHeight() / (gridHeight)) + 1;

            if ((offsetCoordinate.getOrdinate() + verticalGrids) > editorModel.getRealm().getMapHeight() + 2) {
                offsetCoordinate.setOrdinate(editorModel.getRealm().getMapHeight() - verticalGrids + 2);
                if (offsetCoordinate.getOrdinate() < 0) {
                    offsetCoordinate.setOrdinate(0);
                }
            }
            editorModel.setOffsetCoordinate(offsetCoordinate);

            MapImageModel mapImageModel = editorModel.getMapImageModel();
            mapImageModel.setImageDimension(imageDimension);
            mapImageModel.setGridDimension(new Dimension(gridWidth, gridHeight));
            mapImageModel.setHorizontalGrids(horizontalGrids);
            mapImageModel.setVerticalGrids(verticalGrids);

            BufferedImage mapImage = MapImageGenerator.generateMapImage(mapImageModel);
            getEditorFrame().setEditorMapPanelImage(mapImage);
        }
    }

    public void updateActions() {
        actionManager.updateActions();
    }

    public void updateMenuBar() {
        getEditorFrame().setEditorMapDisplayGridCheckBoxMenuItemSelected(getEditorModel().isEditorMapDisplayingGridLines());
        getEditorFrame().setEditorMapDisplayCoordinateCheckBoxMenuItemSelected(getEditorModel().isEditorMapDisplayingCoordinates());
        getEditorFrame().setEditorMapDisplayTileTypeCheckBoxMenuItemSelected(getEditorModel().isEditorMapDisplayingTileTypes());

        getEditorFrame().clearTileTypeMenu();
        getEditorFrame().clearVegetationMenu();
        getEditorFrame().clearBonusResourceMenu();
        if (getEditorModel().getRealm().getMap() != null) {
            Iterator<TileType> tileTypesIterator = getEditorModel().getRealm().getTileTypeManager().getTileTypesIterator();
            while (tileTypesIterator.hasNext()) {
                TileType tileType = tileTypesIterator.next();
                Image tileTypeImage = FreeMarsImageManager.getImage(tileType, -1, -1);
                tileTypeImage = FreeMarsImageManager.createResizedCopy(tileTypeImage, 40, 20, false, null);
                JCheckBoxMenuItem tileTypeMenuItem = new JCheckBoxMenuItem(tileType.getName());
                tileTypeMenuItem.setIcon(new ImageIcon(tileTypeImage));
                if (tileType.equals(getEditorModel().getBrush().getTileType())) {
                    tileTypeMenuItem.setSelected(true);
                }
                tileTypeMenuItem.addActionListener(new TileTypeMenuItemActionListener(this, tileType));
                getEditorFrame().addTileTypeMenuItem(tileTypeMenuItem);
            }

            JCheckBoxMenuItem noVegetationMenuItem = new JCheckBoxMenuItem("None");
            if (getEditorModel().getBrush().getVegetationType() == null) {
                noVegetationMenuItem.setSelected(true);
            }
            noVegetationMenuItem.addActionListener(new VegetationTypeMenuItemActionListener(this, null));
            getEditorFrame().addVegetationMenuItem(noVegetationMenuItem);

            Iterator<VegetationType> vegetationIterator = getEditorModel().getRealm().getVegetationManager().getVegetationTypesIterator();
            while (vegetationIterator.hasNext()) {
                VegetationType vegetationType = vegetationIterator.next();
                if (vegetationType.canGrowOnTileType(getEditorModel().getBrush().getTileType())) {
                    Image vegetationTypeImage = FreeMarsImageManager.getImage(vegetationType, -1, -1);
                    vegetationTypeImage = FreeMarsImageManager.createResizedCopy(vegetationTypeImage, 40, 20, false, null);
                    JCheckBoxMenuItem vegetationTypeMenuItem = new JCheckBoxMenuItem(vegetationType.getName());
                    vegetationTypeMenuItem.setIcon(new ImageIcon(vegetationTypeImage));
                    if (vegetationType.equals(getEditorModel().getBrush().getVegetationType())) {
                        vegetationTypeMenuItem.setSelected(true);
                    }
                    vegetationTypeMenuItem.addActionListener(new VegetationTypeMenuItemActionListener(this, vegetationType));
                    getEditorFrame().addVegetationMenuItem(vegetationTypeMenuItem);
                }
            }

            JCheckBoxMenuItem noBonusResourceMenuItem = new JCheckBoxMenuItem("None");
            if (getEditorModel().getBrush().getBonusResource() == null) {
                noBonusResourceMenuItem.setSelected(true);
            }
            noBonusResourceMenuItem.addActionListener(new BonusResourceMenuItemActionListener(this, null));
            getEditorFrame().addBonusResourceMenuItem(noBonusResourceMenuItem);

            Iterator<BonusResource> bonusResourcesIterator = getEditorModel().getRealm().getBonusResourceManager().getBonusResourcesIterator();
            while (bonusResourcesIterator.hasNext()) {
                BonusResource bonusResource = bonusResourcesIterator.next();
                if (bonusResource.hasTileType(getEditorModel().getBrush().getTileType())) {
                    Image bonusResourceImage = FreeMarsImageManager.getImage(bonusResource, -1, -1);
                    bonusResourceImage = FreeMarsImageManager.createResizedCopy(bonusResourceImage, 20, 20, false, null);
                    JCheckBoxMenuItem bonusResourceMenuItem = new JCheckBoxMenuItem(bonusResource.getName());
                    bonusResourceMenuItem.setIcon(new ImageIcon(bonusResourceImage));
                    if (bonusResource.equals(getEditorModel().getBrush().getBonusResource())) {
                        bonusResourceMenuItem.setSelected(true);
                    }
                    bonusResourceMenuItem.addActionListener(new BonusResourceMenuItemActionListener(this, bonusResource));
                    getEditorFrame().addBonusResourceMenuItem(bonusResourceMenuItem);
                }

            }
        }
    }

    public void displayEditorFrame(int width, int height) {
        updateEditorFrame();
        getEditorFrame().setBounds(new Rectangle(width, height));
        getEditorFrame().setVisible(true);
    }

    public EditorModel getEditorModel() {
        return editorModel;
    }

    public EditorFrame getEditorFrame() {
        return editorFrame;
    }

    public Action getQuitEditorAction() {
        return quitEditorAction;
    }

    public void setQuitEditorAction(Action quitEditorAction) {
        this.quitEditorAction = quitEditorAction;
    }

    public Coordinate getCoordinateAt(int x, int y) {
        int abscissaOffset = editorModel.getOffsetCoordinate().getAbscissa();
        int ordinateOffset = editorModel.getOffsetCoordinate().getOrdinate();
        int gridWidth = (int) editorModel.getGridDimension().getWidth();
        int gridHeight = (int) editorModel.getGridDimension().getHeight();
        int worldAbscissa = abscissaOffset * gridWidth + x;
        int worldOrdinate = ordinateOffset * gridHeight / 2 + y;
        int M = (worldAbscissa + 2 * worldOrdinate - (gridWidth / 2)) / gridWidth;
        int N = (-worldOrdinate + (worldAbscissa / 2) - (gridHeight / 2));
        N = (N > 0 ? (N / gridHeight) + 1 : N / gridHeight);
        int tileAbscissa = ((M + N) / 2) % editorModel.getRealm().getMapWidth();
        Coordinate coordinate = new Coordinate(tileAbscissa, M - N);
        return coordinate;
    }

    private ActionManager getActionManager() {
        if (actionManager == null) {
            actionManager = new ActionManager(this);
        }
        return actionManager;
    }

}

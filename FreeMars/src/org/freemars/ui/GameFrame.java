package org.freemars.ui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.MouseListener;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import org.freemars.model.FreeMarsModel;
import org.freemars.ui.image.FreeMarsImageManager;
import org.freemars.ui.image.ImagePanel;
import org.freemars.ui.map.MapPanel;
import org.freemars.ui.map.MiniMapPanel;
import org.freemars.ui.menu.MapMenu;
import org.freemars.ui.unit.UnitDetailsPanel;
import org.freemars.ui.util.FreeMarsFrame;
import org.freerealm.player.Player;
import org.freerealm.unit.Unit;

/**
 *
 * @author Deniz ARIKAN
 */
public class GameFrame extends FreeMarsFrame {

    private final FreeMarsModel model;
    private MapMenu mapMenu;
    private MapPanel mapPanel;
    private JPanel miniMapMainPanel;
    private MiniMapPanel miniMapPanel;
    private JPanel miniMapControlPanel;
    private JPanel miniMapCentralPanel;
    private ImagePanel miniMapImagePanel;
    private JButton miniMapZoomInButton;
    private JButton miniMapZoomOutButton;
    private JButton miniMapDefaultZoomButton;
    private UnitDetailsPanel unitDetailsPanel;
    private JPanel unitDetailsAndEndTurnMainPanel;
    private ImagePanel unitDetailsAndEndTurnImagePanel;
    private JPanel unitDetailsAndEndTurnCentralPanel;
    private JPanel endTurnPanel;
    private JButton endTurnButton;

    public GameFrame(FreeMarsModel model) {
        this.model = model;
        setJMenuBar(getMapMenu());
        setContentPane(getMapPanel());
        getMapPanel().addMiniMap(getMiniMapPanel());
        pack();
        setExtendedState(getExtendedState() | JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void setMiniMapDefaultZoomButtonAction(Action action) {
        getDefaultZoomButton().setAction(action);
    }

    public void setMiniMapZoomInButtonAction(Action action) {
        getZoomInButton().setAction(action);
    }

    public void setMiniMapZoomOutButtonAction(Action action) {
        getZoomOutButton().setAction(action);
    }

    public void setEndTurnButtonAction(Action action) {
        getEndTurnButton().setAction(action);
    }

    public void setUnitDetailsPanelMouseListener(MouseListener mouseListener) {
        getUnitDetailsPanel().addMouseListener(mouseListener);
    }

    public void update() {
        Player activePlayer = model.getRealm().getPlayerManager().getActivePlayer();
        if (activePlayer != null) {
            getMapMenu().update();
            getMapPanel().update();
            Unit activeUnit = activePlayer.getActiveUnit();
            getUnitDetailsPanel().setUnit(activeUnit);
            CardLayout cardLayout = (CardLayout) (getUnitDetailsAndEndTurnCentralPanel().getLayout());
            if (activeUnit != null) {
                cardLayout.show(getUnitDetailsAndEndTurnCentralPanel(), "UnitDetails");
            } else {
                cardLayout.show(getUnitDetailsAndEndTurnCentralPanel(), "EndTurn");
            }
            getMapPanel().requestFocus();
        }
    }

    public MapMenu getMapMenu() {
        if (mapMenu == null) {
            mapMenu = new MapMenu(model);
        }
        return mapMenu;
    }

    public MapPanel getMapPanel() {
        if (mapPanel == null) {
            mapPanel = new MapPanel(model);
            GridBagLayout gridBagLayout = new GridBagLayout();
            mapPanel.setLayout(gridBagLayout);
            GridBagConstraints gridBagConstraints = new GridBagConstraints();
            gridBagConstraints.weightx = 1;
            gridBagConstraints.weighty = 1;
            gridBagConstraints.anchor = GridBagConstraints.SOUTHWEST;
            mapPanel.add(getMiniMapMainPanel(), gridBagConstraints);
            gridBagConstraints.anchor = GridBagConstraints.SOUTHEAST;
            mapPanel.add(getUnitDetailsAndEndTurnMainPanel(), gridBagConstraints);
        }
        return mapPanel;
    }

    private JPanel getMiniMapMainPanel() {
        if (miniMapMainPanel == null) {
            miniMapMainPanel = new JPanel(new BorderLayout());
            miniMapMainPanel.setPreferredSize(new Dimension(250, 205));
            miniMapMainPanel.setOpaque(false);
            miniMapMainPanel.add(getMiniMapImagePanel(), BorderLayout.PAGE_START);
            miniMapMainPanel.add(getMiniMapCentralPanel(), BorderLayout.CENTER);
        }
        return miniMapMainPanel;
    }

    private ImagePanel getMiniMapImagePanel() {
        if (miniMapImagePanel == null) {
            miniMapImagePanel = new ImagePanel(FreeMarsImageManager.getImage("MINIMAP_TOP"));
            miniMapImagePanel.setOpaque(false);
            miniMapImagePanel.setPreferredSize(new Dimension(250, 25));
        }
        return miniMapImagePanel;
    }

    private JPanel getMiniMapCentralPanel() {
        if (miniMapCentralPanel == null) {
            miniMapCentralPanel = new JPanel(new BorderLayout());
            miniMapCentralPanel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
            miniMapCentralPanel.add(getMiniMapPanel(), BorderLayout.CENTER);
            miniMapCentralPanel.add(getMiniMapControlPanel(), BorderLayout.EAST);
        }
        return miniMapCentralPanel;
    }

    private JPanel getMiniMapControlPanel() {
        if (miniMapControlPanel == null) {
            miniMapControlPanel = new JPanel();
            miniMapControlPanel.setLayout(new GridLayout(3, 1));
            miniMapControlPanel.add(getZoomInButton());
            miniMapControlPanel.add(getZoomOutButton());
            miniMapControlPanel.add(getDefaultZoomButton());
        }
        return miniMapControlPanel;
    }

    private JButton getZoomInButton() {
        if (miniMapZoomInButton == null) {
            miniMapZoomInButton = new JButton();
        }
        return miniMapZoomInButton;
    }

    private JButton getZoomOutButton() {
        if (miniMapZoomOutButton == null) {
            miniMapZoomOutButton = new JButton();
        }
        return miniMapZoomOutButton;
    }

    private JButton getDefaultZoomButton() {
        if (miniMapDefaultZoomButton == null) {
            miniMapDefaultZoomButton = new JButton();
        }
        return miniMapDefaultZoomButton;
    }

    public MiniMapPanel getMiniMapPanel() {
        if (miniMapPanel == null) {
            miniMapPanel = new MiniMapPanel(model);
            miniMapPanel.setMapPanel(getMapPanel());
            miniMapPanel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
        }
        return miniMapPanel;
    }

    private JPanel getUnitDetailsAndEndTurnMainPanel() {
        if (unitDetailsAndEndTurnMainPanel == null) {
            unitDetailsAndEndTurnMainPanel = new JPanel(new BorderLayout());
            unitDetailsAndEndTurnMainPanel.setPreferredSize(new Dimension(250, 205));
            unitDetailsAndEndTurnMainPanel.setOpaque(false);
            unitDetailsAndEndTurnMainPanel.add(getUnitDetailsAndEndTurnImagePanel(), BorderLayout.PAGE_START);
            unitDetailsAndEndTurnMainPanel.add(getUnitDetailsAndEndTurnCentralPanel(), BorderLayout.CENTER);
        }
        return unitDetailsAndEndTurnMainPanel;
    }

    private ImagePanel getUnitDetailsAndEndTurnImagePanel() {
        if (unitDetailsAndEndTurnImagePanel == null) {
            unitDetailsAndEndTurnImagePanel = new ImagePanel(FreeMarsImageManager.getImage("UNIT_DETAILS_AND_END_TURN_TOP"));
            unitDetailsAndEndTurnImagePanel.setOpaque(false);
            unitDetailsAndEndTurnImagePanel.setPreferredSize(new Dimension(250, 25));
        }
        return unitDetailsAndEndTurnImagePanel;
    }

    private JPanel getUnitDetailsAndEndTurnCentralPanel() {
        if (unitDetailsAndEndTurnCentralPanel == null) {
            unitDetailsAndEndTurnCentralPanel = new JPanel(new CardLayout());
            unitDetailsAndEndTurnCentralPanel.add(getUnitDetailsPanel(), "UnitDetails");
            unitDetailsAndEndTurnCentralPanel.add(getEndTurnPanel(), "EndTurn");
        }
        return unitDetailsAndEndTurnCentralPanel;
    }

    private UnitDetailsPanel getUnitDetailsPanel() {
        if (unitDetailsPanel == null) {
            unitDetailsPanel = new UnitDetailsPanel(model);
            unitDetailsPanel.setPreferredSize(new Dimension(250, 180));
        }
        return unitDetailsPanel;
    }

    private JPanel getEndTurnPanel() {
        if (endTurnPanel == null) {
            endTurnPanel = new JPanel(new BorderLayout());
            endTurnPanel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
            int thickness = 50;
            endTurnPanel.add(Box.createVerticalStrut(thickness), BorderLayout.PAGE_START);
            endTurnPanel.add(Box.createHorizontalStrut(thickness), BorderLayout.LINE_START);
            endTurnPanel.add(getEndTurnButton(), BorderLayout.CENTER);
            endTurnPanel.add(Box.createHorizontalStrut(thickness), BorderLayout.LINE_END);
            endTurnPanel.add(Box.createVerticalStrut(thickness), BorderLayout.PAGE_END);
        }
        return endTurnPanel;
    }

    private JButton getEndTurnButton() {
        if (endTurnButton == null) {
            endTurnButton = new JButton();
        }
        return endTurnButton;
    }
}

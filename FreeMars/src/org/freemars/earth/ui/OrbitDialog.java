package org.freemars.earth.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.Map.Entry;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import org.freemars.controller.FreeMarsController;
import org.freemars.earth.Location;
import org.freemars.earth.action.OrbitDialogUnitSelectionAction;
import org.freemars.ui.image.FreeMarsImageManager;
import org.freemars.ui.image.ImagePanel;
import org.freemars.ui.util.FreeMarsDialog;
import org.freerealm.unit.Unit;

/**
 *
 * @author Deniz ARIKAN
 */
public class OrbitDialog extends FreeMarsDialog {

    private final int FRAME_WIDTH = 600;
    private final int FRAME_HEIGHT = 450;
    private ImagePanel centerPanel;
    private JPanel headerPanel;
    private JPanel pageEndPanel;
    private JScrollPane toMarsPanelScrollPane;
    private JPanel toMarsPanel;
    private JScrollPane orbitPanelScrollPane;
    private JPanel orbitPanel;
    private JScrollPane toEarthPanelScrollPane;
    private JPanel toEarthPanel;
    private JButton closeButton;
    private JLabel toMarsLabel;
    private JLabel toEarthLabel;
    private JLabel marsOrbitLabel;
    private final FreeMarsController freeMarsController;

    public OrbitDialog(JFrame parent, FreeMarsController freeMarsController) {
        super(parent);
        this.freeMarsController = freeMarsController;
        setModal(true);
        setResizable(false);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setTitle("Mars orbit");
    }

    public void display() {
        getContentPane().setLayout(new BorderLayout());
        initializeWidgets();
        pack();
        final java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width - FRAME_WIDTH) / 2, (screenSize.height - FRAME_HEIGHT) / 2, FRAME_WIDTH, FRAME_HEIGHT);
        setVisible(true);
        toFront();
    }
    private ButtonGroup bg = new ButtonGroup();

    private void initializeWidgets() {
        add(getHeaderPanel(), BorderLayout.PAGE_START);
        add(getCenterPanel(), BorderLayout.CENTER);
        add(getPageEndPanel(), BorderLayout.PAGE_END);
        update();
    }

    public void update() {
        getToEarthPanel().removeAll();
        getOrbitPanel().removeAll();
        getToMarsPanel().removeAll();
        bg = new ButtonGroup();
        Iterator<Entry<Unit, Location>> iterator = freeMarsController.getFreeMarsModel().getEarth().getUnitLocationsIterator();
        while (iterator.hasNext()) {
            Entry<Unit, Location> entry = iterator.next();
            Unit unit = entry.getKey();
            Location location = entry.getValue();
            if (unit.getPlayer().equals(freeMarsController.getFreeMarsModel().getRealm().getPlayerManager().getActivePlayer())) {
                UnitSelectionToggleButton toggleButton = new UnitSelectionToggleButton(unit);
                toggleButton.setOpaque(false);
                toggleButton.setBorderPainted(false);
                toggleButton.setContentAreaFilled(false);
                if (location.equals(Location.MARS_ORBIT)) {
                    toggleButton.setAction(new OrbitDialogUnitSelectionAction(freeMarsController, this));
                    toggleButton.setText(unit.getName());
                    getOrbitPanel().add(toggleButton);
                    bg.add(toggleButton);
                } else if (location.equals(Location.MARS)) {
                    getOrbitPanel().add(toggleButton);
                    toggleButton.setFocusable(false);
                    toggleButton.setEnabled(false);
                    toggleButton.setToolTipText("");
                } else if (location.equals(Location.TRAVELING_TO_EARTH)) {
                    getToEarthPanel().add(toggleButton);
                    toggleButton.setFocusable(false);
                    String buttonText = "<html><table><tr><td bgcolor='#FFEEEE'>E.T.A " + freeMarsController.getFreeMarsModel().getEarth().findETA(unit) + "</td></tr></table></html>";
                    toggleButton.setText(buttonText);
                    toggleButton.setToolTipText("");
                } else if (location.equals(Location.TRAVELING_TO_MARS)) {
                    getToMarsPanel().add(toggleButton);
                    toggleButton.setFocusable(false);
                    String buttonText = "<html><table><tr><td bgcolor='#FFEEEE'>E.T.A " + freeMarsController.getFreeMarsModel().getEarth().findETA(unit);
                    buttonText = buttonText + "<br>" + freeMarsController.getFreeMarsModel().getEarth().findDestination(unit) + "</td></tr></table></html>";
                    toggleButton.setText(buttonText);
                    toggleButton.setToolTipText("");
                }
                Image unitImage = FreeMarsImageManager.getImage(unit);
                unitImage = FreeMarsImageManager.createResizedCopy(unitImage, 120, -1, false, null);
                toggleButton.setIcon(new ImageIcon(unitImage));
            }
        }
        for (int i = 0; i < 2 - getOrbitPanel().getComponentCount(); i++) {
            getOrbitPanel().add(new JLabel());
        }
        for (int i = 0; i < 2 - getToMarsPanel().getComponentCount(); i++) {
            getToMarsPanel().add(new JLabel());
        }
        for (int i = 0; i < 2 - getToEarthPanel().getComponentCount(); i++) {
            getToEarthPanel().add(new JLabel());
        }
        getCloseButton().requestFocus();
    }

    private JPanel getHeaderPanel() {
        if (headerPanel == null) {
            headerPanel = new JPanel(new GridLayout(1, 3));
            headerPanel.setBackground(Color.BLACK);
            headerPanel.add(getToMarsLabel());
            headerPanel.add(getMarsOrbitLabel());
            headerPanel.add(getToEarthLabel());
        }
        return headerPanel;
    }

    private JLabel getMarsOrbitLabel() {
        if (marsOrbitLabel == null) {
            marsOrbitLabel = new JLabel("  Mars orbit");
            marsOrbitLabel.setForeground(Color.WHITE);
        }
        return marsOrbitLabel;
    }

    private JLabel getToMarsLabel() {
        if (toMarsLabel == null) {
            toMarsLabel = new JLabel("  Traveling To Mars");
            toMarsLabel.setForeground(Color.WHITE);
        }
        return toMarsLabel;
    }

    private JLabel getToEarthLabel() {
        if (toEarthLabel == null) {
            toEarthLabel = new JLabel("  Traveling To Earth");
            toEarthLabel.setForeground(Color.WHITE);
        }
        return toEarthLabel;
    }

    private ImagePanel getCenterPanel() {
        if (centerPanel == null) {
            centerPanel = new ImagePanel(FreeMarsImageManager.getImage("ORBIT_BACKGROUND"));
            centerPanel.setLayout(new GridLayout(1, 3));
            centerPanel.add(getToMarsPanelScrollPane());
            centerPanel.add(getOrbitPanelScrollPane());
            centerPanel.add(getToEarthPanelScrollPane());
        }
        return centerPanel;
    }

    private JScrollPane getToMarsPanelScrollPane() {
        if (toMarsPanelScrollPane == null) {
            toMarsPanelScrollPane = new JScrollPane(getToMarsPanel(), ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
            toMarsPanelScrollPane.setOpaque(false);
            toMarsPanelScrollPane.getViewport().setOpaque(false);
        }
        return toMarsPanelScrollPane;
    }

    private JPanel getToMarsPanel() {
        if (toMarsPanel == null) {
            toMarsPanel = new JPanel(new GridLayout(0, 1));
            toMarsPanel.setOpaque(false);
            toMarsPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
        }
        return toMarsPanel;
    }

    private JScrollPane getOrbitPanelScrollPane() {
        if (orbitPanelScrollPane == null) {
            orbitPanelScrollPane = new JScrollPane(getOrbitPanel(), ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
            orbitPanelScrollPane.setOpaque(false);
            orbitPanelScrollPane.getViewport().setOpaque(false);
        }
        return orbitPanelScrollPane;
    }

    private JPanel getOrbitPanel() {
        if (orbitPanel == null) {
            orbitPanel = new JPanel(new GridLayout(0, 1));
            orbitPanel.setOpaque(false);
            orbitPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
        }
        return orbitPanel;
    }

    private JScrollPane getToEarthPanelScrollPane() {
        if (toEarthPanelScrollPane == null) {
            toEarthPanelScrollPane = new JScrollPane(getToEarthPanel(), ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
            toEarthPanelScrollPane.setOpaque(false);
            toEarthPanelScrollPane.getViewport().setOpaque(false);
        }
        return toEarthPanelScrollPane;
    }

    private JPanel getToEarthPanel() {
        if (toEarthPanel == null) {
            toEarthPanel = new JPanel(new GridLayout(0, 1));
            toEarthPanel.setOpaque(false);
            toEarthPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
        }
        return toEarthPanel;
    }

    private JPanel getPageEndPanel() {
        if (pageEndPanel == null) {
            pageEndPanel = new JPanel();
            pageEndPanel.add(getCloseButton());
        }
        return pageEndPanel;
    }

    private JButton getCloseButton() {
        if (closeButton == null) {
            closeButton = new JButton("Close");
            closeButton.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    dispose();
                }
            });
        }
        return closeButton;
    }
}

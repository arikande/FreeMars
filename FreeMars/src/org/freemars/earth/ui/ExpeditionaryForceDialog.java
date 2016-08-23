package org.freemars.earth.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Image;
import java.util.Iterator;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import org.freemars.ai.ExpeditionaryForcePlayer;
import org.freemars.player.FreeMarsPlayer;
import org.freemars.ui.image.FreeMarsImageManager;
import org.freemars.ui.image.ImagePanel;
import org.freemars.ui.util.FreeMarsDialog;
import org.freerealm.unit.UnitGroupDefinition;
import org.freerealm.unit.UnitType;

/**
 *
 * @author Deniz ARIKAN
 */
public class ExpeditionaryForceDialog extends FreeMarsDialog {

    private final int FRAME_WIDTH = 360;
    private final int FRAME_HEIGHT = 500;
    private JPanel headerPanel;
    private JPanel mainPanel;
    private Border mainPanelBorder;
    private JPanel footerPanel;
    private JButton closeButton;
    private final FreeMarsPlayer activePlayer;
    private final ExpeditionaryForcePlayer expeditionaryForcePlayer;
    private int remainingTurnsToLand;

    public ExpeditionaryForceDialog(Frame owner, FreeMarsPlayer activePlayer, ExpeditionaryForcePlayer expeditionaryForcePlayer, int remainingTurnsToLand) {
        super(owner);
        setModal(true);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setTitle("Expeditionary force");
        this.activePlayer = activePlayer;
        this.expeditionaryForcePlayer = expeditionaryForcePlayer;
        this.remainingTurnsToLand = remainingTurnsToLand;
        initializeWidgets();
    }

    public void display() {
        pack();
        Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width - FRAME_WIDTH) / 2, (screenSize.height - FRAME_HEIGHT) / 2, FRAME_WIDTH, FRAME_HEIGHT);
        setVisible(true);
        toFront();
    }

    public void setCloseAction(Action action) {
        getCloseButton().setAction(action);
    }

    private void initializeWidgets() {
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(getHeaderPanel(), BorderLayout.PAGE_START);
        getContentPane().add(new JLabel("   "), BorderLayout.LINE_START);
        getContentPane().add(getMainPanel(), BorderLayout.CENTER);
        getContentPane().add(new JLabel("   "), BorderLayout.LINE_END);
        getContentPane().add(getFooterPanel(), BorderLayout.PAGE_END);
    }

    private JPanel getHeaderPanel() {
        if (headerPanel == null) {
            headerPanel = new JPanel(new GridLayout(0, 1));
            headerPanel.add(new JLabel());
            if (activePlayer.hasDeclaredIndependence()) {
                headerPanel.add(new JLabel("    Earth government has sent an expeditionary"));
                headerPanel.add(new JLabel("    force to restore Earth rule over our colonies."));
                headerPanel.add(new JLabel());
                if (expeditionaryForcePlayer.hasLanded()) {
                    headerPanel.add(new JLabel("    Our forces are currently fighting this force to"));
                    headerPanel.add(new JLabel("    defend our independence."));
                } else {
                    headerPanel.add(new JLabel("    First wave of this force is currently traveling "));
                    if (remainingTurnsToLand > 0) {
                        headerPanel.add(new JLabel("    to Mars and is expected to land in " + remainingTurnsToLand + " months."));
                    } else {
                        headerPanel.add(new JLabel("    to Mars and is expected to land this month."));
                    }
                }
            } else {
                headerPanel.add(new JLabel("    Earth government has formed an expeditionary"));
                headerPanel.add(new JLabel("    force to quell any uprising on Mars."));
                headerPanel.add(new JLabel());
                headerPanel.add(new JLabel("    Our intelligence shows that this force"));
                headerPanel.add(new JLabel("    consists of the following units."));
            }
            headerPanel.add(new JLabel());
        }
        return headerPanel;
    }

    private JPanel getMainPanel() {
        if (mainPanel == null) {
            mainPanel = new JPanel(new GridLayout(0, 2));
            mainPanel.setBorder(getMainPanelBorder());
            UnitGroupDefinition unitGroupDefinition = expeditionaryForcePlayer.getUnitGroupDefinition();
            Iterator<UnitType> iterator = unitGroupDefinition.getUnitTypesIterator();
            while (iterator.hasNext()) {
                UnitType unitType = iterator.next();
                int count = unitGroupDefinition.getQuantityForUnitType(unitType);
                Image unitImage = FreeMarsImageManager.getImage(unitType);
                unitImage = FreeMarsImageManager.createResizedCopy(unitImage, 70, -1, false, mainPanel);
                ImagePanel imagePanel = new ImagePanel(unitImage);
                imagePanel.setToolTipText(unitType.getName());
                mainPanel.add(imagePanel);
                mainPanel.add(new JLabel(String.valueOf(count)));
            }
        }
        return mainPanel;
    }

    private Border getMainPanelBorder() {
        if (mainPanelBorder == null) {
            mainPanelBorder = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.darkGray, 1), activePlayer.getNation().getAdjective() + " expeditionary force");
        }
        return mainPanelBorder;
    }

    private JPanel getFooterPanel() {
        if (footerPanel == null) {
            footerPanel = new JPanel();
            footerPanel.add(getCloseButton());
        }
        return footerPanel;
    }

    private JButton getCloseButton() {
        if (closeButton == null) {
            closeButton = new JButton();
        }
        return closeButton;
    }
}

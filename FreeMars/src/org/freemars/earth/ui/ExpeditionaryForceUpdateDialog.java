package org.freemars.earth.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Image;
import java.util.HashMap;
import java.util.Iterator;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.freemars.ui.image.FreeMarsImageManager;
import org.freemars.ui.image.ImagePanel;
import org.freemars.ui.util.FreeMarsDialog;
import org.freerealm.unit.FreeRealmUnitType;

/**
 *
 * @author Deniz ARIKAN
 */
public class ExpeditionaryForceUpdateDialog extends FreeMarsDialog {

    private final int FRAME_WIDTH = 280;
    private final int FRAME_HEIGHT = 320;
    private JPanel headerPanel;
    private JPanel mainPanel;
    private JPanel footerPanel;
    private JButton displayAllExpeditionaryForceButton;
    private JButton closeButton;
    private final HashMap<FreeRealmUnitType, Integer> expeditionaryForceUpdate;

    public ExpeditionaryForceUpdateDialog(Frame owner, HashMap<FreeRealmUnitType, Integer> expeditionaryForceUpdate) {
        super(owner);
        this.expeditionaryForceUpdate = expeditionaryForceUpdate;
        setModal(true);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setTitle("Expeditionary force update");
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

    public void setDisplayAllExpeditionaryForceButtonAction(Action action) {
        getDisplayAllExpeditionaryForceButton().setAction(action);
    }

    private void initializeWidgets() {
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(getHeaderPanel(), BorderLayout.PAGE_START);
        getContentPane().add(getMainPanel(), BorderLayout.CENTER);
        getContentPane().add(getFooterPanel(), BorderLayout.PAGE_END);
    }

    private JPanel getHeaderPanel() {
        if (headerPanel == null) {
            headerPanel = new JPanel(new GridLayout(0, 1));
            headerPanel.add(new JLabel());
            headerPanel.add(new JLabel("      The following changes have been"));
            headerPanel.add(new JLabel("      made to expeditionary force"));
            headerPanel.add(new JLabel());
        }
        return headerPanel;
    }

    private JPanel getMainPanel() {
        if (mainPanel == null) {
            mainPanel = new JPanel(new GridLayout(0, 2));
            Iterator<FreeRealmUnitType> iterator = expeditionaryForceUpdate.keySet().iterator();
            while (iterator.hasNext()) {
                FreeRealmUnitType unitType = iterator.next();
                int count = expeditionaryForceUpdate.get(unitType);
                Image unitImage = FreeMarsImageManager.getImage(unitType);
                unitImage = FreeMarsImageManager.createResizedCopy(unitImage, 70, -1, false, mainPanel);
                ImagePanel imagePanel = new ImagePanel(unitImage);
                imagePanel.setImageVerticalAlignment(ImagePanel.CENTER_ALIGNMENT);
                imagePanel.setToolTipText(unitType.getName());
                mainPanel.add(imagePanel);
                if (count > 0) {
                    mainPanel.add(new JLabel("+" + String.valueOf(count)));
                } else {
                    mainPanel.add(new JLabel(String.valueOf(count)));
                }
            }
        }
        return mainPanel;
    }

    private JPanel getFooterPanel() {
        if (footerPanel == null) {
            footerPanel = new JPanel();
            footerPanel.add(getCloseButton());
            footerPanel.add(getDisplayAllExpeditionaryForceButton());
        }
        return footerPanel;
    }

    private JButton getDisplayAllExpeditionaryForceButton() {
        if (displayAllExpeditionaryForceButton == null) {
            displayAllExpeditionaryForceButton = new JButton();
        }
        return displayAllExpeditionaryForceButton;
    }

    private JButton getCloseButton() {
        if (closeButton == null) {
            closeButton = new JButton();
        }
        return closeButton;
    }
}

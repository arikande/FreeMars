package org.freemars.controller;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Window;
import javax.swing.Box;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;
import org.freemars.ui.image.FreeMarsImageManager;
import org.freemars.ui.image.ImagePanel;
import org.freerealm.player.Player;

/**
 *
 * @author Deniz ARIKAN
 */
public class AISplashDisplayer {

    private static JDialog dialog;
    private static final JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
    private static final JPanel centerPanel = new JPanel(new BorderLayout(10, 10));
    private static final JPanel pageEndPanel = new JPanel(new BorderLayout());
    private static final ImagePanel flagImagePanel = new ImagePanel();
    private static final JProgressBar progressBar = new JProgressBar();
    private static final JPanel infoPanel = new JPanel(new GridLayout(0, 1, 0, 5));
    private static final JLabel infoLabel = new JLabel();
    private static final JLabel currentProcessLabel = new JLabel();

    static {
        flagImagePanel.setPreferredSize(new Dimension(36, 24));
        flagImagePanel.setImageHorizontalAlignment(ImagePanel.CENTER_ALIGNMENT);
        flagImagePanel.setImageVerticalAlignment(ImagePanel.CENTER_ALIGNMENT);
        progressBar.setIndeterminate(true);
        infoPanel.add(infoLabel);
        infoPanel.add(currentProcessLabel);
        centerPanel.add(flagImagePanel, BorderLayout.LINE_START);
        centerPanel.add(infoPanel, BorderLayout.CENTER);
        centerPanel.add(Box.createHorizontalStrut(10), BorderLayout.LINE_END);
        pageEndPanel.add(Box.createHorizontalStrut(5), BorderLayout.LINE_START);
        pageEndPanel.add(progressBar, BorderLayout.CENTER);
        pageEndPanel.add(Box.createHorizontalStrut(5), BorderLayout.LINE_END);
        pageEndPanel.add(Box.createVerticalStrut(5), BorderLayout.PAGE_END);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(pageEndPanel, BorderLayout.PAGE_END);
    }

    public static void display(FreeMarsController freeMarsController, Player player) {
        infoLabel.setText(player.getName() + " " + "playing");
        flagImagePanel.setImage(FreeMarsImageManager.getImage(player.getNation()));
        Window win = SwingUtilities.getWindowAncestor(freeMarsController.getCurrentFrame());
        if (dialog == null) {
            dialog = new JDialog(win, Dialog.ModalityType.APPLICATION_MODAL);
            dialog.setUndecorated(true);
            dialog.add(mainPanel);
            dialog.pack();
            dialog.setLocationRelativeTo(win);
        }
        dialog.setVisible(true);
    }

    public static void hide() {
        dialog.dispose();
    }

    public static void setCurrentProcessLabelText(String text) {
        currentProcessLabel.setText(text);
        try {
            Thread.sleep(50);
        } catch (Exception e) {
        }
    }

}

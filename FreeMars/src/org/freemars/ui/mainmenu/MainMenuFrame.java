package org.freemars.ui.mainmenu;

import java.awt.Color;
import javax.swing.JFrame;
import org.freemars.ui.image.FreeMarsImageManager;
import org.freemars.ui.image.ImagePanel;
import org.freemars.ui.util.FreeMarsFrame;

/**
 *
 * @author Deniz ARIKAN
 */
public class MainMenuFrame extends FreeMarsFrame {

    private MenuWindow menuWindow;
    private MainMenuBackgroundImagePanel mainMenuBackgroundPanel;

    public MainMenuFrame() {
        setContentPane(getMainMenuBackgroundPanel());
        pack();
        setExtendedState(getExtendedState() | JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    }

    @Override
    public void setVisible(boolean b) {
        if (!b) {
            hideMainMenuWindow();
        }
        super.setVisible(b);
    }

    public void displayMainMenuWindow() {
        getMenuWindow().setVisible(true);
    }

    public void hideMainMenuWindow() {
        getMenuWindow().setVisible(false);
    }

    private MainMenuBackgroundImagePanel getMainMenuBackgroundPanel() {
        if (mainMenuBackgroundPanel == null) {
            mainMenuBackgroundPanel = new MainMenuBackgroundImagePanel(FreeMarsImageManager.getImage("MAIN_MENU_BACKGROUND"));
            mainMenuBackgroundPanel.setBackground(Color.BLACK);
            mainMenuBackgroundPanel.setOpaque(true);
            mainMenuBackgroundPanel.setImageHorizontalAlignment(ImagePanel.CENTER_ALIGNMENT);
        }
        return mainMenuBackgroundPanel;
    }

    public MenuWindow getMenuWindow() {
        if (menuWindow == null) {
            menuWindow = new MenuWindow(this);
        }
        return menuWindow;
    }
}

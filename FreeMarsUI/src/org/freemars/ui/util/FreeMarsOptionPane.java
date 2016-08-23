package org.freemars.ui.util;

import java.awt.Component;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import org.freemars.ui.image.FreeMarsImageManager;

/**
 *
 * @author Deniz ARIKAN
 */
public class FreeMarsOptionPane {

    public static void showMessageDialog(Component parent, Object message) {
        Icon marsImage = new ImageIcon(FreeMarsImageManager.getImage("MARS", 48, 48));
        JOptionPane.showMessageDialog(parent, message, "", JOptionPane.INFORMATION_MESSAGE, marsImage);
    }

    public static void showMessageDialog(Component parent, Object message, String title) {
        Icon marsImage = new ImageIcon(FreeMarsImageManager.getImage("MARS", 48, 48));
        JOptionPane.showMessageDialog(parent, message, title, JOptionPane.INFORMATION_MESSAGE, marsImage);
    }
}

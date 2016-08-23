package org.freemars.ui.util;

import java.awt.Image;
import javax.swing.JFrame;
import org.freemars.ui.image.FreeMarsImageManager;

/**
 *
 * @author Deniz ARIKAN
 */
public class FreeMarsFrame extends JFrame {

    private static final String TITLE = "Free Mars";

    public FreeMarsFrame() {
        super();
        setTitle(TITLE);
        Image marsImage = FreeMarsImageManager.getImage("FREE_MARS_FRAME_IMAGE");
        setIconImage(marsImage);
    }
}

package org.freemars.mission;

import java.awt.Dimension;
import java.awt.Frame;
import org.freemars.ui.util.FreeMarsImageDialog;

/**
 *
 * @author Deniz ARIKAN
 */
public class MissionDialog extends FreeMarsImageDialog {

    private static final int FRAME_WIDTH = 720;
    private static final int FRAME_HEIGHT = 470;

    public MissionDialog(Frame owner) {
        super(owner);
        setImagePreferredSize(new Dimension(250, 0));
    }

    public void display() {
        pack();
        Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width - FRAME_WIDTH) / 2, (screenSize.height - FRAME_HEIGHT) / 2, FRAME_WIDTH, FRAME_HEIGHT);
        setVisible(true);
        toFront();
    }

    public void appendMissionExplanation(String string) {
        appendText(string);
    }
}

package org.freemars.earth.ui;

import java.awt.Dimension;
import java.awt.Frame;
import org.freemars.ui.image.FreeMarsImageManager;
import org.freemars.ui.util.FreeMarsImageDialog;

/**
 *
 * @author Deniz ARIKAN
 */
public class ExpeditionaryForceDefeatedDialog extends FreeMarsImageDialog {

    private static final int FRAME_WIDTH = 500;
    private static final int FRAME_HEIGHT = 230;

    public ExpeditionaryForceDefeatedDialog(Frame owner) {
        super(owner);
        setImagePreferredSize(new Dimension(130, 250));
        setImage(FreeMarsImageManager.getImage("EXPEDITIONARY_FORCE_DEFEATED"));
        appendText("\n\n");
        appendText("Sir\n\n");
        appendText("The expeditionary force that was sent to restore Earth rule over ");
        appendText("our Martian colonies have been defeated. We have shown them that ");
        appendText("Mars is free and will remain free.\n\n");
        appendText("Colonial defence force");
    }

    public void display() {
        display(FRAME_WIDTH, FRAME_HEIGHT);
    }
}

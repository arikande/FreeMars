package org.freemars.earth.ui;

import java.awt.Dimension;
import java.awt.Frame;
import java.util.List;
import org.freemars.ui.image.FreeMarsImageManager;
import org.freemars.ui.util.FreeMarsImageDialog;
import org.freerealm.unit.Unit;

/**
 *
 * @author Deniz ARIKAN
 */
public class UnitsSeizedDialog extends FreeMarsImageDialog {

    private static final int FRAME_WIDTH = 530;
    private static final int FRAME_HEIGHT = 420;

    public UnitsSeizedDialog(Frame owner, List<Unit> seizedUnits) {
        super(owner);
        setImagePreferredSize(new Dimension(130, 250));
        setImage(FreeMarsImageManager.getImage("UNITS_SEIZED_DIALOG"));
        appendText("<br><br>");
        appendText("Sir,<br><br>");
        appendText("Our units that were waiting in orbit of Earth have ");
        appendText("been seized by Earth government. We should have transferred ");
        appendText("them to Mars when we declared our independence.<br><br>");
        if (seizedUnits != null) {
            appendText("Following units were seized<br><br>");
            for (Unit unit : seizedUnits) {
                appendText("* " + unit.getName() + " (" + unit.getType().getName() + ")<br>");
            }
            appendText("<br>");
        }
        appendText("We also expect Earth expeditionary force to <b>land in 4 months</b>. ");
        appendText("Our colonial defences must be ready to meet them.<br><br>");
        appendText("Colonial defence force");
    }

    public void display() {
        pack();
        Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width - FRAME_WIDTH) / 2, (screenSize.height - FRAME_HEIGHT) / 2, FRAME_WIDTH, FRAME_HEIGHT);
        setVisible(true);
        toFront();
    }
}

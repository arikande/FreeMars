package org.freemars.earth.ui;

import java.awt.Dimension;
import java.awt.Frame;
import org.freemars.ui.image.FreeMarsImageManager;
import org.freemars.ui.util.FreeMarsImageDialog;

/**
 *
 * @author Deniz ARIKAN
 */
public class IndependenceDeclaredDialog extends FreeMarsImageDialog {

    private static final int FRAME_WIDTH = 580;
    private static final int FRAME_HEIGHT = 450;

    public IndependenceDeclaredDialog(Frame owner, int expeditionaryForceTravelTurns) {
        super(owner);
        setImagePreferredSize(new Dimension(130, 250));
        setImage(FreeMarsImageManager.getImage("INDEPENDENCE_DECLARED"));
        appendText("<br>");
        appendText("Sir<br><br>");
        appendText("This is a historic day indeed, we have declared our ");
        appendText("independence from Earth rule. From now on, colonists of Mars are free ");
        appendText("to shape their own destiny, unbounded by Earh dictatorship.<br><br>");
        appendText("Declaration of independence is only the first step to an ");
        appendText("independent Mars. Earth government will surely try to ");
        appendText("regain control on the Red planet by force of arms. Soon ");
        appendText("we will have to fight for our freedom.<br><br>");
        appendText("We expect Earth expeditionary force to <b>land in ");
        appendText(expeditionaryForceTravelTurns);
        appendText(" months</b>. ");
        appendText("Our colonial defences must be ready to meet them.<br><br>");
        appendText("Earth government will seize our spaceships or units ");
        appendText("that are traveling to or already in orbit of Earth.");
        appendText("We must call them back to Mars before it is too late.");
        appendText("<br><br>");
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

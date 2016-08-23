package org.freemars.ui.help;

import java.awt.Frame;
import java.net.URL;
import javax.help.HelpSet;
import javax.help.JHelp;
import org.freemars.ui.util.FreeMarsDialog;

/**
 *
 * @author Deniz ARIKAN
 */
public class FreeMarsHelpDialog extends FreeMarsDialog {

    private final int DIALOG_WIDTH = 1020;
    private final int DIALOG_HEIGHT = 770;
    private JHelp helpViewer;

    public FreeMarsHelpDialog(Frame owner) {
        super(owner);
        setModal(true);
        try {
            URL url = HelpSet.findHelpSet(null, "help/freemars.hs");
            helpViewer = new JHelp(new HelpSet(null, url));
            helpViewer.setCurrentID("Mars.Introduction");
            setTitle("Marsopedia");
        } catch (Exception exception) {
            System.err.println("API Help Set not found");
        }
    }

    public void display(String helpId) {
        if (helpId != null) {
            helpViewer.setCurrentID(helpId);
        }
        getContentPane().add(helpViewer);
        super.display(DIALOG_WIDTH, DIALOG_HEIGHT);
    }
}

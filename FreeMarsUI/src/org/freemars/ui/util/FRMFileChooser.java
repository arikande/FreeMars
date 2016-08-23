package org.freemars.ui.util;

import java.io.File;
import javax.swing.JFileChooser;

/**
 *
 * @author Deniz ARIKAN
 */
public class FRMFileChooser extends JFileChooser {

    public FRMFileChooser() {
        String userHomeDirectory = System.getProperty("user.home");
        File freeMarsDirectory = new File(userHomeDirectory + System.getProperty("file.separator") + "FreeMars" + System.getProperty("file.separator"));
        setCurrentDirectory(freeMarsDirectory);
        FRMFileFilter fRMFileFilter = new FRMFileFilter();
        setFileFilter(fRMFileFilter);
        addChoosableFileFilter(fRMFileFilter);
        setAcceptAllFileFilterUsed(false);
        setFileSelectionMode(JFileChooser.FILES_ONLY);
    }
}

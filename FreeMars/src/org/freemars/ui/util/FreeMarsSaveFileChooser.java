package org.freemars.ui.util;

import java.io.File;
import javax.swing.JFileChooser;

/**
 *
 * @author Deniz ARIKAN
 */
public class FreeMarsSaveFileChooser extends JFileChooser {

    public FreeMarsSaveFileChooser() {
        super(new File(System.getProperty("user.home") + System.getProperty("file.separator") + "FreeMars" + System.getProperty("file.separator")));
        FreeMarsSaveFileFilter freeMarsFileFilter = new FreeMarsSaveFileFilter();
        setFileFilter(freeMarsFileFilter);
        addChoosableFileFilter(freeMarsFileFilter);
        setAcceptAllFileFilterUsed(false);
        setFileSelectionMode(JFileChooser.FILES_ONLY);
    }
}

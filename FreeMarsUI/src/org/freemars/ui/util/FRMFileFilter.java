package org.freemars.ui.util;

import java.io.File;
import javax.swing.filechooser.FileFilter;

/**
 *
 * @author Deniz ARIKAN
 */
public class FRMFileFilter extends FileFilter {

    @Override
    public boolean accept(File f) {
        if (f.isDirectory()) {
            return true;
        }
        if (f.getName().endsWith(".frm")) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String getDescription() {
        return "FreeMars map files";
    }
}

package org.freemars.ui.util;

import java.io.File;
import javax.swing.filechooser.FileFilter;

/**
 *
 * @author Deniz ARIKAN
 */
public class FreeMarsSaveFileFilter extends FileFilter {

    @Override
    public boolean accept(File f) {
        if (f.isDirectory()) {
            return true;
        }
        if (f.getName().endsWith(".fms")) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String getDescription() {
        return "Free Mars save game files";
    }
}

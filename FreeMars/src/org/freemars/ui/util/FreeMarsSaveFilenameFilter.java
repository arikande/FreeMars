package org.freemars.ui.util;

import java.io.File;
import java.io.FilenameFilter;

/**
 *
 * @author Deniz ARIKAN
 */
public class FreeMarsSaveFilenameFilter implements FilenameFilter {

    public boolean accept(File dir, String name) {
        return name.endsWith(".fms");
    }
}

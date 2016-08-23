package org.freemars;

import java.io.File;
import javax.swing.ToolTipManager;
import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalLookAndFeel;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.freemars.about.AddCommand;
import org.freemars.about.ClearConsoleCommand;
import org.freemars.about.ConsoleCommandExecutor;
import org.freemars.about.DisplayCommand;
import org.freemars.about.EarthConsoleCommand;
import org.freemars.about.ExitConsoleCommand;
import org.freemars.about.RemoveCommand;
import org.freemars.ui.image.FreeMarsImageManager;
import org.freemars.ui.util.FreeMarsTheme;
import org.freerealm.xmlwrapper.TagManager;

/**
 *
 * @author Deniz ARIKAN
 */
public class FreeMarsInitializer {

    private static final String LOG4J_CONFIGURATION = "/config/log4j.xml";

    protected static void initializeLogger() {
        DOMConfigurator.configure(FreeMarsInitializer.class.getResource(LOG4J_CONFIGURATION));
        Logger.getLogger(FreeMarsInitializer.class).info("Launching Free Mars. Logger initialized.");
    }

    protected static void initializeGameFolders() {
        File gameDirectory = (new File(System.getProperty("user.home") + System.getProperty("file.separator") + "FreeMars"));
        Logger.getLogger(FreeMarsInitializer.class).info("Checking Free Mars game directory \"" + gameDirectory + "\".");
        if (!gameDirectory.exists()) {
            boolean success = gameDirectory.mkdirs();
            if (success) {
                Logger.getLogger(FreeMarsInitializer.class).info("Free Mars directory created.");
            } else {
                Logger.getLogger(FreeMarsInitializer.class).info("Free Mars directory creation failed.");
            }
        } else {
            Logger.getLogger(FreeMarsInitializer.class).info("Free Mars directory already exists, continuing.");
        }
    }

    protected static void initializeTags() {
        TagManager.readDefaultTags();
        Logger.getLogger(FreeMarsInitializer.class).info("Free Realm object tags initialized.");
        TagManager.readFile("config/tags.xml");
        Logger.getLogger(FreeMarsInitializer.class).info("Free Mars object tags initialized.");
    }

    protected static void initializeUI() {
        FreeMarsImageManager.init();
        ToolTipManager.sharedInstance().setInitialDelay(100);
        ToolTipManager.sharedInstance().setDismissDelay(20000);
        MetalLookAndFeel.setCurrentTheme(new FreeMarsTheme());
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
        } catch (Exception exception) {
            Logger.getLogger(FreeMarsInitializer.class).info("Exception while setting look and feel.");
            Logger.getLogger(FreeMarsInitializer.class).info("Exception message :");
            Logger.getLogger(FreeMarsInitializer.class).info(exception.getMessage());
        }
        Logger.getLogger(FreeMarsInitializer.class).info("UI initialized.");
    }

    protected static void initializeConsoleCommands() {
        ConsoleCommandExecutor.addConsoleCommand(ClearConsoleCommand.class);
        ConsoleCommandExecutor.addConsoleCommand(DisplayCommand.class);
        ConsoleCommandExecutor.addConsoleCommand(ExitConsoleCommand.class);
        ConsoleCommandExecutor.addConsoleCommand(RemoveCommand.class);
        ConsoleCommandExecutor.addConsoleCommand(AddCommand.class);
        ConsoleCommandExecutor.addConsoleCommand(EarthConsoleCommand.class);
    }

}

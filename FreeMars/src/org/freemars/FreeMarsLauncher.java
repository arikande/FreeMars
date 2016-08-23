package org.freemars;

import javax.swing.JPanel;
import org.apache.log4j.Logger;
import org.freemars.controller.FreeMarsController;
import org.freerealm.xmlwrapper.TagManager;

/**
 * 
 * @author Deniz ARIKAN
 */
public class FreeMarsLauncher extends JPanel {

	private static final Logger logger = Logger.getLogger(FreeMarsLauncher.class);

	public static void main(final String[] args) {
		initialize();
		final FreeMarsController freeMarsController = new FreeMarsController();
		freeMarsController.initGameFrame();
		freeMarsController.startViewCommandExecutionThread();
		freeMarsController.startFreeMarsThreads();
		TagManager.setObjectInPool("freeMarsController", freeMarsController);
		javax.swing.SwingUtilities.invokeLater(new Runnable() {

			public void run() {
				logger.info("Displaying main menu.");
				freeMarsController.displayMainMenuFrame();
				freeMarsController.displayMainMenuWindow();
			}
		});
	}

	private static void initialize() {
		FreeMarsInitializer.initializeLogger();
		FreeMarsInitializer.initializeGameFolders();
		FreeMarsInitializer.initializeConsoleCommands();
		FreeMarsInitializer.initializeUI();
		FreeMarsInitializer.initializeTags();
		logger.info("Free Mars game initialized successfully.");
	}

}

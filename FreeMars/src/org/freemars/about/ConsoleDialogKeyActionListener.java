package org.freemars.about;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.freemars.controller.FreeMarsController;

/**
 *
 * @author Deniz ARIKAN
 */
public class ConsoleDialogKeyActionListener implements ActionListener {

    private final FreeMarsController freeMarsController;
    private final ConsoleDialog consoleDialog;

    public ConsoleDialogKeyActionListener(FreeMarsController freeMarsController, ConsoleDialog consoleDialog) {
        this.freeMarsController = freeMarsController;
        this.consoleDialog = consoleDialog;
    }

    public void actionPerformed(ActionEvent ae) {
        String commandText = consoleDialog.getCommandTextFieldText().trim();
        StringBuilder commandOutput = new StringBuilder();
        commandOutput.append(">");
        commandOutput.append(commandText);
        commandOutput.append(System.getProperty("line.separator"));
        String[] commandParts = commandText.split("\\s+");
        String output = ConsoleCommandExecutor.executeCommand(freeMarsController, consoleDialog, commandParts);
        commandOutput.append(output);
        consoleDialog.appendCommandOutput(commandOutput.toString());
        consoleDialog.setCommandTextFieldText("");
    }

}

package org.freemars.controller.viewcommand;

import java.io.BufferedInputStream;

import org.freemars.WavPlayer;
import org.freerealm.command.AbstractCommand;
import org.freerealm.executor.CommandResult;

/**
 * @author Deniz ARIKAN
 */
public class PlaySoundCommand extends AbstractCommand implements ViewCommand {

    private final String fileName;

    public PlaySoundCommand(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public String toString() {
        return "PlaySound";
    }

    public CommandResult execute() {
        WavPlayer.play(new BufferedInputStream(ClassLoader.getSystemResourceAsStream(fileName)));
        return new CommandResult(CommandResult.RESULT_OK, "");
    }

    public boolean isBlockingViewExecutionThread() {
        return false;
    }

}

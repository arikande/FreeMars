package org.freemars.colony.manager;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;

/**
 *
 * @author Deniz ARIKAN
 */
public class SetColoniesTableModeAction extends AbstractAction {

    private final ColonyManagerDialog colonyManagerDialog;
    private final int mode;

    public SetColoniesTableModeAction(ColonyManagerDialog colonyManagerDialog, int mode) {
        this.colonyManagerDialog = colonyManagerDialog;
        this.mode = mode;
    }

    public void actionPerformed(ActionEvent e) {
        colonyManagerDialog.setMode(mode);
    }
}

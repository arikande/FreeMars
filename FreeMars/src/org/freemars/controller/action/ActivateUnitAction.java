package org.freemars.controller.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import org.freemars.controller.FreeMarsController;
import org.freerealm.command.SetActiveUnitCommand;
import org.freerealm.unit.Unit;

/**
 *
 * @author Deniz ARIKAN
 */
public class ActivateUnitAction extends AbstractAction {

    private final FreeMarsController controller;
    private final Unit unit;

    public ActivateUnitAction(FreeMarsController controller, Unit unit) {
        super("Activate unit");
        this.controller = controller;
        this.unit = unit;
    }

    public void actionPerformed(ActionEvent e) {
        controller.execute(new SetActiveUnitCommand(controller.getFreeMarsModel().getActivePlayer(), unit));
    }
}

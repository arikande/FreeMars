package org.freemars.unit.manager;

import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.event.MouseInputAdapter;

import org.freemars.controller.FreeMarsController;
import org.freemars.controller.action.ActivateUnitAction;
import org.freerealm.command.SetActiveUnitCommand;
import org.freerealm.player.Player;
import org.freerealm.unit.Unit;

/**
 * 
 * @author Deniz ARIKAN
 */
public class UnitsTableMouseInputAdapter extends MouseInputAdapter {

	private final FreeMarsController freeMarsController;
	private final UnitManagerDialog unitManagerDialog;
	private final Player player;

	public UnitsTableMouseInputAdapter(FreeMarsController freeMarsController, UnitManagerDialog unitManagerDialog, Player player) {
		this.freeMarsController = freeMarsController;
		this.unitManagerDialog = unitManagerDialog;
		this.player = player;
	}

	@Override
	public void mouseMoved(MouseEvent mouseEvent) {
		int row = unitManagerDialog.getUnitsTableRowAtPoint(mouseEvent.getPoint());
		unitManagerDialog.setUnitsTableSelectionInterval(row, row);
	}

	@Override
	public void mouseClicked(MouseEvent mouseEvent) {
		int unitId = unitManagerDialog.getUnitIdAtPoint(mouseEvent.getPoint());
		Unit unit = player.getUnit(unitId);
		if (unit.getCoordinate() != null) {
			if ((mouseEvent.getModifiers() & InputEvent.BUTTON1_MASK) == InputEvent.BUTTON1_MASK) {
				handleLeftClick(mouseEvent, unit);
			} else if ((mouseEvent.getModifiers() & InputEvent.BUTTON3_MASK) == InputEvent.BUTTON3_MASK) {
				handleRightClick(mouseEvent, unit);
			}
		}
	}

	private void handleLeftClick(MouseEvent mouseEvent, Unit unit) {
		freeMarsController.addCommandToQueue(new SetActiveUnitCommand(player, unit));
	}

	private void handleRightClick(MouseEvent mouseEvent, Unit unit) {
		JPopupMenu popup = new JPopupMenu();
		if (freeMarsController.isActivateEnabledForUnit(unit)) {
			popup.add(new JMenuItem(new ActivateUnitAction(freeMarsController, unit)));
		}
		ClearUnitOrdersFromManagerAction clearUnitOrdersFromManagerAction = new ClearUnitOrdersFromManagerAction(freeMarsController, unitManagerDialog, unit);
		if (clearUnitOrdersFromManagerAction.checkEnabled()) {
			popup.add(new JMenuItem(clearUnitOrdersFromManagerAction));
		}
		FortifyUnitFromManagerAction fortifyUnitFromManagerAction = new FortifyUnitFromManagerAction(freeMarsController, unitManagerDialog, unit);
		if (fortifyUnitFromManagerAction.checkEnabled()) {
			popup.add(new JMenuItem(fortifyUnitFromManagerAction));
		}
		SentryUnitFromManagerAction sentryUnitFromManagerAction = new SentryUnitFromManagerAction(freeMarsController, unitManagerDialog, unit);
		if (sentryUnitFromManagerAction.checkEnabled()) {
			popup.add(new JMenuItem(sentryUnitFromManagerAction));
		}
		popup.add(new JMenuItem(new DisplayRenameUnitDialogFromManagerAction(freeMarsController, unitManagerDialog, unit)));
		popup.add(new JMenuItem(new DisbandUnitFromManagerAction(freeMarsController, unitManagerDialog, unit)));
		popup.show((JComponent) mouseEvent.getSource(), mouseEvent.getX(), mouseEvent.getY());
	}
}

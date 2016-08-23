package org.freemars.editor.controller.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.AbstractButton;
import org.freemars.editor.view.ConvertTilesDialog;

/**
 *
 * @author Deniz ARIKAN
 */
public class ConvertTilesDialogButtonListener implements ActionListener {

    private final ConvertTilesDialog convertTilesDialog;

    public ConvertTilesDialogButtonListener(ConvertTilesDialog convertTilesDialog) {
        this.convertTilesDialog = convertTilesDialog;
    }

    public void actionPerformed(ActionEvent actionEvent) {
        String actionCommand = ((AbstractButton) actionEvent.getSource()).getActionCommand();
        String[] commandParts = actionCommand.split("\\.");
        if ("TILETYPE".equals(commandParts[1])) {
            int tileTypeId = Integer.parseInt(commandParts[2]);
            if ("TO".equals(commandParts[0])) {
                convertTilesDialog.setToTileTypeId(tileTypeId);
            }
            if ("FROM".equals(commandParts[0])) {
                convertTilesDialog.setFromTileTypeId(tileTypeId);
            }
        } else if ("VEGETATIONTYPE".equals(commandParts[1])) {
            int vegetationTypeId = Integer.parseInt(commandParts[2]);
            if ("TO".equals(commandParts[0])) {
                convertTilesDialog.setToVegetationTypeId(vegetationTypeId);
            }
            if ("FROM".equals(commandParts[0])) {
                convertTilesDialog.setFromVegetationTypeId(vegetationTypeId);
            }
        }
    }

}

package org.freemars.editor.controller.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.freemars.editor.controller.EditorController;
import org.freemars.editor.controller.command.SetBrushBonusResourceCommand;
import org.freerealm.resource.bonus.BonusResource;

/**
 *
 * @author Deniz ARIKAN
 */
public class BonusResourceMenuItemActionListener implements ActionListener {

    private final EditorController editorController;
    private final BonusResource bonusResource;

    public BonusResourceMenuItemActionListener(EditorController editorController, BonusResource bonusResource) {
        this.editorController = editorController;
        this.bonusResource = bonusResource;
    }

    public void actionPerformed(ActionEvent actionEvent) {
        editorController.execute(new SetBrushBonusResourceCommand(editorController, bonusResource));
    }

}

package org.freemars.editor.controller.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.freemars.editor.controller.EditorController;
import org.freemars.editor.controller.command.SetBrushVegetationTypeCommand;
import org.freerealm.vegetation.VegetationType;

/**
 *
 * @author Deniz ARIKAN
 */
public class VegetationTypeMenuItemActionListener implements ActionListener {

    private final EditorController editorController;
    private final VegetationType vegetationType;

    public VegetationTypeMenuItemActionListener(EditorController editorController, VegetationType vegetationType) {
        this.editorController = editorController;
        this.vegetationType = vegetationType;
    }

    public void actionPerformed(ActionEvent actionEvent) {
        editorController.execute(new SetBrushVegetationTypeCommand(editorController, vegetationType));
    }

}

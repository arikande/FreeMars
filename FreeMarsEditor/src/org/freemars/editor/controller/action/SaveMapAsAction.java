package org.freemars.editor.controller.action;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileWriter;
import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import org.freemars.editor.controller.EditorController;
import org.freemars.editor.controller.command.SaveMapCommand;
import org.freemars.ui.util.FRMFileChooser;
import org.freemars.ui.util.FreeMarsOptionPane;
import org.freerealm.map.FreeRealmMap;
import org.freerealm.map.Map;
import org.freerealm.xmlwrapper.map.FreeRealmMapXMLConverter;

/**
 *
 * @author Deniz ARIKAN
 */
public class SaveMapAsAction extends AbstractAction {

    private final EditorController editorController;

    public SaveMapAsAction(EditorController editorController) {
        super("Save map as...");
        this.editorController = editorController;
    }

    public void actionPerformed(ActionEvent e) {
        FRMFileChooser frmFileChooser = new FRMFileChooser();
        if (editorController.getEditorModel().getFileName() != null) {
            frmFileChooser.setSelectedFile(new File(editorController.getEditorModel().getFileName()));
        }
        if (frmFileChooser.showSaveDialog(editorController.getEditorFrame()) == JFileChooser.APPROVE_OPTION) {
            String saveFileName = frmFileChooser.getSelectedFile().getAbsolutePath();
            if (!saveFileName.endsWith(".frm")) {
                saveFileName = saveFileName + ".frm";
            }
            editorController.execute(new SaveMapCommand(editorController, saveFileName));
            FreeMarsOptionPane.showMessageDialog(editorController.getEditorFrame(), "Save complete");
        }
    }
}

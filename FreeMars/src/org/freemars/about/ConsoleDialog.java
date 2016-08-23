package org.freemars.about;

import java.awt.BorderLayout;
import java.awt.Font;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import org.freemars.ui.util.FreeMarsDialog;

/**
 *
 * @author Deniz ARIKAN
 */
public class ConsoleDialog extends FreeMarsDialog {

    private final int FRAME_WIDTH = 520;
    private final int FRAME_HEIGHT = 360;
    private JPanel centerPanel;
    private JPanel pageEndPanel;
    private JScrollPane commandOutputScrollPane;
    private JTextArea commandOutputTextArea;
    private JTextField commandTextField;

    public ConsoleDialog(JFrame parent) {
        super(parent);
        setModal(true);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setTitle("Console");
    }

    public void display() {
        getContentPane().setLayout(new BorderLayout());
        initializeWidgets();
        super.display(FRAME_WIDTH, FRAME_HEIGHT);
    }

    public String getCommandTextFieldText() {
        return getCommandTextField().getText();
    }

    public void setCommandTextFieldText(String text) {
        getCommandTextField().setText(text);
    }

    public void clearCommandOutput() {
        getCommandOutputTextArea().setText("");
    }

    public void appendCommandOutput(String text) {
        getCommandOutputTextArea().append(text);
    }

    private void initializeWidgets() {
        getContentPane().add(getCenterPanel(), BorderLayout.CENTER);
        getContentPane().add(getPageEndPanel(), BorderLayout.PAGE_END);
    }

    private JPanel getCenterPanel() {
        if (centerPanel == null) {
            centerPanel = new JPanel(new BorderLayout());
            centerPanel.add(getCommandOutputScrollPane(), BorderLayout.CENTER);
        }
        return centerPanel;
    }

    private JPanel getPageEndPanel() {
        if (pageEndPanel == null) {
            pageEndPanel = new JPanel(new BorderLayout());
            pageEndPanel.add(getCommandTextField(), BorderLayout.CENTER);
        }
        return pageEndPanel;
    }

    private JScrollPane getCommandOutputScrollPane() {
        if (commandOutputScrollPane == null) {
            commandOutputScrollPane = new JScrollPane(getCommandOutputTextArea());
        }
        return commandOutputScrollPane;
    }

    private JTextArea getCommandOutputTextArea() {
        if (commandOutputTextArea == null) {
            commandOutputTextArea = new JTextArea();
            commandOutputTextArea.setEditable(false);
            commandOutputTextArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        }
        return commandOutputTextArea;
    }

    private JTextField getCommandTextField() {
        if (commandTextField == null) {
            commandTextField = new JTextField();
        }
        return commandTextField;
    }
}

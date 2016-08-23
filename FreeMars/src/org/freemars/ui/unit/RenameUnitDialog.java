package org.freemars.ui.unit;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import org.freemars.ui.image.ImagePanel;
import org.freemars.ui.util.FreeMarsDialog;

/**
 *
 * @author Deniz ARIKAN
 */
public class RenameUnitDialog extends FreeMarsDialog {

    private final int FRAME_WIDTH = 210;
    private final int FRAME_HEIGHT = 145;
    private JPanel centerPanel;
    private JPanel footerPanel;
    private ImagePanel unitImagePanel;
    private JPanel unitRenamePanel;
    private JLabel nameLabel;
    private JTextField nameTextField;
    private JButton confirmButton;

    public RenameUnitDialog(JFrame parent) {
        super(parent);
        setModal(true);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setTitle("Rename unit");
        getContentPane().setLayout(new BorderLayout(5, 5));
        initializeWidgets();
        pack();
    }

    public void display() {
        display(FRAME_WIDTH, FRAME_HEIGHT);
    }

    @Override
    protected JRootPane createRootPane() {
        ActionListener actionListener = new ActionListener() {

            public void actionPerformed(ActionEvent actionEvent) {
                getConfirmButton().doClick();
            }
        };
        JRootPane modifiedRootPane = super.createRootPane();
        KeyStroke stroke = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0);
        modifiedRootPane.registerKeyboardAction(actionListener, stroke, JComponent.WHEN_IN_FOCUSED_WINDOW);
        return modifiedRootPane;
    }

    public void setCurrentUnitName(String value) {
        getNameLabel().setText(value);
        getNameTextField().setText(value);
    }

    public void setConfirmButtonAction(Action action) {
        getConfirmButton().setAction(action);
        getConfirmButton().setText("Confirm");
    }

    public String getNameTextFieldValue() {
        return getNameTextField().getText();
    }

    public void setNameTextFieldValue(String name) {
        getNameTextField().setText(name);
    }

    public void setUnitImage(Image image) {
        getUnitImagePanel().setImage(image);
    }

    public void setNameTextFieldSelected() {
        getNameTextField().setSelectionStart(0);
        getNameTextField().setSelectionEnd(getNameTextFieldValue().length());
    }

    public void setNameTextFieldFocused() {
        getNameTextField().requestFocus();
    }

    private void initializeWidgets() {
        add(getNameLabel(), BorderLayout.PAGE_START);
        add(getCenterPanel(), BorderLayout.CENTER);
        add(getFooterPanel(), BorderLayout.PAGE_END);
    }

    private JPanel getCenterPanel() {
        if (centerPanel == null) {
            centerPanel = new JPanel(new BorderLayout());
            centerPanel.add(getUnitImagePanel(), BorderLayout.LINE_START);
            centerPanel.add(getUnitRenamePanel(), BorderLayout.CENTER);
        }
        return centerPanel;
    }

    private JPanel getFooterPanel() {
        if (footerPanel == null) {
            footerPanel = new JPanel();
            footerPanel.add(getConfirmButton());
        }
        return footerPanel;
    }

    private ImagePanel getUnitImagePanel() {
        if (unitImagePanel == null) {
            unitImagePanel = new ImagePanel();
            unitImagePanel.setPreferredSize(new Dimension(80, 80));
        }
        return unitImagePanel;
    }

    private JPanel getUnitRenamePanel() {
        if (unitRenamePanel == null) {
            unitRenamePanel = new JPanel(new GridLayout(2, 0));
            unitRenamePanel.add(getNameLabel());
            unitRenamePanel.add(getNameTextField());
        }
        return unitRenamePanel;
    }

    private JLabel getNameLabel() {
        if (nameLabel == null) {
            nameLabel = new JLabel();
        }
        return nameLabel;
    }

    private JTextField getNameTextField() {
        if (nameTextField == null) {
            nameTextField = new JTextField();
            nameTextField.setFont(nameTextField.getFont().deriveFont(15f));
        }
        return nameTextField;
    }

    private JButton getConfirmButton() {
        if (confirmButton == null) {
            confirmButton = new JButton();
        }
        return confirmButton;
    }
}

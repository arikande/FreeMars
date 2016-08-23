package org.freemars.colonydialog;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.InputStream;
import javax.swing.Action;
import javax.swing.Box;
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
public class RenameColonyDialog extends FreeMarsDialog {

    private final int FRAME_WIDTH = 300;
    private final int FRAME_HEIGHT = 150;
    private JPanel centerPanel;
    private JPanel footerPanel;
    private ImagePanel colonyImagePanel;
    private JPanel namingPanel;
    private JLabel nameLabel;
    private JTextField nameTextField;
    private JButton confirmButton;

    public RenameColonyDialog(JFrame parent) {
        super(parent);
        setModal(true);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setTitle("Rename colony");
        getContentPane().setLayout(new BorderLayout(5, 5));
        initializeWidgets();
        pack();
    }

    public void display() {
        display(FRAME_WIDTH, FRAME_HEIGHT);
    }

    public void setCurrentColonyName(String value) {
        getNameLabel().setText(value);
        getNameTextField().setText(value);
    }

    public void setCurrentColonyImage(Image image) {
        getColonyImagePanel().setImage(image);
        getColonyImagePanel().setPreferredSize(new Dimension(image.getWidth(null), image.getHeight(null)));
    }

    public void setConfirmButtonAction(Action action) {
        getConfirmButton().setAction(action);
        getConfirmButton().setText("Confirm");
    }

    public String getNameTextFieldValue() {
        return getNameTextField().getText();
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

    private void initializeWidgets() {
        add(getCenterPanel(), BorderLayout.CENTER);
        add(getFooterPanel(), BorderLayout.PAGE_END);
    }

    private JPanel getCenterPanel() {
        if (centerPanel == null) {
            centerPanel = new JPanel(new BorderLayout(5, 5));
            centerPanel.add(getColonyImagePanel(), BorderLayout.LINE_START);
            centerPanel.add(getNamingPanel(), BorderLayout.CENTER);
            centerPanel.add(Box.createHorizontalStrut(5), BorderLayout.LINE_END);
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

    private ImagePanel getColonyImagePanel() {
        if (colonyImagePanel == null) {
            colonyImagePanel = new ImagePanel();
            colonyImagePanel.setImageVerticalAlignment(ImagePanel.CENTER_ALIGNMENT);
        }
        return colonyImagePanel;
    }

    private JPanel getNamingPanel() {
        if (namingPanel == null) {
            namingPanel = new JPanel(new GridLayout(0, 1, 0, 5));
            namingPanel.add(getNameLabel());
            namingPanel.add(getNameTextField());
        }
        return namingPanel;
    }

    private JLabel getNameLabel() {
        if (nameLabel == null) {
            nameLabel = new JLabel();

            try {
                InputStream is = this.getClass().getResourceAsStream("/fonts/GUNSHIP2.TTF");
                Font mainMenuWindowFont = Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(Font.PLAIN, 14);
                nameLabel.setFont(mainMenuWindowFont);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return nameLabel;
    }

    private JTextField getNameTextField() {
        if (nameTextField == null) {
            nameTextField = new JTextField();

            try {
                InputStream is = this.getClass().getResourceAsStream("/fonts/GUNSHIP2.TTF");
                Font mainMenuWindowFont = Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(Font.PLAIN, 14);
                nameTextField.setFont(mainMenuWindowFont);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return nameTextField;
    }

    private JButton getConfirmButton() {
        if (confirmButton == null) {
            confirmButton = new JButton("Rename");
        }
        return confirmButton;
    }
}

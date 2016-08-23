package org.freemars.earth.ui;

import java.awt.BorderLayout;
import java.awt.Dialog;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import org.freemars.ui.image.FreeMarsImageManager;
import org.freemars.ui.image.ImagePanel;
import org.freemars.ui.util.FreeMarsDialog;

/**
 *
 * @author Deniz ARIKAN
 */
public class EDialog extends FreeMarsDialog {

    private static final int FRAME_WIDTH = 530;
    private static final int FRAME_HEIGHT = 400;

    private ImagePanel imagePanel;
    private JPanel centerPanel;
    private JScrollPane descriptionScrollPane;
    private JTextPane descriptionTextPane;
    private JPanel footerPanel;
    private JButton okButton;

    public EDialog(Dialog owner) {
        super(owner);
        setTitle("Expeditionary force landed");
        setLayout(new BorderLayout());
        add(getImagePanel(), BorderLayout.LINE_START);
        add(getCenterPanel(), BorderLayout.CENTER);
        add(getFooterPanel(), BorderLayout.PAGE_END);
    }

    public void display() {
        display(FRAME_WIDTH, FRAME_HEIGHT);
    }

    public void setOkButtonAction(Action action) {
        getOkButton().setAction(action);
        getOkButton().setText("Ok");
    }

    private JPanel getCenterPanel() {
        if (centerPanel == null) {
            centerPanel = new JPanel(new BorderLayout());
            centerPanel.add(getDescriptionScrollPane(), BorderLayout.CENTER);
        }
        return centerPanel;
    }

    private JScrollPane getDescriptionScrollPane() {
        if (descriptionScrollPane == null) {
            descriptionScrollPane = new JScrollPane(getDescriptionTextPane());
        }
        return descriptionScrollPane;
    }

    private JTextPane getDescriptionTextPane() {
        if (descriptionTextPane == null) {
            descriptionTextPane = new JTextPane();
        }
        return descriptionTextPane;
    }

    private ImagePanel getImagePanel() {
        if (imagePanel == null) {
            imagePanel = new ImagePanel(FreeMarsImageManager.getImage("EXPEDITIONARY_FORCE_LANDED"));
        }
        return imagePanel;
    }

    private JPanel getFooterPanel() {
        if (footerPanel == null) {
            footerPanel = new JPanel();
            footerPanel.add(getOkButton());
        }
        return footerPanel;
    }

    private JButton getOkButton() {
        if (okButton == null) {
            okButton = new JButton();
        }
        return okButton;
    }
    
    private String getDescriptionTextPaneContent() {
        return "";
    }
}

package org.freemars.earth.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Image;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import org.freemars.ui.image.FreeMarsImageManager;
import org.freemars.ui.image.ImagePanel;
import org.freemars.ui.util.FreeMarsDialog;

/**
 *
 * @author Deniz ARIKAN
 */
public class ExpeditionaryForceLandedDialog extends FreeMarsDialog {

    private static final int FRAME_WIDTH = 580;
    private static final int FRAME_HEIGHT = 450;
    private ImagePanel imagePanel;
    private JPanel mainPanel;
    private JPanel informationPanel;
    private JScrollPane descriptionScrollPane;
    private JTextPane descriptionTextPane;
    private JPanel landedUnitInformationPanel;
    private JPanel footerPanel;
    private JButton okButton;
    private String prefix;

    public ExpeditionaryForceLandedDialog(Frame owner) {
        super(owner);
        setModal(true);
        setTitle("Expeditionary force landed");
        getContentPane().setLayout(new BorderLayout());
        add(getMainPanel(), BorderLayout.CENTER);
        add(getFooterPanel(), BorderLayout.PAGE_END);
        getDescriptionTextPane().setText(getDescriptionTextPaneContent());
    }

    public void display() {
        display(FRAME_WIDTH, FRAME_HEIGHT);
    }

    public void setOkButtonAction(Action action) {
        getOkButton().setAction(action);
        getOkButton().setText("Ok");
    }

    public void addLandedUnitInformation(Image image, int count, String name) {
        JPanel landedUnitInformation = new JPanel(new BorderLayout());
        landedUnitInformation.setBackground(Color.white);
        JLabel unitImageLabel = new JLabel(new ImageIcon(image));
        unitImageLabel.setToolTipText(name);
        JLabel unitCountLabel = new JLabel(String.valueOf(count), SwingConstants.CENTER);
        unitCountLabel.setToolTipText(name);
        unitImageLabel.setPreferredSize(new Dimension(50, 50));
        landedUnitInformation.add(unitImageLabel, BorderLayout.CENTER);
        landedUnitInformation.add(unitCountLabel, BorderLayout.PAGE_END);
        getLandedUnitInformationPanel().add(landedUnitInformation);
    }

    public void setDescriptionPrefix(String prefix) {
        this.prefix = prefix;
        getDescriptionTextPane().setText(getDescriptionTextPaneContent());
    }

    private JPanel getMainPanel() {
        if (mainPanel == null) {
            mainPanel = new JPanel(new BorderLayout());
            mainPanel.add(getImagePanel(), BorderLayout.LINE_START);
            mainPanel.add(getInformationPanel(), BorderLayout.CENTER);
        }
        return mainPanel;
    }

    private JPanel getInformationPanel() {
        if (informationPanel == null) {
            informationPanel = new JPanel(new BorderLayout());
            informationPanel.add(getDescriptionScrollPane(), BorderLayout.CENTER);
            informationPanel.add(getLandedUnitInformationPanel(), BorderLayout.PAGE_END);
        }
        return informationPanel;
    }

    private JScrollPane getDescriptionScrollPane() {
        if (descriptionScrollPane == null) {
            descriptionScrollPane = new JScrollPane(getDescriptionTextPane());
            descriptionScrollPane.setBorder(null);
        }
        return descriptionScrollPane;
    }

    private JTextPane getDescriptionTextPane() {
        if (descriptionTextPane == null) {
            descriptionTextPane = new JTextPane();
            descriptionTextPane.setContentType("text/html");
            descriptionTextPane.setEditable(false);
        }
        return descriptionTextPane;
    }

    private ImagePanel getImagePanel() {
        if (imagePanel == null) {
            Image image = FreeMarsImageManager.getImage("EXPEDITIONARY_FORCE_LANDED");
            imagePanel = new ImagePanel(image);
            imagePanel.setPreferredSize(new Dimension(image.getWidth(this) + 10, image.getHeight(this) + 10));
            imagePanel.setImageVerticalAlignment(ImagePanel.CENTER_ALIGNMENT);
            imagePanel.setBackground(Color.white);
        }
        return imagePanel;
    }

    private JPanel getLandedUnitInformationPanel() {
        if (landedUnitInformationPanel == null) {
            landedUnitInformationPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
            landedUnitInformationPanel.setBackground(Color.white);
        }
        return landedUnitInformationPanel;
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
        StringBuilder descriptionTextPaneContent = new StringBuilder(200);
        descriptionTextPaneContent.append("<br><br>");
        descriptionTextPaneContent.append("Sir<br><br>");
        descriptionTextPaneContent.append(prefix + " wave of the expeditionary force has landed to restore Earth rule over ");
        descriptionTextPaneContent.append("our Martian colonies. We will have to fight and destroy this ");
        descriptionTextPaneContent.append("force if we want to keep our freedom.<br><br>");
        descriptionTextPaneContent.append("We knew this day would come and we will not bow to Earth ");
        descriptionTextPaneContent.append("dictatorship. Surrender is not an option, our forces will ");
        descriptionTextPaneContent.append("fight and gain our independence or die trying.<br><br>");
        descriptionTextPaneContent.append("Colonial defence force<br><br>");
        descriptionTextPaneContent.append("Landed units :");
        return descriptionTextPaneContent.toString();
    }
}

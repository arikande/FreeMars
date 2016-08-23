package org.freemars.ui.util;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import org.freemars.ui.image.ImagePanel;

/**
 *
 * @author Deniz ARIKAN
 */
public class FreeMarsImageDialog extends FreeMarsDialog {

    private static final int DEFAULT_IMAGE_HEIGHT = 250;
    private static final int DEFAULT_IMAGE_WIDTH = 0;
    private JPanel centerPanel;
    private ImagePanel imagePanel;
    private JScrollPane scrollPane;
    private JTextPane textPane;
    private JPanel footerPanel;
    private JButton defaultButton;
    private String textPaneContent;

    public FreeMarsImageDialog(Frame owner) {
        super(owner);
        setModal(true);
        initializeWidgets();
        textPaneContent = "";
    }

    public void setImage(Image image) {
        getImagePanel().setImage(image);
    }

    public void appendText(int text) {
        appendText(String.valueOf(text));
    }

    public void appendText(String text) {
        textPaneContent = textPaneContent + text;
        getTextPane().setText(textPaneContent);
    }

    public void setImagePreferredSize(Dimension dimension) {
        getImagePanel().setPreferredSize(dimension);
    }

    public void setDefaultButtonText(String text) {
        getDefaultButton().setText(text);
    }

    public void addButton(AbstractButton button) {
        getFooterPanel().add(button);
    }

    public void addDefaultButtonActionListener(ActionListener actionListener) {
        getDefaultButton().addActionListener(actionListener);
    }

    private void initializeWidgets() {
        getContentPane().setLayout(new BorderLayout());
        add(getCenterPanel(), BorderLayout.CENTER);
        add(getFooterPanel(), BorderLayout.PAGE_END);
    }

    private JPanel getCenterPanel() {
        if (centerPanel == null) {
            centerPanel = new JPanel(new BorderLayout(10, 10));
            centerPanel.add(getImagePanel(), BorderLayout.LINE_START);
            centerPanel.add(getScrollPane(), BorderLayout.CENTER);
            centerPanel.setBackground(Color.white);
        }
        return centerPanel;
    }

    private ImagePanel getImagePanel() {
        if (imagePanel == null) {
            imagePanel = new ImagePanel();
            imagePanel.setPreferredSize(new Dimension(DEFAULT_IMAGE_WIDTH, DEFAULT_IMAGE_HEIGHT));
            imagePanel.setImageVerticalAlignment(ImagePanel.CENTER_ALIGNMENT);
            imagePanel.setBackground(Color.white);
        }
        return imagePanel;
    }

    private JScrollPane getScrollPane() {
        if (scrollPane == null) {
            scrollPane = new JScrollPane(getTextPane());
            scrollPane.setBorder(null);
        }
        return scrollPane;
    }

    private JTextPane getTextPane() {
        if (textPane == null) {
            textPane = new JTextPane();
            textPane.setContentType("text/html");
            textPane.setEditable(false);
        }
        return textPane;
    }

    private JPanel getFooterPanel() {
        if (footerPanel == null) {
            footerPanel = new JPanel();
            footerPanel.add(getDefaultButton());
            footerPanel.setBackground(Color.white);
        }
        return footerPanel;
    }

    private JButton getDefaultButton() {
        if (defaultButton == null) {
            defaultButton = new JButton("OK");
            defaultButton.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    dispose();
                }
            });
        }
        return defaultButton;
    }
}

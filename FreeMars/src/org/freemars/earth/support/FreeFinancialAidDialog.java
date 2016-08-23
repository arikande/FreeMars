package org.freemars.earth.support;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import org.freemars.model.FreeMarsModel;
import org.freemars.ui.image.FreeMarsImageManager;
import org.freemars.ui.image.ImagePanel;
import org.freemars.ui.util.FreeMarsDialog;

/**
 *
 * @author Deniz ARIKAN
 */
public class FreeFinancialAidDialog extends FreeMarsDialog {

    private static final int FRAME_WIDTH = 480;
    private static final int FRAME_HEIGHT = 210;
    private final FreeMarsModel model;
    private JPanel centerPanel;
    private ImagePanel freeFinancialAidImagePanel;
    private JScrollPane freeFinancialAidScrollPane;
    private JTextArea freeFinancialAidTextArea;
    private JPanel footerPanel;
    private JButton okButton;

    public FreeFinancialAidDialog(Frame owner, FreeMarsModel model) {
        super(owner);
        setTitle("Free financial aid");
        setModal(true);
        this.model = model;
        initializeWidgets();
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    }

    public void display() {
        pack();
        Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width - FRAME_WIDTH) / 2, (screenSize.height - FRAME_HEIGHT) / 2, FRAME_WIDTH, FRAME_HEIGHT);
        setVisible(true);
        toFront();
    }

    private void initializeWidgets() {
        getContentPane().setLayout(new BorderLayout());
        add(getCenterPanel(), BorderLayout.CENTER);
        add(getFooterPanel(), BorderLayout.PAGE_END);
    }

    private JPanel getCenterPanel() {
        if (centerPanel == null) {
            centerPanel = new JPanel(new BorderLayout(10, 10));
            centerPanel.setBackground(Color.white);
            centerPanel.add(getFreeFinancialAidImagePanel(), BorderLayout.LINE_START);
            centerPanel.add(getFreeFinancialAidScrollPane(), BorderLayout.CENTER);
        }
        return centerPanel;
    }

    private ImagePanel getFreeFinancialAidImagePanel() {
        if (freeFinancialAidImagePanel == null) {
            freeFinancialAidImagePanel = new ImagePanel(FreeMarsImageManager.getImage("EARTH_CREDITS"));
            freeFinancialAidImagePanel.setPreferredSize(new Dimension(120, 0));
            freeFinancialAidImagePanel.setImageVerticalAlignment(ImagePanel.CENTER_ALIGNMENT);
            freeFinancialAidImagePanel.setBackground(Color.white);
        }
        return freeFinancialAidImagePanel;
    }

    private JScrollPane getFreeFinancialAidScrollPane() {
        if (freeFinancialAidScrollPane == null) {
            freeFinancialAidScrollPane = new JScrollPane(getFreeFinancialAidTextArea());
            freeFinancialAidScrollPane.setBorder(null);
        }
        return freeFinancialAidScrollPane;
    }

    private JTextArea getFreeFinancialAidTextArea() {
        if (freeFinancialAidTextArea == null) {
            freeFinancialAidTextArea = new JTextArea();
            freeFinancialAidTextArea.setEditable(false);
            freeFinancialAidTextArea.setLineWrap(true);
            freeFinancialAidTextArea.setWrapStyleWord(true);
            freeFinancialAidTextArea.setFont(new Font("Arial", 1, 12));
            freeFinancialAidTextArea.setText("\nTo colonial governor,\n\n");
            freeFinancialAidTextArea.append("You have spent your fifth year ");
            freeFinancialAidTextArea.append("on Mars and your Earth government has decided to ");
            freeFinancialAidTextArea.append("give you a free financial aid. A sum of 20.000 credits ");
            freeFinancialAidTextArea.append("will be transferred to your treasury.\n\n");
            freeFinancialAidTextArea.append("We suggest you spend it wisely.");
        }
        return freeFinancialAidTextArea;
    }

    private JPanel getFooterPanel() {
        if (footerPanel == null) {
            footerPanel = new JPanel();
            footerPanel.setBackground(Color.white);
            footerPanel.add(getOkButton());
        }
        return footerPanel;
    }

    private JButton getOkButton() {
        if (okButton == null) {
            okButton = new JButton("Ok");
            okButton.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    dispose();
                }
            });
        }
        return okButton;
    }
}

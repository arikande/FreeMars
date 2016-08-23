package org.freemars.earth.support;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import org.freemars.controller.FreeMarsController;
import org.freemars.ui.image.FreeMarsImageManager;
import org.freemars.ui.image.ImagePanel;
import org.freemars.ui.util.FreeMarsDialog;
import org.freerealm.settlement.Settlement;

/**
 *
 * @author Deniz ARIKAN
 */
public class FreeTransporterDialog extends FreeMarsDialog {

    private static final int FRAME_WIDTH = 520;
    private static final int FRAME_HEIGHT = 280;
    private final FreeMarsController freeMarsController;
    private JPanel centerPanel;
    private ImagePanel imagePanel;
    private Image unitImage;
    private JScrollPane scrollPane;
    private JTextArea textArea;
    private JPanel coloniesPanel;
    private JButton decideLaterButton;

    public FreeTransporterDialog(Frame owner, FreeMarsController freeMarsController) {
        super(owner);
        setTitle("Free transporter");
        setModal(true);
        this.freeMarsController = freeMarsController;
        initializeWidgets();
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
        add(getColoniesPanel(), BorderLayout.PAGE_END);
    }

    private JPanel getCenterPanel() {
        if (centerPanel == null) {
            centerPanel = new JPanel(new BorderLayout(10, 10));
            centerPanel.setBackground(Color.white);
            centerPanel.add(getImagePanel(), BorderLayout.LINE_START);
            centerPanel.add(getScrollPane(), BorderLayout.CENTER);
        }
        return centerPanel;
    }

    private ImagePanel getImagePanel() {
        if (imagePanel == null) {
            imagePanel = new ImagePanel(getUnitImage());
            imagePanel.setBackground(Color.white);
            imagePanel.setImageVerticalAlignment(ImagePanel.CENTER_ALIGNMENT);
            imagePanel.setPreferredSize(new Dimension(120, 0));
        }
        return imagePanel;
    }

    private Image getUnitImage() {
        if (unitImage == null) {
            unitImage = FreeMarsImageManager.getImage(freeMarsController.getFreeMarsModel().getRealm().getUnitTypeManager().getUnitType("Transporter"));
            unitImage = FreeMarsImageManager.createResizedCopy(unitImage, -1, 110, false, null);
        }
        return unitImage;
    }

    private JScrollPane getScrollPane() {
        if (scrollPane == null) {
            scrollPane = new JScrollPane(getTextArea());
            scrollPane.setBorder(null);
        }
        return scrollPane;
    }

    private JTextArea getTextArea() {
        if (textArea == null) {
            textArea = new JTextArea();
            textArea.setEditable(false);
            textArea.setLineWrap(true);
            textArea.setWrapStyleWord(true);
            textArea.setFont(new Font("Arial", 1, 12));
            textArea.setText("\nTo colonial governor,\n\n");
            textArea.append("You have spent your fourth year ");
            textArea.append("on Mars and your Earth government has decided to ");
            textArea.append("give you a free transporter. You will need this unit ");
            textArea.append("to transfer goods between your colonies.\n\n");
            textArea.append("Please choose the colony to which you want ");
            textArea.append("this free transporter to be transferred.\n\n");
            textArea.append("Also, 1 year maintainance cost for the unit which is 720 credits ");
            textArea.append("(=60 x 12) will be added to your treasury.\n");
        }
        return textArea;
    }

    private JPanel getColoniesPanel() {
        if (coloniesPanel == null) {
            coloniesPanel = new JPanel();
            coloniesPanel.setBackground(Color.white);
            Iterator<Settlement> iterator = freeMarsController.getFreeMarsModel().getActivePlayer().getSettlementsIterator();
            while (iterator.hasNext()) {
                Settlement settlement = iterator.next();
                JButton colonyButton = new JButton(new AddTransporterToColonyAction(freeMarsController, this, settlement));
                colonyButton.setText(settlement.getName());
                coloniesPanel.add(colonyButton);
            }
            coloniesPanel.add(getDecideLaterButton());
        }
        return coloniesPanel;
    }

    private JButton getDecideLaterButton() {
        if (decideLaterButton == null) {
            decideLaterButton = new JButton("Decide next turn");
            decideLaterButton.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    dispose();
                }
            });
        }
        return decideLaterButton;
    }

}

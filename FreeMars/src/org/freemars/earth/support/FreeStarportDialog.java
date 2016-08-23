package org.freemars.earth.support;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
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
public class FreeStarportDialog extends FreeMarsDialog {

    private static final int FRAME_WIDTH = 580;
    private static final int FRAME_HEIGHT = 355;
    private final FreeMarsController freeMarsController;
    private JPanel centerPanel;
    private JPanel lineEndPanel;
    private ImagePanel starportImagePanel;
    private JScrollPane freeStarportScrollPane;
    private JTextArea freeStarportTextArea;
    private JPanel coloniesPanel;
    private JButton decideLaterButton;

    public FreeStarportDialog(Frame owner, FreeMarsController freeMarsController) {
        super(owner);
        setTitle("Free starport");
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
            centerPanel.add(getStarportImagePanel(), BorderLayout.LINE_START);
            centerPanel.add(getFreeStarportScrollPane(), BorderLayout.CENTER);
            centerPanel.add(getLineEndPanel(), BorderLayout.LINE_END);
        }
        return centerPanel;
    }

    private JPanel getLineEndPanel() {
        if (lineEndPanel == null) {
            lineEndPanel = new JPanel();
            lineEndPanel.setBackground(Color.white);
            lineEndPanel.setPreferredSize(new Dimension(20, 0));
        }
        return lineEndPanel;
    }

    private ImagePanel getStarportImagePanel() {
        if (starportImagePanel == null) {
            starportImagePanel = new ImagePanel(FreeMarsImageManager.getImage(freeMarsController.getFreeMarsModel().getRealm().getSettlementImprovementManager().getImprovement("Starport")));
            starportImagePanel.setPreferredSize(new Dimension(140, 0));
            starportImagePanel.setImageVerticalAlignment(ImagePanel.CENTER_ALIGNMENT);
            starportImagePanel.setBackground(Color.white);
        }
        return starportImagePanel;
    }

    private JScrollPane getFreeStarportScrollPane() {
        if (freeStarportScrollPane == null) {
            freeStarportScrollPane = new JScrollPane(getFreeStarportTextArea());
            freeStarportScrollPane.setBorder(null);
        }
        return freeStarportScrollPane;
    }

    private JTextArea getFreeStarportTextArea() {
        if (freeStarportTextArea == null) {
            freeStarportTextArea = new JTextArea();
            freeStarportTextArea.setEditable(false);
            freeStarportTextArea.setLineWrap(true);
            freeStarportTextArea.setWrapStyleWord(true);
            freeStarportTextArea.setFont(new Font("Arial", 1, 12));
            freeStarportTextArea.setText("\nTo colonial governor,\n\n");
            freeStarportTextArea.append("You have spent your second year ");
            freeStarportTextArea.append("on Mars and your Earth government has decided to ");
            freeStarportTextArea.append("finance your first starport on the Red planet. Please choose the colony on which you want ");
            freeStarportTextArea.append("this free starport to be built.\n\n");
            freeStarportTextArea.append("Since the shuttles transporting goods between Earth and Mars ");
            freeStarportTextArea.append("can only land on colonies that have a starport, it is recommened ");
            freeStarportTextArea.append("to choose a location that is producing (or near) exportable goods.\n\n");
            freeStarportTextArea.append("Also, next year's maintainance cost for the starport which is 1920 credits ");
            freeStarportTextArea.append("(=160 x 12) will be added to your treasury.\n");
        }
        return freeStarportTextArea;
    }

    private JPanel getColoniesPanel() {
        if (coloniesPanel == null) {
            coloniesPanel = new JPanel();
            coloniesPanel.setBackground(Color.white);
            Iterator<Settlement> iterator = freeMarsController.getFreeMarsModel().getActivePlayer().getSettlementsIterator();
            while (iterator.hasNext()) {
                Settlement settlement = iterator.next();
                JButton colonyButton = new JButton(new AddStarportToColonyAction(freeMarsController, this, settlement));
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

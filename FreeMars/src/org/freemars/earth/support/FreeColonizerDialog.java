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
import javax.swing.plaf.DimensionUIResource;
import org.freemars.controller.FreeMarsController;
import org.freemars.ui.image.FreeMarsImageManager;
import org.freemars.ui.image.ImagePanel;
import org.freemars.ui.util.FreeMarsDialog;
import org.freerealm.settlement.Settlement;

/**
 *
 * @author Deniz ARIKAN
 */
public class FreeColonizerDialog extends FreeMarsDialog {

    private static final int FRAME_WIDTH = 470;
    private static final int FRAME_HEIGHT = 240;
    private final FreeMarsController freeMarsController;
    private JPanel centerPanel;
    private ImagePanel colonizerImagePanel;
    private Image unitImage;
    private JScrollPane colonizerScrollPane;
    private JTextArea colonizerTextArea;
    private JPanel coloniesPanel;
    private JButton decideLaterButton;

    public FreeColonizerDialog(Frame owner, FreeMarsController freeMarsController) {
        super(owner);
        setTitle("Free colonizer");
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
            centerPanel.add(getColonizerImagePanel(), BorderLayout.LINE_START);
            centerPanel.add(getColonizerScrollPane(), BorderLayout.CENTER);
        }
        return centerPanel;
    }

    private ImagePanel getColonizerImagePanel() {
        if (colonizerImagePanel == null) {
            colonizerImagePanel = new ImagePanel(getUnitImage());
            colonizerImagePanel.setBackground(Color.white);
            colonizerImagePanel.setImageVerticalAlignment(ImagePanel.CENTER_ALIGNMENT);
            colonizerImagePanel.setPreferredSize(new DimensionUIResource(120, 0));
        }
        return colonizerImagePanel;
    }

    private Image getUnitImage() {
        if (unitImage == null) {
            unitImage = FreeMarsImageManager.getImage(freeMarsController.getFreeMarsModel().getRealm().getUnitTypeManager().getUnitType("Colonizer"));
            unitImage = FreeMarsImageManager.createResizedCopy(unitImage, -1, 110, false, null);
        }
        return unitImage;
    }

    private JScrollPane getColonizerScrollPane() {
        if (colonizerScrollPane == null) {
            colonizerScrollPane = new JScrollPane(getColonizerTextArea());
            colonizerScrollPane.setBorder(null);
        }
        return colonizerScrollPane;
    }

    private JTextArea getColonizerTextArea() {
        if (colonizerTextArea == null) {
            colonizerTextArea = new JTextArea();
            colonizerTextArea.setEditable(false);
            colonizerTextArea.setLineWrap(true);
            colonizerTextArea.setWrapStyleWord(true);
            colonizerTextArea.setFont(new Font("Arial", 1, 12));
            colonizerTextArea.setText("\nTo colonial governor,\n\n");
            colonizerTextArea.append("You have spent your third year ");
            colonizerTextArea.append("on Mars and your Earth government has decided to ");
            colonizerTextArea.append("give you a free colonizer. Please choose the colony to which you want ");
            colonizerTextArea.append("this free colonizer to be transferred.\n\n");
            colonizerTextArea.append("Also, 6 month maintainance cost for the unit which is 900 credits ");
            colonizerTextArea.append("(=150 x 6) will be added to your treasury.\n");
        }
        return colonizerTextArea;
    }

    private JPanel getColoniesPanel() {
        if (coloniesPanel == null) {
            coloniesPanel = new JPanel();
            coloniesPanel.setBackground(Color.white);
            Iterator<Settlement> iterator = freeMarsController.getFreeMarsModel().getActivePlayer().getSettlementsIterator();
            while (iterator.hasNext()) {
                Settlement settlement = iterator.next();
                JButton colonyButton = new JButton(new AddColonizerToColonyAction(freeMarsController, this, settlement));
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

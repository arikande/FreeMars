package org.freemars.mission;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import org.freemars.model.FreeMarsModel;
import org.freemars.ui.util.FreeMarsDialog;
import org.freerealm.player.Player;

/**
 *
 * @author Deniz ARIKAN
 */
public class MissionsDialog extends FreeMarsDialog {

    private static final int FRAME_WIDTH = 1200;
    private static final int FRAME_HEIGHT = 700;
    private JPanel mainPanel;
    private JScrollPane scrollPane;
    private MissionsTable missionsTable;
    private JPanel footerPanel;
    private JButton closeButton;
    private final FreeMarsModel freeMarsModel;
    private final Player player;
    private int frameWidth = FRAME_WIDTH;
    private int frameHeight = FRAME_HEIGHT;

    public MissionsDialog(Frame owner, FreeMarsModel freeMarsModel, Player player) {
        super(owner);
        this.freeMarsModel = freeMarsModel;
        this.player = player;
        setModal(true);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setTitle("Missions");
        initializeWidgets();
    }

    public void display() {
        pack();
        Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        if (frameWidth > screenSize.width) {
            frameWidth = screenSize.width;
        }
        if (frameHeight > screenSize.height) {
            frameHeight = screenSize.height;
        }
        setBounds((screenSize.width - frameWidth) / 2, (screenSize.height - frameHeight) / 2, frameWidth, frameHeight);
        setVisible(true);
        toFront();
    }

    public void setColoniesTableMouseAdapter(MouseAdapter mouseAdapter) {
        getMissionsTable().addMouseListener(mouseAdapter);
    }

    private void initializeWidgets() {
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(getMainPanel(), BorderLayout.CENTER);
        getContentPane().add(getFooterPanel(), BorderLayout.PAGE_END);
    }

    private JPanel getMainPanel() {
        if (mainPanel == null) {
            mainPanel = new JPanel(new BorderLayout());
            mainPanel.add(getScrollPane());
        }
        return mainPanel;
    }

    private JScrollPane getScrollPane() {
        if (scrollPane == null) {
            scrollPane = new JScrollPane(getMissionsTable());
        }
        return scrollPane;
    }

    private MissionsTable getMissionsTable() {
        if (missionsTable == null) {
            missionsTable = new MissionsTable(freeMarsModel, player);
        }
        return missionsTable;
    }

    private JPanel getFooterPanel() {
        if (footerPanel == null) {
            footerPanel = new JPanel();
            footerPanel.add(getCloseButton());
        }
        return footerPanel;
    }

    private JButton getCloseButton() {
        if (closeButton == null) {
            closeButton = new JButton("Close");
            closeButton.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    dispose();
                }
            });
        }
        return closeButton;
    }
}

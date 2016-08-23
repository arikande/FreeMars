package org.freemars.trade;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import org.freemars.model.FreeMarsModel;
import org.freemars.player.FreeMarsPlayer;
import org.freemars.ui.util.FreeMarsDialog;

/**
 *
 * @author Deniz ARIKAN
 */
public class TradeDialog extends FreeMarsDialog {

    private final int FRAME_WIDTH = 1200;
    private final int FRAME_HEIGHT = 500;
    private JPanel mainPanel;
    private JScrollPane scrollPane;
    private JTable table;
    private JPanel footerPanel;
    private JButton closeButton;
    private final FreeMarsModel model;
    private int frameWidth = FRAME_WIDTH;
    private int frameHeight = FRAME_HEIGHT;

    public TradeDialog(Frame owner, FreeMarsModel model) {
        super(owner);
        this.model = model;
        setModal(true);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setTitle("Trade");
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

    public void setCloseAction(Action action) {
        getCloseButton().setAction(action);
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
            scrollPane = new JScrollPane(getTable());
        }
        return scrollPane;
    }

    private JTable getTable() {
        if (table == null) {
            table = new TradeTable(model, (FreeMarsPlayer) model.getActivePlayer());
        }
        return table;
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
            closeButton = new JButton();
        }
        return closeButton;
    }
}

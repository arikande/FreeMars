package org.freemars.ui.player;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.event.MouseAdapter;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import org.freemars.ui.util.FreeMarsDialog;
import org.freerealm.player.Message;

/**
 *
 * @author Deniz ARIKAN
 */
public class MessagesDialog extends FreeMarsDialog {

    private JPanel centerPanel;
    private JPanel pageEndPanel;
    private JScrollPane messagesScrollPane;
    private MessagesTable messagesTable;
    private JButton okButton;

    public MessagesDialog(Frame owner) {
        super(owner);
        setTitle("Messages");
        setModal(true);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        initializeWidgets();
    }

    public void addMessagesTableMouseListener(MouseAdapter mouseAdapter) {
        messagesTable.addMouseListener(mouseAdapter);
    }

    public void setOKButtonAction(Action action) {
        getOKButton().setAction(action);
        getOKButton().setText("OK");
    }
/*
    public void display() {
        pack();
        final java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width - getWidth()) / 2, (screenSize.height - getHeight()) / 2, getWidth(), getHeight());
        setVisible(true);
        toFront();
    }
*/
    
    /*
    public void display(int width, int height) {
        pack();
        final java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width - width) / 2, (screenSize.height - height) / 2, width, height);
        setVisible(true);
        toFront();
    }
*/
    public void addMessage(Message message) {
        getMessagesTable().addMessage(message);
    }

    private void initializeWidgets() {
        setLayout(new BorderLayout());
        add(getCenterPanel(), BorderLayout.CENTER);
        add(getPageEndPanel(), BorderLayout.PAGE_END);
    }

    private JPanel getCenterPanel() {
        if (centerPanel == null) {
            centerPanel = new JPanel(new BorderLayout());
            centerPanel.add(getMessagesScrollPane(), BorderLayout.CENTER);
        }
        return centerPanel;
    }

    private JScrollPane getMessagesScrollPane() {
        if (messagesScrollPane == null) {
            messagesScrollPane = new JScrollPane(getMessagesTable());
        }
        return messagesScrollPane;
    }

    private MessagesTable getMessagesTable() {
        if (messagesTable == null) {
            messagesTable = new MessagesTable();
        }
        return messagesTable;
    }

    private JPanel getPageEndPanel() {
        if (pageEndPanel == null) {
            pageEndPanel = new JPanel();
            pageEndPanel.add(getOKButton());
        }
        return pageEndPanel;
    }

    private JButton getOKButton() {
        if (okButton == null) {
            okButton = new JButton();
        }
        return okButton;
    }
}

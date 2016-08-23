package org.freemars.ui.player;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import org.freemars.ui.util.FreeMarsDialog;
import org.freemars.ui.util.TurnToDateConverter;
import org.freerealm.player.DefaultMessage;
import org.freerealm.player.Message;
import org.freerealm.player.Player;

/**
 *
 * @author Deniz ARIKAN
 */
public class MessageHistoryDialog extends FreeMarsDialog {

    private final int FRAME_WIDTH = 600;
    private final int FRAME_HEIGHT = 650;
    private JPanel pageEndPanel;
    private DefaultListModel messageHistoryListModel;
    private JList messageHistoryList;
    private JScrollPane messageHistoryScrollPane;
    private JButton closeButton;
    private final Player player;

    public MessageHistoryDialog(JFrame parent, Player player) {
        super(parent);
        setModal(true);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setTitle("Message history");
        this.player = player;
    }

    public void display() {
        getContentPane().setLayout(new BorderLayout(4, 4));
        initializeWidgets();
        pack();
        final java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width - FRAME_WIDTH) / 2, (screenSize.height - FRAME_HEIGHT) / 2, FRAME_WIDTH, FRAME_HEIGHT);
        setVisible(true);
        toFront();
    }

    private void initializeWidgets() {
        add(getMessageHistoryScrollPane(), BorderLayout.CENTER);
        add(getPageEndPanel(), BorderLayout.PAGE_END);
    }

    private ListModel getMessageHistoryListModel() {
        List<Message> messages = new ArrayList<Message>();
        Iterator<Message> iterator = player.getMessagesIterator();
        while (iterator.hasNext()) {
            DefaultMessage message = (DefaultMessage) iterator.next();
            messages.add(message);
        }
        Collections.reverse(messages);

        if (messageHistoryListModel == null) {
            messageHistoryListModel = new DefaultListModel();
            int lastTurnAdded = -1;
            iterator = messages.iterator();
            while (iterator.hasNext()) {
                DefaultMessage message = (DefaultMessage) iterator.next();
                if (lastTurnAdded != message.getTurnSent()) {
                    if (messageHistoryListModel.getSize() > 0) {
                        messageHistoryListModel.addElement(" ");
                    }
                    lastTurnAdded = message.getTurnSent();
                    String date = new TurnToDateConverter(lastTurnAdded).gateDateString();
                    messageHistoryListModel.addElement(date);
                }
                messageHistoryListModel.addElement(message.getText());
            }
        }

        return messageHistoryListModel;
    }

    private JList getMessageHistoryList() {
        if (messageHistoryList == null) {
            messageHistoryList = new JList(getMessageHistoryListModel());
            messageHistoryList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            messageHistoryList.setSelectionBackground(messageHistoryList.getBackground());
        }
        return messageHistoryList;
    }

    private JScrollPane getMessageHistoryScrollPane() {
        if (messageHistoryScrollPane == null) {
            messageHistoryScrollPane = new JScrollPane(getMessageHistoryList());
        }
        return messageHistoryScrollPane;
    }

    private JPanel getPageEndPanel() {
        if (pageEndPanel == null) {
            pageEndPanel = new JPanel();
            pageEndPanel.add(getCloseButton());
        }
        return pageEndPanel;
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

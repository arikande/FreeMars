package org.freemars.ui.player;

import java.awt.event.MouseEvent;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.MouseInputAdapter;
import org.freerealm.player.Message;

/**
 * @author Deniz ARIKAN
 */
public class MessagesTable extends JTable {

    private MessagesTableModel messagesTableModel;

    public MessagesTable() {
        setModel(getMessagesTableModel());
        setTableHeader(null);
        setShowVerticalLines(false);
        setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        setRowHeight(60);
        setRowMargin(5);
        getColumnModel().getColumn(0).setMaxWidth(100);
        addMouseMotionListener(new MouseInputAdapter() {

            @Override
            public void mouseMoved(MouseEvent e) {
                int row = rowAtPoint(e.getPoint());
                getSelectionModel().setSelectionInterval(row, row);
            }
        });
    }

    public void addMessage(Message message) {
        getMessagesTableModel().addMessage(message);
        repaint();
    }

    public Message getMessageAt(int row) {
        return getMessagesTableModel().getMessageAt(row);
    }

    private MessagesTableModel getMessagesTableModel() {
        if (messagesTableModel == null) {
            messagesTableModel = new MessagesTableModel();
        }
        return messagesTableModel;
    }
}

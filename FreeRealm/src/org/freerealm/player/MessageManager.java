package org.freerealm.player;

import java.util.Iterator;
import java.util.Vector;

/**
 *
 * @author Deniz ARIKAN
 */
public class MessageManager {

    private final Vector<Message> messages;

    public MessageManager() {
        messages = new Vector<Message>();
    }

    public void clear() {
        messages.removeAllElements();
    }

    public void addMessage(Message message) {
        getMessages().add(message);
    }

    public Iterator<Message> getUnreadMessagesIterator() {
        Vector<Message> unreadMessages = new Vector<Message>();
        for (Iterator<Message> iterator = getMessages().iterator(); iterator.hasNext();) {
            Message message = iterator.next();
            if (!message.isRead()) {
                unreadMessages.add(message);
            }
        }
        return unreadMessages.iterator();
    }

    public int getUnreadMessageCount() {
        int unreadMessageCount = 0;
        for (Iterator<Message> iterator = getMessages().iterator(); iterator.hasNext();) {
            if (!iterator.next().isRead()) {
                unreadMessageCount = unreadMessageCount + 1;
            }
        }
        return unreadMessageCount;
    }

    public Iterator<Message> getMessagesIterator() {
        return messages.iterator();
    }

    private Vector<Message> getMessages() {
        return messages;
    }
}

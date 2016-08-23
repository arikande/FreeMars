package org.freerealm.player;

/**
 *
 * @author Deniz ARIKAN
 */
public class DefaultMessage implements Message {

    private String subject;
    private String text;
    private boolean read = false;
    private int turnSent;

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public int getTurnSent() {
        return turnSent;
    }

    public void setTurnSent(int turnSent) {
        this.turnSent = turnSent;
    }
}

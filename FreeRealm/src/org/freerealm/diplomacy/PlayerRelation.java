package org.freerealm.diplomacy;

/**
 *
 * @author Deniz ARIKAN
 */
public class PlayerRelation implements Comparable<PlayerRelation> {

    public static final int NO_DIPLOMACY_ALLOWED = 0;
    public static final int NO_CONTACT = 1;
    public static final int AT_PEACE = 2;
    public static final int AT_WAR = 3;
    public static final int ALLIED = 4;
    public static final int DEFAULT_STATUS = NO_DIPLOMACY_ALLOWED;
    public static final int MINIMUM_ATTITUDE = 1;
    public static final int DEFAULT_ATTITUDE = 550;
    public static final int MAXIMUM_ATTITUDE = 1000;
    private final int targetPlayerId;
    private int status;
    private int attitude;

    public PlayerRelation(int targetPlayerId) {
        this.targetPlayerId = targetPlayerId;
        status = DEFAULT_STATUS;
        attitude = DEFAULT_ATTITUDE;
    }

    public int compareTo(PlayerRelation playerRelation) {
        if (targetPlayerId < playerRelation.getTargetPlayerId()) {
            return -1;
        }
        if (targetPlayerId > playerRelation.getTargetPlayerId()) {
            return 1;
        }
        return 0;
    }

    public int getTargetPlayerId() {
        return targetPlayerId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getAttitude() {
        return attitude;
    }

    public void setAttitude(int attitude) {
        this.attitude = attitude;
    }

    public String getStatusDefinition() {
        switch (status) {
            case NO_DIPLOMACY_ALLOWED:
                return "No diplomacy allowed";
            case NO_CONTACT:
                return "No contact";
            case AT_PEACE:
                return "At peace";
            case AT_WAR:
                return "At war";
            case ALLIED:
                return "Allied";
            default:
                return "No contact";
        }

    }
}

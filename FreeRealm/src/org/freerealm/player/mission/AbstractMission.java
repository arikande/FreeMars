package org.freerealm.player.mission;

import java.util.ArrayList;
import java.util.Iterator;
import org.freerealm.Realm;
import org.freerealm.player.Player;

/**
 *
 * @author Deniz ARIKAN
 */
public abstract class AbstractMission implements Mission {

    private int id;
    private Realm realm;
    private Player player;
    private int status;
    private int turnIssued;
    private int duration;
    private final ArrayList<Reward> rewards;

    public AbstractMission() {
        duration = -1;
        rewards = new ArrayList<Reward>();
    }

    @Override
    public abstract Mission clone();

    public void copyTo(Mission mission) {
        mission.setId(id);
        mission.setRealm(realm);
        mission.setPlayer(player);
        mission.setStatus(status);
        mission.setTurnIssued(turnIssued);
        mission.setDuration(duration);
        for (Reward reward : rewards) {
            mission.addReward(reward);
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Realm getRealm() {
        return realm;
    }

    public void setRealm(Realm realm) {
        this.realm = realm;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getTurnIssued() {
        return turnIssued;
    }

    public void setTurnIssued(int turnIssued) {
        this.turnIssued = turnIssued;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getRewardCount() {
        return rewards.size();
    }

    public void addReward(Reward reward) {
        rewards.add(reward);
    }

    public void clearRewards() {
        rewards.clear();
    }

    public Iterator<Reward> getRewardsIterator() {
        return rewards.iterator();
    }

    protected void checkIfExpired() {
        if (hasExpired()) {
            setStatus(Mission.STATUS_FAILED);
        } else {
            setStatus(Mission.STATUS_ACTIVE);
        }
    }

    private boolean hasExpired() {
        if (getDuration() == -1) {
            return false;
        } else {
            return (getRealm().getNumberOfTurns() - getTurnIssued()) >= getDuration();
        }
    }
}

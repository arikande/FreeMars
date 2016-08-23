package org.freerealm.player.mission;

import java.util.Iterator;
import org.freerealm.Realm;
import org.freerealm.player.Player;

/**
 *
 * @author Deniz ARIKAN
 */
public interface Mission extends Cloneable {

    public static final int STATUS_ACTIVE = 0;
    public static final int STATUS_COMPLETED = 1;
    public static final int STATUS_FAILED = 2;
    public static final int STATUS_CANCELED = 3;

    public Mission clone();

    public int getId();

    public void setId(int id);

    public String getMissionName();

    public Realm getRealm();

    public void setRealm(Realm realm);

    public Player getPlayer();

    public void setPlayer(Player player);

    public int getTurnIssued();

    public void setTurnIssued(int turnIssued);

    public int getDuration();

    public void setDuration(int duration);

    public void checkStatus();

    public int getStatus();

    public void setStatus(int status);

    public int getProgressPercent();

    public void addReward(Reward reward);

    public void clearRewards();

    public Iterator<Reward> getRewardsIterator();

    public int getRewardCount();
}

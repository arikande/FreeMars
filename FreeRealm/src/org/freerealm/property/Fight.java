package org.freerealm.property;

/**
 *
 * @author Deniz ARIKAN
 */
public class Fight implements Property {

    private int attackPoints;
    private int defencePoints;

    public String getName() {
        return "Fight";
    }

    public int getAttackPoints() {
        return attackPoints;
    }

    public void setAttackPoints(int attackPoints) {
        this.attackPoints = attackPoints;
    }

    public int getDefencePoints() {
        return defencePoints;
    }

    public void setDefencePoints(int defencePoints) {
        this.defencePoints = defencePoints;
    }
}

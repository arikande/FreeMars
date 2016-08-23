package org.freemars.model.wizard.newgame;

import java.awt.Color;
import org.freerealm.nation.Nation;

/**
 *
 * @author Deniz ARIKAN
 */
public class NationOption {

    private Nation nation;
    private Color color;
    private boolean selected;
    private boolean human;

    public Nation getNation() {
        return nation;
    }

    public void setNation(Nation nation) {
        this.nation = nation;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean isHuman() {
        return human;
    }

    public void setHuman(boolean human) {
        this.human = human;
    }
}

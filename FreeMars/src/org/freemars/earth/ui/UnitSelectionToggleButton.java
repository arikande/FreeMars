package org.freemars.earth.ui;

import java.awt.Color;
import java.awt.Font;
import javax.swing.AbstractButton;
import javax.swing.JToggleButton;
import javax.swing.JToolTip;
import org.freerealm.unit.Unit;

/**
 *
 * @author Deniz ARIKAN
 */
public class UnitSelectionToggleButton extends JToggleButton {

    private Unit unit;
    private UnitSelectionToggleButtonTooltip unitSelectionToggleButtonTooltip;

    public UnitSelectionToggleButton(Unit unit) {
        this.unit = unit;
        setVerticalTextPosition(AbstractButton.BOTTOM);
        setHorizontalTextPosition(AbstractButton.CENTER);
        setFont(new Font("Courier New", 1, 12));
        setForeground(new Color(202, 63, 63));
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    @Override
    public JToolTip createToolTip() {
        if (unitSelectionToggleButtonTooltip == null) {
            unitSelectionToggleButtonTooltip = new UnitSelectionToggleButtonTooltip(unit);
            unitSelectionToggleButtonTooltip.setComponent(this);
        }
        return unitSelectionToggleButtonTooltip;
    }

}

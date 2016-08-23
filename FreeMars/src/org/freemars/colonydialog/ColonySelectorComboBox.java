package org.freemars.colonydialog;

import javax.swing.JComboBox;
import org.freerealm.settlement.Settlement;

/**
 *
 * @author Deniz ARIKAN
 */
public class ColonySelectorComboBox extends JComboBox {

    public void addColony(Settlement settlement) {
        addItem(settlement);
    }

    public void setSelectedColony(Settlement settlement) {
        for (int i = 0; i < getItemCount(); i++) {
            ColonySelectorComboBoxItem item = (ColonySelectorComboBoxItem) getItemAt(i);
            if (item.getSettlement().equals(settlement)) {
                super.setSelectedItem(item);
                return;
            }
        }
    }

    class ColonySelectorComboBoxItem {

        private final Settlement settlement;

        private ColonySelectorComboBoxItem(Settlement settlement) {
            this.settlement = settlement;
        }

        @Override
        public String toString() {
            return getSettlement().getName();
        }

        private Settlement getSettlement() {
            return settlement;
        }
    }
}

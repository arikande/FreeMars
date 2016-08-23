package org.freemars.colonydialog;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;
import org.freemars.colony.FreeMarsColony;
import org.freemars.model.FreeMarsModel;
import org.freerealm.Realm;
import org.freerealm.resource.ResourceStorer;
import org.freerealm.settlement.Settlement;
import org.freerealm.settlement.improvement.SettlementImprovement;
import org.freerealm.unit.Unit;

/**
 *
 * @author Deniz ARIKAN
 */
public class ColonyDialogModel extends Observable {

    public static final int FULL_UPDATE = 0;
    public static final int COLONY_CHANGE_UPDATE = 1;
    public static final int COLONY_UNITS_UPDATE = 2;
    public static final int COLONY_RENAME_UPDATE = 3;
    public static final int SELECTED_UNIT_UPDATE = 4;
    public static final int WORKFORCE_UPDATE = 5;
    public static final int UNIT_CARGO_CHANGE_UPDATE = 6;
    public static final int CURRENT_PRODUCTION_UPDATE = 7;
    public static final int COLONY_IMPROVEMENTS_UPDATE = 8;
    public static final int COLONY_RESOURCES_UPDATE = 9;
    private FreeMarsModel freeMarsModel;
    private FreeMarsColony freeMarsColony;
    private final List<Settlement> selectableColonies;
    private Unit selectedUnit;
    private SettlementImprovement selectedImprovement;
    private int resourceTransferAmount;
    private ResourceStorer resourceTransferSource;

    public ColonyDialogModel() {
        selectableColonies = new ArrayList<Settlement>();
    }

    public void refresh(int updateType) {
        setChanged();
        notifyObservers(updateType);
    }

    public Realm getRealm() {
        return freeMarsModel.getRealm();
    }

    public FreeMarsModel getFreeMarsModel() {
        return freeMarsModel;
    }

    public void setFreeMarsModel(FreeMarsModel viewModel) {
        this.freeMarsModel = viewModel;
    }

    public FreeMarsColony getColony() {
        return freeMarsColony;
    }

    public void setColony(FreeMarsColony newFreeMarsColony) {
        if (newFreeMarsColony == null) {
            freeMarsColony = null;
            setSelectedUnit(null);
            setChanged();
        } else if (newFreeMarsColony != null && !newFreeMarsColony.equals(freeMarsColony)) {
            freeMarsColony = newFreeMarsColony;
            setSelectedUnit(null);
            setChanged();

        }
        notifyObservers(COLONY_CHANGE_UPDATE);
    }

    public void addSelectableColony(Settlement settlement) {
        selectableColonies.add(settlement);
    }

    public Iterator<Settlement> getSelectableColoniesIterator() {
        return selectableColonies.iterator();
    }

    public int getSelectableColonyCount() {
        return selectableColonies.size();
    }

    public Settlement getPreviousColony(Settlement settlement) {
        if (settlement == null) {
            return null;
        } else {
            int index = selectableColonies.indexOf(settlement);
            if (index == -1) {
                return null;
            }
            index = (index + selectableColonies.size() - 1) % selectableColonies.size();
            return selectableColonies.get(index);
        }
    }

    public Settlement getNextColony(Settlement settlement) {
        if (settlement == null) {
            return null;
        } else {
            int index = selectableColonies.indexOf(settlement);
            if (index == -1) {
                return null;
            }
            index = (index + selectableColonies.size() + 1) % selectableColonies.size();
            return selectableColonies.get(index);
        }
    }

    public Unit getSelectedUnit() {
        return selectedUnit;
    }

    public void setSelectedUnit(Unit newSelectedUnit) {
        if (newSelectedUnit == null) {
            selectedUnit = null;
            setChanged();
        } else if (newSelectedUnit != null && !newSelectedUnit.equals(selectedUnit)) {
            selectedUnit = newSelectedUnit;
            setChanged();
        }
        notifyObservers(SELECTED_UNIT_UPDATE);
    }

    public SettlementImprovement getSelectedImprovement() {
        return selectedImprovement;
    }

    public void setSelectedImprovement(SettlementImprovement newSelectedImprovement) {
        selectedImprovement = newSelectedImprovement;
    }

    public int getResourceTransferAmount() {
        return resourceTransferAmount;
    }

    public void setResourceTransferAmount(int resourceTransferAmount) {
        this.resourceTransferAmount = resourceTransferAmount;
    }

    public ResourceStorer getResourceTransferSource() {
        return resourceTransferSource;
    }

    public void setResourceTransferSource(ResourceStorer resourceTransferSource) {
        this.resourceTransferSource = resourceTransferSource;
    }
}

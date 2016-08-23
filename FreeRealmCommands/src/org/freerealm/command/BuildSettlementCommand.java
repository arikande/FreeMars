package org.freerealm.command;

import java.util.Iterator;

import org.freerealm.Realm;
import org.freerealm.property.BuildSettlementProperty;
import org.freerealm.property.Property;
import org.freerealm.settlement.FreeRealmSettlement;
import org.freerealm.settlement.Settlement;
import org.freerealm.tile.Collectable;
import org.freerealm.unit.Unit;

/**
 * Command class to build a new settlement using the given unit.
 * 
 * Upon execution this command will return an error if:
 * <ul>
 * <li>Settlement name is null or an empty string.</li>
 * <li>Tile already has a settlement.</li>
 * <li>Given unit does not have "BuildSettlement" property.</li>
 * </ul>
 * 
 * If no error is fired after checking these conditions BuildSettlementCommand
 * will add a new settlement to unit's coordinate and remove the unit. Initial
 * population of settlement will be <tt>settlement_founding_population</tt>
 * property of the realm.
 * <p>
 * If settlement building unit was the player's active unit this command will
 * not find and activate player's next unit, normally active unit will be null.
 * If needed, the command caller must make next unit active.
 * 
 * @author Deniz ARIKAN
 */
public class BuildSettlementCommand extends FreeRealmAbstractCommand {

	private final String settlementName;
	private final Unit unit;
	private Settlement settlement;

	/**
	 * Constructs a BuildSettlementCommand using unit and settlementName.
	 * 
	 * @param unit
	 *            Unit to build the settlement, must have "BuildSettlement"
	 *            property
	 * @param settlementName
	 *            Name of new settlement
	 */
	public BuildSettlementCommand(Realm realm, Unit unit, String settlementName, Settlement settlement) {
		super(realm);
		this.unit = unit;
		this.settlementName = settlementName;
		this.settlement = settlement;
	}

	/**
	 * Executes command and adds a new settlement to realm belonging to unit's
	 * player.
	 * 
	 * @return CommandResult
	 */
	public void execute() {
		if ((settlementName == null) || (settlementName.trim().equals(""))) {
			setText("Settlement name cannot be empty");
			setState(FAILED);
			return;
		}
		if (getBuildSettlementProperty(unit) == null) {
			setText("Unit is not able to found new settlements");
			setState(FAILED);
			return;
		}
		if (getRealm().getTile(unit.getCoordinate()).getSettlement() != null) {
			setText("Tile alredy contains a settlement");
			setState(FAILED);
			return;
		}
		if (settlement == null) {
			settlement = new FreeRealmSettlement(getRealm());
		}
		Collectable collectable = getRealm().getTile(unit.getCoordinate()).getCollectable();
		if (collectable != null) {
			getExecutor().execute(new ProcessCollectableCommand(getRealm(), unit, collectable));
		}
		settlement.setCoordinate(unit.getCoordinate());
		settlement.setPlayer(unit.getPlayer());
		settlement.setName(settlementName);
		int settlementFoundingPopulation = Integer.parseInt(getRealm().getProperty("settlement_founding_population"));
		settlement.setPopulation(settlementFoundingPopulation);
		unit.getPlayer().addSettlement(settlement);
		getExecutor().execute(new RemoveUnitCommand(getRealm(), unit.getPlayer(), unit));
		getRealm().getMap().addSettlement(settlement);
		putParameter("settlement", settlement);
		setState(SUCCEEDED);
	}

	private BuildSettlementProperty getBuildSettlementProperty(Unit unit) {
		Iterator<Property> propertyIterator = unit.getType().getPropertiesIterator();
		while (propertyIterator.hasNext()) {
			Property property = propertyIterator.next();
			if (property instanceof BuildSettlementProperty) {
				return (BuildSettlementProperty) property;
			}
		}
		return null;
	}
}

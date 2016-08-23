package org.freerealm.command;

import org.apache.log4j.Logger;
import org.freerealm.Realm;
import org.freerealm.resource.Resource;
import org.freerealm.settlement.Settlement;

/**
 * 
 * @author Deniz ARIKAN
 */
public class SendResourceGiftCommand extends FreeRealmAbstractCommand {

	private static final Logger logger = Logger.getLogger(SendResourceGiftCommand.class);
	public static final String PARAMETER_FROM_SETTLEMENT = "from_settlement";
	public static final String PARAMETER_TO_SETTLEMENT = "to_settlement";
	public static final String PARAMETER_RESOURCE = "  resource";
	public static final String PARAMETER_AMOUNT = "amount";
	private final Realm realm;
	private final Settlement fromSettlement;
	private final Settlement toSettlement;
	private final Resource resource;
	private final int amount;
	private final int resourceUnitValue;

	public SendResourceGiftCommand(Realm realm, Settlement fromSettlement, Settlement toSettlement, Resource resource, int amount, int resourceUnitValue) {
		this.realm = realm;
		this.fromSettlement = fromSettlement;
		this.toSettlement = toSettlement;
		this.resource = resource;
		this.amount = amount;
		this.resourceUnitValue = resourceUnitValue;
	}

	public void execute() {
		logger.trace("SendResourceGiftCommand executing...");
		TransferResourceCommand transferResourceCommand = new TransferResourceCommand(fromSettlement, toSettlement, resource, amount);
		getExecutor().execute(transferResourceCommand);
		if (transferResourceCommand.getState() == SUCCEEDED) {
			int resourceValue = amount * resourceUnitValue;
			String wealthNeededForAttitudeChangeProperty = realm.getProperty("wealth_needed_for_diplomatic_attitude_change");
			int wealthNeededForAttitudeChange = Integer.parseInt(wealthNeededForAttitudeChangeProperty);
			int attitudeDifference = resourceValue / wealthNeededForAttitudeChange;
			int attitude = toSettlement.getPlayer().getDiplomacy().getPlayerRelation(fromSettlement.getPlayer()).getAttitude();
			toSettlement.getPlayer().getDiplomacy().getPlayerRelation(fromSettlement.getPlayer()).setAttitude(attitude + attitudeDifference);
			if (logger.isTraceEnabled()) {
				String currencyUnit = realm.getProperty("currency_unit");
				StringBuilder log = new StringBuilder();
				log.append(amount);
				log.append(" units of ");
				log.append(resource.getName().toLowerCase());
				log.append(" having a worth of ");
				log.append(resourceValue);
				log.append(" ");
				log.append(currencyUnit);
				log.append(" sent from ");
				log.append(fromSettlement.getName());
				log.append(" to ");
				log.append(toSettlement.getName());
				log.append(". ");
				log.append(toSettlement.getPlayer().getName());
				log.append(" attitude to ");
				log.append(fromSettlement.getPlayer().getName());
				log.append(" increased from ");
				log.append(attitude);
				log.append(" to ");
				log.append(attitude + attitudeDifference);
				log.append(".");
				logger.trace(log);
			}
			logger.trace("SendResourceGiftCommand executed successfully.");
			putParameter(PARAMETER_FROM_SETTLEMENT, fromSettlement);
			putParameter(PARAMETER_TO_SETTLEMENT, toSettlement);
			putParameter(PARAMETER_RESOURCE, resource);
			putParameter(PARAMETER_AMOUNT, amount);
			setState(SUCCEEDED);
		} else {
			setState(FAILED);
		}
	}

}

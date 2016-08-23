package org.freerealm.command;

import org.apache.log4j.Logger;
import org.freerealm.Realm;
import org.freerealm.player.Player;

/**
 * 
 * @author Deniz ARIKAN
 */
public class SendWealthGiftCommand extends FreeRealmAbstractCommand {

	private static final Logger logger = Logger.getLogger(SendWealthGiftCommand.class);
	public static final String PARAMETER_FROM_PLAYER = "from_player";
	public static final String PARAMETER_TO_PLAYER = "to_player";
	public static final String PARAMETER_AMOUNT = "amount";
	private final Realm realm;
	private final Player fromPlayer;
	private final Player toPlayer;
	private final int amount;

	public SendWealthGiftCommand(Realm realm, Player fromPlayer, Player toPlayer, int amount) {
		this.realm = realm;
		this.fromPlayer = fromPlayer;
		this.toPlayer = toPlayer;
		this.amount = amount;
	}

	public void execute() {
		logger.trace("SendCreditGiftCommand executing...");
		getExecutor().execute(new WealthTransferCommand(fromPlayer, toPlayer, amount));
		String wealthNeededForAttitudeChangeProperty = realm.getProperty("wealth_needed_for_diplomatic_attitude_change");
		int wealthNeededForAttitudeChange = Integer.parseInt(wealthNeededForAttitudeChangeProperty);
		int attitudeDifference = amount / wealthNeededForAttitudeChange;
		int attitude = toPlayer.getDiplomacy().getPlayerRelation(fromPlayer).getAttitude();
		toPlayer.getDiplomacy().getPlayerRelation(fromPlayer).setAttitude(attitude + attitudeDifference);
		if (logger.isTraceEnabled()) {
			String currencyUnit = realm.getProperty("currency_unit");
			StringBuilder log = new StringBuilder();
			log.append(amount);
			log.append(" ");
			log.append(currencyUnit);
			log.append(" sent. ");
			log.append(toPlayer.getName());
			log.append(" attitude to ");
			log.append(fromPlayer.getName());
			log.append(" increased from ");
			log.append(attitude);
			log.append(" to ");
			log.append(attitude + attitudeDifference);
			log.append(".");
			logger.trace(log);
		}
		logger.trace("SendCreditGiftCommand executed successfully.");
		putParameter(PARAMETER_FROM_PLAYER, fromPlayer);
		putParameter(PARAMETER_TO_PLAYER, toPlayer);
		putParameter(PARAMETER_AMOUNT, amount);
		setState(SUCCEEDED);
	}

}

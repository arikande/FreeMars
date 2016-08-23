package org.freerealm.command;

import org.freerealm.player.Player;
import org.freerealm.random.RandomEvent;

/**
 * 
 * @author Deniz ARIKAN
 */
public abstract class DefaultRandomEventCommand extends FreeRealmAbstractCommand {

	private RandomEvent randomEvent;
	private Player player;

	public RandomEvent getRandomEvent() {
		return randomEvent;
	}

	public void setRandomEvent(RandomEvent randomEvent) {
		this.randomEvent = randomEvent;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}
}

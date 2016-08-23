package org.freerealm.command;

import org.freerealm.Realm;

/**
 * 
 * @author Deniz ARIKAN
 */
public class SetMaximumNumberOfTurnsCommand extends FreeRealmAbstractCommand {

	private final int maximumNumberOfTurns;

	public SetMaximumNumberOfTurnsCommand(Realm realm, int maximumNumberOfTurns) {
		super(realm);
		this.maximumNumberOfTurns = maximumNumberOfTurns;
	}

	public void execute() {
		getRealm().setMaximumNumberOfTurns(maximumNumberOfTurns);
		setState(SUCCEEDED);
	}

}

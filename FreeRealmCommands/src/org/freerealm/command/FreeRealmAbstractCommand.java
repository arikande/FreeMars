package org.freerealm.command;

import org.commandexecutor.AbstractCommand;
import org.freerealm.Realm;

public abstract class FreeRealmAbstractCommand extends AbstractCommand {
	private Realm realm;

	public FreeRealmAbstractCommand() {
	}

	public FreeRealmAbstractCommand(Realm realm) {
		this.realm = realm;
	}

	public Realm getRealm() {
		return realm;
	}

	public void setRealm(Realm realm) {
		this.realm = realm;
	}

}

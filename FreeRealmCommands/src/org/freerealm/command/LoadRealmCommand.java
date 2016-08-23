package org.freerealm.command;

import org.freerealm.Realm;
import org.freerealm.xmlwrapper.RealmXMLWrapper;
import org.w3c.dom.Node;

/**
 * Command class to load a realm from a node.
 * 
 * @author Deniz ARIKAN
 */
public class LoadRealmCommand extends FreeRealmAbstractCommand {

	private final Node node;

	/**
	 * Constructs a LoadRealmCommand using node<br>
	 * 
	 * @param file
	 */
	public LoadRealmCommand(Realm realm, Node node) {
		super(realm);
		this.node = node;
	}

	/**
	 * Executes command to load a realm from file.
	 * 
	 * @param realm
	 * @return CommandResult
	 */
	public void execute() {
		(new RealmXMLWrapper(getRealm())).initializeFromNode(getRealm(), node);
		setState(SUCCEEDED);
	}
}

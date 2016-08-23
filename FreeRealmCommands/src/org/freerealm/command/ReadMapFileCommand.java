package org.freerealm.command;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.xerces.parsers.DOMParser;
import org.freerealm.Realm;
import org.freerealm.map.AStarPathFinder;
import org.freerealm.map.FreeRealmMap;
import org.freerealm.xmlwrapper.XMLConverterUtility;
import org.freerealm.xmlwrapper.map.FreeRealmMapXMLConverter;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * Command class to read a new map for the realm from a stream. Newly read map
 * will replace current map of the realm and all items (units, cities etc.) will
 * be reset. If there is an error in map information a runtime exception will
 * occur.
 * 
 * @author Deniz ARIKAN
 */
public class ReadMapFileCommand extends FreeRealmAbstractCommand {

	private final InputStream inputStream;

	/**
	 * Constructs a ReadMapFileCommand using file.
	 * 
	 * @param inputStream
	 *            Stream containing map information
	 */
	public ReadMapFileCommand(Realm realm, InputStream inputStream) {
		super(realm);
		this.inputStream = inputStream;
	}

	/**
	 * Executes command to read new map from stream for the realm.
	 * 
	 * @return CommandResult
	 */
	public void execute() {
		BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
		InputSource inputSource = new InputSource(bufferedInputStream);
		DOMParser builder = new DOMParser();
		try {
			builder.parse(inputSource);
		} catch (SAXException exception) {
			setText("SAXException : " + exception.getMessage());
			setState(FAILED);
			return;
		} catch (IOException exception) {
			setText("IOException : " + exception.getMessage());
			setState(FAILED);
			return;
		}
		Node freeRealmMapNode = XMLConverterUtility.findNode(builder.getDocument(), "free_realm_map");
		if (freeRealmMapNode != null) {
			FreeRealmMap freeRealmMap = (new FreeRealmMapXMLConverter()).initializeFromNode(getRealm(), freeRealmMapNode);
			getRealm().setMap(freeRealmMap);
			getRealm().setPathFinder(new AStarPathFinder(getRealm(), 100));
			putParameter("map_name", freeRealmMap.getName());
			putParameter("map_description", freeRealmMap.getDescription());
			putParameter("suggested_players", freeRealmMap.getSuggestedPlayers());
			putParameter("width", freeRealmMap.getWidth());
			putParameter("height", freeRealmMap.getHeight());
			setState(SUCCEEDED);
		} else {
			setState(FAILED);
		}
	}
}

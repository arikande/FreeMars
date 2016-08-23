package org.freemars.updateprocessor;

import org.commandexecutor.UpdateProcessor;
import org.freemars.controller.FreeMarsController;

public abstract class FreeMarsUpdateProcessor implements UpdateProcessor {
	private FreeMarsController freeMarsController;

	public FreeMarsUpdateProcessor(FreeMarsController freeMarsController) {
		this.freeMarsController = freeMarsController;
	}

	public FreeMarsController getFreeMarsController() {
		return freeMarsController;
	}

}

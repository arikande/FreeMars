package org.commandexecutor;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractCommand implements Command {

	private Executor executor;
	private int state = Command.READY;
	private String text;
	private Map<String, Object> parameters = new HashMap<>();
	private Object signal;
	private Object waitOn;

	@Override
	public Executor getExecutor() {
		return executor;
	}

	@Override
	public void setExecutor(Executor executor) {
		this.executor = executor;
	}

	@Override
	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public void putParameter(String name, Object value) {
		parameters.put(name, value);
	}

	public Object getParameter(String name) {
		return parameters.get(name);
	}

	@Override
	public Object getSignal() {
		return signal;
	}

	@Override
	public void setSignal(Object signal) {
		this.signal = signal;
	}

	@Override
	public Object getWaitOn() {
		return waitOn;
	}

	@Override
	public void setWaitOn(Object waitOn) {
		this.waitOn = waitOn;
	}
}
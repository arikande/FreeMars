package org.commandexecutor;

public interface Command {

	public static final int READY = 0;
	public static final int EXECUTING = 1;
	public static final int SUCCEEDED = 2;
	public static final int FAILED = 3;

	public void execute();

	public Executor getExecutor();

	public void setExecutor(Executor executor);

	public int getState();

	public void setState(int state);

	public String getText();

	public void putParameter(String name, Object value);

	public Object getParameter(String name);

	public Object getSignal();

	public void setSignal(Object signal);

	public Object getWaitOn();

	public void setWaitOn(Object waitOn);
}

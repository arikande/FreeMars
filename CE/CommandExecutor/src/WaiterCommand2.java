import java.text.SimpleDateFormat;
import java.util.Date;

import org.commandexecutor.AbstractCommand;

public class WaiterCommand2 extends AbstractCommand {
	public static final String NAME = "WaiterCommand2";
	private String name;
	private int waitTime;

	public WaiterCommand2(String name, int waitTime) {
		this.name = name;
		this.waitTime = waitTime;
	}

	public String toString() {
		return name + " (" + waitTime + ")";
	}

	@Override
	public void execute() {
		try {
			System.out.println(getTime() + " " + name + " 222 is starting " + waitTime + "  ");
			Thread.sleep(waitTime);
			System.out.println(getTime() + " " + name + " 222 is finished  ");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private String getTime() {
		long yourmilliseconds = System.currentTimeMillis();
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss.SSS");
		Date resultdate = new Date(yourmilliseconds);
		return sdf.format(resultdate);
	}

}

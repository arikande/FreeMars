import java.text.SimpleDateFormat;
import java.util.Date;

import org.commandexecutor.AbstractCommand;

public class WaiterCommand extends AbstractCommand {

	public static final String NAME = "WaiterCommand";
	private String name;
	private int waitTime;

	public WaiterCommand(String name, int waitTime) {
		this.name = name;
		this.waitTime = waitTime;
	}

	public String toString() {
		return name + " (" + waitTime + ")";
	}

	@Override
	public void execute() {
		try {
			System.out.println(getTime() + " " + name + " is starting " + waitTime + "  ");
			Thread.sleep(waitTime);
			getExecutor().execute(new WaiterCommand2("WWW", 20000));
			System.out.println(getTime() + " " + name + " is finished  ");

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

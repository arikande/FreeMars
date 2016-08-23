import org.commandexecutor.DefaultExecutor;

public class ThreadedCommandsLauncher2 {
	public static void main(String[] args) {
		DefaultExecutor defaultExecutor1 = new DefaultExecutor();
		Thread executorThread1 = new Thread(defaultExecutor1);
		executorThread1.start();
		System.out.println("DefaultExecutor1 starter");

		WaiterCommand waiterCommandA = new WaiterCommand("A", 5000);
		defaultExecutor1.addCommandToQueue(waiterCommandA);

	}

}

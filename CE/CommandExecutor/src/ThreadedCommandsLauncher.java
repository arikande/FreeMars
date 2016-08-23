import org.commandexecutor.DefaultExecutor;
import org.commandexecutor.Executor;

public class ThreadedCommandsLauncher {
	public static void main(String[] args) {

		Executor defaultExecutor1 = new DefaultExecutor();
		Thread executorThread1 = new Thread(defaultExecutor1);
		executorThread1.start();
		System.out.println("DefaultExecutor1 starter");

		DefaultExecutor defaultExecutor2 = new DefaultExecutor();
		Thread executorThread2 = new Thread(defaultExecutor2);
		executorThread2.start();
		System.out.println("DefaultExecutor2 starter");

		Object signal = new Object();
		Object signal2 = new Object();
		Object signal3 = new Object();
		Object signal4 = new Object();

		defaultExecutor1.addCommandToQueue(new WaiterCommand("A", 4000));
		defaultExecutor1.addCommandToQueue(new WaiterCommand("B", 1000));
		WaiterCommand waiterCommand = new WaiterCommand("C", 3000);
		waiterCommand.setSignal(signal);
		defaultExecutor1.addCommandToQueue(waiterCommand);
		WaiterCommand waiterCommandD = new WaiterCommand("D", 11000);
		waiterCommandD.setSignal(signal2);
		defaultExecutor1.addCommandToQueue(waiterCommandD);
		WaiterCommand waiterCommandE = new WaiterCommand("E", 3000);
		waiterCommandE.setWaitOn(signal3);
		defaultExecutor1.addCommandToQueue(waiterCommandE);
		WaiterCommand waiterCommandF = new WaiterCommand("F", 3000);
		waiterCommandF.setWaitOn(signal4);
		defaultExecutor1.addCommandToQueue(waiterCommandF);

		defaultExecutor2.addCommandToQueue(new WaiterCommand("-- a --", 100));
		defaultExecutor2.addCommandToQueue(new WaiterCommand("-- b --", 100));
		defaultExecutor2.addCommandToQueue(new WaiterCommand("-- c --", 100));
		defaultExecutor2.addCommandToQueue(new WaiterCommand("-- d --", 100));
		defaultExecutor2.addCommandToQueue(new WaiterCommand("-- e --", 100));
		WaiterCommand fWaiterCommand = new WaiterCommand("-- f --", 100);
		fWaiterCommand.setWaitOn(signal);
		defaultExecutor2.addCommandToQueue(fWaiterCommand);

		WaiterCommand gWaiterCommand = new WaiterCommand("-- g --", 100);
		gWaiterCommand.setWaitOn(signal2);
		defaultExecutor2.addCommandToQueue(gWaiterCommand);

		defaultExecutor2.addCommandToQueue(new WaiterCommand("-- g1 --", 100));
		defaultExecutor2.addCommandToQueue(new WaiterCommand("-- g2 --", 100));
		defaultExecutor2.addCommandToQueue(new WaiterCommand("-- g3 --", 100));
		defaultExecutor2.addCommandToQueue(new WaiterCommand("-- g4 --", 100));
		defaultExecutor2.addCommandToQueue(new WaiterCommand("-- g5 --", 100));

		WaiterCommand hWaiterCommand = new WaiterCommand("-- h --", 100);
		hWaiterCommand.setSignal(signal3);
		defaultExecutor2.addCommandToQueue(hWaiterCommand);

		// System.out.println("here");
		// Scanner input = new Scanner(System.in);
		// while (true) {
		// String userInput = input.next();
		// defaultExecutor1.addCommandToQueue(new WaiterCommand(userInput,
		// 2000));
		// if (userInput.equals("q")) {
		// input.close();
		// break;
		// }
		// }
	}

}

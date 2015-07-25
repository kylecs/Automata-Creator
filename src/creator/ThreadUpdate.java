package creator;

public class ThreadUpdate implements Runnable{
	GraphicMachineCreator creator;
	Thread thread;
	public ThreadUpdate(GraphicMachineCreator creator){
		this.creator = creator;
		thread = new Thread(this);
		thread.start();
	}
	@Override
	public void run() {
		while(true){
			creator.update();
		}
		
	}
}

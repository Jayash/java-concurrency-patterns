package features.threads;

public class Threads {
	
	public static void main(String[] args) throws InterruptedException {
		
		Thread created = new Thread();
		created.start();
		
		
		Thread threadWithTask = new Thread(() -> System.out.println("Inside Thread " + Thread.currentThread().getName()));
		
		threadWithTask.start();
		
		Runnable interruptblyTask = () -> {
			while(!Thread.interrupted()) {
				System.out.println("I'm not interrupt " + Thread.currentThread().getName());
			}
		};
		
		Thread interruptable = new Thread(interruptblyTask);
		interruptable.start();
		Thread.sleep(1);
		interruptable.interrupt();
		
	}
}

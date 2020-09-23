package features.locks;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class IntrinsicLocks {

	private boolean state;
	
	public synchronized void synchronizedMethod() {
		state = !state;
		
		System.out.println("State changed to " + state);
	}
	
	public void synchronizedBlock() {
		
		System.out.println("locks owned by " + Thread.currentThread().getName());
		
		synchronized(this) {
			state = !state;
			System.out.println("lock owned lock after state changed " + Thread.currentThread().getName());
			System.out.println("State changed to " + state);
		}
		
	}
	
	public synchronized void reentrancy() {
		System.out.println("Before acuquiring again ");
		
		synchronized(this) {
			System.out.println("I own it " + Thread.currentThread().getName());
		}
	}
	
	public static void main(String[] args) throws InterruptedException {
		ExecutorService service = Executors.newCachedThreadPool();
		
		IntrinsicLocks self = new IntrinsicLocks();
		
		for(int i=0; i< 10; i++) {
			service.execute(() -> self.synchronizedMethod()); 
		}
		
		Thread.sleep(1000);
		
		for(int i=0; i< 10; i++) {
			service.execute(() -> self.synchronizedBlock());
		}
		
		Thread.sleep(1000);
		
		for(int i=0; i<10; i++) {
			service.execute(() -> self.reentrancy());
		}
		
		service.shutdown();
	}
}

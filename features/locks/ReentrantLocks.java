package features.locks;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLocks {
	
	private ReentrantLock reentrantLock = new ReentrantLock();
	
	private boolean state;
	
	public void lock() {
		
		reentrantLock.lock();
		
		try {
			System.out.println("Changing state");
			state = !state;
			System.out.println("state changed to " + state);
		} finally {
			reentrantLock.unlock();
		}
		
	}
	
	public void lockWithTiming() throws InterruptedException {
		
		if(!reentrantLock.tryLock(3, TimeUnit.SECONDS)) {
			System.err.println("Failed to acquire lock");
		} else {
			try {
				System.out.println("Simulating a blocking computation - forcing tryLock() to fail");
				Thread.sleep(3000);
			} finally {
				reentrantLock.unlock();
			}
		}
		
	}
	
	public static void main(String[] args) {
		
		ExecutorService service = Executors.newCachedThreadPool();
		
		ReentrantLocks reentrantLocks = new ReentrantLocks();
		
		for(int i=0; i< 10; i++) {
			service.execute(() -> reentrantLocks.lock());
		}
		
		for(int i=0; i< 10; i++) {
			service.execute(() -> {
					try {
						reentrantLocks.lockWithTiming();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				});
		}
		
		
		service.shutdown();
	}
}

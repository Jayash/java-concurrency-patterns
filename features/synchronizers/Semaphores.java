package features.synchronizers;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * Semaphores controls the number of activities that can access a resource or
 * perform a certain action;
 *
 * - First you give a number of 'permits';
 * 
 * - Activities will acquire it and release when they're done;
 * 
 * - If none is available, activity will block until one become available.
 *
 * Good for resource pools.
 *
 */
public class Semaphores {
	
	public static void main(String[] args) {
		ExecutorService service = Executors.newCachedThreadPool();
		
		Semaphore semaphore = new Semaphore(3);
		
		Runnable r = () -> {
			try {
				System.out.println("Trying To acquire lock " + Thread.currentThread().getName());
				
				if(semaphore.tryAcquire(2, TimeUnit.SECONDS)) {
					System.out.println("Acquired " + Thread.currentThread().getName());
					Thread.sleep(2000);
					System.out.println("Done " + Thread.currentThread().getName());
				} else System.out.println("can't acquire lock " + Thread.currentThread().getName());
				
			} catch(InterruptedException e) {
				e.printStackTrace();
			} finally {
				semaphore.release();
			}
		};
		
		for(int i=0; i<10; i++)
			service.execute(r);
		
		service.shutdown();
	}
}

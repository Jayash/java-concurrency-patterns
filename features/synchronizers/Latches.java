package features.synchronizers;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Latches are used to delay the progress of threads until it reach a terminal
 * state
 *
 * Most common implementation is CountDownLatch.
 * 
 * In CountDownLatch, each event adds 1. When ready, countDown() is called,
 * decrementing by counter by 1. await() method releases when counter is 0.
 * 
 * Single use synchronizer.
 */
public class Latches {
	
	public static void main(String[] args) {
		ExecutorService service = Executors.newCachedThreadPool();
		
		CountDownLatch latch = new CountDownLatch(3);
		
		Runnable r = () -> {
			try {
				
				Thread.sleep(1000);
				System.out.println("Service in " + Thread.currentThread().getName() + " initialized");
				latch.countDown();
			} catch(InterruptedException e) {
				e.printStackTrace();
			}
		};
		
		for(int i=0; i< 10; i++)
			service.execute(r);
		
		try {
			latch.await();
			System.out.println("All service are running");
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
		
		
		service.shutdown();
	}
}

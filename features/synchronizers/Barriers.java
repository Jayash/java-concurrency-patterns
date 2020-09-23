package features.synchronizers;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Barriers are used for blocking a group of threads until they come together at
 * a single point in order to proceed. Basically, convergence of threads.
 * 
 * Accepts a runnable in it's constructor to be called when the threads reach the
 * barrier, but before its unblocked
 * 
 * Most common implementation is cyclic barrier.
 * 
 */
public class Barriers {
	public static void main(String[] args) {
		
		Runnable barrierAction = () -> System.out.println("converge action");
		
		ExecutorService service = Executors.newCachedThreadPool();
		
		CyclicBarrier barrier = new CyclicBarrier(10, barrierAction);
		
		Runnable r = () -> {
			
			try {
				System.out.println("Doing Task for " + Thread.currentThread().getName());
				Thread.sleep(new Random().nextInt(10)*1000);
				System.out.println("Done for " + Thread.currentThread().getName());
				barrier.await();
				
				System.out.println("this will run after completion of all thread");
				
			} catch(InterruptedException | BrokenBarrierException e) {
				e.printStackTrace();
			}
		};
		
		for(int i=0; i< 10; i++)
			service.execute(r);
		
		service.shutdown();
	}
}

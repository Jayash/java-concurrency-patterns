package features.collections;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Concurrent Queue interface.
 * 
 * Implementations: LinkedBlockingQueue, ArrayBlockingQueue,
 * PriorityBlockingQueue, SynchronizedQueue.
 * 
 * Used for the Producer-Consumer pattern.
 * 
 * Blocking methods: put/take; Timed blocking methods: offer, poll;
 * 
 * Can be bounded or unbounded.
 * 
 */
public class BlockingQueue {
	
	public static void blockingQueue() {
		LinkedBlockingQueue<UUID> queue = new LinkedBlockingQueue<>(10);
		
		Runnable runConsumer = () -> {
			try {
				while(!Thread.currentThread().isInterrupted()) {
				
					UUID uuid = queue.take();
					System.out.println("Consumed: "+ uuid + " by " + Thread.currentThread().getName());
				}
			} catch (InterruptedException e) {
				System.err.println("Consumer Finished");
			}
				
		};
		
		Thread consumer1 = new Thread(runConsumer);
		consumer1.start();
		Thread consumer2 = new Thread(runConsumer);
		consumer2.start();
		
		Runnable runProducer = () -> {
			try {
				while(!Thread.currentThread().isInterrupted()) {
					Random r = new Random();
					Thread.sleep(r.nextInt(1000));
					UUID randomUUID = UUID.randomUUID();
					System.out.println("Producer: " + randomUUID + " by " +	Thread.currentThread().getName());
					queue.put(randomUUID);
				}
			} catch(InterruptedException e) {
				System.err.println("Producer Finished");
			}
		};
		
		Thread producer1 = new Thread(runProducer);
		producer1.start();
		Thread producer2 = new Thread(runProducer);
		producer2.start();
		Thread producer3 = new Thread(runProducer);
		producer3.start();
		
		try {
			Thread.sleep(10000);
			producer1.interrupt();
			producer2.interrupt();
			producer3.interrupt();
			consumer1.interrupt();
			consumer2.interrupt();
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		blockingQueue();
	}
}

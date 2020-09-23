package features.executors;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class CachedThreadPool {
	
	
	public static void cachedThreadPool() {
		
		ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
		
		List<Future<UUID>> uuids = new LinkedList<>();
		
		for(int i=0; i<10; i++) {
			
			Future<UUID> summitedUUID = cachedThreadPool.submit(() -> {
				
				UUID randomUUID = UUID.randomUUID();
				
				System.out.println("UUID " + randomUUID + 	" from " + Thread.currentThread().getName());
				
				return randomUUID;
			});
						
			uuids.add(summitedUUID);
		}
		
		cachedThreadPool.execute(() -> uuids.forEach((f) -> {
			try {
				System.out.println("Result " + f.get() + " from thread " + Thread.currentThread().getName());
			} catch(InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
		}));
 		
		cachedThreadPool.shutdown();
		try {
			cachedThreadPool.awaitTermination(4, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) {
		cachedThreadPool();
	}
}

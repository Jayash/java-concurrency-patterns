package features.executors;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class FixedThreadPool {
	
	public static void fixedThreadPool() {
		
		ExecutorService fixedThreadPool = Executors.newFixedThreadPool(4);
		
		List<Future<UUID>> uuids = new LinkedList<>();
		
		for(int i=0; i< 10; i++) {
			
			Future<UUID> uuid = fixedThreadPool.submit(() -> {
				UUID randomUUID = UUID.randomUUID();
				System.out.println("UUID " + randomUUID + " from " + Thread.currentThread().getName());
				return randomUUID;
			});
			uuids.add(uuid);
		}
		
		fixedThreadPool.execute(() -> uuids.forEach((f) -> {
			try {
				System.out.println("Result " + f.get()  + " from " + Thread.currentThread().getName());
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
		}));
		
		fixedThreadPool.shutdown();
	}
	
	public static void main(String[] args) {
		fixedThreadPool();
	}
}

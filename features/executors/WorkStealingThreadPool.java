package features.executors;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class WorkStealingThreadPool {
	
	public static void usingWorkStealingThreadPool() {
		
		ExecutorService workStealingPool = Executors.newWorkStealingPool();

		workStealingPool.execute(() -> System.out.println("Prints normally"));

		Callable<UUID> generatesUUID = UUID::randomUUID;
		List<Callable<UUID>> severalUUIDsTasks = new LinkedList<Callable<UUID>>();
		for (int i = 0; i < 20; i++) {
			severalUUIDsTasks.add(generatesUUID);
		}

		try {
			List<Future<UUID>> futureUUIDs = workStealingPool.invokeAll(severalUUIDsTasks);
			for (Future<UUID> future : futureUUIDs) {
				if (future.isDone()) {
					String uuid = future.get().toString();
					System.out.println("New UUID :" + uuid);
				}
			}
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		try {
			workStealingPool.awaitTermination(6, TimeUnit.SECONDS);
			workStealingPool.shutdown();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		usingWorkStealingThreadPool();
	}

}

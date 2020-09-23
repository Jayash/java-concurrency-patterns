package features.executors;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScheduledThreadPool {
	
	public static void scheduledThreadPool() {
		ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(4);
		
		scheduledThreadPool.scheduleAtFixedRate(() -> System.out.println("1) Print every 2 sec"), 0, 2, TimeUnit.SECONDS);
		scheduledThreadPool.scheduleAtFixedRate(() -> System.out.println("2) Print every 2 sec"), 0, 2, TimeUnit.SECONDS);
		scheduledThreadPool.scheduleAtFixedRate(() -> System.out.println("3) Print every 2 sec"), 0, 2, TimeUnit.SECONDS);
		try {
			scheduledThreadPool.awaitTermination(6, TimeUnit.SECONDS);
			scheduledThreadPool.shutdown();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		scheduledThreadPool();
	}
}

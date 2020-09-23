package features.executors;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SingleTreadScheduledExecutor {
	
	public static void singleThreadScheduledExecutor() {
		ScheduledExecutorService singleThreadScheduledExecutor = Executors.newSingleThreadScheduledExecutor();
		singleThreadScheduledExecutor.scheduleAtFixedRate(() -> System.out.println("1) Print every 2s"), 0, 2, TimeUnit.SECONDS);
		singleThreadScheduledExecutor.scheduleWithFixedDelay(() -> System.out.println("2) Print every 2s delay"), 0, 2, TimeUnit.SECONDS);

		try {
			singleThreadScheduledExecutor.awaitTermination(6, TimeUnit.SECONDS);
			singleThreadScheduledExecutor.shutdown();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		singleThreadScheduledExecutor();
	}
}

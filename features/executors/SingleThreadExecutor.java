package features.executors;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SingleThreadExecutor {
	
	public static void singleThreadExecutor() {
		ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();
		
		for(int i=0; i< 10; i++)
			singleThreadExecutor.execute(() -> System.out.println("Print " + Thread.currentThread().getName()));
		
		singleThreadExecutor.shutdown();
		
	}
	
	
	public static void main(String[] args) {
		singleThreadExecutor();
	}
}

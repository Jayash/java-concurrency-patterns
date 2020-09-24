package features.collections;

import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Replacement for synchronized list. Based on the immutable object concept.
 * 
 * Use when reading is far more common than writing.
 * 
 * Creates a new copy every time that the list is modified, only synchronizing
 * briefly to ensure array content visibility.
 * 
 * iterator returns a snapshot of the current state of the collection.
 * 
 * Supports atomic operations.
 * 
 */
public class UsingCopyOnWriteArrayList {
	
	
	public static void copyOnWriteArrayList() {
	
		ExecutorService service = Executors.newCachedThreadPool();
		Random random = new Random();
		
		CopyOnWriteArrayList<Integer> copyOnWriteArrayList = new CopyOnWriteArrayList<>();
		
		for(int i=0; i< 100; i++) {
			if(i%8 == 0) {
				service.execute(() -> {
					int value = random.nextInt();
					System.err.println("Added: " + value);
					copyOnWriteArrayList.add(value);
				});
			} else {
				service.execute(() -> {
					StringBuilder sb = new StringBuilder();
					
					
					for(Integer value: copyOnWriteArrayList)
						sb.append(value + " ");
					
					System.out.println("Reading " + sb.toString());
					
				});
			}
		}
		
		service.shutdown();
		
		try {
			service.awaitTermination(2000, TimeUnit.SECONDS);
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) {
		copyOnWriteArrayList();
	}
}

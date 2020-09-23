package features.locks;

import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;

public class ReadWriteLocks {
	
	private ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
	private String myContent = "A long default content.....";
	
	
	public String showContent() {
		ReadLock readLock = readWriteLock.readLock();
		
		readLock.lock();
		
		try {
			
			System.out.println("Reading state while holding a lock");
			return myContent;
			
		} finally {
			readLock.unlock();
		}
	}
	
	public void writeContent(String content) {
		WriteLock writeLock = readWriteLock.writeLock();
		
		writeLock.lock();
		
		try {
			
			System.out.println("Writing new Content ");
			myContent = new StringBuilder(myContent).append(" " + content).toString();
		} finally {
			writeLock.unlock();
		}
	}
	
	public static void main(String[] args) {
		ExecutorService service = Executors.newCachedThreadPool();
		
		ReadWriteLocks self = new ReadWriteLocks();
		
		for(int i=0; i< 10; i++) {
			service.execute(() -> {
				try {
					Thread.sleep(1000);
					
				} catch(InterruptedException e) {
					e.printStackTrace();
				}
				
				System.out.println(self.myContent);
				
			}); 
		}
		
		for(int i=0; i< 100; i++) {
			service.execute(() -> self.writeContent(UUID.randomUUID().toString()));
		}
		
		service.shutdown();
	}
	
}

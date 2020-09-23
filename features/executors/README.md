Thread creation is expensive and difficult to manage.
  
Executors help us to decouple task submission from execution.
  
We have 6 types of executors:
  
- Single Thread Executor: Uses a single worker to process tasks.
  
- Cached Thread Pool: Unbounded thread limit, good performance for long running tasks.
  
- Fixed Thread Pool: Bounded thread limit, maintains the same thread pool size.
  
- Scheduled Thread Pool: Bounded thread limit, used for delayed tasks.
  
- Single-Thread Scheduled Pool: Similar to the scheduled thread pool, but single-threaded, with only one active task at the time. 
  
- Work-Stealing Thread Pool: Based on Fork/Join Framework, applies the work-stealing algorithm for balancing tasks, with available processors as a paralellism level.
  
And 2 types of tasks:
  
- execute: Executes without giving feedback. Fire-and-forget.
  
- submit: Returns a FutureTask.
  
ThreadPools: Used by the executors described above. ThreadPoolExecutor can be used to create custom Executors.
  
shutdown() -> Waits for tasks to terminate and release resources.
shutdownNow() -> Try to stops all executing tasks and returns a list of not executed tasks.
 
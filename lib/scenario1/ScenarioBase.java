package lib.scenario1;
import java.util.concurrent.*;
public class ScenarioBase {
    public static void main(String[] args) {
        int numProducers = 5; // Número de produtores
        int numNodes = 3; // Número de nós de processamento
        BlockingQueue<Task> taskQueue = new LinkedBlockingQueue<>();
        ConcurrentHashMap<Long, String> resultStorage = new ConcurrentHashMap<>();
  
        ExecutorService executor = Executors.newCachedThreadPool();
  
        for (int i = 0; i < numProducers; i++) {
            executor.execute(new TaskProducer(taskQueue, 10));
        }
        System.out.println(taskQueue.toString());
        for (int i = 0; i < numNodes; i++) {
            executor.execute(new Node(taskQueue, resultStorage));
        }
  
        executor.shutdown();
        try {
            System.out.println("All tasks processed.");
            executor.awaitTermination(5000, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
  }
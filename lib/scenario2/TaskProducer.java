package lib.scenario2;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicLong;

public class TaskProducer implements Runnable {
    private BlockingQueue<Task> queue;
    private Integer priorityInteger;
    private static AtomicLong nextId = new AtomicLong(0); // Para garantir IDs únicos
    private Map<Long, Long> completedTasks = new HashMap<>();

    public TaskProducer(BlockingQueue<Task> queue) {
        this.queue = queue;
    }

    public TaskProducer(BlockingQueue<Task> queue, Integer priorityInteger) {
        this.queue = queue;   
        this.priorityInteger = priorityInteger;     
    }

    @Override
    public void run() {
        new Thread(()-> {
            while(!Thread.interrupted()){
                try {
                    Thread.sleep(5000);
                    printStatistics();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupted();
                }
            }
        }).start();

        while (!Thread.interrupted()) {
            long id = nextId.getAndIncrement(); // Gera um novo ID
            Task task = new Task(id);
            try {
                System.out.println("Producer created");
                
                switch (priorityInteger) {
                    case 0:
                        Thread.sleep(13000);
                    case 1:
                        Thread.sleep(7000);
                    case 2:
                        Thread.sleep(3000);
                }
                queue.put(task);

                long endTime = System.currentTimeMillis();
                task.setEndTime(endTime);
                completedTasks.put(task.getId(), task.getActiveTime());
                // System.out.println(completedTasks.toString());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Producer interrupted");
            }            
        }        
    }

    public void printStatistics() {
        if (this.completedTasks.isEmpty()) {
            System.out.println("no task completed");
            return;
        }

        long totalActiveTime = 0;
        int taskCount = 0;

        for (Map.Entry<Long, Long> entry: completedTasks.entrySet()) {
            long taskId = entry.getKey();

            long activeTime = entry.getValue();

            System.out.println("Task id: " + taskId +  " " + "Active Time: " + activeTime + "\n");


            totalActiveTime += activeTime;

            taskCount++;
        } 

        double averageActiveTime = (double) totalActiveTime / taskCount;

        System.out.println("Média de Tempo Ativo: " + averageActiveTime);
    }
}
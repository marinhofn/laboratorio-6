package lib.scenario2;
import java.util.Random;

public class Task {
    long id;
    long startTime;
    long endTime;

    public Task(long id) {
        this.id = id;
        this.startTime = System.currentTimeMillis();
    }

    public long getStartTime() { 
        return startTime;
    }

    public long getEndTime() { 
        return endTime;
    }

    public void setEndTime(long endTime) { 
        this.endTime = endTime;
    }

    public long getActiveTime(){
        return endTime - startTime;
    }

    public long getId() {
        return this.id;
    }

    public void execute() {
        try {
            // generating a number between 1000 and 15000
            long execDuration = 1000 + (long) (new Random().nextFloat() * (15000 - 1000));
            Thread.sleep(execDuration);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

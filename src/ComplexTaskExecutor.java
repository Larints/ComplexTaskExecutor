import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ComplexTaskExecutor {

    private final CyclicBarrier cycl;
    private final ExecutorService executor;
    private final ComplexTask complexTask;

    public ComplexTaskExecutor(int countOfTasks) {
        this.cycl = new CyclicBarrier(countOfTasks, () -> {
            if (ComplexTask.isCompleted()) {
                System.out.println("All tasks completed");
            }
        });
        this.executor = Executors.newFixedThreadPool(countOfTasks);
        this.complexTask = new ComplexTask(cycl);
    }

    public void executeTasks(int numberOfTasks) {
        for (int i = 0; i < numberOfTasks; i++) {
            executor.execute(() -> {
                complexTask.executeTask();
                try {
                    cycl.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
            });
        }
        executor.shutdown();
    }
}

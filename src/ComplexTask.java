import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CyclicBarrier;

public class ComplexTask {

    private static final List<String> taskList = new ArrayList<>();
    private static volatile int counter;
    private static CyclicBarrier cycl;

    static {
        taskList.add("TestTask1");
        taskList.add("TestTask2");
        taskList.add("TestTask3");
        taskList.add("TestTask4");
        taskList.add("TestTask5");
    }

    public ComplexTask(CyclicBarrier cycl) {
        ComplexTask.cycl = cycl;
    }

    public synchronized void executeTask() {
        if (counter >= taskList.size()) return;
        taskList.set(counter, "JobDone");
        counter++;
        System.out.println(taskList);
    }

    public static boolean isCompleted() {
        return taskList.stream().allMatch(task -> task.equals("JobDone"));
    }
}

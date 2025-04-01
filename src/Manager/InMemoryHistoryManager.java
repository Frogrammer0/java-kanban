package Manager;
import java.util.ArrayList;
import java.util.List;
import TaskObject.*;

public class InMemoryHistoryManager implements HistoryManager {

    private final ArrayList<Task> history = new ArrayList<>(10);

    @Override
    public void add(Task task) {
        if (history.size() == 10) {
            history.removeFirst();
        }
        Task copyTask = new Task(task.getTitle(), task.getDescription(), task.getStatus());
        copyTask.setId(task.getId());
        history.addLast(copyTask);
    }

    @Override
    public List<Task> getHistory() {
        return new ArrayList<>(history);
    }




}

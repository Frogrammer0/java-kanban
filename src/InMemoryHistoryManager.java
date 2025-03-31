import java.util.ArrayList;

public class InMemoryHistoryManager implements HistoryManager {

    private static final ArrayList<Task> history = new ArrayList<>(10);

    @Override
    public void add(Task task) {
        if (history.size() == 10) {
            history.removeFirst();
        }
        history.addLast(task);
    }

    @Override
    public ArrayList<Task> getHistory() {
        return  history;
    }


}

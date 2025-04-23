package Manager;
import java.util.List;
import TaskObject.*;


public interface HistoryManager {

    void add(Task task);

    List<Task> getHistList();

    void removeView(int id);
}

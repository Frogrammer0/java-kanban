package manager;
import java.util.List;
import taskobject.*;


public interface HistoryManager {

    void add(Task task);

    List<Task> getHistList();

    void removeView(int id);
}

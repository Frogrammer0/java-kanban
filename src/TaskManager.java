import java.util.ArrayList;

public interface TaskManager {
    Task getTask(int id);

    SubTask getSubTask(int id);

    Task getEpicTask(int id);

    ArrayList<Task> getAllTask();

    ArrayList<EpicTask> getAllEpicTask();

    ArrayList<SubTask> getAllSubTask();

    ArrayList<Task> getAllTaskAllType();

    void createTask(Task task);

    void removeAllTask();

    void removeTask(int id);

    void removeAllEpic();

    void removeAllSub();

    void removeEpic(int id);

    void removeSub(int id);

    void updateTask(Task task);

}

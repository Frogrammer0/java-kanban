package manager;

import taskobject.*;

import java.util.ArrayList;
import java.util.List;

public interface TaskManager {

    Task getTask(int id);

    SubTask getSubTask(int id);

    Task getEpicTask(int id);

    List<Task> getHistory();

    List<Task> getAllTask();

    List<EpicTask> getAllEpicTask();

    List<SubTask> getAllSubTask();

    ArrayList<SubTask> getAllSubTasksByEpicId(int epicId);

    List<Task> getAllTaskAllType();

    void createTask(Task task);

    void removeAllTask();

    void removeAllEpic();

    void removeAllSub();

    void removeTask(int id);

    void removeEpic(int id);

    void removeSub(int id);

    public void removeHistoryItem(int id);

    void updateTask(Task task);

    void updateEpicTask(EpicTask epicTask);

    void updateSubTask(SubTask subTask);


}

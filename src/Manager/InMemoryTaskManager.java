package Manager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import TaskObject.*;

public class InMemoryTaskManager implements TaskManager {

    private int idNumber = 0;
    private final Map<Integer, Task> taskMap = new HashMap<>();
    private final Map<Integer, EpicTask> epicMap = new HashMap<>();
    private final Map<Integer, SubTask> subMap = new HashMap<>();
    private final HistoryManager historyManager = Managers.getDefaultHistory();

    @Override
    public List<Task> getHistory() {
        return new ArrayList<>(historyManager.getHistory());
    }

    public Map<Integer, Task> getTaskMap() {
        return taskMap;
    }

    public Map<Integer, EpicTask> getEpicMap() {
        return epicMap;
    }

    public Map<Integer, SubTask> getSubMap() {
        return subMap;
    }

    private int assignId() { //метод для присвоения Ид
        idNumber++;
        return idNumber;
    }

    private int resetId() {
        idNumber = 0;
        return idNumber;
    }


    @Override
    public Task getTask(int id) { //метод для получения обычной задачи
        historyManager.add(taskMap.get(id));
        return taskMap.get(id);
    }

    @Override
    public EpicTask getEpicTask(int id) {
        historyManager.add(epicMap.get(id));
        return epicMap.get(id);
    }

    @Override
    public SubTask getSubTask(int id) {
        historyManager.add(subMap.get(id));
        return subMap.get(id);
    }

    @Override
    public ArrayList<Task> getAllTask() {  //метод для получения всех обычных задач
        ArrayList<Task> tasks = new ArrayList<>();
        for (int i : taskMap.keySet()) {
            tasks.add(taskMap.get(i));
        }
        return tasks;
    }

    @Override
    public ArrayList<EpicTask> getAllEpicTask() { //метод для получения всех эпиков
        ArrayList<EpicTask> tasks = new ArrayList<>();
        for (int i : epicMap.keySet()) {
            tasks.add(epicMap.get(i));
        }
        return tasks;
    }

    @Override
    public ArrayList<SubTask> getAllSubTask() { //метод для получения всех подзадачи из всех эпиков
        ArrayList<SubTask> tasks = new ArrayList<>();
        for (int i : subMap.keySet()) {
            tasks.add(subMap.get(i));
        }
        return tasks;
    }

    @Override
    public ArrayList<Task> getAllTaskAllType() { //получения всех задач всех типов
        ArrayList<Task> allTask = new ArrayList<>();

        allTask.addAll(taskMap.values());
        allTask.addAll(epicMap.values());
        allTask.addAll(subMap.values());

        return allTask;
    }

    @Override
    public ArrayList<SubTask> getAllSubTasksByEpicId(int epicId) { //метод для получения списка подзадач из эпика
        ArrayList<SubTask> tasks = new ArrayList<>();
        if (epicMap.containsKey(epicId)) {
            EpicTask epic = epicMap.get(epicId);
            for (int i : epic.getSubTasks().keySet()) {
                tasks.add(epic.getSubTasks().get(i));
            }
        }
        return tasks;
    }

    @Override
    public void createTask(Task task) { //метод для создания любой задачи
        task.setId(assignId());
        if (task instanceof SubTask subTask) {
            if (epicMap.containsKey(subTask.getEpicId())) {
                subMap.put(subTask.getId(), subTask); //сначала кладем подзадачу в общее хранилище подзадач

                epicMap.get(subTask.getEpicId()).setSubTask(subTask);
                epicMap.get(subTask.getEpicId()).setStatus();           //кладем подзадачу в соответствюущий эпик
            }
        } else if (task instanceof EpicTask epic) {
            epicMap.put(epic.getId(), epic);
        } else {
            taskMap.put(task.getId(), task);
        }
    }


    @Override
    public void removeAllTask() { //метод для удаления всех задач
        taskMap.clear();
    }

    @Override
    public void removeAllEpic() {//метод для удаления всех эпик задач

        epicMap.clear();
        subMap.clear();
    }

    @Override
    public void removeAllSub() { //метод для удаления всех задач
        for (EpicTask epic : epicMap.values()) {
            epic.getSubTasks().clear();
            epic.setStatus();
        }
        subMap.clear();
    }

    public void removeAllTasksAllType() {
        taskMap.clear();
        epicMap.clear();
        subMap.clear();
        resetId();
    }

    @Override
    public void removeTask(int id) { //метод для удаления по Ид
        taskMap.remove(id);
    }

    @Override
    public void removeEpic(int id) {
        ArrayList<Integer> deleteKey = new ArrayList<>();
        for (int i : subMap.keySet()) {
            if (subMap.get(i).getEpicId() == id) {
                deleteKey.add(i);
            }
        }
        for (int i : deleteKey) {
            subMap.remove(i);
        }
        epicMap.remove(id);
    }

    @Override
    public void removeSub(int id) {
        if (subMap.containsKey(id)) {
            epicMap.get(subMap.get(id).getEpicId()).removeSub(id);
            epicMap.get(subMap.get(id).getEpicId()).setStatus();
            subMap.remove(id);
        }
    } //данный метод при передаче айди удаляет подзадачу как из эпика, так и из хранилища со всеми подзадачами


    @Override
    public void updateTask(Task task) { //метод для обновления обычной задачи
        if (taskMap.containsKey(task.getId())) {
            taskMap.put(task.getId(), task);
        }
    }


    public void updateEpicTask(EpicTask epicTask) {
        if (epicMap.containsKey(epicTask.getId())) {
            epicMap.get(epicTask.getId()).setTitle(epicTask.getTitle());
            epicMap.get(epicTask.getId()).setDescription(epicTask.getDescription());
        }
    }

    //перегружен
    public void updateSubTask(SubTask subTask) { //метод для обновления подзадачи
        if (subMap.containsKey(subTask.getId()) && epicMap.containsKey(subTask.getEpicId()) &&
                subMap.get(subTask.getId()).getEpicId() == subTask.getEpicId()) {
            subMap.put(subTask.getId(), subTask);
            epicMap.get(subTask.getEpicId()).setSubTask(subTask);
            epicMap.get(subTask.getEpicId()).setStatus();
        }
    }


}

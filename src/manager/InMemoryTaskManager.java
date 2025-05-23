package manager;

import taskobject.EpicTask;
import taskobject.Status;
import taskobject.SubTask;
import taskobject.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryTaskManager implements TaskManager {

    private final Map<Integer, Task> taskMap = new HashMap<>();
    private final Map<Integer, EpicTask> epicMap = new HashMap<>();
    private final Map<Integer, SubTask> subMap = new HashMap<>();
    private final HistoryManager historyManager = Managers.getDefaultHistory();
    private int idNumber = 0;

    @Override
    public List<Task> getHistory() {
        return new ArrayList<>(historyManager.getHistList());
    }

    public Map<Integer, Task> getTaskMap() {
        return taskMap;
    }

    public Map<Integer, EpicTask> getEpicMap() {
        return epicMap;
    }

    public void removeHistoryItem(int id) {
        historyManager.removeView(id);
    }

    public Map<Integer, SubTask> getSubMap() {
        return subMap;
    }

    private int assignId() { //метод для присвоения Ид
        idNumber++;
        return idNumber;
    }

    private void resetId() {
        idNumber = 0;
    }


    @Override
    public Task getTask(int id) { //метод для получения обычной задачи
        if (taskMap.containsKey(id)) {
            historyManager.add(taskMap.get(id));
            return taskMap.get(id);
        } else return null;

    }

    @Override
    public EpicTask getEpicTask(int id) {
        if (epicMap.containsKey(id)) {
            historyManager.add(epicMap.get(id));
            return epicMap.get(id);
        } else return null;
    }

    @Override
    public SubTask getSubTask(int id) {
        if (subMap.containsKey(id)) {
            historyManager.add(subMap.get(id));
            return subMap.get(id);
        } else return null;
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
            for (int i : epic.getSubTasksId()) {
                tasks.add(subMap.get(i));
            }
        }
        return tasks;
    }

    @Override
    public void createTask(Task task) { //метод для создания любой задачи
        task.setId(assignId());
        if (task instanceof SubTask subTask) {
            if (epicMap.containsKey(subTask.getEpicId())) {
                getSubMap().put(subTask.getId(), subTask); //сначала кладем подзадачу в общее хранилище подзадач

                epicMap.get(subTask.getEpicId()).setSubTasksId(subTask.getId());
                setEpicStatus(epicMap.get(subTask.getEpicId()));           //кладем подзадачу в соответствюущий эпик
            }
        } else if (task instanceof EpicTask epic) {
            epicMap.put(epic.getId(), epic);
        } else {
            taskMap.put(task.getId(), task);
        }
    }


    @Override
    public void removeAllTask() {                                                     //метод для удаления всех задач
        for (int id : taskMap.keySet()) {
            historyManager.removeView(id);
        }
        taskMap.clear();
    }

    @Override
    public void removeAllEpic() {                                           //метод для удаления всех эпик задач
        for (int id : epicMap.keySet()) {
            historyManager.removeView(id);
        }
        for (int id : subMap.keySet()) {
            historyManager.removeView(id);
        }
        epicMap.clear();
        subMap.clear();
    }

    @Override
    public void removeAllSub() { //метод для удаления всех подзадач
        for (int id : subMap.keySet()) {
            historyManager.removeView(id);
        }
        for (EpicTask epic : epicMap.values()) {
            epic.getSubTasksId().clear();
            setEpicStatus(epic);
        }
        subMap.clear();
    }

    public void removeAllTasksAllType() {
        for (int i = 1; i <= idNumber; i++) {
            historyManager.removeView(i);
        }
        taskMap.clear();
        epicMap.clear();
        subMap.clear();
        resetId();
    }

    @Override
    public void removeTask(int id) { //метод для удаления по Ид
        taskMap.remove(id);
        historyManager.removeView(id);
    }

    @Override
    public void removeEpic(int id) {
        for (int i : epicMap.get(id).getSubTasksId()) {
            subMap.remove(i);
            historyManager.removeView(i);
        }
        epicMap.remove(id);
        historyManager.removeView(id);

    }

    @Override
    public void removeSub(int id) {
        if (subMap.containsKey(id)) {
            epicMap.get(subMap.get(id).getEpicId()).removeSubId(id);

            setEpicStatus(epicMap.get(subMap.get(id).getEpicId()));

            subMap.remove(id);
        }
        historyManager.removeView(id);
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
            setEpicStatus(epicMap.get(epicTask.getId()));
        }
    }


    public void updateSubTask(SubTask subTask) { //метод для обновления подзадачи
        if (subMap.containsKey(subTask.getId()) && epicMap.containsKey(subTask.getEpicId()) &&
                subMap.get(subTask.getId()).getEpicId() == subTask.getEpicId()) {
            subMap.put(subTask.getId(), subTask);
            epicMap.get(subTask.getEpicId()).setSubTasksId(subTask.getId());
            setEpicStatus(epicMap.get(subTask.getEpicId()));
        }
    }

    public void setEpicStatus(EpicTask epicTask) { //метод для обновления статуса эпика
        boolean hasNew = false;
        boolean hasDone = false;
        boolean hasProgress = false;

        for (int i : epicTask.getSubTasksId()) {

            if (subMap.get(i).getStatus() == Status.NEW) {
                hasNew = true;
            } else if (subMap.get(i).getStatus() == Status.DONE) {
                hasDone = true;
            } else if (subMap.get(i).getStatus() == Status.IN_PROGRESS) {
                hasProgress = true;
            }

        }
        if (hasNew && hasDone || hasProgress) {
            epicTask.setStatus(Status.IN_PROGRESS);
        } else {
            epicTask.setStatus(hasDone ? Status.DONE : Status.NEW);
        }
    }

    protected void loadTask(Task task) {
        if (task.getId() > idNumber) idNumber = task.getId();
        if (task instanceof EpicTask epic) {
            epicMap.put(epic.getId(), epic);
        } else if (task instanceof SubTask sub) {
            subMap.put(sub.getId(), sub);
            epicMap.get(sub.getEpicId()).setSubTasksId(sub.getId());
        } else {
            taskMap.put(task.getId(), task);
        }
    }


}

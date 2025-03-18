import java.util.ArrayList;
import java.util.HashMap;

public class TaskManager {

    private static int idNumber = 0;
    public HashMap<Integer, Task> taskMap = new HashMap<>();
    public HashMap<Integer, EpicTask> epicMap = new HashMap<>();
    public HashMap<Integer, SubTask> subMap = new HashMap<>();


    private int assignId() { //метод для присвоения Ид
        idNumber++;
        return idNumber;
    }

    public Task getTask(int id) { //метод для получения обычной задачи
        return taskMap.get(id);
    }

    public ArrayList<Task> getAllTask() {  //метод для получения всех обычных задач
        ArrayList<Task> tasks = new ArrayList<>();
        for (int i : taskMap.keySet()) {
            tasks.add(taskMap.get(i));
        }
        return tasks;
    }


    public ArrayList<EpicTask> getAllEpicTask() { //метод для получения всех эпиков
        ArrayList<EpicTask> tasks = new ArrayList<>();
        for (int i : epicMap.keySet()) {
            tasks.add(epicMap.get(i));
        }
        return tasks;
    }

    public ArrayList<SubTask> getAllSubTask() { //метод для получения всех подзадачи из всех эпиков
        ArrayList<SubTask> tasks = new ArrayList<>();
        for (int i : subMap.keySet()) {
            tasks.add(subMap.get(i));
        }
        return tasks;
    }

    public ArrayList<Task> getAllTaskAllType() { //получения всех задач всех типов
        ArrayList<Task> allTask = new ArrayList<>();

        allTask.addAll(taskMap.values());
        allTask.addAll(epicMap.values());
        allTask.addAll(subMap.values());

        return allTask;
    }

    public ArrayList<SubTask> getSubTasksFromEpic(int epicId) { //метод для получения списка подзадач из эпика
        ArrayList<SubTask> tasks = new ArrayList<>();
        if (epicMap.containsKey(epicId)) {
            EpicTask epic = epicMap.get(epicId);
            for (int i : epic.subTasks.keySet()) {
                tasks.add(epic.subTasks.get(i));
            }
        }
        return tasks;
    }

    public void createTask(Task task) { //метод для создания обычной задачи
        task.setId(assignId());
        taskMap.put(task.getId(), task);
    }


    public void createEpic(EpicTask epicTask) { //метод для создания эпика
        epicTask.setId(assignId());
        epicMap.put(epicTask.getId(), epicTask);
    }

    public void createSubTask(SubTask subTask) {//метод для создания и добавления подзадач в эпик

        if (epicMap.containsKey(subTask.getEpicId())) {
            subTask.setId(assignId());
            subMap.put(subTask.getId(), subTask); //сначала кладем подзадачу в общее хранилище подзадач

            EpicTask epic = epicMap.get(subTask.getEpicId());
            epic.setSubTask(subTask);
            epic.setStatus();
            epicMap.put(epic.getId(), epic); //кладем подзадачу в соответствюущий эпик
        }
    }


    public void removeAllTask() { //метод для удаления всех задач
        taskMap.clear();
    }

    public void removeAllEpic() {//метод для удаления всех задач

        epicMap.clear();           // !!! не очень понимаю, нужно в это методе удалять так же и все подзадачи (Subtask)?
        /*subMap.clear();*/        // ибо по логике, у нас же не может быть подзадач без Эпик задачи
    }

    public void removeAllSub() { //метод для удаления всех задач
        subMap.clear();
    }

    public void removeTask(int id) { //метод для удаления по Ид
        taskMap.remove(id);
    }

    public void removeEpic(int id) {
        epicMap.remove(id);
    }

    public void removeSub(int id) {
        if (subMap.containsKey(id)) {
            epicMap.get(subMap.get(id).getEpicId()).removeSub(id);
            subMap.remove(id);
        }
    } //данный метод при передаче айди удаляет подзадачу как из эпика, так и из хранилища со всеми подзадачами


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

    public void updateSubTask(SubTask subTask) { //метод для обновления подзадачи
        if (subMap.containsKey(subTask.getId()) && epicMap.containsKey(subTask.getEpicId())) {
            subMap.put(subTask.getId(), subTask);
            epicMap.get(subTask.getEpicId()).setSubTask(subTask);
        }
    }


}

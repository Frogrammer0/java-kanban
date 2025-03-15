import java.util.ArrayList;
import java.util.HashMap;

public class TaskManager {

    private static int idNumber = 0;
    public static HashMap<Integer, Task> taskMap = new HashMap<>();


    public static int assignId() { //метод для присвоения Ид
        idNumber++;
        return idNumber;
    }

    public static Task getTask(int id) {  //метод для получения обычной задачи
        return taskMap.get(id);
    }

    public static HashMap<Integer, Task> getAllTask() { //метод для получения всех обычных задач
        HashMap<Integer, Task> AllTask = new HashMap<>();
        int j = 1;

        for (int i = 1; i <= idNumber; i++) {
            if (getTask(i) != null && getTask(i).getClass() == Task.class) {

                AllTask.put(j++, getTask(i));
            }
        }
        return AllTask;
    }

    public static HashMap<Integer, Task> getAllEpicTask() { //метод для получения всех эпиков
        HashMap<Integer, Task> AllEpicTask = new HashMap<>();
        int j = 1;

        for (int i = 1; i <= idNumber; i++) {
            if (getTask(i) != null && getTask(i).getClass() == EpicTask.class) {
                AllEpicTask.put(j++, getTask(i));
            }
        }
        return AllEpicTask;
    }

    public static ArrayList<Task> getAllSubTask() { //метод для получения всех подзадачи из всех эпиков
        ArrayList<Task> subTasks = new ArrayList<>();


        for (int i = 1; i <= idNumber; i++) {
            if (getTask(i) != null && getTask(i).getClass() == EpicTask.class) {
                EpicTask epic = (EpicTask) getTask(i);
                for (int j = 0; j < epic.getSubTasks().size(); j++) {
                    subTasks.add(epic.getSubTasks().get(j));
                }
            }
        }
        return subTasks;
    }

    public static HashMap<Integer, Task> getAllTaskAllType() { //получения всех задач всех типов
        HashMap<Integer, Task> AllTask = new HashMap<>();

        for (int i = 1; i <= idNumber; i++) {
            if (getTask(i) != null) {
                AllTask.put(i, getTask(i));
            }
        }
        return AllTask;
    }

    public static ArrayList<Task> getSubTaskFromEpic(int id) { //метод для получения списка подзадач из эпика
        EpicTask e = (EpicTask) taskMap.get(id);

        return e.subTasks;
    }


    public static void removeAll() { //метод для удаления всех задач
        for (int i = 0; i <= idNumber; i++) {
            taskMap.remove(i);
        }
    }

    public static void remove(int i) { //метод для удаления по Ид
        taskMap.remove(i);
    }


    public static void createTask(Task task) { //метод для создания обычной задачи
        taskMap.put(task.getId(), task);
    }


    public static void createEpic(EpicTask epicTask) { //метод для создания эпика
        taskMap.put(epicTask.getId(), epicTask);
    }

    public static void createSubTask(SubTask subTask) { //метод для создания и добавления подзадач в эпик
        EpicTask epic = (EpicTask) taskMap.get(subTask.getEpicId());
        epic.setSubTask(subTask);
        epic.setStatus(epic);

        taskMap.put(epic.getId(), epic);

    }

    public static void updateTask(int id, Task task) { //метод для обновления обычной задачи
        if (taskMap.containsKey(id)) {
            taskMap.put(id, task);
        }
    }

    public static void updateSubTask(int taskId, SubTask task) { //метод для обновления подзадачи
        EpicTask epic = (EpicTask) taskMap.get(task.getEpicId());
        int x = -1;

        for (Task subTask : epic.getSubTasks()) {
            if (subTask.getId() == taskId) {
                x = epic.getSubTasks().indexOf(subTask);
            }
        }

        if (x >= 0) {
            epic.subTasks.remove(x);
            epic.subTasks.add(task);
            epic.setStatus(epic);
            taskMap.put(task.getEpicId(), epic);
        }
    }


}

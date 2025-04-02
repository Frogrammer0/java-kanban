
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import TaskObject.*;
import Manager.*;


import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryTaskManagerTest {
    static InMemoryTaskManager taskManager = new InMemoryTaskManager();

    @AfterEach
    void clearAll() {
        taskManager.removeAllTasksAllType();
    }

    @Test
    void createTask() {
        Task task1 = new Task("tit1", "dis1", Status.NEW);
        Task task2 = new Task("tit2", "dis2", Status.NEW);
        taskManager.createTask(task1);
        taskManager.createTask(task2);

        Task savedTask1 = taskManager.getTask(task1.getId());
        Task savedTask2 = taskManager.getTask(task2.getId());

        assertEquals(task1, savedTask1, "Задачи не совпадают");
        assertEquals(task2, savedTask2, "Задачи не совпадают");

        ArrayList<Task> tasks = taskManager.getAllTask();

        assertNotNull(tasks, "Список задач возвращается пустым");
        assertEquals(2, tasks.size(), "Неверный размер списка");
        assertEquals(task1, tasks.getFirst(), "Задачи не совпадают");
    }

    @Test
    void createEpicTask() {
        EpicTask epic1 = new EpicTask("epic1", "disE1");
        EpicTask epic2 = new EpicTask("epic1", "disE1");
        taskManager.createTask(epic1);
        taskManager.createTask(epic2);

        Task savedTask1 = taskManager.getEpicTask(epic1.getId());
        Task savedTask2 = taskManager.getEpicTask(epic2.getId());

        assertEquals(epic1, savedTask1, "Задачи не совпадают");
        assertEquals(epic2, savedTask2, "Задачи не совпадают");

        ArrayList<EpicTask> tasks = taskManager.getAllEpicTask();

        assertNotNull(tasks, "Список задач возвращается пустым");
        assertEquals(2, tasks.size(), "Неверный размер списка");
        assertEquals(epic1, tasks.getFirst(), "Задачи не совпадают");
    }

    @Test
    void createSubTask() {
        EpicTask epic1 = new EpicTask("epic1", "disE1");
        taskManager.createTask(epic1);
        SubTask sub11 = new SubTask(1, "sub11", "disS11", Status.DONE);
        SubTask sub12 = new SubTask(1, "sub12", "disS12", Status.DONE);
        taskManager.createTask(sub11);
        taskManager.createTask(sub12);

        SubTask savedTask1 = taskManager.getSubTask(sub11.getId());
        SubTask savedTask2 = taskManager.getSubTask(sub12.getId());

        assertEquals(sub11, savedTask1, "Задачи не совпадают");
        assertEquals(sub12, savedTask2, "Задачи не совпадают");

        ArrayList<SubTask> tasks = taskManager.getAllSubTasksByEpicId(1);

        assertNotNull(tasks, "Список задач возвращается пустым");
        assertEquals(2, tasks.size(), "Неверный размер списка");
        assertEquals(sub11, tasks.getFirst(), "Начальная задача и задача из списка не совпадают");

    }

    @Test
    void createAndSearchTaskFromId() {
        Task task = new Task("tit1", "dis1", Status.NEW);
        taskManager.createTask(task);


        EpicTask epic1 = new EpicTask("epic1", "disE1");
        taskManager.createTask(epic1);

        SubTask sub11 = new SubTask(2, "sub11", "disS11", Status.DONE);
        taskManager.createTask(sub11);

        Task savedTask = taskManager.getTask(1);
        EpicTask savedEpic = taskManager.getEpicTask(2);
        SubTask savedSub = taskManager.getSubTask(3);
    }

    @Test
    void getAllSubTaskTest() {
        EpicTask epic1 = new EpicTask("epic1", "disE1");
        taskManager.createTask(epic1);
        SubTask sub11 = new SubTask(1, "sub11", "disS11", Status.DONE);
        SubTask sub12 = new SubTask(1, "sub12", "disS12", Status.DONE);
        taskManager.createTask(sub11);
        taskManager.createTask(sub12);

        ArrayList<SubTask> subs = taskManager.getAllSubTask();

        assertArrayEquals(subs.toArray(new Object[2]), taskManager.getSubMap().values().toArray(new SubTask[2]));

    }

    @Test
    void removeAllSubTest(){
        EpicTask epic1 = new EpicTask("epic1", "disE1");
        taskManager.createTask(epic1);
        SubTask sub11 = new SubTask(1, "sub11", "disS11", Status.DONE);
        SubTask sub12 = new SubTask(1, "sub12", "disS12", Status.DONE);
        taskManager.createTask(sub11);
        taskManager.createTask(sub12);

        assertEquals(2, taskManager.getSubMap().size(), "Список подзадач не наполнен");

        taskManager.removeAllSub();

        assertEquals(0, taskManager.getSubMap().size(), "Список подзадач не пуст");

    }

    @Test
    void removeAllTask(){
        Task task1 = new Task("tit1", "dis1", Status.NEW);
        Task task2 = new Task("tit2", "dis2", Status.NEW);
        taskManager.createTask(task1);
        taskManager.createTask(task2);

        taskManager.removeAllTask();

        assertEquals(0, taskManager.getTaskMap().size(), "Спискок задач не пуст");
    }

    @Test
    void removeAllEpic() {
        EpicTask epic1 = new EpicTask("epic1", "disE1");
        taskManager.createTask(epic1);
        SubTask sub11 = new SubTask(1, "sub11", "disS11", Status.DONE);
        SubTask sub12 = new SubTask(1, "sub12", "disS12", Status.DONE);
        taskManager.createTask(sub11);
        taskManager.createTask(sub12);
        EpicTask epic2 = new EpicTask("epic2", "disE2");
        taskManager.createTask(epic1);

        taskManager.removeAllEpic();

        assertEquals(0, taskManager.getEpicMap().size(), "Список эпиков не пуст");
        assertEquals(0, taskManager.getSubMap().size(), "Список подзадач из удаленных эпиков не пуст");

    }

    @Test
    void removeTaskFromId() {
        Task task = new Task("tit1", "dis1", Status.NEW);
        taskManager.createTask(task);
        EpicTask epic1 = new EpicTask("epic1", "disE1");
        taskManager.createTask(epic1);
        SubTask sub11 = new SubTask(2, "sub11", "disS11", Status.DONE);
        SubTask sub12 = new SubTask(2, "sub12", "disS12", Status.DONE);
        taskManager.createTask(sub11);
        taskManager.createTask(sub12);
        EpicTask epic2 = new EpicTask("epic2", "disE2");
        taskManager.createTask(epic2);


        taskManager.removeTask(1);
        assertEquals(0, taskManager.getTaskMap().size(), "Задача не удалена");

        taskManager.removeEpic(5);
        assertEquals(1, taskManager.getEpicMap().size(), "Пустой эпик не удален");

        taskManager.removeSub(3);
        assertEquals(1, taskManager.getSubMap().size(), "Подзадача отдельно не удалена");

        taskManager.removeEpic(2);
        assertEquals(0, taskManager.getEpicMap().size(), "Эпик с подзадачей не удален");
        assertEquals(0, taskManager.getSubMap().size(), "Подзадача не удалена вместе с эпиком ");

    }




}
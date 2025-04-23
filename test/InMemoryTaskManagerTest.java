
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

    //СОЗДАНИЕ РАЗНЫХ ТИПОВ ЗАДАЧ

    @Test
    void createTask() {
        Task task1 = new Task("tit1", "dis1", Status.NEW);
        Task task2 = new Task("tit2", "dis2", Status.NEW);
        taskManager.createTask(task1);
        taskManager.createTask(task2);

        assertNull(taskManager.getTask(99), "Получена задача по несуществующему id");

        Task savedTask1 = taskManager.getTask(task1.getId());
        Task savedTask2 = taskManager.getTask(task2.getId());

        assertEquals(task1, savedTask1, "Задачи не совпадают");
        assertEquals(task2, savedTask2, "Задачи не совпадают");

        ArrayList<Task> tasks = taskManager.getAllTask();

        assertNotNull(tasks, "Список задач возвращается пустым");
        assertEquals(2, tasks.size(), "Неверный размер списка");
        assertEquals(task1, tasks.getFirst(), "Задачи 1 не совпадают");
        assertEquals(task2, tasks.getLast(), "Задачи 2 не совпадают");
    }

    @Test
    void createEpicTask() {
        EpicTask epic1 = new EpicTask("epic1", "disE1");
        EpicTask epic2 = new EpicTask("epic1", "disE1");
        taskManager.createTask(epic1);
        taskManager.createTask(epic2);

        assertNull(taskManager.getEpicTask(99), "Получена Эпик Задача по несуществующему id");

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

        assertNull(taskManager.getSubTask(99), "Получена ПодЗадача по несуществующему id");

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
    void SetAndChangeEpicStatus() {
        EpicTask epic1 = new EpicTask("epic1", "disE1");
        taskManager.createTask(epic1);

        assertEquals(Status.NEW, epic1.getStatus(), "Статус нового эпика не NEW");

        SubTask sub11 = new SubTask(1, "sub11", "disS11", Status.NEW);
        taskManager.createTask(sub11);
        SubTask sub12 = new SubTask(1, "sub11", "disS11", Status.IN_PROGRESS);
        taskManager.createTask(sub12);
        SubTask sub13 = new SubTask(1, "sub11", "disS11", Status.DONE);
        taskManager.createTask(sub13);

        assertEquals(Status.IN_PROGRESS, epic1.getStatus(), "Статус эпика с разными задачами не IN_PROGRESS");

        sub11.setStatus(Status.NEW);
        sub12.setStatus(Status.NEW);
        sub13.setStatus(Status.NEW);
        taskManager.updateEpicTask(epic1);

        assertEquals(Status.NEW, epic1.getStatus(), "Статус эпика только с новыми задачами не NEW");

        sub11.setStatus(Status.IN_PROGRESS);
        sub12.setStatus(Status.IN_PROGRESS);
        sub13.setStatus(Status.IN_PROGRESS);
        taskManager.updateEpicTask(epic1);

        assertEquals(Status.IN_PROGRESS, epic1.getStatus(), "Статус эпика с текущими задачами не IN_PROGRESS");

        sub11.setStatus(Status.DONE);
        sub12.setStatus(Status.DONE);
        sub13.setStatus(Status.DONE);
        taskManager.updateEpicTask(epic1);

        assertEquals(Status.DONE, epic1.getStatus(), "Статус эпика с выполненными задачами не DONE");

    }


    //ПОЛУЧЕНИЕ РАЗНЫХ ТИПОВ ЗАДАЧ

    @Test
    void createAndGetTaskFromId() {
        Task task = new Task("tit1", "dis1", Status.NEW);
        taskManager.createTask(task);


        EpicTask epic1 = new EpicTask("epic1", "disE1");
        taskManager.createTask(epic1);

        SubTask sub11 = new SubTask(2, "sub11", "disS11", Status.DONE);
        taskManager.createTask(sub11);

        Task savedTask = taskManager.getTask(1);
        EpicTask savedEpic = taskManager.getEpicTask(2);
        SubTask savedSub = taskManager.getSubTask(3);

        assertEquals(task, savedTask, "Обычные задачи не совпадают");
        assertEquals(epic1, savedEpic, "ЕпикЗадачи не совпадают");
        assertEquals(sub11, savedSub, "Подзадачи не совпадают");
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

        assertArrayEquals(subs.toArray(new SubTask[2]), taskManager.getSubMap().values().toArray(new SubTask[2]));

    }


    //УДАЛЕНИЕ РАЗНЫХ ТИПОВ ЗАДАЧ


    @Test
    void removeAllSubTest() {
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
    void removeAllTask() {
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

    @Test
    void stayRemovedSubTaskIdInEpic() {
        EpicTask epic1 = new EpicTask("epic1", "disE1");
        taskManager.createTask(epic1);
        SubTask sub11 = new SubTask(1, "sub11", "disS11", Status.DONE);
        SubTask sub12 = new SubTask(1, "sub12", "disS12", Status.DONE);
        SubTask sub13 = new SubTask(1, "sub13", "disS13", Status.DONE);
        taskManager.createTask(sub11);
        taskManager.createTask(sub12);
        taskManager.createTask(sub13);

        assertEquals(3, epic1.getSubTasksId().size(), "Эпик хранит неверное число подзадач");

        taskManager.removeSub(2);

        for (int id : epic1.getSubTasksId()) {
            assertNotEquals(2, id, "В эпике остался id удаленной задачи");
        }


    }


}
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;



import static org.junit.jupiter.api.Assertions.*;

class InMemoryHistoryManagerTest {
    InMemoryTaskManager taskManager = new InMemoryTaskManager();
    InMemoryHistoryManager history = new InMemoryHistoryManager();

    @AfterEach
    void clearAll() {
        taskManager.removeAllTasksAllType();
    }

    @Test
    void add() {
        Task task = new Task("tit1", "dis1", Status.NEW);
        taskManager.createTask(task);

        history.add(task);

        assertEquals(task, history.getHistory().getFirst(), "Объекты не совпадают");
    }

    @Test
    void getHistory() {
    }

    @Test
    void saveOldVersionAfterAddInHistory() {
        Task oldTask = new Task("tit1", "dis1", Status.NEW);
        taskManager.createTask(oldTask);
        taskManager.getTask(1);

        Task updTask = new Task("tit11", "dis11", Status.IN_PROGRESS);
        updTask.setId(1);

        taskManager.updateTask(updTask);

        Task taskFromHist = history.getHistory().getFirst();

        assertEquals(taskFromHist, oldTask, "В историю сохранена другая версия");

        assertEquals(taskManager.getTask(1), updTask, "В массиве лежит не новая задача ");
    }


}
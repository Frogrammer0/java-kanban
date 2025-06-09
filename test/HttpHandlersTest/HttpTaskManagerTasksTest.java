package HttpHandlersTest;

import HttpHandlers.HttpTaskServer;
import com.google.gson.Gson;
import manager.InMemoryTaskManager;
import manager.TaskManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import taskobject.EpicTask;
import taskobject.Status;
import taskobject.SubTask;
import taskobject.Task;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class HttpTaskManagerTasksTest {

    // создаём экземпляр InMemoryTaskManager
    TaskManager manager = new InMemoryTaskManager();
    // передаём его в качестве аргумента в конструктор HttpHandlers.HttpTaskServer
    HttpTaskServer taskServer = new HttpTaskServer(manager);
    Gson gson = HttpTaskServer.getHandlerGson();

    public HttpTaskManagerTasksTest() throws IOException {
    }

    @BeforeEach
    public void setUp() {
        manager.removeAllTasksAllType();
        taskServer.start();
    }

    @AfterEach
    public void shutDown() {
        taskServer.stop();
    }

    @Test
    public void addTaskTest() throws IOException, InterruptedException {
        // создаём задачу
        Task task = new Task("Test 2", "Testing task 2", LocalDateTime.now().format(Task.form), "200",
                Status.NEW);
        // конвертируем её в JSON
        String taskJson = gson.toJson(task);

        // создаём HTTP-клиент и запрос
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks");
        HttpRequest request = HttpRequest.newBuilder().uri(url).POST(HttpRequest.BodyPublishers.ofString(taskJson)).build();

        // вызываем рест, отвечающий за создание задач
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        // проверяем код ответа
        assertEquals(201, response.statusCode());

        // проверяем, что создалась одна задача с корректным именем
        List<Task> tasksFromManager = manager.getAllTask();

        assertNotNull(tasksFromManager, "Задачи не возвращаются");
        assertEquals(1, tasksFromManager.size(), "Некорректное количество задач");
        assertEquals("Test 2", tasksFromManager.getFirst().getTitle(), "Некорректное имя задачи");
    }

    @Test
    public void addSubTaskAndEpicTaskTest() throws IOException, InterruptedException {
        EpicTask epic = new EpicTask("epic1", "disE1");
        manager.createTask(epic);

        SubTask sub1 = new SubTask(epic.getId(), "sub11", "disS11", Status.NEW);
        SubTask sub2 = new SubTask(epic.getId(), "sub22", "disS22", "13.00 12.10.25", "200", Status.IN_PROGRESS);
        manager.createTask(sub1);
        manager.createTask(sub2);

        String epicTaskJson = gson.toJson(epic);
        String sub1TaskJson = gson.toJson(sub1);
        String sub2TaskJson = gson.toJson(sub2);


        HttpClient client = HttpClient.newHttpClient();
        URI urlEpic = URI.create("http://localhost:8080/epictasks");

        HttpRequest requestEpic = HttpRequest.newBuilder().uri(urlEpic).POST(HttpRequest.BodyPublishers.ofString(sub1TaskJson)).build();
        /*URI urlSub = URI.create("http://localhost:8080/subtasks");
        HttpRequest requestSub1 = HttpRequest.newBuilder().uri(urlSub).POST(HttpRequest.BodyPublishers.ofString(sub2TaskJson)).build();
        HttpRequest requestSub2 = HttpRequest.newBuilder().uri(urlSub).POST(HttpRequest.BodyPublishers.ofString(epicTaskJson)).build();*/

        HttpResponse<String> responseEpic = client.send(requestEpic, HttpResponse.BodyHandlers.ofString());
        //HttpResponse<String> responseSub1 = client.send(requestSub1, HttpResponse.BodyHandlers.ofString());
        //HttpResponse<String> responseSub2 = client.send(requestSub2, HttpResponse.BodyHandlers.ofString());


        assertEquals(201, responseEpic.statusCode(), "Код ответа для эпика не верный" + responseEpic.body());
        //assertEquals(201, responseSub1.statusCode(), "Код ответа для Подзадачи 1 не верный");
        //assertEquals(201, responseSub2.statusCode(), "Код ответа для Подзадачи 2 не верный");


    }
}
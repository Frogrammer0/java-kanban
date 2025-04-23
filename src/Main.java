import manager.*;
import taskobject.*;


public class Main {


    public static void main(String[] args) {
        TaskManager manager = Managers.getDefault();


        //ПОЛЬЗОВАТЕЛЬСКИЙ СЦЕНАРИЙ

        Task task1 = new Task("tit1", "dis1", Status.NEW); //задачи
        Task task2 = new Task("tit2", "dis2", Status.NEW);
        EpicTask epic1 = new EpicTask("epic1", "disE1"); // эпики
        EpicTask epic2 = new EpicTask("epic2", "disE2");
        SubTask sub11 = new SubTask(3, "sub21", "disS21", Status.NEW);
        SubTask sub12 = new SubTask(3, "sub22", "disS22", Status.IN_PROGRESS);
        SubTask sub13 = new SubTask(3, "sub23", "disS23", Status.DONE);

        manager.createTask(task1);
        manager.createTask(task2);
        manager.createTask(epic1);
        manager.createTask(epic2);
        manager.createTask(sub11);
        manager.createTask(sub12);
        manager.createTask(sub13);

        manager.getTask(1);
        manager.getTask(2);
        manager.getEpicTask(3);
        manager.getEpicTask(4);
        manager.getSubTask(5);
        manager.getSubTask(6);
        manager.getSubTask(7);

        System.out.println(manager.getHistory());

        manager.getSubTask(7);
        manager.getSubTask(5);
        manager.getEpicTask(3);
        manager.getTask(1);
        manager.getTask(2);
        manager.getEpicTask(4);
        manager.getSubTask(6);
        manager.getTask(2);
        manager.getTask(2);
        manager.getTask(2);

        System.out.println(manager.getHistory());

        manager.removeTask(2);
        manager.removeEpic(4);
        manager.removeSub(6);

        System.out.println(manager.getHistory());

        manager.removeEpic(3);

        System.out.println(manager.getHistory());


    }


}

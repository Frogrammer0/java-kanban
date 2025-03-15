public class Main {



    public static void main(String[] args) {


        Task task1 = new Task("tit1", "dis1", Status.NEW); //задачи
        Task task2 = new Task("tit2", "dis2", Status.NEW);
        Task task3 = new Task("tit3", "dis3", Status.NEW);
        EpicTask epic1 = new EpicTask("epic1", "disE1", Status.NEW); // эпики
        EpicTask epic2 = new EpicTask("epic2", "disE2", Status.NEW);
        SubTask sub11 = new SubTask(4,"sub11", "disS11", Status.NEW); //п подзадачи для разных
        SubTask sub12 = new SubTask(4,"sub12", "disS12", Status.NEW); // эпиков
        SubTask sub13 = new SubTask(4,"sub13", "disS13", Status.NEW);
        SubTask sub21 = new SubTask(5,"sub21", "disS21", Status.NEW);
        SubTask sub22 = new SubTask(5,"sub22", "disS22", Status.IN_PROGRESS);
        SubTask sub23 = new SubTask(5,"sub23", "disS23", Status.DONE);
        SubTask newSub21 = new SubTask(4,"nsub21", "disS21", Status.DONE); //объекты для обновления
        SubTask newSub22 = new SubTask(4,"nsub22", "disS22", Status.DONE); // Подзадач
        SubTask newSub23 = new SubTask(4,"nsub23", "disS23", Status.DONE);


        TaskManager.createTask(task1);
        TaskManager.createTask(task2);
        TaskManager.createTask(task3);
        TaskManager.createEpic(epic1);
        TaskManager.createEpic(epic2);
        TaskManager.createSubTask(sub11);
        TaskManager.createSubTask(sub12);
        TaskManager.createSubTask(sub13);
        TaskManager.createSubTask(sub21);
        TaskManager.createSubTask(sub22);
        TaskManager.createSubTask(sub23);


        System.out.println(TaskManager.getAllTaskAllType() + "\nitAll");


    }


}

import java.util.ArrayList;

public class EpicTask extends Task {
    ArrayList<Task> subTasks;

    public EpicTask(String title, String description, Status status) {
        super(title, description, status);
        subTasks = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "\nEpicTask{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", id=" + id +
                ", status=" + status +
        "\nsubTasks=" +subTasks +
                '}';
    }

    public ArrayList<Task> getSubTasks() {
        return subTasks;
    }

    public void setSubTask(SubTask subTask) { //метод для добавления подзадачи в спискок эпика
        subTasks.add(subTask);
    }

    public void setStatus(EpicTask epicTask) { //метод для обновления статуса эпика
        int a = epicTask.subTasks.size();
        for (Task sub : epicTask.subTasks) {
            if (sub.status == Status.NEW) {
                a--;
            } else if (sub.status == Status.DONE) {
                a++;
            }
        }
        if (a == 0) {
            epicTask.status = Status.NEW;
        } else if (a == (2*epicTask.subTasks.size())) {
            epicTask.status = Status.DONE;
        } else {epicTask.status = Status.IN_PROGRESS;}

    }


}

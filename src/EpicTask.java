import java.util.HashMap;

public class EpicTask extends Task {
    HashMap<Integer, SubTask> subTasks;

    public EpicTask(String title, String description) {
        super(title, description, Status.NEW);
        subTasks = new HashMap<>();
    }

    @Override
    public String toString() {
        return "\nEpicTask{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", id=" + getId() +
                ", status=" + status +
                "\nsubTasks=" + subTasks +
                '}';
    }

    public HashMap<Integer, SubTask> getSubTasks() {
        return subTasks;
    }

    public void setSubTask(SubTask subTask) {//метод для добавления подзадачи в спискок эпика
        subTasks.put(subTask.getId(), subTask);
    }

    public void removeSub(int id) {
        subTasks.remove(id);
    }

    public void removeAllSubTasks() {
        subTasks.clear();

    }

    public void setStatus() { //метод для обновления статуса эпика
        boolean hasNew = false;
        boolean hasDone = false;

        for (SubTask sub : this.subTasks.values()) {
            if (sub.status == Status.NEW) {
                hasNew = true;
            } else if (sub.status == Status.DONE) {
                hasDone = true;
            }
        }

        if (hasNew && hasDone) {
            this.setStatus(Status.IN_PROGRESS);
        }

        this.status = hasDone ? Status.DONE : Status.NEW;
    }


}

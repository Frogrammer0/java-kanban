package taskobject;

import java.util.ArrayList;
import java.util.List;

public class EpicTask extends Task {
    private List<Integer> subTasksId;

    public EpicTask(String title, String description) {
        super(title, description, Status.NEW);
        subTasksId = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "\nEpicTask{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", id=" + getId() +
                ", status=" + status +
                "\nsubTasks=" + subTasksId +
                '}';
    }

    public List<Integer> getSubTasksId() {
        return subTasksId;
    }

    public void setSubTasksId(Integer id) { //метод для добавления подзадачи в список эпика
        subTasksId.add(id);
    }

    public void removeSubId(Integer id) {
        subTasksId.remove(id);
    }

    public void removeAllSubTasks() {
        subTasksId.clear();

    }


}

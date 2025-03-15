public class SubTask extends Task {

    int epicId;

    public SubTask(int epicId, String title, String description, Status status) {
        super(title, description, status);
        this.epicId = epicId;
    }

    public int getEpicId() {
        return epicId;
    }

    @Override
    public String toString() {
        return "\nSubTask{" +
                "epicId='" + epicId + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", id=" + id +
                ", status=" + status +
                '}';
    }
}

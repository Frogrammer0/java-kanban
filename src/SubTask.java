public class SubTask extends Task {

    private int epicId;

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
                ", id=" + getId() +
                ", status=" + status +
                '}';
    }


}

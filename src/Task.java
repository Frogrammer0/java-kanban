public class Task {
    protected String title;
    protected String description;
    protected int id;
    protected Status status;

    public Task(String title, String description, Status status) {
        this.title = title;
        this.description = description;
        this.id = TaskManager.assignId();
        this.status = status;
    }

    @Override
    public String toString() {
        return "\nTask{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", id=" + id +
                ", status=" + status +
                "}";
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        Task task = (Task) o;
        return id == task.id || title.equals(task.title) && description.equals(task.description) &&
                status.equals(task.status);
    }

    @Override
    public int hashCode() {
        int result = title.hashCode();
        result = 31 * result + description.hashCode();
        result = 31 * result + id;
        return result;
    }



    public int getId() {
        return id;
    }



}

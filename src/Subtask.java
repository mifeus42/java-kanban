public class Subtask extends Task {
    private final int epicTaskOwnerId;

    public Subtask(String name, String description, int id, int epicTaskOwnerId) {
        super(name, description, id);
        this.epicTaskOwnerId = epicTaskOwnerId;
    }

    public Subtask(String name, String description, TaskStatus status, int id, int epicTaskOwnerId) {
        super(name, description, status, id);
        this.epicTaskOwnerId = epicTaskOwnerId;
    }

    public int getEpicTaskOwnerId() {
        return epicTaskOwnerId;
    }

    @Override
    public String toString() {
        return " Subtask{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", status=" + status +
                ", epicTaskOwnerId=" + epicTaskOwnerId +
                "} ";
    }
}

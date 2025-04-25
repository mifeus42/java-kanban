public class Subtask extends Task {
    private final EpicTask epicTaskOwner;

    public Subtask(String name, String description, TaskStatus status, int id, EpicTask epicTaskOwner) {
        super(name, description, status, id);
        this.epicTaskOwner = epicTaskOwner;
        epicTaskOwner.addTask(this);
    }

    public EpicTask getEpicTaskOwner() {
        return epicTaskOwner;
    }

    @Override
    public String toString() {
        return " Subtask{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", status=" + status +
                ", epicTaskOwnerId=" + epicTaskOwner.getId() +
                "} ";
    }
}

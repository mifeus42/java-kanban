public class Subtask extends Task {
    private EpicTask epicTaskOwner;

    public Subtask(String name, String description, TaskStatus status, int id) {
        super(name, description, status, id);
    }

    public EpicTask getEpicTaskOwner() {
        return epicTaskOwner;
    }

    public void setEpicTaskOwner(EpicTask epicTaskOwner) {
        this.epicTaskOwner = epicTaskOwner;
    }
}

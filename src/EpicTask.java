import java.util.ArrayList;

public class EpicTask extends Task {
    private final ArrayList<Subtask> subtasks;

    public EpicTask(String name, String description, TaskStatus status, int id) {
        super(name, description, status, id);
        subtasks = new ArrayList<>();
    }

    public void addTask(Subtask subtask) {
        subtask.setEpicTaskOwner(this);
        subtasks.add(subtask);
    }

    public ArrayList<Subtask> getSubtasks() {
        return subtasks;
    }
}

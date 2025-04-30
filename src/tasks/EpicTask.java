package tasks;

import java.util.ArrayList;

public class EpicTask extends Task {
    private final ArrayList<Integer> subtaskIds;

    public EpicTask(String name, String description, int id) {
        super(name, description, id);
        subtaskIds = new ArrayList<>();
    }

    public EpicTask(String name, String description, TaskStatus status, int id, ArrayList<Integer> subtaskIds) {
        super(name, description, status, id);
        this.subtaskIds = subtaskIds;
    }

    public ArrayList<Integer> getSubtaskIds() {
        return new ArrayList<>(subtaskIds);
    }

    @Override
    public String toString() {
        return "tasks.EpicTask{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", status=" + status +
                ", subtaskIds=" + subtaskIds +
                "} ";
    }
}

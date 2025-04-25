import java.util.HashMap;

public class EpicTask extends Task {
    private final HashMap<Integer, Subtask> subtasks;

    public EpicTask(String name, String description, TaskStatus status, int id) {
        super(name, description, TaskStatus.NEW, id);
        subtasks = new HashMap<>();
    }

    public void addTask(Subtask subtask) {
        subtasks.put(subtask.getId(), subtask);
        calcEpicTaskStatus();
    }

    public void removeTask(int id) {
        subtasks.remove(id);
        calcEpicTaskStatus();
    }

    public HashMap<Integer, Subtask> getSubtasks() {
        return subtasks;
    }

    public void deleteSubtasks() {
        subtasks.clear();
    }

    public void calcEpicTaskStatus() {
        if (subtasks.isEmpty()) {
            return;
        }

        boolean isAllTaskNew = true;
        boolean isAllTasksDone = true;

        for (Subtask subtask : subtasks.values()) {
            if (subtask.getStatus() != TaskStatus.DONE) {
                isAllTasksDone = false;
            }
            if (subtask.getStatus() != TaskStatus.NEW) {
                isAllTaskNew = false;
            }
        }

        if (isAllTaskNew) {
            status = TaskStatus.NEW;
        } else if (isAllTasksDone) {
            status = TaskStatus.DONE;
        } else {
            status = TaskStatus.IN_PROGRESS;
        }

    }

    @Override
    public String toString() {
        return "EpicTask{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", status=" + status +
                ", subtasks=" + subtasks +
                "} ";
    }
}

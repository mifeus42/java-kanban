import java.util.ArrayList;
import java.util.HashMap;

public class TaskManager {
    private final HashMap<Integer, Task> tasks;
    private final HashMap<Integer, EpicTask> epicTasks;
    private final HashMap<Integer, Subtask> subtasks;

    public TaskManager() {
        tasks = new HashMap<>();
        epicTasks = new HashMap<>();
        subtasks = new HashMap<>();
    }

    public ArrayList<Task> getAllTasks() {
        return new ArrayList<>(this.tasks.values());
    }

    public ArrayList<EpicTask> getAllEpicTasks() {
        return new ArrayList<>(this.epicTasks.values());
    }

    public ArrayList<Subtask> getAllSubtasks() {
        return new ArrayList<>(this.subtasks.values());
    }

    public ArrayList<Subtask> getAllSubtasks(EpicTask epicTask) {
        return epicTask.getSubtasks();
    }

    public void addTask(Task task) {
        tasks.put(1, task);
    }

    public void addTask(EpicTask task) {
        epicTasks.put(1, task);
    }

    public void addTask(Subtask task) {
        subtasks.put(1, task);
    }

    public void updateTask(Task task) {
        tasks.put(task.getId(), task);
    }

    public void updateTask(EpicTask task) {
        epicTasks.put(task.getId(), task);
    }

    public void updateTask(Subtask task) {
        subtasks.put(task.getId(), task);
    }

    public Task getTask(int id) {
        return tasks.get(id);
    }

    public Subtask getSubtask(int id) {
        return subtasks.get(id);
    }

    public EpicTask getEpicTask(int id) {
        return epicTasks.get(id);
    }

    public void deleteAllTasks() {
        tasks.clear();
    }

    public void deleteAllEpicTasks() {
        epicTasks.clear();
    }

    public void deleteAllSubtasks() {
        subtasks.clear();
    }
}

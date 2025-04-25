import java.util.ArrayList;
import java.util.HashMap;

public class TaskManager {
    private final HashMap<Integer, Task> tasks;
    private final HashMap<Integer, EpicTask> epicTasks;
    private final HashMap<Integer, Subtask> subtasks;

    private int taskId;

    public TaskManager() {
        tasks = new HashMap<>();
        epicTasks = new HashMap<>();
        subtasks = new HashMap<>();
        taskId = 1;
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
        return new ArrayList<>(epicTask.getSubtasks().values());
    }

    public void addTask(Task task) {
        tasks.put(task.getId(), task);
    }

    public void addTask(EpicTask task) {
        epicTasks.put(task.getId(), task);
    }

    public void addTask(Subtask task) {
        subtasks.put(task.getId(), task);
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

    public Task createTask(String name, String description) {
        return new Task(name, description, TaskStatus.NEW, generateTaskId());
    }

    public EpicTask createEpicTask(String name, String description) {
        return new EpicTask(name, description, generateTaskId());
    }

    public Subtask createSubTask(String name, String description, EpicTask epicTask) {
        return new Subtask(name, description, TaskStatus.NEW, generateTaskId(), epicTask);
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
        subtasks.clear();
    }

    public void deleteAllSubtasks() {
        for (EpicTask epicTask : epicTasks.values()) {
            epicTask.deleteSubtasks();
        }
        subtasks.clear();
    }

    public void deleteTask(int id) {
        tasks.remove(id);
    }

    public void deleteSubtask(int id) {
        EpicTask epicTask = subtasks.get(id).getEpicTaskOwner();
        epicTask.removeTask(id);
        subtasks.remove(id);
    }

    public void deleteEpicTask(int id) {
        HashMap<Integer, Subtask> subtasks = epicTasks.get(id).getSubtasks();
        for (Integer subtaskId : subtasks.keySet()) {
            this.subtasks.remove(subtaskId);
        }
        epicTasks.remove(id);
    }

    private int generateTaskId() {
        return taskId++;
    }
}

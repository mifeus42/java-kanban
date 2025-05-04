package managers;

import tasks.*;

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

    public ArrayList<Subtask> getAllSubtasks(int epicTaskId) {
        EpicTask epicTask = epicTasks.get(epicTaskId);
        ArrayList<Subtask> epicSubtasks = new ArrayList<>();

        if ( epicTask != null ) {
            for (Integer subtaskId : epicTask.getSubtaskIds()) {
                epicSubtasks.add(subtasks.get(subtaskId));
            }
        }

        return epicSubtasks;
    }

    public Task addTask(Task task) {
        Task newTask = new Task(task.getName(), task.getDescription(), generateTaskId());
        tasks.put(newTask.getId(), newTask);
        return newTask;
    }

    public EpicTask addEpicTask(EpicTask epicTask) {
        EpicTask newEpicTask = new EpicTask(epicTask.getName(), epicTask.getDescription(), generateTaskId());
        epicTasks.put(newEpicTask.getId(), newEpicTask);
        return newEpicTask;
    }

    public Subtask addSubtask(Subtask subtask) {
        EpicTask epicTask = epicTasks.get(subtask.getEpicTaskOwnerId());

        if (epicTask == null) {
            return null;
        }

        Subtask newSubtask = new Subtask(subtask.getName(), subtask.getDescription(),
                generateTaskId(), subtask.getEpicTaskOwnerId());

        subtasks.put(newSubtask.getId(), newSubtask);
        addSubtaskToEpic(epicTask.getId(), newSubtask.getId());
        return newSubtask;
    }

    public void updateTask(Task task) {
        if (!tasks.containsKey(task.getId())) {
            return;
        }

        tasks.put(task.getId(), task);
    }

    public void updateEpicTask(EpicTask epicTask) {
        if (!epicTasks.containsKey(epicTask.getId())) {
            return;
        }

        EpicTask updatedEpicTask = new EpicTask(epicTask.getName(), epicTask.getDescription(),
                calcEpicTaskStatus(epicTask), epicTask.getId(), epicTask.getSubtaskIds());

        epicTasks.put(updatedEpicTask.getId(), updatedEpicTask);
    }

    public void updateSubtask(Subtask subtask) {
        if (!subtasks.containsKey(subtask.getId())) {
            return;
        }

        subtasks.put(subtask.getId(), subtask);
        updateStatusEpic(subtask.getEpicTaskOwnerId());
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
        ArrayList<EpicTask> epicTasksValues = new ArrayList<>(epicTasks.values());

        for (EpicTask epicTask : epicTasksValues) {
            epicTask = new EpicTask(epicTask.getName(), epicTask.getDescription(), epicTask.getId());
            epicTasks.put(epicTask.getId(), epicTask);
        }

        subtasks.clear();
    }

    public void deleteTask(int id) {
        if (!tasks.containsKey(id)) {
            return;
        }

        tasks.remove(id);
    }

    public void deleteSubtask(int id) {
        if (!subtasks.containsKey(id)) {
            return;
        }

        int epicTaskId = subtasks.get(id).getEpicTaskOwnerId();
        subtasks.remove(id);
        removeSubtaskFromEpic(epicTaskId, id);
    }

    public void deleteEpicTask(int id) {
        if (!epicTasks.containsKey(id)) {
            return;
        }

        for (Integer subtaskId : epicTasks.get(id).getSubtaskIds()) {
            subtasks.remove(subtaskId);
        }
        epicTasks.remove(id);
    }

    private int generateTaskId() {
        return taskId++;
    }

    private TaskStatus calcEpicTaskStatus(EpicTask epicTask) {
        boolean isAllTaskNew = true;
        boolean isAllTasksDone = true;

        for (Integer subtaskId : epicTask.getSubtaskIds()) {
            Subtask subtask = subtasks.get(subtaskId);

            if (subtask.getStatus() != TaskStatus.DONE) {
                isAllTasksDone = false;
            }
            if (subtask.getStatus() != TaskStatus.NEW) {
                isAllTaskNew = false;
            }
        }

        TaskStatus taskStatus;

        if (epicTask.getSubtaskIds().isEmpty() || isAllTaskNew) {
            taskStatus = TaskStatus.NEW;
        } else if (isAllTasksDone) {
            taskStatus = TaskStatus.DONE;
        } else {
            taskStatus = TaskStatus.IN_PROGRESS;
        }

        return taskStatus;
    }

    private void updateStatusEpic(int epicId) {
        EpicTask epicTask = epicTasks.get(epicId);
        epicTask = new EpicTask(epicTask.getName(), epicTask.getDescription(), calcEpicTaskStatus(epicTask),
                epicTask.getId(), epicTask.getSubtaskIds());

        epicTasks.put(epicTask.getId(), epicTask);
    }

    private void addSubtaskToEpic(int epicId, int subtaskId) {
        EpicTask epicTask = epicTasks.get(epicId);
        ArrayList<Integer> subtaskIds = epicTask.getSubtaskIds();
        subtaskIds.add(subtaskId);

        epicTask = new EpicTask(epicTask.getName(), epicTask.getDescription(), calcEpicTaskStatus(epicTask),
                epicTask.getId(), subtaskIds);

        epicTasks.put(epicTask.getId(), epicTask);
    }

    private void removeSubtaskFromEpic(int epicId, int subtaskId) {
        EpicTask epicTask = epicTasks.get(epicId);
        ArrayList<Integer> subtaskIds = epicTask.getSubtaskIds();
        subtaskIds.remove(subtaskId);

        epicTask = new EpicTask(epicTask.getName(), epicTask.getDescription(), calcEpicTaskStatus(epicTask),
                epicTask.getId(), subtaskIds);

        epicTasks.put(epicTask.getId(), epicTask);
    }
}

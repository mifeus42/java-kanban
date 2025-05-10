package managers;

import tasks.*;

import java.util.ArrayList;
import java.util.HashMap;

public class InMemoryTaskManager implements TaskManager{
    private final HashMap<Integer, Task> tasks;
    private final HashMap<Integer, EpicTask> epicTasks;
    private final HashMap<Integer, Subtask> subtasks;

    private final HistoryManager historyManager;

    private int taskId;

    public InMemoryTaskManager(HistoryManager historyManager) {
        this.historyManager = historyManager;
        tasks = new HashMap<>();
        epicTasks = new HashMap<>();
        subtasks = new HashMap<>();
        taskId = 1;
    }

    @Override
    public ArrayList<Task> getAllTasks() {
        return new ArrayList<>(tasks.values());
    }

    @Override
    public ArrayList<EpicTask> getAllEpicTasks() {
        return new ArrayList<>(epicTasks.values());
    }

    @Override
    public ArrayList<Subtask> getAllSubtasks() {
        return new ArrayList<>(subtasks.values());
    }

    @Override
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

    @Override
    public Task addTask(Task task) {
        Task newTask = new Task(task.getName(), task.getDescription(), generateTaskId());
        tasks.put(newTask.getId(), newTask);
        return newTask;
    }

    @Override
    public EpicTask addEpicTask(EpicTask epicTask) {
        EpicTask newEpicTask = new EpicTask(epicTask.getName(), epicTask.getDescription(), generateTaskId());
        epicTasks.put(newEpicTask.getId(), newEpicTask);
        return newEpicTask;
    }

    @Override
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

    @Override
    public void updateTask(Task task) {
        if (!tasks.containsKey(task.getId())) {
            return;
        }

        tasks.put(task.getId(), task);
    }

    @Override
    public void updateEpicTask(EpicTask epicTask) {
        if (!epicTasks.containsKey(epicTask.getId())) {
            return;
        }

        EpicTask updatedEpicTask = new EpicTask(epicTask.getName(), epicTask.getDescription(),
                calcEpicTaskStatus(epicTask.getSubtaskIds()), epicTask.getId(), epicTask.getSubtaskIds());

        epicTasks.put(updatedEpicTask.getId(), updatedEpicTask);
    }

    @Override
    public void updateSubtask(Subtask subtask) {
        if (!subtasks.containsKey(subtask.getId())) {
            return;
        }

        subtasks.put(subtask.getId(), subtask);
        updateStatusEpic(subtask.getEpicTaskOwnerId());
    }

    @Override
    public Task getTask(int id) {
        Task task = tasks.get(id);

        historyManager.add(task);

        return task;
    }

    @Override
    public Subtask getSubtask(int id) {
        Subtask subtask = subtasks.get(id);

        historyManager.add(subtask);

        return subtask;
    }

    @Override
    public EpicTask getEpicTask(int id) {
        EpicTask epicTask = epicTasks.get(id);

        historyManager.add(epicTask);

        return epicTasks.get(id);
    }

    @Override
    public void deleteAllTasks() {
        tasks.clear();
    }

    @Override
    public void deleteAllEpicTasks() {
        epicTasks.clear();
        subtasks.clear();
    }

    @Override
    public void deleteAllSubtasks() {
        ArrayList<EpicTask> epicTasksValues = new ArrayList<>(epicTasks.values());

        for (EpicTask epicTask : epicTasksValues) {
            epicTask = new EpicTask(epicTask.getName(), epicTask.getDescription(), epicTask.getId());
            epicTasks.put(epicTask.getId(), epicTask);
        }

        subtasks.clear();
    }

    @Override
    public void deleteTask(int id) {
        if (!tasks.containsKey(id)) {
            return;
        }

        tasks.remove(id);
    }

    @Override
    public void deleteSubtask(int id) {
        if (!subtasks.containsKey(id)) {
            return;
        }

        int epicTaskId = subtasks.get(id).getEpicTaskOwnerId();
        subtasks.remove(id);
        removeSubtaskFromEpic(epicTaskId, id);
    }

    @Override
    public void deleteEpicTask(int id) {
        if (!epicTasks.containsKey(id)) {
            return;
        }

        for (Integer subtaskId : epicTasks.get(id).getSubtaskIds()) {
            subtasks.remove(subtaskId);
        }
        epicTasks.remove(id);
    }

    @Override
    public ArrayList<Task> getHistory() {
        return historyManager.getHistory();
    }

    private int generateTaskId() {
        return taskId++;
    }

    private TaskStatus calcEpicTaskStatus(ArrayList<Integer> subtaskIds) {
        boolean isAllTaskNew = true;
        boolean isAllTasksDone = true;

        for (Integer subtaskId : subtaskIds) {
            Subtask subtask = subtasks.get(subtaskId);

            if (subtask.getStatus() != TaskStatus.DONE) {
                isAllTasksDone = false;
            }
            if (subtask.getStatus() != TaskStatus.NEW) {
                isAllTaskNew = false;
            }
        }

        TaskStatus taskStatus;

        if (subtaskIds.isEmpty() || isAllTaskNew) {
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
        epicTask = new EpicTask(epicTask.getName(), epicTask.getDescription(),
                calcEpicTaskStatus(epicTask.getSubtaskIds()), epicTask.getId(), epicTask.getSubtaskIds());

        epicTasks.put(epicTask.getId(), epicTask);
    }

    private void addSubtaskToEpic(int epicId, int subtaskId) {
        EpicTask epicTask = epicTasks.get(epicId);
        ArrayList<Integer> subtaskIds = epicTask.getSubtaskIds();
        subtaskIds.add(subtaskId);

        epicTask = new EpicTask(epicTask.getName(), epicTask.getDescription(), calcEpicTaskStatus(subtaskIds),
                epicTask.getId(), subtaskIds);

        epicTasks.put(epicTask.getId(), epicTask);
    }

    private void removeSubtaskFromEpic(int epicId, int subtaskId) {
        EpicTask epicTask = epicTasks.get(epicId);
        ArrayList<Integer> subtaskIds = epicTask.getSubtaskIds();
        subtaskIds.remove((Integer) subtaskId);

        epicTask = new EpicTask(epicTask.getName(), epicTask.getDescription(), calcEpicTaskStatus(subtaskIds),
                epicTask.getId(), subtaskIds);

        epicTasks.put(epicTask.getId(), epicTask);
    }
}

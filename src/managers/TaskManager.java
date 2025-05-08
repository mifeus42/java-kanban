package managers;

import tasks.EpicTask;
import tasks.Subtask;
import tasks.Task;
import tasks.TaskStatus;

import java.util.ArrayList;

public interface TaskManager {

    ArrayList<Task> getAllTasks();

    ArrayList<EpicTask> getAllEpicTasks();

    ArrayList<Subtask> getAllSubtasks();

    ArrayList<Subtask> getAllSubtasks(int epicTaskId);

    Task addTask(Task task);

    EpicTask addEpicTask(EpicTask epicTask);

    Subtask addSubtask(Subtask subtask);

    void updateTask(Task task);

    void updateEpicTask(EpicTask epicTask);

    void updateSubtask(Subtask subtask);

    Task getTask(int id);

    Subtask getSubtask(int id);

    EpicTask getEpicTask(int id);

    void deleteAllTasks();

    void deleteAllEpicTasks();

    void deleteAllSubtasks();

    void deleteTask(int id);

    void deleteSubtask(int id);

    void deleteEpicTask(int id);

    ArrayList<Task> getHistory();
}

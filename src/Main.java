import managers.TaskManager;
import tasks.*;

public class Main {

    public static void main(String[] args) {
        TaskManager taskManager = new TaskManager();

        Task task1 =  taskManager.addTask(new Task("common task1", "description", 0));
        Task task2 = taskManager.addTask(new Task("common task2", "description", 0));

        EpicTask epicTask1 = taskManager.addEpicTask(new EpicTask("EP1", "description", 0));
        EpicTask epicTask2 = taskManager.addEpicTask(new EpicTask("EP2", "description", 0));

        Subtask subtask11 = taskManager.addSubtask(new Subtask("SUB11", "description", 0,
                epicTask1.getId()));
        Subtask subtask12 = taskManager.addSubtask(new Subtask("SUB12", "description", 0,
                epicTask1.getId()));
        Subtask subtask21 = taskManager.addSubtask(new Subtask("SUB21", "description", 0,
                epicTask2.getId()));

        System.out.println(taskManager.getAllTasks());
        System.out.println(taskManager.getAllEpicTasks());
        System.out.println(taskManager.getAllSubtasks() + "\n");

        task1 = new Task(task1.getName(), task1.getDescription(), TaskStatus.IN_PROGRESS, task1.getId());
        taskManager.updateTask(task1);

        subtask11 = new Subtask(subtask11.getName(), subtask11.getDescription(), TaskStatus.IN_PROGRESS,
                subtask11.getId(), subtask11.getEpicTaskOwnerId());

        subtask12 = new Subtask(subtask12.getName(), subtask12.getDescription(), TaskStatus.DONE,
                subtask12.getId(), subtask12.getEpicTaskOwnerId());

        subtask21 = new Subtask(subtask21.getName(), subtask21.getDescription(), TaskStatus.DONE,
                subtask21.getId(), subtask21.getEpicTaskOwnerId());

        taskManager.updateSubtask(subtask11);
        taskManager.updateSubtask(subtask12);
        taskManager.updateSubtask(subtask21);

        System.out.println(taskManager.getAllTasks());
        System.out.println(taskManager.getAllEpicTasks());
        System.out.println(taskManager.getAllSubtasks() + "\n");

        taskManager.deleteTask(task1.getId());
        taskManager.deleteEpicTask(epicTask1.getId());

        System.out.println(taskManager.getAllTasks());
        System.out.println(taskManager.getAllEpicTasks());
        System.out.println(taskManager.getAllSubtasks());
    }
}

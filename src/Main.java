import managers.InMemoryHistoryManager;
import managers.InMemoryTaskManager;
import managers.Managers;
import managers.TaskManager;
import tasks.*;

public class Main {

    public static void main(String[] args) {
        TaskManager taskManager = Managers.getDefault();

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

        printAllTasks(taskManager);

        taskManager.getTask(task2.getId());
        taskManager.getEpicTask(epicTask1.getId());
        taskManager.getTask(task1.getId());
        taskManager.getEpicTask(epicTask2.getId());

        printAllTasks(taskManager);

        task1 = new Task(task1.getName(), task1.getDescription(), TaskStatus.IN_PROGRESS, task1.getId());
        taskManager.updateTask(task1);

        subtask11 = new Subtask(subtask11.getName(), subtask11.getDescription(), TaskStatus.IN_PROGRESS,
                subtask11.getId(), subtask11.getEpicTaskOwnerId());

        subtask21 = new Subtask(subtask21.getName(), subtask21.getDescription(), TaskStatus.DONE,
                subtask21.getId(), subtask21.getEpicTaskOwnerId());

        taskManager.updateSubtask(subtask11);
        taskManager.updateSubtask(subtask21);

        taskManager.deleteTask(task1.getId());
        taskManager.deleteEpicTask(epicTask1.getId());

        printAllTasks(taskManager);

        taskManager.getSubtask(subtask12.getId());
        taskManager.getSubtask(subtask21.getId());

        printAllTasks(taskManager);
    }

    private static void printAllTasks(TaskManager taskManager) {
        System.out.println();

        System.out.println("Задачи:");
        for (Task task : taskManager.getAllTasks()) {
            System.out.println(task);
        }

        System.out.println();

        System.out.println("Эпики:");
        for (Task epic : taskManager.getAllEpicTasks()) {
            System.out.println(epic);

            for (Task task : taskManager.getAllSubtasks(epic.getId())) {
                System.out.println("--> " + task);
            }
        }

        System.out.println();

        System.out.println("Подзадачи:");
        for (Task subtask : taskManager.getAllSubtasks()) {
            System.out.println(subtask);
        }

        System.out.println();

        System.out.println("История:");
        for (Task task : taskManager.getHistory()) {
            System.out.println(task);
        }

        System.out.println("-".repeat(50) );
    }
}

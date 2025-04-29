public class Main {

    public static void main(String[] args) {
        TaskManager taskManager = new TaskManager();

        Task task1 = new Task("common task1", "description", taskManager.generateTaskId());
        Task task2 = new Task("common task2", "description", taskManager.generateTaskId());
        taskManager.addTask(task1);
        taskManager.addTask(task2);

        EpicTask epicTask1 = new EpicTask("EP1", "description", taskManager.generateTaskId());
        EpicTask epicTask2 = new EpicTask("EP2", "description", taskManager.generateTaskId());
        taskManager.addTask(epicTask1);
        taskManager.addTask(epicTask2);

        Subtask subtask11 = new Subtask("SUB11", "description",
                taskManager.generateTaskId(), epicTask1.getId());
        Subtask subtask12 = new Subtask("SUB12", "description",
                taskManager.generateTaskId(), epicTask1.getId());
        Subtask subtask21 = new Subtask("SUB21", "description",
                taskManager.generateTaskId(), epicTask2.getId());
        taskManager.addTask(subtask11);
        taskManager.addTask(subtask12);
        taskManager.addTask(subtask21);

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

        taskManager.updateTask(subtask11);
        taskManager.updateTask(subtask12);
        taskManager.updateTask(subtask21);

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

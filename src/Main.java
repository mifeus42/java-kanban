public class Main {

    public static void main(String[] args) {
        TaskManager taskManager = new TaskManager();

        Task task1 = taskManager.createTask("common task1", "description");
        Task task2 = taskManager.createTask("common task2", "description");
        taskManager.addTask(task1);
        taskManager.addTask(task2);

        EpicTask epicTask1 = taskManager.createEpicTask("EP1", "description");
        EpicTask epicTask2 = taskManager.createEpicTask("EP2", "description");
        taskManager.addTask(epicTask1);
        taskManager.addTask(epicTask2);

        Subtask subtask11 = taskManager.createSubTask("SUB11", "description", epicTask1);
        Subtask subtask12 = taskManager.createSubTask("SUB12", "description", epicTask1);
        Subtask subtask21 = taskManager.createSubTask("SUB21", "description", epicTask2);
        taskManager.addTask(subtask11);
        taskManager.addTask(subtask12);
        taskManager.addTask(subtask21);

        System.out.println(taskManager.getAllTasks());
        System.out.println(taskManager.getAllEpicTasks());
        System.out.println(taskManager.getAllSubtasks() + "\n");

        task1 = new Task(task1.getName(), task1.getDescription(), TaskStatus.IN_PROGRESS, task1.getId());
        taskManager.updateTask(task1);

        subtask11 = new Subtask(subtask11.getName(), subtask11.getDescription(), TaskStatus.IN_PROGRESS,
                subtask11.getId(), subtask11.getEpicTaskOwner());

        subtask12 = new Subtask(subtask12.getName(), subtask12.getDescription(), TaskStatus.DONE,
                subtask12.getId(), subtask12.getEpicTaskOwner());

        subtask21 = new Subtask(subtask21.getName(), subtask21.getDescription(), TaskStatus.DONE,
                subtask21.getId(), subtask21.getEpicTaskOwner());

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

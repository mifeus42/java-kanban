package managers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasks.EpicTask;
import tasks.Subtask;
import tasks.Task;
import tasks.TaskStatus;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryTaskManagerTest {
    InMemoryTaskManager inMemoryTaskManager;

    @BeforeEach
    public void beforeEach() {
        inMemoryTaskManager = new InMemoryTaskManager(new InMemoryHistoryManager());
    }

    @Test
    public void shouldNoChangeFieldsTaskAfterAddInManager() {
        Task task = new Task("Task", "description", 0);

        Task addedTask = inMemoryTaskManager.addTask(task);

        assertEquals(task.getName(), addedTask.getName(), "Имя задачи изменилось");
        assertEquals(task.getDescription(), addedTask.getDescription(), "Описание задачи изменилось");
    }

    @Test
    public void shouldNoChangeFieldsEpicTaskAfterAddInManager() {
        EpicTask epicTask = new EpicTask("EpicTask", "description", 0);

        EpicTask addedEpicTask = inMemoryTaskManager.addEpicTask(epicTask);

        assertEquals(epicTask.getName(), addedEpicTask.getName(), "Имя эпика изменилось");
        assertEquals(epicTask.getDescription(), addedEpicTask.getDescription(), "Описание эпика изменилось");
    }

    @Test
    public void shouldNoChangeFieldsSubtaskAfterAddInManager() {
        EpicTask epicTask = inMemoryTaskManager.addEpicTask(new EpicTask("EpicTask",
                "description", 0));

        Subtask subtask = new Subtask("Subtask", "description", 0, epicTask.getId());

        Subtask addedSubtask = inMemoryTaskManager.addSubtask(subtask);

        assertEquals(subtask.getName(), addedSubtask.getName(), "Имя подзадачи изменилось");
        assertEquals(subtask.getDescription(), addedSubtask.getDescription(), "Описание подзадачи изменилось");
        assertEquals(subtask.getEpicTaskOwnerId(), addedSubtask.getEpicTaskOwnerId(),
                "Эпик подзадачи изменился");
    }

    @Test
    public void shouldDeleteTaskFromManager() {
        Task addedTask = inMemoryTaskManager.addTask(new Task("Task", "description", 0));

        inMemoryTaskManager.deleteTask(addedTask.getId());

        assertNull(inMemoryTaskManager.getTask(addedTask.getId()), "Задача не удалилась");
    }

    @Test
    public void shouldDeleteEpicTaskFromManager() {
        EpicTask epicTask = inMemoryTaskManager.addEpicTask(new EpicTask("EpicTask",
                "description", 0));

        inMemoryTaskManager.deleteEpicTask(epicTask.getId());

        assertNull(inMemoryTaskManager.getEpicTask(epicTask.getId()), "Эпик не удалился");
    }

    @Test
    public void shouldDeleteSubtaskFromManager() {
        EpicTask epicTask = inMemoryTaskManager.addEpicTask(new EpicTask("EpicTask",
                "description", 0));

        Subtask addedSubtask = inMemoryTaskManager.addSubtask(new Subtask("Subtask",
                "description", 0, epicTask.getId()));

        inMemoryTaskManager.deleteSubtask(addedSubtask.getId());

        assertNull(inMemoryTaskManager.getSubtask(addedSubtask.getId()), "Подзадача не удалилась");
    }

    @Test
    public void shouldDeleteSubtaskFromManagerWhenDeleteEpic() {
        EpicTask epicTask = inMemoryTaskManager.addEpicTask(new EpicTask("EpicTask",
                "description", 0));

        Subtask addedSubtask = inMemoryTaskManager.addSubtask(new Subtask("Subtask",
                "description", 0, epicTask.getId()));

        inMemoryTaskManager.deleteEpicTask(epicTask.getId());

        assertNull(inMemoryTaskManager.getSubtask(addedSubtask.getId()), "Подзадача не удалилась");
    }

    @Test
    public void shouldDeleteAllTasksFromManager() {
        inMemoryTaskManager.addTask(new Task("Task", "description", 0));
        inMemoryTaskManager.addTask(new Task("Task", "description", 0));

        inMemoryTaskManager.deleteAllTasks();

        assertEquals(0, inMemoryTaskManager.getAllTasks().size(), "Не все задачи удалились");
    }

    @Test
    public void shouldDeleteAllEpicTasksFromManager() {
        inMemoryTaskManager.addEpicTask(new EpicTask("EpicTask", "description", 0));
        inMemoryTaskManager.addEpicTask(new EpicTask("EpicTask", "description", 0));

        inMemoryTaskManager.deleteAllEpicTasks();

        assertEquals(0, inMemoryTaskManager.getAllEpicTasks().size(), "Не все эпики удалились");
    }

    @Test
    public void shouldDeleteAllSubtasksFromManager() {
        EpicTask epicTask1 = inMemoryTaskManager.addEpicTask(new EpicTask("EpicTask",
                "description", 0));
        EpicTask epicTask2 = inMemoryTaskManager.addEpicTask(new EpicTask("EpicTask",
                "description", 0));

        inMemoryTaskManager.addSubtask(new Subtask("Subtask", "description", 0, epicTask1.getId()));
        inMemoryTaskManager.addSubtask(new Subtask("Subtask", "description", 0, epicTask1.getId()));
        inMemoryTaskManager.addSubtask(new Subtask("Subtask", "description", 0, epicTask2.getId()));
        inMemoryTaskManager.addSubtask(new Subtask("Subtask", "description", 0, epicTask2.getId()));

        inMemoryTaskManager.deleteAllSubtasks();

        assertEquals(0, inMemoryTaskManager.getAllSubtasks().size(), "Не все подзадачи удалились");
    }

    @Test
    public void shouldUpdateFieldsTaskWhenCalledUpdate() {
        Task addedTask = inMemoryTaskManager.addTask(new Task("Task", "description", 0));

        Task updatedTask = new Task("newTaskName", "newDescription",
                TaskStatus.IN_PROGRESS, addedTask.getId());

        inMemoryTaskManager.updateTask(updatedTask);
        updatedTask = inMemoryTaskManager.getTask(updatedTask.getId());

        assertEquals("newTaskName", updatedTask.getName(), "Имя задачи не поменялось");
        assertEquals("newDescription", updatedTask.getDescription(), "Описание задачи не поменялось");
        assertEquals(TaskStatus.IN_PROGRESS, updatedTask.getStatus(), "Статус задачи не поменялся");
    }

    @Test
    public void shouldUpdateFieldsEpicTaskWhenCalledUpdateExceptStatus() {
        EpicTask epicTask = inMemoryTaskManager.addEpicTask(new EpicTask("Task", "description", 0));

        EpicTask updatedEpicTask = new EpicTask("newTaskName", "newDescription",
                TaskStatus.IN_PROGRESS, epicTask.getId(), epicTask.getSubtaskIds());

        inMemoryTaskManager.updateEpicTask(updatedEpicTask);
        updatedEpicTask = inMemoryTaskManager.getEpicTask(updatedEpicTask.getId());

        assertEquals("newTaskName", updatedEpicTask.getName(), "Имя эпика не поменялось");
        assertEquals("newDescription", updatedEpicTask.getDescription(),
                "Описание эпика не поменялось");
        assertEquals(epicTask.getStatus(), updatedEpicTask.getStatus(), "Статус эпика поменялся");
    }

    @Test
    public void shouldUpdateFieldsSubtaskWhenCalledUpdate() {
        EpicTask epicTask = inMemoryTaskManager.addEpicTask(new EpicTask("EpicTask",
                "description", 0));

        Subtask addedSubtask = inMemoryTaskManager.addSubtask(new Subtask("Subtask",
                "description", 0, epicTask.getId()));

        Subtask updatedSubtask = new Subtask("newTaskName", "newDescription",
                TaskStatus.IN_PROGRESS, addedSubtask.getId(), addedSubtask.getEpicTaskOwnerId());

        inMemoryTaskManager.updateSubtask(updatedSubtask);
        updatedSubtask = inMemoryTaskManager.getSubtask(updatedSubtask.getId());

        assertEquals("newTaskName", updatedSubtask.getName(), "Имя подзадачи не поменялось");
        assertEquals("newDescription", updatedSubtask.getDescription(),
                "Описание подзадачи не поменялось");
        assertEquals(TaskStatus.IN_PROGRESS, updatedSubtask.getStatus(), "Статус подзадачи не поменялся");
    }

    @Test
    public void shouldUpdateEpicStatusFromDoneToInProgressWhenAddNewSubtask() {
        EpicTask epicTask = inMemoryTaskManager.addEpicTask(new EpicTask("EpicTask",
                "description", 0));

        Subtask subtask = inMemoryTaskManager.addSubtask(new Subtask("Subtask",
                "description", 0, epicTask.getId()));

        subtask = new Subtask("newTaskName", "newDescription",
                TaskStatus.DONE, subtask.getId(), subtask.getEpicTaskOwnerId());

        inMemoryTaskManager.updateSubtask(subtask);

        inMemoryTaskManager.addSubtask(new Subtask("Subtask", "description", 0, epicTask.getId()));

        assertEquals(TaskStatus.IN_PROGRESS, inMemoryTaskManager.getEpicTask(epicTask.getId()).getStatus(),
                "Статус эпика не поменялся");
    }

    @Test
    public void shouldUpdateEpicStatusWhenUpdateAnySubtaskStatusToInProgress() {
        EpicTask epicTask = inMemoryTaskManager.addEpicTask(new EpicTask("EpicTask",
                "description", 0));

        inMemoryTaskManager.addSubtask(new Subtask("Subtask",
                "description", 0, epicTask.getId()));

        Subtask subtask = inMemoryTaskManager.addSubtask(new Subtask("Subtask",
                "description", 0, epicTask.getId()));

        subtask = new Subtask("newTaskName", "newDescription",
                TaskStatus.IN_PROGRESS, subtask.getId(), subtask.getEpicTaskOwnerId());

        inMemoryTaskManager.updateSubtask(subtask);

        assertEquals(TaskStatus.IN_PROGRESS, inMemoryTaskManager.getEpicTask(epicTask.getId()).getStatus(),
                "Статус эпика не поменялся");
    }

    @Test
    public void shouldUpdateEpicStatusWhenUpdateAllSubtaskStatusToDone() {
        EpicTask epicTask = inMemoryTaskManager.addEpicTask(new EpicTask("EpicTask",
                "description", 0));

        Subtask subtask1 = inMemoryTaskManager.addSubtask(new Subtask("Subtask",
                "description", 0, epicTask.getId()));

        Subtask subtask2 = inMemoryTaskManager.addSubtask(new Subtask("Subtask",
                "description", 0, epicTask.getId()));

        subtask1 = new Subtask("newTaskName", "newDescription",
                TaskStatus.DONE, subtask1.getId(), subtask1.getEpicTaskOwnerId());

        subtask2 = new Subtask("newTaskName", "newDescription",
                TaskStatus.DONE, subtask2.getId(), subtask2.getEpicTaskOwnerId());

        inMemoryTaskManager.updateSubtask(subtask1);
        inMemoryTaskManager.updateSubtask(subtask2);

        assertEquals(TaskStatus.DONE, inMemoryTaskManager.getEpicTask(epicTask.getId()).getStatus(),
                "Статус эпика не поменялся");
    }

    @Test
    public void shouldUpdateEpicStatusWhenDeleteAllSubtaskInProgress() {
        EpicTask epicTask = inMemoryTaskManager.addEpicTask(new EpicTask("EpicTask",
                "description", 0));

        Subtask subtask1 = inMemoryTaskManager.addSubtask(new Subtask("Subtask",
                "description", 0, epicTask.getId()));

        Subtask subtask2 = inMemoryTaskManager.addSubtask(new Subtask("Subtask",
                "description", 0, epicTask.getId()));

        subtask1 = new Subtask("newTaskName", "newDescription",
                TaskStatus.IN_PROGRESS, subtask1.getId(), subtask1.getEpicTaskOwnerId());

        subtask2 = new Subtask("newTaskName", "newDescription",
                TaskStatus.DONE, subtask2.getId(), subtask2.getEpicTaskOwnerId());

        inMemoryTaskManager.updateSubtask(subtask1);
        inMemoryTaskManager.updateSubtask(subtask2);

        inMemoryTaskManager.deleteSubtask(subtask1.getId());

        assertEquals(TaskStatus.DONE, inMemoryTaskManager.getEpicTask(epicTask.getId()).getStatus(),
                "Статус эпика не поменялся");
    }

    @Test
    public void shouldUpdateEpicStatusToNewWhenDeleteAllSubtask() {
        EpicTask epicTask = inMemoryTaskManager.addEpicTask(new EpicTask("EpicTask",
                "description", 0));

        Subtask subtask1 = inMemoryTaskManager.addSubtask(new Subtask("Subtask",
                "description", 0, epicTask.getId()));

        Subtask subtask2 = inMemoryTaskManager.addSubtask(new Subtask("Subtask",
                "description", 0, epicTask.getId()));

        subtask1 = new Subtask("newTaskName", "newDescription",
                TaskStatus.IN_PROGRESS, subtask1.getId(), subtask1.getEpicTaskOwnerId());

        subtask2 = new Subtask("newTaskName", "newDescription",
                TaskStatus.DONE, subtask2.getId(), subtask2.getEpicTaskOwnerId());

        inMemoryTaskManager.updateSubtask(subtask1);
        inMemoryTaskManager.updateSubtask(subtask2);

        inMemoryTaskManager.deleteAllSubtasks();

        assertEquals(TaskStatus.NEW, inMemoryTaskManager.getEpicTask(epicTask.getId()).getStatus(),
                "Статус эпика не поменялся");
    }
}
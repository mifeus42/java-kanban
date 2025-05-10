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
    Task task;
    EpicTask epicTask;

    @BeforeEach
    public void beforeEach() {
        inMemoryTaskManager = new InMemoryTaskManager(new InMemoryHistoryManager());
        task = new Task("Task", "description", 0);
        epicTask = new EpicTask("EpicTask", "description", 0);
    }

    @Test
    public void shouldNoChangeFieldsTaskAfterAddInManager() {
        Task addedTask = inMemoryTaskManager.addTask(task);

        assertEquals(task.getName(), addedTask.getName(), "Имя задачи изменилось");
        assertEquals(task.getDescription(), addedTask.getDescription(), "Описание задачи изменилось");
    }

    @Test
    public void shouldNoChangeFieldsEpicTaskAfterAddInManager() {
        EpicTask addedEpicTask = inMemoryTaskManager.addEpicTask(epicTask);

        assertEquals(epicTask.getName(), addedEpicTask.getName(), "Имя эпика изменилось");
        assertEquals(epicTask.getDescription(), addedEpicTask.getDescription(), "Описание эпика изменилось");
    }

    @Test
    public void shouldNoChangeFieldsSubtaskAfterAddInManager() {
        EpicTask addedEpicTask = inMemoryTaskManager.addEpicTask(epicTask);

        Subtask subtask = new Subtask("Subtask", "description", 0, addedEpicTask.getId());

        Subtask addedSubtask = inMemoryTaskManager.addSubtask(subtask);

        assertEquals(subtask.getName(), addedSubtask.getName(), "Имя подзадачи изменилось");
        assertEquals(subtask.getDescription(), addedSubtask.getDescription(), "Описание подзадачи изменилось");
        assertEquals(subtask.getEpicTaskOwnerId(), addedSubtask.getEpicTaskOwnerId(),
                "Эпик подзадачи изменился");
    }

    @Test
    public void shouldDeleteTaskFromManager() {
        Task addedTask = inMemoryTaskManager.addTask(task);

        inMemoryTaskManager.deleteTask(addedTask.getId());

        assertNull(inMemoryTaskManager.getTask(addedTask.getId()), "Задача не удалилась");
    }

    @Test
    public void shouldDeleteEpicTaskFromManager() {
        EpicTask addedEpicTask = inMemoryTaskManager.addEpicTask(epicTask);

        inMemoryTaskManager.deleteEpicTask(addedEpicTask.getId());

        assertNull(inMemoryTaskManager.getEpicTask(addedEpicTask.getId()), "Эпик не удалился");
    }

    @Test
    public void shouldDeleteSubtaskFromManager() {
        EpicTask addedEpicTask = inMemoryTaskManager.addEpicTask(epicTask);

        Subtask addedSubtask = inMemoryTaskManager.addSubtask(new Subtask("Subtask",
                "description", 0, addedEpicTask.getId()));

        inMemoryTaskManager.deleteSubtask(addedSubtask.getId());

        assertNull(inMemoryTaskManager.getSubtask(addedSubtask.getId()), "Подзадача не удалилась");
    }

    @Test
    public void shouldDeleteSubtaskFromManagerWhenDeleteEpic() {
        EpicTask addedEpicTask = inMemoryTaskManager.addEpicTask(epicTask);

        Subtask addedSubtask = inMemoryTaskManager.addSubtask(new Subtask("Subtask",
                "description", 0, addedEpicTask.getId()));

        inMemoryTaskManager.deleteEpicTask(addedEpicTask.getId());

        assertNull(inMemoryTaskManager.getSubtask(addedSubtask.getId()), "Подзадача не удалилась");
    }

    @Test
    public void shouldDeleteAllTasksFromManager() {
        inMemoryTaskManager.addTask(task);
        inMemoryTaskManager.addTask(task);

        inMemoryTaskManager.deleteAllTasks();

        assertEquals(0, inMemoryTaskManager.getAllTasks().size(), "Не все задачи удалились");
    }

    @Test
    public void shouldDeleteAllEpicTasksFromManager() {
        inMemoryTaskManager.addEpicTask(epicTask);
        inMemoryTaskManager.addEpicTask(epicTask);

        inMemoryTaskManager.deleteAllEpicTasks();

        assertEquals(0, inMemoryTaskManager.getAllEpicTasks().size(), "Не все эпики удалились");
    }

    @Test
    public void shouldDeleteAllSubtasksFromManager() {
        EpicTask epicTask1 = inMemoryTaskManager.addEpicTask(epicTask);
        EpicTask epicTask2 = inMemoryTaskManager.addEpicTask(epicTask);

        inMemoryTaskManager.addSubtask(new Subtask("Subtask", "description", 0, epicTask1.getId()));
        inMemoryTaskManager.addSubtask(new Subtask("Subtask", "description", 0, epicTask1.getId()));
        inMemoryTaskManager.addSubtask(new Subtask("Subtask", "description", 0, epicTask2.getId()));
        inMemoryTaskManager.addSubtask(new Subtask("Subtask", "description", 0, epicTask2.getId()));

        inMemoryTaskManager.deleteAllSubtasks();

        assertEquals(0, inMemoryTaskManager.getAllSubtasks().size(), "Не все подзадачи удалились");
    }

    @Test
    public void shouldUpdateFieldsTaskWhenCalledUpdate() {
        Task addedTask = inMemoryTaskManager.addTask(task);

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
        EpicTask addedEpicTask = inMemoryTaskManager.addEpicTask(epicTask);

        EpicTask updatedEpicTask = new EpicTask("newTaskName", "newDescription",
                TaskStatus.IN_PROGRESS, addedEpicTask.getId(), addedEpicTask.getSubtaskIds());

        inMemoryTaskManager.updateEpicTask(updatedEpicTask);
        updatedEpicTask = inMemoryTaskManager.getEpicTask(updatedEpicTask.getId());

        assertEquals("newTaskName", updatedEpicTask.getName(), "Имя эпика не поменялось");
        assertEquals("newDescription", updatedEpicTask.getDescription(),
                "Описание эпика не поменялось");
        assertEquals(addedEpicTask.getStatus(), updatedEpicTask.getStatus(), "Статус эпика поменялся");
    }

    @Test
    public void shouldUpdateFieldsSubtaskWhenCalledUpdate() {
        EpicTask addedEpicTask = inMemoryTaskManager.addEpicTask(epicTask);

        Subtask addedSubtask = inMemoryTaskManager.addSubtask(new Subtask("Subtask",
                "description", 0, addedEpicTask.getId()));

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
        EpicTask addedEpicTask = inMemoryTaskManager.addEpicTask(epicTask);

        Subtask subtask = inMemoryTaskManager.addSubtask(new Subtask("Subtask",
                "description", 0, addedEpicTask.getId()));

        subtask = new Subtask("newTaskName", "newDescription",
                TaskStatus.DONE, subtask.getId(), subtask.getEpicTaskOwnerId());

        inMemoryTaskManager.updateSubtask(subtask);

        inMemoryTaskManager.addSubtask(new Subtask("Subtask",
                "description", 0, addedEpicTask.getId()));

        assertEquals(TaskStatus.IN_PROGRESS, inMemoryTaskManager.getEpicTask(addedEpicTask.getId()).getStatus(),
                "Статус эпика не поменялся");
    }

    @Test
    public void shouldUpdateEpicStatusWhenUpdateAnySubtaskStatusToInProgress() {
        EpicTask addedEpicTask = inMemoryTaskManager.addEpicTask(epicTask);

        inMemoryTaskManager.addSubtask(new Subtask("Subtask",
                "description", 0, addedEpicTask.getId()));

        Subtask subtask = inMemoryTaskManager.addSubtask(new Subtask("Subtask",
                "description", 0, addedEpicTask.getId()));

        subtask = new Subtask("newTaskName", "newDescription",
                TaskStatus.IN_PROGRESS, subtask.getId(), subtask.getEpicTaskOwnerId());

        inMemoryTaskManager.updateSubtask(subtask);

        assertEquals(TaskStatus.IN_PROGRESS, inMemoryTaskManager.getEpicTask(addedEpicTask.getId()).getStatus(),
                "Статус эпика не поменялся");
    }

    @Test
    public void shouldUpdateEpicStatusWhenUpdateAllSubtaskStatusToDone() {
        EpicTask addedEpicTask = inMemoryTaskManager.addEpicTask(epicTask);

        Subtask subtask1 = inMemoryTaskManager.addSubtask(new Subtask("Subtask",
                "description", 0, addedEpicTask.getId()));

        Subtask subtask2 = inMemoryTaskManager.addSubtask(new Subtask("Subtask",
                "description", 0, addedEpicTask.getId()));

        subtask1 = new Subtask("newTaskName", "newDescription",
                TaskStatus.DONE, subtask1.getId(), subtask1.getEpicTaskOwnerId());

        subtask2 = new Subtask("newTaskName", "newDescription",
                TaskStatus.DONE, subtask2.getId(), subtask2.getEpicTaskOwnerId());

        inMemoryTaskManager.updateSubtask(subtask1);
        inMemoryTaskManager.updateSubtask(subtask2);

        assertEquals(TaskStatus.DONE, inMemoryTaskManager.getEpicTask(addedEpicTask.getId()).getStatus(),
                "Статус эпика не поменялся");
    }

    @Test
    public void shouldUpdateEpicStatusWhenDeleteAllSubtaskInProgress() {
        EpicTask addedEpicTask = inMemoryTaskManager.addEpicTask(epicTask);

        Subtask subtask1 = inMemoryTaskManager.addSubtask(new Subtask("Subtask",
                "description", 0, addedEpicTask.getId()));

        Subtask subtask2 = inMemoryTaskManager.addSubtask(new Subtask("Subtask",
                "description", 0, addedEpicTask.getId()));

        subtask1 = new Subtask("newTaskName", "newDescription",
                TaskStatus.IN_PROGRESS, subtask1.getId(), subtask1.getEpicTaskOwnerId());

        subtask2 = new Subtask("newTaskName", "newDescription",
                TaskStatus.DONE, subtask2.getId(), subtask2.getEpicTaskOwnerId());

        inMemoryTaskManager.updateSubtask(subtask1);
        inMemoryTaskManager.updateSubtask(subtask2);

        inMemoryTaskManager.deleteSubtask(subtask1.getId());

        assertEquals(TaskStatus.DONE, inMemoryTaskManager.getEpicTask(addedEpicTask.getId()).getStatus(),
                "Статус эпика не поменялся");
    }

    @Test
    public void shouldUpdateEpicStatusToNewWhenDeleteAllSubtask() {
        EpicTask addedEpicTask = inMemoryTaskManager.addEpicTask(epicTask);

        Subtask subtask1 = inMemoryTaskManager.addSubtask(new Subtask("Subtask",
                "description", 0, addedEpicTask.getId()));

        Subtask subtask2 = inMemoryTaskManager.addSubtask(new Subtask("Subtask",
                "description", 0, addedEpicTask.getId()));

        subtask1 = new Subtask("newTaskName", "newDescription",
                TaskStatus.IN_PROGRESS, subtask1.getId(), subtask1.getEpicTaskOwnerId());

        subtask2 = new Subtask("newTaskName", "newDescription",
                TaskStatus.DONE, subtask2.getId(), subtask2.getEpicTaskOwnerId());

        inMemoryTaskManager.updateSubtask(subtask1);
        inMemoryTaskManager.updateSubtask(subtask2);

        inMemoryTaskManager.deleteAllSubtasks();

        assertEquals(TaskStatus.NEW, inMemoryTaskManager.getEpicTask(addedEpicTask.getId()).getStatus(),
                "Статус эпика не поменялся");
    }
}
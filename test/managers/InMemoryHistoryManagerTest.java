package managers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasks.Task;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryHistoryManagerTest {
    InMemoryHistoryManager inMemoryHistoryManager;

    @BeforeEach
    public void beforeEach() {
        inMemoryHistoryManager = new InMemoryHistoryManager();
    }

    @Test
    public void shouldAddTaskInHistoryAtEnd() {
        int expectedLastTaskId = 0;

        Task task = new Task("Task", "", 0);
        inMemoryHistoryManager.add(task);

        Task firstTask = inMemoryHistoryManager.getHistory().getFirst();

        assertEquals(expectedLastTaskId, firstTask.getId(), "Задача не добавилась");
    }

    @Test
    public void shouldAddTaskInHistoryAtEndAfterAdded10Task() {
        int expectedLastTaskId = 10;

        for (int i = 0; i < 11; i++) {
            Task task = new Task("Task", "", i);
            inMemoryHistoryManager.add(task);
        }

        Task lastTask = inMemoryHistoryManager.getHistory().getLast();

        assertEquals(expectedLastTaskId, lastTask.getId(), "Задача не добавилась");
    }

    @Test
    public void shouldRemoveFirstElementAfterAdded10Task() {

        int taskIdToDelete = 0;

        for (int i = 0; i < 11; i++) {
            Task task = new Task("Task", "", i);
            inMemoryHistoryManager.add(task);
        }

        Task firstTask = inMemoryHistoryManager.getHistory().getFirst();

        assertNotEquals(taskIdToDelete, firstTask.getId(), "Задача не удалилась");
    }
}
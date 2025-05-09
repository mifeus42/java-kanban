package tasks;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SubtaskTest {

    @Test
    public void shouldEqualsTasksIfEqualsId() {
        Subtask subtask1 = new Subtask("task1", "", 1, 0);
        Subtask subtask2 = new Subtask("task2", "", 1, 0);

        assertEquals(subtask1, subtask2, "Подзадачи не равны");
    }
}
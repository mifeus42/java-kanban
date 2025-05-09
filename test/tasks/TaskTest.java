package tasks;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest {

    @Test
    public void shouldEqualsTasksIfEqualsId() {
        Task task1 = new Task("task1", "", 1);
        Task task2 = new Task("task2", "", 1);

        assertEquals(task1, task2, "Задача не равны");
    }
}
package tasks;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EpicTaskTest {

    @Test
    public void shouldEqualsEpicTasksIfEqualsId() {
        EpicTask epicTask1 = new EpicTask("task1", "", 1);
        EpicTask epicTask2 = new EpicTask("task2", "", 1);

        assertEquals(epicTask1, epicTask2, "Эпики не равны");
    }
}
package managers;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ManagersTest {

    @Test
    public void shouldReturnNotNullManager() {
        assertNotNull(Managers.getDefault(), "Менеджер не создался");
    }
}
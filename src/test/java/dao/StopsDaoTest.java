package dao;

import config.ConfigManager;
import model.dto.StopDto;
import model.dto.StopKey;
import model.exceptions.RepositoryException;
import model.jdbc.StopsDao;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class StopsDaoTest {

    private final StopDto stop_1;
    private final StopDto stop_2;
    private static final StopKey KEY = new StopKey(1, 8382);
    private StopsDao instance;

    public StopsDaoTest() {
        System.out.println("=== StopsDaoTest Constructor ===");
        stop_1 = new StopDto(KEY, 1);
        StopKey invalidKey = new StopKey(56, 9999);
        stop_2 = new StopDto(invalidKey, 5);

        try {
            ConfigManager.getInstance().load();
            instance = StopsDao.getInstance();
        } catch (RepositoryException | IOException e) {
            org.junit.jupiter.api.Assertions.fail("Erreur de connection à la base de données de test", e);
        }
    }

    @Test
    public void testSelectExist() throws Exception {
        System.out.println("testSelectExist");
        //Arrange
        StopDto expected = stop_1;
        //Action
        StopDto result = instance.select(KEY);
        //Assert
        assertEquals(stop_1, result);
    }

    @Test
    public void testSelectNotExist() throws Exception {
        System.out.println("testSelectNotExist");
        //Arrange
        //Action
        StopDto result = instance.select(stop_2.getKey());
        //Assert
        assertNull(result);
    }

    @Test
    public void testSelectIncorrectParameter() throws Exception {
        System.out.println("testSelectIncorrectParameter");
        //Arrange
        StopKey incorrect = null;
        //Assert
        assertThrows(RepositoryException.class, () -> {
            //Action
            instance.select(incorrect);
        });
    }

}

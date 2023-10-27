package repository;

import static org.junit.jupiter.api.Assertions.*;

import model.dto.StopDto;
import model.dto.StopKey;
import model.exceptions.RepositoryException;
import model.jdbc.StopsDao;
import model.repository.StopRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.Mockito.times;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
public class StopRepositoryTest {

    @Mock
    private StopsDao mock;

    private final StopDto stop_1;

    private final StopDto stop_2;

    private static final StopKey KEY = new StopKey(1, 8382);

    public StopRepositoryTest() {
        System.out.println("=== MetroRepositoryTest Constructor ===");
        stop_1 = new StopDto(KEY, 1);
        StopKey key2 = new StopKey(5, 9999); // invalid key
        stop_2 = new StopDto(key2, 2);

    }

    @BeforeEach
    void init() throws RepositoryException {
        System.out.println("=== BEFORE EACH ===");

        Mockito.lenient().when(mock.select(stop_1.getKey())).thenReturn(stop_1);
        Mockito.lenient().when(mock.select(stop_2.getKey())).thenReturn(null);
        Mockito.lenient().when(mock.select(null)).thenThrow(RepositoryException.class);
    }

    @Test
    public void testGetExist() throws Exception {
        System.out.println("testGetExist");
        //Arrange
        StopDto expected = stop_1;
        StopRepository repository = new StopRepository(mock);
        //Action
        StopDto result = repository.get(KEY);
        //Assert
        assertEquals(expected, result);
        Mockito.verify(mock, times(1)).select(KEY);
    }

    @Test
    public void testGetNotExist() throws Exception {
        System.out.println("testGetNotExist");
        //Arrange
        StopRepository repository = new StopRepository(mock);
        //Action
        StopDto result = repository.get(stop_2.getKey());
        //Assert
        assertNull(result);
        Mockito.verify(mock, times(1)).select(stop_2.getKey());
    }

    @Test
    public void testGetIncorrectParameter() throws Exception {
        System.out.println("testGetIncorrectParameter");
        //Arrange
        StopRepository repository = new StopRepository(mock);
        StopKey incorrect = null;
        //Assert
        assertThrows(RepositoryException.class, () -> {
            //Action
            repository.get(incorrect);
        });
        Mockito.verify(mock, times(1)).select(null);
    }
}

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

/** Krok 8: test wyjątku z nazwą miasta. */
public class CityExceptionTest {

    @Test
    void addCityThrowsWithName() {
        Land land = new Land(List.of(
                new Point(0,0), new Point(10,0), new Point(10,10), new Point(0,10)
        ));

        City seaCity = new City(new Point(20, 20), "Wodny Kamień", 2f);
        RuntimeException ex = assertThrows(RuntimeException.class, () -> land.addCity(seaCity));
        assertEquals("Wodny Kamień", ex.getMessage());
    }
}

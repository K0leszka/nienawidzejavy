import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.api.BeforeAll;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

/** Krok 12: test sparametryzowany zasobów. */
public class CityResourceParamTest {

    static Land land;
    static City inlandCity;
    static City portCity;

    @BeforeAll
    static void setup() {
        land = new Land(List.of(
                new Point(0,0), new Point(20,0), new Point(20,20), new Point(0,20)
        ));
        inlandCity = new City(new Point(5,5), "Srodladowe", 4f);
        portCity = new City(new Point(19,5), "Portowe", 4f); // jeden wierzcholek poza ladem => port
        land.addCity(inlandCity);
        land.addCity(portCity);
    }

    static Stream<Object[]> data() {
        return Stream.of(
                new Object[]{inlandCity, new Resource(new Point(6,5), Resource.Type.Coal), true},   // węgiel w zasięgu
                new Object[]{inlandCity, new Resource(new Point(50,50), Resource.Type.Wood), false}, // drewno poza zasięgiem
                new Object[]{portCity,   new Resource(new Point(21,5), Resource.Type.Fish), true},  // ryby dla portowego
                new Object[]{inlandCity, new Resource(new Point(6,6), Resource.Type.Fish), false}   // ryby dla nieportowego
        );
    }

    @ParameterizedTest
    @MethodSource("data")
    void testResources(City city, Resource res, boolean expectedPresent) {
        city.addResourcesInRange(List.of(res), 5f);
        boolean present = city.resources.contains(res.type());
        assertEquals(expectedPresent, present);
    }
}

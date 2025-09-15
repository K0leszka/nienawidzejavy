import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/** Krok 5, 7, 9, 16: Ląd (Polygon) + miasta. */
public class Land extends Polygon {

    private final List<City> cities = new ArrayList<>();

    public Land(List<Point> points) {
        super(points);
    }

    /** Dodaje miasto tylko jeśli środek jest na lądzie. W przeciwnym razie rzuca RuntimeException z nazwą. */
    public void addCity(City city) {
        if (!inside(city.center())) {
            // brak lądu pod środkiem – odrzucamy
            throw new RuntimeException(city.getName());
        }
        // określ portowość na podstawie wierzchołków
        evaluatePort(city);
        cities.add(city);
    }

    /** Krok 9: Miasto portowe jeśli którykolwiek wierzchołek kwadratu leży poza lądem. */
    private void evaluatePort(City city) {
        boolean anyOutside = city.getPoints().stream().anyMatch(p -> !inside(p));
        city.setPort(anyOutside);
    }

    public List<City> getCities() {
        return List.copyOf(cities);
    }

    @Override
    public String toString() {
        return cities.stream().map(City::toString).collect(Collectors.joining(", "));
    }
}

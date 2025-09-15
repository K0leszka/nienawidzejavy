import java.util.HashSet;
import java.util.List;
import java.util.Set;

/** Krok 6, 9, 11, 15, 16: Miasto jako kwadrat osiowo ustawiony. */
public class City extends Polygon {

    public final Point center;
    private String name; // nazwa może być null podczas parsowania
    private boolean port; // czy miasto portowe (ustawiane przez metodę znającą Land i City)
    /*package*/ final Set<Resource.Type> resources = new HashSet<>(); // dostęp pakietowy

    /** Konstruktor: środek, nazwa, długość boku kwadratu. */
    public City(Point center, String name, float side) {
        super(makeSquarePoints(center, side));
        this.center = center;
        this.name = name;
    }

    private static List<Point> makeSquarePoints(Point c, float side) {
        float half = side / 2f;
        float left = c.x() - half;
        float right = c.x() + half;
        float top = c.y() - half;
        float bottom = c.y() + half;
        // kolejność wierzchołków (np. zgodna z ruchem wskazówek zegara):
        return List.of(
                new Point(left, top),
                new Point(right, top),
                new Point(right, bottom),
                new Point(left, bottom)
        );
    }

    public Point center() { return center; }

    public String getName() { return name; }

    /** Krok 15: publiczny mutator nazwy dla dopasowania etykiet. */
    public void setName(String name) { this.name = name; }

    public boolean isPort() { return port; }

    /*package*/ void setPort(boolean port) { this.port = port; }

    /** Krok 11: dodaje typy zasobów w zasięgu (Fish tylko jeśli port). */
    public void addResourcesInRange(List<Resource> resources, float range) {
        float r2 = range * range;
        for (Resource res : resources) {
            float dx = res.position().x() - center.x();
            float dy = res.position().y() - center.y();
            float dist2 = dx*dx + dy*dy;
            if (dist2 <= r2) {
                if (res.type() == Resource.Type.Fish) {
                    if (port) this.resources.add(Resource.Type.Fish);
                } else {
                    this.resources.add(res.type());
                }
            }
        }
    }

    @Override
    public String toString() {
        return name + (port ? "⚓" : "");
    }
}

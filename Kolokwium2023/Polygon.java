import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** Krok 2 + 3: Wielokąt i metoda inside(point) (ray casting). */
public class Polygon {
    private final List<Point> points;

    public Polygon(List<Point> points) {
        if (points == null || points.size() < 3) {
            throw new IllegalArgumentException("Polygon needs at least 3 points");
        }
        this.points = new ArrayList<>(points); // kopia obronna
    }

    public List<Point> getPoints() {
        return Collections.unmodifiableList(points);
    }

    /** Algorytm ray-casting: półprosta w lewo. */
    public boolean inside(Point point) {
        int counter = 0;
        int n = points.size();

        for (int i = 0; i < n; i++) {
            Point pa = points.get(i);
            Point pb = points.get((i + 1) % n);

            // Uporządkuj końce krawędzi tak, aby pa.y <= pb.y
            if (pa.y() > pb.y()) {
                Point tmp = pa;
                pa = pb;
                pb = tmp;
            }

            // Czy pozioma linia przez point.y przecina krawędź (pa,pb)?
            if (pa.y() < point.y() && point.y() <= pb.y()) {
                float d = pb.x() - pa.x();
                float x;
                if (d == 0f) { // krawędź pionowa
                    x = pa.x();
                } else {
                    float a = (pb.y() - pa.y()) / d; // y = a*x + b
                    float b = pa.y() - a * pa.x();
                    x = (point.y() - b) / a;
                }
                if (x < point.x()) {
                    counter++;
                }
            }
        }
        return (counter % 2) == 1;
    }
}

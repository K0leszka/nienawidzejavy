import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

/** Krok 4: trzy testy inside. */
public class PolygonInsideTest {

    @Test
    void pointInside() {
        Polygon p = new Polygon(List.of(
                new Point(0,0), new Point(4,0), new Point(4,3), new Point(0,3)
        ));
        assertTrue(p.inside(new Point(2,1)));
    }

    @Test
    void pointBelow() {
        Polygon p = new Polygon(List.of(
                new Point(1,1), new Point(3,1), new Point(3,3), new Point(1,3)
        ));
        assertFalse(p.inside(new Point(2,0))); // pod wielokątem (mniejsze y)
    }

    @Test
    void pointRight() {
        Polygon p = new Polygon(List.of(
                new Point(1,1), new Point(3,1), new Point(3,3), new Point(1,3)
        ));
        assertFalse(p.inside(new Point(5,2))); // na prawo
    }
}

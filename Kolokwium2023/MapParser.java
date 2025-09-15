import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/** Krok 13–15: Parser SVG (DOM). */
public class MapParser {

    public static class Label {
        public final Point position;
        public final String text;
        public Label(Point p, String t) { this.position = p; this.text = t; }
    }

    private final List<Label> labels = new ArrayList<>();
    private final List<Land> lands = new ArrayList<>();
    private final List<City> cities = new ArrayList<>();

    public List<Land> getLands() { return lands; }
    public List<City> getCities() { return cities; }
    public List<Label> getLabels() { return labels; }

    /** Parsuje zasób /map.svg z classpath. */
    public void parse(String resourcePath) {
        try (InputStream in = MapParser.class.getResourceAsStream(resourcePath)) {
            if (in == null) throw new IllegalArgumentException("Nie znaleziono zasobu: " + resourcePath);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            dbf.setNamespaceAware(false);
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(in);
            doc.getDocumentElement().normalize();

            NodeList polygons = doc.getElementsByTagName("polygon");
            for (int i=0; i<polygons.getLength(); i++) {
                Element el = (Element) polygons.item(i);
                if (!isGreen(el)) continue;
                String pointsAttr = el.getAttribute("points");
                List<Point> pts = parsePointsList(pointsAttr);
                if (pts.size() >= 3) {
                    lands.add(new Land(pts));
                }
            }

            NodeList rects = doc.getElementsByTagName("rect");
            for (int i=0; i<rects.getLength(); i++) {
                Element el = (Element) rects.item(i);
                if (!isRed(el)) continue;
                float x = parseFloat(el.getAttribute("x"));
                float y = parseFloat(el.getAttribute("y"));
                float w = parseFloat(el.getAttribute("width"));
                float h = parseFloat(el.getAttribute("height"));
                float cx = x + w/2f;
                float cy = y + h/2f;
                float side = Math.max(w, h); // powinien być kwadrat; bierzemy w razie czego większy
                cities.add(new City(new Point(cx, cy), null, side));
            }

            NodeList texts = doc.getElementsByTagName("text");
            for (int i=0; i<texts.getLength(); i++) {
                Element el = (Element) texts.item(i);
                float x = parseFloat(el.getAttribute("x"));
                float y = parseFloat(el.getAttribute("y"));
                String t = el.getTextContent().trim();
                if (!t.isEmpty()) labels.add(new Label(new Point(x, y), t));
            }
        } catch (Exception e) {
            throw new RuntimeException("Błąd parsowania SVG: " + e.getMessage(), e);
        }
    }

    /** Krok 14: przypisuje miasta do lądów wg położenia ich środka. */
    public void addCitiesToLands() {
        for (City c : cities) {
            for (Land l : lands) {
                if (l.inside(c.center())) {
                    // addCity ustawi też portowość
                    l.addCity(c);
                    break;
                }
            }
        }
    }

    /** Krok 15: dopasowanie najbliższego Label do każdego miasta. */
    public void matchLabelsToTowns() {
        for (City c : cities) {
            Label best = labels.stream()
                    .min(Comparator.comparingDouble(lb -> dist2(lb.position, c.center())))
                    .orElse(null);
            if (best != null) {
                c.setName(best.text);
            }
        }
    }

    private static double dist2(Point a, Point b) {
        double dx = a.x() - b.x();
        double dy = a.y() - b.y();
        return dx*dx + dy*dy;
    }

    private static boolean isGreen(Element el) {
        String style = el.getAttribute("style"); // np. "fill:#00ff00"
        String fill = el.getAttribute("fill");
        return (style != null && style.toLowerCase().contains("green")) ||
               (style != null && style.toLowerCase().contains("#00ff00")) ||
               (fill != null && fill.equalsIgnoreCase("green")) ||
               (fill != null && fill.equalsIgnoreCase("#00ff00"));
    }

    private static boolean isRed(Element el) {
        String style = el.getAttribute("style");
        String fill = el.getAttribute("fill");
        return (style != null && style.toLowerCase().contains("red")) ||
               (style != null && style.toLowerCase().contains("#ff0000")) ||
               (fill != null && fill.equalsIgnoreCase("red")) ||
               (fill != null && fill.equalsIgnoreCase("#ff0000"));
    }

    private static List<Point> parsePointsList(String pointsAttr) {
        List<Point> list = new ArrayList<>();
        for (String pair : pointsAttr.trim().split("\s+")) {
            String[] xy = pair.split(",");
            if (xy.length == 2) {
                list.add(new Point(parseFloat(xy[0]), parseFloat(xy[1])));
            }
        }
        return list;
    }

    private static float parseFloat(String s) {
        if (s == null || s.isEmpty()) return 0f;
        return Float.parseFloat(s);
    }
}

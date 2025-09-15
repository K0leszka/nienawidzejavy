/** Krok 10: Zasoby. */
public class Resource {

    public enum Type { Coal, Wood, Fish }

    public final Point position;
    public final Type type;

    public Resource(Point position, Type type) {
        this.position = position;
        this.type = type;
    }

    public Point position() { return position; }
    public Type type() { return type; }
}

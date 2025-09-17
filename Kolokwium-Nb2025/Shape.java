// [DRAW-KROK-01] Shape – abstrakcyjna figura: współrzędne + kolor obrysu.
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public abstract class Shape {
    protected double x1, y1, x2, y2;
    protected Color strokeColor;

    public Shape(double x1, double y1, double x2, double y2, Color strokeColor) {
        this.x1 = x1; this.y1 = y1;
        this.x2 = x2; this.y2 = y2;
        this.strokeColor = strokeColor;
    }

    public abstract void draw(GraphicsContext gc);
}

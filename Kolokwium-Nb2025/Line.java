// [DRAW-KROK-03] Line – linia, rysowana strokeLine.
import javafx.scene.canvas.GraphicsContext;

public class Line extends Shape {
    public Line(double x1,double y1,double x2,double y2,javafx.scene.paint.Color stroke){
        super(x1,y1,x2,y2,stroke);
    }

    @Override
    public void draw(GraphicsContext gc){
        gc.setStroke(strokeColor);
        gc.strokeLine(x1,y1,x2,y2);
    }
}

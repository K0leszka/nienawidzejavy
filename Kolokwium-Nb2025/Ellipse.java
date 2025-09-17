// [DRAW-KROK-05] Ellipse – elipsa z wypełnieniem.
import javafx.scene.canvas.GraphicsContext;

public class Ellipse extends FilledShape {
    public Ellipse(double x1,double y1,double x2,double y2,javafx.scene.paint.Color stroke, javafx.scene.paint.Color fill){
        super(x1,y1,x2,y2,stroke,fill);
    }
    @Override
    public void draw(GraphicsContext gc){
        double w = Math.abs(x2-x1);
        double h = Math.abs(y2-y1);
        double xx = Math.min(x1,x2);
        double yy = Math.min(y1,y2);
        gc.setStroke(strokeColor);
        gc.setFill(fillColor);
        gc.strokeOval(xx,yy,w,h);
        gc.fillOval(xx,yy,w,h);
    }
}

// [DRAW-KROK-02] FilledShape – klasa pośrednia: ma kolor wypełnienia.
import javafx.scene.paint.Color;

public abstract class FilledShape extends Shape {
    protected Color fillColor;
    public FilledShape(double x1,double y1,double x2,double y2,Color stroke, Color fill){
        super(x1,y1,x2,y2,stroke);
        this.fillColor = fill;
    }
}

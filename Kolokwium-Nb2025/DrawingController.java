// [DRAW-KROK-06..07] DrawingController – logika GUI (FXML).
// Obsługa myszki: tworzenie figur. Obsługa przycisków: new/open/save.

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.scene.paint.Color;

import java.io.*;
import java.util.*;

public class DrawingController {
    @FXML private Canvas canvas;
    @FXML private RadioButton lineButton, rectButton, ellipseButton;
    @FXML private ColorPicker strokeColorPicker, fillColorPicker;

    private final List<Shape> shapes = new ArrayList<>();
    private double startX, startY;

    @FXML
    public void initialize(){
        redraw();
    }

    @FXML
    private void handleMousePressed(MouseEvent e){
        startX = e.getX();
        startY = e.getY();
    }

    @FXML
    private void handleMouseReleased(MouseEvent e){
        double endX = e.getX();
        double endY = e.getY();
        Color stroke = strokeColorPicker.getValue();
        Color fill = fillColorPicker.getValue();
        Shape s;
        if(lineButton.isSelected()){
            s = new Line(startX,startY,endX,endY,stroke);
        } else if(rectButton.isSelected()){
            s = new Rectangle(startX,startY,endX,endY,stroke,fill);
        } else {
            s = new Ellipse(startX,startY,endX,endY,stroke,fill);
        }
        shapes.add(s);
        redraw();
    }

    private void redraw(){
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0,0,canvas.getWidth(),canvas.getHeight());
        for(Shape s: shapes) s.draw(gc);
    }

    // [DRAW-KROK-07] Obsługa przycisków
    @FXML
    private void handleNew(){
        shapes.clear();
        redraw();
    }

    @FXML
    private void handleSave(){
        FileChooser fc = new FileChooser();
        File f = fc.showSaveDialog(canvas.getScene().getWindow());
        if(f==null) return;
        try(PrintWriter pw = new PrintWriter(f)){
            for(Shape s: shapes){
                if(s instanceof Line l){
                    pw.printf("LINE;%f;%f;%f;%f;%s;%s%n",l.x1,l.y1,l.x2,l.y2,
                            colorToHex(l.strokeColor),"-");
                } else if(s instanceof Rectangle r){
                    pw.printf("RECT;%f;%f;%f;%f;%s;%s%n",r.x1,r.y1,r.x2,r.y2,
                            colorToHex(r.strokeColor),colorToHex(r.fillColor));
                } else if(s instanceof Ellipse el){
                    pw.printf("ELLIPSE;%f;%f;%f;%f;%s;%s%n",el.x1,el.y1,el.x2,el.y2,
                            colorToHex(el.strokeColor),colorToHex(el.fillColor));
                }
            }
        } catch(Exception ex){ ex.printStackTrace(); }
    }

    @FXML
    private void handleOpen(){
        FileChooser fc = new FileChooser();
        File f = fc.showOpenDialog(canvas.getScene().getWindow());
        if(f==null) return;
        shapes.clear();
        try(BufferedReader br = new BufferedReader(new FileReader(f))){
            String line;
            while((line=br.readLine())!=null){
                String[] parts = line.split(";");
                String type = parts[0];
                double x1=Double.parseDouble(parts[1]);
                double y1=Double.parseDouble(parts[2]);
                double x2=Double.parseDouble(parts[3]);
                double y2=Double.parseDouble(parts[4]);
                Color stroke = Color.web(parts[5]);
                Color fill = "-".equals(parts[6])? Color.TRANSPARENT : Color.web(parts[6]);
                switch(type){
                    case "LINE" -> shapes.add(new Line(x1,y1,x2,y2,stroke));
                    case "RECT" -> shapes.add(new Rectangle(x1,y1,x2,y2,stroke,fill));
                    case "ELLIPSE" -> shapes.add(new Ellipse(x1,y1,x2,y2,stroke,fill));
                }
            }
        } catch(Exception ex){ ex.printStackTrace(); }
        redraw();
    }

    private String colorToHex(Color c){
        return String.format("#%02X%02X%02X",
                (int)(c.getRed()*255),
                (int)(c.getGreen()*255),
                (int)(c.getBlue()*255));
    }
}

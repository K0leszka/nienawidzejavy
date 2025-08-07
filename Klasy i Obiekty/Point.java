public class Point {
    public float x;
    public float y;
    public float dx;
    public float dy;

    public String toString(){
        System.out.println("Coordinates x and y:" + x + " " + y );
        return "";
    }

    public String toSvg(){
        return "<svg height= " + "\"" + x + "\""    + " width= " + "\"" + y + "\"" + " " + "xmlns=" + "\"http://www.w3.org/2000/svg\"" + ">" + " " +
                "<circle" + " " + "r=" + "\"45\"" + " " + "cx=" + "\"50\"" + " " + "cy=" + "\"50\"" +  " " + "fill=" + "\"red\"" + " " + "/>" + " " + "</svg>";
    }
    public void translate(float dx, float dy){
        x += dx;
        y += dy;
    }
    public Point translated(float dx, float dy){
        Point newPoint = new Point();
        newPoint.x = this.x + dx;
        newPoint.y = this.x + dy;
        return newPoint;
    }


}

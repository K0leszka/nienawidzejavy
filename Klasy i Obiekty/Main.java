import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
    Point p = new Point();
    p.x = 200;
    p.y = 200;
    Point p3 = new Point();
    p3.x = 510;
    p3.y = 225;
    System.out.println("x = " + p.x);
    System.out.println("y = " + p.y);
    System.out.println(p.toString());
    p.translate(20,40);
    System.out.println("Po przesunięciu:");
    System.out.println(p.toString());
    System.out.println("Drugi punkt:");
    Point p2 = p.translated(150,100);
    System.out.println(p2.toString());
    Segment s = new Segment(p,p2);
    System.out.println(s.length(p2,p));
    System.out.println(p.toSvg());
    Segment s1 = new Segment(p,p2);
    Segment s2= new Segment(p2,p3);
    Segment[] segments = {s1,s2};
    Segment longest = Segment.longestSegment(segments);
    System.out.println("Najdluższy segment: " + longest.length(longest.p1, longest.p2));
    }


}
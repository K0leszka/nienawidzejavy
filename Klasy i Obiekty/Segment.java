public class Segment {
    public Point p1;
    public Point p2;

    public Segment(Point p, Point p2) {
        this.p1 = p;
        this.p2 = p2;
    }


    public double length(Point p1, Point p2) {
      return Math.sqrt(Math.pow((p2.x - p1.x),2) + Math.pow((p2.y - p1.y),2));
   }
    public static Segment longestSegment(Segment[] segments){
        Segment longest = segments[0];
        double maxLength = longest.length(longest.p1,longest.p2);
        for (int i = 1; i < segments.length; i++) {
            double length = segments[i].length(segments[i].p1, segments[i].p2);
            if(length > maxLength){
                maxLength = length;
                longest = segments[i];
            }
        }
        return longest;
    }
}

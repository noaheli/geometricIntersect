public class Line implements Comparable{
    private Point left, right;
    /**
     * @method Line
     * @description Default constructor. Line with endpoints
     *  (0,0) and (0, 1)
     */
    public Line() {
        left = new Point(0, 0);
        right = new Point(1, 1);
    }
    /**
     * @method Line
     * @param p1x
     * @param p1y
     * @param p2x
     * @param p2y
     * @description Overloaded constructor. Determines
     *  which point would be left endpoint based on passed
     *  coordinate parameters and assigns them accordingly.
     */
    public Line(int p1x, int p1y, int p2x, int p2y) {
        if(p1x < p2x) {
            left = new Point(p1x, p1y, true);
            right = new Point(p2x, p2y, false);
        }
        else if(p1x == p2x) {
            if(p1y < p2y) {
                left = new Point(p1x, p1y, true);
                right = new Point(p2x, p2y, false);
            }
            else {
                left = new Point(p2x, p2y, true);
                right = new Point(p1x, p1y, false);
            }
        }
        else {
            left = new Point(p2x, p2y, true);
            right = new Point(p1x, p1y, false);
        }
        left.setParent(this);
        right.setParent(this);
    }
    /**
     * @method intersects
     * @param that
     * @return Boolean value as to whether or not the line intersects
     */
    public boolean intersects(Line that) {
        return segmentsIntersect(this.getLeft(), this.getRight(),
                                 that.getLeft(), that.getRight());
    }
    /**
     * @method segmentsIntersect
     * @param p1
     * @param p2
     * @param p3
     * @param p4
     * @return boolean value as to whether the segments intersect
     * @description Utilizes directions of cross product lines to determine
     *  whether the lines intersect, whether it be by passing each other or
     *  with an endpoint on the other segment.
     */
    private boolean segmentsIntersect(Point p1, Point p2, Point p3, Point p4) {
        int d1 = direction(p3, p4, p1);
        int d2 = direction(p3, p4, p2);
        int d3 = direction(p1, p2, p3);
        int d4 = direction(p1, p2, p4);
        if ((((d1 > 0) && (d2 < 0)) || ((d1 < 0) && (d2 > 0)))
         && (((d3 > 0) && (d4 < 0)) || ((d3 < 0) && (d4 > 0)))) {
             return true;
         }
         else if ((d1 == 0) && onSegment(p3, p4, p1)) return true;
         else if ((d2 == 0) && onSegment(p3, p4, p2)) return true;
         else if ((d3 == 0) && onSegment(p1, p2, p3)) return true;
         else if ((d4 == 0) && onSegment(p1, p2, p4)) return true;
         else return false; 
    }
    /**
     * @method direction
     * @param pi
     * @param pj
     * @param pk
     * @return 
     */
    private int direction(Point pi, Point pj, Point pk) {
        return (((pk.getX() - pi.getX()) * (pj.getY() - pi.getY())) 
              - ((pj.getX() - pi.getX()) * (pk.getY() - pi.getY())));
    }
    /**
     * @method onSegment
     * @param pi
     * @param pj
     * @param pk
     * @return Boolean value to indicate whether one point lay on the line
     *  created by the other two.
     */
    public boolean onSegment(Point pi, Point pj, Point pk) {
        int xmin = Math.min(pi.getX(), pj.getX());
        int xmax = Math.max(pi.getX(), pj.getX());
        int ymin = Math.min(pi.getY(), pj.getY());
        int ymax = Math.max(pi.getY(), pj.getY());
        if ((((xmin <= pk.getX()) && (pk.getX() <= xmax)))
         && (((ymin <= pk.getY()) && (pk.getY() <= ymax))))
            return true;
        return false;
    }
    /**
     * @method crossProd
     * @param that
     * @return A sum of the varied cross products based on the points of the lines
     *  passed. This method can be used to determine whether or not one line is
     *  above the other.
     */
    public int crossProd(Line that) {
        Point p1 = this.getLeft(), p2 = this.getRight(),
              p3 = that.getLeft(), p4 = that.getRight();
        int d1 = direction(p3, p4, p1);
        int d2 = direction(p3, p4, p2);
        int d3 = direction(p1, p2, p3);
        int d4 = direction(p1, p2, p4);
        int sum =  d3 + d4 - d1 - d2;
        return sum;
    }
    /**
     * @method compareTo
     * @param Object Other line variable to be compared to
     * @return int value to indicate whether this is greater than passed line
     *  > 0 if so, < 0 if less than. Equal if same line.
     */
    public int compareTo(Object that) {
        if(!(that instanceof Line)) {
            System.err.println("Wrong type passed in Line comparison");
            return Integer.MIN_VALUE;
        }
        //System.out.println(this.toString() + " | " + ((Line)that).toString() + " | " + this.crossProd((Line)that));
        return this.crossProd((Line)that);
    }
    /**
     * Getters for left and right endpoints
     */
    public Point getLeft() { return this.left; }
    public Point getRight() { return this.right; }
    /**
     * @method toString
     * @return String format of the line, given as '(x1, y1)-(x2, y2)'
     */
    public String toString() { 
        return "(" + left.getX() + ", " + left.getY() + ")-(" + right.getX() + ", " + right.getY() + ")";
    }
}

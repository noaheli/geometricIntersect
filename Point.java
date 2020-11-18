public class Point implements Comparable {
    private int x, y;
    private Line parent = null;
    boolean left;
    /**
    * @method point
    * @description Default constructor, sets x and y to 0.
    */
    public Point() {
        x = 0;
        y = 0;
    }
    /**
    * @method point
    * @description Overloaded constructor for the point object.
    *   sets the x and y values based on passed values.
    */
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }
    /**
     * @method Point
     * @param x
     * @param y
     * @param left
     * @description Overloaded constructor for the point object.
     *   sets the x and y values based on passed parameters. Sets
     *   the left boolean value to the passed parameter, indicating
     *   whether this is a left endpoint.
     */
    public Point(int x, int y, boolean left) {
        this.x = x;
        this.y = y;
        this.left = left;
    }
    /**
     * @method
     * @param Object that
     * @return An integer value representing whether the current point is greater
     *  than or less than the passed point.
     * @description The method first compares x values, marking the one with a higher
     *  x value as being lesser. If they are equal, it breaks ties by 
     */
    public int compareTo(Object that) {
        try{
            if(!(that instanceof Point)) throw new Exception("Wrong type comparison");
            else {
                Point t = (Point)that;
                if(this.x > t.getX()) return -1;
                else if(this.x == t.getX()) {
                    return (this.left && t.getLeft()) ? ( t.getY() - this.y) : ((this.left) ? 1 : -1);
                }
                return 1;
            }
        } catch(Exception e) { System.err.println(e.getMessage()); }
        return -1;
    }
    /**
     * @method toString
     * @return String
     * @description Returns a string of point formatted as '(X, Y, left)'.
     */
    public String toString() { 
        return "(" + x + ", " + y + ", " + left + ")";
    }
    /**
    * @description: Assorted getters and setters for
    * the x and y values.
    */
    public int getX() { return this.x; }
    public int getY() { return this.y; }
    public void setX(int x) { this.x = x; }
    public void setY(int y) { this.y = y; }
    public void setLeft(boolean left) { this.left = left; }
    public boolean getLeft() { return this.left; }
    public void setParent(Line parent) { this.parent = parent; }
    public Line getParent() { return this.parent; }
}
public class Line {
    private class point {
        private int x, y;
        /**
        * @method point
        * @description Default constructor, sets x and y to 0.
        */
        public point() {
            x = 0;
            y = 0;
        }
        /**
        * @method point
        * @description Overloaded constructor for the point object.
        *   sets the x and y values based on passed values.
        */
        public point(int x, int y) {
            this.x = x;
            this.y = y;
        }
        /**
        * @description: Assorted getters and setters for
        * the x and y values.
        */
        public int getX() { return this.x; }
        public int getY() { return this.y; }
        public void setX(int x) { this.x = x; }
        public void setY(int y) { this.y = y; }
    }
}

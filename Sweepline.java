import java.util.TreeMap;
public class Sweepline {
    public Sweepline() {}
    /**
     * @method anySegmentsIntersectHeapSort
     * @param S
     * @return Whether or not any segments were found to intersect.
     * @description This method sorts each point given from the Line
     *  array parameter S using Heap sort with the point comparator. The
     *  method then determines whether any segments intersect, utilizing
     *  a self-balancing BST to maintain the sweeping line status. Null
     *  if none.
     */
    public boolean anySegmentsIntersectHeapSort(Line[] S) {
        TreeMap<Line, Integer> T = new TreeMap<>();
        //sort the points
        Point[] points = new Point[S.length * 2];
        int j = 0;
        for(int i = 0; i < S.length; i++) {
            points[j++] = S[i].getLeft();
            points[j++] = S[i].getRight();
        }

        HeapSort hs = new HeapSort();
        hs.sort(points);
        //hs.printArray(points);
        for(int i = points.length - 1; i >= 0; i--) {
            Point p = points[i];
            Line parent = p.getParent();
            if(p.getLeft()) {
                T.put(parent, p.getY());
                Line a = Above(T, parent);
                Line b = Below(T, parent);
                if(((a != null) && parent.intersects(a)) || 
                   ((b != null) && parent.intersects(b))) {
                       System.out.println("Found with left endpoint line " + parent.toString());
                       return true;                       
                   }
            }
            else {
                Line a = Above(T, parent);
                Line b = Below(T, parent);
                if(((a != null) && (b != null)) && 
                   a.intersects(b)) {
                       System.out.println("Found with " + a.toString() + "\nand " + b.toString());
                        return true;
                   }
                    
                T.remove(parent);
            }
        }
        return false;
    }
    
    /**
     * @method Above
     * @param T
     * @param l
     * @return Line above the parameter passed, l, in T,
     *  a self-balancing binary search tree. Done by searching
     *  for the next highest key in l. Null if none.
     */
    public Line Above(TreeMap<Line, Integer> T, Line l) {
        return T.higherKey(l);
    }
    /**
     * @method Below
     * @param T
     * @param l
     * @return Line below the parameter passed, l, in T,
     *  a self-balancing binary search tree. Done by searching
     *  for the next highest key in l. Null if none.
     */
    public Line Below(TreeMap<Line, Integer> T, Line l) {
        return T.lowerKey(l);
    }

    /**
     * @method anySegmentsIntersectRadixSort
     * @param S
     * @return Whether or not any segments were found to intersect.
     * @description This method sorts each point given from the Line
     *  array parameter S using Radix sort with the x coordinate. The
     *  method then determines whether any segments intersect, utilizing
     *  a vEB tree to maintain the sweeping line status. Null if none.
     */
    public boolean anySegmentsIntersectRadixSort(Line[] S) {
            VEBPQStruct<Line> T = new VEBPQStruct<>(S.length);
            //sort the points
            Point[] points = new Point[S.length * 2];
            int j = 0;
            for(int i = 0; i < S.length; i++) {
                points[j++] = S[i].getLeft();
                points[j++] = S[i].getRight();
            }
    
            RadixSort rs = new RadixSort();
            rs.sort(points);
            //rs.printArray(points);
            for(int i = 0; i < points.length; i++) {
                Point p = points[i];
                Line parent = p.getParent();
                if(p.getLeft()) {
                    T.lineInsert(parent);
                    //T.insert(parent, p.getY());
                    Line a = Above(T, parent);
                    Line b = Below(T, parent);
                    if(((a != null) && parent.intersects(a)) || 
                       ((b != null) && parent.intersects(b))) {
                           System.out.println("Found with left endpoint line " + parent.toString());
                           return true;                       
                       }
                }
                else {
                    Line a = Above(T, parent);
                    Line b = Below(T, parent);
                    if(((a != null) && (b != null)) && 
                        a.intersects(b)) {
                        System.out.println("Found with " + a.toString() + "\nand " + b.toString());
                            return true;
                    }
                    T.delete(T.indexOf(parent));
                    //T.delete(parent.getLeft().getY());
                }
            }
        return false;
    }
    /**
     * @method Above
     * @param T
     * @param l
     * @return The line above the one passed, l, based on the
     *  current sweeping line status. Null if none.
     */
    private Line Above(VEBPQStruct<Line> T, Line l) {
        return T.get(T.treeSuccessor(T.indexOf(l)));
        //return T.get(T.treeSuccessor(l.getLeft().getY()));
    }
        /**
     * @method Below
     * @param T
     * @param l
     * @return The line below the one passed, l, based on the
     *  current sweeping line status. Null if none.
     */
    private Line Below(VEBPQStruct<Line> T, Line l) {
        return T.get(T.treePredecessor(T.indexOf(l)));
        //return T.get(T.treeSuccessor(l.getLeft().getY()));
    }
}
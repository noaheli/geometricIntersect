import java.util.ArrayList;

public class VEBPQStruct<T extends Comparable>  {
    int universe;
    double truniverse = 2;
    int min = Integer.MAX_VALUE, max = Integer.MIN_VALUE;
    T minContainer = null, maxContainer = null;
    VEBPQStruct<T>[] clusters;
    VEBPQStruct<T> summary = null;

    /**
     * @method VEBPQStruct
     * @returns constructor
     * @description Default constructor, initalizes universe size to be 2.
     *              Unlikely to be used.
     */
    public VEBPQStruct() {

        universe = 2;
    }

    /**
     * @method VEBPQStruct
     * @param universe
     * @returns constructor
     * @description Overloaded constructor for the VEB Priority Queue Structure.
     *              Generates a list of empty clusters and initializes this cluster's
     *              summary.
     */
    public VEBPQStruct(int universe) {
        double size = 2;
        if(universe != 2) {

            for(int i = 1; i < 6; i++)
            {
                size = Math.pow(2, Math.pow(2, i));
                if(size >= universe) {
                    universe = (int)size;
                    truniverse = size;
                    break;
                }
            }
            clusters = new VEBPQStruct[((int)Math.sqrt(size))];
            summary = new VEBPQStruct((int)(Math.sqrt(size)));
        }
        this.universe = universe;
    }

    /**
     * @method treeMin
     * @returns int
     * @description Returns the Tree's minimum priority.
     */
    public int treeMin() { return min; }

    /**
     * @nmethod treeMax
     * @returns int
     * @description Returns the Tree's maximum priority.
     */
    public int treeMax() { return max; }

    /**
     * @method treeMinVal
     * @returns T
     * @description Returns the generic value stored in the minimum priority's node
     */
    public T treeMinVal() { return minContainer; }

    /**
     * @method treeMaxVal
     * @returns T
     * @description Returns the generic value stored in the maximum priority's node
     */
    public T treeMaxVal() { return maxContainer; }

    /**
     * @method high
     * @param x
     * @returns int
     * @description Returns the index of which cluster the current variable (passed by x) would be located within.
     */
    private int high(int x) {
        return (int)Math.floor(x / (int)(((Math.pow(2, Math.floor((Math.log(truniverse) / Math.log(2)) / 2))))));
    }
    /**
     * @method low
     * @param x
     * @returns int
     * @description Returns the index to which x belongs to in the deeper clusters.
     */
    private int low(int x) {
        return (x % (int)(((Math.pow(2, Math.floor((Math.log(truniverse) / Math.log(2)) / 2))))));
    }
    /**
     * @method index
     * @param x
     * @param y
     * @returns int
     * @description The calculated index which indicates a position in a higher level VEBStruct
     *          based on the parameters given.
     */
    private int index(int x, int y) {
        return ((x * (int)Math.floor((Math.pow(2, (Math.log(truniverse) / Math.log(2)) / 2))) + y));
    }

    /**
     * @method treeMember
     * @param x
     * @returns boolean
     * @description Returns whether or not a value with the indicated priority exists
     *              within the tree
     */
    public boolean treeMember(int x) {
        if(x == min || x == max) return true;
        else if (universe == 2) return false;
        else return (clusters[high(x)] == null) ? false : clusters[high(x)].treeMember(low(x));
    }

    /**
     * @method emptyTreeInsert
     * @param val
     * @param x
     * @description Inserts the value and the priority into the max and min. Only called
     *              on an empty cluster.
     */
    private void emptyTreeInsert(T val, int x) {
        minContainer = val;
        maxContainer = val;
        this.min = x;
        this.max = x;
    }

    /**
     * @method insert
     * @param val
     * @param x
     * @returns void
     * @description Inserts the value (val) into the index (x) within the tree. Called recursively
     *              on deeper clusters.
     *
     */
    public void insert(T val, int x) {
        
        if(min == Integer.MAX_VALUE) {
            emptyTreeInsert(val, x);
            insert(val, x);
        }
        else {
            if (x < min) {
                min = x;
                minContainer = val;
            }
            if (universe > 2) {
                int hi = high(x), lo = low(x);
                if(clusters[hi] == null) clusters[hi] = new VEBPQStruct<>(high(universe));
                if(summary == null) summary = new VEBPQStruct<>(high(universe));
                if (clusters[hi].treeMin() == Integer.MAX_VALUE) {
                    summary.insert(val, hi);
                    clusters[hi].emptyTreeInsert(val, lo);
                    clusters[hi].insert(val, lo);
                } else clusters[hi].insert(val, lo);
            }
            if (x > max) {
                max = x;
                maxContainer = val;
            }
        }
    }

    /**
     * @method delete
     * @param x
     * @returns void
     * @description Deletes the value at the given index (x) from every iteration of the
     *              Van Emde Boas Structure.
     */

    public T delete(int x) {
        int hi = high(x), lo = low(x);
        T ret = null;
        if(min == max) {
            ret = minContainer;
            min = Integer.MAX_VALUE;
            max = Integer.MIN_VALUE;
            minContainer = null;
            maxContainer = null;
            if(universe != 2) {
                ret = clusters[hi].delete(lo);
            }
        }
        else if(universe == 2) {
            if(x == 0) {
                ret = minContainer;
                min = 1;
                minContainer = maxContainer;
            }
            else {
                ret = maxContainer;
                min = 0;
            }
            max = min;
            maxContainer = minContainer;
        }
        else {
            if(x == min) {
                int cluster = summary.treeMin();
                x = index(cluster, clusters[cluster].treeMin());
                T minVal = clusters[cluster].treeMinVal();
                min = x;
                ret = minContainer;
                minContainer = minVal;
            }
            ret = clusters[hi].delete(lo);
            if(clusters[hi].treeMin() == Integer.MAX_VALUE) {
                summary.delete(hi);
                if(x == max) {
                    int sumMax = summary.treeMax();
                    ret = maxContainer;
                    if(sumMax == Integer.MIN_VALUE) {
                        max = min;
                        maxContainer = minContainer;
                    }
                    else {
                        System.out.println(truniverse + ": " + x + " | " + sumMax);
                        int temp = clusters[sumMax].treeMax();
                        max = index(sumMax, temp);
                        maxContainer = clusters[sumMax].treeMaxVal();
                    }
                }
            }
            else if(x == max) {
                ret = maxContainer;
                max = index(hi, clusters[hi].treeMax());
                maxContainer = clusters[hi].treeMaxVal();
            }
        }
        return ret;
    }

    /**
     * @method treeSuccessor
     * @param x
     * @returns int, the index if successor exists, -1 otherwise.
     * @description Returns the index of the next highest priority, given priority x.
     */
    public int treeSuccessor(int x) {
        if(universe == 2) {
            if(x == 0 && max == 1) return 1;
            else return Integer.MIN_VALUE;
        }
        else if(min != Integer.MAX_VALUE && x < min) return min;
        else {
            int maxLow = clusters[high(x)].treeMax();
            if(maxLow != Integer.MIN_VALUE && low(x) < maxLow) {
                int offset = clusters[high(x)].treeSuccessor(low(x));
                return index(high(x), offset);
            }
            else {
                int succCluster = summary.treeSuccessor(high(x));
                if(succCluster == Integer.MIN_VALUE) return Integer.MIN_VALUE;
                else {
                    int offset = clusters[succCluster].treeMin();
                    return index(succCluster, offset);
                }
            }
        }
    }

    /**
     * @method treePredecessor
     * @param x
     * @returns int, the index if predecessor exists, -1 otherwise.
     * @description Returns the index of the next lowest priority, given priority x.
     */
    public int treePredecessor(int x) {
        if(universe == 2) {
            if(x == 1 && min == 0) return 0;
            else return Integer.MIN_VALUE;
        }
        else if(max != Integer.MIN_VALUE && x > max) return max;
        else {
            int minLow = clusters[high(x)].treeMin();
            if(minLow != Integer.MAX_VALUE && low(x) > minLow) {
                int offset = clusters[high(x)].treePredecessor(low(x));
                return index(high(x), offset);
            }
            else {
                int predCluster = summary.treePredecessor(high(x));
                if(predCluster == Integer.MIN_VALUE) {
                    if(min != Integer.MAX_VALUE && x > min) return min;
                    else return Integer.MIN_VALUE;
                }
                else {
                    int offset = clusters[predCluster].treeMax();
                    return index(predCluster, offset);
                }
            }
        }
    }
    /**
     * @method: ExtractMax
     * @returns int
     * @description the current maximum after it has been successfully removed from the tree,
     *          -1 if there is not value in the tree.
     */
    public int ExtractMax() {
        if(max > 0 && min < universe) {
            int ret = max;
            this.delete(max);
            return ret;
        }
        return -1;
    }

    /**
     * @method IncreaseKey
     * @param value
     * @param i
     * @param priority
     * @returns boolean
     * @description Removes the value at the indicated index and replaces it into the position
                    of one higher priority than the current max.
     */
    public boolean IncreaseKey(int i) {
        if(this.treeMember(i)) {
            if(this.max != universe - 1) {
                T val = delete(i);
                this.insert(val, i + 1);
                return true;
            }
            else { 
                System.err.println("Tree overflow!");
            }
        }
        return false;
    }

    //The methods below are a part of my previous implementation which ran much worse and are
    //  now out of use.

    /**
     * @method shiftRight
     * @param int index
     * @return void
     * @description Shifts all values from max descending to index
     * one index to the right.
     */
    public void shiftRight(int index) {
        for(int i = max; i >= index; i--) {
            if(treeMember(i)) {
                IncreaseKey(i);
            }
        }
    }
    /**
     * @method get
     * @param int index
     * @return The value at the given index, null otherwise.
     */
    public T get(int index) { 
        if(index < 0 || index >= universe) return null;
        if(universe == 2) {
            if(index == 1) return (max == 1) ? maxContainer : null;
            return (min == 0) ? minContainer : null;
        }
        return clusters[this.high(index)].get(this.low(index));
    }
    /**
     * @method indexWalk
     * @param T val
     * @return Value of the index where
     */
    public int indexWalk(T val) {
        if((maxContainer != null) && val.compareTo(maxContainer) > 0) return max + 1;
        //System.out.println("max: " + max);
        for(int i = 0; i < max; i++) {
            T got = get(i);
            if((got != null) && val.compareTo(got) > 0) return i+1;
        }
        return 0;
    }
    /**
     * @method lineInsert
     * @param T
     * @return Whether or not line insert was successful. True
     *  pretty much always.
     * @description This method is the crux of my runtimes. It searched
     *  the vEB structure, in order, at each index until it finds which index
     *  it should be inserted at. It then shifts all values at the index and beyond
     *  to the right, and then inserts the value there.
     */
    public boolean lineInsert(T l) {
            int ind = indexWalk(l);
            shiftRight(ind);
            insert(l, ind);
            return true;
    }
    /**
     * @method indexOf
     * @param T
     * @return index of where param l is in the vEB structure.
     *  Searched the structure linearly.
     */
    public int indexOf(T l) {
        for( int i = 0; i <= max; i++) {
            T x = get(i);
            if(l.compareTo(x) == 0) return i;
        }
        return -1;
    }
    /**
     * @method getMax
     * @return the Index of the max rather than the max value.
     */
    public int getMax() { return max; }


    public static void main(String[] args) {
        //Playground for testing new functions
        VEBPQStruct<Line> x = new VEBPQStruct(16);
        x.insert(new Line(0,0,1,2), 0);
        Line segments[] = new Line[6];
        segments[2] = new Line(0, 0, 4, 5);
        segments[1] = new Line(2, 3, 7, 1);
        segments[0] = new Line(0, 1, 3, 9);
        segments[3] = new Line(10, 2, 11, 7);
        segments[4] = new Line(5, 1, 1, 5);
        segments[5] = new Line(4, 1, 5, 3);
        for(int i = 0; i < 6; i++) {
            System.out.println("--begin walk--");
            for(int j = 0; j <= x.getMax(); j++) {
                System.out.println(j + ": " + x.get(j));
            }
            System.out.println("--end walk--");
            int ind = x.indexWalk(segments[i]);
            System.out.println(segments[i].toString() + " | " + ind);
            x.shiftRight(ind);

            x.insert(segments[i], ind);
        }
        
        //x.delete(0);
        System.out.println("-- start --");
        System.out.println(x.get(0));
        System.out.println(x.get(1));
        System.out.println(x.get(2));
        System.out.println(x.get(3));
        System.out.println(x.get(4));
        System.out.println(x.get(5));
        System.out.println(x.get(6));
        System.out.println(x.get(7));
    }
}
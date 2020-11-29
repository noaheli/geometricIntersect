import java.util.ArrayList;
public class Runner {
    /**
     * @method main
     * @description Runs the varied methods of determining intersects
     */
    public static void main(String[] args) {
        Runner run = new Runner();
        for(int n = 100; n <= 1000000; n *= 10)
        {
            System.out.println("\nStarting cycle with n = "+ n);
            int xmin = Integer.MAX_VALUE, xmax = Integer.MIN_VALUE,
                ymin = Integer.MAX_VALUE, ymax = Integer.MIN_VALUE;

            Line arr[] = new Line[n];
            for(int i = 0; i < n; i++)
            {
                int x1 = i, 
                    x2 = i + 1,
                    y1 = i, 
                    y2 = i * (((int)(Math.random() * 8)) + 2);
                if(x1 < xmin || x2 < xmin) xmin = (x1 < x2) ? x1 : x2;
                if(x1 > xmax || x2 > xmax) xmax = (x1 > x2) ? x1 : x2;
                if(y1 < ymin || y2 < ymin) ymin = (y1 < y2) ? y1 : y2;
                if(y1 > ymax || y2 > ymax) ymax = (x1 > x2) ? y1 : y2;
                arr[i] = new Line(x1, y1, x2, y2);
            }
            Point l = arr[n-1].getLeft();
            l.setY(1);
            l.setX(n - 2);
            arr = run.shuffle(arr);
            int u = Integer.max(xmax - xmin, ymax - ymin);
            run.intersect(n, u, arr, ymax);
            arr[0].getRight().setX(115 * n);
            arr[0].getLeft().setX((115 * n) - 15);
            u = (115 * n) - xmin;
            run.intersect(n, u, arr, ymax);
        }
            
    }

    /**
     * @method intersect
     * @param n
     * @param u
     * @param arr
     * @param maxy
     * @returns void
     * @description Runs the proper intersection method on the given parameter arr,
     *  which is an array of Lines.
     */
    public void intersect(int n, int u, Line arr[], int maxy) {
        long startTime, endTime;
        Sweepline sweep = new Sweepline();
        if( u > (2 * n) && u < (10 * n) ) {
            System.out.println("Running smooth set with u: " + u);
            startTime = System.nanoTime();
            sweep.anySegmentsIntersectRadixSort(arr, maxy);
            endTime = System.nanoTime();
            System.out.println("Completed in " + (endTime - startTime) + " nanoseconds.");
        }
        else {
            System.out.println("Running sparse set with u: " + u);
            startTime = System.nanoTime();
            sweep.anySegmentsIntersectHeapSort(arr);
            endTime = System.nanoTime();
            System.out.println("Completed in " + (endTime - startTime) + " nanoseconds.");
        }
        
    }

    /**
     * @emthod shuffle
     * @param arr
     * @description Shuffles the passed array parameter as to maximize random variability
     *  amongst the Lines before passing to intersection algorithms. 
     *  Taken and adapted from https://bost.ocks.org/mike/shuffle/
     */
    public Line[] shuffle(Line arr[]) {
        int m = arr.length, i;
        Line t;
        while (m > 0) {
            i = (int)Math.floor(Math.random() * m--);
            Line temp = arr[m];
            arr[m] = arr[i];
            arr[i] = temp;
        }
        return arr;
    }
}


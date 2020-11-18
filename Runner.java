import java.util.ArrayList;
public class Runner {
    public static void main(String[] args) {
        Runner run = new Runner();
        for(int n = 100; n <= 1000000; n *= 10)
        {
            System.out.println("\nStarting cycle with n = "+ n);
            ArrayList<Integer> coord = new ArrayList<>(20 * n);
            int xmin = Integer.MAX_VALUE, xmax = Integer.MIN_VALUE,
            ymin = Integer.MAX_VALUE, ymax = Integer.MIN_VALUE;
            for(int i = 0; i < 10*n; i++) {
                coord.add(i);
            }
            Line arr[] = new Line[n];
            for(int i = 0; i < n; i++)
            {
                int x1 = coord.remove((int)((Math.random() * coord.size()))), 
                    x2 = coord.remove((int)((Math.random() * coord.size()))),
                    y1 = coord.remove((int)((Math.random() * coord.size()))), 
                    y2 = coord.remove((int)((Math.random() * coord.size())));
                if(x1 < xmin || x2 < xmin) xmin = (x1 < x2) ? x1 : x2;
                if(x1 > xmax || x2 > xmax) xmax = (x1 > x2) ? x1 : x2;
                if(y1 < ymin || y2 < ymin) ymin = (y1 < y2) ? y1 : y2;
                if(y1 > ymax || y2 > ymax) ymax = (x1 > x2) ? y1 : y2;
                arr[i] = new Line(x1, y1, x2, y2);
            }
            int u = Integer.max(xmax - xmin, ymax - ymin);
            run.intersect(n, u, arr);
            arr[0].getRight().setX(115 * n);
            arr[0].getLeft().setX((115 * n) - 15);
            u = (115 * n) - xmin;
            run.intersect(n, u, arr);
        }
            
    }

    public void intersect(int n, int u, Line arr[]) {
        long startTime, endTime;
        Sweepline sweep = new Sweepline();
        if( u > (2 * n) && u < (10 * n) ) {
            System.out.println("Running smooth set with u: " + u);
            startTime = System.nanoTime();
            sweep.anySegmentsIntersectRadixSort(arr);
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
}


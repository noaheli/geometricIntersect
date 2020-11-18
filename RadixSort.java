import java.util.Arrays;
/**
 * Taken and adapted from https://www.geeksforgeeks.org/radix-sort/
 */
public class RadixSort {
    public RadixSort() { }
    /**
     * @method sort
     * @param arr
     * @description Method called to sort an array of Points based
     *  on the value of their x-coordinate.
     */
    public void sort(Point arr[]) {
        //find max to know max of digits
        int m = getMax(arr, arr.length);

        //Do counting sort for every digit
        for(int exp = 1; m / exp > 0; exp *= 10) {
            countSort(arr, arr.length, exp);
        }
        //final walk to swap all points with matching x values
        //according to y coordinates. If all points had matching
        //x coords and were in descending order with respect to y coord,
        //this walk adds O(n^2) runtime.
        //
        //Note: Later discovered this run will never occur be necessary
        //  due to differing x coordinate values.
        for(int i = 1; i < arr.length; i++) {
            if(arr[i].getX() == arr[i-1].getX()) {
                if(arr[i].getY() < arr[i-1].getY()) {
                    Point temp  = arr[i];
                    arr[i] = arr[i - 1];
                    arr[i - 1] = temp;
                    i = (i == 1) ? i-1 : i-2;
                    continue;
                }
            }
        }
    }
    /**
     * @method countSort
     * @param arr
     * @param n
     * @param exp
     * @description Sorts the current values based on whatever
     *  digit is indicated by the param exp.
     */
    private void countSort(Point arr[], int n, int exp) {
        Point output[] = new Point[n];
        
        int count[] = new int[10];
    
        Arrays.fill(count, 0);

        for(int i = 0; i < n; i++) {
            count[(arr[i].getX() / exp) % 10]++;             
        }

        for(int i = 1; i < 10; i++) {
            count[i] += count[i - 1];
        }

        for(int i = n - 1; i >= 0; i--) {
            output[(count[(arr[i].getX() / exp) % 10] - 1)] = arr[i];
            count[(arr[i].getX() / exp) % 10]--;
        }

        for(int i = 0; i < n; i++)
            arr[i] = output[i];
    }
    /**
     * @method getMax
     * @param arr
     * @param n
     * @return Max integer x-coordinate value within the array.
     *  Used to get the highest possible digit for use in radix sort.
     */
    private int getMax(Point arr[], int n) {
        int mx = arr[0].getX();
        for(int i = 0; i < n; i++) {
            if(arr[i].getX() > mx)
                mx = arr[i].getX();
        }
        return mx;
    }
    public void printArray(Point arr[]) 
    { 
        for (int i = 0; i < arr.length; i++) 
            System.out.print(arr[i].toString() + " ");
        System.out.println();
    } 
}
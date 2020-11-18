/**
 * @class HeapSort
 * @description Methods taken and adapted from https://www.geeksforgeeks.org/heap-sort/
 */
public class HeapSort<T extends Comparable<T>> { 
    /**
     * @method sort
     * @param arr
     * @description Public method to be called on an initially unsorted array. Turns
     *  the array into a max heap structure.
     */
    public void sort(T arr[]) {
        int n = arr.length;
        for(int i=n/2; i >= 0; i--)
        {
            //printArray(arr);
            heapify(arr, n, i);
        }
        
        for(int i=n-1; i >= 0; i--)
        {
            T temp = arr[0];
            arr[0] = arr[i];
            arr[i] = temp;
            heapify(arr, i, 0);
        }
    }
    /**
     * @method heapify
     * @param arr
     * @param n
     * @param i
     * @description Sorts the given heap by comparing the parent with its two children.
     *  When a swap is done, the method is recalled on the initial parent and newly-made child.
     */
    private void heapify(T arr[], int n, int i) {
        int largest = i;
        int l = 2*i + 1;
        int r = 2*i + 2;

        //If left > root
        if(l < n && arr[l].compareTo(arr[largest]) > 0) largest = l;

        //If right > root
        if(r < n && arr[r].compareTo(arr[largest]) > 0) largest = r;

        //If largest is no longer the root
        if(largest != i) {
            T swap = arr[i];
            //System.out.println("swapping " + swap.toString() + " and " + arr[largest].toString());
            arr[i] = arr[largest];
            arr[largest] = swap;

            heapify(arr, n, largest);
        }
    }
    /* A utility function to print array of size n */
    public void printArray(T arr[]) 
    { 
        int n = arr.length; 
        for (int i=0; i<n; ++i) {
            if(arr[i] == null) break;
            System.out.print(arr[i].toString()+" "); 
        }
            
        System.out.println(); 
    }
}
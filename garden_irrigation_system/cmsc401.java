import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;

/*************************
* Roy Chung
* CMSC401
* Programming Assignment 2
*************************/

public class cmsc401 {
    public static void main(String[] args) {
        //create scanner
        Scanner in = new Scanner(System.in);

        //scan number of gardens
        int numGardens = in.nextInt();

        //scan garden size and location and populate respective arrays with information
        int[] coordinate = new int[numGardens];
        int[] size = new int[numGardens];
        for (int i = 0; i < numGardens; i++) {
            coordinate[i] = in.nextInt();
            size[i] = in.nextInt();
        }

        in.close();

        //create arraylist to comvine garden information
        ArrayList<Integer> gardenArrayList = new ArrayList<>();
        for(int i = 0; i < numGardens; i++){
            gardenArrayList.add(coordinate[i]);
            if (size[i] > 1) {
                gardenArrayList.add(coordinate[i]);
            }
            if (size[i] > 2) {
                gardenArrayList.add(coordinate[i]);
            }
        }

        //convert ArrayList to Array
        int[] gardenArray = new int[gardenArrayList.size()];
        for (int i=0; i < gardenArray.length; i++) {
            gardenArray[i] = gardenArrayList.get(i);
        }

        //get middle index of array
        int midpoint = (int) Math.ceil(gardenArray.length/2.0);
        
        //get result
        int optimalY = getOptimalY(gardenArray, 0, gardenArray.length - 1, midpoint);

        //print result
        System.out.println(optimalY);
    }

    //swap two elements in an array
    static void swap(int[] a, int index1, int index2) {
        int temp = a[index1];
        a[index1] = a[index2];
        a[index2] = temp;
    }

    //partition - algorithm from class notes
    static int partition(int a[], int p, int r){
        int x = a[r];
        int i = p-1;
        for (int j = p; j < r; j++) {
            if (a[j] <= x) {
                i++;
                swap(a, i, j);
            }
        }
        swap(a, i+1, r);
        return i + 1;
    }

    static int getOptimalY(int a[], int p, int r, int midpoint) {

        //if there is only one element in the range of the array passed to this method, then return it
        if (p == r && p == midpoint) {
            return a[midpoint];
        }

        //randomize pivot q for better run time
        Random random = new Random();
        int randomIndex = random.nextInt(r - p) + p;
        swap(a,r,randomIndex);
        int q = partition(a, p, r);

        if (q < midpoint) {
            return getOptimalY(a, q+1, r, midpoint);
        } else if (q > midpoint) {
            return getOptimalY(a, p, q-1, midpoint);
        } else {
            return a[q];
        }
    }
}
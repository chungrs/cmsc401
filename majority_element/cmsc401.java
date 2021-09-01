/*************************
* Roy Chung
* CMSC401
* Programming Assignment 1
*************************/
import java.util.Scanner;

public class cmsc401 {
    public static void main(String[] args){
		
		//populate array with inputs
        Scanner in = new Scanner(System.in);
		int arraySize = in.nextInt();
		int[] array = new int[arraySize];
        for (int i = 0; i < arraySize; i++) {
            array[i] = in.nextInt();
		}
		in.close();

		//Boyer & Moore Algorithm for finding potential N/3 MEs
		//declare candidate and counter variables
		int candidate1 = -1, candidate2 = -1, counter1 = 0, counter2 = 0;
		for (int i: array) {
			if (candidate1 == i) { //increment counter1 if candidate1 is equal to current element
				counter1++;
			} else if (candidate2 == i) { //increment counter2 if candidate2 is equal to current element and candidate 1 isn't
				candidate2 = i;
				counter2++;
			} else if (counter1 == 0) { //if counter1 is  0, make candidate1 equal to current element and increment counter1
				candidate1 = i;
				counter1++;
			} else if (counter2 == 0) { //if counter1 is greater than 0 and if counter2 is 0, make candidate2 equal to current element and increment counter2
				candidate2 = i;
				counter2++;
			} else { //decrement counter1 and counter2 if a third element is seen
				counter1--;
				counter2--;
			}
		}

		printResults(candidate1, candidate2, array);
	}

	/**determines whether candidates are in fact n/3 majority elements*/
	static boolean isMajority(int candidate, int[] array) {
		//target = number of elements divided by 3
		int target = array.length / 3;
		int count = 0;
		for (int i : array) {
			if (i == candidate)
				count++;
		}

		if (count > target)
			return true;

		return false;
	}

	/**prints results and arranges them in increasing order when necessary*/
	static void printResults(int candidate1, int candidate2, int[] array) {

		boolean candidate1Majority = isMajority(candidate1, array), candidate2Majority = isMajority(candidate2, array);

		if (candidate1Majority && candidate2Majority) { //if candidates 1 and 2 are both in fact MEs then print them in increasing order
			if (candidate1 < candidate2)
				System.out.println(candidate1 + " " + candidate2);
			else
			System.out.println(candidate2 + " " + candidate1);
		} else if (candidate1Majority) { //only candidate1 is ME - print candidate1
			System.out.println(candidate1);
		} else if (candidate2Majority) { //only candidate2 is ME - print candidate2
			System.out.println(candidate2);
		} else { //neither candidates are ME - print -1
			System.out.println(-1);
		}
	}
}
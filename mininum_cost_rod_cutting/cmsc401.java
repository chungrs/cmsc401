/*************************
 * Roy Chung
 * CMSC401
 * Programming Assignment 4
 *************************/

import java.util.Scanner;
import java.util.Arrays;

public class cmsc401 {

    static class Rod {
        private int rodSize;
        private int numCutPoints;
        private int[] m; //cut points
        private int[][] dp; //memoized results

        public Rod(int rodSize, int numCutPoints) {
            setRodSize(rodSize);
            setNumCutPoints(numCutPoints);

            //initialize dp and m arrays
            dp = new int[numCutPoints][numCutPoints];
            this.m = new int[numCutPoints];

            //populate dp with -1
            for (int i = 0; i < dp.length; i++) {
                Arrays.fill(dp[i], -1);
            }
        }

        public void setRodSize(int rodSize) {
            this.rodSize = rodSize;
        }

        public void setNumCutPoints(int numCutPoints) {
            this.numCutPoints = numCutPoints;
        }

        public void addCutPoint(int i, int cutPoint) {
            m[i] = cutPoint;
        }

        public int getNumCutPoints() {
            return numCutPoints;
        }

        public int minimumCost() {
            return minimumCost(0, numCutPoints-1, 0, rodSize);
        }
    
        public int minimumCost(int leftCutIndex, int rightCutIndex, int segmentStart, int segmentEnd) {

            //base case - if the cutting indecies are out of range or if the left cutting index moves to the right of the right index, return 0
            if (rightCutIndex >= numCutPoints || leftCutIndex > rightCutIndex)
                return 0;

            //if the cutting indecies are the same, then the cost is simply the length of the current segment
            if (leftCutIndex == rightCutIndex)
                return segmentEnd - segmentStart;

            //return memoized result if one exists
            if (dp[leftCutIndex][rightCutIndex] != -1)
                return dp[leftCutIndex][rightCutIndex];
            
            //set current minimum cost to "infinity"
            int currentMin = Integer.MAX_VALUE;

            //loop through cutting points between the left and right cut points
            //recurively find the minimum costs of the left and right segments of the rod.
            //Left segment is the segment from the start of the current rod segment to cutting point m[i], right is m[i] to the end of the current segment
            //cost is the length of the current rod segment + the minimumCost of cuts to the left and right of m[i], update current minimum cost when lower cost is found
            for(int i = leftCutIndex; i <= rightCutIndex; i++)
                currentMin = Math.min(segmentEnd-segmentStart + minimumCost(leftCutIndex, i-1, segmentStart, m[i]) + minimumCost(i+1, rightCutIndex, m[i], segmentEnd), currentMin);

            //memoize result
            dp[leftCutIndex][rightCutIndex] = currentMin;
    
            return currentMin;
        }
    }

    public static void main(String[] args) {
        //create scanner
        Scanner in = new Scanner(System.in);

        //scan rod size
        int rodSize = in.nextInt();

        //scan number of cutting points
        int m = in.nextInt();

        //create rod object
        Rod rod = new Rod(rodSize, m);

        //scan cutting points
        for (int i = 0; i < rod.getNumCutPoints(); i++)
            rod.addCutPoint(i, in.nextInt());

        //close scanner
        in.close();

        //print reesult
        System.out.println(rod.minimumCost());
    }
}
/*************************
* Roy Chung
* CMSC401
* Programming Assignment 3
*************************/

import java.util.Scanner;
import java.util.Arrays;
import java.util.ArrayList;

public class cmsc401 {

    static class Graph{

        private final int INFINITY = Integer.MAX_VALUE/2;
        private int numVertices;
        private int matrix[][];
        private boolean Q[];
        private int qCount;
        private Vertex vertexArray[];
        
        //Vertex class for creating vertex objects
        static class Vertex {
            final int INFINITY = Integer.MAX_VALUE/2;
            private int index;
            private int distance;
            private int predecessor;
            
            public Vertex (int index, int distance, int predecessor) {
                setIndex(index);
                setDistance(distance);
                setPredecessor(predecessor);
            }
        
            public int getIndex() {
                return index;
            }
        
            public int getDistance() {
                return distance;
            }
        
            public int getPredecessor() {
                return predecessor;
            }
        
            public void setIndex(int index) {
                this.index = index;
            }
        
            public void setDistance(int distance) {
                this.distance = distance;
            }
        
            public void setPredecessor(int predecessor) {
                this.predecessor = predecessor;
            }
        }
        

        public Graph(int numVertices) {
            this.numVertices = numVertices;
            matrix = new int[numVertices][numVertices];
        }

        //add edges - add once for x,y and again for y,x because the graph is undirected
        public void addEdge(int source, int destination, int weight) {
            matrix[source][destination] = weight;
            matrix[destination][source] = weight;
        }

        //method for factoring in hotel costs and making the weights infinity for non-existent edges
        public void addHotels(int hotelCosts[][]) {
            //make the weight of non-adjacent cities infinity
            for (int i = 0; i < numVertices; i++) {
                for (int j = 0; j < numVertices; j++) {
                    if (i != j && matrix[i][j] == 0)
                        matrix[i][j] = INFINITY;
                }
            }
            
            for (int i = 0; i < numVertices - 2; i++) {
                for (int j = 0; j < numVertices; j++) {
                    if (j != hotelCosts[i][0] - 1 && matrix[j][hotelCosts[i][0] - 1] != INFINITY)
                        matrix[j][hotelCosts[i][0] - 1] += hotelCosts[i][1];
                }
            }
        }

        void Dijkstra() {
            InitializeSingleSource();
        
            //initialize Q array and populate it with true boolean values
            Q = new boolean[numVertices];
            Arrays.fill(Q, Boolean.TRUE);
            qCount = numVertices;

            while (qCount > 0) {
                int u = ExtractMin();

                for (int v = 0; v < numVertices; v++) {
                    if (matrix[u][v] != INFINITY) {
                        Relax(u, v);
                    }
                }
            }
        }

        void InitializeSingleSource() {
            //initialize array of vertex objects
            vertexArray = new Vertex[numVertices];

            //populate array with vertex objects that have distance values of INFINITY and predecessor values of -1 (NIL)
            for (int i = 0; i < numVertices; i++) {
                vertexArray[i] = new Vertex(i, INFINITY, -1);
            }

            //set distance of vertexArray[0] (source vertex) to 0
            vertexArray[0].setDistance(0);
        }

        void Relax(int u, int v) {
            if(vertexArray[v].getDistance() > (vertexArray[u].getDistance() + matrix[u][v])) {
                vertexArray[v].setDistance(vertexArray[u].getDistance() + matrix[u][v]);
                vertexArray[v].setPredecessor(vertexArray[u].getIndex());
            }
        }

        int ExtractMin() {
            int min_dist = INFINITY;
            int min_dist_v = -1;
            int n = Q.length;
            for(int v = 0; v < n; v++) {
                if (Q[v] == true && vertexArray[v].getDistance() <= min_dist) {
                    min_dist = vertexArray[v].getDistance();
                    min_dist_v = vertexArray[v].getIndex();
                }
            }

            Q[min_dist_v] = false;
            qCount--;

            return min_dist_v;
        }

        public void printCost() {
            int cost = 0;
            ArrayList<Integer> route = new ArrayList<Integer>();
            int destination = 1;
            route.add(destination);
            while (vertexArray[destination].getPredecessor() != -1) {
                route.add(vertexArray[destination].getPredecessor());
                destination = vertexArray[destination].getPredecessor();
            }

            for (int i = 0; i < route.size() - 1; i++) {
                cost += matrix[route.get(i)][route.get(i + 1)];
            }

            System.out.println(cost);
        }

    }
    public static void main(String[] args) {

        //create scanner
        Scanner in = new Scanner(System.in);

        //scan number of cities
        int numCities = in.nextInt();

        //weighted adjacency matrix
        Graph graph = new Graph(numCities);

        //scan number of highways
        int numHighways = in.nextInt();

        //array for storing hotel costs
        int[][] hotelCosts = new int[numCities - 2][2];

        //scan hotel costs
        for (int i = 0; i < numCities - 2; i++) {
            hotelCosts[i][0] = in.nextInt();
            hotelCosts[i][1] = in.nextInt();
        }

        //scan gas costs
        for (int i = 0; i < numHighways; i++) {
            int cityA = in.nextInt();
            int cityB = in.nextInt();
            int gasCost = in.nextInt();

            graph.addEdge(cityA - 1, cityB - 1, gasCost);
        }

        in.close();

        //factor in hotel cost to travel cost
        graph.addHotels(hotelCosts);

        //run dijkstra method on graph
        graph.Dijkstra();

        //print cost
        graph.printCost();
    }
}
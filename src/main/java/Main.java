import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
public class Main {
    static int numberOfIslands;
    static int numberOfFerries;
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String lineOne = reader.readLine();

        String[] partsOne = lineOne.split(" ");
        numberOfIslands = Integer.parseInt(partsOne[0]);
        int maxBridges = Integer.parseInt(partsOne[1]);
        numberOfFerries = Integer.parseInt(partsOne[2]);

        ArrayList<Edge>[] islands = new ArrayList[numberOfIslands + 1];

        for(int i = 0; i <= numberOfIslands;i++){
            islands[i] = new ArrayList<>();
        }

        for (int i = 0; i < maxBridges; i++){
            String line = reader.readLine();
            String[] parts = line.split(" ");
            int a = Integer.parseInt(parts[0]);
            int b = Integer.parseInt(parts[1]);
            int c = Integer.parseInt(parts[2]);
            Edge e = new Edge(a,b,c);
            islands[a].add(e);
            islands[b].add(e);
        }
        int[] prices = new int[numberOfIslands - 1];

        BitSet visitedIslands = new BitSet(numberOfIslands + 1); //keeps track of visited islands
        visitedIslands.set(1); //We visit island 1
        PriorityQueue<Edge> queue = new PriorityQueue<>(); //All the good edges
        queue.addAll(islands[1]); //Adds the inital edges from the first island

        int e = 0;
        while (e < numberOfIslands - 1){
            Edge cheapestEdge = queue.poll();
            if (!visitedIslands.get(cheapestEdge.start)){ //If cheapest route endpoint hasnt been visited
                queue.addAll(islands[cheapestEdge.start]); //Adds all routes from the destination of the cheapest edge
                visitedIslands.set(cheapestEdge.start); //Sets cheapest route as visited
                prices[e] = cheapestEdge.cost;
                e++;
            }
            if (!visitedIslands.get(cheapestEdge.destination)){ //If cheapest route endpoint hasnt been visited
                queue.addAll(islands[cheapestEdge.destination]); //Adds all routes from the destination of the cheapest edge
                visitedIslands.set(cheapestEdge.destination); //Sets cheapest route as visited
                prices[e] = cheapestEdge.cost;
                e++;
            }
        }
        Arrays.parallelSort(prices);
        int totalPris = 0;
        for(int i = 0; i < numberOfIslands-numberOfFerries-1;i++){
            totalPris += prices[i];
        }
        System.out.println(totalPris);
    }
}

class Edge implements Comparable<Edge> {
    int start;
    int destination;
    int cost;
    public Edge(int start, int destination, int cost) {
        this.start = start;
        this.destination = destination;
        this.cost = cost;
    }
    @Override
    public int compareTo(Edge o) {
        if (o == null) return Integer.MIN_VALUE;
        return this.cost - o.cost;
    }
}